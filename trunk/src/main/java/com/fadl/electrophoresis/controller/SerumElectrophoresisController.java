package com.fadl.electrophoresis.controller;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.CommonService;
import com.fadl.common.service.ConfigService;
import com.fadl.electrophoresis.entity.SerumElectrophoresis;
import com.fadl.electrophoresis.service.SerumElectrophoresisService;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.registries.service.ProviderRegistriesService;

/**
 * <p>
 *  血清电泳 前端控制器
 * </p>
 *
 * @author hu
 * @since 2018-06-06
 */
@Controller
@RequestMapping("/serumElectrophoresis")
public class SerumElectrophoresisController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(SerumElectrophoresisController.class);
	
	@Autowired
	public SerumElectrophoresisService serumElectrophoresisService;
	@Autowired
	public CommonService commonService;
	@Autowired
	public ConfigService configService;
	@Autowired
	private ProviderRegistriesService prService;
	/**
	 * 跳转血清电泳页面
	 * @return
	 */
	@RequiresPermissions("serum:view")
	@RequestMapping("/electrophoresis")
	public String electrophoresis(){
		return "/electrophoresis/electrophoresis";
	}
	
	/**
	 * 跳转血清电泳打印页面
	 * @return
	 */
	@RequiresPermissions("serum:view")
	@RequestMapping("/initReport")
	public String initReport(Model model, String sendDate){
		model.addAttribute("sendDate",sendDate);
		SerumElectrophoresis se  = new SerumElectrophoresis();
		se.setUpdateDate(sendDate);
		model.addAttribute("list", serumElectrophoresisService.selectByUpdateDate(se));
		return "/electrophoresis/electrophoresis_report";
	}
	
	/**
	 * 跳转添加血清电泳页面
	 * @return
	 */
	@RequiresPermissions("serum:add")
	@RequestMapping("/addElectrophoresis")
	public String addElectrophoresis(Model model, @RequestParam String providerNo){
		model.addAttribute("providerNo", providerNo);
		return "/electrophoresis/add_electrophoresis";
	}
	
	/**
	 * 跳转修改血清电泳页面
	 * @return
	 */
	@RequiresPermissions("serum:edit")
	@RequestMapping("/updateElectrophoresis")
	public String updateElectrophoresis(@RequestParam Long id,Model model){
		SerumElectrophoresis serum = serumElectrophoresisService.selectById(id);
		model.addAttribute("serum",serum);
		return "/electrophoresis/update_electrophoresis";
	} 
	
	/**
	 * 获取血清电泳列表
	 * @return
	 */
	@RequestMapping(value="/querySerumElectrophoresisList",method = RequestMethod.POST)
	@InvokeLog(name = "querySerumElectrophoresisList", description = "获取血清电泳列表")
	@ResponseBody
	@RequiresPermissions("serum:view")
	public DataRow querySerumElectrophoresisList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p = serumElectrophoresisService.querySerumElectrophoresisList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>querySerumElectrophoresisList",e);
		}
		return messageMap;
	}
	
	/**
	 *  添加血清蛋白电泳
	 * @return
	 */
	@RequestMapping(value="/addSerumElectrophoresis",method = RequestMethod.POST)
	@InvokeLog(name = "addSerumElectrophoresis", description = "添加血清蛋白电泳")
	@ResponseBody
	@RequiresPermissions("serum:add")
	public DataRow addSerumElectrophoresis(SerumElectrophoresis serumElectrophoresis){
		try {
			Wrapper<ProviderRegistries> ew = new EntityWrapper<ProviderRegistries>();
			ew.eq("allId", serumElectrophoresis.getAllId());
			ProviderRegistries prEntity = prService.selectOne(ew);
			if(prEntity == null) {
				messageMap.initFial("浆员信息不存在");
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("providerNo", prEntity.getProviderNo());
			DataRow row = commonService.queryWhereBaseInfoOrDetailObj(map);
			
			if (null!=row) {
				Wrapper<SerumElectrophoresis> ewse = new EntityWrapper<SerumElectrophoresis>();
				ewse.eq("allId", serumElectrophoresis.getAllId());
				SerumElectrophoresis se = serumElectrophoresisService.selectOne(ewse);
				boolean res = false;
				if(se!=null) {
					se.setAtlas(serumElectrophoresis.getAtlas());
					se.setAlbumin(serumElectrophoresis.getAlbumin());
					res = serumElectrophoresisService.updateById(se);
				}else {
					serumElectrophoresis.setProviderNo(prEntity.getProviderNo());
					res = serumElectrophoresisService.insertEntity(serumElectrophoresis);
				}
				if (res) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}else{
				messageMap.initFial("浆员信息不存在");
			}
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>addSerumElectrophoresis",e);
		}
		return messageMap;
	}
	
	/**
	 *  修改血清蛋白电泳
	 * @return
	 */
	@RequestMapping(value="/updateSerumElectrophoresis",method = RequestMethod.POST)
	@InvokeLog(name = "updateSerumElectrophoresis", description = "修改血清蛋白电泳")
	@ResponseBody
	@RequiresPermissions("serum:edit")
	public DataRow updateSerumElectrophoresis(SerumElectrophoresis serumElectrophoresis){
		try {
			boolean res = serumElectrophoresisService.updateById(serumElectrophoresis);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>updateSerumElectrophoresis",e);
		}
		return messageMap;
	}
	
	/**
	 *  删除血清蛋白电泳
	 * @return
	 */
	@RequestMapping(value="/deleteSerumElectrophoresis",method = RequestMethod.POST)
	@InvokeLog(name = "deleteSerumElectrophoresis", description = "删除血清蛋白电泳")
	@ResponseBody
	@RequiresPermissions("serum:del")
	public DataRow deleteSerumElectrophoresis(@RequestParam Long id){
		try { 
			boolean res = serumElectrophoresisService.deleteById(id);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>deleteSerumElectrophoresis",e);
		}
		return messageMap;
	}
	
	/**
	 *  根据浆员卡号查询该浆员是否要做血清蛋白电泳检验 目前是超过1年就要做检验,新浆员也要化验
	 * @return
	 */
	@RequestMapping(value="/queryInfoByProviderNo",method = RequestMethod.POST)
	@InvokeLog(name = "queryInfoByProviderNo", description = " 根据浆员卡号查询该浆员是否要做血清蛋白电泳检验")
	@ResponseBody
	public DataRow queryInfoByProviderNo(@RequestParam String providerNo){
		try { 
			serumElectrophoresisService.queryInfoByProviderNo(providerNo, messageMap);
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>queryInfoByProviderNo",e);
		}
		return messageMap;
	}
	
	/**
	 *  根据浆员卡号查询该浆员是否要做血清蛋白电泳检验
	 *   目前是超过1年就要做检验,新浆员也要化验,
	 *   该提示是超过14天则要提示, 用于检验结果发布的地方,先按一年是否有记录来判定要不要提示, 然后再按14天内是否发布结果.
	 * @return
	 */
	@RequestMapping(value="/queryInfoByProviderNoBefore14Days",method = RequestMethod.POST)
	@InvokeLog(name = "queryInfoByProviderNoBefore14Days", description = " 根据浆员卡号查询该浆员是否要做血清蛋白电泳检验 目前是超过1年就要做检验,新浆员也要化验,该提示是超过14天则要提示")
	@ResponseBody
	public DataRow queryInfoByProviderNoBefore14Days(@RequestParam String providerNo){
		try { 
			Wrapper<SerumElectrophoresis> ew =  new EntityWrapper<SerumElectrophoresis>();
			Calendar ca = Calendar.getInstance();
			Config config = configService.getConfig("electrophoresis_config", "after_days");
			int days = 14;
			if(StringUtils.isNotBlank(config.getValue())) {
				days = Integer.parseInt(config.getValue());
			}
			ca.add(Calendar.DATE, -days);
			ew.eq("providerNo", providerNo).gt("updateDate", ca.getTime());
			ew.orderBy("createDate", false);
			
			List<SerumElectrophoresis> lst = serumElectrophoresisService.selectList(ew);
			// 一年内没有数据则直接提示要做蛋白电泳的检测
			if(lst.size()>0) {
				// 集合有记录要先判定结果是否为空,并且创建时间要大于14天
				SerumElectrophoresis s = lst.get(0);
				// 结果为空
				if(s.getAlbumin() ==null || s.getAtlas()== null) {
					Calendar ca1 = Calendar.getInstance();
					ca1.add(Calendar.DATE, -14);
					// 在没有结果的情况下, 超过14天才提示
					if( DateUtil.formatDate(s.getCreateDate(), DateUtil.yyyy_MM_dd_EN).before(ca1.getTime())) {
						messageMap.initSuccess(true);
					}else {
						messageMap.initSuccess(false);
					}
				}else {
					messageMap.initSuccess(false);
				}
			}else {
				messageMap.initSuccess(true);
			}
		} catch (Exception e) {
			logger.error("SerumElectrophoresisController>>>>>>>>>queryInfoByProviderNo",e);
		}
		return messageMap;
	}
}