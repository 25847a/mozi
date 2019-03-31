package com.fadl.registries.service.impl;

import com.fadl.registries.entity.ProviderAbout;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.check.dao.CheckMapper;
import com.fadl.check.entity.Check;
import com.fadl.collectionConfig.entity.Area;
import com.fadl.collectionConfig.service.AreaService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.ConfigService;
import com.fadl.common.service.SystemSeqService;
import com.fadl.cost.dao.ICostGrantMapper;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.image.dao.PlasmaImageMapper;
import com.fadl.image.entity.PlasmaImage;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.DetailedInfoService;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.refuseInfo.service.RefuseInfoService;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.service.ProviderAboutService;
import com.fadl.registries.service.ProviderRegistriesService;
import com.fadl.workflow.entity.Workflow;
import com.fadl.workflow.service.WorkflowService;
import net.sf.json.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 登记记录表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-05-05
 */
@Service
public class ProviderRegistriesServiceImpl extends ServiceImpl<ProviderRegistriesMapper, ProviderRegistries>
		implements ProviderRegistriesService {

	@Autowired
	ProviderRegistriesMapper providerRegistriesMapper;
	@Autowired
	PlasmaImageService plasmaImageService;
	@Autowired
	SystemSeqService systemSeqService;
	@Autowired
	RefuseInfoService refuseInfoService;
	@Autowired
	ConfigService configService;
	@Autowired
	CheckMapper checkMapper;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	ProviderBaseinfoMapper providerBaseinfoMapper;
	@Autowired
	ICostGrantMapper iCostGrantMapper;
	@Autowired
	AreaService areaService;
	@Autowired
    PlasmaImageMapper plasmaImageMapper;
	@Autowired
	ICostGrantService iCostGrantService;
	@Autowired
	ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	DetailedInfoService detailedInfoService;
	@Autowired
	ProviderAboutService providerAboutService;
	@Autowired
	RabatinfoService rabatinfoService;
	@Autowired
	LogService logService;
	/**
	 * 获取登记信息列表
	 * 
	 * @param registerDate
	 * @param page
	 * @return
	 */
	@Override
	public Page<DataRow> queryRegistrationList(String registerDate,String registerNine, Page<DataRow> page) throws Exception {
		return page.setRecords(providerRegistriesMapper.queryRegistrationList(registerDate,registerNine, page));
	}
	/**
	 *  获取登记断档号
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	public void queryBrokenNumber(DataRow messageMap)throws Exception{
		List<DataRow> list =providerRegistriesMapper.queryBrokenNumber();
		List<DataRow> result = new ArrayList<DataRow>();
		if(!list.isEmpty()) {
			String registriesNo = list.get(list.size()-1).getString("registriesNo");
			for(int i=1;i<Integer.valueOf(registriesNo);i++) {
				for(int j=0;j<list.size();j++) {
					if(list.get(j).getInt("registriesNo")==i) {
						break;
					}
					if(j==list.size()-1) {
						DataRow data = new DataRow();
						data.put("registriesNo", i);
						result.add(data);
					}
				}
			}
			messageMap.initSuccess(result);
		}
	}
	/**
	 * 单击列表查询浆员信息
	 * @param map
	 * @param messageMap
	 * @return
	 */
	@Override
	public void queryPlasmaProviderNo(Map<String,Object> map,DataRow messageMap)throws Exception{
		Map<String, Object> result = providerRegistriesMapper.queryPlasmaProviderNo(map);
		if (null != result) {
			Date data  = DateUtil.sfDay.parse((String) map.get("createDate"));
			map.put("year",DateUtil.sdy.format(new Date()));
			map.put("month",DateUtil.sdm.format(new Date()));
			DataRow da = providerRegistriesMapper.queryCollectionCount(map);
			if(null!=data) {
				result.put("year", da.getString("year"));
				result.put("month", da.getString("month"));
			}else {
				result.put("year",0);
				result.put("month",0);
			}
			messageMap.initSuccess(result);
		} else {
			messageMap.initSuccess("显示失败");
		}
	}
	/**
	 * 根据献浆卡号查询浆员登记记录(读卡器读取,可以直接传值providerNo获取浆员信息)
	 * (写socketUtil方法与设备连接,返回值是messageMap,所以传值是通过messageMap)
	 * @param messageMap
	 * @return
	 */
	@Override
	public void readPlasmaProviderNo(DataRow messageMap) throws Exception {
			JSONObject json = JSONObject.fromObject(messageMap.get("data"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", json.get("code"));
		Map<String, Object> result = providerRegistriesMapper.queryRegistriesByProviderNo(map);
		if (null != result) {
			//获取路费
			Area area = areaService.judgeArea(String.valueOf(result.get("address")));
			if(area!=null){
				result.put("roadFee",String.valueOf(area.getRoadFee()));//浆员路费
			}else{
				result.put("roadFee",String.valueOf(0));//浆员路费
			}
			/*Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DATE, 14);// 把日期往后增加一天.整数往后推,负数往前移动
			Date date = calendar.getTime();
			result.put("aboutDate", DateUtil.sfDay.format(date));//预约时间是采用当天登记时间往后推14天*/
			SystemSeq systemSeqsy=systemSeqService.querySystemSeqInfo(0);
			String row= String.valueOf(systemSeqsy.getValue());
			result.put("registriesNo", Integer.valueOf(row.substring(8, row.length())));
			map.put("year",DateUtil.sdy.format(new Date()));
			map.put("month",DateUtil.sdm.format(new Date()));
			DataRow data = providerRegistriesMapper.queryCollectionCount(map);
			if(null!=data) {
				result.put("year", data.getString("year"));
				result.put("month", data.getString("month"));
			}else {
				result.put("year",0);
				result.put("month",0);
			}
			messageMap.initSuccess(result);
		} else {
			messageMap.customValue(null, "没有献浆员信息记录", "-4");
		}
	}
	/**
	 * 验证登记流程
	 * @param provider
	 * @param messageMap
	 * @throws Exception
	 */
	public DataRow verificationRegister(ProviderRegistries provider,DataRow messageMap)throws Exception{
		// 获取浆员的献浆情况
		DataRow data = providerRegistriesMapper.queryRefuseSituation(provider.getProviderNo());
		if (data == null) {
			messageMap.initFial("浆员未注册过");
			return messageMap;
		}
		// 判断浆员是否已登记
		String time = data.getString("maxtime");
		if (!StringHelper.isEmpty(time) && time.equals(DateUtil.sfDay.format(new Date()))) {
			messageMap.initFial("今天已登记过");
			return messageMap;
		}
		//判断身份证是否到期
		if(!"".equals(data.getString("validDate"))) {
			DateUtil.sfDay.parse(data.getString("validDate")).getTime();
			if(DateUtil.sfDay.parse(data.getString("validDate")).getTime()<=DateUtil.sfDay.parse(DateUtil.sfDay.format(new Date())).getTime()) {
				messageMap.initFial("身份证已到期,请重新办理新的身份证");
				return messageMap;
			}
		}
		// 判断浆员年龄是否符合献浆年龄
		 Config config = configService.getConfig("fixed_config","age");
		 if (data.getInt("age") >= Integer.valueOf(config.getValue())) {
			Integer age = providerRegistriesMapper.queryAgeCount(provider.getProviderNo());
			if(age<2) {
				messageMap.initFial("年龄已超过"+config.getValue()+"岁并且半年内无两次献浆记录");
				return messageMap;
			}
		 }
		 //判断年龄是否超过最大现将年龄
		 Config con = configService.getConfig("fixed_config","maxAge");
			if(data.getInt("age") >=Integer.valueOf(con.getValue())) {
				messageMap.initFial("年龄已超过"+data.getString("age")+",无法献浆");
				return messageMap;
			} 
			 // 判断是否大于14天采浆
			Config c = configService.getConfig("sys_config", "time");
			if (!StringHelper.isEmpty(data.getString("collectionMax"))) {
				long day = DateUtil.daysBetween(DateUtil.formatDate(data.getString("collectionMax"), DateUtil.yyyy_MM_dd_EN),new Date());
				if (day < Long.valueOf(c.getValue())) {
					messageMap.initFial("浆员献浆时间未超过"+c.getValue()+"天,不得献浆");
					return messageMap;
				}
			}
			//判断是否验证身份,人脸识别是否通过
		 if(provider.getIsIdentity()==1) {
			 messageMap.initFial("请进行面部验证"); return messageMap; 
			 	}
			//判断拒绝原因情况
			 if(data.getInt("plasmaState")==1) {//浆员信息表==1 是属于暂时拒绝
				 RefuseInfo refuse = refuseInfoService.plasmaRefuseInfo(provider.getProviderNo()); 
					// 判断浆员是否有拒绝献浆原因(查询最新的数据,根据时间把拒绝天数算出来)
					if (null != refuse && refuse.getOpinion()!= 1) {
						if (null!=refuse.getDay() && refuse.getDay() != 0) {
							long day = DateUtil.daysBetween(refuse.getCreateDate(), DateUtil.sfDay.format(new Date()));
							if ((day - (long) refuse.getDay()) >= 0) {// 大于等于0就超过拒绝天数，可以登记了
							//	献浆员卡号：10010<上次该浆员因(ALT)转氨酶检测值:1 检测结果:不合格 被暂时拒绝到: 2018-09-21日> 现在是否登记?
								messageMap.customValue(null, "浆员编号:"+provider.getProviderNo()+"<上次该浆员因"+refuse.getEliminateReason()+"被暂时拒绝到"+DateUtil.addDate(refuse.getDay(), refuse.getCreateDate())+",现在是否登记?", "4");
								return messageMap;
							} else {
								messageMap.initFial(refuse.getEliminateReason());
								return messageMap;
							}
						}
					}
			 }else if(data.getInt("plasmaState")==2) {
				 messageMap.initFial("浆员永久拒绝,不能献浆");
					return messageMap;
			 }
			messageMap.initSuccess();
			return messageMap; 
	}
	/**
	 * 新增登记记录
	 * @param providerRegistries
	 * @param messageMap
	 * @param img
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DataRow insertRegistries(ProviderRegistries providerRegistries,Integer roadFeeType,BigDecimal roadFee, DataRow messageMap, String img)throws Exception {
		// 获取浆员的献浆情况
		DataRow data = providerRegistriesMapper.queryRefuseSituation(providerRegistries.getProviderNo());
		// 判断是否是非固定浆员 (0非固定 )
		Config conf = configService.getConfig("sys_config","count");
		 if (data.getLong("count") >= Long.valueOf(conf.getValue())) {
			providerRegistries.setPlasmaType(1);
			//这里再判断转氨酶暂拒
			RefuseInfo refu = refuseInfoService.queryTransaminaseInfo("(ALT)",providerRegistries.getProviderNo());
			if(null!=refu) {
				providerRegistries.setPlasmaType(0);
			}
		 }else{
			providerRegistries.setPlasmaType(0);
		 }
		// 判断是否是新浆员
		if (data.getInt("newcount") == 0) {
			providerRegistries.setIsNew(0);
		}
		if(null!=providerRegistries.getRegistriesNo()) {//判断是否存在断档号
			String row = DateUtil.sdf.format(new Date());
			DecimalFormat dec = new DecimalFormat("0000");
			String num = dec.format(providerRegistries.getRegistriesNo());
			providerRegistries.setRegistriesNo(providerRegistries.getRegistriesNo());//设置登记号
			providerRegistries.setAllId(Long.valueOf(row+num));//设置全登记号
		}else {
			SystemSeq registries = systemSeqService.queryNewSystemSeqInfo(0);
			String row= String.valueOf(registries.getValue());//设置全登记号
			providerRegistries.setRegistriesNo(Integer.valueOf(row.substring(8, row.length())));//设置全登记号
			providerRegistries.setAllId(registries.getValue());//设置全登记号
		}
		providerRegistriesMapper.insert(providerRegistries);
		Check check = new Check();
		check.setProviderNo(providerRegistries.getProviderNo());
		check.setAllId(providerRegistries.getAllId());
		//插入费用补助  路程
		CostGrant costGrant =new CostGrant();
        costGrant.setAllId(providerRegistries.getAllId());//设置全登记号
        costGrant.setProviderNo(providerRegistries.getProviderNo());
        costGrant.setRoadFeeType(roadFeeType);
        costGrant.setRoadFee(roadFee);
        iCostGrantMapper.insert(costGrant);
        //更新预约时间,更新浆员健康状态(能登记,说明健康状态是正常,必须设置,拒绝原因判断那边要求的)
        ProviderBaseinfo p = new ProviderBaseinfo();
        EntityWrapper<ProviderBaseinfo> ew = new EntityWrapper<ProviderBaseinfo>();
		ew.eq("providerNo", providerRegistries.getProviderNo());
	//	p.setAboutDate(aboutDate);
		p.setPlasmaState(0);
		providerBaseinfoMapper.update(p, ew);
		//插入到预约时间表
	//	ProviderAbout about = new ProviderAbout();
	//	about.setProviderNo(providerRegistries.getProviderNo());
	//	about.setAllId(providerRegistries.getAllId());
		//about.setAboutDate(aboutDate);
	//	about.setCollectionDate(data.getString("collectionMax")==""?null:data.getString("collectionMax"));
	//	providerAboutService.insert(about);//插入到预约时间表
		plasmaImageService.saveImage(img, 0, providerRegistries.getId(), messageMap);//保存图片
		workflowService.insertWorkflow(providerRegistries.getAllId());//谢鑫那边要求的
		checkMapper.insert(check);// 插入体检表
		//插入胸片记录表
		Rabatinfo rabat = new Rabatinfo();
		rabat.setProviderNo(providerRegistries.getProviderNo());
		DataRow row = rabatinfoService.queryProviderLastTime(rabat);
		if (null==row || row.getInt("time")>365 || providerRegistries.getPlasmaType()==0) {
			//插入胸片记录表
			rabat.setAllId(providerRegistries.getAllId());
			rabatinfoService.insert(rabat);
		}
		messageMap.initSuccess();
		 if (data.getLong("count") < Long.valueOf(conf.getValue())) {//提示注意:该浆员是非固定浆员
			 messageMap.initSuccess("注意:该浆员是非固定浆员");
		 }
		 if("".equals(data.getString("areaId"))) {//提示是否绑定区域
			 messageMap.initSuccess("注意:该浆员未绑定区域");
		 }
		 if(data.getLong("count") < Long.valueOf(conf.getValue()) && "".equals(data.getString("areaId"))) {
			 messageMap.initSuccess("注意:该浆员是非固定浆员 并且 该浆员未绑定区域");
		 }
		 //最后插入日志
		 logService.insertLog(IConstants.REGISTRIES, IConstants.DESC.replace("{0}", "已登记该浆员"),providerRegistries.getProviderNo());
		return messageMap;
	}
	/**
     * 删除登记记录
     * @param ids
     * @param messageMap
     * @throws Exception
     */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void delectRegistration(String ids, DataRow messageMap) throws Exception {
		ProviderRegistries p = providerRegistriesMapper.selectById(ids);
		if(null!=p) {
			//判断流程
			EntityWrapper<Workflow> ew = new EntityWrapper<Workflow>();
			ew.eq("allId", p.getAllId());
			Workflow w =workflowService.selectOne(ew);
			if(w!=null){
				String flowStep = w.getFlowStep();
				String ne =flowStep.substring(1, 2);
				if(ne.equals("1")) {
					providerRegistriesMapper.deleteById(Long.valueOf(ids));
					plasmaImageMapper.delete(new EntityWrapper<PlasmaImage>().eq("type", 0).eq("imgId", p.getId()));//删除image 浆员现场照片
					workflowService.delete(new EntityWrapper<Workflow>().eq("allId", p.getAllId())); //删除workflow  流程
					iCostGrantService.delete(new EntityWrapper<CostGrant>().eq("allId", p.getAllId())); //删除costGrant  路费表
					checkMapper.delete(new EntityWrapper<Check>().eq("allId", p.getAllId()));//刪除体检
					providerAboutService.delete(new EntityWrapper<ProviderAbout>().eq("allId", p.getAllId()));//删除预约时间表
					//最后插入日志
					logService.insertLog(IConstants.DELETEREGISTRIES, IConstants.DESC.replace("{0}", "已取消登记该浆员"),p.getProviderNo());
					messageMap.initSuccess();
				}else {
					messageMap.initFial("浆员已经体检完成,不能删除");
				}
			}
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 导出下载浆员信息数据
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> downloadRegistries(Map<String,String> data)throws Exception{
		return providerRegistriesMapper.downloadRegistries(data);
	}
	/**
	 * 浆员登记记录高级查询
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public void querySeniorRegistries(Map<String, Object> map, Page<DataRow> page) throws Exception {
		map.put("pageNum", ((page.getCurrent() - 1) * page.getSize())+"");
		map.put("pageSize", page.getSize()+"");
		page.setRecords(providerRegistriesMapper.querySeniorRegistries(map));
		page.setTotal(providerRegistriesMapper.querySeniorRegistriesCount(map));
	}
	/**
	 * 小票打印查询浆员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public HashMap<String, String> queryTicketInfo(HashMap<String, String> map) throws Exception {
		Config config =configService.getConfig("stock_config", "company");
		HashMap<String, String> data = new HashMap<String, String>();
		if(!map.containsKey("register")) {
			map.put("register", DateUtil.sfDay.format(new Date()));
		}
		data = providerRegistriesMapper.queryTicketInfo(map);
		data.put("company",config.getValue());
		data.put("register", map.get("register"));
		if(null!=data.get("aboutDate")) {
			data.put("aboutDate", DateUtil.sfDay.format(DateUtil.sfDay.parse(String.valueOf(data.get("aboutDate")))));
		}
		if(null!=data.get("collection")) {
			data.put("collection", DateUtil.sfDay.format(DateUtil.sfDay.parse(String.valueOf(data.get("collection")))));
		}
		Config conf = configService.getConfig("sys_config","count");// 判断是否是非固定浆员 (0非固定 )
		 if (Long.valueOf(String.valueOf(data.get("count"))) >= Long.valueOf(conf.getValue())) {
			 data.put("type", "固定浆员采后检!");
		 }else{
			 data.put("type", "非固定浆员采前检!");
		 }
		return data;
	}
	/**
   	 * 修改手机号码
   	 * @param detailedInfo
   	 * @param messageMap
   	 * @throws Exception
   	 */
	@Override
	public void updatePhoneNumber(DetailedInfo detailedInfo, DataRow messageMap) throws Exception {
		EntityWrapper<DetailedInfo> ew = new EntityWrapper<DetailedInfo>();
		ew.eq("baseId", detailedInfo.getBaseId());
		ew.eq("type", 0);
		boolean row =detailedInfoService.update(detailedInfo, ew);
		if(row) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
   	 * 采浆记录统计人数
   	 * @param map
   	 * @param Pageing
   	 * @throws Exception
   	 */
	@Override
	public void queryCollectionCountPeople(Page<DataRow> pageing, Map<String, String> map) throws Exception {
		map.put("pageNum", ((pageing.getCurrent() - 1) * pageing.getSize())+"");
		map.put("pageSize", pageing.getSize()+"");
		pageing.setRecords(providerRegistriesMapper.queryCollectionCountPeople(map));
		pageing.setTotal(providerRegistriesMapper.queryCollectionCountPeopleCount(map));
	}
	/**
   	 * 采浆记录统计人数详情
   	 * @param providerNo
   	 * @throws Exception
   	 */
	@Override
	public List<DataRow> queryCollectionCountDetails(String providerNo) throws Exception {
		return providerRegistriesMapper.queryCollectionCountDetails(providerNo);
	}
	/**
   	 * 今日建档人数,今日登记人数,今日采浆人数,今日采浆重量(首页数据)
   	 * @param messageMap
   	 * @throws Exception
   	 */
	@Override
	public void queryTodayPeopleInfo(DataRow messageMap) throws Exception {
		DataRow data=providerRegistriesMapper.queryTodayPeopleInfo();
		messageMap.initSuccess(data);
	}
	/**
   	 * 建档人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryPlasmAmountPeopleCount(DataRow messageMap) throws Exception {
		DataRow data=providerRegistriesMapper.queryPlasmAmountPeopleCount();
		messageMap.initSuccess(data);
	}
	/**
	 * 登记人数统计(扇形图)
	 * @return
	 */
	@Override
	public void queryRecordPeople(DataRow messageMap) throws Exception {
		DataRow data=providerRegistriesMapper.queryRecordPeople();
		messageMap.initSuccess(data);
	}
	/**
   	 * 采浆人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryCollectionPeopleCount(DataRow messageMap) throws Exception {
		DataRow data=providerRegistriesMapper.queryCollectionPeopleCount();
		messageMap.initSuccess(data);
	}
	/**
	 * 采浆量统计(扇形图)
	 * @return
	 */
	@Override
	public void queryRegisterPeople(DataRow messageMap) throws Exception {
		DataRow data=providerRegistriesMapper.queryRegisterPeople();
		messageMap.initSuccess(data);
	}
	/**
   	 * 建档人数(柱状图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryHistogramPeople(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow> list = providerRegistriesMapper.queryHistogramPeople(map);
		messageMap.initSuccess(list);
	}
	/**
   	 * 登记人数(折线图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryRegistriesPeople(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow> list = providerRegistriesMapper.queryRegistriesPeople(map);
		messageMap.initSuccess(list);
	}
	/**
   	 * 采浆人数(柱状图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryCollectionPeople(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow> list = providerRegistriesMapper.queryCollectionPeople(map);
		messageMap.initSuccess(list);
	}
	/**
   	 * 采浆重量(折线图)
   	 * @return
   	 * @throws Exception
   	 */
	@Override
	public void queryPlasmAmountPeople(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow> list = providerRegistriesMapper.queryPlasmAmountPeople(map);
		messageMap.initSuccess(list);
	}
}
