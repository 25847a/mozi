package com.fadl.account.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fadl.common.AbstractController;

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
	
	/**
	 * 跳转菜单页面
	 * @return
	 */
	@RequestMapping("/authPage")
	public String login(){
		return "/admin/menu";
	}
}

