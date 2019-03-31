package com.plasma.buss.plasma.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.plasma.service.ProviderBaseinfoTempService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;

/**
 * 临时表
 * @author fadl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class ProviderBaseinfoTempController extends BaseController{

	private static final Logger log = LogManager.getLogger(ProviderBaseinfoTempController.class);
	
	@Autowired
	public ProviderBaseinfoTempService providerBaseinfoTempService;
	
	/**
	 * 跳转取消发卡页面
	 * @return
	 */
	@RequestMapping("cancelCardList")
	public String cancelCardList(){
		return "plasma/cancel_card_list";
	}
	
	/**
	 * 取消发卡数据列表
	 * @return
	 */
	@RequestMapping("/queryCancelCardList")
	@ResponseBody
	public DataRow queryCancelCardList(){
		try {
			pageBean = providerBaseinfoTempService.queryCancelCardList(info, pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>queryCancelCardList",e);
		}
		return messageMap;
	}
	
	/**
	 * 批量更新
	 * @return
	 */
	@RequestMapping("/examinePlasmaUpdate")
	@ResponseBody
	public DataRow examinePlasmaUpdate(){
		try {
			providerBaseinfoTempService.examinePlasmaUpdate(info);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"浆员审核中");
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>examinePlasmaUpdate",e);
		}
		return messageMap;
	}
	
}
