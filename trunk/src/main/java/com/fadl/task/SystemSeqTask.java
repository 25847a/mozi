package com.fadl.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.service.AdminService;
import com.fadl.common.DateUtil;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;

/**
 * 每天定时初始化序号
 * @author hu
 *
 */
@Component
public class SystemSeqTask {

	private static Logger logger = LoggerFactory.getLogger(SystemSeqTask.class); 
	
	@Autowired
	public SystemSeqService systemSeqService;
	
	@Autowired
    public AdminService adminService; 
	
	/**
	 * 更新序列号
	 */
	@Scheduled(cron="0 30 01 * * ?")
    public void updateSeq() {
		try {
			logger.info("执行序列号定时任务开始》》》》》》》》》》》》》");
            EntityWrapper<SystemSeq> ew = new EntityWrapper<SystemSeq>();
            ew.eq("isInit", 0);
			List<SystemSeq> list = systemSeqService.selectList(ew);
			if (null!=list && list.size()>0) {
				String time = DateUtil.formatDate(new Date(), DateUtil.yyyyMMdd)+"0000";
				for (SystemSeq systemSeq : list) {
					systemSeq.setValue(Long.valueOf(time));
					int res = systemSeqService.updateSystemById(systemSeq);
					if (res<1) {
						logger.error("更新序列号出错");
					}
				}
			}
			logger.info("执行序列号定时任务结束》》》》》》》》》》》》》");
		} catch (Exception e) {
			logger.error("SystemSeqTask>>>>>>>>updateSeq>>>>>",e);
		}
	}
}
