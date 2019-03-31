package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 注射管理-特免基础针注射 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface PrivilegeInjectionMapper extends BaseMapper<PrivilegeInjection> {

	/**
	 * 特免基础针注射  未注射
	 * @param page 
	 * */
	public List<DataRow> notShotSpeBase (String updateDate, Pagination pagination) throws SQLException;
	
	/**
	 * 特免基础针注射  已注射
	 * @param page 
	 * */
	public List<DataRow> haveShotSpeBase (String updateDate, Pagination pagination) throws SQLException;
	
	/**
	 * 特免基础针注射  头部内容
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow specialBaseHead(Map<String, Object> map) throws SQLException;
	
	/**
	 * 啊健(查询特免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public PrivilegeInjection queryPrivilegeInfo(String providerNo) throws SQLException;
	
	
}
