package com.fadl.refuseInfo.controller;


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

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.refuseInfo.service.RefuseInfoService;

/**
 * <p>
 * 拒绝信息发布 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
@Controller
@RequestMapping("/refuseInfo")
public class RefuseInfoController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(RefuseInfoController.class);
	
	@Autowired
	public RefuseInfoService refuseInfoService;
	
	/**
	 * 跳转体检拒绝发布页面
	 * @return
	 */
	@RequestMapping("/checkRefuseInfo")
	@RequiresPermissions("checkRefuseInfo:view")
	public String checkRefuseInfo(){
		return "/check/check_refuse_info";
	}
	
	/**
	 * 跳转化验拒绝页面
	 * @return
	 */
	@RequestMapping("/assayRefuseInfo")
	@RequiresPermissions("assayRefuseInfo:view")
	public String assayRefuseInfo(){
		return "/check/assay_refuse_info";
	}
	
	/**
	 * 体检拒绝信息发布列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryCheckRefuseInfoList",method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckRefuseInfoList", description = "体检拒绝信息发布列表")
	@ResponseBody
	@RequiresPermissions("checkRefuseInfo:view")
	public DataRow queryCheckRefuseInfoList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			data.put("type", "0");
			p=refuseInfoService.queryCheckRefuseInfoList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryCheckRefuseInfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 化验拒绝信息发布列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryAssayRefuseInfoList",method = RequestMethod.POST)
	@InvokeLog(name = "queryAssayRefuseInfoList", description = "化验拒绝信息发布列表")
	@ResponseBody
	@RequiresPermissions("assayRefuseInfo:view")
	public DataRow queryAssayRefuseInfoList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			data.put("type", "1");
			p=refuseInfoService.queryAssayRefuseInfoList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryAssayRefuseInfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 化验拒绝信息发布列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryAssayRefuseInfoById",method = RequestMethod.POST)
	@InvokeLog(name = "queryAssayRefuseInfoById", description = "化验拒绝信息发布列表")
	@ResponseBody
	public DataRow queryAssayRefuseInfoById(Long id){
		try {
			if(id>0){
				RefuseInfo refuseInfo = refuseInfoService.selectById(id);
				messageMap.initSuccess(refuseInfo);
			}else{
				messageMap.initFial("请选择浆员");
			}
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryAssayRefuseInfoById",e);
		}
		return messageMap;
	}
	
	/**
	 * 发布体检拒绝信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/upateCheckRefuseInfo",method = RequestMethod.POST)
	@InvokeLog(name = "upateCheckRefuseInfo", description = "发布体检拒绝信息")
	@ResponseBody
	@RequiresPermissions("checkRefuseInfo:update")
	public DataRow upateCheckRefuseInfo(RefuseInfo refuseInfo){
		try {
			refuseInfoService.updateRefuseInfo(refuseInfo,messageMap);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryRefuseInfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 发布化验拒绝信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/upateAssayRefuseInfo",method = RequestMethod.POST)
	@InvokeLog(name = "upateAssayRefuseInfo", description = "发布化验拒绝信息")
	@ResponseBody
	@RequiresPermissions("assayRefuseInfo:update")
	public DataRow upateAssayRefuseInfo(RefuseInfo refuseInfo){
		try {
			refuseInfoService.updateRefuseInfo(refuseInfo,messageMap);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryRefuseInfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 跳转其他拒绝发布页面
	 * @return
	 */
	@RequestMapping("/otherRefuseInfo")
	@RequiresPermissions("checkRefuseInfo:other")
	public String otherRefuseInfo(){
		return "/check/other_refuse";
	}
	
	/**
	 * 生物所反馈
	 * @return
	 */
	@RequestMapping(value="/queryOtherRefuseInfoList",method = RequestMethod.POST)
	@InvokeLog(name = "queryOtherRefuseInfoList", description = "生物所反馈")
	@ResponseBody
	@RequiresPermissions("checkRefuseInfo:other")
	public DataRow queryOtherRefuseInfoList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			data.put("type", "2");
			data.put("isRefuse", "0");
			p=refuseInfoService.queryOtherRefuseInfoList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>queryOtherRefuseInfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 发布生物所拒绝信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/upateOtherRefuseInfo",method = RequestMethod.POST)
	@InvokeLog(name = "upateOtherRefuseInfo", description = "发布生物所拒绝信息")
	@ResponseBody
	@RequiresPermissions("assayRefuseInfo:otherUpdate")
	public DataRow upateOtherRefuseInfo(RefuseInfo refuseInfo){
		try {
			refuseInfoService.upateOtherRefuseInfo(refuseInfo,messageMap);
		} catch (Exception e) {
			logger.error("RefuseInfoController>>>>>>>>>upateOtherRefuseInfo",e);
		}
		return messageMap;
	}
}

