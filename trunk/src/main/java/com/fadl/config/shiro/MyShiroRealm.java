package com.fadl.config.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.SysAdminRole;
import com.fadl.account.entity.SysPermission;
import com.fadl.account.entity.SysRolePermission;
import com.fadl.account.service.AdminService;
import com.fadl.account.service.SysAdminRoleService;
import com.fadl.account.service.SysPermissionService;
import com.fadl.account.service.SysRolePermissionService;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author:wangjing
 * @Description:
 * @Date:2018-04-24
 */
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    private AdminService adminService;

    @Autowired
    private SysAdminRoleService sysAdminRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证 shiro 自身缓存2分钟##################");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            //到数据库查是否有此对象
            Admin admin  = (Admin)principalCollection.getPrimaryPrincipal();
            if("15888888888".equals(admin.getMobile())){
               List<SysPermission> sysPermissionList= sysPermissionService.selectList(null);
                for (SysPermission permission : sysPermissionList) {
                    if (!"".equals(permission.getPermission())&&permission.getPermission()!=null) {
                        authorizationInfo.addStringPermission(permission.getPermission());
                    }
                }
            }else {
                EntityWrapper ew = new EntityWrapper();
                ew.eq("adminId", admin.getId());
                List<SysAdminRole> adminRoleList = sysAdminRoleService.selectList(ew);
                List<Long> roleIds = new ArrayList<Long>();
                for (SysAdminRole sysAdminRole : adminRoleList) {
                    authorizationInfo.addRole(String.valueOf(sysAdminRole.getRoleId()));
                    roleIds.add(sysAdminRole.getRoleId());
                }
                if (roleIds.size() > 0) {
                    List<String> permissionList = sysRolePermissionService.queryRolePermissionList(roleIds);
                    for (String permission : permissionList) {
                        if (!StringHelper.isEmpty(permission)) {
                            authorizationInfo.addStringPermission(permission);
                        }
                    }
                }
            }
            return authorizationInfo;
        }catch (Exception e){
            logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthorizationInfo>>>>>>>>>>>",e);
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
        //获取用户的输入的账号.
        SimpleAuthenticationInfo authenticationInfo=null;
        //获取用户的输入的账号.
        try {
            String mobile = (String)token.getPrincipal();
            //通过mobile从数据库中查找 User对象，如果找到，没找到.
            //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
            Admin admin = adminService.qeuryByMobile(mobile);
            if(admin == null||1==admin.getIsDelete()){//找不到用户 用户如果被逻辑删除
                throw new UnknownAccountException();
            }
            if(DateUtil.sf.parse(admin.getLockDate()).getTime() >= new Date().getTime()&&1==admin.getIsDisable()){//用户被禁用
                throw new LockedAccountException();//用户锁定
            }
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            authenticationInfo = new SimpleAuthenticationInfo(
                    admin, //用户名
                    admin.getPassWord(), //密码
                    ByteSource.Util.bytes(admin.getMobile()),
                    getName()  //realm name
            );
            Session session= SecurityUtils.getSubject().getSession();
            session.setAttribute(IConstants.SESSION_ADMIN_USER,admin);
            session.setAttribute("userSessionId", admin.getId());
        }catch (SQLException e){
            logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthenticationInfo>>>>>>>>>>>",e);
        }catch (ParseException e){
            logger.error("MyShiroRealm>>>>>>>>>>>>>>>doGetAuthenticationInfo>>>>>>>>>>>",e);
        }
        return authenticationInfo;
    }


    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     * @param adminIds 已经修改了权限的userId
     */
    public void clearAdminAuthByUserId(List<Long> adminIds){
        if(null == adminIds || adminIds.size() == 0)	return ;
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session:sessions){
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof Admin){
                    Admin admin = (Admin) obj;
                    logger.info("user:"+admin);
                    //比较用户ID，符合即加入集合
                    if(null != admin && adminIds.contains(admin.getId())){
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm)securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
