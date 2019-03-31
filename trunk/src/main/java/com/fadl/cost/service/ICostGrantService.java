package com.fadl.cost.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.cost.entity.CostGrant;


/**
 * <p>费用发放记录service接口</p>
 * 
 * @author zhanll
 * @since 2018-5-5
 * */
public interface ICostGrantService extends IService<CostGrant>{
	
	/**
	 * 查看费用发放记录
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<CostGrant> getCostGrant(Integer page,Integer limit) throws Exception;
	
	/**
	 * 浆员费用信息列表(未发放)
	 * @param createDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryNotGrantList (CostGrant cost,Page<DataRow> page) throws Exception;
	
	/**
	 * 费用发放主页面显示(已发放)
	 * @param createDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryGrantList (String createDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 根据providerNo相关条件查询费用发放信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> getCostGrantDetailList(String param) throws Exception;
	
	/**
	 * 费用发放首页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCostHead (Map<String, Object> map)throws Exception;
	
	/**
	 * 费用详情查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> costDetail (Long id)throws Exception;
	
	/**
	 * 修改发放状态(取消发放--发放)
	 * @param costGrant
	 * @param image
	 * @param messageMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusTo1(CostGrant costGrant,String image,DataRow messageMap) throws Exception;
	/**
	 * 费用发放验证是否已经进行免疫流程
	 * @param providerNo
	 * @return
	 */
	public void costImmunity(String providerNo,DataRow messageMap)throws Exception;
	/**
	 * 修改发放状态(发放--取消发放)
	 * @param costGrant
	 * @param image
	 * @param messageMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusTo0(CostGrant costGrant, DataRow messageMap) throws Exception;
	
	/**
	 * 费用发放查询
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryCostGrantList(Map<String, String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 导出数据Excel
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> exportCostGrant(Map<String, String> map)throws Exception;
	
	/**
	 * 批量插入费用中间表  
	 * @return
	 * @throws SQLException
	 */
	public void insertActivityCostDetail(List<DataRow> list)throws Exception;
	
	/**
	 * 打印费用详情小票 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> printGrantCost(Long id) throws Exception;
}
