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
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.service.ConversionRecordService;

/**
 * <p>
 * 特免转类记录表 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-27
 */
@Controller
@RequestMapping("/conversionRecord")
public class ConversionRecordController extends AbstractController{

private static Logger logger = LoggerFactory.getLogger(ConversionRecordController.class);
	
	@Autowired
	private ConversionRecordService conversionRecordService;
	
	/**
	 * 特免转类页面
	 * @return
	 */
	@RequestMapping("/toSpecialTransfer")
	@RequiresPermissions("conversion:view")
	public String toSpecialTransfer() {
		return "/immune/specialtransfer/special_transfer";
	}
	/**
	 * 提示是否转类页面
	 * @return
	 */
	@RequestMapping("/toIsTransfer")
	@RequiresPermissions("conversion:zhuan")
	public String toIsTransfer() {
		return "/immune/specialtransfer/is_transfer";
	}
	/**
	 * 特免转类  可转类浆员列表
	 * @param page
	 * @param limit
	 * @param providerNo
	 * @return
	 */
	@RequestMapping("/specialTransferList")
	@InvokeLog(name = "specialTransferList", description = "特免转类  可转类浆员列表")
	@RequiresPermissions("conversion:listW")
	@ResponseBody
	public DataRow specialTransferList(Integer page,Integer limit,@RequestParam Map<String,String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			if(!map.containsKey("updateDate")){
				map.put("updateDate", DateUtil.sfDay.format(new Date()));
			}
			conversionRecordService.specialTransferList(map,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ConversionRecordController>>>>>>>specialTransferList>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 已转类浆员列表
	 * @param page
	 * @param limit
	 * @param providerNo
	 * @return
	 */
	@RequestMapping("/conversionRecordList")
	@InvokeLog(name = "conversionRecordList", description = "已转类浆员列表")
	@RequiresPermissions("conversion:listY")
	@ResponseBody
	public DataRow conversionRecordList(Integer page,Integer limit,@RequestParam Map<String,String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			if(!map.containsKey("updateDate")){
				map.put("updateDate", DateUtil.sfDay.format(new Date()));
			}
			conversionRecordService.conversionRecordList(map,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ConversionRecordController>>>>>>>conversionRecordList>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 *  特免转类  头部内容
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySpecialTransferHead")
	@InvokeLog(name = "querySpecialTransferHead", description = "特免转类  头部内容")
	@ResponseBody
	public DataRow querySpecialTransferHead(@RequestParam Map<String,String> map) {
		try {
			conversionRecordService.querySpecialTransferHead(map,messageMap);
		} catch (Exception e) {
			logger.error("ConversionRecordController>>>>>>>querySpecialTransferHead>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 特免转类 转换
	 * @param providerNo
	 * @param thisStatus
	 * @param transferType
	 * @return
	 */
	@RequestMapping("/transferType")
	@InvokeLog(name = "transferType", description = "特免转类 转换")
	@ResponseBody
	public DataRow transferType(String providerNo,String thisStatus,String transferType) {
		try {
			conversionRecordService.transferType(providerNo, thisStatus, transferType,messageMap);
		} catch (Exception e) {
			logger.error("ConversionRecordController>>>>>>>transferType>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
}

