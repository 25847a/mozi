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
import com.fadl.collectionConfig.service.PlasmTypeService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 采浆机型表 前端控制器
 * </p>
 * @author caijian&guokang
 * @since 2018-04-21
 */
@Controller
@RequestMapping("/room")
public class PlasmTypeController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(PlasmTypeController.class);
	
	@Autowired
	PlasmTypeService plasmTypeService;
	
	/**
	 * 跳转采浆机型页面
	 * @return
	 */
	@RequestMapping("/plasmType")
	@RequiresPermissions("plasmType:view")
	public String plasmType() {
		return "/collectionConfig/plasmType";
	}
	
	/**
	 * 跳转采浆机型新增页面
	 * @return
	 */
	@RequestMapping("/plasmTypeAdd")
	@RequiresPermissions("plasmType:add")
	public String plasmTypeAdd() {
		return "/collectionConfig/plasmType_add";
	}
	
	/**
	 * 跳转采浆机型修改页面
	 * @return
	 */
	@RequestMapping("/plasmTypeDetails")
	@RequiresPermissions("plasmType:update")
	public String plasmTypeDetails(String id,Model model ) {
		try {
			PlasmType plasmType = new PlasmType();
			plasmType = plasmTypeService.selectById(id);
    		model.addAttribute("plasmType",plasmType);
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>plasmTypeDetails>>>>>",e);
		}
		return "/collectionConfig/plasmType_details";
	}
	
	/**
	 * 查询采浆机型表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="queryPlasmTypeList",method=RequestMethod.POST)
	@InvokeLog(name = "queryPlasmTypeList", description = "查询采浆机型表列表")
	@ResponseBody 
	@RequiresPermissions("plasmType:view")
	public DataRow queryPlasmTypeList(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			pageing = new Page<DataRow>(page,limit);
			pageing = plasmTypeService.queryPlasmTypeList(map, pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>>>>>queryPlasmTypeList",e);
		}
		return messageMap;
	}
	/**
	 * 新增采浆机型表
	 * @param plasmType
	 * @return
	 */
	@RequestMapping(value="/insertPlasmType",method=RequestMethod.POST)
	@InvokeLog(name = "insertPlasmType", description = "新增采浆机型表")
	@ResponseBody
	@RequiresPermissions("plasmType:add")
	public DataRow insertPlasmType(PlasmType plasmType) {
		try {
			plasmType.setIsDelete(0);
			plasmTypeService.insertPlasmType(plasmType,messageMap);
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>>>>>insertPlasmType",e);
		}
		return messageMap;
	}
	/**
	 * 修改采浆机型表
	 * @param plasmType
	 * @return
	 */
	@RequestMapping(value="/updatePlasmType",method=RequestMethod.POST)
	@InvokeLog(name = "updatePlasmType", description = "修改采浆机型表")
	@ResponseBody
	@RequiresPermissions("plasmType:update")
	public DataRow updatePlasmType(PlasmType plasmType) {
		try {
			 plasmTypeService.updatePlasmType(plasmType,messageMap);
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>>>>>updatePlasmType",e);
		}
		return messageMap;
	}
	/**
	 * 删除采浆机型表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deletePlasmType",method=RequestMethod.POST)
	@InvokeLog(name = "deletePlasmType", description = "删除采浆机型表")
	@ResponseBody
	@RequiresPermissions("plasmType:del")
	public DataRow deletePlasmType(@RequestParam Long ids) {
		try {
			PlasmType plasmType = new PlasmType();
			EntityWrapper<MachineNumber> ew = new EntityWrapper<MachineNumber>();
			ew.eq("plasmTypeId", ids);
			ew.eq("isDelete", 0);
			//先判断属本机型的采浆机号是否已全部删除
			if(null==new MachineNumber().selectOne(ew)){
				plasmType.setIsDelete(1);
				plasmType.setId(ids);
				boolean res =  plasmTypeService.updateById(plasmType);
				if(res){
					messageMap.initSuccess();
				}else {
					messageMap.initFial();
				}
			}else {
				messageMap.initFial("请先删除属于该机型的机号！");
			}
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>>>>>deletePlasmType",e);
		}
		return messageMap;
	}
	/**
	 * 查询采浆机型表
	 * @return
	 */
	@RequestMapping(value="/queryPlasmTypeByMachine",method= RequestMethod.POST)
	@InvokeLog(name = "queryPlasmTypeByMachine", description = "查询采浆机型表")
	@ResponseBody
	public DataRow queryPlasmTypeByMachine(@RequestParam Long roomId) {
		try {
			EntityWrapper<PlasmType> ew = new EntityWrapper<PlasmType>();
			ew.where("isDelete=0");
			ew.eq("roomId", roomId);
			List<PlasmType> list =plasmTypeService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmTypeController>>>>>>>>>>>>>queryPlasmTypeByMachine",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
}
