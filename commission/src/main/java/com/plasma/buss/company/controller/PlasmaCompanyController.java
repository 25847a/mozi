package com.plasma.buss.company.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.company.service.PlasmaCompanyService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.Validator;

/**
 * 公司管理
 * @author fadl
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class PlasmaCompanyController  extends BaseController{

	private static final Logger log = LogManager.getLogger(PlasmaCompanyController.class);
	
	@Autowired
	public PlasmaCompanyService plasmaCompanyService;
	
	/**
	 * 跳转公司管理页面
	 * @return
	 */
	@RequestMapping("/company")
	public String company(){
		return "company/company_list";
	}
	
	/**
	 * 查询公司列表
	 * @return
	 */
	@RequestMapping("/queryPlasmaCompanyList")
	@ResponseBody
	public DataRow queryPlasmaCompanyList(){
		try {
			pageBean.setPageNum(info.get("pageNum"));
			pageBean.setPageSize(info.getInt("pageSize"));
			pageBean = plasmaCompanyService.queryPlasmaCompanyList(info,pageBean);
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
	 * 查询公司列表
	 * @return
	 */
	@RequestMapping("/queryPlasmaCompanyAllList")
	@ResponseBody
	public DataRow queryPlasmaCompanyAllList(){
		try {
			pageBean.setPageNum(0);
			pageBean.setPageSize(10000);
			pageBean = plasmaCompanyService.queryPlasmaCompanyList(info,pageBean);
			super.fillResultContext(messageMap, pageBean.getPage());
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>queryPlasmaCompanyAllList",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据ID查询详情
	 * @return
	 */
	@RequestMapping("/queryCCDetails")
	@ResponseBody
	public DataRow queryCCDetails(){
		try {
			DataRow row = plasmaCompanyService.queryPlasmaCompanyById(info.getLong("id"));
			super.fillResultContext(messageMap, row);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("ProviderBaseinfoController>>>>>>>queryCCDetails",e);
		}
		return messageMap;
	}
	
	/**
	 * 添加公司
	 * @return
	 */
	@RequestMapping("/savePlasmaCompany")
	@ResponseBody
	public DataRow savePlasmaCompany(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("plasmaName");
			if (val.validate()) {
				if(!StringHelper.isEmpty(info.getString("id"))){
					int res = plasmaCompanyService.updatePlasmaCompanyById(info);
					if(res>0){
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
						messageMap.put(IConstants.APP_RESULT_MSG,"修改成功");
					}else{
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
						messageMap.put(IConstants.APP_RESULT_MSG, "修改失败");
					}
				}else{
					info.put("type", "0");
					plasmaCompanyService.savePlasmaCompany(info);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"添加成功");
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaCompanyController>>>>>>>savePlasmaCompany",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除公司
	 * @return
	 */
	@RequestMapping("/delPlasmaCompany")
	@ResponseBody
	public DataRow delPlasmaCompany(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id");
			if (val.validate()) {
				plasmaCompanyService.deletePlasmaCompanyById(info.getLong("id"),messageMap);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("PlasmaCompanyController>>>>>>>delPlasmaCompany",e);
		}
		return messageMap;
	}
}
