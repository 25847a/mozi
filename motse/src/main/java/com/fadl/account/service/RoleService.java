package com.fadl.account.service;

import com.fadl.account.entity.Role;
import com.fadl.common.DataRow;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 后台页面角色表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface RoleService extends IService<Role> {

	/**
	 * 查询角色管理列表
	 * @param map
	 * @return
	 */
	public DataRow queryRoleList(DataRow messageMap)throws SQLException;
}
