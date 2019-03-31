package com.fadl.collectionConfig.service;

import com.fadl.collectionConfig.entity.Disease;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 淘汰表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-06-27
 */
public interface DiseaseService extends IService<Disease> {
	/**
	 * 新增淘汰信息
	 * @param disease
	 * @return
	 */
	public void insertDisease(Disease disease,DataRow messageMap)throws Exception;
	/**
	 * 修改淘汰信息
	 * @param disease
	 * @return
	 */
	public void updateDisease(Disease disease,DataRow messageMap)throws Exception;
}
