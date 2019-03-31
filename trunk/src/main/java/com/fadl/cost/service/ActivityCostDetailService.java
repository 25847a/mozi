package com.fadl.cost.service;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.cost.entity.ActivityCostDetail;

/**
 * <p>
 * 活动费用明细 服务类
 * </p>
 *
 * @author hkk
 * @since 2018-09-14
 */
public interface ActivityCostDetailService extends IService<ActivityCostDetail> {

	/**
	 * 查询费用明细
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryActivityCostList(Long id)throws Exception;
}
