package com.fadl.common.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fadl.common.entity.SystemSeq;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fadl.account.dao.AdminMapper;
import com.fadl.collectionConfig.entity.Area;
import com.fadl.collectionConfig.service.AreaService;
import com.fadl.common.Base64;
import com.fadl.common.CommonUtil;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.entity.Config;
import com.fadl.electrophoresis.service.SerumElectrophoresisService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.stock.service.PlasmaStockService;
import com.fadl.supplies.service.DetailedService;
import com.fadl.upload.service.HttpClientBuss;
import com.fadl.yell.service.PlasmYellService;

import net.sf.json.JSONObject;

/**
 * @author:wangjing
 * @Description:后台管理用户公共方法
 * @Date:2018-05-08
 */
@Service
public class CommonService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    ProviderBaseinfoMapper providerBaseinfoMapper;
    @Autowired
    DetailedService detailedService;
    @Autowired
    PlasmaStockService plasmaStockService;
    @Autowired
    ConfigService configService;
    @Autowired
    PlasmYellService plasmYellService;
    @Autowired
    ProviderBaseinfoMapper baseinfoMapper;
    @Autowired
    SocketUtil socketUtil;
    @Autowired
	AreaService areaService;
    @Autowired
    SpecimenCollectionService specimenCollectionService;
    @Autowired
    ProviderRegistriesMapper providerRegistriesMapper;
    @Autowired
    SerumElectrophoresisService serumElectrophoresisService;
    @Autowired
	SystemSeqService systemSeqService;
    
    /**
     * 查询用户列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public  List<DataRow> queryWhereAdminInfoList(HashMap<String, String> data)throws SQLException{
        return adminMapper.queryWhereAdminInfoList(data);
    }
    /**
     * 查询用户信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public  DataRow queryWhereAdminInfoObject(HashMap<String, String> data)throws SQLException{
        return adminMapper.queryWhereAdminInfoObject(data);
    }

    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public List<DataRow> queryWhereBaseInfoOrDetailList(HashMap<String, String> data)throws SQLException{
        return providerBaseinfoMapper.queryWhereBaseInfoOrDetailList(data);
    }
    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     * @throws Exception 
     */
     public DataRow queryWhereBaseInfoOrDetailObj(HashMap<String, Object> data)throws Exception{
    	DataRow result = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(data);
    	if(null==result){
    		return null;
    	}
    	data.put("code", data.get("providerNo"));
    	data.put("year",DateUtil.sdy.format(new Date()));
    	data.put("month",DateUtil.sdm.format(new Date()));
    	DataRow map = providerRegistriesMapper.queryCollectionCount(data);
    	result.put("year", map.getString("year"));
    	result.put("month", map.getString("month"));
        return result;
    }
	/**
	 * 读取身份证信息
	 */
	public void idCard(HttpServletRequest request,String type,HashMap<String, String> map,DataRow messageMap) throws Exception{
		String ip = CommonUtil.getIpAddress(request);
		socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), type, map,messageMap);
		DateUtil.loadData(messageMap);
		JSONObject jsonObject =(JSONObject)messageMap.get("data");
		//匹配人员应发放路费
		if(jsonObject!=null&&jsonObject.has("address")){
			Area area = areaService.judgeArea(jsonObject.getString("address"));
			if(area!=null){
				jsonObject.put("roadFee",String.valueOf(area.getRoadFee()));//浆员路费
			}else{
				jsonObject.put("roadFee",String.valueOf(0));//浆员路费
			}
		}
	}
	/**
     * 人脸识别
     */
    public HashMap<String,String> face(MultipartFile[] files)throws Exception{
        HashMap<String,String> map = new HashMap<String,String>();
        if(files!=null){
                map.put("srcPhoto", Base64.encode(files[0].getBytes()));
                map.put("dstPhoto", Base64.encode(files[1].getBytes()));
        }
        return map;
    }
    /**
     * @throws Exception 返回0  代表库存不足  返回1  表示扣除成功
     * 采浆模板消耗耗材(CJ)-1{成功}  1{失败,库存已经为0,无法使用}  2{提示库存超过标准底线,要及时补充,可以继续使用}  
     * @param templateId(模板ID))
     * @param messageMap
     * @throws Exception
     */
    public DataRow useDetailed(Long templateId,DataRow messageMap)throws Exception{
		return detailedService.useDetailed(templateId,messageMap);
    }
    
    
    /**
     * 入库打印条码
     * @param allId
     * @throws Exception 
     */
    public HashMap<String, String> printCollection(String allId) throws Exception{
    	HashMap<String, String> data = plasmaStockService.queryStockInfoAndPrint(allId);;
    	Config config = configService.getConfig("stock_config", "company");
    	data.put("company", config.getValue());
    	data.put("storage", "-20℃或更低");
    	data.put("validity", "三年");
    	data.put("sex", String.valueOf(data.get("sex")).equals("0") ? "男":"女");
    	
    	if (String.valueOf(data.get("blood")).equals("0")) {
    		data.put("blood", "A");
		}else if(String.valueOf(data.get("blood")).equals("1")){
			data.put("blood", "B");
		}else if(String.valueOf(data.get("blood")).equals("2")){
			data.put("blood", "O");
		}else if(String.valueOf(data.get("blood")).equals("3")){
			data.put("blood", "AB");
		}
    	
    	String pro = data.get("plasma");
    	if (null==pro) {
			return null;
		}
		String code = pro.substring(0, 3);
		String time = pro.substring(3,5);
		String no = pro.substring(5,pro.length()-2);
		String num = pro.substring(pro.length()-2,pro.length());
    	data.put("plasmaCut", code+" "+time+" "+no+" "+num);
    	data.put("weight", String.valueOf(data.get("weight"))+"(g)");
    	
    	config = configService.getConfig("stock_config", "parentCompany");
		data.put("factory", config.getValue());
		List<Config> list = configService.getConfig("plasmStock");
		for (Config con : list) {
			if (con.getLable().equals("laws")) {
				data.put("laws", con.getValue());
			}else if(con.getLable().equals("validDate")){
				if (StringUtils.isNumeric(con.getValue())) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.YEAR,Integer.valueOf(con.getValue()));
					cal.add(Calendar.DATE, -1);
					data.put("validDate", DateUtil.formatDate(cal.getTime(), DateUtil.yyyy_MM_dd_EN));
				}else{
					data.put("validDate", con.getValue());
				}
			}
		}
    	return data;
    }
    
    /**
     * 采浆验证打印条码
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> queryPlasmYellPrintInfo(HashMap<String, String> map,DataRow messageMap) throws Exception{
    	HashMap<String, String> data = plasmYellService.queryPlasmYellPrintInfo(map.get("allId"));
    	if (String.valueOf(data.get("type")).equals("0")) {
    		data.put("type", "普通");
		}else if(String.valueOf(data.get("type")).equals("1")){
			data.put("type", "普免");
		}else if(String.valueOf(data.get("type")).equals("2")){
			data.put("type", "特免");
		}
    	if(String.valueOf(data.get("visibleName")).equals("1")){
    		data.put("visibleName", "采后检");
    		data.put("title", "采后检");
    		data.put("isVisible", "1");
    	}else{
    		data.put("visibleName", "");
    		data.put("isVisible", "0");
    		data.put("title", "");
    	}
    	String bloodType = String.valueOf(data.get("bloodType"));
    	if(bloodType.equals("0")) {
    		bloodType = "A";
    	}else if(bloodType.equals("1")) {
    		bloodType = "B";
    	}else if(bloodType.equals("2")) {
    		bloodType = "O";
    	}else if(bloodType.equals("3")) {
    		bloodType = "AB";
    	}
    	data.put("blood", bloodType);
    	//打印蛋白电泳条码 (isSerum=1 要打印条码,其他则不打印)
    	//查询浆员是否需要做血清电泳
		serumElectrophoresisService.queryInfoByProviderNo(data.get("code"), messageMap);
		if (messageMap.getBoolean(IConstants.RESULT_DATA)) {
			//页面标识，表示需要打印 蛋白电泳 条码票
			data.put("isSerum", "1");
		}else{
			//页面标识，表示需要打印 蛋白电泳 条码票
			data.put("isSerum", "2");
			data.put("retention", "");
			data.put("serumCode", "");
		}
		data.put("serumName", "蛋白电泳");
    	return data;
    }
    
    /**
     * 打印卡片
     * @return
     * @throws Exception 
     */
	public HashMap<String, String> printCard(String providerNo) throws Exception{
		//需要被返回的HashMap
		HashMap<String, String> data = new HashMap<String, String>();
		//从卫计委出获取浆站信息（完善卡片信息）
    	HttpClientBuss httpClientBuss = new HttpClientBuss();
    	Map<String, String> sdf = new HashMap<>();
    	sdf.put("providerNo", providerNo);
    	String httpResult = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/verification", sdf);
    	JSONObject jsonObject = JSONObject.fromObject(httpResult);
    	//将相关信息放进data中
    	if(!jsonObject.get("error").equals("-1")){
    		return data;
    	}
		//获取要发卡的浆员基本信息
		Map<String, String> map = new HashMap<>();
    	map.put("providerNo", providerNo);
    	map.put("idNo", "");
    	List<DataRow> baseInfoList = baseinfoMapper.printPunchCard(map);
    	JSONObject json = (JSONObject) jsonObject.get("data");
    	data.put("company", json.get("plasmaName").toString());
    	data.put("code", baseInfoList.get(0).get("providerNo").toString());
    	data.put("name", baseInfoList.get(0).get("name").toString());
    	data.put("sex", baseInfoList.get(0).getInt("sex")==0 ?"男":"女");
    	int bt = (int) baseInfoList.get(0).get("bloodType");
    	String bloodType = "";
    	if(bt == 0) {
    		bloodType = "A";
    	}else if(bt == 1) {
    		bloodType = "B";
    	}else if(bt == 2) {
    		bloodType = "O";
    	}else if(bt == 3) {
    		bloodType = "AB";
    	}
    	data.put("blood", bloodType);
    	String nation = baseInfoList.get(0).get("nation").toString();
    	if(nation.indexOf("族")!=-1){
    		data.put("nation", nation.substring(0, nation.indexOf("族")));
    	}else{
    		data.put("nation", nation);
    	}
    	data.put("idCardNo", baseInfoList.get(0).get("idNo").toString());
    	data.put("born", baseInfoList.get(0).get("birthday").toString().substring(0, 10));
    	data.put("address", baseInfoList.get(0).get("addressx").toString());
    	data.put("register", baseInfoList.get(0).get("createDate").toString().substring(0, 10));
    	data.put("institution", json.get("code").toString());
    	data.put("issuer",json.get("licensor").toString());
    	data.put("elock", json.get("elecLockNumber").toString());
    	data.put("issueDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		data.put("photo", socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+baseInfoList.get(0).get("imgUrl")));

		data.put("issueOrgan", socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+"/image/upload/certificationOrgan.jpg"));
    	return  data;
    }
	
	/**h
     * 打印装箱条码
     * @param boxId
     * @return
     * @throws Exception
     */
    public HashMap<String, String> printBox(String boxId)throws Exception{
    	return plasmaStockService.queryPlasmaStockByBoxId(boxId);
    }
    
    /**
     * 浆员采小血打印小票
     * @throws Exception
     */
    public HashMap<String, String> printSpecimenCollection(Long id, Long isSpecimen)throws Exception{
    	return specimenCollectionService.printSpecimenCollection(id,isSpecimen);
    }
	/**
	 * 生成检疫期回访编号
	 */
	public String loadQuarantineNo()throws Exception{
		SystemSeq systemSeq =systemSeqService.queryNewSystemSeqInfo(10);
		Config config = configService.getConfig("stock_config", "brief");
		String value =config.getValue()+DateUtil.getSystemYearTwo(new Date())+"J"+systemSeq.getValue();
		return value;
	}
}
