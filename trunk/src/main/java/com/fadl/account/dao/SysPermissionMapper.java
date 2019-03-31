package com.fadl.account.dao;

import com.fadl.account.entity.SysPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 查询权限URL
     * @return
     */
    public List<SysPermission> querySysPermissionList()throws SQLException;

    /**
     * 根据名称查询菜单
     */
    public List<SysPermission> queryByName(SysPermission sysPermission)throws SQLException;

    /**
     * 根据ID查询菜单是否有子级
     */
    public List<SysPermission> queryByIdMenuList(Long id) throws SQLException;
    /**
     * 查询所有查单及按钮 用以授权
     */
    public List<Map<String,Object>> queryPermissionList(Long id)throws SQLException;

}
