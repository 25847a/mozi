package com.fadl.propagandist.service.impl;

import com.fadl.propagandist.entity.GroupArea;
import com.fadl.common.DataRow;
import com.fadl.propagandist.dao.GroupAreaMapper;
import com.fadl.propagandist.service.GroupAreaService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组号 区域关联 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
@Service
public class GroupAreaServiceImpl extends ServiceImpl<GroupAreaMapper, GroupArea> implements GroupAreaService {
	@Autowired
	GroupAreaMapper groupAreaMapper;

	/**
	 * 小组区域关联设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public void groupAreaList(Map<String, String> map, Page<DataRow> page) throws Exception {
		page.setRecords(groupAreaMapper.groupAreaList(map, page));
	}
	/**
	 * 新增小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	@Override
	public void insertgroupArea(GroupArea groupArea, DataRow messageMap) throws Exception {
		int row = groupAreaMapper.insert(groupArea);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	@Override
	public void updategroupArea(GroupArea groupArea, DataRow messageMap) throws Exception {
		int row = groupAreaMapper.updateById(groupArea);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除小组区域关联设置
	 * @param id
	 * @return
	 */
	@Override
	public void deletegroupArea(Long id, DataRow messageMap) throws Exception {
		int row = groupAreaMapper.deleteById(id);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
