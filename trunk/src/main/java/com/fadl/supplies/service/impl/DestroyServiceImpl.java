package com.fadl.supplies.service.impl;

import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.DestroyMapper;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.entity.Destroy;
import com.fadl.supplies.entity.Stream;
import com.fadl.supplies.service.DestroyService;
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
 * 耗材销毁信息表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-05-03
 */
@Service
@SuppressWarnings("unchecked")
public class DestroyServiceImpl extends ServiceImpl<DestroyMapper, Destroy> implements DestroyService {
	@Autowired
	DestroyMapper destroyMapper;
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
	public void queryDestroyList(Page<DataRow> pageing,Destroy destroy) throws Exception {
		pageing.setRecords(destroyMapper.queryDestroyList(pageing, destroy));
	}
	/**
	 * 详情页面
	 * @param pageing
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void queryDestroyDatelis(Page<DataRow> pageing, Map<String, String> map) throws Exception {
		pageing.setRecords(destroyMapper.queryDestroyDatelis(pageing, map));
	}
	/**
	 * 获取销毁耗材存入退还表
	 * @param map
	 * @param messageMap
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertDestroy(Map<String,String> map,DataRow messageMap) throws Exception {
		List<Map<String,String>> data =(List<Map<String,String>>) JSONUtils.parse((String) map.get("list"));
		List<Destroy> destroy = new ArrayList<Destroy>();
		SystemSeq systemSeq=systemSeqService.queryNewSystemSeqInfo(5);
		//先判断是否够扣库存的数量
		for(int i=0;i<data.size();i++) {
			Destroy d= new Destroy();
			int num = streamMapper.queryStreamNum(data.get(i));
			if(Integer.valueOf(String.valueOf(data.get(i).get("num")))>num) {
				messageMap.initFial("销毁数量大于库存数量,请重新修改订单");
				return;
			}else {
				Stream Stream = new Stream();
				Stream.setId(Long.valueOf(data.get(i).get("id")));
				Stream.setLockingNumber(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
				streamMapper.updateById(Stream);
			}
			d.setBatchNumber(String.valueOf(data.get(i).get("batchNumber")));
			d.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			d.setDestroyOrder("XH"+systemSeq.getValue());
			d.setReason(map.get("reason"));
			d.setStatus(0);
			d.setNum(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
			destroy.add(d);
		}
		this.insertBatch(destroy);
		//最后插入日志
		logService.insertLog(IConstants.insertDestroy, IConstants.DESC.replace("{0}", "耗材销毁操作"),"");
		messageMap.initSuccess();
	}
	/**
	 * 修改销毁订单信息
	 * @param retur
	 * @return
	 */
	@Override
	public void updateDestroy(Destroy destroy, DataRow messageMap) throws Exception {
		EntityWrapper<Destroy> ew = new EntityWrapper<Destroy>();
		ew.eq("destroyOrder", destroy.getDestroyOrder());
		this.update(destroy, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateDestroy, IConstants.DESC.replace("{0}", "耗材销毁操作"),"");
		messageMap.initSuccess();
	}
	/**
	 * 修改销毁订单审核状态
	 */
	public void updateDestroyStatus(Destroy destroy, DataRow messageMap) throws Exception{
		EntityWrapper<Destroy> ew = new EntityWrapper<Destroy>();
		ew.eq("destroyOrder", destroy.getDestroyOrder());
		if(destroy.getStatus()==0){
			destroy.setStatus(1);
		} else {
			destroy.setStatus(0);
		}
		this.update(destroy, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateDestroyStatus, IConstants.DESC.replace("{0}", "耗材销毁操作"),"");
		messageMap.initSuccess();
	}
}
