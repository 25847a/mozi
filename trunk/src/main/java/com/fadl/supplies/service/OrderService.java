package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Order;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材订购记录表 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
public interface OrderService extends IService<Order> {

	public Page<DataRow> queryOrderList(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 耗材订购,录入耗材订购记录表、订购单记录
	 * @return
	 */
	public void insertOrderform(Map<String,String> map,DataRow messageMap)throws Exception;

}
