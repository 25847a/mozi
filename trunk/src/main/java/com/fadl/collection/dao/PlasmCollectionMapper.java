package com.fadl.collection.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.collection.entity.PlasmCollection;
import com.fadl.common.DataRow;

/**
 * <p>
 * 采浆记录 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface PlasmCollectionMapper extends BaseMapper<PlasmCollection> {

	/**
	 * 查询采浆列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmCollectionList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 根据 id 查询详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmCollectionById(Long id)throws SQLException;
	
	/**
	 * 采浆记录高级查询
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySeniorQueryPlasmCollectionList(Pagination page,HashMap<String, String> map)throws SQLException;
	/**
	 * 采浆记录高级查询(啊健)
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmCollectionCountList(HashMap<String, String> map)throws SQLException;
	/**
	 * 采浆记录高级查询总数(啊健)
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryPlasmCollectionListCount()throws SQLException;
	/**
	 * 采浆记录高级查询
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySeniorQueryPlasmCollectionList(HashMap<String, String> map)throws SQLException;
	/**
	 * 采浆记录高级查询总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int querySeniorQueryPlasmCollectionListCount(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询采浆护士列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCollectionAdminList()throws SQLException;
	
	/**
	 * 更新血浆状态
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updatePlasmStatus(HashMap<String, String> map) throws SQLException;
	/**
	 * 给免疫流程判断是否需要特免登记 
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public DataRow queryImmuneCollectionCount(String providerNo)throws Exception;
	
	/**
	 * 查询浆员采浆次数
	 * @param row
	 * @return
	 * @throws SQLException
	 */
	public int queryPlasmaCollectionCount(DataRow row)throws SQLException;
	
	/**
	 * 献血浆者采浆记录表 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPrintPlasmaCollectionRecordList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询未送样列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySendSimpleList(HashMap<String, Object> map)throws SQLException;
}
