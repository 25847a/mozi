package com.plasma.buss.site.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.site.service.PlasmaSiteService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.Validator;

import java.util.List;

/**
 * 浆站管理
 * @author fadl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class PlasmaSiteController extends BaseController{
	
	private static final Logger log = LogManager.getLogger(PlasmaSiteController.class);
	
	@Autowired
	public PlasmaSiteService plasmaSiteService;
	
	/**
	 * 跳转浆站列表页面
	 * @return
	 */
	@RequestMapping("/plasmaSiteList")
	public String plasmaSiteList(){
		return "company/plasma_site_list";
	}
	
	/**
	 * 查询浆站列表
	 * @return
	 */
	@RequestMapping("/queryPlasmaSiteList")
	@ResponseBody
	public DataRow queryPlasmaSiteList(){
		try {
			pageBean = plasmaSiteService.queryPlasmaSiteList(info, pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaSiteController>>>>>>>queryProviderBaseinfoList",e);
		}
		return messageMap;
	}
	/**
	 * 查询浆站列表
	 * @return
	 */
	@RequestMapping("/queryPlasmaSiteNoPageList")
	@ResponseBody
	public DataRow queryPlasmaSiteNoPageList(){
		try {
			List<DataRow> list= plasmaSiteService.queryPlasmaSiteNoPageList();
			super.fillResultContext(messageMap, list);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaSiteController>>>>>>>queryPlasmaSiteNoPageList",e);
		}
		return messageMap;
	}
	
	/**
	 * 添加浆站
	 * @return
	 */
	@RequestMapping("/savePlasmaSite")
	@ResponseBody
	public DataRow savePlasmaSite(){
		try {
			Validator val = new Validator(info,messageMap);
			if (val.validate()) {
				if(!StringHelper.isEmpty(info.getString("id"))){
					int res = plasmaSiteService.updatePlasmaSiteById(info);
					if(res>0){
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
						messageMap.put(IConstants.APP_RESULT_MSG,"修改成功");
					}else{
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
						messageMap.put(IConstants.APP_RESULT_MSG, "修改失败");
					}
				}else{
					plasmaSiteService.savePlasmaSite(info);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"添加成功");
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaSiteController>>>>>>>savePlasmaSite",e);
		}
		return messageMap;
	}
	
	/**
	 * 查看详情
	 * @return
	 */
	@RequestMapping("/queryPlasmaSiteById")
	@ResponseBody
	public DataRow queryPlasmaSiteById(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				DataRow row = plasmaSiteService.queryPlasmaSiteById(info.getLong("id"));
				super.fillResultContext(messageMap, row);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaSiteController>>>>>>>queryPlasmaSiteById",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除浆站
	 * @return
	 */
	@RequestMapping("/deletePlasmaSiteById")
	@ResponseBody
	public DataRow deletePlasmaSiteById(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				int res =plasmaSiteService.deletePlasmaSiteById(info.getLong("id"));
				if(res>0){
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"删除成功");
				}else{
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, "删除失败");
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaSiteController>>>>>>>queryPlasmaSiteById",e);
		}
		return messageMap;
	}
}
