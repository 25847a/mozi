package com.fadl.inspection.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.inspection.dao.SmallBloodMapper;
import com.fadl.inspection.entity.FixedPulpRegister;
import com.fadl.inspection.entity.SmallBlood;
import com.fadl.inspection.service.FixedPulpRegisterService;
import com.fadl.inspection.service.SmallBloodService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.common.WorkFlow.WorkFlowEnum;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;

/**
 * <p>
 * 检验-采小血 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class SmallBloodServiceImpl extends ServiceImpl<SmallBloodMapper, SmallBlood> implements SmallBloodService {

	@Autowired
	public SpecimenCollectionService specimenCollectionService;
	@Autowired
	private FixedPulpRegisterService fprService;
	@Autowired
	private WorkflowMapper workflowMapper;
	@Override
	public Page<DataRow> queryListByCreateDateAndIsCollection(Page<DataRow> page, SmallBlood blood) throws Exception {
		if(blood.getIsCollection() == 0) {
			blood.setEndTime(blood.getStartTime()+" 23:59:59");
			Date startTime = DateUtil.formatDate(blood.getStartTime()+" 00:00:00", DateUtil.yyyyMMddHHmmss);
			blood.setStartTime(DateUtil.getStrFourteenWithDate(startTime));
		}else {
			blood.setEndTime(blood.getStartTime()+" 23:59:59");
			blood.setStartTime(blood.getStartTime()+" 00:00:00");
		}
		return page.setRecords(baseMapper.queryListByCreateDateAndIsCollection(page, blood));
	}
	/**
	 * 根据日期,浆员卡号查询查询采小血的id,采浆次数(啊健)
	 * @param blood
	 * @return
	 */
	public void querySmallBloodHeadInfo(SmallBlood blood,DataRow messageMap){
		DataRow data = baseMapper.querySmallBloodHeadInfo(blood);
		if(null!=data){
			messageMap.initSuccess(data);
		}else{
			messageMap.initFial();
		}
		
	}
	@Override
	public DataRow querySmallBloodByEntity(Long id) {
		return baseMapper.querySmallBloodByEntity(id);
	}

	/**
	 * 更新采小血信息
	 * @throws Exception 
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateWithCollection(SmallBlood blood,DataRow messageMap) throws Exception {
		SmallBlood bl =  this.selectById(blood.getId());
		if (null!=bl) {
			bl.setIsCollection(1); //设置为已采集
			bl.setIsSendOff(blood.getIsSendOff());
			bl.setSendPerson(blood.getSendPerson());
			int res = baseMapper.updateById(bl);
			if (res>0) {
				boolean result = specimenCollectionService.nextWorkFlow(bl.getAllId(), messageMap, WorkFlowEnum.Inspections.SMALL_BLOOD.ordinal());
				if (result) 
				messageMap.initSuccess();
			}else{
				throw new  Exception();
			}
			
		}else{
			throw new  Exception();
		}
	}

	/**
	 * 取消小采
	 * @param blood
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cacalSmallBlood(SmallBlood blood,DataRow messageMap) throws Exception{
		SmallBlood bl =  this.selectById(blood.getId());
		if (null!=bl && bl.getIsCollection()==1) {
			bl.setIsCollection(0);
			bl.setIsSendOff(0);
			bl.setSendPerson(null);
			boolean res = this.updateById(bl);
			if (res) {
				if( !specimenCollectionService.retractWorkFlow(bl.getAllId(),WorkFlow.Inspections.SMALL_BLOOD,messageMap)) {
					throw new Exception();
				}else {
					messageMap.initSuccess();
				}
			}else{
				throw new  Exception();
			}
		}else{
			throw new  Exception();
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateBatchEntityByIds(List<SmallBlood> smallBloods, DataRow dataRow) throws Exception {
		// 批量更新
		for(SmallBlood collection : smallBloods) {
			updateWithCollection(collection, dataRow);
			boolean result = specimenCollectionService.nextWorkFlow(collection.getAllId(), dataRow, WorkFlowEnum.Inspections.SMALL_BLOOD.ordinal());
			if (result) {
				// 添加到检验登记
				FixedPulpRegister register = new FixedPulpRegister();
				register.setAllId(collection.getAllId());
				register.setIsAssay(0);
				register.setPlasmaId(collection.getPlasmaId());
				register.setProviderNo(collection.getProviderNo());
				if(StringUtils.isNotBlank(collection.getSendPerson()))
					register.setSendPerson(Long.parseLong(collection.getSendPerson()));
				fprService.insert(register);
				// 更新workflow的检验登记状态
				Workflow wf = workflowMapper.selectByAllId(collection.getAllId());
				String[] ws = wf.getWorkStep().split(",");
				String workSteps = ws[WorkFlowEnum.INSPECTIONS.ordinal()];
				char[] wsc = workSteps.toCharArray();
				wsc[WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal()] ='1';
				ws[WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(wsc);
				wf.setWorkStep(StringUtils.join(ws,","));
				workflowMapper.updateById(wf);
				dataRow.initSuccess();
			}else{
				throw new  Exception();
				
			}
		}
		return true;
	}
	@Override
	public List<DataRow> querySendInfosByUpdateDate(String updateDate) throws Exception {
		return baseMapper.querySendInfosByUpdateDate(updateDate);
	}
}
