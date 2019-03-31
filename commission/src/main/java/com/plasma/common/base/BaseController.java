package com.plasma.common.base;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.plasma.common.DataRow;
import com.plasma.common.IConstants;

/**
 * 控制器常用属性和方法
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class BaseController {

	@Autowired
	public HttpServletRequest request;
	@Autowired
	public HttpServletResponse response;
	public DataRow messageMap = new DataRow();
	public PageBean pageBean = new PageBean();
	public DataRow info = new DataRow(); // 发送数据 info
	public List<DataRow> dataList = new ArrayList<DataRow>(); // 发送数据集合
	public static final Logger log = LogManager.getLogger(BaseController.class);

	/**
	 * 初始化返回值的json格式内容
	 * 
	 * @param request
	 * @param response  @RequestParam HttpServletRequest request
	 */
	@ModelAttribute
	public void handleParameter() {
		Enumeration<String> keyNames = request.getParameterNames();
		while(keyNames.hasMoreElements()){
			String attrName = keyNames.nextElement();
			info.put(attrName, request.getParameter(attrName));
			//获取页码
			if ("pageNum".equals(attrName)) {
				pageBean.setPageNum(request.getParameter(attrName));
			}
			//获取页码
			if ("pageSize".equals(attrName)) {
				pageBean.setPageSize(Integer.valueOf(request.getParameter(attrName)));
			}
		}
		
		DataRow user = getSessionUser();
		if(null!=user){
			info.put("plasmaId", user.get("plasmaId"));
		}
	}
	
	/**
	 * 获取用户ID
	 * @return
	 */
	public String getSessionUserId(){
		DataRow user  = (DataRow) request.getSession().getAttribute(IConstants.SESSION_USER);
		if (null==user) {
			return "";
		}
		return user.getString("id");
	}
	
	/**
	 * 获取session 中的用户
	 * @return
	 */
	public DataRow getSessionUser(){
		DataRow user  = (DataRow) request.getSession().getAttribute(IConstants.SESSION_USER);
		return user;
	}
	
	public void setSessionUser(DataRow user){
		request.getSession().setAttribute(IConstants.SESSION_USER, user);
	}
	
	/**
	 * 封装成功返回的json内容
	 * 
	 * @param messageMap
	 *            存储返回的内容
	 * @param obj
	 *            返回json中的data内容
	 */
	public void fillResultContext(DataRow messageMap, Object obj) {
		messageMap.put(IConstants.APP_RESULT_DATA, obj);
		messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
		messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
	}
}
