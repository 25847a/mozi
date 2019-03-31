package com.fadl.cost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.cost.dao.ActivityMapper;
import com.fadl.cost.entity.Activity;
import com.fadl.cost.service.ActivityService;

/**
 * <p>
 * 活动发布表 服务实现类
 * </p>
 *
 * @author zhanll
 * @since 2018-05-11
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
	
	@Autowired
	private ActivityMapper activityMapper;
	
	/** 
	 * 活动发布表查询（列表查询或者根据活动名称进行查询）
	  */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<DataRow> queryActivity(String name,Page page) throws Exception{
		/*RowBounds bounds = new RowBounds((page.getCurrent()-1)*page.getSize(),page.getSize());
		
		List<Map<String, Object>> list = activityMapper.queryActivity(map, bounds);
		page.setRecords(list);
		int count = activityMapper.queryActivityCount(map);
		page.setTotal(count);*/
		return page.setRecords(activityMapper.queryActivity(name, page));
	};
	
	/**
	 * 费用管理---->浆站活动页面---->添加
	 * */
	public int addActivity(Activity activity) throws Exception {
		
		return activityMapper.insert(activity);
	}
	
	/**
	 * 费用管理---->浆站活动页面---->修改
	 * */
	@Transactional
	public int updateActivity(Activity activity) throws Exception {
		//先将页面修改前的活动进行逻辑删除
		activityMapper.updateActivity(activity.getId());
		//再将页面传过来的数据插入进数据库中
		return activityMapper.insert(activity);
	}
	
	/**
	 * 费用管理---->浆站活动页面---->逻辑删除
	 * */
	public int updateActivity(Long id) throws Exception {
		
		return activityMapper.updateActivity(id);
	}

}
