package com.fadl.supplies.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Supply;
import com.fadl.supplies.service.SupplyService;
/**
 * <p>
 * 耗材供应商设置 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/suppliesSupply")
public class SupplyController extends AbstractController{
	
private static Logger logger = LoggerFactory.getLogger(SupplyController.class);
	
	@Autowired
	SupplyService supplyService;
	//跳转供应商页面
	@RequestMapping("/supplyPage")
	@RequiresPermissions("supply:view")
	public String supplyPage() {
		return "/supplies/config/supply";
	}
	//跳转新增页面
	@RequestMapping("/supplyAdd")
	public String supplyAdd() {
		return "/supplies/config/supply_add";
	}
	//跳转修改页面
	@RequestMapping("/supplyUpdate")
	public String supplyUpdate(String id,Model model) {
		Supply supply= new Supply();
		supply= supplyService.selectById(id);
		model.addAttribute("supply",supply);
		return "/supplies/config/supply_update";
	}
	/**
	 * 查询供应商列表
	 * @param suppliesSupply
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/querySuppliesSupplyList")
	@InvokeLog(name = "querySuppliesSupplyList", description = "查询供应商列表")
	@ResponseBody
	public DataRow querySuppliesSupplyList(Supply supply, Integer page, Integer limit) {
		Page<Supply> paging = null;
		try {
			EntityWrapper<Supply> ew = new EntityWrapper<Supply>();
			ew.like("name", supply.getName(), SqlLike.DEFAULT);
			ew.eq("isDelete", 0);
			paging = new Page<Supply>(page, limit);
			paging = supplyService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("SupplyController>>>>>>>>>>>>>querySuppliesSupplyList",e);
		}
		return messageMap;
	}
	/**
	 * 新增供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	@RequestMapping(value = "/insertSupply")
	@InvokeLog(name = "insertSupply", description = "新增供应商信息")
	@ResponseBody
	@RequiresPermissions("supply:add")
	public DataRow insertSuppliesSupply(Supply supply) {
		try {
			supplyService.insertSuppliesSupply(supply,messageMap);
		} catch (Exception e) {
			logger.error("SupplyController>>>>>>>>>>>>>insertSuppliesSupply",e);
		}
		return messageMap;
	}
	/**
	 * 修改供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	@RequestMapping(value = "/updateSupply")
	@InvokeLog(name = "updateSupply", description = "修改供应商信息")
	@ResponseBody
	@RequiresPermissions("supply:update")
	public DataRow updateSuppliesSupply(Supply supply) {
		try {
			supplyService.updateSuppliesSupply(supply,messageMap);
		} catch (Exception e) {
			logger.error("SupplyController>>>>>>>>>>>>>updateSuppliesSupply",e);
		}
		return messageMap;
	}
	/**
	 * 删除供应商信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSupply")
	@InvokeLog(name = "deleteSupply", description = "删除供应商信息")
	@ResponseBody
	@RequiresPermissions("supply:delect")
	public DataRow deleteSuppliesSupply(Long ids) {
		try {
			supplyService.deleteSuppliesSupply(ids,messageMap);
		} catch (Exception e) {
			logger.error("SupplyController>>>>>>>>>>>>>deleteSupply",e);
		}
		return messageMap;
	}
	/**
	 * 获取供应商信息
	 * @return
	 */
	@RequestMapping(value="/querySuppliesSupplyInfo")
	@InvokeLog(name = "querySuppliesSupplyInfo", description = "获取供应商信息")
	@ResponseBody
	public DataRow querySuppliesSupplyInfo() {
		EntityWrapper<Supply> ew = new EntityWrapper<Supply>();
		try {
			ew.eq("isDelete", 0);
			List<Supply> list = supplyService.selectList(ew);
			messageMap.initSuccessObject("获取成功",list);
		} catch (Exception e) {
			logger.error("SupplyController>>>>>>>>>>>>>querySuppliesSupplyInfo",e);
			messageMap.initFial("获取失败");
		}
		return messageMap;
	}
}
