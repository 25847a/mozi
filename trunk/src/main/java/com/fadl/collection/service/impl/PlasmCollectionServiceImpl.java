package com.fadl.collection.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.collection.dao.PlasmCollectionMapper;
import com.fadl.collection.entity.PlasmCollection;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.collectionDetail.entity.PlasmCollectionDetail;
import com.fadl.collectionDetail.service.PlasmCollectionDetailService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.JsonUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.CommonService;
import com.fadl.common.service.ConfigService;
import com.fadl.cost.dao.ICostGrantMapper;
import com.fadl.cost.entity.Activity;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ActivityCostDetailService;
import com.fadl.cost.service.ActivityService;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.immuneAssay.entity.ImmuneAssay;
import com.fadl.immuneAssay.service.ImmuneAssayService;
import com.fadl.immuneAssay.service.ImmuneService;
import com.fadl.inspection.entity.FixedPulpRegister;
import com.fadl.inspection.service.FixedPulpRegisterService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.registries.entity.ProviderAbout;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.registries.service.ProviderAboutService;
import com.fadl.registries.service.ProviderRegistriesService;
import com.fadl.stock.entity.PlasmaStock;
import com.fadl.stock.service.PlasmaStockService;
import com.fadl.supplies.service.TemplateService;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;
import com.fasterxml.jackson.core.type.TypeReference;

import net.sf.json.JSONArray;

/**
 * <p>
 * 采浆记录 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@Service
public class PlasmCollectionServiceImpl extends ServiceImpl<PlasmCollectionMapper, PlasmCollection> implements PlasmCollectionService {

	@Autowired
	public PlasmCollectionMapper plasmCollectionMapper;
	@Autowired
	public ICostGrantMapper icostGrantMapper;
	@Autowired
	public PlasmaStockService plasmaStockService;
	@Autowired
	public CommonService commonService;
	@Autowired
	public WorkflowMapper workflowMapper;
	@Autowired
	public PlasmaImageService plasmaImageService;
	@Autowired
	public ActivityService activityService;
	@Autowired
	public ProviderRegistriesService providerRegistriesService;
	@Autowired
	public FixedPulpRegisterService fixedPulpRegisterService;
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public ImmuneAssayService immuneAssayService;
	@Autowired
	public ConfigService configService;
	@Autowired
	public ImmuneService immuneService;
	@Autowired
	public ActivityCostDetailService activityCostDetailService;
	@Autowired
	public ProviderAboutService providerAboutService;
	@Autowired
	public PlasmCollectionDetailService plasmCollectionDetailService;
	@Autowired
	public TemplateService templateService;
	@Autowired
	public LogService logService;
	/**
	 * 查询采浆列表
	 */
	@Override
	public Page<DataRow> queryPlasmCollectionList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(plasmCollectionMapper.queryPlasmCollectionList(page, map));
	}

	/**
	 * 更新采浆信息
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmCollection(PlasmCollection plasmCollection,String detail,BigDecimal money,String image,Integer bloodGrade,DataRow messageMap) throws Exception {
		//设置状态为 已采集
		plasmCollection.setIsCollection(1);
		// 根据 id 查询采集详情
		PlasmCollection pl = this.selectById(plasmCollection.getId());
		//判断采集状态为 已采集，更新采集信息
		if(pl.getIsCollection()==1){
			//查询费用是否发放
			CostGrant costGrant = new CostGrant();
			costGrant.setAllId(pl.getAllId());
			costGrant = icostGrantMapper.selectOne(costGrant);
			if(null!=costGrant ){
				if(costGrant.getIsGrant()==1){
					messageMap.initFial("该浆员已经发放误工补贴，不能修改信息");
					return;
				}
			}
			int res= plasmCollectionMapper.updateById(plasmCollection);
			if (res>0) {
				//保存采程信息
				boolean result = saveCollectionDetail(detail, pl);
				if (result) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}else{
				messageMap.initFial();
			}
		}else{
			int res= plasmCollectionMapper.updateById(plasmCollection);//把值插入到采浆
			if (res > 0) {
				//保存采程信息
				boolean result = saveCollectionDetail(detail, pl);
				if (!result) {
					messageMap.initFial();
					return;
				}
				//扣除采浆耗材
				DataRow row = commonService.useDetailed(plasmCollection.getSuppliesId(),messageMap);
				if (row.getString(IConstants.RESULT_CODE).equals("-1")) {
					messageMap = new DataRow();
					next(pl,messageMap,image,money);
					
					//更新血管等级
					EntityWrapper<ProviderBaseinfo> ews = new EntityWrapper<ProviderBaseinfo>();
					ews.eq("providerNo", pl.getProviderNo());
					ProviderBaseinfo providerBaseinfo = providerBaseinfoService.selectOne(ews);
					if (null!=bloodGrade && bloodGrade>0) {
						providerBaseinfo.setBloodGrade(bloodGrade);
					}
					// 更新最后采浆时间
					String time = DateUtil.formatDate(new Date(), DateUtil.yyyyMMddHHmmss);
					providerBaseinfo.setCollectionDate(time);
					Config c = configService.getConfig("sys_config", "time");
					//预约时间
					String aboutTime = DateUtil.formatDate(DateUtil.addDate(new Date(), Calendar.DATE, Integer.valueOf(c.getValue())), DateUtil.yyyyMMddHHmmss);
					//设置预约时间
					providerBaseinfo.setAboutDate(aboutTime);
					providerBaseinfoService.updateById(providerBaseinfo);
					//判断免疫类型是否是空的，如果是空的，不需要插入免疫化验表，如果没有开启 采完浆直接入库，就要插入免疫化验表
					if(!StringHelper.isEmpty(providerBaseinfo.getImmuneId())){
						//插入免疫化验表
						ImmuneAssay immuneAssay = new ImmuneAssay();
						immuneAssay.setAllId(pl.getAllId());
						immuneAssay.setProviderNo(pl.getProviderNo());
						immuneAssay.setOldImmuneId(Long.valueOf(providerBaseinfo.getImmuneId()));
						immuneAssayService.insert(immuneAssay);
					}
					//插入操作日志
					logService.insertLog(IConstants.CHECK, IConstants.DESC.replace("{0}", "已采浆该浆员"),pl.getProviderNo());
					
					//插入预约表
					ProviderAbout providerAbout = new ProviderAbout();
					providerAbout.setAllId(pl.getAllId());
					providerAbout.setProviderNo(pl.getProviderNo());
					providerAbout.setAboutDate(aboutTime);
					providerAbout.setCollectionDate(time);
					providerAboutService.insert(providerAbout);
					messageMap.initSuccess(row.getString(IConstants.RESULT_MESSAGE));//操作成功带提示
				}else{
					messageMap.initFial(row.getString(IConstants.RESULT_MESSAGE));
					throw new SQLException();
				}
			}else{
				messageMap.initFial();
			}
		}
	}
	
	/**
	 * 保存采程信息
	 */
	public boolean saveCollectionDetail(String detail,PlasmCollection pl)throws Exception{
		if (!StringHelper.isEmpty(detail)) {
			List<PlasmCollectionDetail> list = JsonUtil.getMapper().readValue(detail, new TypeReference<List<PlasmCollectionDetail>>() {});
			EntityWrapper<PlasmCollectionDetail> det = new EntityWrapper<PlasmCollectionDetail>();
			det.eq("collectionId", pl.getId());
			if(plasmCollectionDetailService.delete(det)){
				for (PlasmCollectionDetail plasmCollectionDetail : list) {
					plasmCollectionDetail.setCollectionId(pl.getId());
				}
				return plasmCollectionDetailService.insertBatch(list);
			}
		}
		return false;
	}
	
	/**
	 * "验证采浆后是否需要进行免疫流程
	 * @param providerNo
	 * @return
	 */
	public void collectionAfterImmune(String providerNo,DataRow messageMap)throws Exception{
		Config config =configService.getConfig("open_config", "after");
		if(config.getIsDisable()==0) {
			Config con = configService.queryConfig("immune_type", "1");
			if(con.getIsDisable()==1){//如果禁用，执行莱士的
				immuneService.immuneRegisterAlone(providerNo, messageMap);
			}else{
				immuneService.immuneRegister(providerNo,messageMap);
			}
			
		}else {
			messageMap.initSuccess();
		}
	}
	/**
	 * 下个流程(插入费用表、老浆员插入化验表、插入血浆入库表),更新流程表
	 * @param pl
	 * @param messageMap
	 * @param money
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void next(PlasmCollection pl,DataRow messageMap,String image,BigDecimal money) throws Exception{
		//更新费用表
		CostGrant costGrant = new CostGrant();
		/*costGrant.setMoney(money);
		costGrant.setCollDate(DateUtil.getSystemDate(new Date()));
		costGrant.setIsCollection(1);//费用表状态改为已采浆*/
		costGrant.setAllId(pl.getAllId());
		costGrant = icostGrantMapper.selectOne(costGrant);
		costGrant.setMoney(money);
		costGrant.setCollDate(DateUtil.getSystemTime(new Date()));
		costGrant.setIsRoadFee(1);//费用表状态改为可以发放路费
		int res = icostGrantMapper.updateById(costGrant);
		if (res >0) {
			// 插入血浆库存表
			PlasmaStock plasmaStock = new PlasmaStock();
			plasmaStock.setProviderNo(pl.getProviderNo());
			plasmaStock.setAllId(pl.getAllId());
			boolean result = plasmaStockService.insert(plasmaStock);
			if (!result) {
				messageMap.initFial();
				throw new SQLException();
			}
			
			EntityWrapper ew = new EntityWrapper();
			ew.eq("allId", pl.getAllId());
			ProviderRegistries providerRegistries = providerRegistriesService.selectOne(ew);
			
			//更新流程表
			Workflow wf = workflowMapper.selectByAllId(pl.getAllId());
			String[] workStep = wf.getWorkStep().split(",");
			char[] work = workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()].toCharArray();
			work[WorkFlow.Collection.COLLECTION.ordinal()]='2'; //采浆流程标记为已完成
			workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = new String(work);
			
			//采浆大流程标记为已完成
			char[] flow = wf.getFlowStep().toCharArray();
			flow[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()]='2';
			
			wf.setWorkStep(StringUtils.join(workStep,","));
			wf.setFlowStep(new String(flow));
			res = workflowMapper.updateById(wf);
			if (res>0) {
				updateCost(costGrant,providerRegistries);
				//插入现场图片
				plasmaImageService.saveImage(image, 7, pl.getId(), messageMap);
				return;
			}
		}
		messageMap.initFial();
		throw new SQLException();
	}

	/**
	 * 更新采浆费用
	 * @param pl
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateCost(CostGrant costGrant,ProviderRegistries providerRegistries) throws Exception{
		List<DataRow> list = this.queryActivityCostList(costGrant, providerRegistries);
		if(null!=list && list.size() > 0){
			//插入采浆费用中间表
			icostGrantMapper.insertActivityCostDetail(list);
		}
	}
	
	/**
	 * 查询采浆活动费用
	 * @param isInsert false 如果没采浆需要计算出采浆费用
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DataRow> queryActivityCostList(CostGrant costGrant,ProviderRegistries providerRegistries)
			throws Exception {
		//查询费用发放明细
		List<DataRow> li = activityCostDetailService.queryActivityCostList(costGrant.getId());
		if (null!=li && li.size()>0) {
			return li;
		}
		EntityWrapper ew = null;
		List<Activity> list = null;
		//新浆员活动
		if (providerRegistries.getIsNew()==0) {
			ew = new EntityWrapper();
			ew.eq("isDelete", 0);
			ew.eq("isNew", 0);
			//查询活动列表
			list = activityService.selectList(ew);
		}else{
			//老浆员流程 
			ew = new EntityWrapper();
			ew.eq("isDelete", 0);
			ew.eq("isNew", 1);
			//查询活动列表
			list = activityService.selectList(ew);
		}
		if(null==list || list.size()==0){
			return null;
		}
		List<DataRow> l = new ArrayList<DataRow>();
		boolean isInsert=false;
		Config config = configService.getConfig("stock_config", "code");
		//查询活动列表
		for (Activity activity : list) {
			
			//计算活动开始时间和结束时间 ,如果活动超过时间，则继续下一个活动
			long day = DateUtil.daysBetween(DateUtil.sfDay.parse(activity.getEndDate()), new Date());
			long day1 = DateUtil.daysBetween(new Date(),DateUtil.sfDay.parse(activity.getStartDate()));
			if (day1 >0 || day > 0) {
				break;
			}
			
			DataRow row  = new DataRow();
			row.put("providerNo", costGrant.getProviderNo());
			row.put("startDate", activity.getStartDate()+" 00:00:00");
			row.put("endDate", activity.getEndDate()+" 23:59:59");
			//查询采浆次数
			int count = plasmCollectionMapper.queryPlasmaCollectionCount(row);
			row = new DataRow();
			count=count+1;
			//如果没有结束次数，就判断开始次数和采浆次数是否相等，如果相等，就插入费用详情
			// 如果有结束次数，就判断采浆次数是否在这个区间，在这个区间就插入费用详情
			if(null!=activity.getEndCount() && (count >=activity.getCount() && count <= activity.getEndCount())){
				isInsert = true;
			}else{
				if(count == activity.getCount()){
					isInsert = true;
				}
			}
			if (isInsert) {
				row.put("costId", costGrant.getId());
				row.put("activityId", activity.getId());
				//判断金额是固定还是 递增
				if(activity.getMoneyType()==0){
					row.put("money", activity.getMoney());
				}else{
					row.put("money", activity.getMoney().multiply(new BigDecimal(count)));					
				}
				row.put("name", activity.getName());
				row.put("num", count);
				row.put("plasmaId", config.getValue());
				row.put("moneyType", activity.getMoneyType());
				l.add(row);
			}
		}
		return l;
	}
	
	/**
	 * 查询没采浆的采浆费用
	 */
	@Override
	public void queryNextCollectionCost(Long allId,DataRow messageMap)throws Exception{
		if(null==allId){
			messageMap.initSuccess();
			return;
		}
		CostGrant costGrant = new CostGrant();
		costGrant.setAllId(allId);
		costGrant = icostGrantMapper.selectOne(costGrant);
		EntityWrapper<ProviderRegistries> ew = new EntityWrapper<ProviderRegistries>();
		ew.eq("allId", allId);
		ProviderRegistries providerRegistries = providerRegistriesService.selectOne(ew);
		List<DataRow> list = queryActivityCostList(costGrant, providerRegistries);
		messageMap.put("roadFee", costGrant.getRoadFee());
		messageMap.initSuccess(list);
	}
	
	/**
	 * 根据 id 查询详情
	 */
	@Override
	public void queryPlasmCollectionById(Long id, DataRow messageMap) throws Exception {
		DataRow row = plasmCollectionMapper.queryPlasmCollectionById(id);
		messageMap.initSuccess(row);
	}

	/**
	 * 采浆记录高级查询
	 */
	@Override
	public Page<DataRow> querySeniorQueryPlasmCollectionList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		map.put("pageNum", ((page.getCurrent() - 1) * page.getSize())+"");
		map.put("pageSize", page.getSize()+"");
		if(map.containsKey("token")) {
			page.setRecords(plasmCollectionMapper.queryPlasmCollectionCountList(map));//a
			page.setTotal(plasmCollectionMapper.queryPlasmCollectionListCount());//a
		}else {
			page.setRecords(plasmCollectionMapper.querySeniorQueryPlasmCollectionList(map));
			page.setTotal(plasmCollectionMapper.querySeniorQueryPlasmCollectionListCount(map));
		}
		return page;
	}

	/**
	 * 采浆记录高级查询
	 */
	@Override
	public List<DataRow> querySeniorQueryPlasmCollectionList(HashMap<String, String> map) throws Exception {
		return plasmCollectionMapper.querySeniorQueryPlasmCollectionList(map);
	}

	/**
	 * 查询采浆护士列表
	 */
	@Override
	public List<DataRow> queryCollectionAdminList() throws Exception {
		return plasmCollectionMapper.queryCollectionAdminList();
	}

	/**
	 * 更新血浆状态
	 */
	@Override
	public int updatePlasmStatus(HashMap<String, String> map) throws Exception {
		return plasmCollectionMapper.updatePlasmStatus(map);
	}
	/**
	 * 给免疫流程判断是否需要特免登记 
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryImmuneCollectionCount(String providerNo) throws Exception {
		return plasmCollectionMapper.queryImmuneCollectionCount(providerNo);
	}

	/**
	 * 根据实体集合更新送样记录
	 * @param pss
	 * @param sendPerson
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public boolean sendOff(List<Long> l,String sendPerson, DataRow dataRow) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", l);
		List<DataRow> list = plasmCollectionMapper.querySendSimpleList(map);
		// 遍历出要送样的入库信息
		for(DataRow ps : list ) {
			PlasmCollection pc = new PlasmCollection();
			pc.setAllId(ps.getLong("allId"));
			// 根据allId 查找献浆员的采浆记录
			pc = plasmCollectionMapper.selectOne(pc);
			if(pc!=null) {
				// 如果是已经送样则不需要再次送样
				if(ps.getInt("plasmaType")==0){
					dataRow.initFial("献浆卡号:"+pc.getProviderNo()+" 是新浆员，不需要送样.");
					throw new Exception("献浆卡号:"+pc.getProviderNo()+" 是新浆员，不需要送样.");
				}else if(pc.getIsSendOff() ==1 ) {
					dataRow.initFial("献浆卡号:"+pc.getProviderNo()+" 已经送样.");
					throw new Exception("献浆卡号:"+pc.getProviderNo()+" 已经送样.");
				} 
				// 更新献浆员的采浆记录为已送样
				pc.setIsSendOff(1);
				pc.setSendPerson(sendPerson);
				baseMapper.updateById(pc);
				if(pc.getIsSendOff()==1) { // 如果是已送样则要走下一步
					nextFlow(pc.getAllId(), pc.getProviderNo(), dataRow);
				}
			}
		}
		dataRow.initSuccess();
		return true;
	}
	
	/**
	 * 内部方法,进行流程下一步.仅用来更新化验模块
	 * @param allId
	 * @param providerNo
	 * @param dataRow
	 * @throws Exception
	 */
	private void nextFlow(Long allId, String providerNo, DataRow dataRow) throws Exception{
		EntityWrapper<ProviderRegistries> ew = new EntityWrapper<ProviderRegistries>();
		ew.eq("allId", allId);
		ProviderRegistries providerRegistries = providerRegistriesService.selectOne(ew);
		if(providerRegistries == null) {
			dataRow.initFial("没有该浆员的登记信息");
			throw new Exception();
		}
		//如果是固定浆员，需要采后检
		if(providerRegistries.getPlasmaType()==1){
			// 插入固定浆员检验登记
			FixedPulpRegister fix = new FixedPulpRegister();
			fix.setAllId(allId);
			fix.setProviderNo(providerNo);
			boolean result = fixedPulpRegisterService.insert(fix);
			if (!result) {
				dataRow.initFial();
				throw new SQLException();
			}
		}
		//更新流程表
		Workflow wf = workflowMapper.selectByAllId(allId);
		String[] workStep = wf.getWorkStep().split(",");
		char[] work = workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()].toCharArray();
		work[WorkFlow.Collection.COLLECTION.ordinal()]='2'; //采浆流程标记为已完成
		workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = new String(work);
		if (providerRegistries.getPlasmaType()==1) {
			//更新 固定浆员检验登记为进行中
			work = workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].toCharArray();
			work[WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal()]='1';
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = new String(work);
		}
		//采浆大流程标记为已完成
		char[] flow = wf.getFlowStep().toCharArray();
		flow[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()]='2';
		if(providerRegistries.getPlasmaType()==1){
			//更新大流程为进行中
			flow[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()]='1';
		}
		wf.setWorkStep(StringUtils.join(workStep,","));
		wf.setFlowStep(new String(flow));
		workflowMapper.updateById(wf);
	}
	
	/**
	 * 献血浆者采浆记录表 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPrintPlasmaCollectionRecordList(HashMap<String, String> map)throws Exception{
		List<DataRow> list= plasmCollectionMapper.queryPrintPlasmaCollectionRecordList(map);
		for(int i=0;i<list.size();i++) {
			StringBuffer code = new StringBuffer();
			List<DataRow> result = templateService.queryDetailedCollection(list.get(i).getInt("templateId"));
			for(int j=0;j<result.size();j++) {
				if(code.length()==0) {
					code.append(result.get(j).getString("number"));
				}else {
					code.append("|"+result.get(j).getString("number"));
				}
			}
			list.get(i).put("code", code.toString());
		}
		 return list;
	}
	/**
	 * 获取耗材信息
	 * @return
	 */
	@Override
	public void queryDetailedCollectionInfo(String data, DataRow messageMap) throws Exception {
		JSONArray json = JSONArray.fromObject(data);
		List<Integer> row = new ArrayList<Integer>();
		List<DataRow> result =new ArrayList<DataRow>();
		for(int i=0;i<json.size();i++) {
			row.add((Integer) json.get(i));
		}
		for(int j=0;j<row.size();j++) {
			List<DataRow> list = templateService.queryDetailedCollection(row.get(j));
			for(int v=0;v<list.size();v++) {
				DataRow dara = new DataRow();
				dara.put("number", list.get(v).get("number"));
				dara.put("name", list.get(v).get("name"));
				dara.put("batchNumber", list.get(v).get("batchNumber"));
				dara.put("supply", list.get(v).get("supply"));
				dara.put("expiryTime", list.get(v).get("expiryTime"));
				result.add(dara);
			}
		}
		messageMap.initSuccess(result);
	}
	
	/**
	 * 修改血浆重量
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePlasmaWeight(PlasmCollection plasm,DataRow messageMap)throws Exception{
		EntityWrapper<PlasmCollection> wrapper = new EntityWrapper<PlasmCollection>();
		wrapper.eq("allId", plasm.getAllId());
		boolean res = this.update(plasm, wrapper);
		if (res) {
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
	}
}
