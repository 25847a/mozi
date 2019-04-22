package com.fadl.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.fadl.account.entity.Admin;

public class SessionUtil {
	
	/**
	 * 获取用户
	 * @return
	 */
	public static Admin getSessionAdmin(){
		 Session session= SecurityUtils.getSubject().getSession();
         Admin admin=null; 
         if(session!=null){
         	  admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
         }
         return admin;
	}
	/**
	 * 获取用户昵称
	 * @return
	 */
	public static String getSessionName(){
		 Session session= SecurityUtils.getSubject().getSession();
        Admin admin=null; 
        if(session!=null){
        	  admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
        	  if(admin!=null){
        		  return admin.getName();
        	  }
        }
        return null;
	}
	/**
	 * 获取用户Id
	 * @return
	 */
	public static Long getSessionId(){
		 Session session= SecurityUtils.getSubject().getSession();
        Admin admin=null; 
        if(session!=null){
        	  admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
        	  if(admin!=null){
        		  return admin.getId();
        	  }
        }
        return null;
	}

}
