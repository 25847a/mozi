package com.plasma.buss.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.home.service.HomeService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.BaseController;

/**
 * 首页
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class HomeController extends BaseController{

	@Autowired
	public HomeService homeService;
	
	/**
	 * 查询用户的菜单
	 * @return
	 */
	@RequestMapping("/queryUserMenu")
	@ResponseBody
	public Object queryUserMenu(){
		try {
			DataRow row = getSessionUser();
			info.put("roleId", row.getString("roleId"));
			List<DataRow> d=homeService.queryUserMenu(info);
			super.fillResultContext(messageMap, d);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("HomeAction>>>queryUserMenu>>>",e);
		}
		return messageMap;
	}
}
