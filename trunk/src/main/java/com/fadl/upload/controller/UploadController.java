package com.fadl.upload.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.JsonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.upload.service.HttpClientBuss;


@RequestMapping("/upload")
@Controller
public class UploadController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private ProviderBaseinfoService providerBaseinfoService;
	
	public String getMessage() {
		
		return "";
	}
	
	/**
	 * 
	 * 未审核浆员发送到卫计委系统
	 * 
	 * */
	/*@RequestMapping("/sendPlasmaMsg")
	@InvokeLog(name = "sendPlasmaMsg", description = "未审核浆员发送到卫计委系统")
	@ResponseBody
	public DataRow sendPlasmaMsg() {
		try {
			List<ProviderBaseinfo> result = providerBaseinfoService.sendPlasmaMsg();
			//将图片当做流传输（图片正反面的参数都为文件名+文件流）
			for (int i = 0; i < result.size(); i++) {
				String imagez = result.get(i).getImagez();
				System.out.println("浆站图片地址："+ReadProperties.getValue("baseDir")+imagez);
				String imagezSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagez);
				result.get(i).setImagez(imagez+imagezSteam);
				String imagef = result.get(i).getImagef();
				String imagefSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagef);
				result.get(i).setImagef(imagef+imagefSteam);
			}
			HttpClientBuss httpClientBuss = new HttpClientBuss();
			Map<String, String> map = new HashMap<>();
			map.put("postData", JsonUtil.getMapper().writeValueAsString(result));
			String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/client/getPlasmaMsg", map);
			System.out.println(posrData);
			JSONObject json = JSONObject.fromObject(posrData);
			json.get("msg");
			System.out.println("获取浆员信息成功".equals(json.get("msg")));
			messageMap.initSuccess(posrData);
		} catch (Exception e) {
			logger.error("UploadController>>>>>>>>>sendPlasmaMsg>>>>>>>>",e);
		}
		return messageMap;
	}*/
	
	/**
	 * 
	 * 发送至卫计委进行审核
	 * 
	 * */
	@RequestMapping("/conectToCommission")
	@InvokeLog(name = "conectToCommission", description = "发送至卫计委进行审核")
	@ResponseBody
	public DataRow conectToCommission() {
		HttpClientBuss httpClientBuss = new HttpClientBuss();
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		String result = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/client/getPlasmaMsg", map);
		if(result != null) {
			messageMap.initSuccess(result);
		}else {
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 获取从卫计委返回的浆站公司信息
	 * 
	 * */
	@RequestMapping("/getCommissionMsg")
	@InvokeLog(name = "getCommissionMsg", description = "获取从卫计委返回的浆站公司信息")
	@ResponseBody
	public DataRow getCommissionMsg(String providerNo) {
		try {
			HttpClientBuss httpClientBuss = new HttpClientBuss();
			Map<String, String> map = new HashMap<>();
			map.put("providerNo", providerNo);
			String result = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/verification", map);
			if(result == null || "".equals(result)) {
				messageMap.initFial();
			}else {
		    	DataRow json = JsonUtil.getMapper().readValue(result, DataRow.class);
				messageMap.initSuccess(json.get("data"));
			}
		} catch (Exception e) {
			logger.error("UploadController>>>>>>>>>UploadController>>>>>",e);
			messageMap.initError();
		}
		
		return messageMap;
	}
	
	/**
	 * 将以发卡浆员审核状态改成已审核
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping("/updateIsGrant")
	@InvokeLog(name = "updateIsGrant", description = "将以发卡浆员审核状态改成已发卡")
	@ResponseBody
	public DataRow updateIsGrant(Long id) throws Exception {
		ProviderBaseinfo baseinfo = new ProviderBaseinfo();
		baseinfo.setIsGrantCard(2);
		baseinfo.setId(id);
		boolean result = providerBaseinfoService.updateById(baseinfo);
		if(result) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
		return messageMap;
	}
	
}
