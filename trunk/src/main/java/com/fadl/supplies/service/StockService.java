package com.fadl.supplies.service;

import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Order;
import com.fadl.supplies.entity.Stock;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材库存记录 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface StockService extends IService<Stock> {
	
	/**
	 * 获取订单入库拥有订单号的耗材(适用报损、退还、销毁、出库)
	 * @param page
	 * @return
	 */
	public void querySuppliesStatus(Page<DataRow> page) throws Exception;
	/**
	 * 查询库房详情列表
	 * @param batchNumber
	 * @param page
	 * @return
	 */
	public Page<DataRow> queryStockDatelis(String id,Page<DataRow> page)throws Exception;
	/**
	 * 插入订单信息入库存表
	 * @param suppliesStock
	 * @return
	 */
	public void insertStock(String result,DataRow messageMap,List<Order> order)throws Exception;
	/**
	 * 库存报警列表
	 * @param map
	 * @param page
	 * @throws Exception
	 */
	public void queryPoliceList(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 修改库存预警配置
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePolice(Map<String,String> map,DataRow messageMap)throws Exception;
	/**
	 * 提供给谢鑫检验查询
	 * @param map
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public void queryTestList(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 提供给谢鑫检验查询2
	 * @param map
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public void queryTestPage(Map<String,String> map,DataRow messageMap)throws Exception;
	/**
	 * 盘点查询
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void queryPointList(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 修改盘点数量
	 * @param stock
	 * @return
	 */
	public void updatePoint(Stock stock,DataRow messageMap)throws Exception;
	/**
	 * 修改剩余数量
	 * @param stock
	 * @return
	 */
	public void updateSurplusNumber(Stock stock,DataRow messageMap)throws Exception;
	/**
	 * 谢鑫使用的SQL语句
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryOutNumberInfo(Page<DataRow> page)throws Exception;
	
	/**
	 * 根据ID查询对应的耗材信息，包含其他实体信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DataRow queryById(Long id)throws Exception;
	
}
