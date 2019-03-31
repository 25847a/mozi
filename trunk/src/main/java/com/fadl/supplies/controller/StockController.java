package com.fadl.supplies.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.CommonService;
import com.fadl.common.service.ConfigService;
import com.fadl.supplies.entity.Order;
import com.fadl.supplies.entity.OrderForm;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.entity.Stock;
import com.fadl.supplies.service.InfoService;
import com.fadl.supplies.service.OrderFormService;
import com.fadl.supplies.service.OrderService;
import com.fadl.supplies.service.OutstockService;
import com.fadl.supplies.service.StockService;

/**
 * <p>
 * 耗材库存记录 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
@Controller
@RequestMapping("/suppliesStock")
public class StockController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(StockController.class);
	
	
	@Autowired
	StockService stockService;
	@Autowired
	InfoService infoService;
	@Autowired
	ConfigService configService;
	@Autowired
	CommonService commonService;
	@Autowired
	OrderFormService orderFormService;
	@Autowired
	OrderService orderService;
	@Autowired
	OutstockService osService;
	/**
	 * 跳转库房列表
	 * @return
	 */
	@RequestMapping("/storageRoom")
	public String storageRoom() {
		return "/supplies/stockPage";
	}
	/**
	 * 跳转耗材入库页面
	 * @return
	 */
	@RequestMapping("/intoStock")
	@RequiresPermissions("stock:list")
    public String intoStock() {
        return "/supplies/intoStock/intoStock";
    }
	/**
	 * 跳转添加耗材入库页面
	 * @return
	 */
	@RequestMapping("/intoStockAdd")
	public String intoStockAdd(){
		 return "/supplies/intoStock/intoStock_add";
	}
	/**
	 * 跳转库房警告页面
	 * @return
	 */
	@RequestMapping("/policePage")
	@RequiresPermissions("police:view")
	public String policePage() {
		return  "/supplies/intoStock/police";
	}
	/**
	 * 跳转盘点页面
	 * @return
	 */
	@RequestMapping("/updatePointPage")
	public String updatePoint(String id,Model model) {
		Stock stock=stockService.selectById(id);
		model.addAttribute("stock", stock);
		return  "/supplies/point/point_update";
	}
	/**
	 * 跳转盘点修改页面
	 * @return
	 */
	@RequestMapping("/pointPage")
	public String pointPage() {
		return  "/supplies/point/point";
	}
	/**
	 * 跳转到期时间设置
	 * @return
	 */
	@RequestMapping("/policeTime")
	@RequiresPermissions("police:time")
	public String policeTime(Model model) {
		EntityWrapper<Config> ew = new EntityWrapper<Config>();
		ew.eq("type", "supplies_config");
		List<Config> list = configService.selectList(ew);
		model.addAttribute("red", list.get(0).getLable());
		model.addAttribute("green", list.get(1).getLable());
		model.addAttribute("blue", list.get(2).getLable());
		model.addAttribute("redId", list.get(0).getId());
		model.addAttribute("greenId", list.get(1).getId());
		model.addAttribute("blueId", list.get(2).getId());
		return "/supplies/intoStock/police_time";
	}
	/**
	 * 跳转耗材数量设置
	 * @return
	 */
	@RequestMapping("/policeNum")
	@RequiresPermissions("police:num")
	public String policeNum(Model model) {
		EntityWrapper<Config> ew = new EntityWrapper<Config>();
		ew.eq("type", "supplies_config");
		List<Config> list = configService.selectList(ew);
		model.addAttribute("red", list.get(3).getLable());
		model.addAttribute("green", list.get(4).getLable());
		model.addAttribute("blue", list.get(5).getLable());
		model.addAttribute("redId", list.get(3).getId());
		model.addAttribute("greenId", list.get(4).getId());
		model.addAttribute("blueId", list.get(5).getId());
		return "/supplies/intoStock/police_num";
	}
	/**
	 * 跳转库存修改剩余数量
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/jumpUpdate")
	public String jumpUpdate(String id,Model model) {
		Stock stock = stockService.selectById(id);
		model.addAttribute("stock",stock);
		return "/supplies/intoStock/intoStock_datelis_update";
	}
	/**
	 * 跳转入库详情页面
	 * @return
	 */
	@RequestMapping("/intoStockDatelis")
	public String intoStockDatelis(String id,Model model) {
		model.addAttribute("id",id);
		return  "/supplies/intoStock/intoStock_datelis";
	}
	/**
	 * 仓库列表
	 * @param map
	 * @param page
	 * @param limt
	 * @return
	 */
	@RequestMapping(value="/queryStockInfoList")
	@InvokeLog(name = "queryStockInfoList", description = "仓库列表")
	@ResponseBody
	public DataRow queryStockInfoList(@RequestParam Map<String,Object> map,Integer page,Integer limit) {
		EntityWrapper<OrderForm> ew = new EntityWrapper<OrderForm>();
		if(map.containsKey("oddNumber")) {
			ew.like("oddNumber", String.valueOf(map.get("oddNumber")),SqlLike.DEFAULT);
		}
		ew.eq("status", 4);
		ew.or();
		ew.eq("status", 5);
		ew.orderBy("createDate", false);
		Page<OrderForm> pageing = null;
		try {
			pageing= new Page<OrderForm>(page,limit);
			pageing=orderFormService.selectPage(pageing, ew);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>querySuppliesInfoList",e);
			messageMap.initFial("查询失败");
		}
		return messageMap;
	}
	/**
	 * 查询库房详情列表
	 * @param batchNumber
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/queryStockDatelis")
	@InvokeLog(name = "queryStockDatelis", description = "查询库房详情列表")
	@ResponseBody
	@RequiresPermissions("stock:details")
	public DataRow queryStockDatelis(String id, Integer page, Integer limit) {
		 Page<DataRow> pageing = new Page<DataRow>(page,limit);
			try {
		         pageing =stockService.queryStockDatelis(id,pageing);
		         messageMap.initPage(pageing);
			} catch (Exception e) {
				logger.error("SuppliesInfoController>>>>>>>>>>>>queryStockDatelis",e);
			}
			return messageMap;
	}
	/**
	 * 插入订单信息入库存表
	 * @param suppliesStock
	 * @return
	 */
	@RequestMapping(value = "/insertStock")
	@InvokeLog(name = "insertStock", description = "插入订单信息入库存表")
	@ResponseBody
	@RequiresPermissions("stock:add")
	public DataRow insertStock(@RequestParam Map<String,String> map) {
		try {
			String result = map.get("list").replace(",[]", "").replace("[],", "");
			List<Order> order = new ArrayList<Order>();//由于不执行事务原因,必须返回到其他方法使用
			stockService.insertStock(result,messageMap,order);
			if(!order.isEmpty()) {
				for(int i=0;i<order.size();i++) {	
					EntityWrapper<Order> ew = new EntityWrapper<Order>();
					ew.eq("orderformId",order.get(i).getOrderformId());
					ew.eq("suppliesId",order.get(i).getSuppliesId());
					Order o = new Order();
					o.setStatus(1);
					orderService.update(o, ew);//更改耗材入库状态
					EntityWrapper<Order> e = new EntityWrapper<Order>();
					e.eq("orderformId",order.get(i).getOrderformId());
					List<Order> list = orderService.selectList(e);
					int row =0;
					for(Order or:list) {
						if(or.getStatus()==0) {
							row=1;
						}
					}
					if(row==0) {
						EntityWrapper<OrderForm> ed = new EntityWrapper<OrderForm>();
						ed.eq("id",order.get(i).getOrderformId());
						OrderForm of = new OrderForm();
						of.setStatus(4);
						orderFormService.update(of, ed);
						}else {
						EntityWrapper<OrderForm> ed = new EntityWrapper<OrderForm>();
						ed.eq("id",order.get(i).getOrderformId());
						OrderForm of = new OrderForm();
						of.setStatus(5);
						orderFormService.update(of, ed);	
						}
					}
				messageMap.initSuccess();
				return messageMap;
						}
		} catch (Exception e) {
			logger.error("SuppliesStockController>>>>>>>insertStock",e);
		}
		return messageMap;
	}
	/**
	 * 库存报警列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryPoliceList")
	@InvokeLog(name = "queryPoliceList", description = "库存报警列表")
	@ResponseBody
	public DataRow queryPoliceList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing =new Page<DataRow>(page,limit);
		try {
			stockService.queryPoliceList(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("SuppliesStockController>>>>>>>queryPoliceList",e);
		}
		return messageMap;
	}
	/**
	 * 查询库存预警配置信息
	 * @return
	 */
	@RequestMapping(value="/querySuppliesConfig")
	@InvokeLog(name = "querySuppliesConfig", description = "查询库存预警配置信息")
	@ResponseBody
	public DataRow querySuppliesConfig() {
		try {
			EntityWrapper<Config> ew = new EntityWrapper<Config>();
			ew.eq("type", "supplies_config");
			List<Config> list = configService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("SuppliesStockController>>>>>>>querySuppliesConfig",e);
		}
		return messageMap;
	}
	/**
	 * 修改库存预警配置信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updatePolice")
	@InvokeLog(name = "updatePolice", description = "修改库存预警配置信息")
	@ResponseBody
	public DataRow updatePolice(@RequestParam Map<String,String> map) {
		try {
			stockService.updatePolice(map, messageMap);
		} catch (Exception e) {
			logger.error("SuppliesStockController>>>>>>>updatePolice",e);
		}
		return messageMap;
	}
	/**
	 * 检验配置查询批号
	 * @param suppliesId(耗材ID)(谢鑫)
	 * @return
	 */
	@RequestMapping(value="/queryStockBatchNumber")
	@InvokeLog(name = "queryStockBatchNumber", description = "检验配置查询批号")
	@ResponseBody
	public DataRow queryStockBatchNumber(String suppliesId) {
		EntityWrapper<Outstock>  ew  = new EntityWrapper<Outstock>();
		ew.eq("suppliesId", suppliesId);
		try {
		List<Outstock> stock = osService.selectList(ew);
		messageMap.initSuccess(stock);
		} catch (Exception e) {
			logger.error("SuppliesStockController>>>>>>>queryStockBatchNumber",e);
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
			stockService.queryTestList(map,pageing);
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
			stockService.queryTestPage(map,messageMap);
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
			messageMap.initSuccess(stockService.selectById(id));
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>queryInfoById",e);
		}
		return messageMap;
	}
	/**
	 * 盘点列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryPointList")
	@InvokeLog(name = "queryPointList", description = "盘点列表")
	@ResponseBody
	public DataRow queryPointList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing= new Page<DataRow>(page,limit);
			stockService.queryPointList(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>queryPointList",e);
		}
		return messageMap;
	}
	/**
	 * 修改盘点数量
	 * @param stock
	 * @return
	 */
	@RequestMapping(value="/updatePoint")
	@InvokeLog(name = "updatePoint", description = "修改盘点数量")
	@ResponseBody
	public DataRow updatePoint(Stock stock) {
		try {
			stockService.updatePoint(stock,messageMap);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>updatePoint",e);
		}
		return messageMap;
	}
	@RequestMapping(value="updateSurplusNumber")
	@ResponseBody
	public DataRow updateSurplusNumber(Stock stock) {
		try {
			stockService.updateSurplusNumber(stock,messageMap);
		} catch (Exception e) {
			logger.error("SuppliesInfoController>>>>>>>>>>>>updateSurplusNumber",e);
		}
		return messageMap;
	}
}
