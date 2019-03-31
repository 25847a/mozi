package com.fadl.cost.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.cost.entity.LevelConfig;
import com.fadl.cost.service.LevelConfigService;

/**
 * <p>
 * 推荐等级奖励设置 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-17
 */
@Controller
@RequestMapping("/levelConfig")
public class LevelConfigController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(LevelConfigController.class);

	@Autowired
	private LevelConfigService configService;
	
	/**
	 * 邀请设置to绑定信息列表页面（条件查询）
	 * 
	 * */
	@RequestMapping("/toBindFit")
	@RequiresPermissions("inviteFit:view")
	public String toBindFit() {
		return "/cost/bind_fit_list";
	}
	
	/**
	 * 邀请设置to绑定信息列表（条件查询）
	 * 
	 * */
	@RequestMapping("/bindFitList")
	@InvokeLog(name = "bindFitList", description = "邀请设置与绑定信息列表")
	@ResponseBody
	@RequiresPermissions("inviteFit:view")
	public DataRow bindFitList(Integer page,Integer limit,String creater,String level) {
		try {
			Page<DataRow> page2 = new Page<>(page,limit);
			Map<String, String> map = new HashMap<>();
			map.put("creater", creater);
			map.put("level", level);
		
			configService.queryLevelConfigList(map, page2);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("LevelConfigController>>>>>bindFitList>>>",e);
		}
		return messageMap;
	}
	/**
	 * 邀请设置--->新增邀请设置页面
	 * 
	 * */
	@RequestMapping("/toAddBindFit")
	@RequiresPermissions("inviteFit:add")
	public String toAddBindFit() {
		return "/cost/bind_fit_add";
	}
	/**
	 * 邀请设置--->新增邀请设置
	 * 
	 * */
	@RequestMapping("/addBindFit")
	@InvokeLog(name = "addBindFit", description = "新增邀请设置")
	@ResponseBody
	@RequiresPermissions("inviteFit:add")
	public DataRow addBindFit(LevelConfig config) {
		try {
			if(configService.insert(config)){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		}catch (Exception e) {
			logger.error("LevelConfigController>>>>>>addBindFit>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 邀请设置--->修改邀请设置页面
	 * 
	 * */
	@RequestMapping("/toUpdateBindFit")
	@RequiresPermissions("inviteFit:update")
	public String toUpdateBindFit(Long id,Model model) {
		try {
			LevelConfig config = configService.selectById(id);
			model.addAttribute("levelConfig",config);
		} catch (Exception e) {
			logger.error("LevelConfigController>>>>>>toUpdateBindFit>>>>>",e);
		}
		
		return "/cost/bind_fit_detail";
	}
	
	/**
	 * 邀请设置--->更新邀请设置
	 * 
	 * */
	@RequestMapping("/updateBindFit")
	@InvokeLog(name = "updateBindFit", description = "修改邀请设置")
	@ResponseBody
	@RequiresPermissions("inviteFit:update")
	public DataRow updateBindFit(LevelConfig levelConfig) {
		try {
			if(configService.updateById(levelConfig)) {
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("LevelConfigController>>>>>updateBindFit>>>",e);
		}
		
		return messageMap;
	}
	
	/**
	 * 邀请设置--->删除邀请设置
	 * 
	 * */
	@RequestMapping("/deleteBindFit")
	@InvokeLog(name = "deleteBindFit", description = "删除邀请设置")
	@ResponseBody
	@RequiresPermissions("inviteFit:delete")
	public DataRow deleteBindFit(Long ids) {
		try {
			if(configService.deleteById(ids)) {
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("LevelConfigController>>>>>deleteBindFit>>>",e);
		}
		
		return messageMap;
	}
}

