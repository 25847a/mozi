package com.fadl.immuneAssay.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.service.OrdinaryInjectionService;
import com.fadl.log.service.LogService;


/**
 * <p>
 * 注射管理--普免基础针注射 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Controller
@RequestMapping("/ordinaryInjection")
public class OrdinaryInjectionController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(OrdinaryInjectionController.class);
	
	@Autowired
	private OrdinaryInjectionService ordinaryInjectionService;
	@Autowired
	LogService logService;
	/**
	 * 跳转普免基础针页面
	 * @return
	 */
	@RequestMapping("/toOrdinaryBaseShot")
	@RequiresPermissions("ordinaryInjection:view")
	public String toOrdinaryBase() {
		return "/immune/injectionmanagement/ordinary_base_shot";
	}
	/**
	 * 注射信息查询页面
	 * @return
	 */
	@RequestMapping("/toShotMessageQuery")
	@RequiresPermissions("todayInjection:view")
	public String toShotMessageQuery() {
		return "/immune/injectionmanagement/shot_message_query";
	}
	/**
	 * 免疫注射信息模块页面
	 * @return
	 */
	@RequestMapping("/toImmuneShotMessage")
	@RequiresPermissions("injectionFrequency:view")
	public String toImmuneShotMessage() {
		return "/immune/injectionmanagement/immune_shot_message";
	}
	/**
	 * 跳转普免打印页面
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/downloadPu")
	@RequiresPermissions("todayInjection:dao")
	public String downloadPu(@RequestParam Map<String,String> map,Model model) {
		map.put("recording", "供血浆者乙肝免疫注射记录");
		model.addAttribute("download", map);
		return "/immune/injectionmanagement/Injection_record";
	}
	/**
	 * 跳转特免打印页面
	 * @param map
	 * @param model
	 * @return
	 */
	@RequestMapping("/downloadTe")
	@RequiresPermissions("todayInjection:dao")
	public String downloadTe(@RequestParam Map<String,String> map,Model model) {
		map.put("recording", "供血浆者乙阳免疫注射记录");
		model.addAttribute("download", map);
		return "/immune/injectionmanagement/Injection_record";
		
	}
	/**
	 * 普免基础针 未注射人员（分页）
	 * @param limit
	 * @param page
	 * @param updateDate
	 * @return
	 */
	@RequestMapping("/notShotOrdBaseList")
	@InvokeLog(name = "notShotOrdBaseList", description = "普免基础针 未注射人员")
	@RequiresPermissions("ordinaryInjection:listW")
	@ResponseBody
	public DataRow notShotOrdBaseList(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryInjectionService.notShotOrdBaseList(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>notShotOrdBaseList>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免基础针 已注射人员（分页）
	 * @param limit
	 * @param page
	 * @param updateDate
	 * @return
	 */
	@RequestMapping("/shotOrdBaseList")
	@InvokeLog(name = "shotOrdBaseList", description = "普免基础针 已注射人员（分页）")
	@RequiresPermissions("ordinaryInjection:listY")
	@ResponseBody
	public DataRow shotOrdBaseList(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryInjectionService.shotOrdBaseList(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>shotOrdBaseList>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免基础针  头部内容
	 * @param map
	 * @return
	 */
	@RequestMapping("/ordinaryBaseHead")
	@InvokeLog(name = "ordinaryBaseHead", description = "普免基础针  头部内容")
	@ResponseBody
	public DataRow ordinaryBaseHead (@RequestParam HashMap<String, Object> map) {
		try {
			DataRow res = ordinaryInjectionService.ordinaryBaseHead(map);
			messageMap.initSuccess(res);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordinaryBaseHead>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免基础针  注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ordBaseToShot")
	@InvokeLog(name = "ordBaseToShot", description = "普免基础针  注射")
	@RequiresPermissions("ordinaryInjection:injection")
	@ResponseBody
	public DataRow ordBaseToShot(OrdinaryInjection entity) {
		try {
			entity.setIsShot(1);
			boolean flag = ordinaryInjectionService.updateById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.ordBaseToShot, IConstants.DESC.replace("{0}", "已普免基础针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordBaseToShot>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免基础针  取消注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ordBaseToNotShot")
	@InvokeLog(name = "ordBaseToNotShot", description = "普免基础针  取消注射")
	@RequiresPermissions("ordinaryInjection:cancel")
	@ResponseBody
	public DataRow ordBaseToNotShot(OrdinaryInjection entity) {
		try {
			entity.setIsShot(0);
			boolean flag = ordinaryInjectionService.updateAllColumnById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.ordBaseToNotShot, IConstants.DESC.replace("{0}", "已取消普免基础针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordBaseToNotShot>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 注射信息查询 
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryShotMsg")
	@InvokeLog(name = "queryShotMsg", description = "注射信息查询 ")
	@RequiresPermissions("todayInjection:list")
	@ResponseBody
	public DataRow queryShotMsg(Integer page,Integer limit,@RequestParam HashMap<String, String> map) {
		try {	
				Page<DataRow> paging = new Page<>(page,limit);
				if(!map.containsKey("startDate") && !map.containsKey("endDate")) {
					map.put("startDate", DateUtil.sfDay.format(new Date()));
					map.put("endDate", DateUtil.sfDay.format(new Date()));
				}
				ordinaryInjectionService.queryShotMsg(map, paging);
				messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>queryShotMsg>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 获取基础免疫所有类别
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseImmuneTypes")
	@InvokeLog(name = "getBaseImmuneTypes", description = "获取免疫所有类别")
	@ResponseBody
	public DataRow getBaseImmuneTypes (String id) {
		try {
			DataRow data =  ordinaryInjectionService.getBaseImmuneTypes(id);
			messageMap.initSuccess(data);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>getBaseImmuneTypes>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 获取疫苗信息(啊健)
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryVaccineInfo")
	@InvokeLog(name = "queryVaccineInfo", description = "获取疫苗信息(啊健)")
	@ResponseBody
	public DataRow queryVaccineInfo (String immune) {
		try {
			DataRow data =  ordinaryInjectionService.queryVaccineInfo(immune);
			messageMap.initSuccess(data);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>queryVaccineInfo>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 获取加强免疫所有类别
	 * @param id
	 * @return
	 */
	@RequestMapping("/getStreImmuneTypes")
	@InvokeLog(name = "getStreImmuneTypes", description = "获取免疫所有类别")
	@ResponseBody
	public DataRow getStreImmuneTypes (String id) {
		try {
			List<DataRow> list =  ordinaryInjectionService.getStreImmuneTypes(id);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>getStreImmuneTypes>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 免疫注射信息      免疫基本信息 
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping("/imuneMsg")
	@InvokeLog(name = "imuneMsg", description = "免疫注射信息      免疫基本信息 ")
	@RequiresPermissions("injectionFrequency:in")
	@ResponseBody
	public DataRow imuneMsg(Integer page,Integer limit,@RequestParam HashMap<String, String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryInjectionService.imuneMsg(map, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>imuneMsg>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 免疫注射信息       基础针注射信息
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping("/baseInjectMsg")
	@InvokeLog(name = "baseInjectMsg", description = "免疫注射信息       基础针注射信息")
	@RequiresPermissions("injectionFrequency:left")
	@ResponseBody
	public DataRow baseInjectMsg(Integer page,Integer limit,@RequestParam Map<String,String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryInjectionService.baseInjectMsg(map, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>baseInjectMsg>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 免疫注射信息       加强针注射信息
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping("/strengthenInjectMsg")
	@InvokeLog(name = "strengthenInjectMsg", description = "免疫注射信息       加强针注射信息")
	@RequiresPermissions("injectionFrequency:right")
	@ResponseBody
	public DataRow strengthenInjectMsg(Integer page,Integer limit,@RequestParam Map<String,String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryInjectionService.strengthenInjectMsg(map, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>strengthenInjectMsg>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 打印注射文件
	 * @param map
	 * @return
	 */
	@RequestMapping("/downloadImmune")
	@InvokeLog(name = "downloadImmune", description = "打印注射文件")
	@RequiresPermissions("translate:immune")
	@ResponseBody
	public DataRow downloadImmune(@RequestParam Map<String,String> map) {
		try {
			ordinaryInjectionService.downloadImmune(map,messageMap);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>downloadImmune>>>>>>",e);
		}
		return messageMap;
		
	}
}

