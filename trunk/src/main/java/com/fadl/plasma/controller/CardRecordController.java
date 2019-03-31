package com.fadl.plasma.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.plasma.service.CardRecordService;

/**
 * <p>
 * 发卡记录表 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-30
 */
@Controller
@RequestMapping("/cardRecord")
public class CardRecordController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(CardRecordController.class);
	
	@Autowired
	private CardRecordService cardRecordService;
	
	/**
	 * 获取浆员发卡记录数
	 * @param id
	 * @return
	 */
	@RequestMapping("/countCardRecord")
	@ResponseBody
	public DataRow countCardRecord(String id) {
		try {
			Integer count = cardRecordService.countCardRecord(id);
			messageMap.initSuccess(count);
		} catch (Exception e) {
			logger.error("CardRecordController>>>>>>>>>countCardRecord>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
}

