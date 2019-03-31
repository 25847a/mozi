package com.fadl.inspection.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.inspection.entity.TestConfig;

/**
 * <p>
 * 检验配置表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
public interface TestConfigService extends IService<TestConfig> {
	/**
	 * 根据ID修改固定和非固定浆员的蛋白值信息
	 * @param config
	 */
	public void updateWithProtein(TestConfig config) ;
	
	/**
	 * 根据ID进行逻辑删除
	 * @param id
	 */
	public void deleteByIdWithDelFlag(Long id);
	
	/**
	 * 返回所有有效的配置模板
	 * @return
	 */
	List<TestConfig> findAllConfigByStatusWithEnable();
}
