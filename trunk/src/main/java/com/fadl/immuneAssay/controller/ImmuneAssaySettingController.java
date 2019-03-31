package com.fadl.immuneAssay.controller;

import java.util.Map;
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
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.ImmuneAssaySetting;
import com.fadl.immuneAssay.service.ImmuneAssaySettingService;

/**
 * <p>
 * 免疫化验效价值设置 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-08-25
 */
@Controller
@RequestMapping("/assaySetting")
public class ImmuneAssaySettingController extends AbstractController{
	
private static Logger logger = LoggerFactory.getLogger(ImmuneAssayController.class);
	
	@Autowired
	ImmuneAssaySettingService immuneAssaySettingService;
	
	/**
	 * 跳转效价值页面
	 * @return
	 */
	@RequestMapping("/assaySettingPage")
	public String assaySettingPage() {
		return "/collectionConfig/effective";
	}
	/**
	 * 新增效价值设置
	 * @return
	 */
	@RequestMapping("/assaySettingAdd")
	public String assaySettingAdd() {
		return "/collectionConfig/effective_add";
	}
	/**
	 * 跳转效价值修改页面
	 * @return
	 */
	@RequestMapping("/assaySettingUpdate")
	public String assaySettingUpdate(String id,Model model) {
		ImmuneAssaySetting immune = immuneAssaySettingService.selectById(id);
		model.addAttribute("immune", immune);
		return "/collectionConfig/effective_update";
	}
	/**
	 * 效价值列表
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/AssaySettingList", method= RequestMethod.POST)
	@InvokeLog(name = "AssaySettingList", description = "效价值列表")
	@ResponseBody
	public DataRow AssaySettingList(Integer page, Integer limit,@RequestParam Map<String,String> map) {
		Page<DataRow> paging = null;
		try {
			paging = new Page<DataRow>(page, limit);
			immuneAssaySettingService.AssaySettingList(map,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneAssaySettingController>>>>>>>>>>>>>AssaySettingList",e);
		}
		return messageMap;
	}
	/**
	 * 新增效价值设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/addAssaySetting", method= RequestMethod.POST)
	@InvokeLog(name = "addAssaySetting", description = "新增效价值设置")
	@ResponseBody
	public DataRow addAssaySetting(ImmuneAssaySetting immune) {
		try {
			immuneAssaySettingService.addAssaySetting(immune,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssaySettingController>>>>>>>>>>>>>addAssaySetting",e);
		}
		return messageMap;
	}
	/**
	 * 修改效价值设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/updateAssaySetting", method= RequestMethod.POST)
	@InvokeLog(name = "updateAssaySetting", description = "修改效价值设置")
	@ResponseBody
	public DataRow updateAssaySetting(ImmuneAssaySetting immune) {
		try {
			immuneAssaySettingService.updateAssaySetting(immune,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssaySettingController>>>>>>>>>>>>>updateAssaySetting",e);
		}
		return messageMap;
	}
	/**
	 * 删除效价值设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteAssaySetting", method= RequestMethod.POST)
	@InvokeLog(name = "deleteAssaySetting", description = "删除效价值设置")
	@ResponseBody
	public DataRow deleteAssaySetting(Long ids) {
		try {
			immuneAssaySettingService.deleteAssaySetting(ids,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssaySettingController>>>>>>>>>>>>>deleteAssaySetting",e);
		}
		return messageMap;
	}
	
}

