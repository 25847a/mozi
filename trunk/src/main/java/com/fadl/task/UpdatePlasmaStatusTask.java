package com.fadl.task;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fadl.common.DataRow;
import com.fadl.common.JsonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.StringHelper;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.upload.service.HttpClientBuss;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 更新浆员状态
 * @author hu
 *
 */
@Component
public class UpdatePlasmaStatusTask {

	private static Logger logger = LoggerFactory.getLogger(UpdatePlasmaStatusTask.class); 
	
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	
	/**
	 * 更新浆员状态 
	 */
	@Scheduled(cron="0 0/2 * * * ?")
	public void updateStatus(){
		try {
			DataRow row = providerBaseinfoService.queryNotGrantCard();
			if(null==row || StringHelper.isEmpty(row.getString("providerNo"))){
				return;
			}
			HttpClientBuss httpClientBuss = new HttpClientBuss();
			HashMap<String,String>map = new HashMap<String,String>();
			map.put("providerNo", row.getString("providerNo"));
			//发送数据到 卫计委
			String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/queryProviderStatus", map);
			if(null==posrData){
				logger.info("没有从卫计委获取到数据");
				return;
			}
			JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
			if(node.get("error").textValue().equals("-1")) {
				String providerNo = node.get("data").textValue();
				if (StringHelper.isEmpty(providerNo)) {
					return;
				}
				//根据浆员卡号更新审核状态
				row = new DataRow();
				row.put("ids", Arrays.asList(providerNo.split(",")));
				int res = providerBaseinfoService.updatePrividerStatus(row);
				if (res<1) {
					logger.error("更新浆员审核状态失败");
				}
			}
		} catch (Exception e) {
			logger.error("UpdatePlasmaStatusTask>>>>>>>>updateStatus>>>>>",e);
		}
	}
}
