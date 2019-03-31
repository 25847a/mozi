package com.plasma.buss.role.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.role.service.UserRoleService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.VP;
import com.plasma.common.util.Validator;

/**
 * 角色管理
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class UserRoleAction extends BaseController {

	private static final Logger log = LogManager.getLogger(UserRoleAction.class);
	
	@Autowired
	public UserRoleService userRoleService;
	
	/**
	 * 角色管理页面
	 * @return
	 */
	@RequestMapping("/role")
	public String role() {
		return "config/role_list";
	}
	
	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping("/roleList")
	@ResponseBody
	public Object roleList(){
		try {
			pageBean = userRoleService.queryRoleList(info,pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>roleList>>>",e);
		}
		return messageMap;
	} 
	/**
	 * 查询角色列表
	 */
	@RequestMapping("/roleAllList")
	@ResponseBody
	public Object roleAllList(){
		try {
			DataRow dr = new DataRow();
			dr.put("listData",userRoleService.queryAllRoleList(info));
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>roleAllList>>>",e);
		}
		return messageMap;
	} 
	/**
	 * 添加角色
	 * @return
	 */
	@RequestMapping("/saveOrUpdateRole")
	@ResponseBody
	public Object saveOrUpdateRole(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("roleName");
			if (val.validate()) {
				if (StringHelper.isEmpty(info.getString("id"))) {
					userRoleService.saveRole(info);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
				}else{
					int res = userRoleService.updateRoleById(info);
					if (res > 0) {
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
						messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
					}else{
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
						messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
					}
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>saveRole>>>",e);
		}
		return messageMap;
	}
	
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public Object delRole(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id",VP.NUMBER);
			if (val.validate()) {
				userRoleService.deleteRoleById(info.getLong("id"),messageMap);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>updateRole>>>",e);
		}
		return messageMap;
	}
}
