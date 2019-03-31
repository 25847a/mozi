package com.fadl.supplies.service.impl;

import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.ReturnMapper;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.entity.Return;
import com.fadl.supplies.entity.Stream;
import com.fadl.supplies.service.ReturnService;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 耗材退还记录表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
@Service
@SuppressWarnings("unchecked")
public class ReturnServiceImpl extends ServiceImpl<ReturnMapper, Return> implements ReturnService {

	@Autowired
	ReturnMapper returnMapper;
	@Autowired
	SystemSeqService systemSeqService;
	@Autowired
	StreamMapper streamMapper;
	@Autowired
	LogService logService;
	/**
	 * 耗材退还首页列表
	 * @param pageing
	 * @param retur
	 * @throws Exception
	 */
	@Override
	public void queryReturnList(Page<DataRow> pageing, Return retur) throws Exception {
		pageing.setRecords(returnMapper.queryReturnList(pageing, retur));
	}
	/**
	 * 详情页面
	 * @param pageing
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void queryReturnDatelis(Page<DataRow> pageing, Map<String, String> map) throws Exception {
		pageing.setRecords(returnMapper.queryReturnDatelis(pageing, map));
	}
	/**
	 * 获取退订耗材存入退还表
	 * @param date
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertReturn(Map<String,String> map,DataRow messageMap) throws Exception {
		List<Map<String,String>> data = (List<Map<String, String>>) JSONUtils.parse((String) map.get("list"));
		List<Return> ruter = new ArrayList<Return>();
		SystemSeq systemSeq=systemSeqService.queryNewSystemSeqInfo(3);
		//先判断是否够扣库存的数量
		for(int i=0;i<data.size();i++) {
			Return r = new Return();
			int num = streamMapper.queryStreamNum(data.get(i));
			if(Integer.valueOf(String.valueOf(data.get(i).get("num")))>num) {
				messageMap.initFial("退还数量大于库存数量,请重新修改订单");
				return;
			}else {
				System.out.println(data.get(i));
				streamMapper.updateStream(data.get(i));
				//出库表使用到
			//	stockMapper.updateStockNum(Integer.valueOf(String.valueOf(data.get(i).get("num"))),
			//			String.valueOf(data.get(i).get("id")),Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			//	Stock stock = new Stock();
			//	stock.setId(Long.valueOf(data.get(i).get("id")));
			//	stock.setLockingNumber(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
			//	stockMapper.updateById(stock);
				Stream Stream = new Stream();
				Stream.setId(Long.valueOf(data.get(i).get("id")));
				Stream.setLockingNumber(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
				streamMapper.updateById(Stream);
			}
			r.setBatchNumber(String.valueOf(data.get(i).get("batchNumber")));
			r.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			r.setReturnOrder("TH"+systemSeq.getValue());
			r.setReason(map.get("reason"));
			r.setStatus(0);
			r.setNum(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
			ruter.add(r);
		}
		this.insertBatch(ruter);
		//最后插入日志
		logService.insertLog(IConstants.insertReturn, IConstants.DESC.replace("{0}", "耗材退还操作"),"");
		messageMap.initSuccess();
	}
	/**
	 * 修改退还订单信息
	 * @param retur
	 * @return
	 */
	@Override
	public void updateReturn(Return retur, DataRow messageMap) throws Exception {
		EntityWrapper<Return> ew = new EntityWrapper<Return>();
		ew.eq("returnOrder", retur.getReturnOrder());
		this.update(retur, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateReturn, IConstants.DESC.replace("{0}", "修改退还订单信息"),"");
		messageMap.initSuccess();
	}
	/**
	 * 审核批准、库存表耗材改变状态
	 */
	public void updateReturnStatus(Return retur,DataRow messageMap)throws Exception{
		EntityWrapper<Return> ew = new EntityWrapper<Return>();
		ew.eq("returnOrder", retur.getReturnOrder());
		if(retur.getStatus()==0){
			retur.setStatus(1);
		} else {
			retur.setStatus(0);
		}
		this.update(retur, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateReturnStatus, IConstants.DESC.replace("{0}", "退还订单审核批准"),"");
		messageMap.initSuccess();
	}
}
