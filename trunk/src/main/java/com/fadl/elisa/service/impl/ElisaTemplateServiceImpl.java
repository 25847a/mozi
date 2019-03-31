package com.fadl.elisa.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.elisa.dao.ElisaApiMapper;
import com.fadl.elisa.dao.ElisaCommonSettingMapper;
import com.fadl.elisa.dao.ElisaGrayAreaSettingsMapper;
import com.fadl.elisa.dao.ElisaMeasurementMethodMapper;
import com.fadl.elisa.dao.ElisaReportMapper;
import com.fadl.elisa.dao.ElisaTemplateMapper;
import com.fadl.elisa.dao.ElisaTemplateValuesMapper;
import com.fadl.elisa.entity.ElisaCommonSetting;
import com.fadl.elisa.entity.ElisaGrayAreaSettings;
import com.fadl.elisa.entity.ElisaMeasurementMethod;
import com.fadl.elisa.entity.ElisaReport;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.ElisaTemplateValues;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 酶标板模板表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Service
public class ElisaTemplateServiceImpl extends ServiceImpl<ElisaTemplateMapper, ElisaTemplate> implements ElisaTemplateService {
	@Autowired
	private ElisaGrayAreaSettingsMapper grayAreaSettingsMapper;
	@Autowired
	private ElisaReportMapper reportMapper;
	@Autowired
	private ElisaMeasurementMethodMapper measurementMethodMapper;
	@Autowired
	private ElisaCommonSettingMapper commonSettingMapper;
	@Autowired
	private ElisaTemplateValuesMapper  etvMapper;
	@Autowired
	private ElisaApiMapper apiMapper;
	@Autowired
	public LogService logService;
	/**
	 * 保存酶标板模板信息,级联保存其它关联信息
	 * @param elisaTemplate
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean insertTemplate(ElisaTemplate elisaTemplate, DataRow dataRow) throws Exception{
		baseMapper.insert(elisaTemplate);
		if(!doSaveOther(elisaTemplate, dataRow)) {
			throw new Exception();
		}else {
			logService.insertLog(IConstants.MODULE_TEST_TEMPLATE, IConstants.DESC.replace("{0}", "新增了酶标板模板配置,ID为"+elisaTemplate.getId()),"null");
		}
		
		dataRow.initSuccess();
		return  true;
	}
	
	
	/** 根据ID查找酶标仪模板设置,包含相关的信息,查询效率比较低,不建议都使用
	 *@param Serializable id
	 * @return
	 */
	@Override
	public ElisaTemplate selectById(Serializable id) {
		ElisaTemplate elisaTemplate = baseMapper.selectById(id);
		Long etId= elisaTemplate.getId();
		//找到报表设置 
		ElisaReport elisaReport =  new ElisaReport();
		elisaReport.setEtId(etId);
		elisaTemplate.setElisaReport(reportMapper.selectOne(elisaReport));
		// 找到常用设置
		ElisaCommonSetting setting = new ElisaCommonSetting();
		setting.setEtId(etId);
		elisaTemplate.setElisaCommonSetting(commonSettingMapper.selectOne(setting));
		//找到灰区设置
		ElisaGrayAreaSettings grayAreaSettings = new  ElisaGrayAreaSettings();
		grayAreaSettings.setEtId(etId);
		elisaTemplate.setElisaGrayAreaSettings(grayAreaSettingsMapper.selectOne(grayAreaSettings));
		//找到计量设置
		ElisaMeasurementMethod method = new ElisaMeasurementMethod();
		method.setEtId(etId);
		elisaTemplate.setElisaMeasurementMethod(measurementMethodMapper.selectOne(method));
		//找到模板值
		Wrapper<ElisaTemplateValues> wrapper = new EntityWrapper<ElisaTemplateValues>();
		wrapper.eq("etId", etId);
		wrapper.orderBy("etvIndex");
		elisaTemplate.setElisaTemplateValues(etvMapper.selectList(wrapper));
		elisaTemplate.setElisaApi(apiMapper.selectById(elisaTemplate.getApiId()));
		return  elisaTemplate;
	}

	/**
     * <p>
     * 根据 ID 删除 逻辑删除 更新delFlag=1
     * </p>
     *
     * @param id 主键ID
     * @return boolean
     */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteById(Serializable id) {
		ElisaTemplate template = baseMapper.selectById(id);
		template.setDelFlag(1);
		if(baseMapper.updateById(template)>1) {
			return false;
		}else {
			try {
				logService.insertLog(IConstants.MODULE_TEST_TEMPLATE, IConstants.DESC.replace("{0}", "删除了酶标板模板配置,ID为"+template.getId()),"null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 修改酶标板模板信息,级联保存其它关联信息
	 * @param elisaTemplate
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateTemplate(ElisaTemplate elisaTemplate, DataRow dataRow) throws Exception {
		baseMapper.updateById(elisaTemplate);
		// 先把绑定的其它属性先删除
		if(!doDelOther(elisaTemplate, dataRow)) {
			throw new Exception();
		}else {
			logService.insertLog(IConstants.MODULE_TEST_TEMPLATE, IConstants.DESC.replace("{0}", "更新了酶标板模板配置,ID为"+elisaTemplate.getId()),"null");
		}
		// 保存信息
		if(!doSaveOther(elisaTemplate, dataRow)) {
			throw new Exception();
		}
		dataRow.initSuccess();
		return true;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean doSaveOther(ElisaTemplate elisaTemplate, DataRow dataRow) {
		Long etid=elisaTemplate.getId();
		//保存酶标板模板值
		for(ElisaTemplateValues etv : elisaTemplate.getElisaTemplateValues()) {
			etv.setEtId(etid);
		}
		etvMapper.addEtvs(elisaTemplate.getElisaTemplateValues());
		// 将模板ID设置进其他关联实体
		ElisaGrayAreaSettings areaSettings = elisaTemplate.getElisaGrayAreaSettings();
		areaSettings.setEtId(etid);
		areaSettings.setCutoffFormula(elisaTemplate.getCutoffFormula());
		grayAreaSettingsMapper.insert(areaSettings);
		ElisaReport elisaReport = elisaTemplate.getElisaReport();
		elisaReport.setEtId(etid);
		reportMapper.insert(elisaReport);
		ElisaMeasurementMethod method = elisaTemplate.getElisaMeasurementMethod();
		method.setEtId(etid);
		measurementMethodMapper.insert(method);
		ElisaCommonSetting setting = elisaTemplate.getElisaCommonSetting();
		setting.setEtId(etid);
		commonSettingMapper.insert(setting);
		
		return true;
	}
	/**
	 * 删除绑定的杂项
	 * @param elisaTemplate
	 * @param dataRow
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean doDelOther(ElisaTemplate elisaTemplate, DataRow dataRow) {
		Long etId= elisaTemplate.getId();
		// 先把绑定的其它属性先删除
		Wrapper<ElisaReport> erwe = new EntityWrapper<ElisaReport>();
		erwe.eq("etId", etId);
		if(reportMapper.delete(erwe)>1) {
			dataRow.initFial("删除了多条报表记录 etId=" + etId);
			return false;
		}
		
		Wrapper<ElisaCommonSetting> ecswe = new EntityWrapper<ElisaCommonSetting>();
		ecswe.eq("etId", etId);
		if(commonSettingMapper.delete(ecswe)>1) {
			dataRow.initFial("删除了多条常用设置记录 etId=" + etId);
			return false;
		}
		Wrapper<ElisaGrayAreaSettings> egaswe = new EntityWrapper<ElisaGrayAreaSettings>();
		egaswe.eq("etId", etId);
		if(grayAreaSettingsMapper.delete(egaswe)>1) {
			dataRow.initFial("删除了多条灰区设置记录 etId=" + etId);
			return false;
		}
		Wrapper<ElisaMeasurementMethod> emmwe = new EntityWrapper<ElisaMeasurementMethod>();
		emmwe.eq("etId", etId);
		if(measurementMethodMapper.delete(emmwe)>1) {
			dataRow.initFial("删除了多条计量设置记录 etId=" + etId);
			return false;
		}
		Wrapper<ElisaTemplateValues> etvwe = new EntityWrapper<ElisaTemplateValues>();
		etvwe.eq("etId", etId);
		if(etvMapper.delete(etvwe)>96) {
			dataRow.initFial("删除了多条酶标板孔记录 etId=" + etId);
			return false;
		}
		
		return true;
	}
	/**
	 * 根据ID查找酶标仪模板设置信息,包含API设置
	 * @param id
	 * @return
	 */
	@Override
	public ElisaTemplate selectInfoAndApiById(Long id) {
		ElisaTemplate et = baseMapper.selectById(id);
		et.setElisaApi(apiMapper.selectById(et.getApiId()));
		return et;
	}
}
