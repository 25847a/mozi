package com.fadl.account.controller;
import com.fadl.account.service.SysRolePermissionService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController extends AbstractController {
    private static Logger logger = LoggerFactory.getLogger(SysRolePermissionController.class);
    @Autowired
    SysRolePermissionService sysRolePermissionService;
    /**
     * 角色权限关联变更
     * @return
     */
    @RequestMapping(value = "/insertRolePermission")
    @InvokeLog(name = "insertRolePermission", description = "角色权限关联变更")
    @ResponseBody
    @RequiresPermissions("permission:add")
    public DataRow insertRolePermission(@RequestParam List<Long> permissionId, Long roleId){
        try {
            sysRolePermissionService.insertRolePermission(permissionId,roleId);
            messageMap.initSuccess();
        }catch (Exception e){
            logger.error("SysRolePermissionController>>>>>>>>>>>>>>insertRolePermission>>>>>>>>>>>", e);
        }
        return messageMap;
    }
}

