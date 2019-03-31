package com.fadl.immuneAssay.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.OrdinaryInjection;

/**
 * <p>
 * 注射管理--普免基础针注射 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface OrdinaryInjectionService extends IService<OrdinaryInjection> {

	/**
	 * 普免基础针 未注射人员（分页）
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> notShotOrdBaseList (String updateDate,Page<DataRow> page)throws Exception;
	
	/**
	 * 普免基础针 已注射人员（分页）
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> shotOrdBaseList (String updateDate,Page<DataRow> page)throws Exception;
	
	/**
	 * 普免基础针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow ordinaryBaseHead (Map<String, Object> map) throws Exception;
	
	/**
	 * 注射信息查询 
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryShotMsg (Map<String, String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 免疫注射信息      免疫基本信息
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> imuneMsg (Map<String, String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 免疫注射信息     基础针注射信息
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void baseInjectMsg (Map<String,String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 免疫注射信息       加强针注射信息
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void strengthenInjectMsg (Map<String,String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 获取基础免疫所有类别
	 * @return
	 * @throws Exception
	 */
	public DataRow getBaseImmuneTypes (String id) throws Exception;
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws Exception
	 */
	public DataRow queryVaccineInfo (String immune) throws Exception;
	
	/**
	 * 获取加强免疫所有类别
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> getStreImmuneTypes (String id) throws Exception;
	
	/**
	 * 啊健(查询普免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public OrdinaryInjection queryInjectionInfo(String providerNo) throws SQLException;
	/**
	 * 打印注射文件
	 * @param map
	 * @return
	 */
	public void downloadImmune(Map<String,String> map,DataRow messageMap)throws Exception;
}
