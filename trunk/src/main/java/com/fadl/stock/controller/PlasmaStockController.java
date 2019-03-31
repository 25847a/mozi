package com.fadl.stock.controller;


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
import com.fadl.account.entity.Admin;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.stock.entity.PlasmaStock;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 血浆入库表 前端控制器
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
@Controller
@RequestMapping("/plasmaStock")
public class PlasmaStockController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PlasmaStockController.class);
	
	@Autowired
	public PlasmaStockService plasmaStockService;
	@Autowired
	private PlasmCollectionService pcService;
	@Autowired
	private TestConfigInfoService tciService;
	
	/**
	 * 跳转入库页面
	 * @return
	 */
	@RequestMapping("/stock")
	@RequiresPermissions("plasmaStock:view")
	public String stock(){
		return "/plasmaStock/stock_list";
	}
	
	/**
	 * 打印装箱清单
	 * @return
	 */
	@RequestMapping("/printBoxDetail")
	@RequiresPermissions("plasmaStock:printBoxDetail")
	public String printBoxDetail(){
		return "/plasmaStock/print_box_detail";
	}
	
	/**
	 * 批量报废
	 * @return
	 */
	@RequestMapping("/batchScrap")
	@RequiresPermissions("plasmaStock:batchScrap")
	public String batchScrap(){
		return "/plasmaStock/batch_scrap";
	}
	
	/**
	 * 固定浆员留小样
	 * @return
	 */
	@RequestMapping("/returnSample")
	public String returnSample(){
		return "/plasmaStock/return_sample"; 
	}
	
	/**
	 * 打印固定浆员留小样
	 * @return
	 */
	@RequestMapping("/printSample")
	public String printSample(){
		return "/plasmaStock/print_simple"; 
	}
	
	/**
	 * 血浆装箱单
	 * @return
	 */
	@RequestMapping("/printPlasmaBox")
	public String plasmaBox(){
		return "/plasmaStock/print_plasma_box"; 
	}
	
	/**
	 * 查询入库列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryPlasmaStockList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaStockList", description = "查询入库列表")
	@ResponseBody
	@RequiresPermissions("plasmaStock:view")
	public DataRow queryPlasmaStockList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p=plasmaStockService.queryPlasmaStockList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryPlasmaStockList",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据 id 查询 箱号信息和浆量
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPlasmaStockById",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaStockById", description = "根据 id 查询 箱号信息和浆量")
	@ResponseBody
	public DataRow queryPlasmaStockById(@RequestParam Long id){
		try {
			DataRow data = plasmaStockService.queryPlasmaStockById(id);
			messageMap.initSuccess(data);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryPlasmaStockList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆入库
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPlasmaStock",method = RequestMethod.POST)
	@InvokeLog(name = "addPlasmaStock", description = "血浆入库")
	@ResponseBody
	@RequiresPermissions("plasmaStock:add")
	public DataRow addPlasmaStock(PlasmaStock plasmaStock){
		try {
			Admin admin = (Admin) request.getSession().getAttribute(IConstants.SESSION_ADMIN_USER);
			plasmaStock.setCreater(admin.getId());
			plasmaStockService.addPlasmaStock(plasmaStock , messageMap);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>addPlasmaStock",e);
		}
		return messageMap;
	}
	
	/**
	 * 浆库高级查询
	 * @return
	 */
	@RequestMapping("/queryStockList")
	@RequiresPermissions("queryStock:view")
	public String queryStockList(){
		return "/plasmaStock/query_stock_list";
	}
	
	/**
	 * 浆库高级查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/querySeniorStockList",method = RequestMethod.POST)
	@InvokeLog(name = "querySeniorStockList", description = "浆库高级查询")
	@ResponseBody
	@RequiresPermissions("queryStock:view")
	public DataRow querySeniorStockList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p=plasmaStockService.querySeniorStockList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>querySeniorStockList",e);
		}
		return messageMap;
	}
	
	/**
	 * 打印血浆列表页面
	 * @return
	 */
	@RequestMapping("/printPlasmaStock")
	public String printPlasmaStock(){
		return "/plasmaStock/print_stock";
	}
	
	/**
	 * 血浆装满一箱 打印 列表页面
	 * @return
	 */
	@RequestMapping(value="/queryPrintPlasmaStockList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPrintPlasmaStockList", description = "血浆装满一箱 打印 列表页面")
	@ResponseBody
	public DataRow queryPrintPlasmaStockList(@RequestParam String boxId){
		try {
			List<DataRow> list = plasmaStockService.queryPlasmaStockListByBoxId(boxId);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryPrintPlasmaStockList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆报废
	 * @return
	 */
	@RequestMapping(value="/updatePlasmaStockScrap",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmaStockScrap", description = "血浆报废")
	@ResponseBody
	@RequiresPermissions("queryStock:scrap")
	public DataRow updatePlasmaStockScrap(PlasmaStock plasmaStock){
		try {
			plasmaStockService.updatePlasmaStockScrap(plasmaStock, messageMap);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>updatePlasmaStockScrap",e);
		}
		return messageMap;
	}
	
	/**
	 * 取消入库
	 * @return
	 */
	@RequestMapping(value="/updatePlasmaStockStatus",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmaStockStatus", description = "取消入库")
	@ResponseBody
	@RequiresPermissions("queryStock:cancel")
	public DataRow updatePlasmaStockStatus(PlasmaStock plasmaStock){
		try {
			plasmaStockService.updatePlasmaStockStatus(plasmaStock, messageMap);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>updatePlasmaStockStatus",e);
		}
		return messageMap;
	}
	
	/**
	 * 打印装箱条码
	 * @return
	 */
	@RequestMapping("/printBox")
	public String printBox(){
		return "/plasmaStock/print_box";
	}
	
	/**
	 * 打印装箱清单（根据箱子编号查询 箱子血浆信息）
	 * @return
	 */
	@RequestMapping(value="/queryBoxPlasmaInfo",method = RequestMethod.POST)
	@InvokeLog(name = "queryBoxPlasmaInfo", description = "打印装箱清单（根据箱子编号查询 箱子血浆信息）")
	@ResponseBody
	public DataRow queryBoxPlasmaInfo(@RequestParam HashMap<String, String> map){
		try {
			List<DataRow> list = plasmaStockService.queryBoxPlasmaInfo(map);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryBoxPlasmaInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询装箱清单 试剂信息 
	 * @return
	 */
	@RequestMapping(value="/queryBoxPlasmaInfoReagents",method = RequestMethod.POST)
	@InvokeLog(name = "queryBoxPlasmaInfoReagents", description = "查询装箱清单 试剂信息 ")
	@ResponseBody
	public DataRow queryBoxPlasmaInfoReagents(@RequestParam HashMap<String, String> map){
		try {
			List<DataRow> list = plasmaStockService.queryBoxPlasmaInfoReagents(map);
			HashMap<String, String> map1 = new HashMap<>();
			String checkDate = map.get("time");
			map1.put("lable", "全血比重");
			map1.put("endTime", checkDate);
			List<DataRow> lst= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst.size()!=0) {
				messageMap.put("ptItem", lst.get(0));
			}
			map1.put("lable", "蛋白量");
			map1.put("endTime", checkDate);
			List<DataRow> lst2= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst2.size()!=0) {
				messageMap.put("wbItem", lst2.get(0));
			}
			map1.put("lable", "血型");
			map1.put("endTime", checkDate);
			List<DataRow> lst1= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst1.size()!=0) {
				messageMap.put("bloodItem", lst1.get(0));
			}
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryBoxPlasmaInfoReagents",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据卡号、姓名 查询 未出库列表
	 * @return
	 */
	@RequestMapping(value="/queryScrapPlasmaList",method = RequestMethod.POST)
	@InvokeLog(name = "queryScrapPlasmaList", description = "查询装箱清单 试剂信息 ")
	@ResponseBody
	public DataRow queryScrapPlasmaList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			plasmaStockService.queryScrapPlasmaList(p, data);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryScrapPlasmaList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆批量报废
	 * @return
	 */
	@RequestMapping(value="/updatePlasmaScrapList",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmaScrapList", description = "血浆批量报废")
	@ResponseBody
	public DataRow updatePlasmaScrapList(@RequestParam List<Long> id){
		try {
			plasmaStockService.updatePlasmaScrapList(id, messageMap);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>updatePlasmaScrapList",e);
		}
		return messageMap;
	}
	
	/**
	 * 血浆留样
	 * @return
	 */
	@RequestMapping(value="/queryReturnSimpleList")
	@InvokeLog(name = "queryReturnSimpleList", description = "血浆留样")
	@ResponseBody
	public DataRow queryReturnSimpleList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			plasmaStockService.queryReturnSimpleList(data,p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>>queryReturnSimpleList",e);
		}
		return messageMap;
	}
	
	/**
	 * 批量送样
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/updateWithSendOff", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithSendOff", description = "批量送样")
	@ResponseBody
	// TODO @RequiresPermissions("queryStock:send")
	public DataRow updateWithSendOff(@RequestParam List<Long> ids, String sendPerson) {
		try {
    		pcService.sendOff(ids,sendPerson, messageMap);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>updateWithSendOff>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	
	/**
	 * 血浆装箱单
	 * @return
	 */
	@RequestMapping(value = "/queryPlasmaBoxList", method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaBoxList", description = "")
	@ResponseBody
	public DataRow queryPlasmaBoxList(@RequestParam HashMap<String, String> data){
		try {
			List<DataRow> list = plasmaStockService.queryPlasmaBoxList(data);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmaStockController>>>>>>>>queryPlasmaBoxList>>>>>", e);
		}
		return messageMap;
	}
}

