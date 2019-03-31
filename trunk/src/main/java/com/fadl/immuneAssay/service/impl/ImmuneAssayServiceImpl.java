package com.fadl.immuneAssay.service.impl;
import com.fadl.immuneAssay.entity.ImmuneAssay;
import com.fadl.immuneAssay.entity.ImmuneAssaySetting;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.ConfigService;
import com.fadl.common.service.SystemSeqService;
import com.fadl.immuneAssay.dao.ImmuneAssayMapper;
import com.fadl.immuneAssay.service.ImmuneAssayService;
import com.fadl.immuneAssay.service.ImmuneAssaySettingService;
import com.fadl.log.service.LogService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 免疫化验 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@Service
public class ImmuneAssayServiceImpl extends ServiceImpl<ImmuneAssayMapper, ImmuneAssay> implements ImmuneAssayService {
	
	@Autowired
	private ImmuneAssayMapper immuneAssayMapper;
	@Autowired
	private SystemSeqService systemSeqService;
	@Autowired
	private ImmuneAssaySettingService immuneAssaySettingService;
	@Autowired
	private ConfigService configService;
	@Autowired
	LogService logService;
	/**
	 * 修改免疫流程配置状态
	 * @param type
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void updateImmuneConfigType(String type, String id,DataRow messageMap) throws Exception {
		Config config = new Config();
		config.setId(Long.valueOf(id));
		if("true".equals(type)) {
			config.setIsDisable(0);
		}else if("false".equals(type)) {
			config.setIsDisable(1);
		}
		boolean row =configService.updateById(config);
		if(row) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 免疫化验   特免化验（未化验）
	 * @param workTime
	 * @return
	 * @throws Exception
	 */
	@Override
	public void specialNotAssay(String createDate,Page<DataRow> paging) throws Exception {
		paging.setRecords(immuneAssayMapper.specialNotAssay(createDate,paging));
	}
	/**
	 * 免疫化验   特免化验（已化验）
	 * @param workTime
	 * @return
	 * @throws Exception
	 */
	@Override
	public void specialHaveAssay(String createDate,Page<DataRow> paging) throws Exception {
		paging.setRecords(immuneAssayMapper.specialHaveAssay(createDate,paging));
	}
	/**
	 * 免疫化验   特免化验  --> 发布化验
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void publishAssay(ImmuneAssay entity,DataRow messageMap)throws Exception{
		EntityWrapper<ImmuneAssaySetting> ew = new EntityWrapper<ImmuneAssaySetting>();
		ew.eq("immuneId", entity.getOldImmuneId());
		ew.eq("isDelete", 0);
		List<ImmuneAssaySetting> list =immuneAssaySettingService.selectList(ew);
		if(list.isEmpty()) {
			messageMap.initFial("请配置效价值参数");return;
		}
		for(int i=0;i<list.size();i++) {
			if(entity.getEffectiveValue().floatValue()>=list.get(i).getEffectiveMin().floatValue() && entity.getEffectiveValue().floatValue()<=list.get(i).getEffectiveMax().floatValue()) {
				entity.setImmuneId(list.get(i).getPackingImmuneId());//发布结果，更新最新的免疫类型
			}
		}
		entity.setIsAssay(1);
		int row =immuneAssayMapper.updateById(entity);
		if(row>0) {
			//最后插入日志
			logService.insertLog(IConstants.publishAssay, IConstants.DESC.replace("{0}", "已免疫化验发布结果,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 免疫化验   特免化验  --> 取消化验
	 * @param entity
	 * @return
	 */
	public void cancelAssay(ImmuneAssay entity,DataRow messageMap)throws Exception{
		entity.setImmuneId(null);
		entity.setIsAssay(0);
		int row = immuneAssayMapper.updateAllColumnById(entity);
		if(row>0) {
			//最后插入日志
			logService.insertLog(IConstants.cancelAssay, IConstants.DESC.replace("{0}", "已取消免疫化验,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 扫描器自动更新浆员信息
	 * @param allId
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateAssayNumber (String allId,DataRow messageMap) throws Exception{
		ImmuneAssay assay=immuneAssayMapper.queryScanningNumber(allId);
		if(null!=assay) {
			if(null==assay.getNumber()) {
				SystemSeq registries = systemSeqService.queryNewSystemSeqInfo(8);
				String num= String.valueOf(registries.getValue());
				String  number = num.substring(8,num.length());
				int row = immuneAssayMapper.updateAssayNumber(number, allId);
				if(row>0) {
					DataRow list =immuneAssayMapper.queryImmuneTodayCollection(allId);
					messageMap.initSuccess(list);
				}else {
					messageMap.initFial("该浆员今天无献浆记录,无法录入");
				}
			}else {
				messageMap.initFial("该浆员已扫描过,请勿重复扫描");
			}
		}else {
			messageMap.initFial("查询不到该浆员的全登记号");
		}
		
	}
	/**
	 * 根据时间和卡号查询头部信息(啊健)
	 * @param providerNo
	 * @return
	 */
	@Override
	public void queryHeadInfo(Map<String, String> map, DataRow messageMap) throws Exception {
		DataRow result = immuneAssayMapper.queryHeadInfo(map);
		if(null!=result) {
			messageMap.initSuccess(result);
		}else {
			messageMap.initFial("没有该浆员信息");
		}
		
	}
	/**
	 * 免疫化验   特免检测查询
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> querySpecialImmune(Map<String, String> map,Page<DataRow> page) throws Exception {
		return page.setRecords(immuneAssayMapper.querySpecialImmune(map,page));
	}
	/**
	 * 免疫化验   特免检测查询结果导出EXCEL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataRow> exportSpecialImmune(Map<String, String> map) throws Exception {
		return immuneAssayMapper.querySpecialImmune(map);
	}
}
