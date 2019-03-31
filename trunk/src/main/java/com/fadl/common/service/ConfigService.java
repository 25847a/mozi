package com.fadl.common.service;

import com.fadl.common.DataRow;
import com.fadl.common.entity.Config;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-11
 */
public interface ConfigService extends IService<Config> {

	/**
	 * 获取配置
	 * @param type
	 * @param label
	 * @return
	 */
	public Config getConfig(String type,String label)throws Exception;
	/**
	 * 获取浆站配置信息
	 * @return
	 * @throws Exception
	 */
	public List<Config> getConfig(String type)throws Exception;
	/**
	 * 获取配置
	 * @param lable
	 * @param value
	 * @return
	 */
	public Config queryConfig (String lable,String value)throws Exception;
	/**
	 * 库存预警修改(健)
	 * @param config
	 * @throws Exception
	 */
	public void updatePolice(List<Config> config)throws Exception;
	/**
	 * 新增系统设置
	 * @param config
	 * @param messageMap
	 */
	public void insertConfig(Config config, DataRow messageMap) throws Exception;
	/**
	 * 修改系统设置
	 * @param config
	 * @param messageMap
	 */
	public void updateConfig(Config config, DataRow messageMap) throws Exception;
	/**
	 * 根据 type value 获取 配置
	 * @param type
	 * @param value
	 * @return
	 */
	public Config getConfigByValue(String type,String value)throws Exception;
}
