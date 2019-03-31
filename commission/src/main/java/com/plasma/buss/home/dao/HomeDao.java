package com.plasma.buss.home.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

public interface HomeDao {

	/**
	 * 查询用户权限下面的菜单
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryUserMenu(DataRow data)throws SQLException;
}
