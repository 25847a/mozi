package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.PrivilegeStrengthenInjection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 注射管理-特免加强针注射 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface PrivilegeStrengthenInjectionMapper extends BaseMapper<PrivilegeStrengthenInjection> {

	
	/**
	 * 特免加强针注射  未注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> notShotSpeStrengthen (String updateDate, Pagination pagination) throws SQLException;
	
	/**
	 * 特免加强针注射  已注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> haveShotSpeStrengthen (String updateDate,Pagination pagination) throws SQLException;
	
	/**
	 * 特免加强针  头部内容
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow specialStrengthenHead (Map<String, Object> map) throws SQLException;
	/**
	 * 啊健(查询特免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public PrivilegeStrengthenInjection queryStrengPrivilegeInfo(String providerNo) throws SQLException;
}
