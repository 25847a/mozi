package com.fadl.rabatUnit.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.rabatUnit.entity.RabatUnit;
import com.fadl.rabatUnit.service.RabatUnitService;

/**
 * <p>
 * 胸片检查单位表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-09
 */
@Controller
@RequestMapping("/rabatUnit")
public class RabatUnitController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(RabatUnitController.class);
	
	@Autowired
	public RabatUnitService rabatUnitService;
	
	/**
	 * 跳转体检单位设置页面
	 * @return
	 */
	@RequestMapping("/rabatUnitList")
	public String rabatUnitList(){
		return "/collectionConfig/rabat_unit";
	}
	
	/**
	 * 添加体检单位页面
	 * @return
	 */
	@RequestMapping("/addRabatUnit")
	public String addRabatUnit(){
		return "/collectionConfig/rabat_unit_add";
	} 
	
	/**
	 * 胸片检查单位列表
	 * @return
	 */
	@RequestMapping(value="/queryRabatUnitList",method = RequestMethod.POST)
	@InvokeLog(name = "queryRabatUnitList", description = "胸片检查单位列表")
	@ResponseBody
	public DataRow queryRabatUnitList(){
		try {
			EntityWrapper<RabatUnit> ew = new EntityWrapper<RabatUnit>();
			ew.eq("isDelete", 0);
			List<RabatUnit> list = rabatUnitService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("RabatUnitController>>>>>>>>>queryAllRabatUnitList",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询胸片详情
	 * @return
	 */
	@RequestMapping("/queryRabatUnitById")
	@InvokeLog(name = "queryRabatUnitById", description = "查询胸片详情")
	public String queryRabatUnitById(@RequestParam Long id,Model model){
		try {
			RabatUnit rabatUnit =  rabatUnitService.selectById(id);
			model.addAttribute("rabatUnit",rabatUnit);
		} catch (Exception e) {
			logger.error("RabatUnitController>>>>>>>>>queryAllRabatUnitList",e);
		}
		return "/collectionConfig/rabat_unit_detail";
	}
	
	/**
	 * 添加胸片检查单位
	 * @return
	 */
	@RequestMapping(value="/addRabatUnit",method = RequestMethod.POST)
	@InvokeLog(name = "addRabatUnit", description = "添加胸片检查单位")
	@ResponseBody
	@RequiresPermissions("rabatUnit:add")
	public DataRow addRabatUnit(RabatUnit rabatUnit){
		try {
			rabatUnitService.addRabatUnit(rabatUnit,messageMap);
		} catch (Exception e) {
			logger.error("RabatUnitController>>>>>>>>>addRabatUnit",e);
		}
		return messageMap;
	}
	
	/**
	 * 修改胸片检查单位
	 * @return
	 */
	@RequestMapping(value="/updateRabatUnit",method = RequestMethod.POST)
	@InvokeLog(name = "updateRabatUnit", description = "修改胸片检查单位")
	@ResponseBody
	@RequiresPermissions("rabatUnit:update")
	public DataRow updateRabatUnit(RabatUnit rabatUnit){
		try {
			rabatUnitService.updateRabatUnit(rabatUnit,messageMap);
		} catch (Exception e) {
			logger.error("RabatUnitController>>>>>>>>>updateRabatUnit",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除胸片检查单位
	 * @return
	 */
	@RequestMapping(value="/deleteRabatUnit",method = RequestMethod.POST)
	@InvokeLog(name = "deleteRabatUnit", description = "删除胸片检查单位")
	@ResponseBody
	@RequiresPermissions("rabatUnit:del")
	public DataRow deleteRabatUnit(@RequestParam Long ids){
		try {
			RabatUnit rabatUnit = new RabatUnit();
			rabatUnit.setId(ids);
			rabatUnit.setIsDelete(1);
			boolean res = rabatUnitService.updateById(rabatUnit);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("RabatUnitController>>>>>>>>>deleteRabatUnit",e);
		}
		return messageMap;
	}
}


