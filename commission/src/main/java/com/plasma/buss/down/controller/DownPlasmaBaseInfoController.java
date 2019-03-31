package com.plasma.buss.down.controller;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.down.service.DownPlasmaBaseInfoService;
import com.plasma.buss.plasma.service.ProviderBaseinfoService;
import com.plasma.buss.plasma.service.ProviderBaseinfoTempService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;

/**
 * 接收浆站传上来的新浆员数据
 * @author fadl
 *
 */
@Controller
@Scope("prototype")
public class DownPlasmaBaseInfoController extends BaseController{

	private static final Logger log = LogManager.getLogger(DownPlasmaBaseInfoController.class);
	
	@Autowired
	public DownPlasmaBaseInfoService downPlasmaBaseInfoService;
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public ProviderBaseinfoTempService providerBaseinfoTempService;
	
	/**
	 * 接收浆站传上来的浆员信息
	 * @return
	 */
	@RequestMapping("/downPlasmaBaseInfo")
	@ResponseBody
	public DataRow downPlasmaBaseInfo(String info){
		try {
			downPlasmaBaseInfoService.savePlasmaBaseInfo(info,messageMap);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>downPlasmaBaseInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 验证浆员是否审核通过
	 * @return
	 */
	@RequestMapping("/verification")
	@ResponseBody
	public DataRow verification(String providerNo){
		try {
			downPlasmaBaseInfoService.verification(info,messageMap);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>verification",e);
		}
		return messageMap;
	}
	
	/**
	 * 根据浆员卡号查询浆员信息
	 * @return
	 */
	@RequestMapping("/queryBaseInfo")
	@ResponseBody
	public DataRow queryBaseInfo(){
		try {
			DataRow row = providerBaseinfoService.queryBaseInfo(info);
			if(null!=row && row.getInt("status")==1){
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
				messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
			}else{
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
				messageMap.put(IConstants.APP_RESULT_MSG,"请先审核浆员");
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>queryBaseInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 接收取消发卡的请求
	 * @return
	 */
	@RequestMapping("/receiveCancelCard")
	@ResponseBody
	public DataRow receiveCancelCard(){
		try {
			providerBaseinfoTempService.receiveCancelCard(info, messageMap);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>receiveCancelCard",e);
		}
		return messageMap;
	}
	
	/**
	 * 接收信息变更申请
	 * @return
	 */
	@RequestMapping("/receivePlasmaUpdate")
	@ResponseBody
	public DataRow receivePlasmaUpdate(){
		try {
			providerBaseinfoTempService.receivePlasmaUpdate(info, messageMap);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>receivePlasmaUpdate",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询浆员审核状态
	 * @return
	 */
	@RequestMapping("/queryProviderStatus")
	@ResponseBody
	public DataRow queryProviderStatus(String providerNo){
		try {
			if(!StringHelper.isEmpty(providerNo)){
				info.put("ids", Arrays.asList(providerNo.split(",")));
				String row = providerBaseinfoService.queryProviderStatus(info);
				super.fillResultContext(messageMap, row);
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>queryProviderStatus",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除浆员信息
	 * @return
	 */
	@RequestMapping("/deleteProviderInfo")
	@ResponseBody
	public DataRow deleteProviderInfo(){
		try {
			int res = providerBaseinfoService.deleteProviderInfo(info);
			if(res>0){
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
				messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
			}else{
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
				messageMap.put(IConstants.APP_RESULT_MSG,"操作失败");
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("DownPlasmaBaseInfoController>>>>>>>deleteProviderInfo",e);
		}
		return messageMap;
	}
}
