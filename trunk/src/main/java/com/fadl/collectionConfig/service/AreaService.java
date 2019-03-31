package com.fadl.collectionConfig.service;

import com.fadl.collectionConfig.entity.Area;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 区域设置表 服务类
 * </p>
 *
 * @author guokang
 * @since 2018-05-24
 */
public interface AreaService extends IService<Area> {
	/**
	 * 新增区域设置
	 * @param area
	 * @param messageMap
	 * @throws Exception
	 */
	public void insertArea(Area area,DataRow messageMap)throws Exception;
	/**
	 * 修改区域设置
	 * @param area
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateArea(Area area,DataRow messageMap)throws Exception;
	/**
	 * 判断合法范围
	 * @param area
	 * @param messageMap
	 * @return
	 * @throws Exception
	 */
	public Area judgeArea(String address);
}