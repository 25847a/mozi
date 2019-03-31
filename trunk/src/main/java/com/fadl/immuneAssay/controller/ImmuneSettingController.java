package com.fadl.immuneAssay.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.entity.ImmuneSetting;
import com.fadl.immuneAssay.service.ImmuneSettingService;

/**
 * <p>
 * 免疫类别设置 前端控制器
 * </p>
 *
 * @author hkk
 * @since 2018-07-12
 */
@Controller
@RequestMapping("/immuneSetting")
public class ImmuneSettingController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ImmuneSettingController.class);
	
	@Autowired
	public ImmuneSettingService smmuneSettingService;
	@Autowired
	private ConfigService configService;
	/**
	 * 跳转免疫管理页面
	 * @return
	 */
	@RequestMapping("/immunePageManage")
	public String immunePageManage() {
		return "/immune/immunePage";
	}
	 /**
     * 跳转免疫类别设置页面
     */
    @RequestMapping("/immunePage")
    @RequiresPermissions("immuneSet:view")
    public String immunePage() {
        return "/immune/config/immnre_config";
    }
	 /**
     * 跳转免疫类别设置添加页面
     */
    @RequestMapping("/immuneAddPage")
    public String immuneAddPage() {
        return "/immune/config/immune_config_add";
    }
    /**
	 * 跳转免疫类别设置修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/immuneUpdate")
    public String immuneUpdate(String id, Model model) {
    	try {
    		ImmuneSetting im = new ImmuneSetting();
    		im = smmuneSettingService.selectById(id);
    		model.addAttribute("im",im);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>immuneUpdate>>>>>",e);
		}
        return "/immune/config/immune_config_update";
    }
	/**
	 * 查询免疫设置
	 * @return
	 */
	@RequestMapping("/queryImmuneSettingList")
	@InvokeLog(name = "queryImmuneSettingList", description = "查询免疫设置")
	@ResponseBody
	public DataRow queryImmuneSettingList(String type){
		EntityWrapper<ImmuneSetting> ew = new EntityWrapper<ImmuneSetting>();
		try {
			if(null!=type) {
				if(type.equals("0")) {
					ew.eq("type", 0);
				}else if(type.equals("1")){
					ew.eq("type", 1);
				}else if(type.equals("2")) {
					ew.eq("type", 2);
				}
			}
			List<ImmuneSetting> list = smmuneSettingService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>queryImmuneSettingList",e);
		}
		return messageMap;
	}
	/**
	 * 查询免疫设置(获取来世版本的)
	 * @return
	 */
	@RequestMapping("/queryAmmuneSetting")
	@InvokeLog(name = "queryAmmuneSetting", description = "查询免疫设置")
	@ResponseBody
	public DataRow queryAmmuneSetting(Integer type){
		try {
			List<DataRow> list = smmuneSettingService.queryAmmuneSetting(type);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>queryAmmuneSetting",e);
		}
		return messageMap;
	}
	/**
	 * 免疫类别设置列表
	 * @param im
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/immuneSettingList", method= RequestMethod.POST)
	@InvokeLog(name = "immuneSettingList", description = "免疫类别设置列表")
	@RequiresPermissions("immuneSet:list")
	@ResponseBody
	public DataRow immuneSettingList(ImmuneSetting im, Integer page, Integer limit) {
		Page<DataRow> paging = null;
		try {
			Config config = configService.queryConfig("immune_type","1");
			if(config.getIsDisable()==1) {
				im.setType(1);
			}
			paging = new Page<DataRow>(page, limit);
			smmuneSettingService.immuneSettingList(im,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>immuneSettingList",e);
		}
		return messageMap;
	}
	/**
	 * 新增免疫类别设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/insertImmuneSetting", method= RequestMethod.POST)
	@InvokeLog(name = "insertImmuneSetting", description = "新增免疫类别设置")
	@RequiresPermissions("immuneSet:add")
	@ResponseBody
	public DataRow insertImmuneSetting(ImmuneSetting im) {
		try {
			smmuneSettingService.insertImmuneSetting(im,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>insertimmuneSetting",e);
		}
		return messageMap;
	}

	/**
	 * 修改免疫类别设置
	 * @param im
	 * @return
	 */
	@RequestMapping(value = "/updateImmuneSetting", method= RequestMethod.POST)
	@InvokeLog(name = "updateImmuneSetting", description = "修改免疫类别设置")
	@RequiresPermissions("immuneSet:update")
	@ResponseBody
	public DataRow updateImmuneSetting(ImmuneSetting im) {
		try {
			smmuneSettingService.updateImmuneSetting(im,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>updateImmuneSetting",e);
		}
		return messageMap;
	}
	/**
	 * 删除免疫类别设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteImmuneSetting", method= RequestMethod.POST)
	@InvokeLog(name = "deleteImmuneSetting", description = "删除免疫类别设置")
	@RequiresPermissions("immuneSet:delete")
	@ResponseBody
	public DataRow deleteImmuneSetting(Long ids) {
		try {
			smmuneSettingService.deleteImmuneSetting(ids,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>deleteImmuneSetting",e);
		}
		return messageMap;
	}
	/**
	 * 查询免疫设置信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryImmuneSettingIn",method= RequestMethod.POST)
	@InvokeLog(name = "queryImmuneSettingIn", description = "查询免疫设置信息")
	@ResponseBody
	public DataRow queryImmuneSettingIn(Long id) {
		try {
			smmuneSettingService.queryImmuneSettingIn(id,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryImmuneSettingIn",e);
		}
		return messageMap;
		
	}
	/**
	 * 获取所有免疫类别设置类型
	 * @return
	 */
	@RequestMapping("/getAmmuneSetting")
	@InvokeLog(name = "getAmmuneSetting", description = "获取所有免疫类别设置类型")
	@ResponseBody
	public DataRow getAmmuneSetting() {
		try {
			List<ImmuneSetting> result = smmuneSettingService.getAmmuneSetting();
			messageMap.initSuccess(result);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryImmuneSettingIn",e);
		}
		return messageMap;
	}
	/**
	 * 获取免疫类型
	 * @return
	 */
	@RequestMapping("/queryImmunType")
	@InvokeLog(name = "queryImmunType", description = "获取免疫类型")
	@ResponseBody
	public DataRow queryImmunType() {
		try {
			EntityWrapper<Config> ew =new EntityWrapper<Config>();
			ew.eq("lable", "immune_type");
			ew.eq("isDisable", 0);
			List<Config> result = configService.selectList(ew);
			messageMap.initSuccess(result);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryImmunType",e);
		}
		return messageMap;
	}
	/**
	 * 根据普免开关来获取免疫类型
	 * @return
	 */
	@RequestMapping("/queryImmunTypeOne")
	@ResponseBody
	public DataRow queryImmunTypeOne() {
		try {
			Config config = configService.queryConfig("immune_type","1");
			if(null!=config) {
				messageMap.initSuccess(config);
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>queryImmunTypeOne",e);
		}
		return messageMap;
	}
}

