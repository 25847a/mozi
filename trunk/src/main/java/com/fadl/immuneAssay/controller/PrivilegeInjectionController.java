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
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.immuneAssay.service.PrivilegeInjectionService;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 注射管理-特免基础针注射 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Controller
@RequestMapping("/privilegeInjection")
public class PrivilegeInjectionController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PrivilegeInjectionController.class);
	
	@Autowired
	private PrivilegeInjectionService privilegeInjectionService;
	@Autowired
	LogService logService;
	
	
	/**
	 * 特免基础针注射页面
	 * @return
	 */
	@RequestMapping("/toSpecialBaseShot")
	@RequiresPermissions("privilegeInjection:view")
	public String toSpecialBase() {
		return "/immune/injectionmanagement/special_base_shot";
	}
	
	/**
	 * 普免基础针 未注射人员（分页）
	 * @param limit
	 * @param page
	 * @param updateDate
	 * @return
	 */
	@RequestMapping("/notShotSpeBase")
	@InvokeLog(name = "notShotSpeBase", description = "普免基础针 未注射人员")
	@RequiresPermissions("privilegeInjection:listW")
	@ResponseBody
	public DataRow notShotSpeBase(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			privilegeInjectionService.notShotSpeBase(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>notShotSpeBase>>>>>>",e);
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
	@RequestMapping("/haveShotSpeBase")
	@InvokeLog(name = "haveShotSpeBase", description = "普免基础针 已注射人员")
	@RequiresPermissions("privilegeInjection:listY")
	@ResponseBody
	public DataRow haveShotSpeBase(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			privilegeInjectionService.haveShotSpeBase(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>haveShotSpeBase>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免基础针注射  头部内容
	 * @param map
	 * @return
	 */
	@RequestMapping("/specialBaseHead")
	@InvokeLog(name = "specialBaseHead", description = "特免基础针注射  头部内容")
	@ResponseBody
	public DataRow specialBaseHead (@RequestParam HashMap<String, Object> map) {
		try {
			DataRow res = privilegeInjectionService.specialBaseHead(map);
			messageMap.initSuccess(res);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>specialBaseHead>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免基础针  注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/speBaseToShot")
	@InvokeLog(name = "speBaseToShot", description = "特免基础针  注射")
	@RequiresPermissions("privilegeInjection:injection")
	@ResponseBody
	public DataRow speBaseToShot(PrivilegeInjection entity) {
		try {
			entity.setIsShot(1);
			boolean flag = privilegeInjectionService.updateById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.speBaseToShot, IConstants.DESC.replace("{0}", "已特免基础针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>speBaseToShot>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 特免基础针  取消注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/speBaseToNotShot")
	@InvokeLog(name = "speBaseToNotShot", description = "特免基础针  取消注射")
	@RequiresPermissions("privilegeInjection:cancel")
	@ResponseBody
	public DataRow speBaseToNotShot(PrivilegeInjection entity) {
		try {
			entity.setIsShot(0);
			boolean flag = privilegeInjectionService.updateAllColumnById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.speBaseToNotShot, IConstants.DESC.replace("{0}", "已取消特免基础针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>speBaseToNotShot>>>>>>",e);
		}
		return messageMap;
	}
	
}

