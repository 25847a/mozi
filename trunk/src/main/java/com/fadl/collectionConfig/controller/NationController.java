package com.fadl.collectionConfig.controller;

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

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.collectionConfig.entity.Nation;
import com.fadl.collectionConfig.service.NationService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 民族表 前端控制器
 * </p>
 *
 * @author guokang
 * @since 2018-05-31
 */
@Controller
@RequestMapping("/nation")
public class NationController extends AbstractController {
	private static Logger logger = LoggerFactory.getLogger(NationController.class);
	@Autowired
	NationService nationService;
	
	/**
	 * 跳转民族页面
	 * @return
	 */
	@RequestMapping("/nation")
	@RequiresPermissions("nation:view")
	public String nation() {
		return "/collectionConfig/nation";
	}
	
	/**
	 * 跳转民族新增页面
	 * @return
	 */
	@RequestMapping("/nationAdd")
	@RequiresPermissions("nation:add")
	public String nationAdd() {
		return "/collectionConfig/nation_add";
	}
	
	/**
	 * 跳转民族修改页面
	 * @return
	 */
	@RequestMapping("/nationDetails")
	@RequiresPermissions("nation:update")
	public String nationDetails(String id,Model model ) {
		try {
			Nation nation = new Nation();
			nation = nationService.selectById(id);
    		model.addAttribute("nation",nation);
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>nationDetails>>>>>",e);
		}
		return "/collectionConfig/nation_details";
	}
	
	/**
	 * 民族列表
	 * @param nation
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryNationList",method= RequestMethod.POST)
	@InvokeLog(name = "queryNationList", description = "民族列表")
	@ResponseBody
	@RequiresPermissions("nation:view")
	public DataRow queryNationList(Nation nation, Integer page, Integer limit) {
		Page<Nation> paging = null;
		try {
			EntityWrapper<Nation> ew=new EntityWrapper<Nation>();
	        ew.where("isDelete=0");
	        ew.like("name", nation.getName(),SqlLike.DEFAULT);
			paging = new Page<Nation>(page, limit);
			paging = nationService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>>>>>queryNationList",e);
		}
		return messageMap;
	}
	/**
	 * 新增民族
	 * @param nation
	 * @return
	 */
	@RequestMapping(value = "/insertNation",method= RequestMethod.POST)
	@InvokeLog(name = "insertNation", description = "新增民族")
	@ResponseBody
	@RequiresPermissions("nation:add")
	public DataRow insertNation(Nation nation) {
		try {
			nation.setIsDelete(0);
			nationService.insertNation(nation,messageMap);
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>>>>>insertNation",e);
		}
		return messageMap;
	}

	/**
	 * 修改民族
	 * @param nation
	 * @return
	 */
	@RequestMapping(value = "/updateNation",method= RequestMethod.POST)
	@InvokeLog(name = "updateNation", description = "修改民族")
	@ResponseBody
	@RequiresPermissions("nation:update")
	public DataRow updateNation(Nation nation) {
		try {
			nationService.updateNation(nation,messageMap);
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>>>>>updateNation",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除采室表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteNation",method= RequestMethod.POST)
	@InvokeLog(name = "deleteNation", description = "删除采室表")
	@ResponseBody
	@RequiresPermissions("nation:del")
	public DataRow deleteNation(@RequestParam Long ids) {
		try {
			Nation nation = new Nation();
			nation.setIsDelete(1);
			nation.setId(ids);
			boolean res = nationService.updateById(nation);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>>>>>deleteNation",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询民族列表
	 * @return
	 */
	@RequestMapping(value="/queryNationByPlasmType",method= RequestMethod.POST)
	@InvokeLog(name = "queryNationByPlasmType", description = "查询民族列表")
	@ResponseBody
	public DataRow queryNationByPlasmType() {
		try {
			EntityWrapper<Nation> ew = new EntityWrapper<Nation>();
			ew.where("isDelete=0");
			List<Nation> list =nationService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("NationController>>>>>>>>>>>>>queryNationByPlasmType",e);
			messageMap.initFial();
		}
		return messageMap;
	}
}

