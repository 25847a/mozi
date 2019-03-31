package com.fadl.propagandist.service;

import com.fadl.common.DataRow;
import com.fadl.propagandist.entity.GroupArea;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 组号 区域关联 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
public interface GroupAreaService extends IService<GroupArea> {
	/**
	 * 小组区域关联设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	public void groupAreaList(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 新增小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	public void insertgroupArea(GroupArea groupArea,DataRow messageMap)throws Exception;
	/**
	 * 修改小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	public void updategroupArea(GroupArea groupArea,DataRow messageMap)throws Exception;
	/**
	 * 删除小组区域关联设置
	 * @param id
	 * @return
	 */
	public void deletegroupArea(Long id,DataRow messageMap)throws Exception;
}
