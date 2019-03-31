package com.fadl.common;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fadl.refuseInfo.service.RefuseInfoService;

@Component
@EnableScheduling
public class TimedTask {
	private static Logger logger = LoggerFactory.getLogger(TimedTask.class);
	@Autowired
	RefuseInfoService refuseInfoService;
	
	@Scheduled(cron="0 0 6 * * ?")
	public void scheduled() {
		try {
			System.out.println("每天上午6点执行自动淘汰超龄浆员>>>>>>>>>>>>>>>>>>>>>>>>>>>啊健"+DateUtil.sf.format(new Date()));
			refuseInfoService.queryEliminateYearOld();
		} catch (Exception e) {
			logger.error("<<<<<<<<<<<<",e);
			
		}
		
	}
	
}

