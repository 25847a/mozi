package com.fadl.elisa.dao;

import com.fadl.elisa.entity.ElisaTemplateValues;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 酶标板模板值设置 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaTemplateValuesMapper extends BaseMapper<ElisaTemplateValues> {
	/**
	 * 批量插入
	 * @param etvs
	 */
	public void addEtvs(@Param("etvs")List<ElisaTemplateValues> etvs);  
}
