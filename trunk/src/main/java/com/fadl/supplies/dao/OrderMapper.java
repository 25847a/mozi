package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Order;

/**
 * <p>
  * 耗材订购记录表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface OrderMapper extends BaseMapper<Order> {
	/**
	 * 订单审核列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<DataRow> queryOrderList(Map<String,Object>map,Pagination pagination)throws Exception;
}