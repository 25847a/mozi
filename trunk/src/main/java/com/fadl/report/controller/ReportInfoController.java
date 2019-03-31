package com.fadl.report.controller;


import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.service.NewCardService;
import com.fadl.report.entity.ReportInfo;
import com.fadl.report.service.ReportInfoService;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 报表页眉页脚 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/reportInfo")
public class ReportInfoController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(ReportInfoController.class);
	@Autowired
	private NewCardService ncService;
	@Autowired
	private ReportInfoService riService;
	@Autowired
	public PlasmaStockService plasmaStockService;

	
	/**
	 * 跳转到化验的报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:elisa:view")
	@RequestMapping("/initElisaPage")
	public String initElisaPage(Model model) {
		return getPageModel(model, "0");
	} 
	/**
	 * 跳转到标本采集的送样报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:specimen:view")
	@RequestMapping("/initSpecimenPage")
	public String initSpecimenPage(Model model) {
		return getPageModel(model, "1");
	} 
	/**
	 * 跳转到检验登记样本拒收报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:fixedRefuse:view")
	@RequestMapping("/initRefusePage")
	public String initRefusePage(Model model) {
		return getPageModel(model, "2");
	} 
	/**
	 * 跳转到蛋白电泳报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:serum:view")
	@RequestMapping("/initSerumPage")
	public String initSerumPage(Model model) {
		return getPageModel(model, "3");
	}
	/**
	 * 跳转到献血浆检验报告单报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:newcard:view")
	@RequestMapping("/initNewcardPage")
	public String initNewcardPage(@RequestParam HashMap<String, Object> map, Model model) {
		
		return getPageModel(model, "4");
	} 
	/**
	 * 跳转到每日检验结果汇总记录表报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:newcard:view")
	@RequestMapping("/initeveryDayReportPage")
	public String initeveryDayReportPage(Model model) {
		return getPageModel(model, "5");
	} 
	/**
	 * 跳转到血红蛋白含量报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:brpc:view")
	@RequestMapping("/initBRPCPage")
	public String initBRPCPage(Model model) {
		return getPageModel(model, "6");
	}
	/**
	 * 跳转到免疫注射记录表报表设置页面
	 * @return
	 */
	//@RequiresPermissions("report:immune:view")
	@RequestMapping("/initImmuneReportPage")
	public String initImmuneReportPage(Model model) {
		return getPageModel(model, "7");
	}
	/**
	 * 跳转到蛋白含量报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:pc:view")
	@RequestMapping("/initPCPage")
	public String initPCPage(Model model) {
		return getPageModel(model, "8");
	}
	/**
	 * 跳转到体检报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:check:view")
	@RequestMapping("/initCheckPage")
	public String initCheckPage(Model model) {
		return getPageModel(model, "9");
	}
	
	/**
	 * 跳转到 血浆装箱单  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:plasmaBox:view")
	@RequestMapping("/initPlasmaBoxPage")
	public String initPlasmaBoxPage(Model model) {
		return getPageModel(model, "10");
	}
	
	/**
	 * 跳转到 血浆留样  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:plasmaSimple:view")
	@RequestMapping("/initPlasmaSimplePage")
	public String initPlasmaSimplePage(Model model) {
		return getPageModel(model, "11");
	}
	
	/**
	 * 跳转到 献血浆者采浆记录表  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:plasmaCollection:view")
	@RequestMapping("/initPlasmaCollectionPage")
	public String initPlasmaCollectionPage(Model model) {
		return getPageModel(model, "12");
	}
	
	/**
	 * 跳转到 血浆检测装运表  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:plasmaCheck:view")
	@RequestMapping("/initPlasmaCheckPage")
	public String initPlasmaCheckPage(Model model) {
		return getPageModel(model, "13");
	}
	
	/**
	 * 跳转到 血浆装箱汇总表打印页眉页脚设置  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:plasmaSummary:view")
	@RequestMapping("/initPlasmaSummaryPage")
	public String initPlasmaSummaryPage(Model model) {
		return getPageModel(model, "14");
	}
	
	/**
	 * 跳转到 板次打印一  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:pp:view")
	@RequestMapping("/initPlatePrintOnePage")
	public String initPlatePrintOnePage(Model model) {
		return getPageModel(model, "15");
	}
	
	/**
	 * 跳转到 板次打印二  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:pp:view")
	@RequestMapping("/initPlatePrintTwoPage")
	public String initPlatePrintTwoPage(Model model) {
		return getPageModel(model, "16");
	}
	
	/**
	 * 跳转到 赖氏法  板次打印二  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:pp:view")
	@RequestMapping("/initReitmanPlatePrintOnePage")
	public String initReitmanPlatePrintOnePage(Model model) {
		return getPageModel(model, "17");
	}
	
	/**
	 * 跳转到 赖氏法 板次打印二  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:pp:view")
	@RequestMapping("/initReitmanPlatePrintTwoPage")
	public String initReitmanPlatePrintTwoPage(Model model) {
		return getPageModel(model, "18");
	}
	/**
	 * 跳转到 即刻打印一  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:qc:view")
	@RequestMapping("/initQCPageOne")
	public String initQCPageOne(Model model) {
		return getPageModel(model, "19");
	}
	/**
	 * 跳转到 即刻打印二  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:qc:view")
	@RequestMapping("/initQCPageTwo")
	public String initQCPageTwo(Model model) {
		return getPageModel(model, "20");
	}
	
	/**
	 * 跳转到 新浆员体格检查表设置(正面) 页面
	 * @return
	 */
	@RequiresPermissions("report:newPlasmaCheckZ:view")
	@RequestMapping("/initNewPlasmaCheckPageZ")
	public String initNewPlasmaCheckPageZ(Model model) {
		return getPageModel(model, "21");
	}
	
	/**
	 * 跳转到 新浆员体格检查表设置(饭面) 页面
	 * @return
	 */
	@RequiresPermissions("report:newPlasmaCheckF:view")
	@RequestMapping("/initNewPlasmaCheckPageF")
	public String initNewPlasmaCheckPageF(Model model) {
		return getPageModel(model, "22");
	}
	
	/**
	 * 跳转到 质控图打印  报表设置页面
	 * @return
	 */
	@RequiresPermissions("report:qc:view")
	@RequestMapping("/initQCImgPage")
	public String initQCImgPage(Model model) {
		return getPageModel(model, "23");
	}
	
	/**
	 * 根据modType跳转不一样的配置页面, 其它报表自行添加mapping入口,再调用本方法
	 * @param model
	 * @param modType 0:elisa,
	 * @return
	 */
	private String getPageModel(Model model, String modType) {
		Wrapper<ReportInfo> ew = new EntityWrapper<ReportInfo>();
		ew.eq("reportNO", modType);
		ReportInfo info = riService.selectOne(ew);
		if(info == null ) { // 如果没有该设置则新增一条记录
			info = new ReportInfo();
			info.setReportNO(modType);
			riService.insert(info); // 新增一条记录, 不再提示要添加,要提示则将false改成true
			model.addAttribute("notInfo", false);
		}else {
			model.addAttribute("notInfo", false);
		}
		model.addAttribute("info", info);
		return "/business/busblood/a_a";
	}
	
	
	/**
	 * 保存报表设置
	 * @return
	 */
//	@RequiresPermissions("report:edit")
	@RequestMapping("/saveInfo")
	@ResponseBody
	@InvokeLog(name = "saveInfo", description = "保存报表设置")
	public DataRow saveInfo(ReportInfo info) {
		if(null == info.getId()) {
			messageMap.initFial("没有该报表的页眉页脚信息");
		}
		ReportInfo oldInfo = riService.selectById(info.getId());
		// 仅更新6个文本框内容
		oldInfo.setHeadCenter(info.getHeadCenter());
		oldInfo.setHeadLeft(info.getHeadLeft());
		oldInfo.setHeadRight(info.getHeadRight());
		oldInfo.setFootCenter(info.getFootCenter());
		oldInfo.setFootLeft(info.getFootLeft());
		oldInfo.setFootRight(info.getFootRight());
		if(riService.updateById(oldInfo))
		messageMap.initSuccess();
		return messageMap;
	} 


	

	/**
	 * 根据类型查询报表的页眉页脚
	 * @param reportNO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoByReportNO")
	@InvokeLog(name = "getInfo", description = "根据序号查询报表的页眉页脚")
	public DataRow getInfo(String reportNO) {
		Wrapper<ReportInfo> ew = new EntityWrapper<ReportInfo>();
		ew.eq("reportNO", reportNO);
		ReportInfo info = riService.selectOne(ew);
		messageMap.initSuccess(info);
		return messageMap;
	} 
	
}

