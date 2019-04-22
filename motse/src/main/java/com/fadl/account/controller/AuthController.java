package com.fadl.account.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.service.AuthService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;

/**
 * <p>
 * 权限菜单表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	AuthService authService;
	/**
	 * 跳转菜单页面
	 * @return
	 */
	@RequestMapping("/authPage")
	public String login(){
		return "/admin/menu";
	}
	
	/**
	 * 查询设备数据列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryAuthList")
	@ResponseBody
	public DataRow queryAuthList(@RequestParam Map<String,String> map){
		try {
		messageMap=authService.queryAuthList(messageMap);
		} catch (Exception e) {
			logger.error("AuthController>>>>>>>>>>>>>queryAuthList",e);
		}
		return messageMap;
	}
}

