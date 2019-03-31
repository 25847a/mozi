package com.fadl.supplies.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.service.DetailedService;
 
/**
 * <p>
 * 耗材模板明细 前端控制器
 * </p>
 * @author 啊健
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/detailed")
public class DetailedController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(DepotController.class);
	
	@Autowired
	DetailedService detailedService;
	//跳转模块详情页面
		@RequestMapping("/templateDatelis")
		@RequiresPermissions("template:details")
		public String templateDatelis(String id,Model model) {
			model.addAttribute("id", id);
			return "/supplies/used/used_datelis";
		}
		/**
		 * 查询详情页面列表
		 * @param String
		 * @param page
		 * @param limit
		 * @return
		 */
		@RequestMapping(value="/queryTemplateDatelis")
		@InvokeLog(name = "queryTemplateDatelis", description = "查询耗材详情页面列表")
		@ResponseBody
		public DataRow queryTemplateDatelis(String templateId,Integer page,Integer limit) {
			Page<DataRow> pageing=null;
			try {
				pageing = new Page<DataRow>(page,limit);
				detailedService.queryTemplateDatelis(pageing,templateId);
				messageMap.initPage(pageing);
			} catch (Exception e) {
				logger.error("TemplateController>>>>>>>>>queryTemplateList",e);
			}
			return messageMap;
		}
	
}
