package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.PrivilegeStrengthenInjection;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 注射管理-特免加强针注射 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface PrivilegeStrengthenInjectionService extends IService<PrivilegeStrengthenInjection> {

	
	/**
	 * 特免加强针注射  未注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> notShotSpeStrengthen (String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 特免加强针注射  已注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> haveShotSpeStrengthen (String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 特免加强针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow specialStrengthenHead (Map<String, Object> map) throws Exception;
	/**
	 * 啊健(查询特免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public PrivilegeStrengthenInjection queryStrengPrivilegeInfo(String providerNo) throws SQLException;

}
