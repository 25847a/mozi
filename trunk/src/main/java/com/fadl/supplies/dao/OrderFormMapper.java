package com.fadl.supplies.dao;
 

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.OrderForm;

/**
 * <p>
 * 订购单记录 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface OrderFormMapper extends BaseMapper<OrderForm> {
	/**
	 * 入库根据订单号查询耗材
	 * @return
	 */
	public List<DataRow> querySuppliesInfo(Long order,Pagination pagination)throws Exception;
	
	/**
	 * 获取耗材检验列表
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<DataRow>  queryQuarantineList(Map<String, Object> quarantineData,Pagination pagination)throws Exception;
	/**
	 * 订购列表接口
	 * @param orderForm
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryOrder(OrderForm orderForm,Pagination pagination)throws Exception;
	/**
	 * 库存入库时耗材添加入库使用的接口
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryOrderFormPage(Pagination pagination)throws Exception;
}