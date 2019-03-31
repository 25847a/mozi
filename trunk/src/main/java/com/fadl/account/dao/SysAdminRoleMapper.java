package com.fadl.account.dao;

import com.fadl.account.entity.SysAdminRole;
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
public interface SysAdminRoleMapper extends BaseMapper<SysAdminRole> {
    /**
     * 查询用户角色关联表
     * @param adminList
     * @return
     * @throws SQLException
     */
    public List<SysAdminRole> queryAdminRoleList(List<Long> adminList)throws SQLException;
    /**
     * 查询用户角色关联表
     * @param roleId
     * @return
     * @throws SQLException
     */
    public List<Long> queryAdminRoleByRoleIdList(Long roleId)throws SQLException;
}
