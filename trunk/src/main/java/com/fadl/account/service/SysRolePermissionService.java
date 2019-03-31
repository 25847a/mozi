package com.fadl.account.service;

import com.fadl.account.entity.SysRolePermission;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-23
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {
    /**
     * 根据role查询权限
     */
    public List<String> queryRolePermissionList(List<Long> list)throws Exception;
    /**
     * 角色权限关联变更
     * @param permissionId 权限编号
     * @param roleId 角色编号
     * @throws Exception
     */
    public void insertRolePermission(List<Long> permissionId, Long roleId)throws Exception;
}
