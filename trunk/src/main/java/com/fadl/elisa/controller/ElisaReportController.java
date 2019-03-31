package com.fadl.elisa.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.elisa.entity.ElisaGrayAreaSettings;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.ElisaValues;
import com.fadl.elisa.service.ElisaIiqcService;
import com.fadl.elisa.service.ElisaInfoService;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.supplies.entity.Info;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.entity.Stock;
import com.fadl.supplies.entity.Supply;
import com.fadl.supplies.service.InfoService;
import com.fadl.supplies.service.OutstockService;
import com.fadl.supplies.service.StockService;
import com.fadl.supplies.service.SupplyService;

/**
 * <p>
 * 酶标板报表头设置 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Controller
@RequestMapping("/elisaReport")
public class ElisaReportController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ElisaReportController.class);
	
	@Autowired
	private ElisaInfoService eiService;
	@Autowired
	private StockService sService;
	@Autowired
	private SupplyService syService;
	@Autowired
	private InfoService iService;
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	private ElisaIiqcService eiiService;
	@Autowired
	private OutstockService osService;
	
 
	/**
	 * 跳转酶标板打印页面 根据type的类型跳转不一样的风格页面
	 * @param model
	 * @param id
	 * @param eadbutS
	 * @param showSampleNo
	 * @param type
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/printElisa")
	@InvokeLog(name = "printElisa", description = "跳转酶标板打印页面 根据type的类型跳转不一样的风格页面")
	public String printElisa(Model model, Long id, String eadbutS, String showSampleNo, String type) {
		ElisaInfo ei  = eiService.selectById(id);
		Outstock stock = osService.selectById(ei.getReagentId());
		ElisaTemplate et = ei.getElisaTemplate();
		Info info = iService.selectById(stock.getSuppliesId());
		Supply supply = syService.selectById(info.getSupplyId());
		TestConfigInfo tci = tciService.selectById(ei.getTciId());
		ElisaGrayAreaSettings areaSettings = et.getElisaGrayAreaSettings();
		
		Double cutOffValue = ei.getCutOffValue().doubleValue();
		Double grayMin =Double.NaN;
		Double grayMax =Double.NaN;
		boolean isisMaxWithCutOff = areaSettings.getIsMaxWithCutOff()==1;
		if(areaSettings.getOptionsValue() == 0) { // 常规
			grayMin = cutOffValue - areaSettings.getConventionalValue().doubleValue();
			grayMax = isisMaxWithCutOff?cutOffValue:cutOffValue+areaSettings.getConventionalValue().doubleValue();
		}else if(areaSettings.getOptionsValue() == 1) { // 比例
			grayMin = cutOffValue *(1- areaSettings.getProportionValue().doubleValue());
			grayMax = isisMaxWithCutOff?cutOffValue:cutOffValue*(1+areaSettings.getProportionValue().doubleValue());
		}else { //定值
			grayMin = areaSettings.getMinValue().doubleValue();
			grayMax = areaSettings.getMaxValue().doubleValue();
		}
		BigDecimal bgGrayMin = new BigDecimal(grayMin).setScale(3, RoundingMode.UP);
		BigDecimal bgGrayMax = new BigDecimal(grayMax).setScale(3, RoundingMode.UP);
		Stock stock2 = sService.selectById(ei.getQcId());
		Info info2 = iService.selectById(stock2.getSuppliesId());
		Supply supply2 = syService.selectById(info2.getSupplyId());
		model.addAttribute("stock", stock);
		model.addAttribute("qcStock", stock2);
		model.addAttribute("eadbutS", eadbutS);
		model.addAttribute("showSampleNo", showSampleNo);
		model.addAttribute("supply", supply);
		model.addAttribute("qcSupply", supply2);
		model.addAttribute("grayMin", bgGrayMin.doubleValue());
		model.addAttribute("grayMax", bgGrayMax.doubleValue());
		model.addAttribute("tci", tci);
		model.addAttribute("elisaInfo", ei);
		model.addAttribute("printTime", DateUtil.sf.format(new Date()));
		// ALT 走这里
		if(ei.getElisaTemplate().getProjectId()==1) {
			List<ElisaValues> etValues = ei.getElisaValues();
			double[] x = { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN };
             int j=0;
 			// 获取 STD值 计算
 			for(int i=0;i<etValues.size();i++) {
 				ElisaValues ev = etValues.get(i);
 				if(ev.getType()==0) {
 					x[j] = ev.getOdValue().doubleValue();
 					j++;
 				}
 			}
 			model.addAttribute("x1", x[0]);
 			model.addAttribute("x2", x[1]);
 			model.addAttribute("x3", x[2]);
 			model.addAttribute("x4", x[3]);
 			model.addAttribute("x5", x[4]);
			model.addAttribute("emm", et.getElisaMeasurementMethod());
			if("2".equals(type)) {
				return "/business/elisa/bancdayinReitman2";
			}else {
				return "/business/elisa/bancdayinReitman";
			}
			
		}else {
			if("2".equals(type)) {
				return "/business/elisa/bancdayin2";
			}else {
				return "/business/elisa/bancdayin";
			}
		}
	}
	
	/**
	 * 根据id查找Elisa Info的信息 用于即控报表打印
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectQCReportForTable")
	@InvokeLog(name = "selectQCReportForTable", description = "根据id查找Elisa Info的信息 用于即控报表打印")
	@RequiresPermissions("einfo:view")
	public DataRow selectQCReportForTable( Long tciid, Long qcId, Integer page,Integer limit, String type) {
		int current  = page; 
		if(page ==null ) { //  初始化当前页码
			page = 1;
		}
		int stratRow = 0 , endRow =20;
		if(page >1) {
			page = page -1;
			stratRow = endRow+(page-1)*29;
			endRow = 29;
		}
		Page<DataRow> dr = new Page<DataRow>(page,endRow);
		try {
			if(tciid!= null && qcId != null)
			dr = eiiService.findByTciidAndPageNo(dr, tciid, qcId,stratRow ,endRow );
			List<DataRow> ls =  dr.getRecords();
			if(ls.size()>0) {
				messageMap.put("firstItem",ls.get(0));
				messageMap.put("lastItem",ls.get(ls.size()-1));
			}else {
				messageMap.put("firstItem",null);
				messageMap.put("lastItem",null);
			}
			messageMap.initSuccess(ls);
		} catch (Exception e) {
			logger.error("ElisaReportController>>>>>>>>>selectQCReportForTable>>>>>", e);
		}
		if(stratRow == 0 ) {
			current = 1;
		}
		if(null!=tciid )
			messageMap.put("tci",  tciService.selectById(tciid));
		messageMap.put("isFirst", current<=1);
		messageMap.put("hasPrev", current>1);
		messageMap.put("hasNext", (dr.isAsc()?dr.getPages()+1:dr.getPages())>current);
		messageMap.put("isLast", current>=(dr.isAsc()?dr.getPages()+1:dr.getPages()));
		messageMap.put("pages", dr.isAsc()?dr.getPages()+1:dr.getPages());
		messageMap.put("pageIndex", current);
		return messageMap;
	}
	
	/**
	 * 跳转质控页面 
	 * @param model
	 * @param tciid
	 * @param qcId
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/printQCReportForTable")
	@InvokeLog(name = "printQCReportForTable", description = "跳转质控页面 ")
	public String printQCReportForTable(Model model,Long tciid, Long qcId, Integer page,Integer limit, String type) {
		model.addAttribute("tciid", tciid);
		
		model.addAttribute("qcId", qcId);
		model.addAttribute("page", page);
		model.addAttribute("limit", limit);
		model.addAttribute("type", type);
		if("2".equals(type)) {
			return "/business/elisa/zhikdayin2";
		}else {
			return "/business/elisa/zhikdayin";
		}
	}
	
	/**
	 * 跳转质控页面
	 * 
	 * @return
	 */
	@RequiresPermissions("einfo:view")
	@RequestMapping("/printQCReportForImg")
	public String printQCReportForImg(Model model,Long tciid, Long qcId, Integer page,Integer limit,String type) {
		model.addAttribute("tciid", tciid);
		model.addAttribute("tci",  tciService.selectById(tciid));
		model.addAttribute("qcId", qcId);
		model.addAttribute("page", page);
		model.addAttribute("limit", limit);
		model.addAttribute("type", type);
		if("2".equals(type)) {
			return "/business/elisa/azhiktuTwo";
		}else {
			return "/business/elisa/azhiktu";
		}
		
	}
	
	
	
	
	
	
	
}

