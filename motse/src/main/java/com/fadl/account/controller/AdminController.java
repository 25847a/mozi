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
     * 加载左导航
     * @return
     */
    @RequestMapping("/leftNavigation")
    public String leftNavigation() {
        return "/business/busblood/busines";
    }
    /**
     * 登记信息查询页面
     * @return
     */
    @RequestMapping("/registration")
    public String regisinformation() {
        return "/business/register/registration";
    }
    
    
    
    @RequestMapping("/verifyRegister")
    @RequiresPermissions("admin:add")
    @ResponseBody
    public DataRow verifyRegister(){
    	try {
			System.out.println("111111111111111111111111111");
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

