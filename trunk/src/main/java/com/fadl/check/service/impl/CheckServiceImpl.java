package com.fadl.check.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.check.dao.CheckMapper;
import com.fadl.check.entity.Check;
import com.fadl.check.service.CheckService;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.inspection.dao.SmallBloodMapper;
import com.fadl.inspection.dao.SpecimenCollectionMapper;
import com.fadl.inspection.entity.SmallBlood;
import com.fadl.inspection.entity.SpecimenCollection;
import com.fadl.log.service.LogService;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.refuseInfo.service.RefuseInfoService;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.registries.service.ProviderRegistriesService;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;

/**
 * <p>
 * 体检记录表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2017-02-13
 */
@Service
public class CheckServiceImpl extends ServiceImpl<CheckMapper, Check> implements CheckService {
	
	@Autowired
	public CheckMapper checkMapper;
	@Autowired
	public PlasmaImageService plasmaImageService;
	@Autowired
	public SpecimenCollectionMapper specimenCollectionMapper;
	@Autowired
	public SmallBloodMapper smallBloodMapper;
	@Autowired
	public RefuseInfoService refuseInfoService;
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	public ProviderRegistriesService providerRegistriesService;
	@Autowired
	public WorkflowMapper workflowMapper;
	@Autowired
	public ICostGrantService iCostGrantService;
	@Autowired
	public ConfigService configService;
	@Autowired
	public RabatinfoService rabatinfoService;
	@Autowired
	public LogService logService;
	
	/**
	 * 查询未体检/体检人员
	 * @param map
	 * @return 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Page<DataRow> queryCheckList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(checkMapper.queryCheckList(page, map));
	}
	/**
	 * 查询体检头部信息 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public void queryCheckHeadInfo(Map<String, String> map, DataRow messageMap) throws Exception {
		DataRow result=checkMapper.queryCheckHeadInfo(map);
		if(null!=result) {
			messageMap.initSuccess(result);
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 更新体检信息并插入标本采集或血红蛋白含量
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateCheck(Check check,Integer isRoadFee,DataRow messageMap) throws Exception {
		// TODO 这里会有问题，如果发布拒绝信息后，需要先判断是否已经发布拒绝信息，如果已经发布需要先取消，然后点击体检合格，删除拒绝信息表数据，更新体检状态为合格
		//查询该浆员是否是新浆员
		Check c = checkMapper.selectById(check.getId());
		if(c==null){
			messageMap.initFial("未找到该浆员体检信息");
			return;
		}
		Config config = configService.getConfig("open_config","isCheck");
		// 南岳版本
		if (config.getIsDisable()==1) {
			//查询胸片记录
			EntityWrapper<Rabatinfo> rabat = new EntityWrapper<Rabatinfo>();
			rabat.eq("allId", c.getAllId());
			Rabatinfo rabatinfo = rabatinfoService.selectOne(rabat);
			if (null!=rabatinfo && rabatinfo.getIsCheck()==0) {
				messageMap.initFial("请先上传胸片");
				return;
			}
			if (null==check.getFinalResult()) {
				messageMap.initFial("请选择综合结果");
				return;
			}
			check.setAllId(c.getAllId());
			if(check.getFinalResult()==0){
				this.checkHege(check, messageMap, c,config);
			}else{
				this.checkBuhege(check, isRoadFee, messageMap, c,config);
			}
		}else{
			check.setAllId(c.getAllId());
			//莱士版本
			if(null==check.getFinalResult() && check.getResult()==0){
				this.checkHege(check, messageMap, c,config);
			}else if(check.getFinalResult()==0){
				int res = checkMapper.updateById(check);
				if(res>0){
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}else{
				//更新体检信息
				this.checkBuhege(check, isRoadFee, messageMap, c, config);
			}
		}
		
	}
	
	/**
	 * 体检合格
	 * @param check
	 * @param messageMap
	 * @param c
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void checkHege(Check check,DataRow messageMap,Check c,Config config) throws Exception{
		//更新体检信息
		int res = checkMapper.updateById(check);
		//体检合格
		if ((res > 0 && !(check.getFinalResult() == c.getFinalResult())) || null==c.getResult()) {
			//删除拒绝信息
			EntityWrapper<RefuseInfo> ew = new EntityWrapper<RefuseInfo>();
			ew.eq("allId", c.getAllId());
			ew.eq("isRefuse", 0);
			boolean result = refuseInfoService.delete(ew);
			if (!result) {
				messageMap.initFial("请先取消后面的流程，才能改为合格");
				throw new SQLException();
			}
			updateCostGrant(check, true);
			
			EntityWrapper<ProviderRegistries> ews=new EntityWrapper<ProviderRegistries>();
			ews.eq("allId", c.getAllId());
			ProviderRegistries providerRegistries = providerRegistriesService.selectOne(ews);
			
			//插入操作日志
			logService.insertLog(IConstants.CHECK, IConstants.DESC.replace("{0}", "已体检该浆员"),c.getProviderNo());
			
			//继续下一个流程
			nextProcedure(c, providerRegistries,messageMap);

		}else{
			if(res>0){
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
				throw new SQLException();
			}
		}
	}
	
	/**
	 * 体检不合格
	 * @throws SQLException 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void checkBuhege(Check check,Integer isRoadFee,DataRow messageMap,Check c,Config config) throws SQLException{
		//更新体检信息
		int res = checkMapper.updateById(check);
		if (res>0) {
			//是否发放路费
			if (null!=isRoadFee) {
				if(isRoadFee==1){
					updateCostGrant(check, false);
				}
			}else{
				messageMap.initFial("请选择是否发放路费");
				throw new SQLException();
			}
			if (res > 0 && !(check.getResult() == c.getResult())) {
				if(config.getIsDisable()==1){
					//体检不合格
					Workflow wf = workflowMapper.selectByAllId(c.getAllId());
					String[] workStep = wf.getWorkStep().split(",");
					//判断检验是否有做
					if (workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].indexOf("2")!=-1) {
						messageMap.initFial("请先取消后面的流程，才能改为合格");
						throw new SQLException();
					}
					//删除标本采集
					EntityWrapper<SpecimenCollection> spec= new EntityWrapper<SpecimenCollection>();
					spec.eq("allId", c.getAllId());
					specimenCollectionMapper.delete(spec);
					//删除采小血
					EntityWrapper<SmallBlood> small= new EntityWrapper<SmallBlood>();
					small.eq("allId", c.getAllId());
					smallBloodMapper.delete(small);
				}
				//插入拒绝信息
				RefuseInfo refuseInfo = new RefuseInfo();
				refuseInfo.setProviderNo(c.getProviderNo());
				refuseInfo.setAllId(c.getAllId());
				refuseInfo.setEliminateReason(check.getReason());
				refuseInfoService.insert(refuseInfo);
				messageMap.initSuccess();
				return;
			}else{
				if(res>0){
					messageMap.initSuccess();
					return;
				}
			}
		}else{
			messageMap.initFial();
			throw new SQLException();
		}
	}
	/**
	 * 更新是否发放路费
	 * @param c
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateCostGrant(Check check,boolean isCost){
		EntityWrapper<CostGrant> eww = new EntityWrapper<CostGrant>();
		eww.eq("allId", check.getAllId());
		CostGrant costGrant =iCostGrantService.selectOne(eww);
		if (isCost) {
			costGrant.setIsRoadFee(0); //改状态为是否发放路费
			costGrant.setRemarks(null);
		}else{
			costGrant.setIsRoadFee(1); //改状态为是否发放路费
			costGrant.setRemarks("体检结果不合格，原因："+check.getReason());
		}
		iCostGrantService.updateById(costGrant);
	}
	
	/**
	 * 根据浆员类型判断浆员走哪个流程
	 * @param res
	 * @param c
	 * @param check
	 * @param messageMap
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void nextProcedure(Check c,ProviderRegistries providerRegistries,DataRow messageMap) throws Exception{
		int res=0;
		//如果新浆员或者是非固定浆员（上次采浆时间和现在时间对比大于180） 插入标本采集表
		if(!(providerRegistries.getPlasmaType()==1)){
			EntityWrapper<SpecimenCollection> ews= new EntityWrapper<SpecimenCollection>();
			ews.eq("allId", c.getAllId());
			specimenCollectionMapper.delete(ews);
			// 插入标本采集
			SpecimenCollection coll = new SpecimenCollection();
			coll.setAllId(c.getAllId());
			coll.setProviderNo(c.getProviderNo());
			coll.setIsCollection(0);
			res = specimenCollectionMapper.insert(coll);
		}else{
			EntityWrapper<SmallBlood> ews= new EntityWrapper<SmallBlood>();
			ews.eq("allId", c.getAllId());
			smallBloodMapper.delete(ews);
			//老浆员 插入采小血
			SmallBlood sm = new SmallBlood();
			sm.setAllId(c.getAllId());
			sm.setProviderNo(c.getProviderNo());
			sm.setIsCollection(0);
			res = smallBloodMapper.insert(sm);
		}
		if (res > 0) {
			//更新流程表中的状态
			Workflow wf = workflowMapper.selectByAllId(c.getAllId());
			String[] workStep = wf.getWorkStep().split(",");
			char[] flow = wf.getFlowStep().toCharArray();
			
			//获取检验里面的小流程
			char[] wr = workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].toCharArray();
			//新浆员
			if (providerRegistries.getIsNew()==0 || providerRegistries.getPlasmaType()==0) {
				wr[WorkFlow.Inspections.REGISTER.ordinal()]='1';
			}else{
				wr[WorkFlow.Inspections.SMALL_BLOOD.ordinal()]='1';
			}
			flow[WorkFlow.WorkFlowEnum.CHECKS.ordinal()]='2';//体检大流程标记为 2 已完成 
			flow[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()]='1';//检验 大流程标记为 1进行中
			workStep[WorkFlow.WorkFlowEnum.CHECKS.ordinal()]="2"; //小流程标记为 已完成
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()]=new String(wr);//重新设置检验流程
			wf.setWorkStep(StringUtils.join(workStep,","));
			wf.setFlowStep(new String(flow));
			//更新留存
			res = workflowMapper.updateById(wf);
			if (res>0) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}else{
			messageMap.initFial();
			throw new SQLException("插入血红蛋白含量出错>>>>>>>>>>>>");
		}
	}
	

	/**
	 * 献浆员体检高级查询 
	 */
	@Override
	public Page<DataRow> queryCheckQueryList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(checkMapper.queryCheckQueryList(page, map));
	}

	/**
	 * 献浆员体检高级查询
	 */
	@Override
	public List<DataRow> queryCheckQueryList(HashMap<String, String> map) throws Exception {
		return checkMapper.queryCheckQueryList(map);
	}

	/**
	 * 取消体检
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void cacalCheckInfo(Long id,DataRow messageMap) throws Exception {
		Check check = this.selectById(id);
		if (null==check) {
			messageMap.initFial("该浆员不存在");
		}else if(check.getIsCheck()==0){
			messageMap.initFial("该浆员还未体检");
			return;
		}else{
			Workflow wf = workflowMapper.selectByAllId(check.getAllId());
			String[] workStep = wf.getWorkStep().split(",");
			//判断检验是否有做
			if (workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].indexOf("2")!=-1) {
				messageMap.initFial("请先取消后面的流程，才能取消体检");
				return;
			}
			// 更新工作流
			char[] flow = wf.getFlowStep().toCharArray();
			//获取检验里面的小流程
			char[] wr = workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].toCharArray();
			wr[WorkFlow.Inspections.SMALL_BLOOD.ordinal()]='0';
			wr[WorkFlow.Inspections.REGISTER.ordinal()]='0';
			flow[WorkFlow.WorkFlowEnum.CHECKS.ordinal()]='1';//体检大流程标记为 2 已完成 
			flow[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()]='0';//检验 大流程标记为 1进行中
			workStep[WorkFlow.WorkFlowEnum.CHECKS.ordinal()]="1"; //小流程标记为 已完成
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()]=new String(wr);//重新设置检验流程
			wf.setWorkStep(StringUtils.join(workStep,","));
			wf.setFlowStep(new String(flow));
			
			int result = workflowMapper.updateById(wf);
			if (result<1) {
				throw new SQLException();
			}
			//如果体检不合格
			if (check.getResult()==1) {
				EntityWrapper<RefuseInfo> ref = new EntityWrapper<RefuseInfo>();
				ref.eq("allId", check.getAllId());
				RefuseInfo r = refuseInfoService.selectOne(ref);
				if (null!=r) {
					if (r.getIsRefuse()==1) {
						messageMap.initFial("请先取消发布拒绝信息");
						return;
					}
					boolean res = refuseInfoService.deleteById(r.getId());
					if (!res) {
						messageMap.initFial("取消失败");
						return;
					}
				}
			}
			// 修改路费发放状态
			updateCostGrant(check, true);
			
			//插入操作日志
			logService.insertLog(IConstants.NOT_CHECK, IConstants.DESCRIBE.replace("{0}", check.getProviderNo())
	        		.replace("{1}", check.getProviderNo()).replace("{0}", "已取消体检"),check.getProviderNo());
			//删除标本采集
			EntityWrapper<SpecimenCollection> spec = new EntityWrapper<SpecimenCollection>();
			spec.eq("allId", check.getAllId());
			specimenCollectionMapper.delete(spec);
			//删除采小血
			EntityWrapper<SmallBlood> sm = new EntityWrapper<SmallBlood>();
			sm.eq("allId", check.getAllId());
			smallBloodMapper.delete(sm);
			// 删除胸片
			rabatinfoService.updateRabatInfoByAllId(check.getAllId());
			
			//更新体检状态
			Check c = new Check();
			c.setAllId(check.getAllId());
			c.setProviderNo(check.getProviderNo());
			c.setId(id);
			c.setIsCheck(0);
			c.setCreateDate(check.getCreateDate());
			c.setCreater(check.getCreater());
			c.setCheckType(0);
			c.setIsDel(0);
			boolean res = this.updateAllColumnById(c);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}
	}

	/**
	 * 查询上次体检记录
	 */
	@Override
	public DataRow queryPrevCheckInfo(HashMap<String, String> map) throws Exception {
		return checkMapper.queryPrevCheckInfo(map);
	}

	/**
	 * 同步数据到叫号系统
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCallDataList(HashMap<String, String> map)throws Exception{
		return checkMapper.queryCallDataList(map);
	}
	
	/**
	 * 重检
	 */
	public void restCheck(Check check,DataRow messageMap) throws Exception{
		Check c = checkMapper.selectById(check.getId());
		if (null==c) {
			messageMap.initFial("未找到浆员体检记录");
			return;
		}
		check.setProviderNo(c.getProviderNo());
		check.setCheckType(1);
		check.setIsCheck(1);
		Integer res = checkMapper.insert(check);
		if (res>0) {
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
	}
	
	/**
	 * 打印体检记录
	 */
	@Override
	public List<DataRow> queryCheckRecordList(HashMap<String, String> map) throws SQLException {
		return checkMapper.queryCheckRecordList(map);
	}
}
