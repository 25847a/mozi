package com.fadl.common.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.JsonUtil;
import com.fadl.common.dao.ConfigMapper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.config.RedisConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-11
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

	@Autowired
	public ConfigMapper configMapper;
	@Autowired
	public RedisConfig redisConfig;

	/**
	 * 获取config 配置
	 * @param type
	 * @param label
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public Config getConfig(String type,String label) throws Exception{
		Jedis jedis = redisConfig.redisPoolFactory().getResource();
		String res = jedis.get("config_label_task:"+type+":"+label);
		jedis.close();
		return JsonUtil.getMapper().readValue(res, Config.class);
	}
	
	/**
	 * 获取config 配置
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Config getConfigByValue(String type,String value) throws Exception{
		Jedis jedis = redisConfig.redisPoolFactory().getResource();
		String res = jedis.get("config_value_task:"+type+":"+value);
		jedis.close();
		return JsonUtil.getMapper().readValue(res, Config.class);
	}

	/**
	 * 获取浆站配置信息
	 */
	@Override
	public List<Config> getConfig(String type) throws Exception {
		Jedis jedis = redisConfig.redisPoolFactory().getResource();
		Set<String> set = jedis.zrange("config_task_list:"+type, 0, -1);
		List<Config> list = new ArrayList<Config>();
		for (String config : set) {
			Config con = JsonUtil.getMapper().readValue(config, Config.class);
			list.add(con);
		}
		jedis.close();
		return list;
	}
	/**
	 * 获取配置
	 * @param lable
	 * @param value
	 * @return
	 */
	public Config queryConfig (String lable,String value)throws Exception{
		EntityWrapper<Config> ew = new EntityWrapper<Config>();
		ew.eq("lable", lable);
		ew.eq("value", value);
		return this.selectOne(ew);
	}
	/**
	 * 库存预警修改(健)
	 * @param config
	 * @throws Exception
	 */
	public void updatePolice(List<Config> config)throws Exception{
		this.updateBatchById(config);
	}
	/**
	 * 新增系统设置
	 */
	@Override
	public void insertConfig(Config config, DataRow messageMap) throws Exception {
		int row = configMapper.insert(config);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改系统设置
	 */
	@Override
	public void updateConfig(Config config, DataRow messageMap) throws Exception {
		int row = configMapper.updateById(config);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}

}
