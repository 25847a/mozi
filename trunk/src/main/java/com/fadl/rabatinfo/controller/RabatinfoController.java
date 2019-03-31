package com.fadl.rabatinfo.controller;


import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;

/**
 * <p>
 * 胸片检查记录表 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-08
 */
@Controller
@RequestMapping("/rabatinfo")
public class RabatinfoController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(RabatinfoController.class);

	@Autowired
	public RabatinfoService rabatinfoService;
	
	/**
	 * 跳转浆员胸片页面
	 * @return
	 */
	@RequestMapping("/rabat")
	@RequiresPermissions("rabatinfo:view")
	public String chest(){
		return "/check/rabat";
	}
	
	/**
	 * 胸片记录查询页面
	 * @return
	 */
	@RequestMapping("/rabatinfoList")
	@RequiresPermissions("rabatinfo:view")
	public String rabatinfoList(){
		return "/check/rabatinfo_list";
	}
	
	/**
	 * 查询胸片列表
	 * @return
	 */
	@RequestMapping(value="/queryRabatinfoList",method = RequestMethod.POST)
	@InvokeLog(name = "queryRabatinfoList", description = "查询胸片列表")
	@ResponseBody
	@RequiresPermissions("rabatinfo:view")
	public DataRow queryRabatinfoList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
            Page<DataRow> p = new Page<DataRow>(page,limit);
            rabatinfoService.queryRabatinfoListByProviderNo(data, p);
            messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("RabatinfoController>>>>>>>>>queryRabatinfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询胸片详情
	 * @return
	 */
	@RequestMapping(value="/queryRabatinfoById",method = RequestMethod.POST)
	@InvokeLog(name = "queryRabatinfoById", description = "查询胸片详情")
	@ResponseBody
	public DataRow queryRabatinfoById(Long id){
		try {
			Rabatinfo rabatinfo = rabatinfoService.selectById(id);
            messageMap.initSuccess(rabatinfo);
		} catch (Exception e) {
			logger.error("RabatinfoController>>>>>>>>>queryRabatinfoById",e);
		}
		return messageMap;
	}
	
	/**
	 * 录入胸片结果
	 * @return
	 */
	@RequestMapping(value="/insertRabatinfo",method = RequestMethod.POST)
	@InvokeLog(name = "insertRabatinfo", description = "录入胸片结果")
	@ResponseBody
	@RequiresPermissions("rabatinfo:add")
	public DataRow insertRabatinfo(MultipartFile file,Rabatinfo rabatinfo){
		try {
			if (!StringHelper.isEmpty(rabatinfo.getProviderNo())) {
				rabatinfoService.insertRabatinfo(file, rabatinfo, messageMap);
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("RabatinfoController>>>>>>>>>insertRabatinfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 判断浆员是否需要 胸片检查(1年1次)
	 * @return
	 */
	@RequestMapping(value="/queryProviderLastTime",method = RequestMethod.POST)
	@InvokeLog(name = "queryProviderLastTime", description = "判断浆员是否需要 胸片检查(1年1次)")
	@ResponseBody
	public DataRow queryProviderLastTime(Rabatinfo rabatinfo){
		try {
			DataRow row = rabatinfoService.queryProviderLastTime(rabatinfo);
			messageMap.initSuccess(row);
		} catch (Exception e) {
			logger.error("RabatinfoController>>>>>>>>>queryProviderLastTime",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询胸片信息
	 * @return
	 */
	@RequestMapping(value="/queryRabationInfoByAllId",method = RequestMethod.POST)
	@InvokeLog(name = "queryRabationInfoByAllId", description = "判断浆员是否需要 胸片检查(1年1次)")
	@ResponseBody
	public DataRow queryRabationInfoByAllId(Rabatinfo rabatinfo){
		try {
			EntityWrapper<Rabatinfo> rab = new EntityWrapper<Rabatinfo>();
			rab.eq("allId", rabatinfo.getAllId());
			rabatinfo = rabatinfoService.selectOne(rab);
			messageMap.initSuccess(rabatinfo);
		} catch (Exception e) {
			logger.error("RabatinfoController>>>>>>>>>queryRabationInfoByAllId",e);
		}
		return messageMap;
	}
}

