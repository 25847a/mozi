package com.plasma.buss.role.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 角色管理
 * @author hu
 *
 */
public interface UserRoleDao {

	public List<DataRow> queryRoleList(DataRow data) throws SQLException;

	public int queryRoleListCount(DataRow data) throws SQLException;
	
	/**
	 * 查询全部角色列表
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAllRoleList(DataRow dr) throws SQLException;

	public DataRow queryRoleById(Long id) throws SQLException;

	public void saveRole(DataRow data) throws SQLException;

	public int updateRoleById(DataRow data) throws SQLException;

	public int deleteRoleById(Long id) throws SQLException;

}
