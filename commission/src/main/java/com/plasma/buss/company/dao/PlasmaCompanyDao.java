package com.plasma.buss.company.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 公司管理
 * @author fadl
 *
 */
public interface PlasmaCompanyDao {

	public List<DataRow> queryPlasmaCompanyList(DataRow data) throws SQLException;

	public int queryPlasmaCompanyListCount(DataRow data) throws SQLException;

	public DataRow queryPlasmaCompanyById(Long id) throws SQLException;

	public void savePlasmaCompany(DataRow data) throws SQLException;

	public int updatePlasmaCompanyById(DataRow data) throws SQLException;

	public int deletePlasmaCompanyById(Long id) throws SQLException;
}
