package com.plasma.buss.down.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.assay.service.NewCardService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;

/**
 * 接收化验数据
 * @author hu
 *
 */
@Controller
@Scope("prototype")
public class DownNewCardController extends BaseController{

	private static final Logger log = LogManager.getLogger(DownNewCardController.class);
	
	@Autowired
	public NewCardService newCardService;
	
	/**
	 * 查询最后同步时间
	 * @return
	 */
	@RequestMapping("/queryNewCardMaxDate")
	@ResponseBody
	public DataRow queryNewCardMaxDate(){
		try {
			DataRow row = newCardService.queryNewCardMaxDate(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
			messageMap.put(IConstants.APP_RESULT_DATA, row);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownNewCardController>>>>>>>queryNewCardMaxDate",e);
		}
		return messageMap;
	}
	
	/**
	 * 接收化验数据
	 * @return
	 */
	@RequestMapping("/downAssay")
	@ResponseBody
	public DataRow downAssay(){
		try {
			newCardService.saveNewCard(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownNewCardController>>>>>>>downAssay",e);
		}
		return messageMap;
	}
}
