package com.fadl.health.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fadl.common.AbstractController;

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
	
	
	/**
     * 添加使用者用户页面
     * @return
     */
    @RequestMapping("/addUserPage")
    public String addUserPage(){
    	return "/user/addUser";
    }
	
}

