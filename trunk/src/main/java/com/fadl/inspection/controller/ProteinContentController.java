package com.fadl.inspection.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.inspection.entity.ProteinContent;
import com.fadl.inspection.service.ProteinContentService;
import com.fadl.inspection.service.TestConfigInfoService;

/**
 * <p>
 * 检验-蛋白含量 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/proteinContent")
public class ProteinContentController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(DetectionProteinsController.class);
	@Autowired
	private ProteinContentService proteinContentService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private TestConfigInfoService tciService;
	/**
	 * 跳转蛋白含量页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("pcc:view")
	public String initPage() {
		return "/business/inspection/in_protein";
	}
	
	/**
	 * 根据更新时间查找所有的血红蛋白检测的记录带分页
	 * @param proteins
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryListByUpdateDate", method=RequestMethod.POST)
	@InvokeLog(name = "queryListByUpdateDate", description = "根据更新时间查找所有的血红蛋白检测的记录带分页")
	@ResponseBody
	@RequiresPermissions("pcc:view")
	public DataRow queryListByUpdateDate(ProteinContent content, Integer page, Integer limit) {
		try {
			 String startTime = content.getStartTime();
	            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime)+" 00:00:00";
	            Page<DataRow> dr = new Page<DataRow>(page,limit);
	            Page<DataRow> page2 = proteinContentService.queryListByUpdateDate(dr, content);
	            messageMap.initPage(page2);
		}catch (Exception e) {
			logger.error("ProteinContentController>>>>>>>>queryListByUpdateDate>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 查询头部信息(啊健)
	 * @param content
	 * @return
	 */
	@RequestMapping("/queryProteinHeadInfo")
	@ResponseBody
	public DataRow queryProteinHeadInfo(ProteinContent content) {
		try {
			String startTime = content.getStartTime();
            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime);
			proteinContentService.queryProteinHeadInfo(content,messageMap);
			
		} catch (Exception e) {
			logger.error("ProteinContentController>>>>>>>>queryProteinHeadInfo>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID查找具体的血红蛋白检测信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryByID", method=RequestMethod.POST)
	@InvokeLog(name = "queryByID", description = "根据ID查找具体的血红蛋白检测信息")
	@ResponseBody
	@RequiresPermissions("pcc:view")
	public DataRow queryByID(@NotEmpty Long id) {
		try {
			messageMap.initSuccess(proteinContentService.queryProteinContentById(id));
		} catch (Exception e) {
			logger.error("ProteinContentController>>>>>>>>queryByID>>>>>", e);
		}
		return messageMap;
		
	}
	/**
	 * 根据ID修改血红蛋白检测信息,并生成蛋白含量信息
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/updateInfoById", method=RequestMethod.POST)
	@InvokeLog(name = "updateInfoById", description = "根据ID修改血红蛋白检测信息,并生成蛋白含量信息")
	@ResponseBody
	@RequiresPermissions("pcc:edit")
	public DataRow updateInfoById(ProteinContent proteins) {
		try {
			ProteinContent proteins2 = proteinContentService.selectById(proteins.getId());
			proteins2.setValue(proteins.getValue());
			proteins2.setResult(proteins.getResult());
			proteins2.setProtein(proteins.getProtein());
			if(2==proteins.getResult()) {
				proteins2.setValue(null);
				proteins2.setProtein(null);
			}
			proteinContentService.updateInfoById(proteins2, messageMap);
			messageMap.initSuccess();
		}catch(Exception e) {
			logger.error("ProteinContentController>>>>>>>>updateInfoById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	
	/**
	 * 根据ID撤回血红蛋白检测信息,并删除蛋白含量信息
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/updateInfoById0", method=RequestMethod.POST)
	@InvokeLog(name = "updateInfoById0", description = "根据ID撤回血红蛋白检测信息,并删除蛋白含量信息")
	@ResponseBody
	@RequiresPermissions("pcc:cancel")
	public DataRow updateInfoById0(ProteinContent proteins) {
		try {
			ProteinContent proteins2 = proteinContentService.selectById(proteins.getId());
			proteins2.setValue(proteins.getValue());
			proteins2.setResult(proteins.getResult());
			proteins2.setProtein(proteins.getProtein());
			if(2==proteins.getResult()) {
				proteins2.setValue(null);
				proteins2.setProtein(null);
			}
			proteinContentService.updateInfoById(proteins2, messageMap);
			messageMap.initSuccess();
		}catch(Exception e) {
			logger.error("ProteinContentController>>>>>>>>updateInfoById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	
	  /**
     * 跳转蛋白含量检测报表
     * @return
     */
    //@RequiresPermissions("pcc:report")
	@RequestMapping(value="/gotoPrint", method=RequestMethod.GET)
    @InvokeLog(name = "gotoPrint", description = "跳转血红蛋白检测报表")
	public String gotoPrint(Model model,String chooseDate, String type) {
		try {
			Config config = configService.getConfig("stock_config", "company");
			model.addAttribute("config",config);
		} catch (Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>gotoPrint>>>>>", e);
		}
		model.addAttribute("checkDate", chooseDate);
		model.addAttribute("type", type);
		return "/business/inspection/a_danbaihl";
	}
    /**
     * 查询蛋白含量检测报表
     * @return
     */
    //@RequiresPermissions("pcc:report")
	@RequestMapping(value="/printReport", method=RequestMethod.POST)
    @InvokeLog(name = "printReport", description = " 查询血红蛋白检测报表")
	@ResponseBody
    public DataRow printReport( String chooseDate) {
		try {
			chooseDate = DateUtil.getSystemDate(DateUtil.formatDate(chooseDate, DateUtil.yyyy_MM_dd_EN));
		} catch (Exception e) {
			chooseDate = DateUtil.getSystemDate(null);
		}
		List<DataRow> lst1 = proteinContentService.queryInfosByChooseDate(chooseDate);
		messageMap.initSuccess(lst1);
		HashMap<String, String> map = new HashMap<String, String>(); 
		try {
			map.put("lable", "全血比重");
			map.put("endTime", chooseDate);
			List<DataRow> lst= tciService.querySuppliesInfoByProjectNameLable(map);
			if(lst1.size()!= 0 && lst.size()!=0) {
				messageMap.put("bzItem", lst.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return messageMap;
    }

}

