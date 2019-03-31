package com.fadl.account.controller;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.SysAdminRole;
import com.fadl.account.entity.SysRole;
import com.fadl.account.service.AdminService;
import com.fadl.account.service.SysAdminRoleService;
import com.fadl.account.service.SysRoleService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 用户操作
 * </p>
 *
 * @author wangjing
 * @since 2018-04-02
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminService adminService;
    @Autowired
    SysAdminRoleService sysAdminRoleService;
    @Autowired
    SysRoleService sysRoleService;
    /**
     * 跳转主页
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
    	Session session = SecurityUtils.getSubject().getSession();
    	Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
    	model.addAttribute("admin", admin.getName());
        return "/index";
    }
    /**
     * 跳转权限页面
     * @return
     */
    @RequestMapping("/powerPage")
    public String adminPower() {
        return "/user/power_page";
    }
    /**
     * 跳转用户列表页面
     */
    @RequestMapping("/adminList")
    @RequiresPermissions("admin:view")
    public String adminUserList() {
        return "/user/admin_list";
    }
    /**
     * 跳转修改密码页面
     * @param model
     * @return
     */
    @RequestMapping("/updatePasswordPage")
    public String updatePasswordPage(Model model) {
    	Session session = SecurityUtils.getSubject().getSession();
    	Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
    	model.addAttribute("admin", admin);
		return "/user/password_update";
    }
    /**
     * 查询用户详情
     */
    @RequestMapping("/queryAdminDetails")
    @InvokeLog(name = "queryAdminDetails", description = "查询用户详情")
    @RequiresPermissions("admin:view")
    public String adminDetails(String id, Model model) {
        try {
            Admin admin =adminService.selectById(id);
            EntityWrapper ew = new EntityWrapper();
            ew.setEntity(new SysAdminRole());
            ew.eq("adminId",id);
            SysAdminRole sar = sysAdminRoleService.selectOne(ew);
            if(admin!=null&&sar!=null){
                admin.setRoleId(sar.getRoleId());
            }
            model.addAttribute("admin",admin);
        }catch (Exception e){
            logger.error("AdminController>>>>>>>>>adminDetails>>>>>",e);
        }
        return "/user/admin_details";
    }
    /**
     * 跳转用户添加页面
     */
    @RequestMapping("/adminAddPage")
    @RequiresPermissions("admin:add")
    public String adminAddPage() {
        return "/user/admin_add";
    }
    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value="/queryAdminList",method= RequestMethod.POST)
    @InvokeLog(name = "queryAdminList", description = "查询用户列表")
    @ResponseBody
    @RequiresPermissions("admin:view")
    public DataRow queryAdminList(@RequestParam HashMap<String,Object> map, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = adminService.queryAdminList(dr, map);
            messageMap.initPage(dr);
        } catch (Exception e) {
            logger.error("AdminController>>>>>>>>>queryAdminList>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 添加用户信息
     * @param file 文件
     * @param admin 用户对象
     * @return
     */
    @RequestMapping(value = "/insertAdmin",method = RequestMethod.POST)
    @InvokeLog(name = "insertAdmin", description = "添加用户信息")
    @ResponseBody
    @RequiresPermissions("admin:add")
    public DataRow insertAdmin(MultipartFile file,Admin admin) {
    	try {
    		adminService.insertAdmin(file, admin,messageMap);
		} catch (Exception e) {
			  logger.error("AdminController>>>>>>>>>insertAdmin>>>>>",e);
		}
		return messageMap;
    }
    /**
     * 修改用户信息
     * @param file 文件流
     * @param admin 用户信息
     * @return
     */
    @RequestMapping(value = "/updateAdmin",method = RequestMethod.POST)
    @InvokeLog(name = "updateAdmin", description = "修改用户信息")
    @ResponseBody
    @RequiresPermissions("admin:update")
    public DataRow updateAdmin(MultipartFile file,Admin admin) {
        try {
            adminService.updateAdmin(file, admin,messageMap);
        }catch (Exception e){
            logger.error("AdminController>>>>>>>>>>>>>>updateAdmin>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 删除 用户信息
     * @param ids 逻辑删除
     * @return
     */
    @RequestMapping(value = "/deleteAdmin")
    @InvokeLog(name = "deleteAdmin", description = "删除用户信息")
    @ResponseBody
    @RequiresPermissions("admin:del")
    public DataRow deleteAdmin(@RequestParam List<Long> ids){
        try {
             adminService.deleteAdmin(ids,messageMap);
        }catch (Exception e){
            logger.error("AdminController>>>>>>>>>>>>>>deleteAdmin>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 修改密码
     * @param admin
     * @return
     */
    @RequestMapping(value="/updatePassword")
    @ResponseBody
    public DataRow updatePassword(Admin admin) {
    	try {
            adminService.updatePassword(admin,messageMap);
       }catch (Exception e){
           logger.error("AdminController>>>>>>>>>>>>>>updatePassword>>>>>>>>>>>",e);
       }
       return messageMap;
    }
}
