package com.fadl.immuneAssay.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.ImmuneControl;
import com.fadl.immuneAssay.service.ImmuneControlService;

/**
 * <p>
 * 免疫针次控制设置 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-17
 */
@Controller
@RequestMapping("/immuneControl")
public class ImmuneControlController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ImmuneControlController.class);
	
	@Autowired
	ImmuneControlService immuneControlService;
	 /**
     * 跳转免疫针次控制设置页面
     */
    @RequestMapping("/immuneControlPage")
    public String immuneControlPage() {
        return "/immune/config/immune_control";
    }
	 /**
     * 跳转免疫针次控制设置添加页面
     */
    @RequestMapping("/immuneControlAddPage")
    public String immuneControlAddPage() {
        return "/immune/config/immune_control_add";
    }
    /**
	 * 跳转免疫针次控制设置修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/immuneControlUpdate")
    public String immuneControlUpdate(String id, Model model) {
    	try {
    		ImmuneControl immune = new ImmuneControl();
    		immune = immuneControlService.selectById(id);
    		model.addAttribute("immune",immune);
		} catch (Exception e) {
			logger.error("ImmuneControlController>>>>>>>>>immuneControlUpdate>>>>>",e);
		}
        return "/immune/config/immune_control_update";
    }
    /**
	 * 免疫针次控制设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/immuneControlList", method= RequestMethod.POST)
	@InvokeLog(name = "immuneControlList", description = "免疫针次控制设置列表")
	@ResponseBody
	public DataRow immuneControlList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			immuneControlService.immuneControlList(paging,map);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneControlController>>>>>>>>>>>>>immuneControlList",e);
		}
		return messageMap;
	}
	/**
	 * 新增免疫针次控制设置
	 * @param vaccine
	 * @return
	 */
	@RequestMapping(value = "/insertimmuneControl", method= RequestMethod.POST)
	@InvokeLog(name = "insertimmuneControl", description = "新增免疫针次控制设置")
	@ResponseBody
	public DataRow insertimmuneControl(ImmuneControl immune) {
		try {
			immuneControlService.insertimmuneControl(immune,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneControlController>>>>>>>>>>>>>insertimmuneControl",e);
		}
		return messageMap;
	}
	/**
	 * 修改免疫针次控制设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/updateimmuneControl", method= RequestMethod.POST)
	@InvokeLog(name = "updateimmuneControl", description = "修改免疫针次控制设置")
	@ResponseBody
	public DataRow updateimmuneControl(ImmuneControl immune) {
		try {
			immuneControlService.updateimmuneControl(immune,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneControlController>>>>>>>>>>>>>updateimmuneControl",e);
		}
		return messageMap;
	}
	/**
	 * 删除免疫针次控制设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteimmuneControl", method= RequestMethod.POST)
	@InvokeLog(name = "deleteimmuneControl", description = "删除免疫针次控制设置")
	@ResponseBody
	public DataRow deleteimmuneControl(Long ids) {
		try {
			immuneControlService.deleteimmuneControl(ids,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneControlController>>>>>>>>>>>>>deleteimmuneControl",e);
		}
		return messageMap;
	}
}

