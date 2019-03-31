package com.fadl.immuneAssay.controller;


import java.util.Date;
import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.fadl.immuneAssay.entity.PrivilegeStrengthenInjection;
import com.fadl.immuneAssay.service.PrivilegeStrengthenInjectionService;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 注射管理-特免加强针注射 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Controller
@RequestMapping("/privilegeStrengthenInjection")
public class PrivilegeStrengthenInjectionController extends AbstractController{

	
	private static Logger logger = LoggerFactory.getLogger(PrivilegeStrengthenInjectionController.class);
	
	@Autowired
	private PrivilegeStrengthenInjectionService privilegeStrengthenInjectionService; 
	@Autowired
	LogService logService;
	/**
	 * 跳转特免加强针页面
	 * @return
	 */
	@RequestMapping("/toSpecialStrengthenShot")
	@RequiresPermissions("privilegeStrengthen:view")
	public String toSpecialStrengthen() {
		return "/immune/injectionmanagement/special_strengthen_shot";
	}
	
	/**
	 * 普免基础针 未注射人员（分页）
	 * */
	@RequestMapping("/notShotSpeStrengthen")
	@InvokeLog(name = "notShotSpeStrengthen", description = "普免基础针 未注射人员")
	@RequiresPermissions("privilegeStrengthen:listW")
	@ResponseBody
	public DataRow notShotSpeStrengthen(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			privilegeStrengthenInjectionService.notShotSpeStrengthen(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>notShotSpeStrengthen>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免基础针 已注射人员（分页）
	 * */
	@RequestMapping("/haveShotSpeStrengthen")
	@InvokeLog(name = "haveShotSpeStrengthen", description = "普免基础针 已注射人员")
	@RequiresPermissions("privilegeStrengthen:listY")
	@ResponseBody
	public DataRow haveShotSpeStrengthen(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			privilegeStrengthenInjectionService.haveShotSpeStrengthen(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>haveShotSpeStrengthen>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免加强针注射  头部内容
	 * */
	@RequestMapping("/specialStrengthenHead")
	@InvokeLog(name = "specialStrengthenHead", description = "特免加强针注射  头部内容")
	@ResponseBody
	public DataRow specialStrengthenHead (@RequestParam HashMap<String, Object> map) {
		try {
			DataRow res = privilegeStrengthenInjectionService.specialStrengthenHead(map);
			messageMap.initSuccess(res);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>specialStrengthenHead>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免加强针  注射
	 * */
	@RequestMapping("/speStrengthenToShot")
	@InvokeLog(name = "speStrengthenToShot", description = "特免加强针  注射")
	@RequiresPermissions("privilegeStrengthen:injection")
	@ResponseBody
	public DataRow speStrengthenToShot(PrivilegeStrengthenInjection entity) {
		try {
			entity.setIsShot(1);
			boolean flag = privilegeStrengthenInjectionService.updateById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.speStrengthenToShot, IConstants.DESC.replace("{0}", "已取消特免加强针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>speStrengthenToShot>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免加强针  取消注射
	 * */
	@RequestMapping("/speStrengthenToNotShot")
	@InvokeLog(name = "speStrengthenToNotShot", description = "特免加强针  取消注射")
	@RequiresPermissions("privilegeStrengthen:cancel")
	@ResponseBody
	public DataRow speStrengthenToNotShot(PrivilegeStrengthenInjection entity) {
		try {
			entity.setIsShot(0);
			boolean flag = privilegeStrengthenInjectionService.updateAllColumnById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.speStrengthenToNotShot, IConstants.DESC.replace("{0}", "已取消特免加强针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>speStrengthenToNotShot>>>>>>",e);
		}
		return messageMap;
	}
	
	
}

