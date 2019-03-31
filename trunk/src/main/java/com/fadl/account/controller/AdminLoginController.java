package com.fadl.account.controller;

import java.net.InetAddress;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.entity.Admin;
import com.fadl.account.service.AdminService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.ReadProperties;
import com.fadl.common.annotation.InvokeLog;

/**
 * @author:wangjing
 * @Description:登陆操作
 * @Date:2018-04-24
 */
@Controller
@RequestMapping("/log")
public class AdminLoginController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
    @Autowired
    AdminService adminService;

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "/login";
    }
    /**
     * 解决子页面，重定向之后，出现页面嵌套的问题
     * @return
     */
    @RequestMapping("/tologin")
    public String tologin() {
        return "/tologin";
    }
    /**
     * 跳转数据库设置页面
     * @return
     */
    @RequestMapping("/logia")
    public String logia() {
        return "/logia";
    }
    /**
     * 登录操作
     * @return
     */
    @RequestMapping(value="/queryAdminLogin",method = RequestMethod.POST)
    @InvokeLog(name = "queryAdminLogin", description = "登录操作")
    @ResponseBody
    public DataRow queryAdminLogin(String mobile,String passWord) {
        Admin updateAdmin = new Admin();//用于修改的用户对象
        String hostAddress="";
        Admin admin=null;
        try {
            admin= adminService.qeuryByMobile(mobile);//查询手机是否存在
            hostAddress = InetAddress.getLocalHost().getHostAddress();//获取请求地址
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, passWord);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);//对用户进行验证
            updateAdmin.setIsLoginCount(admin.getIsLoginCount() + 1);//经过shiro认证，admin不为空才会进入
            updateAdmin.setIp(hostAddress);
            updateAdmin.setLoginErrorCount(0);
            updateAdmin.setIsDisable(0);
            updateAdmin.setId(admin.getId());
            messageMap.initSuccess();
        }catch(UnknownAccountException uae){
            messageMap.initFial("用户不存在");
        }catch(IncorrectCredentialsException ice){
            updateAdmin.setLoginErrorCount(admin.getLoginErrorCount() + 1);
            updateAdmin.setIp(hostAddress);
            updateAdmin.setId(admin.getId());
            if(admin.getLoginErrorCount()>=2){
                updateAdmin.setLockDate(DateUtil.sf.format(DateUtil.getSystemAddHours()));
                updateAdmin.setIsDisable(1);
            }
            messageMap.initFial("密码不正确");
        }catch(LockedAccountException lae){
            messageMap.initFial(IConstants.USER_LOGIN_ERROR_NIMIETY.replace("{0}",admin.getLockDate()));
        }catch(ExcessiveAttemptsException eae){
            messageMap.initFial("用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            logger.error("AdminController>>>>>>>>>queryAdminLogin>>>>>",ae);
            messageMap.initFial("用户名或密码不正确");
        }catch (Exception e) {
            messageMap.initFial("系统异常");
        }finally {
            try {
                if(updateAdmin!=null&&null!=updateAdmin.getId()){
                    adminService.updateById(updateAdmin); // 更新登陆次数和IP
                }
            }catch (Exception e){
                messageMap.initFial("系统异常");
                logger.error("AdminController>>>>>>>>>queryAdminLogin>>>>>",e);
            }
        }
        return messageMap;
    }
    /**
     * 获取数据库连接信息
     * @return
     */
    @RequestMapping(value="/queryDatabase", method = RequestMethod.POST)
    @InvokeLog(name = "queryDatabase", description = "获取数据库连接信息")
    @ResponseBody
    public DataRow queryDatabase() {
        try {
            adminService.queryDatabase(messageMap);
        } catch (Exception e) {
            logger.error("AdminController>>>>>>>>>queryDatabase>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 测试本地数据库连接
     * @return
     */
    @RequestMapping(value="/testDatabase",method = RequestMethod.POST)
    @InvokeLog(name = "testDatabase", description = "测试本地数据库连接")
    @ResponseBody
    public DataRow testDatabase() {
        //获取前端传值
        String dataName  = request.getParameter("dataName").trim();
        String dataWord  = request.getParameter("dataWord").trim();
        String IPString  = request.getParameter("IPString").trim();
        String database  = request.getParameter("database").trim();
        //获取配置数据库值
        String correctName = ReadProperties.getValue("username");
        String correctWord = ReadProperties.getValue("password");
        String ip = ReadProperties.getValue("url");
        String regexString=".*(\\d{3}(\\.\\d{1,3}){3}).*";
        String correcIp=ip.replaceAll(regexString,"$1");
        String name = ReadProperties.getValue("url");
        String correcBase=name.substring(name.lastIndexOf("/")+1,name.lastIndexOf("?")-1);
        //进行判断
        if(!dataName.equals(correctName)) {
            messageMap.initFial("登录账户不正确");
        }else if(!dataWord.equals(correctWord)) {
            messageMap.initFial("登录密码不正确");
        }else if(!IPString.equals(correcIp)) {
            messageMap.initFial("服务地址不正确");
        }else if(!database.equals(correcBase)) {
            messageMap.initFial("数据库名不正确");
        }else {
            messageMap.initSuccess("数据库连接正常");
        }
        return messageMap;
    }
    
    /**
     * 用户退出
     * @return
     */
    @RequestMapping(value="/loginOut",method = RequestMethod.POST)
    @InvokeLog(name = "loginOut", description = "用户退出")
    @ResponseBody
    public DataRow loginOut(){
    	try {
    		SecurityUtils.getSubject().logout(); 
    		messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("AdminController>>>>>>>>>loginOut>>>>>",e);
		}
    	return messageMap;
    }

}
