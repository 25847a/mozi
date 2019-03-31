package com.fadl.supplies.controller;


import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.service.OutstockService;
import com.fadl.supplies.service.StockService;
/**
 * <p>
 * 出库表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-05-28
 */
@Controller
@RequestMapping("/outstock")
public class OutstockController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(OutstockController.class);
	
	@Autowired
	OutstockService outstockService;
	@Autowired
	StockService stockService;
	/**
	 * 跳转出库页面
	 * @return
	 */
	@RequestMapping("/outstockPage")
	@RequiresPermissions("outstock:view")
	public String outstockPage() {
		return "/supplies/used/outStock";
	}
	/**
	 * 跳转新增页面
	 * @return
	 */
	@RequestMapping("/outStockAdd")
	@RequiresPermissions("outstock:add")
	public String outStockAdd() {
		return "/supplies/used/outStock_add";
	}
	/**
	 * 新增出库库存列表
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/queryAddOutStockList")
	@InvokeLog(name = "queryAddOutStockList", description = "新增出库库存列表")
	@ResponseBody
	public DataRow queryAddOutStockList(Integer page,Integer limit) {
			Page<DataRow> pageing = null;
		try {
			pageing = new Page<DataRow>(page,limit);
			stockService.querySuppliesStatus(pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>queryAddOutStockList",e);
		}
		return messageMap;
	}
	/**
	 * 出库列表
	 * @param map
	 * @param page
	 * @param limt
	 * @return
	 */
	@RequestMapping(value="/queryoutstockList")
	@InvokeLog(name = "queryoutstockList", description = "出库列表")
	@ResponseBody
	public DataRow queryOutstockList(@RequestParam Map<String,Object> map,Integer page,Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing= new Page<DataRow>(page,limit);
			outstockService.queryOutstockList(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>queryoutstockList",e);
		}
		return messageMap;
	}
	/**
	 * 插入出库表
	 * @param map
	 * @return
	 */
	@RequestMapping("/insertOutstock")
	@InvokeLog(name = "insertOutstock", description = "插入出库表")
	@ResponseBody
	@RequiresPermissions("outstock:add")
	public DataRow insertOutstock(@RequestParam Map<String,String> map) {
		try {
			String list =map.get("list").replace(",[]", "").replace("[],", "");
			map.put("list", list);
			outstockService.insertOutstock(map,messageMap);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>insertOutstock",e);
		}
		return messageMap;
	}
	/**
	 *提供给谢鑫检验查询
	 * @param  
	 *  入参：type(0.试剂 1.质控品)    status(0、可用 1、不可用)
	 * @return
	 */
	@RequestMapping(value="/queryTestList")
	@InvokeLog(name = "queryTestList", description = "提供给谢鑫检验查询")
	@ResponseBody
	public DataRow queryTestList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing= new Page<DataRow>(page,limit);
			outstockService.queryTestList(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>queryTestList",e);
		}
		return messageMap;
	}
	/**
	 *提供给谢鑫检验查询2
	 * @param  
	 *  入参：type(0.试剂 1.质控品)    projectName(项目名称)
	 * @return
	 */
	@RequestMapping(value="/queryTestPage")
	@InvokeLog(name = "queryTestPage", description = "提供给谢鑫检验查询2")
	@ResponseBody
	public DataRow queryTestPage(@RequestParam Map<String,String> map) {
		try {
			outstockService.queryTestPage(map,messageMap);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>queryTestPage",e);
		}
		return messageMap;
	}
	/**
	 *提供给谢鑫检验查询2
	 * @param  
	 *  入参：type(0.试剂 1.质控品)    projectName(项目名称)
	 * @return
	 */
	@RequestMapping(value="/queryInfoById")
	@InvokeLog(name = "queryInfoById", description = "提供给谢鑫检验查询2")
	@ResponseBody
	public DataRow queryInfoById(Long id) {
		try {
			messageMap.initSuccess(outstockService.queryById(id));
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>queryInfoById",e);
		}
		return messageMap;
	}
	/**
	 * 提供给免疫查询的列表
	 * @return
	 */
	@RequestMapping("/queryVaccineList")
	@InvokeLog(name = "queryVaccineList", description = "提供给免疫查询的列表")
	@ResponseBody
	public DataRow queryVaccineList() {
		try {
			List<DataRow> list =outstockService.queryVaccineList();
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>queryVaccineList",e);
		}
		return messageMap;
	}
	/**
	 * 查询免疫查询的批号列表
	 * @return
	 */
	@RequestMapping("/queryVaccineBatchNumber")
	@InvokeLog(name = "queryVaccineBatchNumber", description = "查询免疫查询的批号列表")
	@ResponseBody
	public DataRow queryVaccineBatchNumber(@RequestParam Map<String,String> map) {
		try {
			outstockService.queryVaccineBatchNumber(map,messageMap);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>queryVaccineBatchNumber",e);
		}
		return messageMap;
	}
	/**
	 * 提供给免疫查询的批号列表
	 * @return
	 */
	@RequestMapping("/queryVaccineBatch")
	@InvokeLog(name = "queryVaccineBatch", description = "提供给免疫查询的批号列表")
	@ResponseBody
	public DataRow queryVaccineBatch() {
		EntityWrapper<Outstock> ew = new EntityWrapper<Outstock>();
		try {
			ew.groupBy("batchNumber");
			List<Outstock> list =outstockService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("OutstockController>>>>>>>>>>>>queryVaccineBatch",e);
		}
		return messageMap;
	}
}

