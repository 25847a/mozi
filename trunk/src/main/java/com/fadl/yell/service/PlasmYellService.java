package com.fadl.yell.service;

import java.sql.SQLException;
import java.util.HashMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.yell.entity.PlasmYell;

/**
 * <p>
 * 采浆叫号 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface PlasmYellService extends IService<PlasmYell> {

	/**
	 * 查询未体检/体检人员
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryplasmaYellList(HashMap<String, String> map, Page<DataRow> page)throws Exception;
	
	/**
	 * 验证浆员
	 * @param plasmYell
	 * @param messageMap
	 * @throws SQLException
	 */
	public void updatePlasmYellById(HashMap<String, String> map,DataRow messageMap)throws Exception;
	
	/**
	 * 取消喊号
	 * @param map
	 * @param messageMap
	 * @throws SQLException
	 */
	public void cancelPlasmYell(HashMap<String, String> map,DataRow messageMap)throws Exception;
	
	/**
	 * 采浆验证打印条码
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> queryPlasmYellPrintInfo(String allId)throws Exception;
	/**
	 * 采浆前验证免疫
	 * @param providerNo
	 * @param messageMap
	 * @throws Exception
	 */
	public void verifyingImmunity(String providerNo,DataRow messageMap)throws Exception;
	

}
