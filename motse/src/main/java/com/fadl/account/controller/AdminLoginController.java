package com.fadl.account.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;

@RequestMapping("log")
@Controller
public class AdminLoginController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	 * 用户登陆
	 * @param admin
	 * @return
	 */
	@RequestMapping("/queryAdminLogin")
	@ResponseBody
	public DataRow queryAdminLogin(Admin admin){
		try {
			System.out.println(admin);
			UsernamePasswordToken token = new UsernamePasswordToken(admin.getAccount(),admin.getPassWord());
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			messageMap.initSuccess();
		} catch (UnknownAccountException un) {
			messageMap.initFial("用户不存在");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",un);
		}catch(IncorrectCredentialsException in){
			messageMap.initFial("密码错误");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",in);
		}catch(AuthenticationException au){
			messageMap.initFial("用户名或密码不正确");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",au);
		}
		return messageMap;
	}
}
