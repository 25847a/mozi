package com.plasma.buss.check.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 体检
 * @author hu
 *
 */
public interface CheckDao {

	public List<DataRow> queryCheckList(DataRow data) throws SQLException;

	public int queryCheckListCount(DataRow data) throws SQLException;

	public DataRow queryCheckById(Long id) throws SQLException;

	public void saveCheck(List<DataRow> data) throws SQLException;

	public int updateCheckById(DataRow data) throws SQLException;

	public int deleteCheckById(Long id) throws SQLException;
	
	/**
	 * 查询最后体检时间 
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryCheckMaxDate(DataRow info)throws SQLException;

}
