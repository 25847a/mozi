package com.fadl.check.controller;

import java.util.Date;
import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.check.entity.Check;
import com.fadl.check.service.CheckService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 体检记录表 前端控制器 (新浆员)
 * </p>
 *
 * @author wangjing
 * @since 2017-03-12
 */
@Controller
@RequestMapping("/newCheck")
public class NewCheckController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(CheckController.class);

	@Autowired
	public CheckService checkService;


	/**
	 * 跳转新献浆员体检页面
	 * 
	 * @return
	 */
	@RequestMapping("/newCheckList")
	@RequiresPermissions("newCheck:view")
	public String newCheckList() {
		return "/check/new_check_list";
	}

	/**
	 * 查询已体检人员
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryCheckList", method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckList", description = "查询已体检人员")
	@ResponseBody
	@RequiresPermissions("newCheck:view")
	public DataRow queryCheckList(@RequestParam HashMap<String, String> data, Integer page, Integer limit) {
		try {
			Page<DataRow> p = new Page<DataRow>(page, limit);
			data.put("isCheck", "1");
			if (StringHelper.isEmpty(data.get("date"))) {
				data.put("date", DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN));
			}
			data.put("isNew", "0");
			checkService.queryCheckList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckList", e);
		}
		return messageMap;
	}

	/**
	 * 查询未体检人员
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryNotCheckList", method = RequestMethod.POST)
	@InvokeLog(name = "queryNotCheckList", description = "查询未体检人员")
	@ResponseBody
	@RequiresPermissions("newCheck:view")
	public DataRow queryNotCheckList(@RequestParam HashMap<String, String> data, Integer page, Integer limit) {
		try {
			Page<DataRow> p = new Page<DataRow>(page, limit);
			data.put("isCheck", "0");
			if (StringHelper.isEmpty(data.get("date"))) {
				data.put("date", DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN));
			}
			data.put("isNew", "0");
			checkService.queryCheckList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckList", e);
		}
		return messageMap;
	}

	/**
	 * 保存或更新体检信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateChcekInfo", method = RequestMethod.POST)
	@InvokeLog(name = "updateChcekInfo", description = "保存或更新体检信息")
	@ResponseBody
	@RequiresPermissions("newCheck:update")
	public DataRow updateChcekInfo(Check check,@RequestParam Integer isRoadFee) {
		try {
			// 修改状态为已体检
			check.setIsCheck(1);
			checkService.updateCheck(check,isRoadFee, messageMap);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>updateChcekInfo", e);
		}
		return messageMap;
	}

}
