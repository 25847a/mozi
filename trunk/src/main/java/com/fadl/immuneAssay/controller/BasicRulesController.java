package com.fadl.immuneAssay.controller;


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
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.entity.BasicRules;
import com.fadl.immuneAssay.service.BasicRulesService;

/**
 * <p>
 * 注射规则设置 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-13
 */
@Controller
@RequestMapping("/basicRules")
public class BasicRulesController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(BasicRulesController.class);
	
	@Autowired
	BasicRulesService basicRulesService;
	@Autowired
	private ConfigService configService;
	/**
     * 跳转基础针注册规则设置页面
     */
    @RequestMapping("/basicRulesPage")
    public String basicRulesPage() {
        return "/immune/config/basics";
    }
    /**
     * 跳转加强针注册规则设置页面(加强)
     */
    @RequestMapping("/strongBasicRulesPage")
    public String strongBasicRulesPage() {
        return "/immune/config/strengthen";
    }
	 /**
     * 跳转基础针注册规则设置添加页面
     */
    @RequestMapping("/basicRulesAddPage")
    @RequiresPermissions("basis:view")
    public String basicRulesAddPage() {
        return "/immune/config/basics_add";
    }
    /**
     * 跳转加强针注册规则设置添加页面(加强)
     */
    @RequestMapping("/strongBasicRulesAddPage")
    @RequiresPermissions("strong:list")
    public String strongBasicRulesAddPage() {
        return "/immune/config/strengthen_add";
    }
    /**
   	 * 跳转基础针注册规则设置修改页面
   	 * @param id
   	 * @param model
   	 * @return
   	 */
       @RequestMapping(value = "/basicRulesUpdate")
       public String basicRulesUpdate(String id, Model model) {
       	try {
       		BasicRules basicRules = new BasicRules();
       		basicRules = basicRulesService.selectById(id);
       		model.addAttribute("basicRules",basicRules);
   		} catch (Exception e) {
   			logger.error("BasicRulesController>>>>>>>>>basicRulesUpdate>>>>>",e);
   		}
           return "/immune/config/basics_update";
       }
    /**
	 * 跳转加强针注册规则设置修改页面(加强)
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/strongBasicRulesUpdate")
    public String strongBasicRulesUpdate(String id, Model model) {
    	try {
    		BasicRules basicRules = new BasicRules();
    		basicRules = basicRulesService.selectById(id);
    		model.addAttribute("basicRules",basicRules);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>basicRulesUpdate>>>>>",e);
		}
        return "/immune/config/strengthen_update";
    }
    
    /**
	 * 基础针注册规则设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/basicRulesList", method= RequestMethod.POST)
	@InvokeLog(name = "basicRulesList", description = "基础针注册规则设置列表")
	@RequiresPermissions("basis:list")
	@ResponseBody
	public DataRow basicRulesList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			Config config = configService.queryConfig("immune_type","1");
			if(config.getIsDisable()==1) {
				map.put("type", "1");
			}
			paging = new Page<DataRow>(page, limit);
			basicRulesService.basicRulesList(paging,map);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>basicRulesList",e);
		}
		return messageMap;
	}
	/**
	 * 加强针注册规则设置列表(加强)
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/strongBasicRulesList", method= RequestMethod.POST)
	@InvokeLog(name = "strongBasicRulesList", description = "加强针注册规则设置列表(加强)")
	@RequiresPermissions("strong:list")
	@ResponseBody
	public DataRow strongBasicRulesList(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			Config config = configService.queryConfig("immune_type","1");
			if(config.getIsDisable()==1) {
				map.put("type", "1");
			}
			paging = new Page<DataRow>(page, limit);
			basicRulesService.strongBasicRulesList(paging,map);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>strongBasicRulesList",e);
		}
		return messageMap;
	}
	/**
	 * 新增基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	@RequestMapping(value = "/insertBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "insertBasicRules", description = "新增基础针注册规则设置")
	@RequiresPermissions("basis:add")
	@ResponseBody
	public DataRow insertBasicRules(BasicRules basicRules) {
		try {
			basicRulesService.insertBasicRules(basicRules,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>insertBasicRules",e);
		}
		return messageMap;
	}
	/**
	 * 新增加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	@RequestMapping(value = "/insertStrongBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "insertStrongBasicRules", description = "新增加强针注册规则设置(加强)")
	@RequiresPermissions("strong:add")
	@ResponseBody
	public DataRow insertStrongBasicRules(BasicRules basicRules) {
		try {
			basicRulesService.insertStrongBasicRules(basicRules,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>insertstrongBasicRules",e);
		}
		return messageMap;
	}
	/**
	 * 修改基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	@RequestMapping(value = "/updateBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "updateBasicRules", description = "修改基础针注册规则设置")
	@RequiresPermissions("basis:update")
	@ResponseBody
	public DataRow updateBasicRules(BasicRules basicRules) {
		try {
			basicRulesService.updateBasicRules(basicRules,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>updateBasicRules",e);
		}
		return messageMap;
	}
	/**
	 * 修改加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	@RequestMapping(value = "/updateStrongBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "updateStrongBasicRules", description = "修改加强针注册规则设置(加强)")
	@RequiresPermissions("strong:update")
	@ResponseBody
	public DataRow updateStrongBasicRules(BasicRules basicRules) {
		try {
			basicRulesService.updateStrongBasicRules(basicRules,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>updateBasicRules",e);
		}
		return messageMap;
	}
	/**
	 * 删除基础针注册规则设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "deleteBasicRules", description = "删除基础针注册规则设置")
	@RequiresPermissions("basis:delete")
	@ResponseBody
	public DataRow deleteBasicRules(Long ids) {
		try {
			basicRulesService.deleteBasicRules(ids,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>deleteBasicRules",e);
		}
		return messageMap;
	}
	/**
	 * 删除加强针注册规则设置(加强)
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteStrongBasicRules", method= RequestMethod.POST)
	@InvokeLog(name = "deleteStrongBasicRules", description = "删除加强针注册规则设置(加强)")
	@RequiresPermissions("strong:delete")
	@ResponseBody
	public DataRow deleteStrongBasicRules(Long ids) {
		try {
			basicRulesService.deleteStrongBasicRules(ids,messageMap);
		} catch (Exception e) {
			logger.error("BasicRulesController>>>>>>>>>>>>>deleteStrongBasicRules",e);
		}
		return messageMap;
	}
}

