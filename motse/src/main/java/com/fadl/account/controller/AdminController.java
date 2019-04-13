package com.fadl.account.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;

/**
 * <p>
 * 后台页面用户表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "/login";
	}
	/**
	 * 跳转首页页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "/index";
	}
    /**
     * 跳转欢迎界面
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "/welcome";
    }
    /**
     * 养老院页面
     * @return
     */
    @RequestMapping("/beadhousePage")
    public String beadhousePage(){
    	return "/home/beadhouse";
    }
    /**
	 * 跳转代理商页面
	 * @return
	 */
	@RequestMapping("/agentPage")
	public String agentPage(){
		return "/backstage/agent";
	}
	/**
	 * 跳转用户管理页面
	 * @return
	 */
	@RequestMapping("/adminPage")
	public String health(){
		return "/admin/admin";
	}
    
    
    
    @RequestMapping("/verifyRegister")
    @RequiresPermissions("admin:add")
    @ResponseBody
    public DataRow verifyRegister(){
    	try {
			System.out.println("111111111111111111111111111");
	//		WebSocketServer.sendInfo(message);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<verifyRegister",e);
		}
		return messageMap;
    }
    
    
    @RequestMapping("/readPlasmaProviderNo")
    @RequiresPermissions("admin:query")
    @ResponseBody
    public DataRow readPlasmaProviderNo(){
    	try {
			System.out.println("111111111111111111111111111");
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<readPlasmaProviderNo",e);
		}
		return messageMap;
    }
}

