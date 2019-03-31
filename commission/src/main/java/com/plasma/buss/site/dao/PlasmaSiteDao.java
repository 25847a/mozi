package com.plasma.buss.site.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 浆站管理
 * 
 * @author fadl
 *
 */
public interface PlasmaSiteDao {

	public List<DataRow> queryPlasmaSiteList(DataRow data) throws SQLException;

	public int queryPlasmaSiteListCount(DataRow data) throws SQLException;

	public DataRow queryPlasmaSiteById(Long id) throws SQLException;

	public void savePlasmaSite(DataRow data) throws SQLException;

	public int updatePlasmaSiteById(DataRow data) throws SQLException;

	public int deletePlasmaSiteById(Long id) throws SQLException;
	
	/**
	 * 查询公司下面的浆站数量 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int queryPlasmaSiteCountByCompanyId(Long companyId)throws SQLException;
	/**
	 * 查询浆站列表
	 */
	public List<DataRow> queryPlasmaSiteNoPageList()throws SQLException;
}
