package com.fadl.system.dao;

import com.fadl.common.DataRow;
import com.fadl.system.entity.BedNumber;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 床位表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
public interface BedNumberMapper extends BaseMapper<BedNumber> {

	/**
	 * 查询床位列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryBedList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询床位列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryBedListCount(Map<String,Object> map)throws SQLException;
}
