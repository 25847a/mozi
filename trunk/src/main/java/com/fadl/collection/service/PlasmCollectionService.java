package com.fadl.collection.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.collection.entity.PlasmCollection;
import com.fadl.common.DataRow;

/**
 * <p>
 * 采浆记录 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface PlasmCollectionService extends IService<PlasmCollection> {

	/**
	 * 查询采浆列表
	 * @param map
	 * @param page
	 * @throws SQLException
	 */
	public Page<DataRow> queryPlasmCollectionList(HashMap<String, String> map, Page<DataRow> page)throws Exception;
	/**
	 * 更新采浆信息
	 * @param map
	 * @throws SQLException
	 */
	public void updatePlasmCollection(PlasmCollection plasmCollection,String detail,BigDecimal money,String image,Integer bloodGrade,DataRow messageMap)throws Exception;
	/**
	 * "验证采浆后是否需要进行免疫流程
	 * @param providerNo
	 * @return
	 */
	public void collectionAfterImmune(String providerNo,DataRow messageMap)throws Exception;
	/**
	 * 根据 id 查询详情
	 * @param id
	 * @param messageMap
	 * @throws SQLException
	 */
	public void queryPlasmCollectionById(Long id,DataRow messageMap)throws Exception;
	
	/**
	 * 采浆记录高级查询
	 * @param map
	 * @param page
	 * @throws SQLException
	 */
	public Page<DataRow> querySeniorQueryPlasmCollectionList(HashMap<String, String> map, Page<DataRow> page)throws Exception; 
	
	/**
	 * 采浆记录高级查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySeniorQueryPlasmCollectionList(HashMap<String, String> map)throws Exception; 
	
	/**
	 * 查询采浆护士列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCollectionAdminList()throws Exception;
	
	/**
	 * 更新血浆状态
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updatePlasmStatus(HashMap<String, String> map) throws Exception;
	
	/**
	 * 查询采浆活动费用
	 * @param costGrant
	 * @param providerRegistries
	 * @return
	 * @throws Exception
	 */
	public void queryNextCollectionCost(Long allId,DataRow messageMap)throws Exception;
	/**
	 * 给免疫流程判断是否需要特免登记 
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public DataRow queryImmuneCollectionCount(String providerNo)throws Exception;
	
	/**
	 * 根据实体集合更新送样记录
	 * @param pss
	 * @param sendPerson
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	boolean sendOff (List<Long> l,String sendPerson, DataRow dataRow) throws Exception;
	
	/**
	 * 献血浆者采浆记录表 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPrintPlasmaCollectionRecordList(HashMap<String, String> map)throws Exception;
	/**
	 * 获取耗材信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public void queryDetailedCollectionInfo(String data,DataRow messageMap)throws Exception;
	
	/**
	 * 修改血浆重量
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePlasmaWeight(PlasmCollection plasm,DataRow messageMap)throws Exception;
}

