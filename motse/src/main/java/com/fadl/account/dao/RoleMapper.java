package com.fadl.account.dao;

import com.fadl.account.entity.Role;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 后台页面角色表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 查询角色列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryRoleList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询角色列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryRoleListCount(Map<String,Object> map)throws SQLException;
}
