package com.fadl.collectionConfig.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.collectionConfig.entity.Disease;
import com.fadl.collectionConfig.service.DiseaseService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
/**
 * <p>
 * 淘汰表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-06-27
 */
@Controller
@RequestMapping("/disease")
public class DiseaseController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(DiseaseController.class);
	
	@Autowired
	DiseaseService diseaseService;
	/**
	 * 跳转淘汰设置页面
	 * @return
	 */
	@RequestMapping("/diseasePage")
	public String diseasePage() {
		return "/collectionConfig/disease";
	}
	/**
	 * 跳转淘汰新增设置页面
	 * @return
	 */
	@RequestMapping("/diseaseAdd")
	public String diseaseAdd() {
		return "/collectionConfig/disease_add";
	}
	/**
	 * 跳转淘汰修改设置页面
	 * @return
	 */
	@RequestMapping("/diseaseUpdate")
	public String diseaseUpdate(String id, Model model) {
		try {
			Disease disease = new Disease();
			disease = diseaseService.selectById(id);
    		model.addAttribute("disease",disease);
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>diseaseUpdate>>>>>",e);
		}
		return "/collectionConfig/disease_update";
	}
	/**
	 * 淘汰设置列表
	 * @param disease
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryDiseaseList",method= RequestMethod.POST)
	@InvokeLog(name = "queryDiseaseList", description = "淘汰设置列表")
	@ResponseBody
	public DataRow queryAreaList(Disease disease, Integer page, Integer limit) {
		Page<Disease> paging = null;
		try {
			EntityWrapper<Disease> ew=new EntityWrapper<Disease>();
			ew.where("isDelete=0");
	        ew.like("name", disease.getName(),SqlLike.DEFAULT);
			paging = new Page<Disease>(page, limit);
			paging = diseaseService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>>>>>queryDiseaseList",e);
		}
		return messageMap;
	}
	/**
	 * 新增淘汰设置
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/insertDisease",method= RequestMethod.POST)
	@InvokeLog(name = "insertDisease", description = "新增淘汰设置")
	@ResponseBody
	public DataRow insertDisease(Disease disease) {
		try {
			diseaseService.insertDisease(disease,messageMap);
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>>>>>insertDisease",e);
		}
		return messageMap;
	}
	/**
	 * 修改淘汰设置
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/updateDisease",method= RequestMethod.POST)
	@InvokeLog(name = "updateDisease", description = "修改淘汰设置")
	@ResponseBody
	public DataRow updateDisease(Disease disease) {
		try {
			diseaseService.updateDisease(disease,messageMap);
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>>>>>updateDisease",e);
		}
		return messageMap;
	}
	/**
	 * 删除淘汰设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteDisease",method= RequestMethod.POST)
	@InvokeLog(name = "deleteDisease", description = "删除淘汰设置")
	@ResponseBody
	public DataRow deleteDisease(@RequestParam Long ids) {
		try {
			Disease d = new Disease();
			d.setIsDelete(1);
			d.setId(ids);
			boolean res = diseaseService.updateById(d);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>>>>>deleteDisease",e);
		}
		return messageMap;
	}
	/**
	 * 查询仓库信息
	 * @return
	 */
	@RequestMapping(value="/queryDiseaseInfo" , method= RequestMethod.POST)
	@InvokeLog(name = "queryDiseaseInfo", description = "查询仓库信息")
	@ResponseBody
	public DataRow queryDiseaseInfo() {
		EntityWrapper<Disease> ew = new EntityWrapper<Disease>();
		try {
			ew.eq("isDelete", 0);
			List<Disease> list =diseaseService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("DiseaseController>>>>>>>>>>>>>queryDiseaseInfo",e);
		}
		return messageMap;
	}
}

