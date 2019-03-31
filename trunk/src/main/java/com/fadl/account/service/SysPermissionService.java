package com.fadl.account.service;

import com.fadl.account.entity.SysPermission;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 查询权限URL
     * @return
     */
    public List<SysPermission> querySysPermissionList()throws Exception;

    /**
     * 新增菜单
     * @param sysPermission 菜单信息
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void insertMenu(SysPermission sysPermission, DataRow messageMap)throws Exception;
    /**
     * 修改菜单
     * @param sysPermission 菜单信息
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void updateByIdSysRole(SysPermission sysPermission, DataRow messageMap)throws Exception;
    /**
     * 删除菜单
     * @param id 菜单id
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void deleteMenu(Long id, DataRow messageMap)throws Exception;

    /**
     * 查询所有查单及按钮 用以授权
     */
    public List<Map<String,Object>> queryPermissionList(Long id)throws Exception;
}
