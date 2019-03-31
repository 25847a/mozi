package com.fadl.cost.controller;


import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.cost.entity.Activity;
import com.fadl.cost.service.ActivityService;


/**
 * <p>
 * 活动发布表 前端控制器
 * </p>
 *
 * @author zhanll
 * @since 2018-05-11
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 费用管理---->浆站活动页面
	 * */
	@RequestMapping("/plasmaActivity")
	@RequiresPermissions("activity:view")
	public String toPlasmaActivity() {
		return "/cost/activity_cost_list";
	}
	
	/**
	 * 费用管理---->浆站活动页面---->查询
	 * */
	@RequestMapping("/queryActity")
	@InvokeLog(name = "queryActity", description = "活动查询")
	@ResponseBody
	@RequiresPermissions("activity:view")
	public DataRow queryActity(@Param("name") String name,Integer page,Integer limit) {
		Page<DataRow> page2 = new Page<>(page,limit);
		try {
			activityService.queryActivity(name, page2);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("ActivityController>>>>>>>>>>queryActity>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理---->浆站活动页面---->添加页面
	 * */
	
	@RequestMapping("/toAddActivity")
	@RequiresPermissions("activity:add")
	public String toAddActivity() {
		return "/cost/activity_add";
	}
	/**
	 * 费用管理---->浆站活动页面---->添加
	 * */
	@RequestMapping("/addActivity")
	@InvokeLog(name = "addActivity", description = "活动添加")
	@ResponseBody
	@RequiresPermissions("activity:add")
	public DataRow addActivity(Activity activity) {
		try {
			if("".equals(activity.getStartDate())) {
				activity.setStartDate(null);
			}
			if("".equals(activity.getEndDate())) {
				activity.setEndDate(null);
			}
			if(activityService.addActivity(activity) > 0) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("ActivityController>>>>>>>>>>addActivity>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理---->浆站活动页面---->修改页面
	 * */
	@RequestMapping("/toUpdateActivity")
	@RequiresPermissions("activity:update")
	public String toUpdateActivity(String id,Model model) {
		try {
			//先根据id查询出活动信息
			Activity activity =  activityService.selectById(id);
			model.addAttribute("activity", activity);
		} catch (Exception e) {
			logger.error("ActivityController>>>>>>>>>>toUpdateActivity>>>>>>",e);
		}
		
		return "/cost/activity_details";
	}
	
	/**
	 * 费用管理---->浆站活动页面---->修改
	 * */
	@RequestMapping("/updateActivity")
	@InvokeLog(name = "updateActivity", description = "活动修改")
	@ResponseBody
	@RequiresPermissions("activity:update")
	public DataRow updateActivity(Activity activity) {
		
		try {
			if(activityService.updateActivity(activity) > 0) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("ActivityController>>>>>>>>>>addActivity>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理---->浆站活动页面---->逻辑删除
	 * */
	@RequestMapping("/deleteActity")
	@InvokeLog(name = "deleteActity", description = "活动删除")
	@ResponseBody
	@RequiresPermissions("activity:delete")
	public DataRow deleteActity(Long id){
		try {
			if(activityService.updateActivity(id) > 0) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("ActivityController>>>>>>>>>>deleteActity>>>>>>",e);
		}
		return messageMap;
	}
	
}

