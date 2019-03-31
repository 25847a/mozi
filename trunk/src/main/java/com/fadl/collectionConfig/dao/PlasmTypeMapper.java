package com.fadl.collectionConfig.dao;

import com.fadl.collectionConfig.entity.PlasmType;
import com.fadl.common.DataRow;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 采浆机型表 Mapper 接口
 * </p>
 * @author caijian&guokang
 * @since 2018-04-21
 */
public interface PlasmTypeMapper extends BaseMapper<PlasmType> {
	/**
	 * 查询采浆机型表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<DataRow> queryPlasmTypeList(Map<String,Object> map,Pagination page)throws Exception;
}