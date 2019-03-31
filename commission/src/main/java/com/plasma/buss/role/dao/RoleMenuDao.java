package com.plasma.buss.role.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 角色菜单关联
 * 
 * @author hu
 *
 */
public interface RoleMenuDao {

	public List<DataRow> queryRoleMenuList(DataRow data) throws SQLException;

	public int queryRoleMenuListCount(DataRow data) throws SQLException;

	public DataRow queryRoleMenuById(Long id) throws SQLException;

	public void saveRoleMenu(List<DataRow> list) throws SQLException;

	public int updateRoleMenuById(DataRow data) throws SQLException;

	public int deleteRoleMenuById(Long id) throws SQLException;
	
	/**
	 * 删除角色关联的菜单权限
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public int deleteRoleMenuByRoleId(Long roleId)throws SQLException;
	
	/**
	 * 查询某个角色的权限 
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryRoleMenuListByRoleId(Long roleId)throws SQLException;
	
	/**
	 * 查询授权菜单总数
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public int queryRoleMenuCount(Long roleId)throws SQLException;

}
