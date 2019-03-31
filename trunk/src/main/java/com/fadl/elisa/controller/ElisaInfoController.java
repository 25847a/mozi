package com.fadl.elisa.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.CommonUtil;
import com.fadl.common.PolynomialRegression;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.elisa.entity.ElisaApi;
import com.fadl.elisa.entity.ElisaCommonSetting;
import com.fadl.elisa.entity.ElisaGrayAreaSettings;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaMeasurementMethod;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.ElisaTemplateValues;
import com.fadl.elisa.entity.ElisaValues;
import com.fadl.elisa.entity.TestResult;
import com.fadl.elisa.service.ElisaInfoService;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.elisa.service.ElisaValuesService;
import com.fadl.elisa.service.TestResultService;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.TestConfigInfoService;

import net.sf.json.JSONObject;

/**
 * <p>
 * 酶标板检测信息 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Controller
@RequestMapping("/elisaInfo")
public class ElisaInfoController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ElisaInfoController.class);
	
	@Autowired
	private SocketUtil socketUtil;
	@Autowired
	private ElisaTemplateService etlService;
	@Autowired
	private ElisaInfoService eiService;
	@Autowired
	private ElisaValuesService elService;
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	private TestResultService  trService;


	/**
	 * 跳转酶标板检测页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/initPage")
	public String initPage() {
		return "/business/inspection/elisa";
	}
	
	/**
	 * 跳转酶标板检测页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage2")
	@RequiresPermissions("einfo:view")
	public String initPage2() {
		String ip = CommonUtil.getIpAddress(request);
    	socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "eliasao",null,messageMap);
		return "/business/elisa/elisa_jiemei";
	}
	
	/**
	 * 跳转酶标板检测页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/gotoEVinfo")
	public String gotoEVinfo(Model model, String id) {
    	model.addAttribute("id", id);
		return "/business/elisa/elisa_xiangq";
	}
	/**
	 * 跳转酶标板检测页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/gotoCM")
	public String gotoCM() {
		return "/business/elisa/elisa_jilv";
	}
	/**
	 * 跳转质控品添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/gotoQC")
	public String gotoQC() {
		return "/business/elisa/elisahao_add";
	}
	/**
	 * 读自动化酶标设备  保存读板记录并且返回至页面
	 * @return
	 */
	@RequestMapping("/doAutoElisa")
	@InvokeLog(name = "doAutoElisa", description = "读自动化酶标设备 保存读板记录并且返回至页面")
	@ResponseBody
	@RequiresPermissions("einfo:auto")
	public DataRow doAutoElisa() {
		try {
			 Session session= SecurityUtils.getSubject().getSession();
	            Admin admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
	            messageMap.put("creater", admin.getId());
			if(trService.doAutoElisa(messageMap)) {
				messageMap.initSuccess("操作成功");
			}
		} catch (Exception e) {
			messageMap.remove("size");
			messageMap.initFial(e.getMessage());
			e.printStackTrace();
		}
		return messageMap;
	}
	/**
	 * 读酶标仪 保存读板记录并且返回至页面
	 * @return
	 */
	@RequestMapping("/doElisa")
	@InvokeLog(name = "doElisa", description = "读酶标仪 保存读板记录并且返回至页面")
	@ResponseBody
	@RequiresPermissions("einfo:save")
	public DataRow doElisa(@RequestParam Map<String,String> map) {
		
		try {
			String templateId = map.get("templateId");
			if(!StringUtils.isEmpty(templateId)) {
				// 找到使用的模板信息
				ElisaTemplate template = etlService.selectById(templateId);
				
				// 先得到批号
				String reagentId  = map.get("reagentId");
				Wrapper<TestConfigInfo> ew = new EntityWrapper<TestConfigInfo>();
				ew.eq("reagentBatch", reagentId);
				ew.eq("del_flag", 0);
				ew.le("startDate", DateUtil.formatDate(new Date(),DateUtil.yyyy_MM_dd_EN)+" 00:00:00");
				ew.ge("endTime", DateUtil.formatDate(new Date(),DateUtil.yyyy_MM_dd_EN)+" 23:59:59");
				TestConfigInfo tci = tciService.selectOne(ew);
				if(tci==null) {
					 messageMap.initFial("没有找到试剂的信息");
					 return messageMap;
				}
				String ip = CommonUtil.getIpAddress(request);
				
            	socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "eliasa",getParam(template.getElisaApi(), tci.getTestingMethodid()),messageMap);
            	// 如果成功则要保存到数据库
            	if(messageMap.getString("code").equals("-1")) {
            		// 修改数据格式为数组格式
            		String data = messageMap.getString("data");
            		// 这里是模拟数据使用的, 真实环境一定要注释
//            		if("1".equals(tci.getProjectName())) {
//            			data = getTestElisaValue(false);
//            		}else {
//            			data = getTestElisaValue(true);
//            		}
            		if(StringUtils.isEmpty(data) || data.length() < 200) {
            			 messageMap.initFial("没有接收到酶标读数,请联系管理员.");
            			 return messageMap;
            		}
            		if(data.indexOf("OVER") != -1) {
            			messageMap.initFial("超出范围，请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("LOW") != -1) {
            			messageMap.initFial("灯低误差，请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("SCAN") != -1) {
            			messageMap.initFial("采集在所选时间之前停止，请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("...") != -1) {
            			messageMap.initFial("无可用数据（部分板测量），请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("???") != -1) {
            			messageMap.initFial("尚未计算的数据（例如极化），请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("###") != -1) {
            			messageMap.initFial("数据未计算（计算错误），请确认.");
           			 return messageMap;
            		}
            		if(data.indexOf("STOP") != -1) {
            			messageMap.initFial("采集在所选时间之前停止，请确认.");
           			 return messageMap;
            		}
            		data = data.substring(data.indexOf("<"),data.length()-2);
            		String[] trs = data.split("\\.");
            		
            		int index=0;
            		// 96个孔位
            		String[] values = new String[96]; 
            		if(template.getArrangement() ==0) { // 横向
                		for(int i= 1, j=trs.length;i<j;i++) {
                			String [] tds  = trs[i].split(",");
                			for(int k =1, l = tds.length;k<l;k++) {
                				
                				values[index]  =  ""+Math.abs(Integer.valueOf(tds[k]));
                				index++;
                			}
                		}
            		}else {//纵向
            			if(trs!=null) {
            				int j =trs[0].split(",").length;
            				for(int i=1;i<j;i++) {
            					for(int k =1, l = trs.length;k<l;k++) {
            						String [] tds  = trs[k].split(",");
            						values[index]  =  ""+Math.abs(Integer.valueOf(tds[i]));
            						index++;
            					}
            				}
            				
            			}else {
            				 messageMap.initFial("模板中没有配置微板孔信息!");
            			}
            		}
            		eiService.doInsertEi(map, tci, template, values, messageMap);
            	}
			}
			
        } catch (Exception e) {
            logger.error("ElisaInfoController>>>>>>>>>doElisa>>>>>",e);
            messageMap.initFial(e.getMessage());
        }
		return messageMap;
	}

	/** 
	 *  整板删除未发布的酶标读板信息
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delElisaInfo")
	@InvokeLog(name = "delElisaInfo", description = "整板删除未发布的酶标读板信息")
	@RequiresPermissions("einfo:del")
	public DataRow delElisaInfo(ElisaInfo elisaInfo) {
		elisaInfo =  eiService.selectById(elisaInfo.getId());
		Wrapper<ElisaValues> ew  = new EntityWrapper<ElisaValues>();
		ew.eq("reportStatus", 1);
		ew.eq("eiId", elisaInfo.getId());
		List<ElisaValues> evs =  elService.selectList(ew);
		if(evs.size()>0) {
			messageMap.initFial("该板已经有孔发布，不能删除。");
			return messageMap;
		}
		try {
			eiService.delById(elisaInfo.getId());
		} catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>delElisaInfo>>>>>",e);
		}
		//reportStatus
		return messageMap;
	}
	/** 
	 * 保存发布的酶标仪检测数据.支持批量保存
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveElisaInfo")
	@InvokeLog(name = "saveElisaInfo", description = "保存发布的酶标仪检测数据.支持批量保存")
	@RequiresPermissions("einfo:save")
	public DataRow saveElisaInfo(ElisaInfo elisaInfo) {
		try{
			// 根据eiID是否为空判断是否insert或者update
			if(null==elisaInfo.getId()) {// 插入
				
			}else { //更新
				
			}
			if(eiService.insertOrUpdate(elisaInfo)) {
//				messageMap.initSuccess();
			}
		}catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>saveElisaInfo>>>>>",e);
		}
		
		
		return messageMap;
	}
	

	
	/**
	 * 根据elisaInfo中的模板和时间查找序号记录项
	 * @param elisaInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectSNByParams")
	@InvokeLog(name = "selectSNByParams", description = "根据elisaInfo中的模板和时间查找序号记录项")
	@RequiresPermissions("einfo:view")
	public DataRow selectSNByParams(ElisaInfo elisaInfo) {
		Wrapper<ElisaInfo> ew = new EntityWrapper<ElisaInfo>();
		String createDate = elisaInfo.getCreateDate();
		if(StringUtils.isEmpty(createDate)) {
			createDate = DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN);
		}else {
			Date date;
			try {
				date = DateUtil.formatDate(createDate, DateUtil.yyyy_MM_dd_EN);
				createDate = DateUtil.formatDate(date, DateUtil.yyyy_MM_dd_EN);
			} catch (Exception e) {
				logger.error("ElisaInfoController>>>>>>>>>selectSNByParams>>>>>",e);
	            messageMap.initFial();
			}
		}
		ew.like("createDate", createDate);
		if(null != elisaInfo.getEtId()) {
			ew.eq("etId", elisaInfo.getEtId());
		}else {
			//ew.groupBy("etId");  // 先删除分组，用来页面显示批量插入记录
		}
		List<ElisaInfo> list =eiService.selectList(ew);
		messageMap.initSuccess(list);
		return messageMap;
	}
	
	/**
	 * 根据id查找Elisa Info的信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectElisaInfoById")
	@InvokeLog(name = "selectElisaInfoById", description = "根据id查找Elisa Info的信息")
	@RequiresPermissions("einfo:view")
	public DataRow selectElisaInfoById(Long id) {
		ElisaInfo elisaInfo = eiService.selectById(id);
		
		messageMap.initSuccess(elisaInfo);
		return messageMap;
	}
	
	/**
	 * 根据选择的酶标板孔位发布信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/releaseElisaValue")
	@InvokeLog(name = "releaseElisaValue", description = "根据选择的酶标板孔位发布信息")
	@RequiresPermissions("einfo:release")
	public DataRow releaseElisaValue(long infoId, String evids, String type) {
		ElisaInfo ei = eiService.selectById(infoId);
		if("2".equals(ei.getQcResult() )) { // 如果质控结果为2即失控,则不能发布结果
			messageMap.initFial("本次实验的质控结果为失控,不能发布结果.");
			return messageMap;
		}
		try {
			if(elService.releaseElisaValue(ei,evids,type)) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>releaseElisaValue>>>>>",e);
			messageMap.initFial(e.getMessage());
		}
	
		return messageMap;
	}
	
	
	/**
	 * 根据id查找Elisa Info的信息
	 * @param elisaInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectElisaInfoByCondition")
	@InvokeLog(name = "selectElisaInfoByCondition", description = "根据id查找Elisa Info的信息")
	@RequiresPermissions("einfo:view")
	public DataRow selectElisaInfoByCondition(ElisaInfo elisaInfo, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page,limit);
			dr  = eiService.selectInfoByCondition(dr, elisaInfo);
			messageMap.initPage(dr);
		}catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>selectElisaInfoByCondition>>>>>",e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	} 
	
	/**
	 * 根据eiid查找ElisaValues的信息
	 * @param elisaInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectElisaValueByEIID")
	@InvokeLog(name = "selectElisaValueByEIID", description = "根据eiid查找ElisaValues的信息")
	@RequiresPermissions("einfo:view")
	public DataRow selectElisaValueByEIID(Long id) {
		try {
			Wrapper<ElisaValues> ew = new EntityWrapper<ElisaValues>();
			messageMap.initSuccess(elService.selectList(ew));
		}catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>selectElisaValueByEIID>>>>>",e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	} 
	
	/**
	 * 根据eiid查找ElisaValues的信息
	 * @param elisaInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllSequenceNumber")
	@InvokeLog(name = "getAllSequenceNumber", description = "根据eiid查找ElisaValues的信息")
	@RequiresPermissions("einfo:view")
	public DataRow getAllSequenceNumber() {
		try {
			
			messageMap.initSuccess(eiService.getAllSequenceNumber());
		}catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>getAllSequenceNumber>>>>>",e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	} 
	
	/**
	 * 根据eiid查找ElisaValues的信息
	 * @param elisaInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	@InvokeLog(name = "delById", description = "根据eiid查找ElisaValues的信息")
	@RequiresPermissions("einfo:delete")
	public DataRow delById(Long id) {
		try {
			
			messageMap.initSuccess(eiService.delById(id));
		}catch (Exception e) {
			logger.error("ElisaInfoController>>>>>>>>>delById>>>>>",e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	} 
	

	private String getTestElisaValue(boolean isElisa) {
		if(isElisa) {
			JSONObject outData = new JSONObject();
			outData.put("measure", "<>,1,2,3,4,5,6,7,8,9,10,11,12.A,90,60,50,8700,10380,1730,80,90,130,110,130,140.B,90,50,60,90,80,80,70,80,120,110,130,130.C,50,40,50,16610,20280,2550,110,120,170,150,160,160.D,100,60,50,80,80,80,80,80,130,90,110,180.E,100,50,70,8630,10850,610,60,50,80,110,110,90.F,40,40,40,50,50,30,40,40,70,60,100,110.G,0,0,0,0,0,0,0,0,0,0,0,0.H,0,0,0,0,0,0,0,0,0,0,0,0");
			return outData.toString();
		}else {
			JSONObject outData = new JSONObject();
			outData.put("measure", "<>,1,2,3,4,5,6,7,8,9,10,11,12.A,3600,4710,5140,6160,8090,5550,3780,3970,3830,3730,3850,3850.B,3650,3950,3400,3330,3690,3880,3600,3180,3710,3200,3570,3560.C,3440,4780,3700,3590,4090,3840,3580,3800,4440,3570,4050,3610.D,3710,4120,3430,3520,4170,3810,3490,4790,4220,4050,3690,3480.E,3620,3750,3800,4200,3530,3820,4100,3790,3920,3330,3670,3710.F,3640,3540,4140,4020,3930,3990,3860,4150,4360,3770,4040,4030.G,3860,3980,4020,3640,4160,5050,3980,4270,4120,3990,4320,4130.H,4110,5180,5900,4840,4850,4800,4320,4820,4560,4440,4770,4180");
			return outData.toString();
		}
		
	}
	
	/**
	 * 封装酶标仪读取参数
	 * @param api
	 * @return
	 */
	private HashMap<String, String> getParam(ElisaApi api, Long testingMethodid) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("mainFilter", ""+api.getMainFilter());
		param.put("viceFilter", ""+api.getSubFilter());
		param.put("programMode", "empty");
		param.put("plateMode", ""+api.getSubFilter());
		if(api.getIsOscillation()==1) { // 是否开启震动
			//oscillationSpeed
			param.put("shakeIntensity", ""+api.getOscillationSpeed());
			//oscillationTime
			param.put("shakeDuration", ""+api.getOscillationTime());
		}else {
			//oscillationSpeed
			param.put("shakeIntensity", "Normal");
			//oscillationTime
			param.put("shakeDuration", "0");
		}
		param.put("delayTime", ""+api.getExtensionOfTime());
		param.put("readNumber", ""+api.getReadCount());
		param.put("readInterval", ""+api.getIntervals());
		param.put("selfTest", "empty");
		param.put("nCycle", "0"); // 取第一次读
		param.put("iWhich", "0");
//		if(testingMethodid ==2) {
//			param.put("iWhich", "0"); // 取双波检测值
//		}else {
//			param.put("iWhich", "1"); // 取双波检测值
//		}
		param.put("debug", "false");
		return param;
	}

	

}

