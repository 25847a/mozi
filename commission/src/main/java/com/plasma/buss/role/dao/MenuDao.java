package com.plasma.buss.role.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 菜单管理
 * @author hu
 *
 */
public interface MenuDao {
	/**
	 * 查询全部菜单信息
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> menuAllList() throws SQLException;
	/**
	 * 查询分页菜单列表
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryMenuList(DataRow data) throws SQLException;
	/**
	 * 查询菜单列表总数
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int queryMenuListCount(DataRow data) throws SQLException;
	/**
	 * 查询菜单对象
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryMenuById(Long id) throws SQLException;
	/**
	 * 添加菜单信息
	 * @param data
	 * @throws SQLException
	 */
	public void saveMenu(DataRow data) throws SQLException;
	/**
	 * 修改菜单信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateMenuById(DataRow data) throws SQLException;
	/**
	 * 删除菜单信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int deleteMenuById(Long id) throws SQLException;
	public int deleteRoleMenuById(Long id) throws SQLException;

}
