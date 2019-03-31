package com.fadl.propagandist.service.impl;

import com.fadl.propagandist.entity.Group;
import com.fadl.common.DataRow;
import com.fadl.propagandist.dao.GroupMapper;
import com.fadl.propagandist.service.GroupService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组号表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

	@Autowired
	GroupMapper groupMapper;
	/**
	 * 小组设置列表
	 * @param group
	 * @param page
	 * @throws Exception
	 */
	@Override
	public void groupList(Group group, Page<Group> page) throws Exception {
		page.setRecords(groupMapper.groupList(group, page));
	}
	/**
	 * 新增小组设置
	 * @param group
	 * @throws Exception
	 */
	@Override
	public void insertGroup(Group group,DataRow messageMap) throws Exception {
		int row = groupMapper.insert(group);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改小组设置
	 * @param group
	 * @throws Exception
	 */
	@Override
	public void updateGroup(Group group, DataRow messageMap) throws Exception {
		int row = groupMapper.updateById(group);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除小组设置
	 * @param group
	 * @throws Exception
	 */
	@Override
	public void deleteGroup(Long id, DataRow messageMap) throws Exception {
		int row = groupMapper.deleteById(id);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}

}
