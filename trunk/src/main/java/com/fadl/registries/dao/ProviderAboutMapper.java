package com.fadl.registries.dao;

import com.fadl.common.DataRow;
import com.fadl.registries.entity.ProviderAbout;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 预约时间表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-09-11
 */
public interface ProviderAboutMapper extends BaseMapper<ProviderAbout> {

	/**
	 * 预约人数查询
	 * @param aboutDate
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryAboutPeople(Map<String,String> map,Pagination pagination)throws Exception;
}
