package com.fadl.inspection.controller;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.entity.TestConfig;
import com.fadl.inspection.service.TestConfigService;
import com.fadl.supplies.entity.Supply;
import com.fadl.supplies.service.SupplyService;

/**
 * <p>
 * 检验配置表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
@Controller
@RequestMapping("/testConfig")
public class TestConfigController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(TestConfigController.class);

	@Autowired
	private TestConfigService testConfigService;
	@Autowired
	private SupplyService supplyService; 
	
	
	/**
     * 跳转检验配置页面
     * @return
     */
    @RequestMapping("/initPage")
    @RequiresPermissions("testc:view")
    public String initPage() {
        return "/business/inspection/intoconig";
    }
    
	/**
     * 跳转检验项目设置-默认配置页面
     * @return
     */
    @RequestMapping("/bussetup")
    @RequiresPermissions("testc:view1")
    public String bussetup(@NotEmpty Long id, Model model) {
    	TestConfig config = testConfigService.selectById(id);
    	List<Supply> supplies = supplyService.selectList(null);
    	
    	model.addAttribute("supplies", supplies);
    	model.addAttribute("config", config);
    	
        return "/popup/bus/bussetup";
    }
	
	/**
     * 跳转检验配置参数配置页面
     * @return
     */
    @RequestMapping("/busintoconig")
    @RequiresPermissions("testc:view")
    public String busintoconig(@NotEmpty Long id, Model model) {
    	try {
    	TestConfig config = testConfigService.selectById(id);
    	model.addAttribute("config", config);
    	} catch (Exception e) {
    		logger.error("TestConfigController>>>>>>>>>busintoconig>>>>>", e);
    	}
        return "/popup/bus/busintoconig";
    }
    /**
     * 跳转检验配xinzeng页面
     * @return
     */
    @RequestMapping("/intoconigAdd")
    @RequiresPermissions("testc:view")
    public String intoconigAdd(Model model, String id) {
    	model.addAttribute("id", id);
        return "/business/inspection/intoconig_add";
    }
	
	/**
	 * 根据ID获取检测配置信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryTestConfigById", method = RequestMethod.POST)
	@InvokeLog(name = "queryTestConfigById", description = "根据ID获取检测配置信息")
	@ResponseBody
	@RequiresPermissions("testc:view")
	public DataRow queryTestConfigById(@NotEmpty Long id) {
		try {
			TestConfig config = testConfigService.selectById(id);
			messageMap.initSuccess(config);
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>queryTestConfigById>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;

	}

	/**
	 * 获取所有检测配置列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryTestConfigList", method = RequestMethod.POST)
	@InvokeLog(name = "queryTestConfigList", description = "获取所有检测配置列表")
	@ResponseBody
	@RequiresPermissions("testc:view")
	public DataRow queryTestConfigList() {
		try {
			EntityWrapper<TestConfig> ew=new EntityWrapper<TestConfig>();
            ew.setEntity(new TestConfig());
            ew.eq("delFlag", "0");
			List<TestConfig> configs = testConfigService.selectList(ew);
		
			messageMap.initSuccess(configs);
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>queryTestConfigList>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;

	}

	/**
	 * 获取所有有效检测配置列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryTestConfigListByEffective", method = RequestMethod.POST)
	@InvokeLog(name = "queryTestConfigListByEffective", description = "获取所有有效检测配置列表")
	@ResponseBody
	@RequiresPermissions("testc:view")
	public DataRow queryTestConfigListByEffective() {
		try {
			EntityWrapper<TestConfig> ew=new EntityWrapper<TestConfig>();
            ew.setEntity(new TestConfig());
            ew.eq("status", "1");
            ew.eq("delFlag", "0");
            ew.orderBy("isDefault", false);
            ew.orderBy("createDate", false);
			List<TestConfig> configs = testConfigService.selectList(ew);
		
			messageMap.initSuccess(configs);
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>queryTestConfigListByEffective>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;

	}
	/**
	 * 保存检测配置信息
	 * 
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/updateTestConfig", method = RequestMethod.POST)
	@InvokeLog(name = "updateTestConfig", description = "保存检测配置信息")
	@ResponseBody
	@RequiresPermissions("testc:edit")
	public DataRow updateTestConfig(TestConfig testConfig) {
		try {
			TestConfig testConfig1 = testConfigService.selectById(testConfig.getId());
			testConfig1.setTemplateName(testConfig.getTemplateName());
			testConfig1.setCmDate(testConfig.getCmDate());
			testConfig1.setStatus(testConfig.getStatus());
			testConfig1.setIsDefault(testConfig.getIsDefault());
			testConfigService.insertOrUpdate(testConfig);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>updateTestConfig>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}

	/**
	 * 更新蛋白值配置根据配置ID
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/updateTestConfigWithProtein", method = RequestMethod.POST)
	@InvokeLog(name = "updateTestConfigWithProtein", description = "更新蛋白值配置根据配置ID")
	@ResponseBody
	@RequiresPermissions("testc:edit")
	public DataRow updateTestConfigWithProtein(TestConfig testConfig) {
		try {
			testConfigService.updateWithProtein(testConfig);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>updateTestConfigWithProtein>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 重置配置信息
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/updateTestConfigWithDefault", method = RequestMethod.POST)
	@InvokeLog(name = "updateTestConfigWithDefault", description = "重置配置信息")
	@ResponseBody
	@RequiresPermissions("testc:edit")
	public DataRow updateTestConfigWithDefault(TestConfig testConfig) {
		try {
			TestConfig testConfig1 = testConfigService.selectById(testConfig.getId());
			testConfig1.setIsDefault(1);
			testConfigService.insertOrUpdate(testConfig1);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>updateTestConfigWithDefault>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}

	/**
	 * 添加检测配置信息
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/insertTestConfig", method = RequestMethod.POST)
	@InvokeLog(name = "insertTestConfig", description = "添加检测配置信息")
	@ResponseBody
	@RequiresPermissions("testc:save")
	public DataRow insertTestConfig(TestConfig testConfig) {
	
		try {
			if(null!=testConfig.getId()&&testConfig.getId()>0l) {
				return updateTestConfig(testConfig);
			}
			testConfigService.insert(testConfig);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>insertTestConfig>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}

	/**
	 * 删除检测配置信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTestConfig", method = RequestMethod.POST)
	@InvokeLog(name = "deleteTestConfig", description = "删除检测配置信息")
	@ResponseBody
	@RequiresPermissions("testc:delete")
	public DataRow deleteTestConfig(@RequestParam(value = "id", defaultValue = "0") Long id) {
		try {
			if (id.intValue() == 0) {
				messageMap.initFial("错误的配置信息主键");
			}
			testConfigService.deleteByIdWithDelFlag(id);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigController>>>>>>>>>deleteTestConfig>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}

}
