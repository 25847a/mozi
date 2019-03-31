package com.fadl.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.check.service.CheckService;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DateUtil;
import com.fadl.common.JsonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.inspection.service.NewCardService;
import com.fadl.upload.service.HttpClientBuss;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 同步 浆员信息、体检、化验、献浆记录 到卫计委系统
 * @author hu
 *
 */
@Component
public class SyncCommissionTask {

	private static Logger logger = LoggerFactory.getLogger(SyncCommissionTask.class); 
	@Autowired
	public NewCardService newCardService;
	@Autowired
	public CheckService checkService;
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	public ConfigService configService;
	
	public static boolean flag=true;
	
	public static String sync="";
	
	/**
	 * 同步 config 配置文件
	 */
	@Scheduled(cron="0 0 23 * * ?")
	public void syncStart(){
		synchronized (sync) {
			if (flag) {
				flag=false;
				try {
					Thread.sleep(50000);
					Config config = configService.getConfig("stock_config", "code");
					if(null==config){
						logger.error("未获取到浆站id，同步失败");
						return;
					}
					String plasmaId= config.getValue();
					//化验
					sycnData("queryNewCardMaxDate","downAssay",0,plasmaId);
					//体检
					sycnData("queryCheckMaxDate","downCheck",1,plasmaId);
					//采浆
					sycnData("queryPlasmaCollectionMaxDate","downPlasmaCollection",2,plasmaId);
				} catch (Exception e) {
					logger.error("NewCardTask>>>>>>>syncNewCard>>>>>>>>",e);
				}finally{
					flag=true;
				}
			}
		}
	}
	
	/**
	 * 化验
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sycnData(String url,String uploadUrl,int type,String plasmaId) throws Exception{
		HttpClientBuss http = new HttpClientBuss();
		//发送数据到 卫计委
		Map<String, String> map = new HashMap<String, String>();
		map.put("plasmaId", plasmaId);
		String result = http.doPost(ReadProperties.getValue("commissionAddress")+"/"+url, map);
		JsonNode node = JsonUtil.getMapper().readValue(result, JsonNode.class);
		if (node.get("error").textValue().equals("-1")) {
			// 获取最后同步时间
			node = node.get("data").get("date");
			Long date = null;
			if(null!=node){
				date = node.asLong();
			}
			EntityWrapper ews = null;
			int page=1;
			while(true){
				ews = new EntityWrapper();
				if(null!=date){
					ews.gt("updateDate", DateUtil.formatDate(new Date(date), DateUtil.yyyyMMddHHmmss));
				}
				ews.orderBy("updateDate", true);
				ews.last("LIMIT "+((page-1)*500)+",500");
				List<Object> list=null;
				switch (type) {
				case 0:
					ews.eq("isAssay", 1);
					list = newCardService.selectList(ews);
					break;
				case 1:
					ews.eq("isCheck", 1);
					list = checkService.selectList(ews);
					break;
				case 2:
					ews.eq("isCollection", 1);
					list = plasmCollectionService.selectList(ews);
					break;
				}
				if (null==list || list.size()==0) {
					break;
				}else{
					map.put("data", JsonUtil.getMapper().writeValueAsString(list));
					result = http.doPost(ReadProperties.getValue("commissionAddress")+"/"+uploadUrl, map);
					node = JsonUtil.getMapper().readValue(result, JsonNode.class);
					if (node.get("error").textValue().equals("-1")) {
						logger.info("同步数据成功》》》》》》》》》+"+list.size());
					}else{
						throw new Exception("同步数据异常》》》》》》》》》》》》》");
					}
					if (list.size() < 100) {
						break;
					}
					page++;
				}
			}
		}else{
			logger.error("同步数据失败》》》》》》》");
		}
	}
}
