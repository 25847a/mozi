package com.plasma.buss.plasma.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 浆员临时表
 * 
 * @author fadl
 *
 */
public interface ProviderBaseinfoTempDao {

	public List<DataRow> queryProviderBaseinfoTempList(DataRow data) throws SQLException;

	public int queryProviderBaseinfoTempListCount(DataRow data) throws SQLException;

	public DataRow queryProviderBaseinfoTemp(DataRow data) throws SQLException;

	public void saveProviderBaseinfoTemp(DataRow data) throws SQLException;

	public int updateProviderBaseinfoTempById(DataRow data) throws SQLException;

	public int deleteProviderBaseinfoTempById(Long id) throws SQLException;
	
	/**
	 * 更新浆员状态
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updatePlasmaStatus(DataRow data)throws SQLException;
	
	/**
	 * 根据条件查询浆员信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaTempInfo(DataRow data)throws SQLException;
	
	/**
	 * 查询临时表浆员详情
	 * @return
	 * @throws Exception
	 */
	public DataRow queryBaseTempInfo(DataRow data)throws SQLException;
	
	/**
	 * 批量审核浆员
	 * @return
	 * @throws SQLException
	 */
	public int updateTempByProvider(DataRow data)throws SQLException;
}
