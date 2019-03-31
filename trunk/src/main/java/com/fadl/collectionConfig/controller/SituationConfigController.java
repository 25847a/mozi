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
import com.fadl.collectionConfig.entity.SituationConfig;
import com.fadl.collectionConfig.service.SituationConfigService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 采浆情况设置表 前端控制器
 * </p>
 * @author caijian&guokang
 * @since 2018-04-21
 */
@Controller
@RequestMapping("/situation")
public class SituationConfigController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(SituationConfigController.class);

	@Autowired
	SituationConfigService situationConfigService;

	/**
	 * 跳转页面采浆情况设置表
	 * @return
	 */
	@RequestMapping("/situationConfig")
	@RequiresPermissions("situationConfig:view")
	public String situationConfig() {
		return "/collectionConfig/situation";
	}
	
	/**
	 * 跳转采浆情况设置新增页面
	 * @return
	 */
	@RequestMapping("/situationConfigAdd")
	@RequiresPermissions("situationConfig:add")
	public String situationConfigAdd() {
		return "/collectionConfig/situation_add";
	}
	
	/**
	 * 跳转采浆情况设置修改页面
	 * @return
	 */
	@RequestMapping("/situationConfigDetails")
	@RequiresPermissions("situationConfig:update")
	public String situationConfigDetails(String id,Model model ) {
		try {
			SituationConfig situationConfig = new SituationConfig();
			situationConfig = situationConfigService.selectById(id);
    		model.addAttribute("situationConfig",situationConfig);
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>situationConfigDetails>>>>>",e);
		}
		return "/collectionConfig/situation_details";
	}

	/**
	 * 采浆情况设置表列表
	 * @param situationConfig
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/situationconfigList",method= RequestMethod.POST)
	@InvokeLog(name = "situationconfigList", description = "采浆情况设置表列表")
	@ResponseBody
	@RequiresPermissions("situationConfig:view")
	public DataRow situationconfigList(SituationConfig situationConfig, Integer page, Integer limit) {
		Page<SituationConfig> paging = null;
		try {
			EntityWrapper<SituationConfig> ew = new EntityWrapper<SituationConfig>();
			ew.where("isDelete=0");
			ew.like("name", situationConfig.getName(), SqlLike.DEFAULT);
			paging = new Page<SituationConfig>(page, limit);
			paging = situationConfigService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>>>>>situationconfigList" + e);
		}
		return messageMap;
	}
	/**
	 * 新增采浆情况设置表
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/insertSituationconfig",method= RequestMethod.POST)
	@InvokeLog(name = "insertSituationconfig", description = "新增采浆情况设置表")
	@ResponseBody
	@RequiresPermissions("situationConfig:add")
	public DataRow insertSituationconfig(SituationConfig situationConfig) {
		try {
			situationConfig.setIsDelete(0);
			situationConfigService.insertSituationconfig(situationConfig,messageMap);
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>>>>>insertSituationconfig" + e);
		}
		return messageMap;
	}

	/**
	 * 修改采浆情况设置表
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/updateSituationconfig",method= RequestMethod.POST)
	@InvokeLog(name = "updateSituationconfig", description = "修改采浆情况设置表")
	@ResponseBody
	@RequiresPermissions("situationConfig:update")
	public DataRow updateSituationconfig(SituationConfig situationConfig) {
		try {
			situationConfigService.updateSituationconfig(situationConfig,messageMap);
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>>>>>updateSituationconfig" + e);
		}
		return messageMap;
	}
	
	/**
	 * 删除采浆情况设置表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSituationconfig",method= RequestMethod.POST)
	@InvokeLog(name = "deleteSituationconfig", description = "删除采浆情况设置表")
	@ResponseBody
	@RequiresPermissions("situationConfig:del")
	public DataRow deleteSituationconfig(@RequestParam Long ids) {
		try {
			SituationConfig situationConfig = new SituationConfig();
			situationConfig.setIsDelete(1);
			situationConfig.setId(ids);
			boolean res = situationConfigService.updateById(situationConfig);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>>>>>deleteSituationconfig" + e);
		}
		return messageMap;
	}

	/**
	 * 查询采浆情况设置列表
	 * @return
	 */
	@RequestMapping(value = "/querySituationConfigList",method= RequestMethod.POST)
	@InvokeLog(name = "querySituationConfigList", description = "查询采浆情况设置列表")
	@ResponseBody
	public DataRow querySituationConfigList(){
		try {
			EntityWrapper<SituationConfig> ew = new EntityWrapper<SituationConfig>();
			ew.where("isDelete=0");
			List<SituationConfig> list = situationConfigService.selectList(null);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("SituationConfigController>>>>>>>>>>>>>querySituationConfigList" + e);
		}
		return messageMap;
	}
}
