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
import com.fadl.supplies.entity.Return;
import com.fadl.supplies.service.ReturnService;
import com.fadl.supplies.service.StreamService;

/**
 * <p>
 * 耗材退还记录表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
@Controller
@RequestMapping("/return")
public class ReturnController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ReturnController.class);
	
	@Autowired
	ReturnService returnService;
	@Autowired
	StreamService streamService;
	/**
	 * 跳转退还页面
	 * @return
	 */
	@RequestMapping(value="/returnPage")
	@RequiresPermissions("return:view")
	public String lossPage() {
		return "/supplies/return/return";
	}
	/**
	 * 跳转退还新增页面
	 * @return
	 */
	@RequestMapping(value="/returnAdd")
	@RequiresPermissions("return:add")
	public String returnAdd() {
		return "/supplies/return/return_add";
	}
	/**
	 * 跳转详情页面
	 * @param returnOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/returnDatelis")
	@RequiresPermissions("return:details")
	public String returnDatelis(String returnOrder,Model model) {
		model.addAttribute("returnOrder",returnOrder);
		return "/supplies/return/return_datelis";
	}
	/**
	 * 跳转退还查询
	 * @return
	 */
	@RequestMapping(value="/queryReturn")
	@RequiresPermissions("return:list")
	public String queryLoss() {
		return "/supplies/return/returnQuery";
	}
	/**
	 * 跳转修改页面
	 * @param returnOrder
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/returnUpdate")
	@RequiresPermissions("return:list")
	public String returnUpdate(String returnOrder,String reason,Model model) {
		model.addAttribute("returnOrder",returnOrder);
		model.addAttribute("reason",reason);
		return "/supplies/return/return_update";
	}
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @param map
	 * @param page
	 * @param limt
	 * @return
	 */
	@RequestMapping(value="/queryAddReturn")
	@ResponseBody
	public DataRow queryAddReturn(Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
			pageing = new Page<DataRow>(page,limit);
			streamService.queryStreamInfo(pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>queryAddReturn",e);
		}
		return messageMap;
	}
	/**
	 * 插入到耗材报废表里面
	 * @param suppliesLoss
	 * @return
	 */
	@RequestMapping(value="/insertReturn")
	@InvokeLog(name = "insertReturn", description = "插入到耗材报废表里面")
	@ResponseBody
	@RequiresPermissions("return:add")
	public DataRow insertReturn(@RequestParam Map<String,String> map) {
		try {
			String list =map.get("list").replace(",[]", "").replace("[],", "");
			map.put("list", list);
			returnService.insertReturn(map,messageMap);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>insertReturn",e);
		}
		return messageMap;
	}
	/**
	 * 耗材退还首页列表
	 * @param retur
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryReturnList")
	@InvokeLog(name = "queryReturnList", description = "耗材退还首页列表")
	@ResponseBody
	@RequiresPermissions("return:view")
	public DataRow queryReturnList(Return retur,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	returnService.queryReturnList(pageing,retur);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>queryReturnList",e);
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
	@RequestMapping(value="queryReturnDatelis")
	@InvokeLog(name = "queryReturnDatelis", description = "耗材退还详情页面")
	@ResponseBody
	@RequiresPermissions("return:details")
	public DataRow queryReturnDatelis(@RequestParam Map<String,String> map,Integer page,Integer limit) {
		Page<DataRow> pageing =null;
		try {
	    	pageing = new Page<DataRow>(page,limit);
	    	returnService.queryReturnDatelis(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>queryReturnDatelis",e);
		}
		return messageMap;
	}
	/**
	 * 修改退还订单信息
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateReturn")
	@InvokeLog(name = "updateReturn", description = "修改退还订单信息")
	@ResponseBody
	@RequiresPermissions("updateReturn")
	public DataRow updateReturn(Return retur){
		try {
			returnService.updateReturn(retur,messageMap);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>updateReturn",e);
		}
		return messageMap;
	}
	/**
	 * 修改退还订单状态
	 * @param retur
	 * @return
	 */
	@RequestMapping(value="/updateReturnStatus")
	@InvokeLog(name = "updateReturnStatus", description = "修改退还订单状态")
	@ResponseBody
	@RequiresPermissions("return:status")
	public DataRow updateReturnStatus(Return retur){
		try {
			returnService.updateReturnStatus(retur,messageMap);
		} catch (Exception e) {
			logger.error("ReturnController>>>>>>>>updateReturnStatus",e);
		}
		return messageMap;
	}
}
