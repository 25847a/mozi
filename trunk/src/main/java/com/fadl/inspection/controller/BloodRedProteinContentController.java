package com.fadl.inspection.controller;


import java.util.Date;
import java.util.List;

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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.inspection.entity.BloodRedProteinContent;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.BloodRedProteinContentService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.entity.Supply;
import com.fadl.supplies.service.OutstockService;
import com.fadl.supplies.service.SupplyService;

/**
 * <p>
 * 检验-血红蛋白含量表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/bloodRedProteinContent")
public class BloodRedProteinContentController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(SpecimenCollectionController.class);
	
	@Autowired
	private BloodRedProteinContentService bloodRedProteinContentService;
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private SupplyService supplyService;
	@Autowired
	private OutstockService outStockService;
	/**
	 * 跳转蛋白含量页面
	 * 
	 * @return
	 */
    @RequiresPermissions("brpc:view")
	@RequestMapping("/initPage")
	public String initPage() {
		return "/business/inspection/in_hemprotein";
	}
	
	/**
	 * 根据更新时间查找所有的血红蛋白含量检测的记录带分页
	 * @param content
	 * @param page
	 * @param limit
	 * @return
	 */
    @RequiresPermissions("brpc:view")
	@RequestMapping(value="/queryListByUpdateDate", method=RequestMethod.POST)
    @InvokeLog(name = "queryListByUpdateDate", description = "根据更新时间查找所有的血红蛋白含量检测的记录带分页")
	@ResponseBody
	public DataRow queryListByUpdateDate(BloodRedProteinContent content, Integer page, Integer limit) {
    	
		try {
			 String startTime = content.getStartTime();
	            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime);
	            Page<DataRow> dr = new Page<DataRow>(page,Integer.MAX_VALUE);
	            content.setStartTime(startTime);
	            Page<DataRow> page2 = bloodRedProteinContentService.queryListByUpdateDate(dr, content);
	            messageMap.initPage(page2);
		}catch (Exception e) {
			logger.error("BloodRedProteinContentController>>>>>>>>queryListByUpdateDate>>>>>", e);
		}
		return messageMap;
	}
	@RequestMapping("/queryBloodRedHeadInfo")
	@ResponseBody
	public DataRow queryBloodRedHeadInfo(BloodRedProteinContent content){
		try {
			String startTime = content.getStartTime();
            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime);
            content.setStartTime(startTime);
            EntityWrapper<BloodRedProteinContent> ew = new EntityWrapper<BloodRedProteinContent>();
            ew.eq("providerNo", content.getProviderNo());
            ew.eq("DATE_FORMAT(createDate,'%Y-%m-%d')", content.getStartTime());
            ew.eq("isCollection", 0);
            BloodRedProteinContent blood= bloodRedProteinContentService.selectOne(ew);
            messageMap.initSuccess(blood);
		} catch (Exception e) {
			logger.error("BloodRedProteinContentController>>>>>>>>queryBloodRedHeadInfo>>>>>", e);
		}
		return messageMap;
		
	}
	/**
	 * 根据ID查找具体的血红蛋白含量信息
	 * @param id
	 * @return
	 */
    @RequiresPermissions("brpc:view")
	@RequestMapping(value="/queryByID", method=RequestMethod.POST)
    @InvokeLog(name = "queryByID", description = "根据ID查找具体的血红蛋白含量信息")
	@ResponseBody
	public DataRow queryByID(@NotEmpty Long id) {
		try {
			messageMap.initSuccess(bloodRedProteinContentService.queryBloodRedProteinContentById(id));
		} catch (Exception e) {
			logger.error("BloodRedProteinContentController>>>>>>>>queryByID>>>>>", e);
		}
		return messageMap;
		
	}
	
	/**
	 * 根据ID修改血红蛋白含量信息,并生成血红蛋白检测信息
	 * @param content
	 * @return
	 */
    @RequiresPermissions("brpc:update")
	@RequestMapping(value="/updateInfoById", method=RequestMethod.POST)
    @InvokeLog(name = "updateInfoById", description = "根据ID修改血红蛋白含量信息,并生成血红蛋白检测信息")
	@ResponseBody
	public DataRow updateInfoById(BloodRedProteinContent content) {
		try {
			BloodRedProteinContent content2 = bloodRedProteinContentService.selectById(content.getId());
			content2.setBluestone(content.getBluestone());
			content2.setResultId(content.getResultId());
			content2.setCheckedAdminId(content.getCheckedAdminId());
			content2.setIsCollection(1);
			bloodRedProteinContentService.updateInfoById(content2,messageMap);
			
		}catch(Exception e) {
			logger.error("BloodRedProteinContentController>>>>>>>>updateInfoById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
    
    /**
	 * 根据ID撤回血红蛋白含量信息,并删除血红蛋白检测信息
	 * @param content
	 * @return
	 */
    @RequiresPermissions("brpc:cancel")
	@RequestMapping(value="/updateInfoById0", method=RequestMethod.POST)
    @InvokeLog(name = "updateInfoById0", description = "根据ID撤回血红蛋白含量信息,并删除血红蛋白检测信息")
	@ResponseBody
	public DataRow updateInfoById0(BloodRedProteinContent content) {
		try {
			BloodRedProteinContent content2 = bloodRedProteinContentService.selectById(content.getId());
			content2.setBluestone(null);
			content2.setResultId(null);
			content2.setCheckedAdminId(null);
			content2.setIsCollection(0);
			bloodRedProteinContentService.updateInfoById(content2,messageMap);
		}catch(Exception e) {
			logger.error("BloodRedProteinContentController>>>>>>>>updateInfoById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
    
    
    /**
     * 跳转  血红蛋白含量报表
     * @return
     */
    //@RequiresPermissions("brpc:report")
	@RequestMapping(value="/printReport", method=RequestMethod.GET)
    @InvokeLog(name = "printReport", description = "跳转  血红蛋白含量报表")
    public String printReport(Model model, String chooseDate) {
		try {
			chooseDate = DateUtil.getSystemDate(DateUtil.formatDate(chooseDate, DateUtil.yyyy_MM_dd_EN));
			Config config = configService.getConfig("stock_config", "company");
			model.addAttribute("config",config);
		} catch (Exception e) {
			chooseDate = DateUtil.getSystemDate(null);
		}
		List<DataRow>  drs = bloodRedProteinContentService.queryInfosByChooseDate(chooseDate);
 		int manSize =0, womanSize =0 , qualified = 0;
		for(DataRow dr : drs) {
			if(dr.getInt("sex")==1) { // 得到男女总数
				womanSize ++;
			}else if(dr.getInt("sex")==0) {
				manSize ++;
			}
			if(dr.getInt("bluestone")==0) {// 得到合格总数
				qualified ++;
			}
			
		}
		// 得到血红蛋白含量的检测配置信息
		Wrapper<Config> ewConfig = new EntityWrapper<Config>();
		ewConfig.eq("type", "elisa_check_project").eq("lable", "全血比重");
		Config config = configService.selectOne(ewConfig);
		// 得到检验配置信息
		Wrapper<TestConfigInfo> ew = new EntityWrapper<TestConfigInfo>();
		ew.eq("projectName", config.getValue());
		ew.ge("endTime", chooseDate);
		ew.le("startDate", chooseDate);
		TestConfigInfo tci = tciService.selectOne(ew);
		// 得到耗材信息
		Outstock os = outStockService.selectById(tci.getReagentBatch()); 
		Supply supply = supplyService.selectById(tci.getVenderid());
		// 得到检验方法信息
		Wrapper<Config> ewConfig1 = new EntityWrapper<Config>();
		ewConfig1.eq("type", "check_method").eq("value", tci.getTestingMethodid());
		Config config1 = configService.selectOne(ewConfig1);
		model.addAttribute("outStock", os);
		model.addAttribute("config1", config1);
		model.addAttribute("supply", supply);
		model.addAttribute("tci", tci);
		model.addAttribute("lists", drs);
		model.addAttribute("qualified", qualified);
		model.addAttribute("manSize", manSize);
		model.addAttribute("womanSize", womanSize);
    	model.addAttribute("checkDate", chooseDate);
    	return "/business/inspection/a_xuebaihl";
    }
}

