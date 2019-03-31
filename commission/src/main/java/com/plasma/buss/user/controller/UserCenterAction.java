package com.plasma.buss.user.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.user.service.UserService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.Validator;

/**
 * 账户管理
 * @author wangjing
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class UserCenterAction extends BaseController{
	private static final Logger log = LogManager.getLogger(UserCenterAction.class);
	@Autowired 
	private UserService userService;
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(){
		return "user/user_list";
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/queryUserList")
	@ResponseBody
	public Object queryUserList(){
		try {
			Validator val = new Validator(info,messageMap);
			if (val.validate()) {
				pageBean = userService.queryUserList(info,pageBean);
				DataRow dr = new DataRow();
				dr.put("pageNum", pageBean.pageNum);
				dr.put("pageSize", pageBean.getPageSize());
				dr.put("listData", pageBean.getPage());
				dr.put("totalNum", pageBean.getTotalNum());
				super.fillResultContext(messageMap, dr);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserAction>>>>>queryUserList>>>",e);
		}
		return messageMap;
	} 
	/**
	 * 添加或修改用户信息
	 */
	@RequestMapping("/saveOrUpdateUser")
	@ResponseBody
	public Object saveOrUpdateUser(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("roleId");
			if (val.validate()) {
				if (StringHelper.isEmpty(info.getString("plasmaId"))) {
					info.put("plasmaId", null);
				}
				userService.saveOrUpdateUser(info,messageMap);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserAction>>>>>saveOrUpdateUser>>>",e);
		}
		return messageMap;
	}
	/**
	 * 删除指定用户信息
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Object deleteUser(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				int result =userService.deleteUser(info);
				if (result>0) {
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
				}else{
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserAction>>>>>deleteUser>>>",e);
		}
		return messageMap;
	}
	/**
	 * 查询指定用户信息
	 */
	@RequestMapping("/queryByIdUser")
	@ResponseBody
	public Object queryByIdUser(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				DataRow dr = userService.queryByIdUser(info);
				super.fillResultContext(messageMap, dr);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserAction>>>>>deleteUser>>>",e);
		}
		return messageMap;
	}
}
