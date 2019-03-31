package com.plasma.buss.check.controller;

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
 * 体检记录
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class CheckController extends BaseController{

	private static final Logger log = LogManager.getLogger(CheckController.class);
	
	@Autowired
	public CheckService checkService;
	
	/**
	 * 跳转体检页面
	 * @return
	 */
	@RequestMapping("/check")
	public String check(){
		return "check/check_list";
	}
	
	/**
	 * 查询体检列表数据
	 * @return
	 */
	@RequestMapping("/queryCheckList")
	@ResponseBody
	public DataRow queryCheckList(){
		try {
			pageBean.setPageNum(info.get("pageNum"));
			pageBean.setPageSize(info.getInt("pageSize"));
			pageBean = checkService.queryCheckList(info, pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("CheckController>>>>>>>queryCheckList",e);
		}
		return messageMap;
	}
}
