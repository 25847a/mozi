package com.fadl.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.CommonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.CommonService;
import com.fadl.common.service.ConfigService;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.registries.service.ProviderRegistriesService;

/**
 * @author:wangjing
 * @Description:公共跳转
 * @Date:2018-04-20
 */
@Controller
@RequestMapping("/common")
public class CommonController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    CommonService commonService;
    @Autowired
    SocketUtil socketUtil;
    @Autowired
    ProviderRegistriesService providerRegistriesService;
    @Autowired
    ICostGrantService iCostGrantService;
    @Autowired
    ConfigService configService;
    
    /**
     * 加载左导航
     * @return
     */
    @RequestMapping("/leftNavigation")
    public String leftNavigation() {
        return "/business/busblood/busines";
    }
    /**
     * 跳转无权限页面
     */
    @RequestMapping("/jurhundred")
    public String jurhundred() {
        return "/errorpage/jurhundred";
    }
    
    /**
     * 跳转人脸识别页面
     * @return
     */
    @RequestMapping("/identityValidate")
    public String identityValidate(){

        return "/common/identity_validate";
    }

    /**
     * 加载摄像头
     * @return
     */
    @RequestMapping("/busCamera")
    public String busCamera() {
        return "/common/bus_camera";
    }

    /**
     * 查询用户列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    @RequestMapping(value="/queryWhereAdminInfoList",method= RequestMethod.POST)
    @InvokeLog(name = "queryWhereAdminInfoList", description = "查询用户列表信息")
    @ResponseBody
    public DataRow queryWhereAdminInfoList(@RequestParam HashMap<String, String> data){
        try {
           List<DataRow> list = commonService.queryWhereAdminInfoList(data);
           messageMap.put("loginId", CommonUtil.getSessionAdmin().getId());
           messageMap.initSuccess(list);
        }catch (Exception e){
            logger.error("CommonController>>>>>>>>>queryWhereAdminInfoList>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 查询用户信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    @RequestMapping(value="/queryWhereAdminInfoObject",method= RequestMethod.POST)
    @InvokeLog(name = "queryWhereAdminInfoObject", description = "查询用户信息")
    @ResponseBody
    public  DataRow queryWhereAdminInfoObject(@RequestParam HashMap<String, String> data){
        try {
            DataRow obj = commonService.queryWhereAdminInfoObject(data);
            messageMap.initSuccess(obj);
        }catch (Exception e){
            logger.error("CommonController>>>>>>>>>queryWhereAdminInfoObject>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    @RequestMapping(value="/queryWhereBaseInfoOrDetailList",method= RequestMethod.POST)
    @InvokeLog(name = "queryWhereBaseInfoOrDetailList", description = "查询浆员列表信息")
    @ResponseBody
    public DataRow queryWhereBaseInfoOrDetailList(@RequestParam HashMap<String, String> data){
        try {
            List<DataRow> list = commonService.queryWhereBaseInfoOrDetailList(data);
            messageMap.initSuccess(list);
        }catch (Exception e){
            logger.error("CommonController>>>>>>>>>queryWhereBaseInfoOrDetailList>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    @RequestMapping(value="/queryWhereBaseInfoOrDetailObj",method= RequestMethod.POST)
    @InvokeLog(name = "queryWhereBaseInfoOrDetailObj", description = "查询浆员列表信息")
    @ResponseBody
    public DataRow queryWhereBaseInfoOrDetailObj(@RequestParam HashMap<String, Object> data){
        try {
            DataRow obj = commonService.queryWhereBaseInfoOrDetailObj(data);
            if(null!=obj){
            	messageMap.initSuccess(obj);
            }else{
            	messageMap.initFial("没有找到浆员信息");
            }
        }catch (Exception e){
            logger.error("CommonController>>>>>>>>>queryWhereBaseInfoOrDetailObj>>>>>",e);
        }
        return messageMap;
    }
    
    /**
	 * 人脸识别
	 * 入参：
		{"type":"face","data":{"srcPhoto":"...","dstPhoto":"..."}}
		出参：
		{"recvCode":"1/0","recvMsg":"成功/失败描述","type":"face","data":{"result":"true/false"}}
	 * @return
	 */
	@RequestMapping(value="/face",method = RequestMethod.POST)
	@InvokeLog(name = "face", description = "人脸识别")
	@ResponseBody
	public DataRow face(@RequestParam("files") MultipartFile[] files){
		try {
            String ip = CommonUtil.getIpAddress(request);
            HashMap<String,String> map =commonService.face(files);
            socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "face", map,messageMap);
            DateUtil.loadData(messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>face>>>>>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 身份证读取
	 * @return
	 */
	@RequestMapping(value="/idCard",method = RequestMethod.POST)
	@InvokeLog(name = "idCard", description = "身份证读取")
	@ResponseBody
	public DataRow idCard(){
		try {
			commonService.idCard(request,"idCard",null,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>idCard>>>>>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 身份证读取正面
	 * @return
	 */
	@RequestMapping(value="/LoadIdCardJust",method = RequestMethod.POST)
	@InvokeLog(name = "LoadIdCardJust", description = "身份证读取正面")
	@ResponseBody
	public DataRow LoadIdCardJust(){
		try {
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("picType","1");
			commonService.idCard(request,"idCardm",map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>LoadIdCardJust>>>>>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 身份证读取反面
	 * @return
	 */
	@RequestMapping(value="/LoadIdCardBack",method = RequestMethod.POST)
	@InvokeLog(name = "LoadIdCardBack", description = "身份证读取反面")
	@ResponseBody
	public DataRow LoadIdCardBack(){
		try {
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("picType","2");
			commonService.idCard(request,"idCardm",map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>LoadIdCardBack>>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 小票打印
	 *  HashMap<String, String> map = new HashMap<String, String>();
		map.put("company", "深圳市伏安动力科技有限公司"); //公司
		map.put("code", "101077");//浆员编号
		map.put("name", "柳国康");//名字
		map.put("blood", "O型");//血型
		map.put("sample", "23");//登记号
		map.put("register", "2017-03-01");//登记日期
		map.put("aboutDate", "2017-03-01");//预约日期(下次来的时间)由于客户要求采浆后生成预约时间,登记小票的预约时间没用了，暂时给空值
		map.put("collection","2018-01-01");
		map.put("type","1");//0(注意：非固定浆员采前检!)  1(注意：固定浆员采后检!)
		前端传providerNo  到后台就可以了
	 * @return
	 */
	@RequestMapping(value="/ticket",method = RequestMethod.POST)
	@InvokeLog(name = "ticket", description = "小票打印")
	@ResponseBody
	public DataRow ticket(@RequestParam HashMap<String, String> map){
		//传登记号，登记日期
		try {
			map = providerRegistriesService.queryTicketInfo(map);
			String ip = CommonUtil.getIpAddress(request);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "ticket", map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>ticket>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 采浆验证打印条码 
	 * 入参：
		{"type":"barcodeb","data":{"sample":"201804180209","type":"狂免",
		"typeSample":"K201804180209","code":"408836","name":"黄光荣","register":"209",
		"validate":"2018-04-18"}}
		出参：
		{"recvCode":"1/0","recvMsg":"成功/失败描述","type":"barcodeb","data":{}}
	 * @return
	 */
	@RequestMapping(value="/yell",method = RequestMethod.POST)
	@InvokeLog(name = "yell", description = "采浆验证打印条码")
	@ResponseBody
	public DataRow yell(@RequestParam HashMap<String, String> map){
		try {
			HashMap<String, String> row = commonService.queryPlasmYellPrintInfo(map,messageMap);
			String ip = CommonUtil.getIpAddress(request);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodeb", row,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>yell>>>>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 读取浆员卡号
	 * @param map
	 * @return
	 */
	@RequestMapping("/readProviderNoInfo")
	@ResponseBody
	public DataRow readProviderNoInfo(@RequestParam Map<String,String> map) {
		try {
			String ip = CommonUtil.getIpAddress(request);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "cardr",null,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>readProviderNoInfo>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆入库打印血浆条码
	 * 入参：
	{"type":"barcodea","data":{"plasma":"20218T10012623","name":"柳国康","sex":"男",
	"blood":"O","code":"407757","plasmaType":"乙免","pulpingDate":"2018-04-23",
	"weight":"600(g)","company":"单采血浆站","storage":"-20℃或更低",
	"validity":"三年","plasmaCut":"202 18 T100126 23"}}
	出参：
	{"recvCode":"1/0","recvMsg":"成功/失败描述","type":"barcodea","data":{}}
	 * @return
	 */
	@RequestMapping(value="/stock",method = RequestMethod.POST)
	@InvokeLog(name = "stock", description = "血浆入库打印血浆条码")
	@ResponseBody
	public DataRow stock(@RequestParam String allId){
		try {
			HashMap<String, String> row =commonService.printCollection(allId);
			if (null!=row) {
				String ip = CommonUtil.getIpAddress(request);
				socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodea", row,messageMap);
			}else{
				messageMap.initFial("请先血浆入库");
			}
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>stock>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 打印装箱条码(血浆箱子装满后打印条码)
	 * 
	 * @return
	 */
	@RequestMapping(value="/printBox",method = RequestMethod.POST)
	@InvokeLog(name = "printBox", description = "打印装箱条码(血浆箱子装满后打印条码)")
	@ResponseBody
	public DataRow printBox(@RequestParam String boxId){
		try {
			HashMap<String, String> row = commonService.printBox(boxId);
			if (null!=row) {
				String ip = CommonUtil.getIpAddress(request);
				socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcoded", row,messageMap);
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printBox>>>>>>>>>",e);
		}
		return messageMap;
	}

	/**
	 * 检疫期回访登记
	 *
	 * @return
	 */
	@RequestMapping(value="/printQuarantine",method = RequestMethod.POST)
	@InvokeLog(name = "printQuarantine", description = "检疫期回访登记")
	@ResponseBody
	public DataRow printQuarantine(@RequestParam HashMap<String,String> data){
		try {
			if (null!=data) {
				String ip = CommonUtil.getIpAddress(request);
				socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodee", data,messageMap);
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printQuarantine>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 读取icNum卡号读取
	 * @return
	 */
	@RequestMapping(value="/readICNumber",method = RequestMethod.POST)
	@InvokeLog(name = "readICNumber", description = "读取icNum卡号读取")
	@ResponseBody
	public DataRow readICNumber(@RequestParam HashMap<String, String> map){
		try {
			String ip = CommonUtil.getIpAddress(request);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "cardr", null,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>readICNumber>>>>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	
	/**
	 * 写入浆员编号
	 * @return
	 */
	@RequestMapping(value="/writeProviderNo",method = RequestMethod.POST)
	@InvokeLog(name = "writeProviderNo", description = "写入浆员编号")
	@ResponseBody
	public DataRow writeProviderNo(@RequestParam String code){
		try {
			String ip = CommonUtil.getIpAddress(request);
			HashMap<String, String> map = new HashMap<>();
			map.put("code", code);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "cardw", map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>writeProviderNo>>>>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 打印浆员卡
	 * @return
	 */
	@RequestMapping(value="/printCard",method = RequestMethod.POST)
	@InvokeLog(name = "printCard", description = "打印浆员卡")
	@ResponseBody
	public DataRow printCard(@RequestParam String providerNo){
		try {
			String ip = CommonUtil.getIpAddress(request);
			HashMap<String, String> map = commonService.printCard(providerNo);
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "card", map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printCard>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 浆员采小血打印小票
	 * @return
	 */
	@RequestMapping(value="/printSpecimenCollection",method = RequestMethod.POST)
	@InvokeLog(name = "printSpecimenCollection", description = "浆员采小血打印小票")
	@ResponseBody
	public DataRow printSpecimenCollection(@RequestParam Long id, @RequestParam Long isSpecimen){
		try {
			HashMap<String, String> row = commonService.printSpecimenCollection(id,isSpecimen);
			if(null!=row){
				String ip = CommonUtil.getIpAddress(request);
				socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodec", row,messageMap);
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printSpecimenCollection>>>>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 浆员检验登记打印小票
	 * @return
	 */
	@RequestMapping(value="/printPulpRegisterCollection",method = RequestMethod.POST)
	@InvokeLog(name = "printPulpRegisterCollection", description = "浆员检验登记打印小票")
	@ResponseBody
	public DataRow printPulpRegisterCollection(@RequestParam Long id, @RequestParam Long isSpecimen){
		try {
			HashMap<String, String> row = commonService.printSpecimenCollection(id,isSpecimen);
			if(null!=row){
				String ip = CommonUtil.getIpAddress(request);
				socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodef", row,messageMap);
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printPulpRegisterCollection>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 打印费用详情小票
	 * @param id
	 * @return
	 */
	@RequestMapping("/printGrantCost")
	@ResponseBody
	public DataRow printGrantCost(Long id) {
		try {
			HashMap<String, String> map = new LinkedHashMap<>();
			String ip = CommonUtil.getIpAddress(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<DataRow> result = iCostGrantService.printGrantCost(id);
			if(result.size() < 1) {
				messageMap.initFial();
			}else {
				Config config = configService.getConfig("stock_config", "company");
				//只有一个活动时
				map.put("公司", config.getValue());
				map.put("浆员姓名", result.get(0).get("name").toString());
				map.put("浆员卡号", result.get(0).get("providerNo").toString());
				map.put("采浆费用", result.get(0).get("colMoney").toString());
				map.put("采浆时间", result.get(0).get("collDate")==null?sdf.format(new Date()):result.get(0).get("collDate").toString());
				map.put("路费", result.get(0).get("roadMoney").toString());
				if(result.get(0).get("activityname") != null) {
					map.put(result.get(0).get("activityname").toString(), result.get(0).get("activityMoney").toString());
				}
				if(result.size() > 1) {
					for(int i = 0; i< result.size();i++) {
						if(i>=1) {
							map.put(result.get(i).get("activityname").toString(), result.get(i).get("activityMoney").toString());
						}
					}	
				}
				map.put("下次预约时间", result.get(0).get("nextCollDate")==null?"":result.get(0).get("nextCollDate").toString());
			}
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "ticketfy", map,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printGrantCost>>>>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 读取 电子秤 重量
	 * @return
	 */
	@RequestMapping("/readPlasmAmount")
	@ResponseBody
	public DataRow readPlasmAmount(){
		try {
			String ip = CommonUtil.getIpAddress(request);
			HashMap<String, String> row = new HashMap<String, String>();
			socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "balance", row,messageMap);
		} catch (Exception e) {
			logger.error("CommonController>>>>>>>>>printGrantCost>>>>>>>>>",e);
		}
		return messageMap;
	}
}
