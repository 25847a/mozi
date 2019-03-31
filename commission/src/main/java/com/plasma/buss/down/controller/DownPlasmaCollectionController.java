package com.plasma.buss.down.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.collection.service.PlasmaCollectionService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;

/**
 * 接收采浆数据
 * @author hu
 *
 */
@Controller
@Scope("prototype")
public class DownPlasmaCollectionController extends BaseController{

	private static final Logger log = LogManager.getLogger(DownPlasmaCollectionController.class);
	
	@Autowired
	public PlasmaCollectionService plasmaCollectionService;
	
	/**
	 * 查询最后同步时间
	 * @return
	 */
	@RequestMapping("/queryPlasmaCollectionMaxDate")
	@ResponseBody
	public DataRow queryPlasmaCollectionMaxDate(){
		try {
			DataRow row = plasmaCollectionService.queryPlasmaCollectionMaxDate(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
			messageMap.put(IConstants.APP_RESULT_DATA, row);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaCollectionController>>>>>>>queryPlasmaCollectionMaxDate",e);
		}
		return messageMap;
	}
	

	/**
	 * 接收采浆数据
	 * @return
	 */
	@RequestMapping("/downPlasmaCollection")
	@ResponseBody
	public DataRow downPlasmaCollection(){
		try {
			plasmaCollectionService.savePlasmCollection(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaCollectionController>>>>>>>downPlasmaCollection",e);
		}
		return messageMap;
	}
}
