package com.fadl.propagandist.service;

import com.fadl.common.DataRow;
import com.fadl.propagandist.entity.Group;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 组号表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
public interface GroupService extends IService<Group> {
	/**
	 * 小组设置列表
	 * @param group
	 * @param page
	 * @throws Exception
	 */
	public void groupList(Group group,Page<Group> page)throws Exception;
	/**
	 * 新增小组设置
	 * @param group
	 * @throws Exception
	 */
	public void insertGroup(Group group,DataRow messageMap)throws Exception;
	/**
	 * 修改小组设置
	 * @param group
	 * @throws Exception
	 */
	public void updateGroup(Group group,DataRow messageMap)throws Exception;
	/**
	 * 删除小组设置
	 * @param group
	 * @throws Exception
	 */
	public void deleteGroup(Long id,DataRow messageMap)throws Exception;
}
