package com.fadl.account.dao;

import com.fadl.account.entity.SysRolePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-04-23
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
    /**
     * 根据role查询权限
     */
    public List<String> queryRolePermissionList(List<Long> list)throws SQLException;
    /**
     * 角色权限关联变更
     * @param list 权限关联集合
     * @throws Exception
     */
    public void insertRolePermission(List<SysRolePermission> list)throws SQLException;
}
