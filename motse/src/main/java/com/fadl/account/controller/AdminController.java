package com.fadl.account.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.entity.Admin;
import com.fadl.account.service.AdminService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.SessionUtil;
import com.fadl.common.WebSocketServer;

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
	
	@Autowired
	AdminService adminService;
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
    @RequiresPermissions("beadhouse:view")
    public String beadhousePage(Model model){
    	Session session = SecurityUtils.getSubject().getSession();
    	Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
    	model.addAttribute("adminId", admin.getId());
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
    
    
    /**
     * 查询代理商列表
     * @return
     */
    @RequestMapping("/queryAdminList")
    @ResponseBody
    public DataRow verifyRegister(){
    	try {
    		messageMap = adminService.queryAdminList(messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<queryAdminList",e);
		}
		return messageMap;
    }
}

