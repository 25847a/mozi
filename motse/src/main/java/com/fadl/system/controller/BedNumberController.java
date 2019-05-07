package com.fadl.system.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.health.controller.HealthController;
import com.fadl.system.service.BedNumberService;

/**
 * <p>
 * 床位表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Controller
@RequestMapping("/bedNumber")
public class BedNumberController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	@Autowired
	BedNumberService bedNumberService;
	
	/**
	 * 查询床位数据
	 * @return
	 */
	@RequestMapping("/queryBedNumberInfo")
	@ResponseBody
	public DataRow queryBedNumberInfo(){
		try {
		Admin admin = SessionUtil.getSessionAdmin();
		messageMap=bedNumberService.queryBedNumberInfo(admin,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController>>>>>>>>>>>>>queryBedNumberInfo",e);
		}
		return messageMap;
	}
}

