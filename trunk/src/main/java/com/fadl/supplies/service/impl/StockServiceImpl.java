package com.fadl.supplies.service.impl;

import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.OrderFormMapper;
import com.fadl.supplies.dao.OrderMapper;
import com.fadl.supplies.dao.StockMapper;
import com.fadl.supplies.entity.Imexport;
import com.fadl.supplies.entity.Order;
import com.fadl.supplies.entity.Stock;
import com.fadl.supplies.service.ImexportService;
import com.fadl.supplies.service.StockService;
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
 * 耗材库存记录 服务实现类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
@Service
@SuppressWarnings("unchecked")
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

	@Autowired
	StockMapper stockMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ImexportService imexportService;
	@Autowired
	OrderFormMapper orderFormMapper;
	@Autowired
	ConfigService configService;
	@Autowired
	ElisaTemplateService etService;
	@Autowired
	LogService logService;
	
	/**
	 * 获取订单入库拥有订单号的耗材(适用报损、退还、销毁、出库)
	 * @param pageing
	 * @return
	 */
	@Override
	public void querySuppliesStatus(Page<DataRow> pageing) throws Exception {
 		 pageing.setRecords(stockMapper.querySuppliesStatus(pageing));
	}
	/**
	 * 查询库房详情列表
	 * @param batchNumber
	 * @param page
	 * @return
	 */
	public Page<DataRow> queryStockDatelis(String id,Page<DataRow> page)throws Exception{
		return page.setRecords(stockMapper.queryStockDatelis(id, page));
	}
	/**
	 * 插入订单信息入库存表
	 * @param suppliesStock
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertStock(String result,DataRow messageMap,List<Order> order)throws Exception {
		List<Map<String,Object>> data =(List<Map<String,Object>>) JSONUtils.parse(result);
		List<Stock> stock= new ArrayList<Stock>();//插入库存表
		List<Imexport> imexport = new ArrayList<Imexport>();//插入库存进出表
		if(null!=data) {
			for(int i=0;i<data.size();i++) {
						EntityWrapper<Stock> eq = new EntityWrapper<Stock>();
						eq.eq("batchNumber",data.get(i).get("batchNumber"));
						Stock stock1 =this.selectOne(eq);
						if(null!=stock1) {
							messageMap.initFial("该批号"+(String) data.get(i).get("batchNumber")+"已存在");
							return;
						}
						Stock s =new Stock();
						s.setOrderId(Long.valueOf((String) data.get(i).get("orderformId")));
						s.setSuppliesId(Long.valueOf((String) data.get(i).get("suppliesId")));
						s.setSurplusNumber(Integer.valueOf((String) data.get(i).get("num")));
						s.setBatchNumber((String) data.get(i).get("batchNumber"));
						s.setExpiryTime((String)data.get(i).get("expiryTime"));
						s.setStartTime((String)data.get(i).get("startTime"));
						s.setEndTime((String)data.get(i).get("endTime"));
						s.setType(((String)data.get(i).get("type")).equals("质控品")?1:0);
						stock.add(s);
						Imexport im = new Imexport();
						im.setSuppliesId(Long.valueOf((String) data.get(i).get("suppliesId")));
						im.setOrderId(Long.valueOf((String) data.get(i).get("orderformId")));
						im.setSum(Integer.valueOf((String) data.get(i).get("num")));
						im.setBatchNumber((String) data.get(i).get("batchNumber"));
						im.setExpiryTime((String)data.get(i).get("expiryTime"));
						imexport.add(im);
						
						Order o = new Order();
						o.setSuppliesId(Long.valueOf((String) data.get(i).get("suppliesId")));
						o.setOrderformId(Long.valueOf((String) data.get(i).get("orderformId")));
						order.add(o);
			}
		}else {
			messageMap.initFial();
			throw new Exception("入库失败");
		}
		this.insertBatch(stock);//插入库存表
		imexportService.insertImexport(imexport);//插入出入表
		//最后插入日志
		logService.insertLog(IConstants.insertStock, IConstants.DESC.replace("{0}", "插入订单信息入库存表"),"");
		messageMap.initSuccess();
		return;
	}
	/**
	 * 库存报警列表
	 * @param map
	 * @param page
	 * @throws Exception
	 */
	@Override
	public void queryPoliceList(Map<String, String> map, Page<DataRow> page) throws Exception {
		page.setRecords(stockMapper.queryPoliceList(map, page));
	}
	/**
	 * 修改库存预警配置
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePolice(Map<String,String> map,DataRow messageMap)throws Exception{
		List<Config> config = new ArrayList<Config>();
		for(int i=0;i<3;i++) {
			Config c= new Config();
			c.setId(Long.valueOf(map.get("redId")));
			c.setLable(map.get("red"));
			config.add(c);
		}
		configService.updatePolice(config);
		messageMap.initSuccess();
	}
	/**
	 * 提供给谢鑫检验查询
	 */
	@Override
	public void queryTestList(Map<String, String> map, Page<DataRow> page) throws Exception {
		page.setRecords(stockMapper.queryTestList(map, page));
	}
	/**
	 * 提供给谢鑫检验查询2
	 * @param map
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	@Override
	public void queryTestPage(Map<String, String> map, DataRow messageMap) throws Exception {
		if("1".equals(map.get("type"))) { // 质控品
			ElisaTemplate et  = etService.selectById(map.get("id"));
			ElisaQc qc = new ElisaQc();
			qc.setCheckProject(et.getProjectId());
			messageMap.initSuccess(baseMapper.queryBatchNumberByQC(qc));
		}else {
			messageMap.initSuccess(stockMapper.queryTestPage(map));
		}
	}
	/**
	 * 盘点查询
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public void queryPointList(Map<String, String> map, Page<DataRow> page) throws Exception {
		page.setRecords(stockMapper.queryPointList(map, page));
	}
	/**
	 * 修改盘点数量
	 * @param stock
	 * @return
	 */
	@Override
	public void updatePoint(Stock stock, DataRow messageMap) throws Exception {
		if(stock.getSurplusNumber()<0) {
			messageMap.initFial("不能为负数,请重新输入");
			return;
		}
		int row = stockMapper.updateById(stock);
		if(row>0){
			//最后插入日志
			logService.insertLog(IConstants.updatePoint, IConstants.DESC.replace("{0}", "修改盘点数量"),"");
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改剩余数量
	 * @param stock
	 * @return
	 */
	@Override
	public void updateSurplusNumber(Stock stock, DataRow messageMap) throws Exception {
		int row = stockMapper.updateById(stock);
		if(row>0){
			//最后插入日志
			logService.insertLog(IConstants.updateSurplusNumber, IConstants.DESC.replace("{0}", "修改入库剩余数量"),"");
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 谢鑫使用的SQL语句
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> queryOutNumberInfo(Page<DataRow> page) throws Exception {
		
		return page.setRecords(stockMapper.queryOutNumberInfo(page));
	}
	
	@Override
	public DataRow queryById(Long id) throws Exception {
		return baseMapper.queryById(id);
	}
}
