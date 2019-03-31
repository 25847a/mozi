package com.fadl.cost.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.DataRow;
import com.fadl.cost.entity.CostGrant;


/**
 * <p>费用发放记录Mapper接口</p>
 * 
 * @author zhanll
 * @since 2018-5-5
 * */
public interface ICostGrantMapper extends BaseMapper<CostGrant>{
	
	
	/**费用发放主页面显示(未发放)*/
	public List<DataRow> queryNotGrantList (CostGrant cost,Page<DataRow> page) throws SQLException;
	
	/**费用发放主页面显示(已发放)*/
	public List<DataRow> queryGrantList (String createDate,Page<DataRow> page) throws SQLException;
	
	/**根据相关条件查询费用发放信息 （编号，浆员卡号）*/
	public List<DataRow> getCostGrantDetailList(String map) throws SQLException;
	
	/**费用发放首页*/
	public Map<String, Object> queryCostHead (Map<String, Object> map)throws SQLException;
	
	/**费用详情查询*/
	public List<DataRow> costDetail (Long id)throws SQLException;
	
	/**费用发放查询*/
	public List<DataRow> queryCostGrantList(Map<String, String> map,Page<DataRow> page)throws SQLException;
	
	/**导出数据Excel*/
	public List<DataRow> queryCostGrantList(Map<String, String> map)throws SQLException;
	
	/**
	 * 批量插入费用中间表  
	 * @return
	 * @throws SQLException
	 */
	public void insertActivityCostDetail(List<DataRow> list)throws SQLException;
	/**
	 * 更新费用发放记录
	 * @param costGrant
	 * @return
	 * @throws SQLException
	 */
	public int update(CostGrant costGrant)throws SQLException;
	
	/**
	 * 打印费用详情小票 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> printGrantCost(Long id) throws SQLException;
	
	/**
	 * 取消费用发放
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int updateCostGrantById(Long id)throws SQLException;
	
	/**
	 * 发放费用
	 * @param costGrant
	 * @return
	 * @throws SQLException
	 */
	public int updateGrandCostStatus(CostGrant costGrant)throws SQLException;
}
