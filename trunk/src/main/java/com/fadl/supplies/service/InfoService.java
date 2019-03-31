package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Info;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材信息表 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
public interface InfoService extends IService<Info> {
	
	/**
	 * 查询耗材信息表列表
	 * @param suppliesInfo
	 * @param page
	 * @param limit
	 * @return
	 */
	public void querySuppliesInfoList(Page<DataRow> page,Map<String,String> map)throws Exception; 
	/**
	 * 新增耗材信息表
	 * @param suppliesInfo
	 * @return
	 */
	public void insertSuppliesInfo(Info suppliesInfo,DataRow messageMap)throws Exception;
	/**
	 * 修改耗材信息表
	 * @param situationConfig
	 * @return
	 */
	public void updateSuppliesInfo(Info suppliesInfo,DataRow messageMap)throws Exception;
	/**
	 * 删除耗材信息表
	 * @param id
	 * @return
	 */
	public void deleteSuppliesInfo(Long id,DataRow messageMap) throws Exception;
}
