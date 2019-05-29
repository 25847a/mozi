package com.fadl.account.controller;

import java.util.Date;

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
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.account.service.AdminService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.health.entity.Usercode;

@RequestMapping("log")
@Controller
public class AdminLoginController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	AdminService adminService;
	/**
	 * 跳转忘记密码页面
	 * @return
	 */
	@RequestMapping("/getpassword")
	public String getpassword(){
		return "/getpassword";
	}
	/**
	 * 用户登陆
	 * @param admin
	 * @return
	 */
	@RequestMapping("/queryAdminLogin")
	@ResponseBody
	public DataRow queryAdminLogin(String account,String passWord){
		Admin updateAdmin = new Admin();//用于修改的用户对象
		Admin admin = null;
		try {
			admin= adminService.queryAdminInfo(account);//查询手机是否存在
			UsernamePasswordToken token = new UsernamePasswordToken(account,passWord);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			updateAdmin.setIsLoginCount(admin.getIsLoginCount() + 1);//经过shiro认证，admin不为空才会进入
            updateAdmin.setId(admin.getId());
            if(DateUtil.sf.parse(admin.getLockDate()).getTime() <= new Date().getTime() && admin.getLoginErrorCount()>=5){//用户被禁用
            	updateAdmin.setLoginErrorCount(0);
            }
            updateAdmin.setIsDisable(0);
            updateAdmin.setLastDate(DateUtil.sf.format(new Date()));
			messageMap.initSuccess();
		}catch (UnknownAccountException un) {//用户不存在
			messageMap.initFial("用户名或密码不正确");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",un);
		}catch(IncorrectCredentialsException in){//密码错误
			updateAdmin.setLoginErrorCount(admin.getLoginErrorCount() + 1);
            updateAdmin.setId(admin.getId());
            if(admin.getLoginErrorCount()>=5){
                updateAdmin.setLockDate(DateUtil.sf.format(DateUtil.getSystemAddMinute(10)));//加10分钟
                updateAdmin.setIsDisable(1);
            }
			messageMap.initFial("用户名或密码不正确");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",in);
		}catch(LockedAccountException lae){
			String message =admin.getLockDate();
            messageMap.initFial(IConstants.USER_LOGIN_ERROR_NIMIETY.replace("{0}",message.subSequence(0, message.length()-2)));
        }catch(ExcessiveAttemptsException eae){
            messageMap.initFial("用户名或密码错误次数过多");
        }catch(AuthenticationException au){//报异常,可能是sql语句为空
			messageMap.initFial("用户名或密码不正确");
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",au);
		}catch(Exception aa){
			logger.error("<<<<<<<<<<<<<<<<<<<<<<AdminController<<<<<<<<<<<<<<<<<<queryAdminLogin",aa);
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
     * 修改密码验证手机号码
     * phone
     * @return
     */
    @RequestMapping("/checkingPhone")
    @ResponseBody
    public DataRow checkingPhone(String phone){
    	try {
    		messageMap =  adminService.checkingPhone(phone,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<checkingPhone",e);
		}
		return messageMap;
    }
    /**
     * 通过手机号码获取验证码
     * phone
     * @return
     */
    @RequestMapping("/checkingCode")
    @ResponseBody
    public DataRow checkingCode(String phone){
    	try {
    		messageMap =  adminService.checkingCode(phone,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<checkingCode",e);
		}
		return messageMap;
    }
    /**
     * 判断验证码是否正确
     * phone
     * @return
     */
    @RequestMapping("/queryCheckingCode")
    @ResponseBody
    public DataRow queryCheckingCode(Usercode usercode){
    	try {
    		messageMap =  adminService.queryCheckingCode(usercode,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<queryCheckingCode",e);
		}
		return messageMap;
    }
    /**
     * 忘记密码更改密码
     * admin
     * @return
     */
    @RequestMapping("/updatePassWord")
    @ResponseBody
    public DataRow updatePassWord(Admin admin){
    	try {
    		messageMap =  adminService.updatePassWord(admin,messageMap);
		} catch (Exception e) {
			logger.error("AdminController<<<<<<<<<<<<<<<<<<updatePassWord",e);
		}
		return messageMap;
    }
}
