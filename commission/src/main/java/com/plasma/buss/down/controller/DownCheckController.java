package com.plasma.buss.down.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.check.service.CheckService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;

/**
 * 接收体检数据
 * @author hu
 *
 */
@Controller
@Scope("prototype")
public class DownCheckController extends BaseController{

	private static final Logger log = LogManager.getLogger(DownCheckController.class);
	@Autowired
	public CheckService checkService;
	
	/**
	 * 查询最后同步时间
	 * @return
	 */
	@RequestMapping("/queryCheckMaxDate")
	@ResponseBody
	public DataRow queryCheckMaxDate(){
		try {
			DataRow row = checkService.queryCheckMaxDate(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
			messageMap.put(IConstants.APP_RESULT_DATA, row);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownCheckController>>>>>>>queryCheckMaxDate",e);
		}
		return messageMap;
	}
	
	/**
	 * 接收化验数据
	 * @return
	 */
	@RequestMapping("/downCheck")
	@ResponseBody
	public DataRow downCheck(){
		try {
			checkService.saveCheck(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownCheckController>>>>>>>downCheck",e);
		}
		return messageMap;
	} 
}
