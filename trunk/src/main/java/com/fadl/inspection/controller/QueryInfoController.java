package com.fadl.inspection.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.service.NewCardService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 检验-化验记录高级查询 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-21
 */
@Controller
@RequestMapping("/queryInfo")
public class QueryInfoController extends AbstractController{
	@Autowired
	private NewCardService ncService;
	@Autowired
	public PlasmaStockService plasmaStockService;
	private static Logger logger = LoggerFactory.getLogger(QueryInfoController.class);
	@Autowired
	private TestConfigInfoService tciService;
	/**
	 * 跳转化验记录高级查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	public String initPage() {
		return "/business/inspection/in_ladoratory";
	}
	/**
	 * 跳转化验结果打印页面
	 * 
	 * @return
	 */
	@RequestMapping("/initReportPage")
	@RequiresPermissions("newcard:view")
	public String initReportPage(@RequestParam HashMap<String, Object> map, Model model) {
		try {
			// 不分页根据结果查询
			Page<DataRow> dr = new Page<DataRow>(1,9999999);
			
			String checkDate = DateUtil.getSystemDate(null);
			if(null!=map.get("sendDate")) {
				checkDate  = String.valueOf(map.get("sendDate"));
			}
			map.put("startTime",checkDate+" 00:00:00");
			map.put("endTime",checkDate+" 23:59:59");
			List<DataRow>  proList = ncService.queryListsByCondition(dr, map).getRecords();
			if(proList.size()>0) {
				DataRow lastItem = proList.get(proList.size()-1);
				addAttribute(model, lastItem, checkDate);
				
				
			}
			int oked = 0, un = 0;
			for(DataRow drItem : proList) {
				if(drItem.getString("result").equals("0")) {
					oked++;
				}else {
					un++;
				}
			}
			
			model.addAttribute("oked",oked);
			model.addAttribute("checkDate",checkDate);
			model.addAttribute("un",un);
			model.addAttribute("listSize",proList.size());
			model.addAttribute("proList",proList);
		} catch (Exception e) {
			logger.error("QueryInfoController>>>>>>>>>>>>>>>queryListsByCondition>>>>>>>>>",e);
		}
		return "/business/inspection/a_report";
	}
	
	/**
	 * 跳转每日化验结果打印页面
	 * 
	 * @return
	 */
	@RequestMapping("/initeveryDayReportPage")
	@RequiresPermissions("newcard:view")
	public String initeveryDayReportPage(Model model, String sendDate) {
		model.addAttribute("sendDate",sendDate);
		// 获取当天的不合格化验记录
		try {
			List<DataRow> undrs = ncService.queryListsByUnqualified(sendDate);
			// 获取当天的合格化验记录
			List<DataRow> drs = ncService.queryListsByQualified(sendDate);
			Wrapper<NewCard> ew = new EntityWrapper<NewCard>();
			ew.between("updateDate", sendDate+" 00:00:00" , sendDate+" 23:59:59");
			// 获得化验总数等信息 包含 不合格浆员列表 
			model.addAttribute("checkedSize", ncService.selectCount(ew));
			model.addAttribute("unqualified", undrs.size());
			model.addAttribute("unqualifiedLst", undrs);
			model.addAttribute("qualified", drs.size());
			if(drs !=null && drs.size()>0) {
				DataRow lastItem = drs.get(drs.size()-1); 
				// 根据最后一个浆员的化验信息获得化验耗材信息
				addAttribute(model, lastItem, sendDate);
			}
		} catch (Exception e) {
			logger.error("QueryInfoController>>>>>>>>>>>>>>>queryListsByCondition>>>>>>>>>",e);
		}
		return "/business/inspection/everyday_check_report";
	}
	
	/**
	 * 根据条件查询化验记录 
	 * 目前支持小样号段,创建时间段查询
	 * @param newCard
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryListsByCondition", method=RequestMethod.POST)
	@InvokeLog(name = "queryListsByCondition", description = "根据条件查询化验记录 ")
	@RequiresPermissions("newcard:view")
	public DataRow queryListsByCondition(@RequestParam HashMap<String, Object> map, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page,limit);
			if(map.get("startTime")==null) {
				String dateStr = DateUtil.getSystemDate(new Date());
				map.put("startTime", dateStr);
				map.put("endTime", dateStr);
			}
			messageMap.initPage(ncService.queryListsByCondition(dr, map));
		} catch (Exception e) {
			logger.error("QueryInfoController>>>>>>>>>>>>>>>queryListsByCondition>>>>>>>>>",e);
		}
		
		return messageMap;
	}
	
	private void addAttribute(Model model, DataRow lastItem, String checkDate) throws Exception{
		HashMap<String,String> map =  new HashMap<String,String>();
		map.put("allId", lastItem.get("allId").toString());
		List<DataRow> list = plasmaStockService.queryBoxPlasmaInfoReagents(map);
		for(DataRow drItem : list) {
			if("ALT".equals(drItem.get("lable"))) {
				model.addAttribute("altItem",drItem);
			}else if("HIV".equals(drItem.get("lable"))) {
				model.addAttribute("hivItem",drItem);
			}else if("HCV".equals(drItem.get("lable"))) {
				model.addAttribute("hcvItem",drItem);
			}else if("HBsAG".equals(drItem.get("lable"))) {
				model.addAttribute("tp2Item",drItem);
			}else if("syphilis".equals(drItem.get("lable"))) {
				model.addAttribute("spItem",drItem);
			}
		}
		try {
			HashMap<String, String> map1 = new HashMap<>();
			map1.put("lable", "全血比重");
			map1.put("endTime", checkDate);
			List<DataRow> lst= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst.size()!=0) {
				model.addAttribute("ptItem", lst.get(0));
			}
			map1.put("lable", "蛋白量");
			map1.put("endTime", checkDate);
			List<DataRow> lst2= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst2.size()!=0) {
				model.addAttribute("wbItem", lst2.get(0));
			}
			map1.put("lable", "血型");
			map1.put("endTime", checkDate);
			List<DataRow> lst1= tciService.querySuppliesInfoByProjectNameLable(map1);
			if( lst1.size()!=0) {
				model.addAttribute("bloodItem", lst1.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
