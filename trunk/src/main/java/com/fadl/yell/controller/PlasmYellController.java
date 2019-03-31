package com.fadl.yell.controller;


import java.util.HashMap;
import java.util.Map;

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
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.service.ImmuneService;
import com.fadl.yell.entity.PlasmYell;
import com.fadl.yell.service.PlasmYellService;

/**
 * <p>
 * 采浆叫号 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@Controller
@RequestMapping("/plasmYell")
public class PlasmYellController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PlasmYellController.class);
	
	@Autowired
	public PlasmYellService plasmYellService;
	@Autowired
	ImmuneService immuneService;
	@Autowired
	ConfigService configservice;
	
	/**
	 * 跳转叫号页面
	 * @return
	 */
	@RequestMapping("/plasmaYell")
	@RequiresPermissions("yell:view")
	public String plasmaYell(){
		return "/plasm_collection/collection_validate";
	}
	/**
	 * 跳转免疫确认页面
	 * @return
	 */
	@RequestMapping("/immuneDetails")
	public String immuneDetails(@RequestParam Map<String,String> map,Model model){
		model.addAttribute("map", map);
		return "/plasm_collection/immune_details";
	}
	
	/**
	 * 查询叫号页面列表
	 * @return
	 */
	@RequestMapping(value="/queryplasmaYellList",method = RequestMethod.POST)
	@InvokeLog(name = "queryplasmaYellList", description = "查询叫号页面列表")
	@ResponseBody
	@RequiresPermissions("yell:view")
	public DataRow queryplasmaYellList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p=plasmYellService.queryplasmaYellList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>queryplasmaYellList",e);
		}
		return messageMap;
	}
	/**
	 * 获取头部信息(啊健)
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryValidateHeadInfo")
	@ResponseBody
	public DataRow queryValidateHeadInfo(PlasmYell yell) {
		try {
			EntityWrapper<PlasmYell> ew = new EntityWrapper<PlasmYell>();
			ew.eq("providerNo", yell.getProviderNo());
			ew.eq("allId", yell.getAllId());
			ew.eq("status", "0");
			PlasmYell plasm =plasmYellService.selectOne(ew);
			messageMap.initSuccess(plasm);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>queryValidateHeadInfo",e);
		}
		return messageMap;
	}
	/**
	 * 验证并喊号
	 * @return
	 */
	@RequestMapping(value="/updatePlasmYell",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmYell", description = "验证并喊号")
	@ResponseBody
	@RequiresPermissions("yell:update")
	public DataRow updatePlasmYell(@RequestParam HashMap<String, String> map){
		try {
			plasmYellService.updatePlasmYellById(map, messageMap);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>updatePlasmYell",e);
		}
		return messageMap;
	}
	
	/**
	 * 取消喊号
	 * @return
	 */
	@RequestMapping(value="/cancelPlasmYell",method = RequestMethod.POST)
	@InvokeLog(name = "cancelPlasmYell", description = "取消喊号")
	@ResponseBody
	@RequiresPermissions("yell:cancel")
	public DataRow cancelPlasmYell(@RequestParam HashMap<String, String> map){
		try {
			plasmYellService.cancelPlasmYell(map, messageMap);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>cancelPlasmYell",e);
		}
		return messageMap;
	}
	/**
	 * 验证是否需要进行免疫流程
	 * @param providerNo
	 * @return
	 */
	@RequestMapping(value="/verifyingImmunity")
	@InvokeLog(name = "verifyingImmunity", description = "验证是否需要进行免疫流程")
	@ResponseBody
	public DataRow verifyingImmunity(String providerNo) {
		try {
			plasmYellService.verifyingImmunity(providerNo,messageMap);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>verifyingImmunity",e);
		}
		return messageMap;
	}
	/**
	 * 进行免疫流程(插入统一使用)
	 * @param providerNo
	 * @return
	 */
	@RequestMapping(value="/operationImmunity")
	@InvokeLog(name = "operationImmunity", description = "进行免疫流程")
	@ResponseBody
	public DataRow operationImmunity(String providerNo) {
		try {
			Config config = configservice.queryConfig("immune_type","1");
			if(config.getIsDisable()==1) {
				immuneService.operationImmunityAlone(providerNo, messageMap);
			}else {
				immuneService.operationImmunity(providerNo,messageMap);
			}
			
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>operationImmunity",e);
		}
		return messageMap;
	}
	/**
	 * 获取免疫验证拒绝免疫设置
	 * @return
	 */
	@RequestMapping("/queryImmuneRefuse")
	@ResponseBody
	public DataRow queryImmuneRefuse() {
		try {
			Config config =  configservice.queryConfig("immune_refuse", "0");
			messageMap.initSuccess(config);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryImmuneRefuse",e);
		}
		return messageMap;
	}
	/**
	 * 更新免疫验证拒绝免疫设置
	 * @return
	 */
	@RequestMapping("/updateImmuneRefuse")
	@ResponseBody
	public DataRow updateImmuneRefuse(PlasmYell yell) {
		try {
			PlasmYell p = new PlasmYell();
			p.setIsImmune(0);
			EntityWrapper<PlasmYell> ew = new EntityWrapper<PlasmYell>();
			ew.eq("providerNo", yell.getProviderNo());
			ew.eq("allId", yell.getAllId());
			plasmYellService.update(p, ew);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>updateImmuneRefuse",e);
		}
		return messageMap;
	}
	/**
	 * 查询采浆验证免疫验证拒绝免疫设置
	 * @return
	 */
	@RequestMapping("/queryImmuneYell")
	@ResponseBody
	public DataRow queryImmuneYell(PlasmYell yell) {
		//这里先判断是否强制免疫，再判断此浆员是否已经免疫了
		try {
			
			EntityWrapper<PlasmYell> ew = new EntityWrapper<PlasmYell>();
			ew.eq("providerNo", yell.getProviderNo());
			ew.eq("allId", yell.getAllId());
			PlasmYell p =plasmYellService.selectOne(ew);
			messageMap.initSuccess(p);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>updateImmuneRefuse",e);
		}
		return messageMap;
	}
	/**
	 * 读卡判断是否存在此浆员
	 * @return
	 */
	@RequestMapping("/queryPlasmYellInfoDu")
	@ResponseBody
	public DataRow queryPlasmYellInfoDu(PlasmYell yell) {
		try {
			EntityWrapper<PlasmYell> ew = new EntityWrapper<PlasmYell>();
			ew.eq("providerNo", yell.getProviderNo());
			ew.eq("DATE_FORMAT(updateDate,'%Y-%m-%d')", yell.getUpdateDate());
			PlasmYell p =plasmYellService.selectOne(ew);
			messageMap.initSuccess(p);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryPlasmYellInfoDu",e);
		}
		return messageMap;
	}
}

