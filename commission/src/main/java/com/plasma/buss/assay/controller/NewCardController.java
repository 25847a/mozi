package com.plasma.buss.assay.controller;

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
 * 新老卡化验
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class NewCardController extends BaseController{

	private static final Logger log = LogManager.getLogger(NewCardController.class);
	
	@Autowired
	public NewCardService newCardService;
	
	/**
	 * 跳转新老卡化验界面
	 * @return
	 */
	@RequestMapping("/newCard")
	public String newCard(){
		return "assay/new_card_list";
	}
	
	/**
	 * 查询新老卡化验列表
	 * @return
	 */
	@RequestMapping("/queryNewCardList")
	@ResponseBody
	public DataRow queryNewCardList(){
		try {
			pageBean.setPageNum(info.get("pageNum"));
			pageBean.setPageSize(info.getInt("pageSize"));
			pageBean = newCardService.queryNewCardList(info,pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("NewCardController>>>>>>>queryNewCardList",e);
		}
		return messageMap;
	}
}
