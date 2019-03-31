package com.fadl.immuneAssay.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.OrdinaryStrengthenInjection;

/**
 * <p>
 * 注射管理-普免加强针注射 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface OrdinaryStrengthenInjectionMapper extends BaseMapper<OrdinaryStrengthenInjection> {
	
	/**
	 * 普免加强针注射  未注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> notShotOrdStrengthen(String updateDate, Pagination pagination) throws SQLException;
	
	/**
	 * 普免加强针注射  已注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> haveShotOrdStrengthen(String updateDate, Pagination pagination) throws SQLException;

	/**
	 * 普免加强针  头部内容
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow ordinaryStrengthenHead (Map<String, Object> map) throws SQLException;
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryVaccineInfoStreng (String immune) throws SQLException;
	/**
	 * 获取加强免疫所有类别
	 * @param id
	 * @return
	 */
	public DataRow getBaseImmuneStreng(String id) throws SQLException;
	/**
	 * 啊健(查询普免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public OrdinaryStrengthenInjection queryStrengInjectionInfo(String providerNo) throws SQLException;
}
