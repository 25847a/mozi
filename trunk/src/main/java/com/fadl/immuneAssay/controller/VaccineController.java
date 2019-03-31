package com.fadl.immuneAssay.controller;


import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.entity.Vaccine;
import com.fadl.immuneAssay.service.VaccineService;

/**
 * <p>
 * 疫苗设置 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-14
 */
@Controller
@RequestMapping("/vaccine")
public class VaccineController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(VaccineController.class);
	
	@Autowired
	VaccineService vaccineService;
	@Autowired
	private ConfigService configService;
	 /**
     * 跳转疫苗设置页面
     */
    @RequestMapping("/vaccinePage")
    @RequiresPermissions("vaccine:view")
    public String vaccinePage() {
        return "/immune/config/vaccine";
    }
	 /**
     * 跳转疫苗设置添加页面
     */
    @RequestMapping("/vaccineAddPage")
    public String vaccineAddPage() {
        return "/immune/config/vaccine_add";
    }
    /**
	 * 跳转疫苗设置修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/vaccineUpdate")
    public String vaccineUpdate(String id, Model model) {
    	try {
    		Vaccine vaccine = new Vaccine();
    		vaccine = vaccineService.selectById(id);
    		vaccine.setEndDate(DateUtil.sfDay.format(DateUtil.sfDay.parse(vaccine.getEndDate())));
    		vaccine.setValidMonth(DateUtil.sfDay.format(DateUtil.sfDay.parse(vaccine.getValidMonth())));
    		model.addAttribute("vaccine",vaccine);
		} catch (Exception e) {
			logger.error("VaccineController>>>>>>>>>vaccineUpdate>>>>>",e);
		}
        return "/immune/config/vaccine_update";
    }
	/**
	 * 疫苗设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/vaccineList", method= RequestMethod.POST)
	@InvokeLog(name = "vaccineList", description = "疫苗设置列表")
	@RequiresPermissions("vaccine:list")
	@ResponseBody
	public DataRow vaccineList(@RequestParam Map<String,String> map, Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			Config config = configService.queryConfig("immune_type","1");
			if(config.getIsDisable()==1) {
				map.put("type", "1");
			}
			paging = new Page<DataRow>(page, limit);
			vaccineService.vaccineList(paging,map);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("VaccineController>>>>>>>>>>>>>vaccineList",e);
		}
		return messageMap;
	}
	/**
	 * 新增疫苗设置
	 * @param vaccine
	 * @return
	 */
	@RequestMapping(value = "/insertVaccine", method= RequestMethod.POST)
	@InvokeLog(name = "insertVaccine", description = "新增疫苗设置")
	@RequiresPermissions("vaccine:add")
	@ResponseBody
	public DataRow insertVaccine(Vaccine vaccine) {
		try {
			vaccineService.insertVaccine(vaccine,messageMap);
		} catch (Exception e) {
			logger.error("VaccineController>>>>>>>>>>>>>insertVaccine",e);
		}
		return messageMap;
	}
	/**
	 * 修改疫苗设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/updateVaccine", method= RequestMethod.POST)
	@InvokeLog(name = "updateVaccine", description = "修改疫苗设置")
	@RequiresPermissions("vaccine:update")
	@ResponseBody
	public DataRow updateVaccine(Vaccine vaccine) {
		try {
			vaccineService.updateVaccine(vaccine,messageMap);
		} catch (Exception e) {
			logger.error("VaccineController>>>>>>>>>>>>>updateVaccine",e);
		}
		return messageMap;
	}
	/**
	 * 删除疫苗设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteVaccine", method= RequestMethod.POST)
	@InvokeLog(name = "deleteVaccine", description = "删除疫苗设置")
	@RequiresPermissions("vaccine:delete")
	@ResponseBody
	public DataRow deleteVaccine(Long ids) {
		try {
			vaccineService.deleteVaccine(ids,messageMap);
		} catch (Exception e) {
			logger.error("VaccineController>>>>>>>>>>>>>deleteVaccine",e);
		}
		return messageMap;
	}
    
    
}

