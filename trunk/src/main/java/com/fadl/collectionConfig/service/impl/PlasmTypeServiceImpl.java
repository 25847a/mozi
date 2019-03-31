package com.fadl.collectionConfig.service.impl;

import com.fadl.collectionConfig.entity.PlasmType;
import com.fadl.collectionConfig.dao.PlasmTypeMapper;
import com.fadl.collectionConfig.service.PlasmTypeService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采浆机型表 服务实现类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
@Service
public class PlasmTypeServiceImpl extends ServiceImpl<PlasmTypeMapper, PlasmType> implements PlasmTypeService {

	@Autowired
	PlasmTypeMapper plasmTypeMapper;
	/**
	 * 查询采浆机型表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public Page<DataRow> queryPlasmTypeList(Map<String,Object> map, Page<DataRow> pageing) throws Exception {
		return pageing.setRecords(plasmTypeMapper.queryPlasmTypeList(map, pageing));
	}
	/**
	 * 新增采浆机型表
	 * @param plasmType
	 * @return
	 */
	@Override
	public void insertPlasmType(PlasmType plasmType,DataRow messageMap) throws Exception {
		int row = plasmTypeMapper.insert(plasmType);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改采浆机型表
	 * @param plasmType
	 * @return
	 */
	@Override
	public void updatePlasmType(PlasmType plasmType,DataRow messageMap) throws Exception {
		int row = plasmTypeMapper.updateById(plasmType);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
