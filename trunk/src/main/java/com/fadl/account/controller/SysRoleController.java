package com.fadl.account.controller;


import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.SysRole;
import com.fadl.account.service.SysRoleService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    @Autowired
    SysRoleService sysRoleService;
    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping(value="/queryRoleList",method= RequestMethod.POST)
    @InvokeLog(name = "queryRoleList", description = "查询角色列表")
    @ResponseBody
    public DataRow queryRoleList(){
        try {
            List<SysRole> list =sysRoleService.selectList(null);
            messageMap.initSuccess(list);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>queryRoleList>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 跳转角色列表
     */
    @RequestMapping("/rolePage")
    @RequiresPermissions("role:view")
    public String rolePage() {
        return "/user/role_list";
    }
    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping(value="/queryRoleListPage",method= RequestMethod.POST)
    @InvokeLog(name = "queryRoleListPage", description = "查询角色列表")
    @ResponseBody
    @RequiresPermissions("role:view")
    public DataRow queryRoleListPage(SysRole sysRole, Integer page, Integer limit){
        try {
            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new SysRole());
            ew.like("name",sysRole.getName(), SqlLike.DEFAULT);
            Page p = new Page(page,limit);
            p =sysRoleService.selectPage(p,ew);
            messageMap.initPage(p);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>queryRoleListPage>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 跳转角色新增
     */
    @RequestMapping("/roleAddPage")
    @RequiresPermissions("role:add")
    public String roleAddPage() {
        return "/user/role_add";
    }
    /**
     * 新增角色信息
     * @return
     */
    @RequestMapping(value="/insertRole",method= RequestMethod.POST)
    @InvokeLog(name = "insertRole", description = "新增角色信息")
    @ResponseBody
    @RequiresPermissions("role:add")
    public DataRow insertRole(SysRole sysRole){
        try {
            sysRoleService.insertSysRole(sysRole,messageMap);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>insertRole>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 查询角色详情
     */
    @RequestMapping("/queryRoleDetails")
    @InvokeLog(name = "queryRoleDetails", description = "查询角色详情")
    @RequiresPermissions("role:view")
    public String queryRoleDetails(String id, Model model) {
        try {
            SysRole sysRole =sysRoleService.selectById(id);
            model.addAttribute("sysRole",sysRole);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>queryRoleDetails>>>>>",e);
        }
        return "/user/role_details";
    }

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     * @return
     */
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    @InvokeLog(name = "updateRole", description = "修改角色信息")
    @ResponseBody
    @RequiresPermissions("role:update")
    public DataRow updateRole(SysRole sysRole) {
        try {
            sysRoleService.updateByIdSysRole(sysRole,messageMap);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>>>>>>updateRole>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 删除 角色信息
     * @param ids 逻辑删除
     * @return
     */
    @RequestMapping(value = "/deleteRole")
    @InvokeLog(name = "deleteRole", description = "删除 角色信息")
    @ResponseBody
    @RequiresPermissions("role:del")
    public DataRow deleteRole(@RequestParam List<Long> ids){
        try {
            sysRoleService.deleteRole(ids,messageMap);
        }catch (Exception e){
            logger.error("SysRoleController>>>>>>>>>>>>>>deleteRole>>>>>>>>>>>",e);
        }
        return messageMap;
    }
}

