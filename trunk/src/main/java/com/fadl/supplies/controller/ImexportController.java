package com.fadl.supplies.controller;

 
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.service.ImexportService;

/**
 * <p>
 * 库存进出表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
@Controller
@RequestMapping("/imexport")
public class ImexportController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ImexportController.class);
	
	@Autowired
	ImexportService imexportService;
	
	//跳转耗材使用记录页面
		@RequestMapping("/usedQueryPage")
		@RequiresPermissions("imexport:view")
		public String usedQueryPage() {
			return "/supplies/used/usedQuery";
		}
		/**
		 * 查询出入库流水表列表
		 * @param map
		 * @param page
		 * @param limit
		 * @return
		 */
		@RequestMapping(value="/queryUsedList")
		@InvokeLog(name = "queryUsedList", description = "查询出入库流水表列表")
		@ResponseBody
		@RequiresPermissions("imexport:view")
		public DataRow queryUsedList(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
			Page<DataRow> pageing = null;
	        try {
	        	pageing = new Page<DataRow>(page,limit);
	        	imexportService.queryUsedList(map, pageing);
	            messageMap.initPage(pageing);
	        } catch (Exception e) {
	            logger.error("ImexportController>>>>>>>>>queryUsedList>>>>>",e);
	        }
	        return messageMap;
		}
		
}

