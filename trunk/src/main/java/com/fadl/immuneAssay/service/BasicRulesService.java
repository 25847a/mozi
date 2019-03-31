package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.BasicRules;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 注射规则设置 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-13
 */
public interface BasicRulesService extends IService<BasicRules> {
	/**
	 * 基础针注册规则设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	public void basicRulesList(Page<DataRow> page,Map<String,String> map)throws Exception;
	/**
	 * 加强针注册规则设置列表(加强)
	 * @param map
	 * @param page
	 * @return
	 */
	public void strongBasicRulesList(Page<DataRow> page,Map<String,String> map)throws Exception;
	/**
	 * 新增基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	public void insertBasicRules(BasicRules basicRules,DataRow messageMap)throws Exception;
	/**
	 * 新增加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	public void insertStrongBasicRules(BasicRules basicRules,DataRow messageMap)throws Exception;
	/**
	 * 修改基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	public void updateBasicRules(BasicRules basicRules,DataRow messageMap)throws Exception;
	/**
	 * 修改加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	public void updateStrongBasicRules(BasicRules basicRules,DataRow messageMap)throws Exception;
	/**
	 * 删除基础针注册规则设置
	 * @param id
	 * @return
	 */
	public void deleteBasicRules(Long id,DataRow messageMap)throws Exception;
	/**
	 * 删除加强针注册规则设置(加强)
	 * @param id
	 * @return
	 */
	public void deleteStrongBasicRules(Long id,DataRow messageMap)throws Exception;
}
