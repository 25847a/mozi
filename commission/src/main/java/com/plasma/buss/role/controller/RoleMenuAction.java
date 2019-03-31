package com.plasma.buss.role.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.role.service.RoleMenuService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.VP;
import com.plasma.common.util.Validator;

/**
 * 角色权限关联
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class RoleMenuAction extends BaseController {

	private static final Logger log = LogManager.getLogger(RoleMenuAction.class);
	
	@Autowired
	public RoleMenuService roleMenuService;
	
	/**
	 * 根据用户查询用户角色权限
	 * @return
	 */
	@RequestMapping("/queryRoleMenuListByRoleId")
	@ResponseBody
	public Object queryRoleMenuListByRoleId(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				List<DataRow> list = roleMenuService.queryRoleMenuListByRoleId(info.getLong("id"));
				super.fillResultContext(messageMap, list);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("RoleMenuAction>>>>>addRoleMenu>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 角色权限关联
	 * @return
	 */
	@RequestMapping("/addRoleMenu")
	@ResponseBody
	public Object addRoleMenu(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("roleId");
			val.addParamCheck("menuId");
			if (val.validate()) {
				roleMenuService.saveRoleMenu(info);
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
				messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("RoleMenuAction>>>>>addRoleMenu>>>",e);
		}
		return messageMap;
	}
	/**
	 * 查询用户角色权限
	 * @return
	 */
	@RequestMapping("/queryUserRoleMenu")
	@ResponseBody
	public Object queryUserRoleMenu(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("roleId",VP.NUMBER); //角色ID
			if (val.validate()) {
				List<DataRow> drList =roleMenuService.queryUserRoleMenu(info);
				DataRow dr = new DataRow();
				dr.put("listData", drList);
				super.fillResultContext(messageMap, dr);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>queryUserRoleMenu>>>",e);
		}
		return messageMap;
	}
}
