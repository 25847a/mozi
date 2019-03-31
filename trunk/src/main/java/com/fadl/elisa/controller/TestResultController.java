package com.fadl.elisa.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fadl.common.AbstractController;

/**
 * <p>
 * 测试结果 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-10-20
 */
@Controller
@RequestMapping("/testResult")
public class TestResultController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(TestResultController.class);
	
	/**
	 * 跳转全自动生化检测页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:bio:view")
	@RequestMapping("/initBioPage")
	public String initBioPage() {
		return "/business/elisa/a_kong";
	}
	
	
}

