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
import com.plasma.common.base.BaseController;
import com.plasma.common.util.MD5Util;
import com.plasma.common.util.Validator;

@Controller
@Scope("prototype")
public class UserLoginController extends BaseController{
	
	private static final Logger log = LogManager.getLogger(UserLoginController.class);

	@Autowired
	public UserService userService;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public DataRow userLogin(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("userName");
			val.addParamCheck("passWord");
			if (val.validate()) {
				info.put("passWord", MD5Util.Md5ByKey(info.getString("passWord")));
				DataRow user = userService.queryUserLogin(info);
				if (null!=user) {
					setSessionUser(user);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"登录成功");
				}else{
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, "用户名或密码错误");
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserLoginAction>>>>>>>userLogin",e);
		}
		return messageMap;
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping("/exit")
	@ResponseBody
	public DataRow exit(){
		try {
			request.getSession().removeAttribute(IConstants.SESSION_USER);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"删除成功");
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserLoginController>>>>>>>exit",e);
		}
		return messageMap;
	}
}
