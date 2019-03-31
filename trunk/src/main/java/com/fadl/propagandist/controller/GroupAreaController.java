package com.fadl.propagandist.controller;

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
import com.fadl.propagandist.entity.GroupArea;
import com.fadl.propagandist.service.GroupAreaService;

/**
 * <p>
 * 组号 区域关联 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
@Controller
@RequestMapping("/groupArea")
public class GroupAreaController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	@Autowired
	GroupAreaService groupAreaService;
	/**
     * 跳转小组设置页面
     */
    @RequestMapping("/groupAreaPage")
    public String groupAreaPage() {
        return "/collectionConfig/groupArea";
    }
	 /**
     * 跳转小组设置添加页面
     */
    @RequestMapping("/groupAreaAddPage")
    public String groupAreaAddPage() {
        return "/collectionConfig/groupArea_add";
    }
    /**
	 * 跳转小组设置修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/groupAreaUpdate")
    public String groupAreaUpdate(String id, Model model) {
    	try {
    		GroupArea groupArea = new GroupArea();
    		groupArea = groupAreaService.selectById(id);
    		model.addAttribute("groupArea",groupArea);
		} catch (Exception e) {
			logger.error("GroupAreaController>>>>>>>>>groupAreaUpdate>>>>>",e);
		}
        return "/collectionConfig/groupArea_update";
    }
    /**
	 * 小组区域关联设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/groupAreaList", method= RequestMethod.POST)
	@InvokeLog(name = "groupAreaList", description = "小组区域关联设置列表")
	@ResponseBody
	public DataRow groupAreaList(@RequestParam Map<String,String> map, Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			groupAreaService.groupAreaList(map,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("GroupAreaController>>>>>>>>>>>>>groupAreaList",e);
		}
		return messageMap;
	}
	/**
	 * 新增小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	@RequestMapping(value = "/insertgroupArea", method= RequestMethod.POST)
	@InvokeLog(name = "insertgroupArea", description = "新增小组区域关联设置")
	@ResponseBody
	public DataRow insertgroupArea(GroupArea groupArea) {
		try {
			groupAreaService.insertgroupArea(groupArea,messageMap);
		} catch (Exception e) {
			logger.error("GroupAreaController>>>>>>>>>>>>>insertgroupArea",e);
		}
		return messageMap;
	}
	/**
	 * 修改小组区域关联设置
	 * @param groupArea
	 * @return
	 */
	@RequestMapping(value = "/updategroupArea", method= RequestMethod.POST)
	@InvokeLog(name = "updategroupArea", description = "修改小组区域关联设置")
	@ResponseBody
	public DataRow updategroupArea(GroupArea groupArea) {
		try {
			groupAreaService.updategroupArea(groupArea,messageMap);
		} catch (Exception e) {
			logger.error("GroupAreaController>>>>>>>>>>>>>updategroupArea",e);
		}
		return messageMap;
	}
	/**
	 * 删除小组区域关联设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deletegroupArea", method= RequestMethod.POST)
	@InvokeLog(name = "deletegroupArea", description = "删除小组区域关联设置")
	@ResponseBody
	public DataRow deletegroupArea(Long ids) {
		try {
			groupAreaService.deletegroupArea(ids,messageMap);
		} catch (Exception e) {
			logger.error("GroupAreaController>>>>>>>>>>>>>deletegroupArea",e);
		}
		return messageMap;
	}
}

