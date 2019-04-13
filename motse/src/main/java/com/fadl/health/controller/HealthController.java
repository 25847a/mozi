package com.fadl.health.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fadl.account.controller.AdminController;
import com.fadl.common.AbstractController;

/**
 * <p>
 * 惊凡给的数据表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/health")
public class HealthController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	
	/**
	 * 跳转历史健康数据页面
	 * @return
	 */
	@RequestMapping("/historyPage")
	public String historyPage(){
		return "/health/history";
	}
	/**
	 * 跳转重点关爱管理页面
	 * @return
	 */
	@RequestMapping("/lovePage")
	public String lovePage(){
		return "/health/love";
	}
	/**
	 * 跳转健康数据管理页面
	 * @return
	 */
	@RequestMapping("/healthPage")
	public String healthPage(){
		return "/health/health";
	}
}

