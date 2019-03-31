package com.plasma.buss.collection.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 采浆
 * @author hu
 *
 */
public interface PlasmaCollectionDao {

	public List<DataRow> queryPlasmCollectionList(DataRow data) throws SQLException;

	public int queryPlasmCollectionListCount(DataRow data) throws SQLException;

	public DataRow queryPlasmCollectionById(Long id) throws SQLException;

	public void savePlasmCollection(List<DataRow> data) throws SQLException;

	public int updatePlasmCollectionById(DataRow data) throws SQLException;

	public int deletePlasmCollectionById(Long id) throws SQLException;
	
	/**
	 * 查询最后同步时间
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaCollectionMaxDate(DataRow info)throws SQLException;
}
