package com.fadl.account.service;

import com.fadl.account.entity.SysAdminRole;
import com.baomidou.mybatisplus.service.IService;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-23
 */
public interface SysAdminRoleService extends IService<SysAdminRole> {
    /**
     * 查询用户角色关联表
     * @param list
     * @return
     * @throws SQLException
     */
    public List<SysAdminRole> queryAdminRoleList(List<Long> list)throws Exception;
}
