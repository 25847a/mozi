package com.fadl.cost.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.cost.entity.Activity;

/**
 * <p>
 * 活动发布表 服务类
 * </p>
 *
 * @author zhanll
 * @since 2018-05-11
 */
public interface ActivityService extends IService<Activity> {
	
	
	/** 活动发布表查询（列表查询或者根据活动名称进行查询）*/
	public Page<DataRow> queryActivity(String name,@SuppressWarnings("rawtypes") Page page) throws Exception;
		
	/**
	 * 费用管理---->浆站活动页面---->添加
	 * */
	public int addActivity(Activity activity) throws Exception;
	
	/**
	 * 费用管理---->浆站活动页面---->修改
	 * */
	public int updateActivity(Activity activity)throws Exception;
	
	/**
	 * 费用管理---->浆站活动页面---->逻辑删除
	 * */
	public int updateActivity(Long id)throws Exception;
	
}
