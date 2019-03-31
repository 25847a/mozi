package com.fadl.account.controller;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.SysPermission;
import com.fadl.account.service.SysPermissionService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  菜单处理
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController  extends AbstractController {
    private static Logger logger = LoggerFactory.getLogger(SysPermissionController.class);
    @Autowired
    SysPermissionService sysPermissionService;
    /**
     * 跳转菜单列表页面
     */
    @RequestMapping("/menuPage")

    public String menuPage() {
        return "/user/menu_list";
    }

    /**
     * 查询菜单列表
     * @return
     */
    @RequestMapping(value="/querySysPermissionList",method= RequestMethod.POST)
    @InvokeLog(name = "querySysPermissionList", description = "查询菜单列表")
    @ResponseBody
    @RequiresPermissions("menu:view")
    public DataRow querySysPermissionList(SysPermission sysPermission, Integer page, Integer limit) {
        try {
            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new SysPermission());
            ew.like("name",sysPermission.getName(),SqlLike.DEFAULT);
            Page p = new Page(page,limit);
            p =sysPermissionService.selectPage(p,ew);
            messageMap.initPage(p);
        } catch (Exception e) {
            logger.error("SysPermissionController>>>>>>>>>querySysPermissionList>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 查询菜单列表
     * @return
     */
    @RequestMapping(value="/queryMenuList",method= RequestMethod.POST)
    @InvokeLog(name = "queryMenuList", description = "查询菜单列表")
    @ResponseBody
    public DataRow queryMenuList() {
        try {
            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new SysPermission());
            ew.eq("masterType","menu");
            List<SysPermission> list =sysPermissionService.selectList(ew);
            messageMap.initSuccess(list);
        } catch (Exception e) {
            logger.error("SysPermissionController>>>>>>>>>queryMenuList>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 跳转菜单新增
     */
    @RequestMapping("/menuAddPage")
    @RequiresPermissions("menu:add")
    public String roleAddPage() {
        return "/user/menu_add";
    }
    /**
     * 新增菜单信息
     * @return
     */
    @RequestMapping(value="/insertMenu",method= RequestMethod.POST)
    @InvokeLog(name = "insertMenu", description = "新增菜单信息")
    @ResponseBody
    @RequiresPermissions("menu:add")
    public DataRow insertMenu(SysPermission sysPermission){
        try {
            sysPermissionService.insertMenu(sysPermission,messageMap);
        }catch (Exception e){
            logger.error("SysPermissionController>>>>>>>>>insertMenu>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 查询菜单详情
     */
    @RequestMapping("/queryMenuDetails")
    @InvokeLog(name = "queryMenuDetails", description = "查询菜单详情")
    @RequiresPermissions("menu:view")
    public String queryMenuDetails(String id, Model model) {
        try {
            SysPermission sysPermission =sysPermissionService.selectById(id);
            model.addAttribute("sysPermission",sysPermission);
        }catch (Exception e){
            logger.error("SysPermissionController>>>>>>>>>queryMenuDetails>>>>>",e);
        }
        return "/user/menu_details";
    }

    /**
     * 修改菜单信息
     * @param sysPermission 菜单信息
     * @return
     */
    @RequestMapping(value = "/updateMenu",method = RequestMethod.POST)
    @InvokeLog(name = "updateMenu", description = "修改菜单信息")
    @ResponseBody
    @RequiresPermissions("menu:update")
    public DataRow updateMenu(SysPermission sysPermission) {
        try {
            sysPermissionService.updateByIdSysRole(sysPermission,messageMap);
        }catch (Exception e){
            logger.error("SysPermissionController>>>>>>>>>>>>>>updateMenu>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 删除 菜单信息
     * @param ids 逻辑删除   暂只支持单个删除
     * @return
     */
    @RequestMapping(value = "/deleteMenu")
    @InvokeLog(name = "deleteMenu", description = "删除菜单信息")
    @ResponseBody
    @RequiresPermissions("menu:del")
    public DataRow deleteMenu(@RequestParam List<Long> ids){
        try {
            sysPermissionService.deleteMenu(ids.get(0),messageMap);
        }catch (Exception e){
            logger.error("SysPermissionController>>>>>>>>>>>>>>deleteMenu>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 跳转菜单列表页面
     */
    @RequestMapping("/permissionPage")
    @RequiresPermissions("permission:view")
    public String permissionPage() {
        return "/user/permission_list";
    }

    /**
     * 查询树形权限，并显示角色拥有的权限
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryPermissionList")
    @InvokeLog(name = "queryPermissionList", description = "查询树形权限，并显示角色拥有的权限")
    @ResponseBody
    @RequiresPermissions("permission:view")
    public DataRow queryPermissionList(Long id){
        try {
            List<Map<String,Object>> list =sysPermissionService.queryPermissionList(id);
            messageMap.initSuccess(list);
        }catch (Exception e) {
            logger.error("SysPermissionController>>>>>>>>>>>>>>queryPermissionList>>>>>>>>>>>", e);
        }
        return messageMap;
    }
}

