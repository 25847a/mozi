package cn.mozistar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.mozistar.service.PushRecordService;
import cn.mozistar.util.DataRow;
import cn.mozistar.util.ResultData;

@Controller
@RequestMapping(value = "/pushRecord")
public class PushRecordController {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PushRecordService pushRecordService;
	
		/**
		 * 获取预警历史记录
		 * @param m
		 * @return
		 */
		@RequestMapping(value = "/queryPushRecord")
		@ResponseBody
		public ResultData<DataRow> queryPushRecordInfo(@RequestBody DataRow map) {
			ResultData<DataRow> re= new ResultData<DataRow>();
			try {
				re = pushRecordService.queryPushRecordInfo(map,re);
			} catch (Exception e) {
				logger.error("PushRecordController>>>>>>>>>>>>>>>>>>queryPushRecordInfo",e);
			}
			return re;
		}	
}
