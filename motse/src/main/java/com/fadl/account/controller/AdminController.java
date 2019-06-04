package com.fadl.account.controller;


import java.sql.SQLException;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.AdminRole;
import com.fadl.account.service.AdminService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.SessionUtil;
import com.fadl.health.entity.User;
import com.fadl.health.service.UserService;

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
	@Autowired
	UserService userService;
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
	public String index(Model model){
		Session session = SecurityUtils.getSubject().getSession();
		Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
		try {
			DataRow row=adminService.queryAdminAgentInfo(admin.getId());
			model.addAttribute("agentName", row.getString("agentName"));
			model.addAttribute("adminName", row.getString("name"));
			model.addAttribute("avatar", row.getString("avatar"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
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
     * @throws Exception 
     */
    @RequestMapping("/beadhousePage")
    @RequiresPermissions("beadhouse:view")
    public String beadhousePage(Model model) throws Exception{
    	Admin admin = SessionUtil.getSessionAdmin();
    	EntityWrapper<User> ew = new EntityWrapper<User>();
    	ew.eq("role", "使用者");
    	int count =userService.queryUserCount(admin.getId());
    	model.addAttribute("adminId", admin.getId());
    	model.addAttribute("count", count);
    	return "/home/beadhouse";
    }
    /**
     * 用户分布图页面
     * @return
     * @throws Exception 
     */
    @RequestMapping("/distributionPage")
    @RequiresPermissions("map:view")
    public String distributionPage(){
    	return "/home/distribution";
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
	public String adminPage(){
		return "/admin/admin";
	}
	/**
	 * 跳转机构信息页面
	 * @return
	 */
	@RequestMapping("/adminInfoPage")
	public String adminInfoPage(){
		return "/department/department";
	}
    /**
     * 查询用户管理列表
     * @return
     */
    @RequestMapping("/queryAdminInfoList")
    @ResponseBody
    public DataRow queryAdminInfoList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap = adminService.queryAdminInfoList(map,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<queryAdminInfoList",e);
		}
		return messageMap;
    }
    /**
     * 新增用户信息
     * @return
     */
    @RequestMapping("/addAdminInfo")
    @ResponseBody
    public DataRow addRoleInfo(Admin admin,Long roleId){
    	try {
    		messageMap = adminService.addAdminInfo(admin,roleId,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<addRoleInfo",e);
		}
		return messageMap;
    }
    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping("/updateAdminInfo")
    @ResponseBody
    public DataRow updateRoleInfo(Admin admin,AdminRole adminRole){
    	try {
    		messageMap = adminService.updateAdminInfo(admin,adminRole,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<updateAdminInfo",e);
		}
		return messageMap;
    }
    /**
     * 删除用户信息
     * @return
     */
    @RequestMapping("/deleteAdminInfo")
    @ResponseBody
    public DataRow deleteRoleInfo(Admin admin){
    	try {
    		messageMap = adminService.deleteAdminInfo(admin,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<deleteAdminInfo",e);
		}
		return messageMap;
    };
    
    /**
     * 修改供应商的头像
     * avatar
     * @return
     */
    @RequestMapping("/updateAgentImage")
    @ResponseBody
    public DataRow updateAgentImage(String avatar){
    	try {
    		Admin admin = SessionUtil.getSessionAdmin();
    		admin.setAvatar(avatar);
    		messageMap =  adminService.updateAgentImage(admin,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<updateAgentImage",e);
		}
		return messageMap;
    }
    /**
     * 修改供应商的密码
     * map
     * @return
     */
    @RequestMapping("/updateAgentPassword")
    @ResponseBody
    public DataRow updateAgentPassword(@RequestParam Map<String,String> map){
    	try {
    		Admin admin = SessionUtil.getSessionAdmin();
    		messageMap =  adminService.updateAgentPassword(admin,map,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<updateAgentPassword",e);
		}
		return messageMap;
    }
	/**
	 * 查询用户要修改的信息
	 * @return
	 */
	@RequestMapping("/queryupdateAdminInfo")
	@ResponseBody
	public DataRow queryupdateAdminInfo(Long id){
		try {
			messageMap = adminService.queryupdateAdminInfo(id,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<queryupdateAdminInfo",e);
		}
		return messageMap;
	}
}