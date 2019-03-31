package com.fadl.account.service;

import com.fadl.account.entity.AdminRole;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 后台用户---角色关联表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AdminRoleService extends IService<AdminRole> {

	
	/**
	 * 查询用户、角色关联信息
	 * @return
	 * @throws SQLException
	 */
	public List<AdminRole> queryAdminRoleInfo(Long id)throws SQLException;
}
