package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 注射管理--普免基础针注射 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface OrdinaryInjectionMapper extends BaseMapper<OrdinaryInjection> {

	/**
	 * 普免基础针 未注射人员
	 * @param pagination 
	 * @param updateDate
	 * */
	public List<DataRow> notShotOrdBaseList (String updateDate,Pagination pagination)throws SQLException;
	
	/**
	 * 普免基础针 已注射人员
	 * @param updateDate 
	 * @param pagination
	 * */
	public List<DataRow> shotOrdBaseList (String updateDate, Pagination pagination)throws SQLException;
	
	/**
	 * 普免基础针  头部内容
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow ordinaryBaseHead (Map<String, Object> map) throws SQLException;
	
	/**
	 * 注射信息查询 
	 * @param map
	 * @param pagination 
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryShotMsg (Map<String, String> map, Pagination pagination)throws SQLException;
	
	/**
	 * 免疫注射信息      免疫基本信息
	 * @param map
	 * @param pagination
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> imuneMsg (Map<String, String> map,Pagination pagination)throws SQLException;
	
	/**
	 * 免疫注射信息     基础针注射信息
	 * @param map
	 * @param pagination
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> baseInjectMsg (Map<String,String> map,Pagination pagination)throws SQLException;
	
	/**
	 * 免疫注射信息     加强针注射信息
	 * @param map
	 * @param pagination
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> strengthenInjectMsg (Map<String,String> map,Pagination pagination)throws SQLException;
	
	//////////////////////////
	/**
	 * 获取基础免疫所有类别
	 * @return
	 * @throws SQLException
	 */
	public DataRow getBaseImmuneTypes (String id) throws SQLException;
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryVaccineInfo (String immune) throws SQLException;
	/**
	 * 获取加强免疫所有类别
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> getStreImmuneTypes (String id) throws SQLException;
	
	/**
	 * 啊健(查询普免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public OrdinaryInjection queryInjectionInfo(String providerNo) throws SQLException;
	/**
	 * 打印免疫乙肝表格
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> downloadImmune(Map<String,String> map) throws SQLException;
}
