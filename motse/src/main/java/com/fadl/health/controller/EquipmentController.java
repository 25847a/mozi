package com.fadl.health.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fadl.common.AbstractController;

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
	/**
     * 设备管理页面
     * @return
     */
    @RequestMapping("/equipmentPage")
    public String addUser(){
    	return "/backstage/equipment";
    }
}

