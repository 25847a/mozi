package com.plasma.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plasma.common.IConstants;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Object obj =req.getSession().getAttribute(IConstants.SESSION_USER);//获取网站的session
		if (null == obj) {
			response.setContentType("text/html; charset=UTF-8");	//设置发送到前台的编码是UTF-8
			PrintWriter out = response.getWriter();		//输出的是文本
			if (isAjaxRequest(req)) {
				out.println("noLogin");
			} else {
				String path = req.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path + "/login";
				out.println(
						"<script type=\"text/javascript\">window.parent.location.href=\"" + basePath + "\";</script>");
			}
			out.close();
		} else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
}
