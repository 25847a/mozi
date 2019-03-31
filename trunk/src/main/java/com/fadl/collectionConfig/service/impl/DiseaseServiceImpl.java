package com.fadl.collectionConfig.service.impl;

import com.fadl.collectionConfig.entity.Disease;
import com.fadl.collectionConfig.dao.DiseaseMapper;
import com.fadl.collectionConfig.service.DiseaseService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 淘汰表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-06-27
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

	@Autowired
	DiseaseMapper diseaseMapper;
	/**
	 * 新增淘汰信息
	 * @param disease
	 * @return
	 */
	@Override
	public void insertDisease(Disease disease, DataRow messageMap) throws Exception {
		int row = diseaseMapper.insert(disease);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改仓库信息
	 * @param disease
	 * @return
	 */
	@Override
	public void updateDisease(Disease disease, DataRow messageMap) throws Exception {
		int row = diseaseMapper.updateById(disease);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
