package com.fadl.cost.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.cost.entity.Activity;

/**
 * <p>
 * 活动发布表 Mapper 接口
 * </p>
 *
 * @author zhanll
 * @since 2018-05-11
 */
public interface ActivityMapper extends BaseMapper<Activity> {
	
	/** 活动发布表查询（列表查询或者根据活动名称进行查询）*/
	public List<Map<String, Object>> queryActivity(String name,Pagination pagination) throws SQLException;
	
	/**删除活动（逻辑删除）*/
	public int updateActivity(Long id)throws SQLException;
	
}
