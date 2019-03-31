package com.fadl.propagandist.dao;

import com.fadl.propagandist.entity.Group;
import java.util.List;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 组号表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
public interface GroupMapper extends BaseMapper<Group> {
	/**
	 * 小组设置列表
	 * @param group
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<Group> groupList(Group group,Pagination pagination)throws Exception;
}
