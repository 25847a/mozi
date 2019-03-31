package com.fadl.elisa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.elisa.dao.ElisaQcMapper;
import com.fadl.elisa.dao.ElisaTemplateMapper;
import com.fadl.elisa.dao.TestResultMapper;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.TestResult;
import com.fadl.elisa.service.ElisaInfoService;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.elisa.service.TestResultService;
import com.fadl.inspection.dao.TestConfigInfoMapper;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.log.service.LogService;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.supplies.dao.InfoMapper;
import com.fadl.supplies.dao.OutstockMapper;
import com.fadl.supplies.dao.StockMapper;
import com.fadl.supplies.entity.Info;
import com.fadl.supplies.entity.Outstock;
import com.fadl.supplies.entity.Stock;

/**
 * <p>
 * 测试结果 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-10-20
 */
@Service
public class TestResultServiceImpl extends ServiceImpl<TestResultMapper, TestResult> implements TestResultService {
	@Autowired
	private TestResultMapper trMapper;
	@Autowired
	private TestConfigInfoMapper tciMapper;
	@Autowired
	private OutstockMapper osMapper;
	@Autowired
	private StockMapper sMapper;
	@Autowired
	private ElisaQcMapper eqcMapper;
	@Autowired
	private ElisaTemplateMapper etMapper;
	@Autowired
	private InfoMapper infoMapper;
	@Autowired
	private ProviderRegistriesMapper prMapper;
	@Autowired
	private ProviderBaseinfoMapper pbMapper;
	@Autowired
	private ElisaInfoService eiService;
	@Autowired
	private ElisaTemplateService etService;
	@Autowired
	private LogService logService;
	 /**
	 * 得到全自动设备检验记录,并按照实验方法和项目进行分析
	 * @param DataRow dr
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean doAutoElisa(DataRow dr) throws Exception{
		Wrapper<TestResult> ew =  new EntityWrapper<TestResult>();
		// 获取当前时间
		String timeStr = DateUtil.getSystemTime(null);
		ew.orderBy("batchPlate").orderBy("row").orderBy("col");
		// 得到目前需要处理的数据
		List<TestResult> lst = trMapper.selectList(ew);
		if(lst.size()==0) {
			dr.initFial("没有要处理的自动化设备对接记录!");
			return false;
		}
		// 得到所有的板信息
		List<String> plateNos = new ArrayList<String>();
		List<TestResult> trs = new ArrayList<>();
		// 单独每项整理成集合 
		Map<String, List<TestResult>> trMap = new HashMap<>();
		for(TestResult tr : lst) {
			if(!plateNos.contains(tr.getBatchPlate())) {
				plateNos.add(tr.getBatchPlate());
				trs.add(tr);
			}
			if(trMap.get(tr.getBatchPlate()) == null) {
				List<TestResult>  list = new ArrayList<>();
				list.add(tr);
				trMap.put(tr.getBatchPlate(), list);
			}else {
				trMap.get(tr.getBatchPlate()).add(tr);
				// 额外判断,判断是否重复发布了化验结果  目前使用96孔板子, 如果使用其它板子修改为具体数字就好
				if(trMap.get(tr.getBatchPlate()).size()>96) {
					throw new Exception("在全自动机器中重复发布了板号:"+tr.getReagentBatchNo()+"的化验记录!");
				}
			}
		}
		
		for(TestResult tr : trs) {
			// 单独每项获取耗材信息
			Wrapper<Outstock> osew = new EntityWrapper<>();
			osew.eq("batchNumber", tr.getReagentBatchNo());
			osew.orderBy("expiryTime", false);
			List<Outstock> oss = osMapper.selectList(osew);
			Outstock os = new Outstock();
			if(oss!=null && oss.size()>0) {
				os = oss.get(0);
			}else {
				throw new Exception("没有找到批号为:"+tr.getReagentBatchNo()+"的试剂!");
			}
			// 单独每项获取实验配置模板
			Wrapper<TestConfigInfo> tciEW = new EntityWrapper<>();
			tciEW.eq("reagentBatch", os.getId());
			tciEW.eq("del_flag", 0);
			tciEW.le("startDate", timeStr);
			tciEW.gt("endTime", timeStr);
			tciEW.gt("termOfValidity", timeStr);
			osew.orderBy("termOfValidity", false);
			List<TestConfigInfo> tciList = tciMapper.selectList(tciEW);
			TestConfigInfo  tci = new TestConfigInfo();
			if(tciList!=null && tciList.size()>0) {
				tci = tciList.get(0);
			}else {
				throw new Exception("没有找到批号为:"+tr.getReagentBatchNo()+"的试剂绑定的检验配置信息!");
			}
			
			Info qcInfo = new Info();
			
			// 单独每项获取质控品信息
			ElisaQc eqc = new ElisaQc();
			Wrapper<Stock> sew = new EntityWrapper<Stock>();
			sew.eq("batchNumber", tr.getQcBatchNo());
			sew.eq("status", 0);
			sew.eq("type", 1);
			sew.le("startTime", timeStr);
			sew.gt("endTime", timeStr);
			sew.gt("expiryTime", timeStr);
			List<Stock> sList =sMapper.selectList(sew);
			if(sList !=null && sList.size()>0) {
				Stock stock = sList.get(0);
				Wrapper<ElisaQc> qcew = new EntityWrapper<ElisaQc>();
				qcew.eq("stockId", stock.getId());
				qcInfo = infoMapper.selectById(stock.getSuppliesId());
				List<ElisaQc> qcList =  eqcMapper.selectList(qcew);
				if(qcList != null && qcList.size()>0) {
					eqc = qcList.get(0);
				}else {
					throw new Exception("没有找到批号为:"+tr.getReagentBatchNo()+"的质控品绑定的检验配置信息!");
				}
			}else {
				throw new Exception("没有找到批号为:"+tr.getQcBatchNo()+"的质控品信息!");
			}
			// 保存化验记录
			ElisaInfo elisaInfo = new ElisaInfo();
			elisaInfo.setTestConfigInfo(tci);
			//elisaInfo.setQcId(e);
			ElisaTemplate et = new ElisaTemplate();
			et.setReagentId((long)tci.getReagentid());
			et.setQcId(qcInfo.getId());
			et.setIsAuto(1);
			et.setDelFlag(0);
			// 得到指定的酶标读板模板信息
			et = etMapper.selectOne(et);
			if(et == null) {
				throw new Exception("没有找到项目为"+qcInfo.getName()+"酶标读板模板的信息!");
			}
			et = etService.selectById(et.getId());
			elisaInfo.setEtId(et.getId());
			elisaInfo.setElisaTemplate(et);
			List<TestResult> trItems = trMap.get(tr.getBatchPlate());
			// OD值
			String[] values = new String[trItems.size()];
			// 记录位置信息
			String[] tds = new String[trItems.size()]; 
			// 根据A1位是否为特殊孔位来判定是否拼板了.  4为阴性孔
			boolean isCombo = true;
			int icount = 0;
			// 得到实验数据
			int index =0;
			if(et.getProjectId() ==1 ) { // 转氨酶走这里
				for(int i =0,j=12;i<j;i++) {
					for(int k=0,l=8;k<l;k++) {
						TestResult item = trItems.get(k*12+i);
						
		    			values[index] = ""+((int)(item.getOd().floatValue()*10000));
		    			// 拼接 需要的数据     小样号_全登记号_浆员信息ID_血型_浆员姓名
		    			ProviderRegistries pr = new ProviderRegistries();
		    			if(StringUtils.isBlank(item.getSampleNo())) {
		    				pr = null;
		    			}else {
		    				pr.setSampleNo(Long.valueOf(item.getSampleNo()));
		    				pr = prMapper.selectOne(pr);
		    			}
		    			if(pr != null) { // 有浆员信息
		    				ProviderBaseinfo pbInfo = new ProviderBaseinfo();
		    				pbInfo.setProviderNo(pr.getProviderNo());
		    				pbInfo = pbMapper.selectOne(pbInfo);
		    				tds[index] = pr.getSampleNo()+"_"+pr.getAllId()+"_"+pr.getProviderNo()+"_"+pbInfo.getBloodType()+"_"+pbInfo.getName();
		    			}else {  // 没有浆员信息
		    				tds[index] = item.getSampleNo()+"_-1_-1_-1_-1";
		    			}
		    			index++;
					}
				}
			}else {
				for(int i =0,j=12;i<j;i++) {
					for(int k=0,l=8;k<l;k++) {
						TestResult item = trItems.get(k*12+i);
						// 循环找第一个阴性孔,找到为止
						if(item.getHoleType()!=4&&isCombo) { 
		    				icount++;
		    				continue;
		    			}
						isCombo =false;
		    			values[index] = ""+((int)(item.getOd().floatValue()*10000));
		    			// 拼接 需要的数据     小样号_全登记号_浆员信息ID_血型_浆员姓名
		    			ProviderRegistries pr = new ProviderRegistries();
		    			if(StringUtils.isBlank(item.getSampleNo())) {
		    				pr = null;
		    			}else {
		    				pr.setSampleNo(Long.valueOf(item.getSampleNo()));
		    				pr = prMapper.selectOne(pr);
		    			}
		    			if(pr != null) { // 有浆员信息
		    				ProviderBaseinfo pbInfo = new ProviderBaseinfo();
		    				pbInfo.setProviderNo(pr.getProviderNo());
		    				pbInfo = pbMapper.selectOne(pbInfo);
		    				tds[index] = pr.getSampleNo()+"_"+pr.getAllId()+"_"+pr.getProviderNo()+"_"+pbInfo.getBloodType()+"_"+pbInfo.getName();
		    			}else {  // 没有浆员信息
		    				tds[index] = item.getSampleNo()+"_-1_-1_-1_-1";
		    			}
		    			index++;
					}
				}
			}
			
    		
			for(int i = tds.length-icount;i<96;i++) {
				tds[i] =  "-1_-1_-1_-1_-1";
				values[i] = "0";
			}
    		Map<String,String> map = new HashMap<>();
    		map.put("tds", StringUtils.join(tds,","));
    		map.put("qcId", ""+eqc.getStockId());
    		map.put("reagentId", ""+tci.getReagentBatch());
    		map.put("creater", dr.getString("creater"));
    		map.put("testDate", tr.getTestDate());
    		// 本步骤无问题,需要设置为成功.后面质控需要判定
    		dr.initSuccess();
    		try {
    			eiService.doInsertEi(map, tci, et, values, dr);
    		}catch (Exception e) {
    			// 如果要求批量处理可以提交有效数据,则这里不再抛出异常即可.需要考虑不合格微板如何提示.不保证只有一个微板不合格.
    			throw new Exception("微孔板号:"+ tr.getBatchPlate()+" 的"+e.getMessage());
			}
    		Wrapper<TestResult> delew = new EntityWrapper<>();
    		delew.eq("batchPlate", tr.getBatchPlate());
    		trMapper.delete(delew);
    		dr.put("size", plateNos.size());
    		logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "操作了自动化酶免设备的实验记录,当前处理的板号为:"+tr.getBatchPlate()),"null");
		}
		return  true;
	}
}
