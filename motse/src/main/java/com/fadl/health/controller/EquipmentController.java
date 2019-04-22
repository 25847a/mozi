package com.fadl.health.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.health.service.EquipmentService;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/equipment")
public class EquipmentController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);
	
	@Autowired
	EquipmentService equipmentService;
	/**
     * 设备管理页面
     * @return
     */
    @RequestMapping("/equipmentPage")
    public String equipmentPage(){
    	return "/backstage/equipment";
    }
    /**
	 * 查询设备数据列表
	 */
	@RequestMapping("/queryEquipmentList")
	@ResponseBody
	public DataRow queryEquipmentList(@RequestParam Map<String,String> map){
		try {
		messageMap=equipmentService.queryEquipmentList(messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>queryEquipmentList",e);
		}
		return messageMap;
	}
}

