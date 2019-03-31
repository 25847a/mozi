package com.fadl.supplies.controller;

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

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Destroy;
import com.fadl.supplies.service.DestroyService;
import com.fadl.supplies.service.StreamService;

/**
 * <p>
 * 耗材销毁信息表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-05-03
 */
@Controller
@RequestMapping("/destroy")
public class DestroyController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(DestroyController.class);
	
	@Autowired
	DestroyService destroyService;
	@Autowired
	StreamService streamService;
	/**
	 * 跳转报损页面
	 * @return
	 */
	@RequestMapping(value="destroyPage")
	@RequiresPermissions("destroy:view")
	public String lossPage(){
		return "/supplies/destroy/destroy";
	}
	/**
	 * 跳转报损新增页面
	 * @return
	 */
	@RequestMapping(value="/destroyAdd")
	@RequiresPermissions("destroy:add")
	public String returnAdd() {
		return "/supplies/destroy/destroy_add";
	}
	/**
	 * 跳转报损查询
	 * @return
	 */
	@RequestMapping(value="/queryDestroy")
	@RequiresPermissions("destroy:list")
	public String queryDestroy() {
		return "/supplies/destroy/destroyQuery";
	}
	/**
	 * 跳转详情页面
	 * @param destroyOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/destroyDatelis")
	@RequiresPermissions("destroy:details")
	public String destroyDatelis(String destroyOrder,Model model) {
		model.addAttribute("destroyOrder",destroyOrder);
		return "/supplies/destroy/destroy_datelis";
	}
	/**
	 * 跳转修改页面
	 * @param destroyOrder
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/destroyUpdate")
	@RequiresPermissions("destroy:update")
	public String destroyUpdate(String destroyOrder,String reason,Model model) {
		model.addAttribute("destroyOrder",destroyOrder);
		model.addAttribute("reason",reason);
		return "/supplies/destroy/destroy_update";
	}
	
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @param map
	 * @param page
	 * @param limt
	 * @return
	 */
	@RequestMapping(value="/queryDestroyPage")
	@ResponseBody
	public DataRow queryDestroyPage(Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
			pageing = new Page<DataRow>(page,limit);
			streamService.queryStreamInfo(pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>queryDestroyPage",e);
		}
		return messageMap;
	}
	/**
	 * 插入到耗材报损表里面
	 * @param suppliesLoss
	 * @return
	 */
	@RequestMapping(value="/insertDestroy")
	@InvokeLog(name = "insertDestroy", description = "插入到耗材报损表里面")
	@ResponseBody
	@RequiresPermissions("destroy:add")
	public DataRow insertDestroy(@RequestParam Map<String,String> map) {
		try {
			String list =map.get("list").replace(",[]", "").replace("[],", "");
			map.put("list", list);
			destroyService.insertDestroy(map,messageMap);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>insertDestroy",e);
		}
		return messageMap;
	}
	/**
	 * 耗材销毁首页列表
	 * @param retur
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryDestroyList")
	@InvokeLog(name = "queryDestroyList", description = "耗材销毁首页列表")
	@ResponseBody
	@RequiresPermissions("destroy:view")
	public DataRow queryDestroyList(Destroy destroy,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	destroyService.queryDestroyList(pageing,destroy);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>queryDestroyList",e);
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
	@RequestMapping(value="queryDestroyDatelis")
	@InvokeLog(name = "queryDestroyDatelis", description = "耗材销毁详情页面")
	@ResponseBody
	@RequiresPermissions("destroy:details")
	public DataRow queryDestroyDatelis(@RequestParam Map<String,String> map,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	destroyService.queryDestroyDatelis(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>queryDestroyDatelis",e);
		}
		return messageMap;
	}
	/**
	 * 修改销毁订单信息
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateDestroy")
	@InvokeLog(name = "updateDestroy", description = "修改销毁订单信息")
	@ResponseBody
	@RequiresPermissions("destroy:update")
	public DataRow updateDestroy(Destroy destroy){
		try {
			destroyService.updateDestroy(destroy,messageMap);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>updateDestroy",e);
		}
		return messageMap;
	}
	/**
	 * 修改销毁订单状态
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateDestroyStatus")
	@InvokeLog(name = "updateDestroyStatus", description = "修改销毁订单状态")
	@ResponseBody
	@RequiresPermissions("destroy:status")
	public DataRow updateDestroyStatus(Destroy destroy){
		try {
			destroyService.updateDestroyStatus(destroy,messageMap);
		} catch (Exception e) {
			logger.error("DestroyController>>>>>>>>updateDestroyStatus",e);
		}
		return messageMap;
	}
}
