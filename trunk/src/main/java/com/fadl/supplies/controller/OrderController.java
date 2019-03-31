package com.fadl.supplies.controller;


import java.util.Map;

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
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.service.OrderFormService;
import com.fadl.supplies.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;//耗材订购记录表
	@Autowired
	OrderFormService orderFormService;//订购单记录
	//跳转订单页面
	@RequestMapping("/orderPage")
	@RequiresPermissions("order:view")
	public String orderPage() {
		return "/supplies/order/order";
	}
	//跳转订单新增页面
	@RequestMapping("/orderAdd")
	@RequiresPermissions("order:add")
	public String orderAdd() {
		return "/supplies/order/order_add";
	}
	//跳转订单审核页面
		@RequestMapping("/examinePage")
		@RequiresPermissions("order:list")
		public String examinePage() {
			return "/supplies/order/examine";
		}
	/**
	 * 订单审核列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryOrderList",method= RequestMethod.POST)
	@InvokeLog(name = "queryOrderList", description = "订单审核列表")
	@ResponseBody
	public DataRow queryOrderList(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
		Page<DataRow> pageing = null;
        try {
        	pageing = new Page<DataRow>(page,limit);
        	orderService.queryOrderList(map, pageing);
            messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("OrderController>>>>>>>>>queryOrderList>>>>>",e);
        }
        return messageMap;
		
	}
	/**
	 * 耗材订购,录入耗材订购记录表、订购单记录
	 * @return
	 */
	@RequestMapping(value="/insertOrderform")
	@InvokeLog(name = "insertOrderform", description = "耗材订购,录入耗材订购记录表、订购单记录")
	@ResponseBody
	public DataRow insertOrderform(@RequestParam Map<String,String> map) {
		try {
			String list =map.get("list").replace(",[]", "").replace("[],", "");
			map.put("list", list);
			orderService.insertOrderform(map,messageMap);
		} catch (Exception e) {
			logger.error("OrderController>>>>>>>>>>>>insertOrderform",e);
		}
		return messageMap;
	}

}
