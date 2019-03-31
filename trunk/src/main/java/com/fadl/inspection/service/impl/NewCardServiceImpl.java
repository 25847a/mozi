package com.fadl.inspection.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.inspection.dao.NewCardMapper;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.NewCardService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.common.WorkFlow.Inspections;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;

/**
 * <p>
 * 检验-新-老卡化验 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class NewCardServiceImpl extends ServiceImpl<NewCardMapper, NewCard> implements NewCardService {
	@Autowired
	private ProviderBaseinfoMapper baseinfoMapper;
	@Autowired
	private SpecimenCollectionService scService;
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	private RefuseInfoMapper rfMapper;
	@Autowired
	private WorkflowMapper workflowMapper;
	/**
	 * 根据创建时间查询检验记录
	 * @param page
	 * @param newCard
	 * @return
	 */
	@Override
	public Page<DataRow> queryListsByCreateDate(Page<DataRow> page, NewCard newCard) {
		
		return page.setRecords(baseMapper.queryListsByCreateDate(page, newCard));
	}

	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param newCard
	 * @return
	 */
	@Override
	public Page<DataRow> queryListsByCondition(Page<DataRow> page, NewCard newCard) {
		return page.setRecords(baseMapper.queryListsByNewCard(page, newCard));
	}

	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public Page<DataRow> queryListsByCondition(Page<DataRow> page, HashMap<String, Object> map) throws Exception{
		return page.setRecords(baseMapper.queryListsByCondition(page, map));
	}
	/**
	 * 查询未化验浆员信息, 带分页 需要指定查询日期和是否新老浆员
	 * @param page
	 * @param newCard
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Page<DataRow> queryListByCreateDateAndIsAssay(Page<DataRow> page, NewCard newCard) throws Exception {
		newCard.setEndTime(newCard.getStartTime()+" 23:59:59");
		Date startTime = DateUtil.formatDate(newCard.getStartTime()+" 00:00:00", DateUtil.yyyyMMddHHmmss);
		newCard.setStartTime(DateUtil.getStrFourteenWithDate(startTime));
		return page.setRecords(baseMapper.queryListByCreateDateAndIsAssay(page, newCard));
	}

	@Override
	public Page<DataRow> queryListByCreateDateAndIsAssayWith1(Page<DataRow> page, NewCard newCard) {
		return page.setRecords(baseMapper.queryListByCreateDateAndIsAssayWith1(page, newCard));
	}

	@Override
	public DataRow selectInfoById(NewCard newCard) {
		return baseMapper.selectInfoById(newCard);
	}
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateInfoById(NewCard card, DataRow dataRow)throws Exception {
		// 状态为复检的不能发布结果
		if(card.getResult()==2) {
			throw new Exception("该浆员需要复检,请先不要发布结果.");
		}
		NewCard card1 = baseMapper.selectById(card.getId());
		card1.setSerumProtein(card.getSerumProtein());
		card1.setBloodRedProteinValue(card.getBloodRedProteinValue());
		if(card1.getType()==1 && card.getIsAssay()==0) {
			throw new Exception("非新浆员不能撤回");
		}
		if(StringUtils.isEmpty(card1.getProviderNo())) {
			throw new Exception("没有浆员卡号");
		}
		// 设置为已化验
		card.setIsAssay(1);
		// 如果浆员是临时卡和者血型不为空则能修改
		if(!StringUtils.isEmpty(card.getBlood())&&card1.getProviderNo().indexOf("f")!=-1) {
			card1.setBlood(card.getBlood());
			ProviderBaseinfo baseinfo = new ProviderBaseinfo();
			baseinfo.setProviderNo(card1.getProviderNo());
			baseinfo = baseinfoMapper.selectOne(baseinfo);
			baseinfo.setBloodType(Integer.valueOf(card1.getBlood()));
			// 设置项目为血型
			TestConfigInfo tci =  tciService.getByLableAndType("血型", "ABO正定型");
			card1.setBloodTciId(tci.getId());
			int count = baseinfoMapper.updateById(baseinfo);
			if(count!=1) {
				throw new Exception("更新信息出错");
			}
		}
		card1.setIsAssay(card.getIsAssay());
		card1.setRemarks(card.getRemarks());
		card1.setResult(card.getResult());
		card1.setReportAdminid(card.getReportAdminid());
		card1.setSuppliesId(card.getSuppliesId());
		int count = baseMapper.updateById(card1);
		if(count!=1) {
			throw new Exception("更新信息出错");
		}
		if(card.getResult() == 1) {
			RefuseInfo info = new RefuseInfo();
			 // 无法找到对应的拒绝信息,只能新增
			info.setAllId(card1.getAllId());
			// 此处的1为化验发布
			info.setType(1);
			info.setEliminateReason(card.getRemarks());
			info.setProviderNo(card1.getProviderNo());
			rfMapper.insert(info); // 保存拒绝信息
		}
		// 走下一个流程 
		if(card.getIsAssay()==1) {
			if(card.getResult() == 0) //检查合格才走流程
			scService.nextWorkFlow(card1.getAllId(), dataRow, card1.getType()==0?6:7);
			else { // 发布不合格则更新检测流程为完结
				Workflow wf = workflowMapper.selectByAllId(card1.getAllId());
				String fs = wf.getFlowStep();
				char[] fsCharArray = fs.toCharArray();
				fsCharArray[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] ='2';
				wf.setFlowStep(String.valueOf(fsCharArray)); // 
				String ws = wf.getWorkStep();
				String [] wsStringArray = ws.split(",");
				// 直接写死，检验流程完成，不走采浆
				wsStringArray[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] ="222222222";
				wf.setWorkStep(org.apache.commons.lang3.StringUtils.join(wsStringArray,","));
				if(workflowMapper.updateById(wf)!=1) {
					dataRow.initFial("更新了多条工作流程记录");
					throw new Exception("更新了多条工作流程记录");
				}
			}
		}else { // 撤回流程
			scService.retractWorkFlow(card1.getAllId(), Inspections.NEW_CARD_ASSAY, dataRow);
		}
		return count==1;
	}
	/**
	 *  查询当天的已化验的不合格记录信息 传入参数为null 则默认使用系统时间
	 * @param newCard
	 * @return
	 */
	@Override
	public List<DataRow> queryListsByUnqualified(String sendDate) {
		NewCard nc = new NewCard();
		nc.setIsAssay(1);
		nc.setResult(1);
		if(StringUtils.isEmpty(sendDate)) {
			nc.setStartTime(DateUtil.getSystemDate(null));
		}else {
			nc.setStartTime(sendDate);
		}
		return baseMapper.queryLists(nc);
	}
	/**
	 *  查询当天的已化验的合格记录信息 传入参数为null 则默认使用系统时间
	 * @param newCard
	 * @return
	 */
	@Override
	public List<DataRow> queryListsByQualified(String sendDate) {
		NewCard nc = new NewCard();
		nc.setIsAssay(1);
		nc.setResult(0);
		if(StringUtils.isEmpty(sendDate)) {
			nc.setStartTime(DateUtil.getSystemDate(null));
		}else {
			nc.setStartTime(sendDate);
		}
		return baseMapper.queryLists(nc);
	}
	
	/**
	 * 查询已经发布的化验结果记录,根据时间段查询
	 * @param card
	 * @return
	 */
	@Override
	public List<HashMap<String, String>> queryListByUpdateDateAndIsAssayWith1(NewCard card){
		return baseMapper.queryListByUpdateDateAndIsAssayWith1(card);
	}

}
