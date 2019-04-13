package com.fadl.health.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fadl.common.AbstractController;

/**
 * <p>
 * 设备项目版本表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/versionhistory")
public class VersionhistoryController extends AbstractController{

	
	private static Logger logger = LoggerFactory.getLogger(VersionhistoryController.class);
	/**
     * 版本管理页面
     * @return
     */
    @RequestMapping("/versionPage")
    public String versionPage(){
    	return "/backstage/version";
    }
}

