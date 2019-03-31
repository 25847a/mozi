package com.fadl.immuneAssay.service;

import java.sql.SQLException;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.OrdinaryStrengthenInjection;

/**
 * <p>
 * 注射管理-普免加强针注射 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface OrdinaryStrengthenInjectionService extends IService<OrdinaryStrengthenInjection> {

	/**
	 * 普免加强针注射  未注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> notShotOrdStrengthen(String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 普免加强针注射  已注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> haveShotOrdStrengthen(String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 普免加强针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow ordinaryStrengthenHead (Map<String, Object> map) throws Exception;
	/**
	 * 获取加强免疫所有类别
	 * @param id
	 * @return
	 */
	public DataRow getBaseImmuneStreng(String id)throws SQLException;
	/**
	 * 啊健(查询普免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public OrdinaryStrengthenInjection queryStrengInjectionInfo(String providerNo) throws SQLException;
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws Exception
	 */
	public DataRow queryVaccineInfoStreng (String immune) throws Exception;
}
