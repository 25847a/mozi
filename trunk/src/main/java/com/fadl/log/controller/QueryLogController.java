package com.fadl.log.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 操作日志查询 前端控制器
 * </p>
 *
 * @author guokang
 * @since 2018-07-24
 */
@Controller
@RequestMapping("/log")
public class QueryLogController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(QueryLogController.class);
	
	@Autowired
	LogService logService;
	
	/**
	 * 跳转用户操作日志查询页面
	 * @return
	 */
	@RequestMapping("/log")
	//@RequiresPermissions("log:view")
	public String log() {
		return "/user/query_log";
	}
	
	/**
	 * 用户操作日志查询列表
	 * @param log
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryLog",method= RequestMethod.POST)
	@InvokeLog(name = "queryLog", description = "用户操作日志查询")
	@ResponseBody
	//@RequiresPermissions("log:view")
	public DataRow queryLog(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
		Page<DataRow> pageing = null;
        try {
        	pageing = new Page<DataRow>(page,limit);
        	logService.queryLog(map, pageing);
            messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("QueryLogController>>>>>>>>>queryLog>>>>>",e);
        }
        return messageMap;
		
	}
	
}

