package com.fadl.registries.controller;


import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.registries.service.ProviderAboutService;

/**
 * <p>
 * 预约时间表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-09-11
 */
@Controller
@RequestMapping("/providerAbout")
public class ProviderAboutController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ProviderAboutController.class);
	
	@Autowired
	ProviderAboutService providerAboutService;
	/**
     * 跳转预约人员页面
     * @return
     */
    @RequestMapping("/aboutPeople")
    @RequiresPermissions("registration:about")
    public String aboutPeople() {
		return "/business/register/about_people";
    }
    /**
	 * 预约人数查询
	 * @param aboutDate
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/queryAboutPeople")
	@InvokeLog(name = "queryAboutPeople", description = "预未到人数查询")
	@ResponseBody
	public DataRow queryAboutPeople(@RequestParam Map<String,String> map,Integer page, Integer limit) {
		Page<DataRow> pageing = null;
		try {
			if(!map.containsKey("aboutDate")) {
				map.put("aboutDate", DateUtil.sfDay.format(new Date()));
        	}
			pageing = new Page<DataRow>(page,limit);
			providerAboutService.queryAboutPeople(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ProviderRegistriesController>>queryAboutPeople",e);
		}
		return messageMap;
	}
    
}

