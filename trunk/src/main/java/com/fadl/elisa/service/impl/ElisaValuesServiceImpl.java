package com.fadl.elisa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.dao.ConfigMapper;
import com.fadl.common.entity.Config;
import com.fadl.elisa.dao.ElisaValuesMapper;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaValues;
import com.fadl.elisa.service.ElisaValuesService;
import com.fadl.inspection.dao.NewCardMapper;
import com.fadl.inspection.dao.TestConfigInfoMapper;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.log.service.LogService;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.entity.ProviderRegistries;

/**
 * <p>
 * 酶标仪检测记录表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Service
public class ElisaValuesServiceImpl extends ServiceImpl<ElisaValuesMapper, ElisaValues> implements ElisaValuesService {
	@Autowired
	private NewCardMapper newCardMapper;
	@Autowired
	private RefuseInfoMapper rfMapper;
	@Autowired
	private TestConfigInfoMapper tciMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private ProviderRegistriesMapper prMapper;
	@Autowired
	private LogService logService;
	/**
	 * 发布酶标仪检测信息 批量发布 
	 * @param ei
	 * @param evids
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean releaseElisaValue(ElisaInfo ei, String evids, String type) throws Exception {
		Wrapper<TestConfigInfo> ew = new EntityWrapper<TestConfigInfo>();
		ew.eq("reagentBatch", ei.getReagentId());
		ew.eq("del_flag", 0);
		ew.le("startDate", new Date());
		ew.ge("endTime", new Date());
		List<TestConfigInfo> tcis = tciMapper.selectList(ew);
		if(tcis==null || tcis.size()==0) {
			throw new Exception("没有试剂信息!");
		}
		TestConfigInfo tci = tcis.get(0);
		int testProjectName = Integer.parseInt( tci.getProjectName());
		boolean isRelease = true;
		if("1".equals(type)) { // 判断是发布还是取消发布
			isRelease = false;
		}
		List<ElisaValues> evs = new ArrayList<ElisaValues>();
		String [] evidArray = evids.split(",");
		for(String  evid : evidArray) {
			NewCard card = new NewCard();
			ElisaValues ev = baseMapper.selectById(evid);
			if(ev.getSampleType() ==1) { // 复检 则更新 // 
				card.setRechecked(1);
			}
			evs.add(ev);
			ev.setReportStatus(isRelease?1:0);
			try {
				card.setSampleNo(Long.parseLong(ev.getSampleNo()));
				card.setRechecked(null);
			}catch (Exception e) {
				continue;
			}
			card = newCardMapper.selectOne(card);
			if(card== null) {
				throw new Exception("没有该登记浆员信息");
			}
			Config entity = new Config();
			entity.setLable("maxHour");
			entity.setType("check_off_time");
			entity= configMapper.selectOne(entity);
			// 得到检验有效小时数
			int diffHour =  Integer.valueOf(entity.getValue());
			// 判断是否过了设定的小时数
			Date createDate =  DateUtil.sf.parse(card.getCreateDate());
			// 得到与当前时间的差额
			long diffTime = (new Date()).getTime()- createDate.getTime();
			// 计算差额具体数值
		    long hour = diffTime % 86400000 /3600000;
			if(hour > diffHour) {
				throw new Exception("小样号为:"+card.getSampleNo()+"的采样时间已经过了"+diffHour+"小时,不能发布结果.");
			}
			String odValue = ""+ev.getJudgementResult();
			String eliminateReason = "";
			switch (testProjectName) {
			case 5: // 梅毒
				card.setSyphilis(odValue);
				eliminateReason="梅毒检测";
				break;
			case 1: // 转氨酶 ALT
				card.setAlt("");
				if(97<=ev.getValue().doubleValue()) {
					card.setAlt("3");
					odValue =">=97";
				}else if(57<=ev.getValue().doubleValue()) {
					card.setAlt("2");
					odValue =">=57";
				}else if(25<ev.getValue().doubleValue()) {
					card.setAlt("1");
					odValue =">25";
				}else if(25>=ev.getValue().doubleValue()) {
					card.setAlt("0");
					odValue ="<=25";
				}
				eliminateReason="(ALT)转氨酶检测";
				
				break;
			case 2: // 乙肝 HBsAg
				card.setHbsag(odValue);
				eliminateReason="乙肝检测";
				break;
			case 3: // 丙肝 HCV
				card.setHcv(odValue);
				eliminateReason="丙肝检测";
				break;
			case 4: // 艾滋 HIV
				card.setHiv(odValue);
				eliminateReason="艾滋检测";
				break;
			default:
				break;
			}
			if(ev.getReportStatus() ==1) {
				logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "发布浆员卡号:"+card.getProviderNo()+"的"+eliminateReason+"的化验记录,结果为:"+(ev.getJudgementResult()==0?"合格":ev.getJudgementResult()==1?"不合格":"灰区")),"null");
			}else {
				logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "取消发布浆员卡号:"+card.getProviderNo()+"的"+eliminateReason+"的化验记录,结果为:"+(ev.getJudgementResult()==0?"合格":ev.getJudgementResult()==1?"不合格":"灰区")),"null");
			}
			if(ev.getJudgementResult()==1) { // 发布拒绝信息
				ProviderRegistries pr = new ProviderRegistries();
				Wrapper<ProviderRegistries> prew =  new EntityWrapper<ProviderRegistries>();
				prew.eq("providerNo", ev.getPbId()).eq("sampleNo", ev.getSampleNo());
				List<ProviderRegistries> lst = prMapper.selectList(prew);
				if(lst.size()>0) {
					pr = lst.get(0);
				}else {
					continue;
				}
				card.setResult(ev.getJudgementResult());
				RefuseInfo info = new RefuseInfo();
				info.setEvId(ev.getId());
				info = rfMapper.selectOne(info);
				if(isRelease) { // 如果是发布信息走这里
				// 无法判定具体拒绝的信息,只能新增
				info = new RefuseInfo();
				info.setEvId(ev.getId());
				info.setAllId(pr.getAllId());
				info.setType(1);
				if("1".equals(odValue)) {
					odValue = "阳性";
				}
				info.setEliminateReason(eliminateReason+"为:"+odValue +" 检测结果:不合格");
				info.setProviderNo(""+ev.getPbId());
				// rfMapper.insert(info); // 保存拒绝信息
					
				}else { // 取消发布走这里
					if(info !=null && info.getIsRefuse()==0) { // 如果还没有发布出来则可以删除
						rfMapper.deleteById(info.getId());
					}else {
						if(info !=null && info.getIsRefuse()==1) {
							throw new Exception("拒绝信息已经正式发布,不能取消");
						}
					}
				}
			}else {
				if(card.getResult() == null) {
					card.setResult(1);
				}
				if(card.getResult()!=1||ev.getSampleType()==1) {  //  如果等于1 为不合格,不允许修改, 在其它地方进行修改 如果是复检则允许修改
					card.setResult(ev.getJudgementResult());
				}
			}
			
			newCardMapper.updateById(card); // 保存 新老卡化验结果
			ev.setNewCardId(card.getId());
			ev.setAllId(card.getAllId());
		}
		return updateBatchById(evs);// 批量更新酶标板发布信息
	}
	
	
	/**
	 * 根据小样号查询检测记录
	 * @param sampleNos
	 * @return
	 */
	public List<ElisaValues> selectBysampleNos(String[] sampleNos){
		Object[] objs = ifRepeat(sampleNos);
		String ss="";
		for(Object obj :objs) {
			ss+="'"+(obj==null||obj.equals("null")?"":obj.toString())+"',";
		}
		if(ss.length()>1) {
			ss= ss.substring(0, ss.length()-1);
		}
		return baseMapper.selectBysampleNos(ss);
	}
	
	 private static Object[] ifRepeat(Object[] arr){  
	        //实例化一个set集合  
	        Set<Object> set = new HashSet<Object>();  
	        //遍历数组并存入集合,如果元素已存在则不会重复存入  
	        for (int i = 0; i < arr.length; i++) {  
	            set.add(arr[i]);  
	        }  
	        //返回Set集合的数组形式  
	        return set.toArray();  
	    }  

}
