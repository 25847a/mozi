package com.fadl.collectionConfig.service.impl;

import com.fadl.collectionConfig.entity.Nation;
import com.fadl.collectionConfig.dao.NationMapper;
import com.fadl.collectionConfig.service.NationService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 民族表 服务实现类
 * </p>
 *
 * @author guokang
 * @since 2018-05-31
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {
	
	@Autowired
	NationMapper nationMapper;
	
	/**
	 * 新增民族
	 */
	@Override
	public void insertNation(Nation nation,DataRow messageMap)throws Exception {
		int row = nationMapper.insert(nation);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改民族
	 */
	@Override
	public void updateNation(Nation nation,DataRow messageMap)throws Exception {
		int row = nationMapper.updateById(nation);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
