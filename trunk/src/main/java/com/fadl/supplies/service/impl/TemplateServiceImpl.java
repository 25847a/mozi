package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.DetailedMapper;
import com.fadl.supplies.dao.TemplateMapper;
import com.fadl.supplies.entity.Detailed;
import com.fadl.supplies.entity.Template;
import com.fadl.supplies.service.DetailedService;
import com.fadl.supplies.service.TemplateService;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 耗材模板配置 服务实现类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
@Service
@SuppressWarnings("unchecked")
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {
	
	@Autowired
	TemplateMapper templateMapper;
	@Autowired
	DetailedMapper detailedMapper;
	@Autowired
	DetailedService detailedService;
	@Autowired
	LogService logService;
	/**
	 * 查询耗材模板配置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public Page<DataRow> queryTemplateList(Page<DataRow> pageing, Map<String, String> map) throws Exception {
		return pageing.setRecords(templateMapper.queryTemplateList(pageing, map));
	}
	/**
	 * 添加耗材模板的耗材列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public void queryTemplateSuppliesInfo(Map<String, Object> map, Page<DataRow> pageing) throws Exception {
		pageing.setRecords(templateMapper.queryTemplateSuppliesInfo(pageing, map));
	}
	/**
	 * 新增耗材模板配置
	 * @param Template
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertTemplate(Map<String,String> map,DataRow messageMap) throws Exception {
		String list =map.get("list").replace(",[]", "").replace("[],", "");
		List<Map<String,String>> data = (List<Map<String,String>>) JSONUtils.parse(list);
		List<Detailed> detailed = new ArrayList<Detailed>();
		Template template = new Template();
		template.setName(map.get("templateName"));//模板名称
		template.setIsDisable(1);//状态
		template.setApply(Integer.valueOf(map.get("apply")));//用于模块
		template.setDepotId(Integer.valueOf( map.get("depotId")));//仓库ID
		template.setStartDate((String) map.get("startTime"));//开始时间
		template.setEndDate((String) map.get("endTime"));//结束时间
		templateMapper.insert(template);
		for(int i=0;i<data.size();i++) {
			Detailed d = new Detailed();
			d.setTemplateId(template.getId());
			d.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			d.setBatchNumber(String.valueOf(data.get(i).get("batchNumber")));
			d.setNum(Long.valueOf(String.valueOf(data.get(i).get("num"))));
			detailed.add(d);
		}
		detailedService.insertDetailed(detailed);
		//最后插入日志
		logService.insertLog(IConstants.insertTemplate, IConstants.DESC.replace("{0}", "新增耗材模板配置"),"");
		messageMap.initSuccess();
	}
	/**
	 * 修改耗材模板配置
	 * @param id
	 * @return
	 */
	@Override
	public void updateTemplate(Template template, DataRow messageMap) throws Exception {
		int row = templateMapper.updateById(template);
		if(row>0) {
			//最后插入日志
			logService.insertLog(IConstants.updateTemplate, IConstants.DESC.replace("{0}", "修改耗材模板配置"),"");
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除耗材模板配置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteTemplate(Template template,DataRow messageMap) throws Exception {
		template.setIsDelete(1);
		int row = templateMapper.updateById(template);
		if(row>0) {
			//最后插入日志
			logService.insertLog(IConstants.deleteTemplate, IConstants.DESC.replace("{0}", "删除耗材模板配置"),"");
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 采浆打印需要查询耗材编号
	 * @param suppliesId
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryDetailedCollection(Integer suppliesId)throws Exception{
		return templateMapper.queryDetailedCollection(suppliesId);
	}
}
