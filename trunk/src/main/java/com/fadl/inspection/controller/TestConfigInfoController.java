package com.fadl.inspection.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.TestConfigInfoService;

/**
 * <p>
 * 检验配置记录表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
@RestController
@RequestMapping("/testConfigInfo")
public class TestConfigInfoController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(TestConfigInfoController.class);
	
	@Autowired
	private TestConfigInfoService testConfigInfoService;
	
	/**
	 * 根据ID获取检测配置信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryTestConfigInfoListByTcid", method = RequestMethod.POST) 
	@InvokeLog(name = "queryTestConfigInfoListByTcid", description = "根据ID获取检测配置信息")
	@ResponseBody 
	@RequiresPermissions("testci:view")
	public DataRow queryTestConfigInfoListByTcid(@RequestParam Long tcid, Integer page, Integer limit){
		try {
			Page<DataRow> dr = new Page<DataRow>(page, limit);
			messageMap.initPage(testConfigInfoService.queryTestConfigInfoListByTcid(dr, tcid));
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>queryTestConfigInfoListByTcid>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID获取检测配置信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryTestConfigInfoById", method = RequestMethod.POST) 
	@InvokeLog(name = "queryTestConfigInfoById", description = "根据ID获取检测配置信息")
	@ResponseBody 
	@RequiresPermissions("testci:view")
	public DataRow queryTestConfigInfoById(@NotEmpty Long id) {
		try {
			TestConfigInfo config = testConfigInfoService.selectById(id);
			messageMap.initSuccess(config);
		}catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>queryTestConfigInfoById>>>>>",e);
			messageMap.initFial();
		}
		return messageMap; 
	}
	
	/**
	 * 根据检测配置ID获取详细信息的列表集合
	 * @param tcId
	 * @return
	 */
	@RequestMapping(value="/queryTestConfigInfoByTcId", method = RequestMethod.POST) 
	@InvokeLog(name = "queryTestConfigInfoByTcId", description = "根据检测配置ID获取详细信息的列表集合")
	@ResponseBody 
	@RequiresPermissions("testci:view")
	public DataRow  queryTestConfigInfoByTcId(@NotEmpty Long tcId, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page, limit);
			messageMap.initPage(testConfigInfoService.queryByTCId(dr,tcId));
		}catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>queryTestConfigInfoByTcId>>>>>",e);
		}
		return messageMap; 
		
	}
	
	/**
	 * 保存检测配置信息
	 * 
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/addTestConfigInfo", method = RequestMethod.POST)
	@InvokeLog(name = "addTestConfigInfo", description = "保存检测配置信息")
	@ResponseBody
	@RequiresPermissions("testci:add")
	public DataRow addTestConfigInfo(TestConfigInfo testConfig) {
		try {
			testConfigInfoService.insert(testConfig);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>updateTestConfigInfo>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 修改检测配置信息
	 * 
	 * @param testConfig
	 * @return
	 */
	@RequestMapping(value = "/updateTestConfigInfo", method = RequestMethod.POST)
	@InvokeLog(name = "updateTestConfigInfo", description = "修改检测配置信息")
	@ResponseBody
	@RequiresPermissions("testci:edit")
	public DataRow updateTestConfigInfo(TestConfigInfo testConfig) {
		try {
			boolean res = testConfigInfoService.updateById(testConfig);
			if (res) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>updateTestConfigInfo>>>>>", e);
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
	@RequiresPermissions("testci:add")
	public DataRow insertTestConfigInfo(TestConfigInfo testConfig) {
		try {
			testConfigInfoService.insert(testConfig);
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>insertTestConfig>>>>>", e);
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
	@RequestMapping(value = "/deleteTestConfigInfo", method = RequestMethod.POST)
	@InvokeLog(name = "deleteTestConfigInfo", description = "删除检测配置信息")
	@ResponseBody
	@RequiresPermissions("testci:delete")
	public DataRow deleteTestConfigInfo(@RequestParam(value = "id", defaultValue = "0") Long id) {
		try {
			if (id.intValue() == 0) {
				messageMap.initFial("错误的配置信息主键");
			}
			testConfigInfoService.deleteByIdWithDelFlag(id);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>deleteTestConfigInfo>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	
	 /**
	 * 根据模板Id查询对应的配置详情信息
	 * @param etId
	 * @return
	 */
	@RequestMapping(value = "/queryByEtId", method = RequestMethod.POST)
	@InvokeLog(name = "queryByEtId", description = "根据模板Id查询对应的配置详情信息")
	@ResponseBody
	@RequiresPermissions("testci:view")
	public DataRow queryByEtId (Long etId){
		try {
			if(etId!= null) {
				messageMap.initSuccess(testConfigInfoService.queryByEtId(etId));
			}else {
				messageMap.initFial("模板ID不能为空");
			}
		} catch (Exception e) {
			logger.error("TestConfigInfoController>>>>>>>>>queryByEtId>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
}

