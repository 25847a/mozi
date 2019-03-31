package com.fadl.immuneAssay.controller;


import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.immuneAssay.service.ImmuneRegisterService;

/**
 * <p>
 * 免疫登记 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-18
 */
@Controller
@RequestMapping("/immuneRegister")
public class ImmuneRegisterController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(ImmuneRegisterController.class);
	
	@Autowired
	ImmuneRegisterService immuneRegisterService;
	/**
	 * 普免登记
	 * @return
	 */
	@RequestMapping("/immuneRegisterPage")
	@RequiresPermissions("ordinaryRegister:view")
	public String immuneRegisterPage() {
		return "/immune/register/puregister";
	}
	/**
	 * 普通免疫基本信息查询
	 * @return
	 */
	@RequestMapping("/puregisterInfoPage")
	@RequiresPermissions("ordinaryRegister:query")
	public String puregisterInfoPage() {
		return "/immune/register/puregisterInfo";
	}
	/**
	 * 特免登记
	 * @return
	 */
	@RequestMapping("/teregisterPage")
	@RequiresPermissions("strengRegister:view")
	public String teregisterPage() {
		return "/immune/register/teregister";
	}
	/**
	 * 特殊免疫基本信息查询
	 * @return
	 */
	@RequestMapping("/turegisterInfoPage")
	@RequiresPermissions("strengRegister:query")
	public String turegisterInfoPage() {
		return "/immune/register/teregisterInfo";
	}
	/**
	 * 普免登记列表(不通过)
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryImmuneRegister")
	@InvokeLog(name = "queryImmuneRegister", description = "普免登记列表(不通过)")
	@RequiresPermissions("ordinaryRegister:listW")
	@ResponseBody
	public DataRow queryImmuneRegister(String startTime,String num,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			if(StringHelper.isEmpty(startTime) && StringHelper.isEmpty(num)) {
				startTime=DateUtil.sfDay.format(new Date());
			}
			immuneRegisterService.queryImmuneRegister(paging,startTime);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryImmuneRegister",e);
		}
		return messageMap;
	}
	/**
	 * 特免登记列表(不通过)
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryImmuneRegisterSpecial")
	@InvokeLog(name = "queryImmuneRegisterSpecial", description = " 特免登记列表(不通过)")
	@RequiresPermissions("strengRegister:listW")
	@ResponseBody
	public DataRow queryImmuneRegisterSpecial(String startTime,String num,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			if(StringHelper.isEmpty(startTime) && StringHelper.isEmpty(num)) {
				startTime=DateUtil.sfDay.format(new Date());
			}
			immuneRegisterService.queryImmuneRegisterSpecial(paging,startTime);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryImmuneRegisterSpecial",e);
		}
		return messageMap;
	}
	/**
	 * 普免登记列表(已通过)
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryImmuneRegisterAdopt")
	@InvokeLog(name = "queryImmuneRegisterAdopt", description = "普免登记列表(已通过)")
	@RequiresPermissions("ordinaryRegister:listY")
	@ResponseBody
	public DataRow queryImmuneRegisterAdopt(String startTime,String num,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			if(StringHelper.isEmpty(startTime) && StringHelper.isEmpty(num)) {
				startTime=DateUtil.sfDay.format(new Date());
			}
			immuneRegisterService.queryImmuneRegisterAdopt(paging,startTime);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryImmuneRegisterAdopt",e);
		}
		return messageMap;
	}
	/**
	 * 特免登记列表(已通过)
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryTeregisterAdopt")
	@InvokeLog(name = "queryTeregisterAdopt", description = "普免登记列表(已通过)")
	@RequiresPermissions("strengRegister:listY")
	@ResponseBody
	public DataRow queryTeregisterAdopt(String startTime,String num,Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			if(StringHelper.isEmpty(startTime) && StringHelper.isEmpty(num)) {
				startTime=DateUtil.sfDay.format(new Date());
			}
			immuneRegisterService.queryTeregisterAdopt(paging,startTime);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryTeregisterAdopt",e);
		}
		return messageMap;
	}
	/**
	 * 插入普免登记信息
	 * @param immuneRegister
	 * @return
	 */
	@RequestMapping(value="updateImmuneRegister")
	@InvokeLog(name = "updateImmuneRegister", description = "插入普免登记信息")
	@RequiresPermissions("ordinaryRegister:register")
	@ResponseBody
	public DataRow updateImmuneRegister(ImmuneRegister immuneRegister) {
		try {
			immuneRegisterService.updateImmuneRegister(immuneRegister,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>insertImmuneRegister",e);
		}
		return messageMap;
	}
	/**
	 * 取消普免登记
	 * @param OrdinaryInjection
	 * @return
	 */
	@RequestMapping(value="cancelPuregisterRegister")
	@InvokeLog(name = "cancelPuregisterRegister", description = "插入普免登记信息")
	@RequiresPermissions("puregister:translate")
	@ResponseBody
	public DataRow cancelPuregisterRegister(OrdinaryInjection ord) {
		try {
			immuneRegisterService.cancelPuregisterRegister(ord,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>cancelImmuneRegister",e);
		}
		return messageMap;
	}
	/**
	 * 取消特免登记
	 * @param OrdinaryInjection
	 * @return
	 */
	@RequestMapping(value="canceltergisterRegister")
	@InvokeLog(name = "canceltergisterRegister", description = "取消特免登记")
	@RequiresPermissions("tergister:translate")
	@ResponseBody
	public DataRow canceltergisterRegister(PrivilegeInjection pri) {
		try {
			immuneRegisterService.canceltergisterRegister(pri,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>canceltergisterRegister",e);
		}
		return messageMap;
	}
	/**
	 * 插入特免登记信息
	 * @param immuneRegister
	 * @return
	 */
	@RequestMapping(value="updateteregister")
	@InvokeLog(name = "updateteregister", description = "插入特免登记信息")
	@RequiresPermissions("strengRegister:register")
	@ResponseBody
	public DataRow updateteregister(ImmuneRegister immuneRegister) {
		try {
			immuneRegisterService.updateteregister(immuneRegister,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>updateteregister",e);
		}
		return messageMap;
	}
	/**
	 * 查询普通免疫基本信息查询列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryPuregisterInfo")
	@InvokeLog(name = "queryPuregisterInfo", description = "查询普通免疫基本信息查询列表")
	@ResponseBody
	public DataRow queryPuregisterInfo(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = new Page<DataRow>(page,limit);
		try {
			immuneRegisterService.queryPuregisterInfo(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryPuregisterInfo",e);
		}
		return messageMap;
	}
	/**
	 * 查询特殊免疫基本信息查询列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryTuregisterInfo")
	@InvokeLog(name = "queryTuregisterInfo", description = "查询特殊免疫基本信息查询列表")
	@ResponseBody
	public DataRow queryTuregisterInfo(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = new Page<DataRow>(page,limit);
		try {
			immuneRegisterService.queryTuregisterInfo(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ImmuneRegisterController>>>>>>>>>>>>>queryTuregisterInfo",e);
		}
		return messageMap;
	}
}

