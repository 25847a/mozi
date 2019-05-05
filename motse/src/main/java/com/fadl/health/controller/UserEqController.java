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
import com.fadl.health.service.UserEqService;

/**
 * <p>
 * 用户设备关联表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
@Controller
@RequestMapping("/userEq")
public class UserEqController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(UserEqController.class);
	
	@Autowired
	UserEqService userEqService;
	
	/**
     * 输入手机号码添加观察者
     * @param map
     * @return
     */
    @RequestMapping("/addFollowInfo")
    @ResponseBody
    public DataRow queryUserEquipmentInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=userEqService.addFollowInfo(map, messageMap);
		} catch (Exception e) {
			logger.error("UserEqController>>>>>>>>>>>>>userEqService",e);
		}
		return messageMap;
    }
}

