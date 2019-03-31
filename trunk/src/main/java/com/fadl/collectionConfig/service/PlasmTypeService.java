package com.fadl.collectionConfig.service;

import com.fadl.collectionConfig.entity.PlasmType;
import com.fadl.common.DataRow;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 采浆机型表 服务类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
public interface PlasmTypeService extends IService<PlasmType> {
	/**
	 * 查询采浆机型表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<DataRow> queryPlasmTypeList(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 新增采浆机型表
	 * @param plasmType
	 * @return
	 */
	public void insertPlasmType(PlasmType plasmType,DataRow messageMap)throws Exception;
	/**
	 * 修改采浆机型表
	 * @param plasmType
	 * @return
	 */
	public void updatePlasmType(PlasmType plasmType,DataRow messageMap)throws Exception;
}
