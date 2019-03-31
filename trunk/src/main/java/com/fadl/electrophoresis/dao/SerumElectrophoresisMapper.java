package com.fadl.electrophoresis.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.electrophoresis.entity.SerumElectrophoresis;

/**
 * <p>
 *  血清电泳 Mapper 接口
 * </p>
 *
 * @author hu
 * @since 2018-06-06
 */
public interface SerumElectrophoresisMapper extends BaseMapper<SerumElectrophoresis> {

	/**
	 * 查询血清电泳列表
	 * @param page
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	
	
	public List<DataRow> querySerumElectrophoresisList(Pagination page,HashMap<String, String> map)throws SQLException;
	/**
	 * 根据更新时间查找蛋白电泳记录,包含浆员基础信息
	 * @param electrophoresis
	 * @return
	 */
	List<DataRow> selectByUpdateDate(SerumElectrophoresis electrophoresis);
}
