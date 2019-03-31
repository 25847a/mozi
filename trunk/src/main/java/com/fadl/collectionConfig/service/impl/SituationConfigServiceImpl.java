package com.fadl.collectionConfig.service.impl;

import com.fadl.collectionConfig.entity.SituationConfig;
import com.fadl.collectionConfig.dao.SituationConfigMapper;
import com.fadl.collectionConfig.service.SituationConfigService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采浆情况设置表 服务实现类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
@Service
public class SituationConfigServiceImpl extends ServiceImpl<SituationConfigMapper, SituationConfig>
		implements SituationConfigService {
	@Autowired
	SituationConfigMapper situationConfigMapper;
	/**
	 * 新增采浆情况设置表
	 * 
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertSituationconfig(SituationConfig situationConfig,DataRow messageMap) throws Exception {
		int row = situationConfigMapper.insert(situationConfig);
		if (row > 0) {
			messageMap.initSuccess();
		} else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改采浆情况设置表
	 * 
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateSituationconfig(SituationConfig situationConfig,DataRow messageMap) throws Exception {
		int row = situationConfigMapper.updateById(situationConfig);
		if (row > 0) {
			messageMap.initSuccess();
		} else {
			messageMap.initFial();
		}
	}
}
