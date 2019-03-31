package com.fadl.propagandist.dao;

import com.fadl.common.DataRow;
import com.fadl.propagandist.entity.GroupArea;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 组号 区域关联 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
public interface GroupAreaMapper extends BaseMapper<GroupArea> {
	/**
	 * 小组区域关联设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	public List<DataRow> groupAreaList(Map<String,String> map,Pagination pagination)throws Exception;
}
