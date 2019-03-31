package com.fadl.collectionConfig.service;

import com.fadl.collectionConfig.entity.Nation;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 民族表 服务类
 * </p>
 *
 * @author guokang
 * @since 2018-05-31
 */
public interface NationService extends IService<Nation> {
	/**
	 * 新增民族
	 * @param nation
	 * @param messageMap
	 * @throws Exception
	 */
	public void insertNation(Nation nation,DataRow messageMap)throws Exception;
	/**
	 * 修改民族
	 * @param nation
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateNation(Nation nation,DataRow messageMap)throws Exception;
	
}
