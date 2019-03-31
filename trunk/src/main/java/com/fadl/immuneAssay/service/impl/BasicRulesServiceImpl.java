package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.BasicRules;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.BasicRulesMapper;
import com.fadl.immuneAssay.service.BasicRulesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 注射规则设置 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-13
 */
@Service
public class BasicRulesServiceImpl extends ServiceImpl<BasicRulesMapper, BasicRules> implements BasicRulesService {

	@Autowired
	BasicRulesMapper basicRulesMapper;
	/**
	 * 基础针注册规则设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public void basicRulesList(Page<DataRow> page, Map<String, String> map) throws Exception {
		page.setRecords(basicRulesMapper.basicRulesList(map, page));
		
	}
	/**
	 * 加强针注册规则设置列表(加强)
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public void strongBasicRulesList(Page<DataRow> page, Map<String, String> map) throws Exception {
		page.setRecords(basicRulesMapper.strongBasicRulesList(map, page));
	}
	/**
	 * 新增基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	@Override
	public void insertBasicRules(BasicRules basicRules, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.insert(basicRules);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}
	/**
	 * 新增加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	@Override
	public void insertStrongBasicRules(BasicRules basicRules, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.insert(basicRules);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}
	/**
	 * 修改基础针注册规则设置
	 * @param basicRules
	 * @return
	 */
	@Override
	public void updateBasicRules(BasicRules basicRules, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.updateById(basicRules);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 修改加强针注册规则设置(加强)
	 * @param basicRules
	 * @return
	 */
	@Override
	public void updateStrongBasicRules(BasicRules basicRules, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.updateById(basicRules);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 删除基础针注册规则设置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteBasicRules(Long id, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}
	/**
	 * 删除加强针注册规则设置(加强)
	 * @param id
	 * @return
	 */
	@Override
	public void deleteStrongBasicRules(Long id, DataRow messageMap) throws Exception {
		int row = basicRulesMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}

}
