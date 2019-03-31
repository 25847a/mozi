package com.fadl.cost.service.impl;

import com.fadl.cost.entity.ActivityCostDetail;
import com.fadl.common.DataRow;
import com.fadl.cost.dao.ActivityCostDetailMapper;
import com.fadl.cost.service.ActivityCostDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动费用明细 服务实现类
 * </p>
 *
 * @author hkk
 * @since 2018-09-14
 */
@Service
public class ActivityCostDetailServiceImpl extends ServiceImpl<ActivityCostDetailMapper, ActivityCostDetail> implements ActivityCostDetailService {

	@Autowired
	public ActivityCostDetailMapper activityCostDetailMapper;
	
	/**
	 * 查询费用明细
	 */
	@Override
	public List<DataRow> queryActivityCostList(Long id) throws Exception {
		return activityCostDetailMapper.queryActivityCostList(id);
	}

}
