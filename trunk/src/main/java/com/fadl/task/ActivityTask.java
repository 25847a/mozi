package com.fadl.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.common.DateUtil;
import com.fadl.cost.entity.Activity;
import com.fadl.cost.service.ActivityService;

/**
 * 定时关闭到期活动
 * @author fadl
 *
 */
@Component
public class ActivityTask {
	
	private static Logger logger = LoggerFactory.getLogger(ActivityTask.class);

	@Autowired
	public ActivityService activityService;
	
	/**
	 * 更新活动状态
	 */
	@Scheduled(cron="0 30 23 * * ?")
	public void updateStatus(){
		try {
			EntityWrapper<Activity> ew = new EntityWrapper<Activity>();
			ew.eq("isDelete", 0);
			List<Activity> list = activityService.selectList(ew);
			for (Activity activity : list) {
				long day = DateUtil.daysBetween(activity.getStartDate(), activity.getEndDate());
				if (day<1) {
					activity.setIsDelete(1);
					activityService.updateById(activity);
				}
			}
		} catch (Exception e) {
			logger.error("ActivityTask>>>>>>>>updateStatus>>>>>",e);
		}
	}
}
