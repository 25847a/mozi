package com.fadl.elisa.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.elisa.entity.ElisaApi;
import com.fadl.elisa.service.ElisaApiService;

/**
 * <p>
 * 酶标板设备接口信息 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Controller
@RequestMapping("/elisaApi")
public class ElisaApiController extends AbstractController{
	@Autowired
	private ElisaApiService elisaApiService;
	private static Logger logger = LoggerFactory.getLogger(ElisaApiController.class);
	/**
	 * 跳转模板对酶标仪设置页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	   @RequiresPermissions("eapi:view")
	public String initPage(Long id, Model model) {
		if(null!= id) {
			ElisaApi api= elisaApiService.selectById(id);
			model.addAttribute("api", api);
		}
		return "/business/elisa/elisa_hmoshe";
	}
	/**
	 * 根据ID查询项目信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryProject")
	@InvokeLog(name = "queryProject", description = "根据ID查询项目信息")
	@ResponseBody
	   @RequiresPermissions("eapi:view")
	public DataRow queryProject(Long id) {
		try {
			ElisaApi api = elisaApiService.selectById(id);
			if (api != null) {
				messageMap.initSuccess(api);
			}
		} catch (Exception e) {
			logger.error("ElisaApiController>>>>>>>>>queryProject>>>>>", e);
		}

		return messageMap;
	}
	/**
	 * 获取所有的项目集合
	 * @return
	 */
	@RequestMapping("/queryAllProjects")
	@InvokeLog(name = "queryAllProjects", description = "获取所有的项目集合")
	@ResponseBody
	   @RequiresPermissions("eapi:view")
	public DataRow queryAllProjects() {
		try {
			Wrapper<ElisaApi> ew = new EntityWrapper<ElisaApi>();
			ew.eq("delFlag", 0);
			List<ElisaApi> lst = elisaApiService.selectList(ew);
			messageMap.initSuccess(lst);
		}catch (Exception e) {
			logger.error("ElisaApiController>>>>>>>>>queryAllProjects>>>>>", e);
		}
		return messageMap;
	}

	/**
	 * 保存项目记录
	 * @param api
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveProject")
	@InvokeLog(name = "saveProject", description = "保存项目记录")
	   @RequiresPermissions("eapi:update")
	public DataRow saveProject(ElisaApi api, String projectNameT) {
		try {
			api.setProjectName(projectNameT);
			if(api.getId()==null) {
				elisaApiService.insert(api);
			}else {
				elisaApiService.updateById(api);
			}
			messageMap.initSuccess();
		}catch (Exception e) {
			logger.error("ElisaApiController>>>>>>>>>saveProject>>>>>", e);
		}
		
		
		
		
		return messageMap;
	}
}

