package com.fadl.registries.controller;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.CommonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.service.CommonService;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.service.DetailedInfoService;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.registries.service.ProviderRegistriesService;

/**
 * <p>
 * 登记记录表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-05-05
 */
@Controller
@RequestMapping("/providerRegistries")
public class ProviderRegistriesController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ProviderRegistriesController.class);
	@Autowired
	ProviderRegistriesService providerRegistriesService;
	@Autowired
	ICostGrantService iCostGrantService; 
	@Autowired
	CommonService commonService;
	@Autowired
	SocketUtil socketUtil;
	@Autowired
	DetailedInfoService detailedInfoService;
	/**
     * 登记信息查询页面
     * @return
     */
    @RequestMapping("/regisinformation")
    @RequiresPermissions("registration:save")
    public String regisinformation() {
        return "/business/register/regisinformation";
    }
    /**
     * 固定浆员登记页面
     * @return
     */
    @RequestMapping("/registration")
    @RequiresPermissions("registration:view")
    public String registration() {
        return "/business/register/registration";
    }
    /**
     * 跳转静脉验证页面
     * @return
     */
    @RequestMapping("/vein")
    public String vein() {
		return "/popup/bus/Bus_New-Veri";
    }
    /**
     * 跳转手机修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/updatephone")
    @RequiresPermissions("registration:phone")
    public String updatephone(String id,Model model) {
    	EntityWrapper<DetailedInfo> ew = new EntityWrapper<DetailedInfo>();
    	ew.eq("baseId", id);
    	ew.eq("type", 0);
    	DetailedInfo detailed =detailedInfoService.selectOne(ew);
    	model.addAttribute("detailed", detailed);
		return "/business/register/phone_update";
    }
    /**
     * 跳转采浆人数统计页面
     * @return
     */
    @RequestMapping("/collectionCount")
    @RequiresPermissions("registration:collection")
    public String collectionCount() {
		return "/business/register/collection_count";
    }
    /**
     * 跳转采浆人数统计详情页面
     * @return
     */
    @RequestMapping("/collectionCountDetails")
    public String collectionCountDetails(String providerNo,Model model) {
    	model.addAttribute("providerNo", providerNo);
    	return "/business/register/collection_count_details";
    }
    /**
     * 跳转首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
    	return "/hpage";
    }
    /**
     * 跳转拒绝信息页面
     * @return
     */
    @RequestMapping("/immuneRegister")
    public String immuneRegister(String message,Model model) {
    	model.addAttribute("register", message);
    	return "/business/register/register_details";
    }
    /**
     * 查询浆员基本信息
     * @param registerDate 条件对象
     * @param page 页码
     * @param limit 每页条数
     * @return
     */
    @RequestMapping(value="/queryRegistrationList",method= RequestMethod.POST)
    @InvokeLog(name = "queryRegistrationList", description = "查询浆员基本信息")
    @ResponseBody
    @RequiresPermissions("registration:list")
    public DataRow queryRegistrationList(String registerDate, Integer page, Integer limit) {
        Page<DataRow> pageing = null;
        String registerNine="";
        try {
        	if(StringHelper.isEmpty(registerDate)) {
        		registerDate=DateUtil.sfZero.format(new Date());
        		registerNine=DateUtil.sfNine.format(new Date());
        	}else {
        		registerDate=DateUtil.sfZero.format(DateUtil.sfDay.parse(registerDate));
        		registerNine=DateUtil.sfNine.format(DateUtil.sfDay.parse(registerDate));
        	}
        	pageing = new Page<DataRow>(page,limit);
            //默认查询当天登记
        	pageing=providerRegistriesService.queryRegistrationList(registerDate,registerNine, pageing);
           messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("ProviderRegistriesController>>>>>>>>>queryProviderRegistriesList>>>>>",e);
        }
        return messageMap;
    }
    /**
      * 获取登记断档号
     * @return
     */
    @RequestMapping("/queryBrokenNumber")
    @InvokeLog(name = "queryBrokenNumber", description = "获取登记断档号")
    @ResponseBody
    public DataRow queryBrokenNumber() {
    	try {
    		providerRegistriesService.queryBrokenNumber(messageMap);
		} catch (Exception e) {
			  logger.error("ProviderRegistriesController>>>>>>>>>queryBrokenNumber>>>>>",e);
		}
		return messageMap;
    }
    /**
     * 单击列表查询浆员信息
     * @param map
     * @return
     */
    @RequestMapping(value="queryPlasmaProviderNo",method= RequestMethod.POST)
    @InvokeLog(name = "queryPlasmaProviderNo", description = "单击列表查询浆员信息")
    @ResponseBody
    public DataRow queryPlasmaProviderNo(@RequestParam Map<String,Object> map) {
    	 try {
             providerRegistriesService.queryPlasmaProviderNo(map,messageMap);
         } catch (Exception e) {
             logger.error("ProviderRegistriesController>>>>>>>>>queryPlasmaProviderNo>>>>>",e);
         }
         return messageMap;
    }
    /**
     * 登记献浆卡读取浆员个人信息
     * 手动登记、读卡登记共用接口
     * @return
     */
    @RequestMapping(value="/readPlasmaProviderNo",method= RequestMethod.POST)
    @InvokeLog(name = "readPlasmaProviderNo", description = "登记献浆卡读取浆员个人信息")
    @RequiresPermissions("registration:query")
    @ResponseBody
    public DataRow readPlasmaProviderNo(@RequestParam Map<String,String> map) {
        try {
        	if(StringHelper.isEmpty(map.get("code"))) {
        		String ip = CommonUtil.getIpAddress(request);
            	socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "cardr",null,messageMap);
            	if(messageMap.getInt("code")==-1) {
            		providerRegistriesService.readPlasmaProviderNo(messageMap);
            	}
        	}else {
        		messageMap.put("data", map);
        		providerRegistriesService.readPlasmaProviderNo(messageMap);
        	}
        } catch (Exception e) {
            logger.error("ProviderRegistriesController>>>>>>>>>readPlasmaProviderNo>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 验证登记流程
     * @param provider
     * @return
     */
    @RequestMapping("/verifyRegister")
    @InvokeLog(name = "verifyRegister", description = "验证登记流程")
    @ResponseBody
    public DataRow verificationRegister(ProviderRegistries provider) {
    	try {
    		providerRegistriesService.verificationRegister(provider,messageMap);
		} catch (Exception e) {
			 logger.error("ProviderRegistriesController>>>>>>>>>readPlasmaProviderNo>>>>>",e);
		}
		return messageMap;
    }
    /**
     * 新增登记浆员信息
     * @param request 文件流
     * @param ProviderRegistries  登记实体类
     * @param img 图片
     * @return
     */
   
    @RequestMapping(value="/insertRegistries",method= RequestMethod.POST)
    @InvokeLog(name = "insertRegistries", description = "新增登记浆员信息")
    @ResponseBody
    @RequiresPermissions("registration:add")
    public DataRow insertRegistries(ProviderRegistries providerRegistries,Integer roadFeeType,BigDecimal roadFee,HttpServletRequest request){
    	try {
    		String img  = request.getParameter("sceneImage");
    		providerRegistriesService.insertRegistries(providerRegistries,roadFeeType,roadFee,messageMap,img);
        }catch (Exception e){
            logger.error("ProviderRegistriesController>>>>>>>>>insertRegistries>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 删除登记记录
     * @param ids
     * @param messageMap
     * @throws Exception
     */
    @RequestMapping(value="/delectRegistration",method=RequestMethod.POST)
    @InvokeLog(name = "delectRegistration", description = "删除登记记录")
    @ResponseBody
    public DataRow delectRegistration(String ids) {
    	try {
    		providerRegistriesService.delectRegistration(ids,messageMap);
		} catch (Exception e) {
			 logger.error("ProviderRegistriesController>>>>>>>>>delectRegistration>>>>>",e);
		}
		return messageMap;
    }
    /**
     * 浆员登记高级查询
     * @param map 
     * @param page 页码
     * @param limit 每页条数
     * @return
     */
    @RequestMapping(value="/querySeniorRegistries",method= RequestMethod.POST)
    @InvokeLog(name = "querySeniorRegistries", description = "浆员登记高级查询")
    @ResponseBody
    public DataRow querySeniorRegistries(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
        Page<DataRow> pageing = null;
        try {
            pageing = new Page<DataRow>(page,limit);
            providerRegistriesService.querySeniorRegistries(map, pageing);
            messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("ProviderRegistriesController>>>>>>>>>querySeniorRegistries>>>>>"+e);
        }
        return messageMap;
    }
    
    /**
     * 查询用户列表信息
     * @param param 条件参数 参数于数据库对应
     * @return
     */
    @RequestMapping(value="/queryWhereAdminInfoList",method= RequestMethod.POST)
    @InvokeLog(name = "queryWhereAdminInfoList", description = "查询用户列表信息")
    @ResponseBody
    public DataRow queryWhereAdminInfoList(){
        try {
           List<DataRow> list = commonService.queryWhereAdminInfoList(null);
           messageMap.initSuccess(list);
        }catch (Exception e){
            logger.error("ProviderRegistriesController>>>>>>>>>queryWhereAdminInfoList>>>>>",e);
        }
        return messageMap;
    }
        
    /**
	 * 下载 浆员登记列表
	 * @param response
	 * @param request
	 * @return
	*/
	@RequestMapping("/downRegistries")
	@InvokeLog(name = "downRegistries", description = "下载 浆员登记列表")
	public DataRow Registries(@RequestParam Map<String,String> data,HttpServletResponse response ){
		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("浆员登记记录列表", "UTF-8") + ".xlsx");
			// 设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");
			List<DataRow> list =providerRegistriesService.downloadRegistries(data);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("icNumber", "IC卡号");
			map.put("providerNo", "献浆卡号");
			map.put("name", "姓名");
			map.put("idNo", "身份证号");
			map.put("registriesNo", "登记号");
			map.put("sex", "性别");
			map.put("plasmaType", "免疫类型");
			map.put("registriesType", "验证类型");
			map.put("registerDate", "登记日期");
			map.put("createDate", "建档日期");
			map.put("phone", "联系方式");
			map.put("addressx", "现住地址");
			map.put("remarks", "备注");
			RegistriesExcelUtil.exportExcelX("浆员登记记录列表",map,list,null,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("ExportDataAction>> downUserRegList >>Exception:",e);
		}
		return null;
	} 
	/**
   	 * 修改手机号码
   	 * @param detailedInfo
   	 * @param messageMap
   	 * @throws Exception
   	 */
	@RequestMapping(value="/updatePhoneNumber")
	@InvokeLog(name = "updatePhoneNumber", description = "修改手机号码")
	@ResponseBody
	public DataRow updatePhoneNumber(DetailedInfo detailedInfo) {
		try {
			providerRegistriesService.updatePhoneNumber(detailedInfo,messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>updatePhoneNumber",e);
		}
		return messageMap;
	}
	
	/**
	 * 以老带新路费添加
	 * @return
	 */
	@RequestMapping("addCost")
	@RequiresPermissions("registration:addCost")
	public String addCost(){
		return "/business/busblood/add_cost";
	}
	
	/**
	 * 以老带新路费添加
	 * @return
	 */
	@RequestMapping(value="/addOldPlasmaCost",method= RequestMethod.POST)
    @InvokeLog(name = "addOldPlasmaCost", description = "以老带新路费添加")
    @ResponseBody
	public DataRow addOldPlasmaCost(CostGrant costGrant){
		try {
			costGrant.setRoadFeeType(0);
			costGrant.setIsRoadFee(1);
			boolean res = iCostGrantService.insert(costGrant);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>> addOldPlasmaCost >>Exception:",e);
		}
		return messageMap;
	}
	/**
	 * 下载 浆员登记列表
	 * @param response
	 * @param request
	 * @return
	
	@RequestMapping("/downAbout")
	@InvokeLog(name = "downAbout", description = "下载 预约未到人员列表")
	public DataRow downAbout(HttpServletResponse response,HttpServletRequest request ){
		try {
			Map<String, String[]> maps = request.getParameterMap();
			DataRow data=new DataRow();
			for(String str : maps.keySet()){
				data.put(str,maps.get(str)[0]);
			}
			System.out.println(maps.toString());
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("预约未到人员列表", "UTF-8") + ".xlsx");
			// 设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");
			//导出不分页
			data.put("fenye", "false");
			Page page = new Page();
			List<DataRow> list = providerRegistriesService.querySeniorRegistries(data,page);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("providerNo", "献浆卡号");
			map.put("name", "姓名");
			map.put("sex", "性别");
			map.put("immuneName", "类型");
			map.put("birthday", "生日");
			map.put("maxDate", "最后采浆时间");
			map.put("aboutDate", "预约日期");
			map.put("status", "状态");
			map.put("phone", "手机号码");
			map.put("addressx", "地址");
			map.put("groupName", "组号");
			map.put("remarks", "备注");
			RegistriesExcelUtil.exportExcelX("预约未到人员列表",map,list,null,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("ExportDataAction>> downAbout >>Exception:",e);
		}
		return null;
	} */
	/**
   	 * 采浆记录统计人数
   	 * @param map
   	 * @param Pageing
   	 * @throws Exception
   	 */
	@RequestMapping("/queryCollectionCountPeople")
	@ResponseBody
	public DataRow queryCollectionCountPeople(Integer page, Integer limit,@RequestParam Map<String,String> map) {
		try {
			Page<DataRow> pageing = new Page<DataRow>(page,limit);
			providerRegistriesService.queryCollectionCountPeople(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryCollectionCount",e);
		}
		return messageMap;
	}
	/**
   	 * 采浆记录统计人数详情
   	 * @param providerNo
   	 * @throws Exception
   	 */
	@RequestMapping("/queryCollectionCountDetails")
	@ResponseBody
	public DataRow queryCollectionCountDetails(String providerNo) {
		try {
			List<DataRow> list =providerRegistriesService.queryCollectionCountDetails(providerNo);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryCollectionCountDetails",e);
		}
		return messageMap;
	}
	/**
	 * 今日建档人数,今日登记人数,今日采浆人数,今日采浆重量(首页数据)
	 * @return
	 */
	@RequestMapping("/queryTodayPeopleInfo")
	@ResponseBody
	public DataRow queryTodayPeopleInfo() {
		try {
			providerRegistriesService.queryTodayPeopleInfo(messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryTodayPeopleInfo",e);
		}
		return messageMap;
	}
	/**
	 * 建档人数统计(扇形图)
	 * @return
	 */
	@RequestMapping("/queryRecordPeople")
	@ResponseBody
	public DataRow queryRecordPeople() {
		try {
			providerRegistriesService.queryRecordPeople(messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryRecordPeople",e);
		}
		return messageMap;
	}
	/**
	 * 登记人数统计(扇形图)
	 * @return
	 */
	@RequestMapping("/queryRegisterPeople")
	@ResponseBody
	public DataRow queryRegisterPeople() {
		try {
			providerRegistriesService.queryRegisterPeople(messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryRegisterPeople",e);
		}
		return messageMap;
	}
	/**
	 * 采浆人数统计(扇形图)
	 * @return
	 */
	@RequestMapping("/queryCollectionPeopleCount")
	@ResponseBody
	public DataRow queryCollectionPeopleCount() {
		try {
			providerRegistriesService.queryCollectionPeopleCount(messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryCollectionPeopleCount",e);
		}
		return messageMap;
	}
	/**
	 * 采浆量统计(扇形图)
	 * @return
	 */
	@RequestMapping("/queryPlasmAmountPeopleCount")
	@ResponseBody
	public DataRow queryPlasmAmountPeopleCount() {
		try {
			providerRegistriesService.queryPlasmAmountPeopleCount(messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryPlasmAmountPeopleCount",e);
		}
		return messageMap;
	}
	/**
	 * 建档人数(柱状图)
	 * @return
	 */
	@RequestMapping("/queryHistogramPeople")
	@ResponseBody
	public DataRow queryHistogramPeople(@RequestParam Map<String,String> map) {
		try {
			providerRegistriesService.queryHistogramPeople(map,messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryHistogramPeople",e);
		}
		return messageMap;
	}
	/**
	 * 登记人数(折线图)
	 * @return
	 */
	@RequestMapping("/queryRegistriesPeople")
	@ResponseBody
	public DataRow queryRegistriesPeople(@RequestParam Map<String,String> map) {
		try {
			providerRegistriesService.queryRegistriesPeople(map,messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryRegistriesPeople",e);
		}
		return messageMap;
	}
	/**
	 * 采浆人数(柱状图)
	 * @return
	 */
	@RequestMapping("/queryCollectionPeople")
	@ResponseBody
	public DataRow queryCollectionPeople(@RequestParam Map<String,String> map) {
		try {
			providerRegistriesService.queryCollectionPeople(map,messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryCollectionPeople",e);
		}
		return messageMap;
	}
	/**
	 * 采浆重量(折线图)
	 * @return
	 */
	@RequestMapping("/queryPlasmAmountPeople")
	@ResponseBody
	public DataRow queryPlasmAmountPeople(@RequestParam Map<String,String> map) {
		try {
			providerRegistriesService.queryPlasmAmountPeople(map,messageMap);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryPlasmAmountPeople",e);
		}
		return messageMap;
	}
}


