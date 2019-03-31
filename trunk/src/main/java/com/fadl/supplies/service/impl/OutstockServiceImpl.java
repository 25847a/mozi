package com.fadl.supplies.service.impl;

import com.fadl.supplies.entity.Imexport;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.entity.Stream;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.log.service.LogService;
import com.fadl.supplies.dao.OutstockMapper;
import com.fadl.supplies.dao.StockMapper;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.service.ImexportService;
import com.fadl.supplies.service.OutstockService;
import com.fadl.supplies.service.StreamService;
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
 * 出库表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-05-28
 */
@Service
@SuppressWarnings("unchecked")
public class OutstockServiceImpl extends ServiceImpl<OutstockMapper, Outstock> implements OutstockService {

	@Autowired
	OutstockMapper outstockMapper;
	@Autowired
	StockMapper stockMapper;
	@Autowired
	StreamService streamService;
	@Autowired
	ImexportService imexportService;
	@Autowired
	StreamMapper streamMapper;
	@Autowired
	ElisaTemplateService etService;
	@Autowired
	LogService logService;
	/**
	 * 出库列表
	 */
	@Override
	public void queryOutstockList(Page<DataRow> pageing, Map<String, Object> map) throws Exception {
		pageing.setRecords(outstockMapper.queryoutstockList(pageing, map));
	}
	/**
	 * 插入出库表
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertOutstock(Map<String, String> map, DataRow messageMap) throws Exception {
		List<Map<String,String>> data = (List<Map<String,String>>) JSONUtils.parse((String) map.get("list"));
		List<Outstock> outstock = new ArrayList<Outstock>();
		List<Imexport> imexport = new ArrayList<Imexport>();
		List<Stream> stream = new ArrayList<Stream>();
		for(int i=0;i<data.size();i++) {
			//入库表要减少耗材
			stockMapper.updateStockNum(Integer.valueOf(String.valueOf(data.get(i).get("outNumber"))),String.valueOf(data.get(i).get("id")),Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			//存入出库表
			Outstock o = new Outstock();
			o.setOrderId(Long.valueOf(String.valueOf(data.get(i).get("orderId"))));
			o.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			o.setOutNumber(Integer.valueOf(String.valueOf(data.get(i).get("outNumber")))*-1);
			o.setBatchNumber(data.get(i).get("batchNumber"));
			o.setExpiryTime(String.valueOf(data.get(i).get("expiryTime")));
			o.setRemarks(String.valueOf(data.get(i).get("remarks")));
			o.setStockId(Long.valueOf(data.get(i).get("id")));
			outstock.add(o);
			//存入库存进出表
			Imexport im = new Imexport();
			im.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			im.setOrderId(Long.valueOf(String.valueOf(data.get(i).get("orderId"))));
			im.setSum(Integer.valueOf(String.valueOf(data.get(i).get("outNumber")))*-1);
			im.setBatchNumber(data.get(i).get("batchNumber"));
			im.setExpiryTime(String.valueOf(data.get(i).get("expiryTime")));
			imexport.add(im);
			//存入流水表
			EntityWrapper<Stream> ew = new EntityWrapper<Stream>();
			ew.eq("suppliesId", String.valueOf(data.get(i).get("suppliesId")));
			ew.eq("batchNumber", data.get(i).get("batchNumber"));
			Stream str=streamService.selectOne(ew);
			if(null!=str) {
				streamMapper.AddStreamNum(Long.valueOf(String.valueOf(data.get(i).get("outNumber"))), data.get(i).get("batchNumber"),Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
			}else {
				Stream s = new Stream();
				s.setSuppliesId(Long.valueOf(String.valueOf(data.get(i).get("suppliesId"))));
				s.setOrderId(Long.valueOf(String.valueOf(data.get(i).get("orderId"))));
				s.setBatchNumber(data.get(i).get("batchNumber"));
				s.setSum(Long.valueOf(String.valueOf(data.get(i).get("outNumber"))));
				s.setExpiryTime(String.valueOf(data.get(i).get("expiryTime")));
				stream.add(s);
			}
		}
		this.insertBatch(outstock);//插入到出库表
		if(!stream.isEmpty()) {
			streamService.insertStream(stream);//插入到流水表
		}
		imexportService.insertImexport(imexport);//插入到出入表
		//最后插入日志
		logService.insertLog(IConstants.insertOutstock, IConstants.DESC.replace("{0}", "插入出库表"),"");
		messageMap.initSuccess();
	}
	
	/**
	 * 提供给谢鑫检验查询
	 */
	@Override
	public void queryTestList(Map<String, String> map, Page<DataRow> page) throws Exception {
		map.put("isDisable", "0");
		page.setRecords(baseMapper.queryTestList(map, page));
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
			messageMap.initSuccess(stockMapper.queryBatchNumberByQC(qc));
		}else {
			messageMap.initSuccess(baseMapper.queryTestPage(map));
		}
	}
	/**
	 * 提供给免疫查询的列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataRow> queryVaccineList() throws Exception {
		return outstockMapper.queryVaccineList();
	}
	/**
	 * 根据ID查询信息,包含有库存表的信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryById(Long id) throws Exception {
		return baseMapper.queryById(id);
	}
	/**
	 * 查询免疫查询的批号列表
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void queryVaccineBatchNumber(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow>list =outstockMapper.queryVaccineBatchNumber(map);
		if(!list.isEmpty()) {
			messageMap.initSuccess(list);
		}else {
			messageMap.initError();
		}
	}
}
