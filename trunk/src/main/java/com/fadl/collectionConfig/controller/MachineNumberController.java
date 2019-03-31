package com.fadl.collectionConfig.controller;

import java.util.List;
import java.util.Map;

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
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.collectionConfig.entity.MachineNumber;
import com.fadl.collectionConfig.entity.PlasmType;
import com.fadl.collectionConfig.service.MachineNumberService;
import com.fadl.collectionConfig.service.PlasmTypeService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 采浆机号 前端控制器
 * </p>
 *
 * @author caijian&guokang
 * @since 2018-04-21
 */
@Controller
@RequestMapping("/room")
public class MachineNumberController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(MachineNumberController.class);
	
	@Autowired
	MachineNumberService machineNumberService;
	@Autowired
	PlasmTypeService plasmTypeService;
	
	/**
	 * 跳转采浆机号页面
	 * @return
	 */
	@RequestMapping("/machineNumber")
	@RequiresPermissions("machineNumber:view")
	public String machineNumber() {
		return "/collectionConfig/machineNumber";
	}
	
	/**
	 * 跳转采浆机号新增页面
	 * @return
	 */
	@RequestMapping("/machineNumberAdd")
	@RequiresPermissions("machineNumber:add")
	public String machineNumberAdd() {
		return "/collectionConfig/machineNumber_add";
	}
	
	/**
	 * 跳转采浆机号修改页面
	 * @return
	 */
	@RequestMapping("/machineNumberDetails")
	@RequiresPermissions("machineNumber:update")
	public String MachineNumber(String id,Model model ) {
		try {
			MachineNumber	machineNumber = machineNumberService.selectById(id);
			PlasmType plasmType  = plasmTypeService.selectById(machineNumber.getPlasmTypeId());
    		model.addAttribute("plasmType",plasmType);
    		model.addAttribute("machineNumber",machineNumber);
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>machineNumberDetails>>>>>",e);
		}
		return "/collectionConfig/machineNumber_details";
	}
	
	/**
	 * 采浆机号列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryMachineNumberList",method=RequestMethod.POST)
	@InvokeLog(name = "queryMachineNumberList", description = "采浆机号列表")
	@ResponseBody
	@RequiresPermissions("machineNumber:view")
	public DataRow queryMachineNumberList(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing = new Page<DataRow>(page,limit);
			pageing = machineNumberService.queryMachineNumberList(map, pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>>>>>queryMachineNumberList",e);
		}
		return messageMap;
	}
	/**
	 * 新增采浆机号
	 * @param machineNumber
	 * @return
	 */
	@RequestMapping(value = "/insertMachineNumber",method= RequestMethod.POST)
	@InvokeLog(name = "insertMachineNumber", description = "新增采浆机号")
	@ResponseBody
	@RequiresPermissions("machineNumber:add")
	public DataRow insertMachineNumber(MachineNumber machineNumber) {
		try {
			 machineNumber.setIsDelete(0);
			 machineNumberService.insertMachineNumber(machineNumber,messageMap);
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>>>>>insertMachineNumber",e);
		}
		return messageMap;
	}
	/**
	 * 修改采浆机号
	 * @param machineNumber
	 * @return
	 */
	@RequestMapping(value = "/updateMachineNumber",method= RequestMethod.POST)
	@InvokeLog(name = "updateMachineNumber", description = "修改采浆机号")
	@ResponseBody
	@RequiresPermissions("machineNumber:update")
	public DataRow updateMachineNumber(MachineNumber machineNumber) {
		try {
			machineNumberService.updateMachineNumber(machineNumber,messageMap);
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>>>>>updateMachineNumber",e);
		}
		return messageMap;
	}
	/**
	 * 删除采浆机号
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteMachineNumber",method= RequestMethod.POST)
	@InvokeLog(name = "deleteMachineNumber", description = "删除采浆机号")
	@ResponseBody
	@RequiresPermissions("machineNumber:del")
	public DataRow deleteMachineNumberId(@RequestParam Long ids) {
		try {
			MachineNumber machineNumber = new MachineNumber();
			machineNumber.setIsDelete(1);
			machineNumber.setId(ids);
			boolean res = machineNumberService.updateById(machineNumber);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>>>>>deleteMachineNumber",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询采浆机号表
	 * @return
	 */
	@RequestMapping(value="/queryMachineNumberById",method= RequestMethod.POST)
	@InvokeLog(name = "queryMachineNumberById", description = "查询采浆机号表")
	@ResponseBody
	public DataRow queryMachineNumberById(@RequestParam Long plasmTypeId) {
		try {
			EntityWrapper<MachineNumber> ew = new EntityWrapper<MachineNumber>();
			ew.where("isDelete=0");
			ew.eq("plasmTypeId", plasmTypeId);
			List<MachineNumber> list =machineNumberService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("MachineNumberController>>>>>>>>>>>>>queryMachineNumberById",e);
			messageMap.initFial();
		}
		return messageMap;
	}
}
