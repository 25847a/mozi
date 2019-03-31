package com.fadl.supplies.controller;
 
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.OrderForm;
import com.fadl.supplies.service.OrderFormService;
import com.fadl.supplies.service.OrderService;

/**
 * <p>
 * 订购单记录 前端控制器
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
@Controller
@RequestMapping("/orderForm")
public class OrderFormController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(OrderFormController.class);
	
	@Autowired
	OrderFormService orderFormService;
	@Autowired
	OrderService orderService;
	
	/**
	 * 跳转耗材检验页面
	 * @return
	 */
	@RequestMapping("/quarantine")
	@RequiresPermissions("stock:view")
    public String quarantine() {
        return "/supplies/intoStock/quarantine";
    }
	/**
	 * 跳转修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderDatelis")
	public String orderDatelis(String id,Model model) {
		OrderForm orderForm = orderFormService.selectById(id);
		model.addAttribute("orderForm",orderForm);
		return "/supplies/order/order_update";
	}
	/**
	 * 跳转删除页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderDelect")
	public String orderDelect(String id,Model model) {
		OrderForm orderForm = orderFormService.selectById(id);
		model.addAttribute("orderForm",orderForm);
		return "/supplies/order/order_delect";
	}
	/**
	 * 跳转详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryDatelis")
	@RequiresPermissions("order:details")
	public String queryDatelis(String id,Model model) {
		model.addAttribute("id",id);
		return "/supplies/order/order_datelis";
	}
	/**
	 * 库存入库时耗材添加入库使用的接口
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryOrderFormPage")
	@InvokeLog(name = "queryOrderFormPage", description = "库存入库时耗材添加入库使用的接口")
	@ResponseBody
	public DataRow queryOrderFormPage(Integer page,Integer limit) {
		try {
            Page<DataRow> p = new Page<DataRow>(page,limit);
            p=orderFormService.queryOrderFormPage(p);
            messageMap.initPage(p);
        } catch (Exception e) {
            logger.error("OrderFormController>>>>>>>>>queryOrderFormPage>>>>>",e);
        }
        return messageMap;
	}
	
	/**
	 * 查询耗材检验列表
	 * @param quarantine
	 * @param page
	 * @param limit
	 * @return
	 */
    @RequestMapping(value="/queryQuarantineList")
    @InvokeLog(name = "queryQuarantineList", description = "查询耗材检验列表")
    @ResponseBody
    public DataRow queryQuarantineList(@RequestParam Map<String, Object> map, Integer page, Integer limit) {
        Page<DataRow> pageing = null;
        try {
        	pageing = new Page<DataRow>(page,limit);
        	orderFormService.queryQuarantineList(map, pageing);
           messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("OrderFormController>>>>>>>>>queryQuarantineList>>>>>",e);
        }
        return messageMap;
    }
	
	/**
	 * 审批完成,更改订购单记录表的订单状态,2.已完成
	 * @param orderForm
	 * @return
	 */
	@RequestMapping(value="updateBuyStatus")
	@InvokeLog(name = "updateBuyStatus", description = "审批完成,更改订购单记录表的订单状态,2.已完成")
	@ResponseBody
	@RequiresPermissions("stock:status")
	public DataRow updateBuyStatus(OrderForm orderForm) {
		try {
			Admin admin= (Admin)request.getSession().getAttribute("admin");
			Long id = admin.getId();
			orderFormService.updateBuyStatus(orderForm,messageMap,id);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>updateBuyStatus"+e);
		}
		return messageMap;
		}
	/**
	 *	订购列表接口
	 * @param order
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryOrder")
	@InvokeLog(name = "queryOrder", description = "订购列表接口")
	@ResponseBody
	public DataRow queryOrder(OrderForm orderForm, Integer page, Integer limit) {
		try {
	         Page<DataRow> pageing = new Page<DataRow>(page,limit);
	         orderFormService.queryOrder(orderForm,pageing);
	         messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>>queryOrder",e);
		}
		return messageMap;
	}
	/**
	 * 查询详情页面列表
	 * @param orderformId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryDatelisList")
	@InvokeLog(name = "queryDatelisList", description = "查询详情页面列表")
	@ResponseBody
	public DataRow queryDatelis(String order, Integer page, Integer limit) {
		 Page<DataRow> pageing = new Page<DataRow>(page,limit);
		try {
	         pageing =orderFormService.queryDatelisList(order,pageing);
	         messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("SuppliesOrderController>>>>>>>>>>>>queryOrder",e);
		}
		return messageMap;
	}
	
	
	/**
	 *  订单作废,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	@RequestMapping(value="/UselessStatus",method= RequestMethod.POST)
	@InvokeLog(name = "UselessStatus", description = "订单作废,更改订购单记录表的订单状态")
	@ResponseBody
	@RequiresPermissions("order:nullify")
	public DataRow UselessStatus(@RequestBody List<OrderForm> result) {
		try {
			orderFormService.UselessStatus(result,messageMap);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>updateBuyStatus"+e);
		}
		return messageMap;
		}
	/**
	 * 订单作废
	 * @param result
	 * @return
	 */
	@RequestMapping("/delectOrder")
	@InvokeLog(name = "delectOrder", description = "订单作废")
	@ResponseBody
	@RequiresPermissions("order:update")
	public DataRow delectOrder(OrderForm order) {
		try {
			orderFormService.delectOrder(order,messageMap);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>delectOrder",e);
		}
		return messageMap;
	}
	/**
	 * 修改订单信息
	 */
	@RequestMapping("/updateOrder")
	@InvokeLog(name = "updateOrder", description = "修改订单信息")
	@ResponseBody
	@RequiresPermissions("order:delect")
	public DataRow updateOrder(OrderForm orderForm) {
		try {
			orderFormService.updateOrder(orderForm,messageMap);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>updateOrder",e);
		}
		return messageMap;
	}
	/**
	 * 待审批、待检验,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	@RequestMapping(value="/updateStatus",method= RequestMethod.POST)
	@InvokeLog(name = "updateStatus", description = "待审批、待检验,更改订购单记录表的订单状态")
	@ResponseBody
	@RequiresPermissions("order:status")
	public DataRow updateStatus(OrderForm orderForm) {
		try {
			Admin admin= (Admin)request.getSession().getAttribute("admin");
			Long id = admin.getId();
			orderFormService.updateStatus(orderForm,messageMap,id);
		} catch (Exception e) {
			logger.error("OrderFormController>>>>>>>>>>>updateBuyStatus"+e);
		}
		return messageMap;
		}
}
