package com.fadl.account.dao;

import com.fadl.account.entity.AdminRole;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 后台用户---角色关联表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
	/**
	 * 查询用户、角色关联信息
	 * @return
	 * @throws SQLException
	 */
	public List<AdminRole> queryAdminRoleInfo(Long id)throws SQLException;
}
