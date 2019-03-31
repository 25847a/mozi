package com.fadl.supplies.service.impl;
 

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.OrderFormMapper;
import com.fadl.supplies.entity.OrderForm;
import com.fadl.supplies.service.OrderFormService;

/**
 * <p>
 * 订购单记录 服务实现类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
@Service
public class OrderFormServiceImpl extends ServiceImpl<OrderFormMapper, OrderForm> implements OrderFormService {

	@Autowired
	OrderFormMapper orderFormMapper;
	@Autowired
	LogService logService;
	/**
	 * 审批完成,更改订购单记录表的订单状态,2.已完成
	 * @param orderForm
	 * @return
	 */
	@Override
	public void updateBuyStatus(OrderForm orderForm,DataRow messageMap,Long id) throws Exception {
			if(orderForm.getStatus()==1) {
				orderForm.setStatus(2);
			}else {
				orderForm.setStatus(1);
			}
			orderForm.setIntoStockDate(DateUtil.sf.format(new Date()));
			orderForm.setIntoStocker(id);
			EntityWrapper<OrderForm> ew = new EntityWrapper<OrderForm>();
			ew.eq("id", orderForm.getId());
			orderFormMapper.update(orderForm, ew);
			//最后插入日志
			logService.insertLog(IConstants.updateBuyStatus, IConstants.DESC.replace("{0}", "待检验、已完成,更改订购单记录表的订单状态"),"");
			messageMap.initSuccess();
	}
	/**
	 * 入库根据订单号查询耗材
	 * @return
	 */
	@Override
	public void querySuppliesInfo(Long order,DataRow messageMap) throws Exception {
		List<DataRow> list = orderFormMapper.querySuppliesInfo(order,null);
		if(null!=list) {
			messageMap.initSuccess(list);
		}
			messageMap.initFial();
	}
	/**
	 * 订购列表接口
	 * @param orderForm
	 * @param pageing
	 * @return
	 * @throws Exception
	 */
	public void queryOrder(OrderForm orderForm,Page<DataRow> pageing)throws Exception{
		pageing.setRecords(orderFormMapper.queryOrder(orderForm, pageing));
	}
	/**
	 * 查询详情页面列表
	 * @param orderformId
	 * @return
	 */
	public Page<DataRow> queryDatelisList(String order,Page<DataRow> pageing)throws Exception{
		return pageing.setRecords(orderFormMapper.querySuppliesInfo(Long.valueOf(order), pageing));
		
	}
	/**
	 * 查看检验信息列表
	 * @param quarantineData
	 * @param rowBounds
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow>  queryQuarantineList(Map<String, Object> quarantineData,Page<DataRow> page)throws Exception{
		return page.setRecords(orderFormMapper.queryQuarantineList(quarantineData, page));
	}
	/**
	 * 订单作废,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	@Override
	public void UselessStatus(List<OrderForm> result,DataRow messageMap) throws Exception {
		for(OrderForm orderForm:result) {
			orderForm.setStatus(3);
			orderFormMapper.updateAllColumnById(orderForm);
		}
		//最后插入日志
		logService.insertLog(IConstants.UselessStatus, IConstants.DESC.replace("{0}", "订单作废,更改订购单记录表的订单状态"),"");
		messageMap.initSuccess();
	}
	/**
	 * 订单删除
	 * @param orderForm
	 * @return
	 */
	public DataRow delectOrder(OrderForm order,DataRow messageMap)throws Exception{
			if(order.getStatus()==2 | order.getStatus()==4) {
				messageMap.initSuccess("订单已完成或者入库,不能删除");
				return messageMap;
			}
			order.setStatus(3);
			int result =orderFormMapper.updateById(order);
			if(result>0) {
				//最后插入日志
				logService.insertLog(IConstants.delectOrder, IConstants.DESC.replace("{0}", "订单删除"),"");
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
			return messageMap;
	}
	/**
	 * 订单修改
	 * @param orderForm
	 * @return
	 */
	public void updateOrder(OrderForm orderForm,DataRow messageMap)throws Exception{
		int row =orderFormMapper.updateById(orderForm);
		if(row>0) {
			//最后插入日志
			logService.insertLog(IConstants.updateOrder, IConstants.DESC.replace("{0}", "订单修改"),"");
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 待审批、待检验,更改订购单记录表的订单状态
	 * @param orderForm
	 * @return
	 */
	@Override
	public void updateStatus(OrderForm orderForm,DataRow messageMap,Long id) throws Exception {
			if(orderForm.getStatus()==0) {
				orderForm.setStatus(1);
			}else {
				orderForm.setStatus(0);
			}
			orderForm.setQuarantineDate(DateUtil.sf.format(new Date()));
			orderForm.setQuarantiner(id);
			EntityWrapper<OrderForm> ew = new EntityWrapper<OrderForm>();
			ew.eq("id", orderForm.getId());
			orderFormMapper.update(orderForm, ew);
			//最后插入日志
			logService.insertLog(IConstants.updateStatus, IConstants.DESC.replace("{0}", "待审批、待检验,更改订购单记录表的订单状态"),"");
			messageMap.initSuccess();
	}
	/**
	 * 库存入库时耗材添加入库使用的接口
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> queryOrderFormPage(Page<DataRow> page) throws Exception {
		return page.setRecords(orderFormMapper.queryOrderFormPage(page));
	}
}
