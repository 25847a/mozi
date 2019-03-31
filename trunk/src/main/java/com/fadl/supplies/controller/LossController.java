package com.fadl.supplies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Loss;
import com.fadl.supplies.service.LossService;
import com.fadl.supplies.service.StreamService;
/**
 * <p>
 * 耗材报损记录表 前端控制器
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
@Controller
@RequestMapping("/loss")
public class LossController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(LossController.class);
	
	@Autowired
	LossService lossService;
	@Autowired
	StreamService streamService;
	/**
	 * 跳转报损页面
	 * @return
	 */
	@RequestMapping(value="lossPage")
	@RequiresPermissions("loss:view")
	public String lossPage(){
		return "/supplies/loss/loss";
	}
	/**
	 * 跳转报损新增页面
	 * @return
	 */
	@RequestMapping(value="/lossAdd")
	@RequiresPermissions("loss:add")
	public String returnAdd() {
		return "/supplies/loss/loss_add";
	}
	/**
	 * 跳转报损查询
	 * @return
	 */
	@RequestMapping(value="/queryLoss")
	@RequiresPermissions("loss:list")
	public String queryLoss() {
		return "/supplies/loss/lossQuery";
	}
	/**
	 * 跳转详情页面
	 * @param damageOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/lossDatelis")
	@RequiresPermissions("loss:details")
	public String lossDatelis(String damageOrder,Model model) {
		model.addAttribute("damageOrder",damageOrder);
		return "/supplies/loss/loss_datelis";
	}
	/**
	 * 跳转修改页面
	 * @param damageOrder
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/lossUpdate")
	@RequiresPermissions("loss:update")
	public String lossUpdate(String damageOrder,String reason,Model model) {
		model.addAttribute("damageOrder",damageOrder);
		model.addAttribute("reason",reason);
		return "/supplies/loss/loss_update";
		
	}
	/**
	 * 耗材退还首页列表
	 * @param retur
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryLossList")
	@InvokeLog(name = "queryLossList", description = "耗材退还首页列表")
	@ResponseBody
	@RequiresPermissions("loss:view")
	public DataRow queryLossList(Loss loss,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	lossService.queryLossList(pageing,loss);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>queryLossList",e);
		}
		return messageMap;
	}
	/**
	 * 详情页面
	 * @param retur
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryLossDatelis")
	@InvokeLog(name = "queryLossDatelis", description = "耗材退还详情页面")
	@ResponseBody
	@RequiresPermissions("loss:details")
	public DataRow queryLossDatelis(@RequestParam Map<String,String> map,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	lossService.queryLossDatelis(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>queryLossDatelis",e);
		}
		return messageMap;
	}
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @param map
	 * @param page
	 * @param limt
	 * @return
	 */
	@RequestMapping(value="/queryLossPage")
	@ResponseBody
	public DataRow queryLossPage(Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
			pageing = new Page<DataRow>(page,limit);
			streamService.queryStreamInfo(pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>queryLossPage",e);
		}
		return messageMap;
	}
	/**
	 * 插入到耗材报损表里面
	 * @param suppliesLoss
	 * @return
	 */
	@RequestMapping(value="/insertLoss")
	@InvokeLog(name = "insertLoss", description = "插入到耗材报损表里面")
	@ResponseBody
	@RequiresPermissions("loss:add")
	public DataRow insertLoss(@RequestParam Map<String,String> map) {
		try {
			String list =map.get("list").replace(",[]", "").replace("[],", "");
			map.put("list", list);
			lossService.insertLoss(map,messageMap);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>insertLoss",e);
		}
		return messageMap;
	}
	/**
	 * 修改退还订单信息
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateLoss")
	@InvokeLog(name = "updateLoss", description = "修改退还订单信息")
	@ResponseBody
	@RequiresPermissions("loss:update")
	public DataRow updateLoss(Loss loss){
		try {
			lossService.updateLoss(loss,messageMap);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>updateLoss",e);
		}
		return messageMap;
	}
	/**
	 * 修改报损订单审核状态
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateLossStatus")
	@InvokeLog(name = "updateLossStatus", description = "修改报损订单审核状态")
	@ResponseBody
	@RequiresPermissions("loss:status")
	public DataRow updateLossStatus(Loss loss){
		try {
			lossService.updateLossStatus(loss,messageMap);
		} catch (Exception e) {
			logger.error("LossController>>>>>>>>updateLossStatus",e);
		}
		return messageMap;
	}
}
