package com.fadl.check.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fadl.check.entity.Check;
import com.fadl.check.service.CheckRepeatService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 体检记录表-重检 前端控制器
 * </p>
 *
 * @author hkk
 * @since 2018-10-17
 */
@RestController
@RequestMapping("/check")
public class CheckRepeatController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CheckRepeatController.class);
	
	@Autowired
	public CheckRepeatService checkRepeatService;

	/**
	 * 查询已体检人员 
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/saveRepeatCheck",method = RequestMethod.POST)
	@InvokeLog(name = "saveRepeatCheck", description = "保存重检记录")
	@ResponseBody
	@RequiresPermissions("check:repeat")
	public DataRow saveRepeatCheck(Check check,Integer page, Integer limit){
		try {
			checkRepeatService.saveCheckRepeat(check, messageMap);
		} catch (Exception e) {
			logger.error("CheckRepeatController>>>>>>>>>saveRepeatCheck",e);
		}
		return messageMap;
	}
}

