package com.fadl.comfig.shiro;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fadl.account.dao.RoleAuthMapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.AdminRole;
import com.fadl.account.service.AdminRoleService;
import com.fadl.account.service.AdminService;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;


public class MyShiroRealm extends AuthorizingRealm{
	
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
	
	@Autowired
	AdminService adminService;
	@Autowired
	AdminRoleService adminRoleService;
	@Autowired
	RoleAuthMapper roleAuthMapper;
	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 logger.info("##################执行Shiro权限认证 shiro 自身缓存2分钟##################");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		try {
		Admin admin =(Admin) principals.getPrimaryPrincipal();
		//获取该用户下的角色集合
		List<AdminRole> adminRole;
		adminRole = adminRoleService.queryAdminRoleInfo(admin.getId());
		List<Long> roleSize = new ArrayList<Long>();
		for(AdminRole role:adminRole){
			roleSize.add(role.getRoleId());
		}
		if(roleSize.size()>0){
			List<String> permissionList= roleAuthMapper.queryRoleAuthList(roleSize);
			for(String permission:permissionList){
				simpleAuthorizationInfo.addStringPermission(permission);
			}
		}
		} catch (SQLException e) {
			 logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthorizationInfo>>>>>>>>>>>",e);
		}
		//然后获取每一个角色下的权限
		return simpleAuthorizationInfo;
	}
	/**
	 * 获取身份验证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
		SimpleAuthenticationInfo simpleAuthenticationInfo=null;
		//获取用户输入的账号
		String account = (String) token.getPrincipal();
		logger.error(">>>>>>>>>>>>>>>>>>用户====="+account+"=====准备登陆<<<<<<<<<<<<<<<<<<");
		try {
			Admin admin =adminService.queryAdminInfo(account);
			if(admin == null){//找不到用户 用户如果被逻辑删除
                throw new UnknownAccountException();
            }
			if(DateUtil.sf.parse(admin.getLockDate()).getTime() >= new Date().getTime()&&1==admin.getIsDisable()){//用户被禁用
	            throw new LockedAccountException();//用户锁定
	        }
			simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin,admin.getPassWord(),ByteSource.Util.bytes(admin.getAccount()),getName());
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(IConstants.SESSION_ADMIN_USER, admin);
			session.setAttribute("userSessionId", admin.getId());
		} catch (SQLException e) {
			logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthenticationInfo>>>>>>>>>>>",e);
		}catch (ParseException e){
            logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthenticationInfo>>>>>>>>>>>",e);
        }
		return simpleAuthenticationInfo;
	}
}
