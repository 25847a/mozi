package com.fadl.elisa.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.ElisaTemplateValues;
import com.fadl.elisa.service.ElisaTemplateService;

/**
 * <p>
 * 酶标板模板表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Controller
@RequestMapping("/elisaTemplate")
public class ElisaTemplateController extends AbstractController{
	@Autowired
	private ElisaTemplateService elisaTemplateService;
	
	
	private static Logger logger = LoggerFactory.getLogger(ElisaTemplateService.class);
	
	
	/**
	 * 跳转模板配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("etemp:view")
	public String initPage() {
		return "/business/elisa/elisa_mobanp";
	}
	
	/**
	 * 查询所有没有被删除的酶标仪测试模板记录
	 * @param elisaTemplate
	 * @return
	 */
	@RequestMapping(value="/queryTemplates", method=RequestMethod.POST)
	@InvokeLog(name = "queryTemplates", description = "查询所有没有被删除的酶标仪测试模板记录")
	@ResponseBody
	@RequiresPermissions("etemp:view")
	public DataRow queryTemplates(ElisaTemplate elisaTemplate) {
		try {
			Wrapper<ElisaTemplate> ew = new EntityWrapper<ElisaTemplate>();
			ew.eq("delFlag", 0);
			if(null != elisaTemplate.getIsAuto()) {
				ew.eq("isAuto", elisaTemplate.getIsAuto());
			}
			messageMap.initSuccess(elisaTemplateService.selectList(ew));
		} catch (Exception e) {
			logger.error("ElisaTemplateController>>>>>>>>>queryTemplates>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 新增酶标板模板配置
	 * @param elisaTemplate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertElisaTemplate", method=RequestMethod.POST)
	@InvokeLog(name = "insertElisaTemplate", description = "新增酶标板模板配置")
	@RequiresPermissions("etemp:save")
	public DataRow insertElisaTemplate(ElisaTemplate elisaTemplate, HttpServletRequest request ) {
		String [] etvs = request.getParameter("etvs").split(",");
		List<ElisaTemplateValues> list = new ArrayList<ElisaTemplateValues>();
		List<String> types =Arrays.asList("STD","SMP","PC","PCⅡ","NC","QC","BLK","");
		// 格式化酶标板中的孔对应的值
		for(String etv : etvs) {
			String [] value =  etv.split(":");
			ElisaTemplateValues elisaTemplateValues = new ElisaTemplateValues();
			elisaTemplateValues.setEtId(elisaTemplate.getId());
			elisaTemplateValues.setEtvIndex(Integer.valueOf(value[0]));
			String type ="";
			if(value.length>1) {
				String value1= value[1];
				elisaTemplateValues.setTitle(value1);
				type = value1.substring(0, value1.indexOf("(")-1);
				if(HasDigit(type)) {
					type = type.substring(0,type.length()-1);
				}
			}
			elisaTemplateValues.setType(types.indexOf(type));
			list.add(elisaTemplateValues);
		}
		elisaTemplate.setElisaTemplateValues(list);
		try {
			if(null!=elisaTemplate.getId()) {
				if(elisaTemplateService.updateTemplate(elisaTemplate,messageMap)) {
					messageMap.initSuccess();
				}
			}else {
				if(elisaTemplateService.insertTemplate(elisaTemplate,messageMap)) {
					messageMap.initSuccess();
				}
			}
		} catch (Exception e) {
			logger.error("ElisaTemplateController>>>>>>>>>insertElisaTemplate>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID查找模板信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryElisaTemplateById", method=RequestMethod.POST)
	@InvokeLog(name = "queryElisaTemplateById", description = "根据ID查找模板信息")
	@RequiresPermissions("etemp:view")
	public DataRow queryElisaTemplateById(Long id) {
		if(null==id) {
			return messageMap;
		}
		try {
			ElisaTemplate elisaTemplate = elisaTemplateService.selectById(id);
			messageMap.initSuccess(elisaTemplate);
		}catch (Exception e) {
			logger.error("ElisaTemplateController>>>>>>>>>queryElisaTemplateById>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 根据ID删除模板信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delElisaTemplateById", method=RequestMethod.POST)
	@InvokeLog(name = "delElisaTemplateById", description = "根据ID删除模板信息")
	@RequiresPermissions("etemp:delete")
	public DataRow delElisaTemplateById(Long id) {
		if(null==id) {
			return messageMap;
		}
		try {
			if(elisaTemplateService.deleteById(id)) {
				messageMap.initSuccess();
			}
		}catch (Exception e) {
			logger.error("ElisaTemplateController>>>>>>>>>queryElisaTemplateById>>>>>", e);
		}
		
		
		
		return messageMap;
	}
	
	/**
	 * 判断字符中是否有数字  
	 * @param content
	 * @return
	 */
	private boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}

