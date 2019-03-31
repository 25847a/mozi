package com.fadl.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.config.RedisConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

/**
 * 配置文件同步到 redis
 * @author hu
 *
 */
@Component
public class ConfigTask {

	private static Logger logger = LoggerFactory.getLogger(ConfigTask.class); 
	
	@Autowired
	public RedisConfig redisConfig;
	@Autowired
	public ConfigService configService;
	
	/**
	 * 同步 config 配置文件 每5分钟更新一次
	 */
	@Scheduled(cron="0 0/5 * * * ?")
	public void syncConfig(){
		Jedis jedis = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			jedis = redisConfig.redisPoolFactory().getResource();
			//获取所有的配置文件
			List<Config> list = configService.selectList(null);
			if (null!=list && list.size()>0) {
				for (Config config : list) {
					//根据 key 获取数据
					String res = jedis.get("config_value_task:"+config.getType()+":"+config.getValue());
					String con = mapper.writeValueAsString(config);
					//先判断是否存在 key ，如果不存在就添加，否则先删除，然后添加
					if(StringHelper.isEmpty(res)){
						jedis.zadd("config_task_list:"+config.getType(), config.getId(), con);
					}else{
						jedis.zrem("config_task_list:"+config.getType(), con);
						jedis.zadd("config_task_list:"+config.getType(), config.getId(), con);
					}
					//根据 type:value 进行 redis 存储
					jedis.set("config_value_task:"+config.getType()+":"+config.getValue(), con);
					//根据 type:label 进行 redis 存储
					jedis.set("config_label_task:"+config.getType()+":"+config.getLable(), con);
				}
			}
		} catch (Exception e) {
			logger.error("ConfigTask>>>>>>>syncConfig>>>>>>>>",e);
		}finally{
			if(null!=jedis){
				jedis.close();
			}
		}
	}
}
