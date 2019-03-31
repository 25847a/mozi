package com.fadl.immuneAssay.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.Control;
import com.fadl.immuneAssay.service.ControlService;

/**
 * <p>
 * 免疫针次控制设置————控制名称表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-16
 */
@Controller
@RequestMapping("/control")
public class ControlController extends AbstractController{
	
private static Logger logger = LoggerFactory.getLogger(ControlController.class);
	
	@Autowired
	ControlService controlService;
	 /**
     * 跳转控制名称表页面
     */
    @RequestMapping("/controlPage")
    public String controlPage() {
        return "/immune/config/control";
    }
	 /**
     * 跳转控制名称表添加页面
     */
    @RequestMapping("/controlAddPage")
    public String controlAddPage() {
        return "/immune/config/control_add";
    }
    /**
	 * 跳转控制名称表修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/controlUpdate")
    public String controlUpdate(String id, Model model) {
    	try {
    		Control control = new Control();
    		control = controlService.selectById(id);
    		model.addAttribute("control",control);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>controlUpdate>>>>>",e);
		}
        return "/immune/config/control_update";
    }
	/**
	 * 控制名称表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/controlList", method= RequestMethod.POST)
	@InvokeLog(name = "controlList", description = "免疫控制名称表")
	@ResponseBody
	public DataRow controlList(Control control, Integer page, Integer limit) {
		Page<Control> paging = null;
		try {
			paging = new Page<Control>(page, limit);
			EntityWrapper<Control> ew = new EntityWrapper<Control>();
			ew.like("controlName",control.getControlName(), SqlLike.DEFAULT);
			controlService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>>>>>controlList",e);
		}
		return messageMap;
	}
	/**
	 * 新增控制名称表
	 * @param vaccine
	 * @return
	 */
	@RequestMapping(value = "/insertControl", method= RequestMethod.POST)
	@InvokeLog(name = "insertControl", description = "新增控制名称表")
	@ResponseBody
	public DataRow insertControl(Control control) {
		try {
			controlService.insertControl(control,messageMap);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>>>>>insertControl",e);
		}
		return messageMap;
	}
	/**
	 * 修改控制名称表
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/updateControl", method= RequestMethod.POST)
	@InvokeLog(name = "updateControl", description = "修改控制名称表")
	@ResponseBody
	public DataRow updateControl(Control control) {
		try {
			controlService.updateControl(control,messageMap);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>>>>>updateControl",e);
		}
		return messageMap;
	}
	/**
	 * 删除控制名称表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteControl", method= RequestMethod.POST)
	@InvokeLog(name = "deleteControl", description = "删除控制名称表")
	@ResponseBody
	public DataRow deleteControl(Long ids) {
		try {
			controlService.deleteControl(ids,messageMap);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>>>>>deleteControl",e);
		}
		return messageMap;
	}
	/**
	 * 控制名称表信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryControlInfo", method= RequestMethod.POST)
	@InvokeLog(name = "queryControlInfo", description = "控制名称表信息")
	@ResponseBody
	public DataRow queryControlInfo() {
		try {
			List<Control> list=controlService.selectList(null);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("ControlController>>>>>>>>>>>>>queryControlInfo",e);
		}
		return messageMap;
	}
}

