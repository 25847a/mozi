package com.fadl.supplies.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.fadl.supplies.entity.Depot;
import com.fadl.supplies.service.DepotService;

/**
 * <p>
 * 仓库信息表 前端控制器
 * </p>
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/depot")
public class DepotController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(DepotController.class);
	
	@Autowired
	DepotService depotService;
	
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping("/depot")
	@RequiresPermissions("depot:view")
    public String depot() {
        return "/supplies/config/depot";
    }
    /**
     * 跳转仓库添加页面
     */
    @RequestMapping("/depotAddPage")
    public String depotAddPage() {
        return "/supplies/config/depot_add";
    }
	/**
	 * 跳转仓库修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/depotUpdate")
    public String depotDetails(String id, Model model) {
    	try {
    		Depot depot = new Depot();
    		depot = depotService.selectById(id);
    		model.addAttribute("depot",depot);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>depotDetails>>>>>",e);
		}
        return "/supplies/config/depot_update";
    }
	/**
	 * 查询仓库列表
	 * @param depot
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryDepotList" , method= RequestMethod.POST)
	@InvokeLog(name = "queryDepotList", description = "查询仓库列表")
	@ResponseBody
	public DataRow queryDepotList(Depot depot, Integer page, Integer limit) {
		Page<Depot> paging = null;
		EntityWrapper<Depot> ew = new EntityWrapper<Depot>();
		ew.like("name", depot.getName(), SqlLike.DEFAULT);
		ew.eq("isDelete", 0);
		try {
			paging = new Page<Depot>(page, limit);
			paging = depotService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>>>>>queryDepotList",e);
		}
		return messageMap;
	}
	/**
	 * 新增仓库信息
	 * @param depot
	 * @return
	 */
	@RequestMapping(value = "/insertDepot" , method= RequestMethod.POST)
	@InvokeLog(name = "insertDepot", description = "新增仓库信息")
	@ResponseBody
	@RequiresPermissions("depot:add")
	public DataRow insertDepot(Depot depot) {
		try {
			depotService.insertDepot(depot,messageMap);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>>>>>insertDepot",e);
		}
		return messageMap;
	}
	/**
	 * 修改仓库信息
	 * @param depot
	 * @return
	 */
	@RequestMapping(value = "/updateDepot" , method= RequestMethod.POST)
	@InvokeLog(name = "updateDepot", description = "修改仓库信息")
	@ResponseBody
	@RequiresPermissions("depot:update")
	public DataRow updateDepot(Depot depot) {
		try {
			depotService.updateDepot(depot,messageMap);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>>>>>updateDepot",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除仓库信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteDepot" , method= RequestMethod.POST)
	@InvokeLog(name = "deleteDepot", description = "删除仓库信息")
	@ResponseBody
	@RequiresPermissions("depot:delect")
	public DataRow deleteDepot(Long ids) {
		try {
			depotService.deleteDepot(ids,messageMap);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>>>>>deleteDepot",e);
		}
		return messageMap;
	}
	/**
	 * 查询仓库信息
	 * @return
	 */
	@RequestMapping(value="/queryDepotInfo" , method= RequestMethod.POST)
	@InvokeLog(name = "queryDepotInfo", description = "查询仓库信息")
	@ResponseBody
	public DataRow queryDepotInfo() {
		EntityWrapper<Depot> ew = new EntityWrapper<Depot>();
		try {
			ew.eq("isDelete", 0);
			List<Depot> list =depotService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("DepotController>>>>>>>>>>>>>deleteDepot",e);
		}
		return messageMap;
	}
}
