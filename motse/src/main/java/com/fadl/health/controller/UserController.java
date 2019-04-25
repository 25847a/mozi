package com.fadl.health.controller;


import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.health.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	
	/**
     * 添加使用者用户页面
     * @return
     */
    @RequestMapping("/addUserPage")
    @RequiresPermissions("addUser:view")
    public String addUserPage(){
    	return "/user/addUser";
    }
	/**
	 *  代理商列表
	 * @param map
	 * @return
	 */
    @RequestMapping("/queryAgentList")
    @ResponseBody
    public DataRow queryAgentList(@RequestParam Map<String,String> map){
    	try {
			
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryAgentList",e);
		}
		return messageMap;
    	
    }
}

