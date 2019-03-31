package com.fadl.common.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
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
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-11
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ConfigController.class);
	@Autowired
	ConfigService configService;
	
	/**
	 * 跳转设置管理
	 * @return
	 */
	@RequestMapping("/sysConfig")
	public String sysConfig() {
		return "/collectionConfig/configPage";
	}
	
	/**
	 * 跳转系统设置页面
	 * @return
	 */
	@RequestMapping("/config")
	@RequiresPermissions("config:view")
	public String config() {
		return "/collectionConfig/config";
	}
	/**
	 * 跳转新增系统设置页面
	 * @return
	 */
	@RequestMapping("/configAdd")
	@RequiresPermissions("config:add")
	public String configAdd() {
		return "/collectionConfig/config_add";
	}
	/**
	 * 跳转系统设置修改页面
	 * @return
	 */
	@RequestMapping("/configDetails")
	@RequiresPermissions("config:update")
	public String configDetails(String id,Model model ) {
		try {
			Config config = new Config();
			config = configService.selectById(id);
    		model.addAttribute("config",config);
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>configDetails>>>>>",e);
		}
		return "/collectionConfig/config_details";
	}
	
	/**
	 * 查询系统配置列表
	 * @param config
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryConfigList",method= RequestMethod.POST)
	@InvokeLog(name = "queryConfigList", description = "查询系统配置列表")
	@ResponseBody
	@RequiresPermissions("config:view")
	public DataRow queryConfigList(Config config, Integer page, Integer limit) {
		Page<Config> paging = null;
		try {
			EntityWrapper<Config> ew=new EntityWrapper<Config>();
	        ew.where("isDelete=0");
	        ew.like("configName", config.getConfigName(),SqlLike.DEFAULT);
	        if(config.getType()!=null && !"".equals(config.getType())){
	        	ew.eq("type", config.getType());
	        } 
	        if(config.getIsDisable()!=null && !"".equals(config.getIsDisable())){
	        	ew.eq("isDisable", config.getIsDisable());
	        }
			paging = new Page<Config>(page, limit);
			paging = configService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>>>>>queryConfigList",e);
		}
		return messageMap;
	}
	/**
	 * 新增系统设置
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/insertConfig",method= RequestMethod.POST)
	@InvokeLog(name = "insertConfig", description = "新增系统设置")
	@ResponseBody
	@RequiresPermissions("config:add")
	public DataRow insertConfig(Config config) {
		try {
			config.setIsDelete(0);
			configService.insertConfig(config,messageMap);
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>>>>>insertConfig",e);
		}
		return messageMap;
	}
	/**
	 * 修改宣传员
	 * @param propagandist
	 * @return
	 */
	@RequestMapping(value = "/updateConfig",method= RequestMethod.POST)
	@InvokeLog(name = "updateConfig", description = "修改宣传员")
	@ResponseBody
	@RequiresPermissions("config:update")
	public DataRow updateConfig(Config config) {
		try {
			configService.updateConfig(config,messageMap);
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>>>>>updateConfig",e);
		}
		return messageMap;
	}
	/**
	 * 删除系统配置
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteConfig",method= RequestMethod.POST)
	@InvokeLog(name = "deleteConfig", description = "删除系统配置")
	@ResponseBody
	@RequiresPermissions("config:del")
	public DataRow deleteConfig(@RequestParam Long ids) {
		try {
			Config config = new Config();
			config.setIsDelete(1);
			config.setId(ids);
			boolean res = configService.updateById(config);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>>>>>deleteConfig",e);
		}
		return messageMap;
	}
	/**
	 * 查询系统设置类型type列表
	 * @return
	 */
	@RequestMapping(value="/queryConfigType")
	@InvokeLog(name = "queryConfigType", description = "查询系统设置类型type列表")
	@ResponseBody
	public DataRow queryConfigType() {
		try {
			EntityWrapper<Config> ew = new EntityWrapper<Config>();
			ew.where("1=1 and isDelete=0");
			//类型去重
			ew.groupBy("type");
			List<Config> list =configService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("ConfigController>>>>>>>>>>>>>queryConfigType",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 根据Type获取字典集合
	 * @param type
	 * @return
	 */
	//如果取消@RestController请这里加@ResponseBody
	@ResponseBody
	@RequestMapping(value="/queryDictListByType", method= RequestMethod.POST)
	public DataRow queryDictListByType(@NotEmpty String type) {
		try {
			Wrapper<Config> wr= new EntityWrapper<Config>();
			wr.eq("type", type);
			wr.eq("isDelete", 0);
			wr.eq("isDisable", 0);
			messageMap.initSuccess(configService.selectList(wr));
		}catch (Exception e) {
			 logger.error("config>>>>>>>>>queryDictsByType>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据Type和Value获取对应的字典值
	 * @param type
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryDictByTypeAndValue", method= RequestMethod.POST)
	@InvokeLog(name = "queryAdminLogin", description = "根据Type和Value获取对应的字典值")
	public DataRow queryDictByTypeAndValue(@NotEmpty String type, @NotEmpty String value) {
		try {
			Wrapper<Config> wr= new EntityWrapper<Config>();
			wr.eq("type", type);
			wr.eq("value", value);
			List<Config> lst = configService.selectList(wr);
			if(lst.size()>0) {
				messageMap.initSuccess(lst.get(0));
			}
		}catch (Exception e) {
			 logger.error("config>>>>>>>>>queryDictByTypeAndValue>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据Type和label获取对应的字典值
	 * @param type
	 * @param label
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryDictByTypeAndLabel", method= RequestMethod.POST)
	@InvokeLog(name = "queryDictByTypeAndLabel", description = "根据Type和label获取对应的字典值")
	public DataRow queryDictByTypeAndLabel(@NotEmpty String type, @NotEmpty String label){
		try {
			Config config = configService.getConfig(type, label);
			messageMap.initSuccess(config);
		}catch (Exception e) {
			logger.error("config>>>>>>>>>queryDictByTypeAndValue>>>>>",e);
		}
		return messageMap;
	}
	
}

