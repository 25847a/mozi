package com.fadl.supplies.controller;


import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Template;
import com.fadl.supplies.service.TemplateService;

/**
 * <p>
 * 耗材模板配置 前端控制器
 * </p>
 * @author 啊健
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/template")
public class TemplateController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	TemplateService templateService;
	
	//跳转耗材模板页面
	@RequestMapping("/templatePage")
	@RequiresPermissions("template:view")
	public String templatePage() {
		return "/supplies/used/used";
	}
	//耗材模块添加
	@RequestMapping("/templateAdd")
	public String templateAdd(String apply,String depotId,Model model) {
		model.addAttribute("apply",apply);
		model.addAttribute("depotId",depotId);
		return "/supplies/used/used_add";
	}
	//耗材模块修改
	@RequestMapping("/templateUpdate")
	public String templateUpdate(Long id,Model model) throws ParseException {
		Template template =templateService.selectById(id);
		template.setStartDate(DateUtil.sfDay.format(DateUtil.sfDay.parse(template.getStartDate())));
		template.setEndDate(DateUtil.sfDay.format(DateUtil.sfDay.parse(template.getEndDate())));
		model.addAttribute("template",template);
		return "/supplies/used/used_update";
	}
	/**
	 * 查询耗材模板配置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryTemplateList")
	@InvokeLog(name = "queryTemplateList", description = "查询耗材模板配置列表")
	@ResponseBody
	public DataRow queryTemplateList(@RequestParam Map<String,String> map,Integer page,Integer limit) {
		Page<DataRow> pageing=null;
		try {
			pageing = new Page<DataRow>(page,limit);
			templateService.queryTemplateList(pageing, map);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>queryTemplateList",e);
		}
		return messageMap;
	}
	/**
	 * 添加耗材模板的耗材列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryTemplateSuppliesInfo")
	@InvokeLog(name = "queryTemplateSuppliesInfo", description = "添加耗材模板的耗材列表")
	@ResponseBody
	public DataRow queryTemplateSuppliesInfo(@RequestParam Map<String,Object> map,Integer page,Integer limit) {
		Page<DataRow> pageing=null;
		try {
			pageing = new Page<DataRow>(page,limit);
			templateService.queryTemplateSuppliesInfo(map,pageing);
			messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>queryTemplateSuppliesInfo",e);
		}
		return messageMap;
	}
	/**
	 * 新增耗材模板配置
	 * @param Template
	 * @return
	 */
	@RequestMapping(value = "/insertTemplate")
	@InvokeLog(name = "insertTemplate", description = "新增耗材模板配置")
	@ResponseBody
	@RequiresPermissions("template:add")
	public DataRow insertTemplate(@RequestParam Map<String,String> map) {
		try {
			templateService.insertTemplate(map,messageMap);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>>>>>insertTemplate",e);
		}
		return messageMap;
	}
	/**
	 * 修改耗材模板配置
	 * @param template
	 * @return
	 */
	@RequestMapping("/updateTemplate")
	@InvokeLog(name = "updateTemplate", description = "修改耗材模板配置")
	@ResponseBody
	public DataRow updateTemplate(Template template) {
		try {
			templateService.updateTemplate(template,messageMap);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>>>>>updateTemplate",e);
		}
		return messageMap;
	}
	/**
	 * 删除耗材模板配置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTemplate")
	@InvokeLog(name = "deleteTemplate", description = "删除耗材模板配置")
	@ResponseBody
	@RequiresPermissions("template:delect")
	public DataRow deleteTemplate(Template template) {
		try {
			templateService.deleteTemplate(template,messageMap);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>>>>>deleteTemplate",e);
		}
		return messageMap;
	}
	/**
	 * 查询耗材采浆模板配置(康康使用)
	 * @param t
	 * @return
	 */
	@RequestMapping(value="/queryPulpingTemplate")
	@InvokeLog(name = "queryPulpingTemplate", description = "查询耗材采浆模板配置")
	@ResponseBody
	public DataRow queryPulpingTemplate(Template t) {
		try {
			EntityWrapper<Template> ew = new EntityWrapper<Template>();
			ew.eq("isDisable", 1);
			ew.eq("apply", t.getApply());
			ew.eq("isDelete", 0);
			List<Template> list =templateService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("TemplateController>>>>>>>>>>>>>queryPulpingTemplate", e);
		}
		return messageMap;
	}
}
