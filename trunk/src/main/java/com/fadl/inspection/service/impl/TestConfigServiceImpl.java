package com.fadl.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.IConstants;
import com.fadl.inspection.dao.TestConfigMapper;
import com.fadl.inspection.entity.TestConfig;
import com.fadl.inspection.service.TestConfigService;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 检验配置表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
@Service
public class TestConfigServiceImpl extends ServiceImpl<TestConfigMapper, TestConfig> implements TestConfigService {
	
	@Autowired
	public LogService logService;
	/**
	 * 根据ID修改固定和非固定浆员的蛋白值信息
	 * @param config
	 */
	public void updateWithProtein(TestConfig config) {
		baseMapper.updateWithProtein(config);
		try {
			logService.insertLog(IConstants.MODULE_TCI,IConstants.DESC.replace("{0}", "修改了检验配置的固定和非固定浆员的蛋白值信息"),"null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID进行逻辑删除
	 * @param id
	 */
	public void deleteByIdWithDelFlag(Long id) {
		baseMapper.deleteByIdWithDelFlag(id);
		try {
			logService.insertLog(IConstants.MODULE_TCI,IConstants.DESC.replace("{0}", "删除了检验配置,ID为"+id),"null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TestConfig> findAllConfigByStatusWithEnable() {
		
		return baseMapper.findAllConfigByStatusWithEnable();
	}
}
