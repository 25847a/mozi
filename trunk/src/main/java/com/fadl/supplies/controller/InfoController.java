package com.fadl.supplies.controller;
 
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Info;
import com.fadl.supplies.service.InfoService;
/**
 * <p>
 * 耗材信息表 前端控制器
 * </p>
 * 
 * @author 啊健
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/suppliesInfo")
public class InfoController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(InfoController.class);
	
	@Autowired
	InfoService infoService;
	//跳转页面
	@RequestMapping("/suppliesInfoPage")
	@RequiresPermissions("suppliesInfo:view")
	public String suppliesInfo() {
		return "/supplies/config/suppliesInfo";
	}
	//跳转新增页面
	@RequestMapping("/suppliesInfoAdd")
	public String suppliesInfoAdd() {
		return "/supplies/config/supplies_add";
	}
	//跳转修改页面
	@RequestMapping("/suppliesInfoUpdate")
	public String suppliesInfoUpdate(String id,Model model) {
		Info info = new Info();
		info = infoService.selectById(id);
		model.addAttribute("info",info);
		return "/supplies/config/supplies_update";
	}
	

	/**
	 * 查询耗材信息表列表,可使用于订单记录查询
	 * @param suppliesInfo
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/querySuppliesInfoList")
	@InvokeLog(name = "querySuppliesInfoList", description = "查询耗材信息表列表,可使用于订单记录查询")
	@ResponseBody
	public DataRow querySuppliesInfoList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing = new Page<DataRow>(page,limit);
			infoService.querySuppliesInfoList(pageing,map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>querySuppliesInfoList",e);
		}
		return messageMap;
	}

	/**
	 * 新增耗材信息表
	 * @param suppliesInfo
	 * @return
	 */
	@RequestMapping(value = "/insertSuppliesInfo")
	@InvokeLog(name = "insertSuppliesInfo", description = "新增耗材信息表")
	@ResponseBody
	@RequiresPermissions("suppliesInfo:add")
	public DataRow insertSuppliesInfo(Info info) {
		try {
			infoService.insertSuppliesInfo(info,messageMap);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>>insertSuppliesInfo",e);
		}
		return messageMap;
	}

	/**
	 * 修改耗材信息表
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/updateSuppliesInfo")
	@InvokeLog(name = "updateSuppliesInfo", description = "修改耗材信息表")
	@ResponseBody
	@RequiresPermissions("suppliesInfo:update")
	public DataRow updateSuppliesInfo(Info suppliesInfo) {
		try {
			infoService.updateSuppliesInfo(suppliesInfo,messageMap);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>>updateSuppliesInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除耗材信息表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSuppliesInfo")
	@InvokeLog(name = "deleteSuppliesInfo", description = "删除耗材信息表")
	@ResponseBody
	@RequiresPermissions("suppliesInfo:delect")
	public DataRow deleteSuppliesInfo(Long ids) {
		try {
			infoService.deleteSuppliesInfo(ids,messageMap);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>>deleteSuppliesInfo",e);
		}
		return messageMap;
	}
	/**
	 * 查询耗材信息(谢鑫需要用到)
	 * supplyId  供应商ID
	 * @return
	 */
	@RequestMapping(value="/queryInfo")
	@InvokeLog(name = "queryInfo", description = "查询耗材信息")
	@ResponseBody
	public DataRow queryInfo(String supplyId) {
		EntityWrapper<Info>  ew  = new EntityWrapper<Info>();
		ew.eq("supplyId", supplyId).eq("type", 0).eq("apply", 1);
		try {
			List<Info> info = infoService.selectList(ew);
			messageMap.initSuccess(info);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>>queryInfo",e);
		}
		return messageMap;
	}
	/**
	 * 查询耗材信息(疫苗那边用到)
	 * supplyId  供应商ID
	 * @return
	该接口目前用不上(2019-01-09啊健备注)
	@RequestMapping(value="/querysuppliesInfo")
	@InvokeLog(name = "querysuppliesInfo", description = "查询耗材信息(疫苗那边用到)")
	@ResponseBody
	public DataRow querysuppliesInfo() {
		EntityWrapper<Info>  ew  = new EntityWrapper<Info>();
		ew.eq("isDelete", 0);
		ew.eq("disable", 0);
		ew.eq("type", 0);
		try {
			List<Info> info = infoService.selectList(ew);
			messageMap.initSuccess(info);
		} catch (Exception e) {
			logger.error("InfoController>>>>>>>>>>>>>querysuppliesInfo",e);
		}
		return messageMap;
	} */
}
