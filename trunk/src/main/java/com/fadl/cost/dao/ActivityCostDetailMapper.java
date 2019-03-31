package com.fadl.cost.dao;

import com.fadl.common.DataRow;
import com.fadl.cost.entity.ActivityCostDetail;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 活动费用明细 Mapper 接口
 * </p>
 *
 * @author hkk
 * @since 2018-09-14
 */
public interface ActivityCostDetailMapper extends BaseMapper<ActivityCostDetail> {

	/**
	 * 查询费用明细
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryActivityCostList(Long id)throws SQLException;
}
