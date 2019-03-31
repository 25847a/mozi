package com.fadl.account.service;

import com.fadl.account.entity.SysRole;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 添加角色信息
     * @param sysRole 角色对象
     * @param messageMap 信息返回
     * @throws SQLException
     */
    public void insertSysRole(SysRole sysRole,DataRow messageMap) throws Exception;

    /**
     * 修改角色信息
     * @param sysRole 角色信息
     * @throws SQLException
     */
    public void updateByIdSysRole(SysRole sysRole,DataRow messageMap)throws Exception;

    /**
     * 删除角色信息
     * @param ids 角色ID
     * @param messageMap
     * @throws Exception
     */
    public void deleteRole(List<Long> ids,DataRow messageMap) throws Exception;
}
