package com.fadl.elisa.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.elisa.service.ElisaQcService;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.supplies.entity.Stock;
import com.fadl.supplies.service.StockService;

import sun.rmi.transport.tcp.TCPConnection;

/**
 * <p>
 * 酶标板模板使用耗材 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Controller
@RequestMapping("/elisaTemplateSupplies")
public class ElisaTemplateSuppliesController extends AbstractController{

	@Autowired
	private StockService sService;
	@Autowired
	private ElisaQcService qcService;
	@Autowired
	private TestConfigInfoService tciService;
	
	private static Logger logger = LoggerFactory.getLogger(ElisaTemplateSuppliesController.class);
	/**
	 * 跳转耗材配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("etsupp:view")
	public String initPage() {
		
		return "/business/elisa/elisa_haoc";
	}
	
	/**
	 * 跳转耗材配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/gotoAdd")
	@RequiresPermissions("etsupp:view")
	public String gotoAdd() {
		return "/business/elisa/elisahao_add";
	}
	/**
	 * 根据状态查询耗材或者质控品信息 目前后面2个参数没实现
	 * @param stock
	 * @param page  
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySupplies", method=RequestMethod.POST)
	@InvokeLog(name = "querySupplies", description = "根据状态查询耗材或者质控品信息")
	@RequiresPermissions("etsupp:view")
	public DataRow querySupplies(Stock stock, Integer page, Integer limit) {
		try {
			Wrapper<Stock> ew = new EntityWrapper<Stock>();
			ew.eq("status", stock.getStatus());
			ew.eq("type", stock.getType());
			ew.orderBy("expiryTime");
			messageMap.initSuccess(sService.selectList(ew));
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>querySupplies>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据状态查询耗材或者质控品信息 目前后面2个参数没实现
	 * @param stock
	 * @param page  
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySuppliesQC", method=RequestMethod.POST)
	@InvokeLog(name = "querySuppliesQC", description = "查询出库的有效的质控品")
	@RequiresPermissions("etsupp:view")
	public DataRow querySuppliesQC( Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page,limit);
			Page<DataRow> page2 = sService.queryOutNumberInfo(dr);
			messageMap.initPage(page2);
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>querySuppliesQC>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID更新状态为启用
	 * @param stock
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateStockEnableById", method=RequestMethod.POST)
	@InvokeLog(name = "updateStockEnableById", description = "根据ID更新状态为启用")
	@RequiresPermissions("etsupp:edit")
	public DataRow updateStockEnableById(Stock stock) {
		try {
			Stock stock2 = sService.selectById(stock.getId());
			stock2.setStatus(0);
			if(sService.updateById(stock2))
				messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>updateStockStatusById>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID更新状态为禁用
	 * @param stock
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateStockUnEnableById", method=RequestMethod.POST)
	@InvokeLog(name = "updateStockUnEnableById", description = "根据ID更新状态为禁用")
	@RequiresPermissions("etsupp:edit")
	public DataRow updateStockUnEnableById(Stock stock) {
		try {
			Stock stock2 = sService.selectById(stock.getId());
			stock2.setStatus(1);
			if(sService.updateById(stock2))
				messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>updateStockStatusById>>>>>", e);
		}
		return messageMap;
	}
	
	
	/**
	 * 根据ID查询耗材的基础信息
	 * @param stock
	 * @param page  
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryStockById", method=RequestMethod.POST)
	@InvokeLog(name = "queryStockById", description = "根据ID查询耗材的基础信息")
	@RequiresPermissions("etsupp:view")
	public DataRow queryStockById(Long  id) {
		try {
			;
			messageMap.initSuccess(sService.queryById(id));
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>queryStockById>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 保存质控品对应的检测方法和检测项目
	 * @param elisaQc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveElisaQC", method=RequestMethod.POST)
	@InvokeLog(name = "saveElisaQC", description = "根据ID查询耗材的基础信息")
	@RequiresPermissions("etsupp:edit")
	public DataRow saveElisaQC(ElisaQc elisaQc) {
		try {
			messageMap.initSuccess(qcService.insert(elisaQc));
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>saveElisaQC>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据检测项目Id查询质控品的基础信息
	 * @param elisaQc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryQCList", method=RequestMethod.POST)
	@InvokeLog(name = "queryQCList", description = "根据检测项目Id查询质控品的基础信息")
	@RequiresPermissions("etsupp:view")
	public DataRow queryQCList(ElisaQc elisaQc) {
		try {
			messageMap.initSuccess(qcService.querySuppliesListByProjectId(elisaQc));
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>queryQCList>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据检测项目Id查询试剂的基础信息
	 * @param elisaQc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySuppliesList", method=RequestMethod.POST)
	@InvokeLog(name = "querySuppliesList", description = "根据检测项目Id查询试剂的基础信息")
	@RequiresPermissions("etsupp:view")
	public DataRow querySuppliesList(TestConfigInfo tci) {
		try {
			messageMap.initSuccess(tciService.querySuppliesListByProjectId(tci));
		}catch (Exception e) {
			logger.error("ElisaTemplateSuppliesController>>>>>>>>>querySuppliesList>>>>>", e);
		}
		return messageMap;
	}
	
	
}

