package com.fadl.immuneAssay.controller;


import java.util.Date;
import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.immuneAssay.entity.OrdinaryStrengthenInjection;
import com.fadl.immuneAssay.service.OrdinaryStrengthenInjectionService;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 注射管理-普免加强针注射 前端控制器
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Controller
@RequestMapping("/ordinaryStrengthenInjection")
public class OrdinaryStrengthenInjectionController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(OrdinaryStrengthenInjectionController.class);
	
	@Autowired
	private OrdinaryStrengthenInjectionService ordinaryStrengthenInjectionService;
	@Autowired
	LogService logService;
	/**
	 * 跳转普免加强针页面
	 * @return
	 */
	@RequestMapping("/toOrdinaryStrengthenShot")
	@RequiresPermissions("ordinaryStrengthenIn:view")
	public String toOrdinaryStrengthen() {
		return "/immune/injectionmanagement/ordinary_strengthen_shot";
	}
	
	/**
	 * 普免加强针 未注射人员（分页）
	 * @param limit
	 * @param page
	 * @param updateDate
	 * @return
	 */
	@RequestMapping("/notShotOrdStrengthen")
	@InvokeLog(name = "notShotOrdStrengthen", description = "普免加强针 未注射人员（分页）")
	@RequiresPermissions("ordinaryStrengthenIn:listW")
	@ResponseBody
	public DataRow notShotOrdStrengthen(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryStrengthenInjectionService.notShotOrdStrengthen(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>notShotOrdBaseList>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免加强针 已注射人员（分页）
	 * @param limit
	 * @param page
	 * @param updateDate
	 * @return
	 */
	@RequestMapping("/haveShotOrdStrengthen")
	@InvokeLog(name = "haveShotOrdStrengthen", description = "普免加强针 已注射人员（分页）")
	@RequiresPermissions("ordinaryStrengthenIn:listY")
	@ResponseBody
	public DataRow haveShotOrdStrengthen(Integer limit,Integer page,String updateDate) {
		try {
			if(StringHelper.isEmpty(updateDate)) {
				updateDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			ordinaryStrengthenInjectionService.haveShotOrdStrengthen(updateDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>haveShotOrdStrengthen>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免加强针  头部内容
	 * @param map
	 * @return
	 */
	@RequestMapping("/ordinaryStrengthenHead")
	@InvokeLog(name = "ordinaryStrengthenHead", description = "普免加强针  头部内容")
	@ResponseBody
	public DataRow ordinaryStrengthenHead (@RequestParam HashMap<String, Object> map) {
		try {
			DataRow res = ordinaryStrengthenInjectionService.ordinaryStrengthenHead(map);
			messageMap.initSuccess(res);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordinaryStrengthenHead>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 获取加强免疫所有类别
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseImmuneStreng")
	@InvokeLog(name = "getBaseImmuneStreng", description = "获取加强免疫所有类别")
	@ResponseBody
	public DataRow getBaseImmuneStreng (String id) {
		try {
			DataRow data =  ordinaryStrengthenInjectionService.getBaseImmuneStreng(id);
			messageMap.initSuccess(data);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>getBaseImmuneStreng>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 获取疫苗信息(啊健)
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryVaccineInfoStreng")
	@InvokeLog(name = "queryVaccineInfoStreng", description = "获取疫苗信息(啊健)")
	@ResponseBody
	public DataRow queryVaccineInfo (String immune) {
		try {
			DataRow data =  ordinaryStrengthenInjectionService.queryVaccineInfoStreng(immune);
			messageMap.initSuccess(data);
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>queryVaccineInfo>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 普免加强针  注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ordStrengthenToShot")
	@InvokeLog(name = "ordStrengthenToShot", description = "普免加强针  注射")
	@RequiresPermissions("ordinaryStrengthenIn:injection")
	@ResponseBody
	public DataRow ordStrengthenToShot(OrdinaryStrengthenInjection entity) {
		try {
			entity.setIsShot(1);
			boolean flag = ordinaryStrengthenInjectionService.updateById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.ordStrengthenToShot, IConstants.DESC.replace("{0}", "已取消普免加强针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordStrengthenToShot>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 普免加强针  取消注射
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ordStrengthenToNotShot")
	@InvokeLog(name = "ordStrengthenToNotShot", description = "普免加强针  取消注射")
	@RequiresPermissions("ordinaryStrengthenIn:cancel")
	@ResponseBody
	public DataRow ordStrengthenToNotShot(OrdinaryStrengthenInjection entity) {
		try {
			entity.setIsShot(0);
			boolean flag = ordinaryStrengthenInjectionService.updateAllColumnById(entity);
			if(flag) {
				//最后插入日志
				logService.insertLog(IConstants.ordStrengthenToNotShot, IConstants.DESC.replace("{0}", "已取消普免加强针注射,浆员卡号"+entity.getProviderNo()),entity.getProviderNo());
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("OrdinaryInjectionController>>>>>>>>>>ordStrengthenToNotShot>>>>>>",e);
		}
		return messageMap;
	}
	
}

