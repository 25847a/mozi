package com.fadl.inspection.controller;


import java.util.Date;

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
import com.fadl.inspection.entity.DetectionProteins;
import com.fadl.inspection.service.DetectionProteinsService;

/**
 * <p>
 * 检验-血红蛋白检测 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/detectionProteins")
public class DetectionProteinsController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(DetectionProteinsController.class);
	@Autowired
	private DetectionProteinsService proteinsService;
	@Autowired
	private ConfigService configService;
	/**
	 * 跳转血红蛋白检测页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("dprot:view")
	public String initPage() {
		return "/business/inspection/in_hemog";
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
	@RequiresPermissions("dprot:view")
	public DataRow queryListByUpdateDate(DetectionProteins proteins, Integer page, Integer limit) {
		try {
			 String startTime = proteins.getStartTime();
	            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime)+" 00:00:00";
	            Page<DataRow> dr = new Page<DataRow>(page,limit);
	            proteins.setStartTime(startTime);
	            Page<DataRow> page2 = proteinsService.queryListByUpdateDate(dr, proteins);
	            messageMap.initPage(page2);
		}catch (Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>queryListByUpdateDate>>>>>", e);
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
	@RequiresPermissions("dprot:view")
	public DataRow queryByID(@NotEmpty Long id) {
		try {
			messageMap.initSuccess(proteinsService.queryDetectionProteinsById(id));
		} catch (Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>queryByID>>>>>", e);
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
	@RequiresPermissions("dprot:update")
	public DataRow updateInfoById(DetectionProteins proteins) {
		try {
			DetectionProteins proteins2 = proteinsService.selectById(proteins.getId());
			proteins2.setResult(proteins.getResult());
			proteins2.setValue(proteins.getValue());
			if("2".equals(proteins.getResult())) {
				proteins2.setValue(null);
			}
			proteinsService.updateInfoById(proteins2, messageMap);
		}catch(Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>updateInfoById>>>>>", e);
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
	@RequiresPermissions("dprot:cancel")
	public DataRow updateInfoById0(DetectionProteins proteins) {
		try {
			DetectionProteins proteins2 = proteinsService.selectById(proteins.getId());
			proteins2.setResult(proteins.getResult());
			proteins2.setValue(proteins.getValue());
			if("2".equals(proteins.getResult())) {
				proteins2.setValue(null);
			}
			proteinsService.updateInfoById(proteins2, messageMap);
		}catch(Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>updateInfoById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	
	  /**
     * 跳转血红蛋白检测报表
     * @return
     */
    //@RequiresPermissions("dprot:report")
	@RequestMapping(value="/gotoPrint", method=RequestMethod.GET)
    @InvokeLog(name = "gotoPrint", description = "跳转血红蛋白检测报表")
	public String gotoPrint(Model model,String chooseDate) {
		try {
			Config config = configService.getConfig("stock_config", "company");
			model.addAttribute("config",config);
		} catch (Exception e) {
			logger.error("DetectionProteinsController>>>>>>>>gotoPrint>>>>>", e);
		}
		model.addAttribute("checkDate", chooseDate);
		return "/business/inspection/a_xuejianc";
	}
    /**
     * 查询血红蛋白检测报表
     * @return
     */
    //@RequiresPermissions("dprot:report")
	@RequestMapping(value="/printReport", method=RequestMethod.POST)
    @InvokeLog(name = "printReport", description = " 查询血红蛋白检测报表")
	@ResponseBody
    public DataRow printReport( String chooseDate) {
		try {
			chooseDate = DateUtil.getSystemDate(DateUtil.formatDate(chooseDate, DateUtil.yyyy_MM_dd_EN));
		} catch (Exception e) {
			chooseDate = DateUtil.getSystemDate(null);
		}
		messageMap.initSuccess(proteinsService.queryInfosByChooseDate(chooseDate));
    	return messageMap;
    }
}

