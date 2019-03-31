package com.plasma.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseAction {

	@Autowired
	public HttpServletRequest request;
	@Autowired
	public HttpServletResponse response;

	/**
	 * 把用户设置到 session
	 * @param dataRow
	 */
	public void setSessionUser(DataRow dataRow) {  
        request.getSession().setAttribute("SESSION_USER",dataRow);  
    } 
	
	/**
	 * 获取 session 中的用户
	 * 
	 * @return
	 */
	public DataRow getSessionUser() {
		return (DataRow) request.getSession().getAttribute("SESSION_USER");
	}

	/**
	 * 删除 session 中的用户
	 * 
	 * @return
	 */
	public void delSessionUser() {
		request.getSession().removeAttribute("SESSION_USER");
	}
}
