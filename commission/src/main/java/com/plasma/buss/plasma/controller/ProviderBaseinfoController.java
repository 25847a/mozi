package com.plasma.buss.plasma.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.plasma.buss.plasma.service.ProviderBaseinfoService;
import com.plasma.buss.plasma.service.ProviderBaseinfoTempService;
import com.plasma.common.CommonUtil;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.ReadProperties;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.Validator;

/**
 * 浆员信息管理
 * @author fadl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class ProviderBaseinfoController extends BaseController{

	private static final Logger log = LogManager.getLogger(ProviderBaseinfoController.class);
	
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public ProviderBaseinfoTempService providerBaseinfoTempService;
	
	/**
	 * 跳转左侧导航
	 * @return
	 */
	@RequestMapping("/left")
	public String left(){
		return "left";
	}
	
	/**
	 * 跳转浆员列表页面
	 * @return
	 */
	@RequestMapping("providerBaseinfoList")
	public String providerBaseinfoList(){
		return "plasma/plasma_list";
	}
	
	/**
	 * 查询未审核浆员列表
	 * @return
	 */
	@RequestMapping("/queryProviderBaseinfoList")
	@ResponseBody
	public DataRow queryProviderBaseinfoList(){
		try {
			pageBean.setPageNum(info.get("pageNum"));
			pageBean.setPageSize(info.getInt("pageSize"));
			pageBean = providerBaseinfoService.queryProviderBaseinfoList(info, pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>queryProviderBaseinfoList",e);
		}
		return messageMap;
	}
	
	/**
	 * 审核浆员
	 * @return
	 */
	@RequestMapping("/examinePlasma")
	@ResponseBody
	public DataRow examinePlasma(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				String ip= CommonUtil.getIpAddress(request);
                info.put("ip",ip);
                info.put("port", ReadProperties.getValue("port"));
                info.put("creater", getSessionUserId());
				 providerBaseinfoService.examinePlasma(info,messageMap);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>examinePlasma",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询浆员详情
	 * @return
	 */
	@RequestMapping("/queryDetail")
	public ModelAndView queryDetail(){
		ModelAndView mv = null;
		try {
			if(info.getInt("type")==2){
				mv = new ModelAndView("plasma/model_duibi");
			}else{
				mv = new ModelAndView("plasma/model");
			}
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				if(info.getInt("type")==2){
					DataRow data = providerBaseinfoTempService.queryBaseTempInfo(info);
					mv.addObject("result", data);
					DataRow row = providerBaseinfoService.queryDetail(data.getLong("baseId"));
					mv.addObject("data",row);
				}else{
					DataRow row = providerBaseinfoService.queryDetail(info.getLong("id"));
					mv.addObject("result",row);
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>queryDetail",e);
		}
		return mv;
	}
	
	/**
	 * 取消发卡
	 * @return
	 */
	@RequestMapping("/cancelExaminePlasma")
	@ResponseBody
	public DataRow cancelExaminePlasma(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				int res = providerBaseinfoService.cancelExaminePlasma(info);
				if (res > 0) {
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"取消发卡成功");
				}else{
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, "取消发卡失败");
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>cancelExaminePlasma",e);
		}
		return messageMap;
	}
}
