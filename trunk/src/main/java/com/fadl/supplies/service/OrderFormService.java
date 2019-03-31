package com.fadl.supplies.service;
 
import java.util.List;
import java.util.Map;

import com.fadl.common.DataRow;
import com.fadl.supplies.entity.OrderForm;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 订购单记录 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface OrderFormService extends IService<OrderForm> {
	
	
	/**
	 * 审批完成,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	public void updateBuyStatus(OrderForm orderForm,DataRow messageMap,Long id)throws Exception;
	/**
	 * 入库根据订单号查询耗材
	 * @return
	 */
	public void querySuppliesInfo(Long order,DataRow messageMap)throws Exception;
	/**
	 * 订购列表接口
	 * @param orderForm
	 * @param pageing
	 * @return
	 * @throws Exception
	 */
	public void queryOrder(OrderForm orderForm,Page<DataRow> pageing)throws Exception;
	/**
	 * 查询详情页面列表
	 * @param orderformId
	 * @return
	 */
	public Page<DataRow> queryDatelisList(String order,Page<DataRow> pageing)throws Exception;
	/**
	 * 查询耗材检验列表
	 * @param registerDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryQuarantineList(Map<String, Object> registerDate,Page<DataRow> page)throws Exception;
	/**
	 * 订单作废,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	public void UselessStatus(List<OrderForm> result,DataRow messageMap)throws Exception;
	/**
	 * 订单删除
	 * @param orderForm
	 * @return
	 */
	public DataRow delectOrder(OrderForm order,DataRow messageMap)throws Exception;
	/**
	 * 订单修改
	 * @param orderForm
	 * @return
	 */
	public void updateOrder(OrderForm orderForm,DataRow messageMap)throws Exception;
	/**
	 * 待审批、待检验,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	public void updateStatus(OrderForm orderForm,DataRow messageMap,Long id)throws Exception;
	/**
	 * 库存入库时耗材添加入库使用的接口
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryOrderFormPage(Page<DataRow> page)throws Exception;
}
