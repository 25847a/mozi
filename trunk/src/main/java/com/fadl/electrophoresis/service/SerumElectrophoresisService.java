package com.fadl.electrophoresis.service;

import com.fadl.common.DataRow;
import com.fadl.electrophoresis.entity.SerumElectrophoresis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  血清电泳 服务类
 * </p>
 *
 * @author hu
 * @since 2018-06-06
 */
public interface SerumElectrophoresisService extends IService<SerumElectrophoresis> {
	/**
	 * 查询未体检/体检人员
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> querySerumElectrophoresisList(HashMap<String, String> map, Page<DataRow> page) throws Exception;
	
	/**
	 * 根据浆员卡号查询该浆员是否要做血清蛋白电泳检验 目前是超过1年就要做检验,新浆员也要化验
	 * @param providerNo
	 * @param messageMap
	 * @throws Exception
	 */
	public void queryInfoByProviderNo(String providerNo,DataRow messageMap)throws Exception;
	
	/**
	 * 保存血清蛋白电泳方法
	 *  @param entity
	 *  @return boolean
	 *  @throws Exception
	 */
	boolean insertEntity(SerumElectrophoresis entity)throws Exception;
	
	/**
	 * 根据更新时间查找蛋白电泳记录,包含浆员基础信息
	 * @param electrophoresis
	 * @return
	 */
	List<DataRow> selectByUpdateDate(SerumElectrophoresis electrophoresis);
}
