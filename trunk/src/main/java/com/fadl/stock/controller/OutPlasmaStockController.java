package com.fadl.stock.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
import com.fadl.box.service.BoxInfoService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.stock.service.PlasmaStockService;

/**
 * 血浆出库
 * @author hu
 *
 */
@Controller
@RequestMapping("/plasmaStock")
public class OutPlasmaStockController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(OutPlasmaStockController.class);
	
	@Autowired
	public PlasmaStockService plasmaStockService;
	@Autowired
	public BoxInfoService boxInfoService;
	
	/**
	 * 血浆出库
	 * @return
	 */
	@RequestMapping("/outPlasmaStock")
	@RequiresPermissions("plasmaStock:out")
	public String outPlasmaStock(){
		return "/plasmaStock/stock_out";
	}
	
	/**
	 * 血浆检测装运表
	 * @return
	 */
	@RequestMapping("/plasmaCheck")
	public String plasmaCheck(){
		return "/plasmaStock/print_plasma_check";
	}
	
	/**
	 * 血浆装箱汇总表
	 * @return
	 */
	@RequestMapping("/plasmaSummary")
	public String plasmaSummary(){
		return "/plasmaStock/print_plasma_summary";
	}
	
	/**
	 * 血浆出库列表
	 * @return
	 */
	@RequestMapping(value="/queryOutPlasmaStock",method = RequestMethod.POST)
	@InvokeLog(name = "queryOutPlasmaStock", description = "血浆出库列表")
	@ResponseBody
	@RequiresPermissions("plasmaStock:out")
	public DataRow queryOutPlasmaStock(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p = plasmaStockService.queryOutPlasmaStock(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("OutPlasmaStockController>>>>>>>>>queryOutPlasmaStock",e);
		}
		return messageMap;
	}
	
	
	/**
	 * 单箱/批量  送浆
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/updatePulpingList",method = RequestMethod.POST)
	@InvokeLog(name = "updatePulpingList", description = "单箱送浆")
	@ResponseBody
	@RequiresPermissions("plasmaStock:outUpdate")
	public DataRow updatePulpingList(@RequestParam HashMap<String, Object> map){
		try {
			String[] ids = String.valueOf(map.get("id")).split(",");
			map.put("ids", Arrays.asList(ids));
			int res = plasmaStockService.updatePulpingList(map);
			if (res > 0) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OutPlasmaStockController>>>>>>>>>updatePulpingList",e);
		}
		return messageMap;
	}
	
	/**
	 * 取消出库
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/cacalPulpingList",method = RequestMethod.POST)
	@InvokeLog(name = "cacalPulpingList", description = "取消出库")
	@ResponseBody
	@RequiresPermissions("plasmaStock:outCacal")
	public DataRow cacalPulpingList(@RequestParam HashMap<String, Object> map){
		try {
			String[] ids = String.valueOf(map.get("id")).split(",");
			map.put("ids", Arrays.asList(ids));
			int res = plasmaStockService.cacalPulpingList(map);
			if (res>0) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OutPlasmaStockController>>>>>>>>>cacalPulpingList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆检测装运表
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/queryPlasmaCheckList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaCheckList", description = "血浆检测装运表")
	@ResponseBody
	public DataRow queryPlasmaCheckList(@RequestParam HashMap<String, String> data){
		try {
			List<DataRow> list = plasmaStockService.queryPlasmaCheckList(data);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("OutPlasmaStockController>>>>>>>>>queryPlasmaCheckList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆装箱汇总表
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/queryPlasmaBoxSummaryList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaBoxSummaryList", description = "血浆装箱汇总表")
	@ResponseBody
	public DataRow queryPlasmaBoxSummaryList(@RequestParam HashMap<String, String> data){
		try {
			List<DataRow> list = plasmaStockService.queryPlasmaBoxSummaryList(data);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("OutPlasmaStockController>>>>>>>>>queryPlasmaBoxSummaryList",e);
		}
		return messageMap;
	}
}
