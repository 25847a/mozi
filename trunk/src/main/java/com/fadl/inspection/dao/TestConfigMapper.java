package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.inspection.entity.TestConfig;

/**
 * <p>
 * 检验配置表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
public interface TestConfigMapper extends BaseMapper<TestConfig> {
	
	/**
	 * 修改检验配置的固定和非固定浆员的蛋白信息 
	 * @param config
	 */
	void updateWithProtein(TestConfig config) ;
	
	/**
	 * 根据ID逻辑删除
	 * @param id
	 */
	void deleteByIdWithDelFlag(Long id);
	
	/**
	 * 返回所有有效的配置模板
	 * @return
	 */
	List<TestConfig> findAllConfigByStatusWithEnable();
	
	
	
	
}
