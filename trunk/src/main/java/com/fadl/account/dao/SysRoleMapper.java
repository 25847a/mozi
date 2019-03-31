package com.fadl.account.dao;

import com.fadl.account.entity.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据角色名称查询角色信息
     * @param name
     * @return
     */
    public SysRole queryByName(String name)throws SQLException;
    /**
     * 查询所有菜单及按钮 用以授权
     */
    public List<Map<String,Object>> queryPermissionList(Long id)throws SQLException;
}
