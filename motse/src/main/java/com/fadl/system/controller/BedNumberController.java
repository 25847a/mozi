package com.fadl.system.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.health.controller.HealthController;
import com.fadl.system.entity.BedNumber;
import com.fadl.system.service.BedNumberService;

/**
 * <p>
 * 床位表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Controller
@RequestMapping("/bedNumber")
public class BedNumberController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	
	
	/**
	 * 跳转床位页面
	 * @return
	 */
	@RequestMapping("/bedPage")
	public String bedPage(){
		return "/system/bed";
	}
	@Autowired
	BedNumberService bedNumberService;
	
	
	/**
     * 查询分属护士列表
     * @return
     */
    @RequestMapping("/queryBedList")
    @ResponseBody
    public DataRow queryBedList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap = bedNumberService.queryBedList(map,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController<<<<<<<<<<<<<<<<<<queryBedList",e);
		}
		return messageMap;
    }
    /**
     * 新增分属护士信息
     * @return
     */
    @RequestMapping("/addBedInfo")
    @ResponseBody
    public DataRow addBedInfo(BedNumber bedNumber){
    	try {
    		messageMap = bedNumberService.addBedInfo(bedNumber,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController<<<<<<<<<<<<<<<<<<addBedInfo",e);
		}
		return messageMap;
    }
    /**
     * 修改分属护士信息
     * @return
     */
    @RequestMapping("/updateBedInfo")
    @ResponseBody
    public DataRow updateBedInfo(BedNumber bedNumber){
    	try {
    		messageMap = bedNumberService.updateBedInfo(bedNumber,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController<<<<<<<<<<<<<<<<<<updateBedInfo",e);
		}
		return messageMap;
    }
    /**
     * 删除分属护士信息
     * @return
     */
    @RequestMapping("/deleteBedInfo")
    @ResponseBody
    public DataRow deleteBedInfo(Long id){
    	try {
    		messageMap = bedNumberService.deleteBedInfo(id,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController<<<<<<<<<<<<<<<<<<deleteBedInfo",e);
		}
		return messageMap;
    }
	/**
	 * 查询床位数据
	 * @return
	 */
	@RequestMapping("/queryBedNumberInfo")
	@ResponseBody
	public DataRow queryBedNumberInfo(){
		try {
		Admin admin = SessionUtil.getSessionAdmin();
		messageMap=bedNumberService.queryBedNumberInfo(admin,messageMap);
		} catch (Exception e) {
			logger.error("BedNumberController>>>>>>>>>>>>>queryBedNumberInfo",e);
		}
		return messageMap;
	}
}

