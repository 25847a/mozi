package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.OrderFormMapper;
import com.fadl.supplies.dao.OrderMapper;
import com.fadl.supplies.entity.Order;
import com.fadl.supplies.entity.OrderForm;
import com.fadl.supplies.service.OrderService;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 耗材订购记录表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
@Service
@SuppressWarnings("unchecked")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
	@Autowired
	OrderMapper suppliesOrderMapper;
	@Autowired
	OrderFormMapper orderFormMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	SystemSeqService systemSeqService;
	@Autowired
	LogService logService;
	/**
	 * 订单审核列表
	 * @param map
	 * @param pageing
	 * @return
	 */
	@Override
	public Page<DataRow> queryOrderList(Map<String, Object> map, Page<DataRow> pageing) throws Exception {
		return pageing.setRecords(orderMapper.queryOrderList(map, pageing));
	}
	/**
	 * 耗材订购,录入耗材订购记录表、订购单记录
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertOrderform(Map<String,String> map,DataRow messageMap) throws Exception {
		List<Map<String,String>> data = (List<Map<String,String>>) JSONUtils.parse((String) map.get("list"));
		OrderForm orderForm =new OrderForm();
		SystemSeq systemSeq = systemSeqService.queryNewSystemSeqInfo(2);
		orderForm.setOddNumber("DT"+String.valueOf(systemSeq.getValue()));//订单号
		orderForm.setPaymentType(Integer.valueOf(map.get("pay")));//付款方式
		orderForm.setStatus(0);//状态
		orderForm.setRemarks((String)map.get("remarks"));
		orderForm.setSumMoney(new BigDecimal(map.get("total")));
		orderFormMapper.insert(orderForm);//插入订单表
		long depotId=Long.valueOf(map.get("depotId"));//仓库
		List<Order> order = new ArrayList<Order>();
		for(int i=0;i<data.size();i++) {
			Order o =  new Order();
			o.setOrderformId(orderForm.getId());
			o.setDepotId(depotId);
			o.setSuppliesId(Long.valueOf(data.get(i).get("id")));
			o.setNum(Integer.valueOf(data.get(i).get("num")));
			o.setMoney(new BigDecimal(data.get(i).get("money")));
			o.setSumMoney(new BigDecimal(Double.valueOf(data.get(i).get("money"))*Double.valueOf(data.get(i).get("num"))));
			o.setRemarks((String)map.get("remarks"));
			order.add(o);
		}
		this.insertBatch(order);
		//最后插入日志
		logService.insertLog(IConstants.insertOrderform, IConstants.DESC.replace("{0}", "耗材订购,录入耗材订购记录表、订购单记录"),"");
		messageMap.initSuccess("订单生成成功");
	}
}
