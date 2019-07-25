package cn.mozistar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.mozistar.service.MessageService;
import cn.mozistar.util.DataRow;
import cn.mozistar.util.ResultData;

@Controller
@RequestMapping(value = "/message")
public class MessageController {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MessageService messageService;
	
		/**
		 * 获取预警历史记录
		 * @param m
		 * @return
		 */
		@RequestMapping(value = "/queryMessage")
		@ResponseBody
		public ResultData<DataRow> queryMessage(@RequestBody DataRow map) {
			ResultData<DataRow> re= new ResultData<DataRow>();
			try {
				re = messageService.queryMessage(map,re);
			} catch (Exception e) {
				logger.error("MessageController>>>>>>>>>>>>>>>>>>queryMessage",e);
			}
			return re;
		}	
}
