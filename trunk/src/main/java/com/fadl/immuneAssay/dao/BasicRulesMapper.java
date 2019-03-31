package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.BasicRules;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 注射规则设置 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-13
 */
public interface BasicRulesMapper extends BaseMapper<BasicRules> {
	/**
	 * 基础针注册规则设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	public List<DataRow> basicRulesList(Map<String,String> map,Pagination pagination)throws Exception;
	/**
	 * 加强针注册规则设置列表(加强)
	 * @param map
	 * @param page
	 * @return
	 */
	public List<DataRow> strongBasicRulesList(Map<String,String> map,Pagination pagination)throws Exception;
}
