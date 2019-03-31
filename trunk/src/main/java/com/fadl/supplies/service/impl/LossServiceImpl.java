package com.fadl.supplies.service.impl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.LossMapper;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.entity.Loss;
import com.fadl.supplies.entity.Stream;
import com.fadl.supplies.service.LossService;
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
 * 耗材报损记录表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
@Service
@SuppressWarnings("unchecked")
public class LossServiceImpl extends ServiceImpl<LossMapper, Loss> implements LossService {

	@Autowired
	LossMapper lossMapper;
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
	public void queryLossList(Page<DataRow> pageing, Loss loss) throws Exception {
		pageing.setRecords(lossMapper.queryLossList(pageing, loss));
	}
	/**
	 * 详情页面
	 * @param pageing
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void queryLossDatelis(Page<DataRow> pageing, Map<String, String> map) throws Exception {
		pageing.setRecords(lossMapper.queryLossDatelis(pageing, map));
	}
	/**
	 * 获取报损耗材存入退还表
	 * @param date
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertLoss(Map<String,String> map,DataRow messageMap) throws Exception {
		List<Map<String,String>> data = (List<Map<String,String>>) JSONUtils.parse((String) map.get("list"));
		List<Loss> loss = new ArrayList<Loss>();
		SystemSeq systemSeq=systemSeqService.queryNewSystemSeqInfo(4);
		//先判断是否够扣库存的数量
		for(int i=0;i<data.size();i++) {
			Loss l = new Loss();
			int num = streamMapper.queryStreamNum(data.get(i));
			if(Integer.valueOf(String.valueOf(data.get(i).get("num")))>num) {
				messageMap.initFial("报损数量大于库存数量,请重新修改订单");
				return;
			}else {
				Stream Stream = new Stream();
				Stream.setId(Long.valueOf(data.get(i).get("id")));
				Stream.setLockingNumber(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
				streamMapper.updateById(Stream);
			}
			l.setBatchNumber(String.valueOf(data.get(i).get("batchNumber")));
			l.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			l.setDamageOrder("BS"+systemSeq.getValue());
			l.setReason(map.get("reason"));
			l.setStatus(0);
			l.setNum(Integer.valueOf(String.valueOf(data.get(i).get("num"))));
			loss.add(l);
		}
		this.insertBatch(loss);
		//最后插入日志
		logService.insertLog(IConstants.insertLoss, IConstants.DESC.replace("{0}", "耗材报损操作"),"");
		messageMap.initSuccess();
	}
	/**
	 * 修改退还订单信息
	 * @param retur
	 * @return
	 */
	@Override
	public void updateLoss(Loss loss, DataRow messageMap) throws Exception {
		EntityWrapper<Loss> ew = new EntityWrapper<Loss>();
		ew.eq("damageOrder", loss.getDamageOrder());
		this.update(loss, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateLoss, IConstants.DESC.replace("{0}", "耗材报损操作"),"");
		messageMap.initSuccess();
	}
	/**
	 * 审核批准、库存表耗材改变状态
	 */
	public void updateLossStatus(Loss loss,DataRow messageMap)throws Exception{
		EntityWrapper<Loss> ew = new EntityWrapper<Loss>();
		ew.eq("damageOrder", loss.getDamageOrder());
		if(loss.getStatus()==0){
			loss.setStatus(1);
		} else {
			loss.setStatus(0);
		}
		this.update(loss, ew);
		//最后插入日志
		logService.insertLog(IConstants.updateLossStatus, IConstants.DESC.replace("{0}", "耗材报损操作"),"");
		messageMap.initSuccess();
	}
}
