package com.fadl.account.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fadl.common.AbstractController;
/**
 * <p>
 * 后台页面角色表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 * 跳转角色管理页面
	 * @return
	 */
	@RequestMapping("/rolePage")
	public String historyPage(){
		return "/admin/role";
	}
}

