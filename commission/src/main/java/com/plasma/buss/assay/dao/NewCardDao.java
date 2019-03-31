package com.plasma.buss.assay.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 新老卡化验
 * @author hu
 *
 */
public interface NewCardDao {

	public List<DataRow> queryNewCardList(DataRow data) throws SQLException;

	public int queryNewCardListCount(DataRow data) throws SQLException;

	public DataRow queryNewCardById(Long id) throws SQLException;

	public void saveNewCard(List<DataRow> list) throws SQLException;

	public int updateNewCardById(DataRow data) throws SQLException;

	public int deleteNewCardById(Long id) throws SQLException;
	
	/**
	 * 查询最后同步时间
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryNewCardMaxDate(DataRow info)throws SQLException;
}
