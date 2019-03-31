package com.plasma.buss.home.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plasma.buss.home.dao.HomeDao;
import com.plasma.common.DataRow;

@Service
public class HomeService {

	@Autowired
	public HomeDao homeDao;
	
	/**
	 * 查询用户权限下面的菜单
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryUserMenu(DataRow data)throws SQLException{
		return homeDao.queryUserMenu(data);
	}
}
