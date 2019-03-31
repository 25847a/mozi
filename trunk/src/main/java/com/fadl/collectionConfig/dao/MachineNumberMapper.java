package com.fadl.collectionConfig.dao;

import java.util.List;
import java.util.Map;

import com.fadl.collectionConfig.entity.MachineNumber;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 采浆机号 Mapper 接口
 * </p>
 *
 * @author caijian&guokang
 * @since 2018-04-21
 */
public interface MachineNumberMapper extends BaseMapper<MachineNumber> {
	/**
	 * 查询采浆机号表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<DataRow> queryMachineNumberList(Map<String,Object> map,Pagination page)throws Exception;
}