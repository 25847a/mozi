package com.fadl.comfig.shiro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.AdminRole;
import com.fadl.account.service.AdminRoleService;
import com.fadl.account.service.AdminService;


public class MyShiroRealm extends AuthorizingRealm{
	
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
	
	@Autowired
	AdminService adminService;
	@Autowired
	AdminRoleService adminRoleService;
	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		try {
		Admin admin =(Admin) simpleAuthorizationInfo.getStringPermissions();
		//获取该用户下的角色集合
		List<AdminRole> adminRole;
	
			adminRole = adminRoleService.queryAdminRoleInfo(admin.getId());
	
		List<Long> roleSize = new ArrayList<Long>();
		for(AdminRole role:adminRole){
			roleSize.add(role.getRoleId());
		}
		if(roleSize.size()>0){
			
		}
		} catch (SQLException e) {
			 logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthorizationInfo>>>>>>>>>>>",e);
		}
		//然后获取每一个角色下的权限
		return null;
	}
	/**
	 * 获取身份验证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo simpleAuthenticationInfo=null;
		//获取用户输入的账号
		String account = (String) token.getPrincipal();
		logger.error(">>>>>>>>>>>>>>>>>>用户====="+account+"=====准备登陆<<<<<<<<<<<<<<<<<<");
		try {
			Admin admin =adminService.queryAdminInfo(account);
			if(admin==null){
				throw new UnknownAccountException();
			}
			simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin,admin.getPassWord(),ByteSource.Util.bytes(admin.getAccount()),getName());
		} catch (Exception e) {
			logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthenticationInfo>>>>>>>>>>>",e);
		}
		return simpleAuthenticationInfo;
	}
}
