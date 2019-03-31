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
import com.fadl.supplies.entity.Unit;
import com.fadl.supplies.service.UnitService;

/**
 * <p>
 * 耗材单位 前端控制器
 * </p>
 * @author 啊健
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/unit")
public class UnitController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(UnitController.class);
	@Autowired
	UnitService unitService;
	
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping("/unit")
	@RequiresPermissions("unit:view")
    public String unit() {
        return "/supplies/config/unit";
    }
    /**
     * 跳转单位添加页面
     */
    @RequestMapping("/unitAddPage")
    public String unitAddPage() {
        return "/supplies/config/unit_add";
    }
    /**
	 * 跳转单位修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/unitUpdate")
    public String unitDetails(String id, Model model) {
    	try {
    		Unit unit = new Unit();
    		unit = unitService.selectById(id);
    		model.addAttribute("unit",unit);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>unitDetails>>>>>",e);
		}
        return "/supplies/config/unit_update";
    }
	
	/**
	 * 查询耗材单位列表
	 * @param unit
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryUnitList", method= RequestMethod.POST)
	@InvokeLog(name = "queryUnitList", description = "查询耗材单位列表")
	@ResponseBody
	public DataRow queryUnitList(Unit unit, Integer page, Integer limit) {
		Page<Unit> paging = null;
		try {
			EntityWrapper<Unit> ew = new EntityWrapper<Unit>();
			ew.like("name", unit.getName(), SqlLike.DEFAULT);
			// depot 以后可以用这个做为分页的查询条件
			ew.eq("isDelete", 0);
			paging = new Page<Unit>(page, limit);
			paging = unitService.selectPage(paging,ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>>>>>queryUnitList",e);
		}
		return messageMap;
	}
	
	/**
	 * 新增耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/insertUnit", method= RequestMethod.POST)
	@InvokeLog(name = "insertUnit", description = "新增耗材单位信息")
	@ResponseBody
	@RequiresPermissions("unit:add")
	public DataRow insertUnit(Unit unit) {
		try {
			unitService.insertUnit(unit,messageMap);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>>>>>insertUnit",e);
		}
		return messageMap;
	}

	/**
	 * 修改耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/updateUnit", method= RequestMethod.POST)
	@InvokeLog(name = "updateUnit", description = "修改耗材单位信息")
	@ResponseBody
	@RequiresPermissions("unit:update")
	public DataRow updateUnit(Unit unit) {
		try {
			unitService.updateUnit(unit,messageMap);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>>>>>updateUnit",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除耗材单位信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteUnit", method= RequestMethod.POST)
	@InvokeLog(name = "deleteUnit", description = "删除耗材单位信息")
	@ResponseBody
	@RequiresPermissions("unit:delect")
	public DataRow deleteUnit(Long ids) {
		try {
			unitService.deleteUnit(ids,messageMap);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>>>>>deleteUnit",e);
		}
		return messageMap;
	}
	/**
	 * 查询耗材单位信息
	 * @return
	 */
	@RequestMapping(value="/queryUnitInfo", method= RequestMethod.POST)
	@InvokeLog(name = "queryUnitInfo", description = "查询耗材单位信息")
	@ResponseBody
	public DataRow queryUnitInfo() {
		EntityWrapper<Unit> ew = new EntityWrapper<Unit>();
		try {
			List<Unit> list =unitService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("UnitController>>>>>>>>>>>>>queryUnitInfo",e);
		}
		return messageMap;
	}
	
}
