package com.fadl.elisa.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.MathUtils;
import com.fadl.common.PolynomialRegression;
import com.fadl.common.StandardDeviation;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.elisa.dao.ElisaInfoMapper;
import com.fadl.elisa.dao.ElisaValuesMapper;
import com.fadl.elisa.entity.ElisaCommonSetting;
import com.fadl.elisa.entity.ElisaGrayAreaSettings;
import com.fadl.elisa.entity.ElisaIiqc;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaMeasurementMethod;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.elisa.entity.ElisaTemplateValues;
import com.fadl.elisa.entity.ElisaValues;
import com.fadl.elisa.service.ElisaIiqcService;
import com.fadl.elisa.service.ElisaInfoService;
import com.fadl.elisa.service.ElisaTemplateService;
import com.fadl.elisa.service.ElisaValuesService;
import com.fadl.inspection.dao.TestConfigInfoMapper;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.log.service.LogService;

/**
 * <p>
 * 酶标板检测信息 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@Service
public class ElisaInfoServiceImpl extends ServiceImpl<ElisaInfoMapper, ElisaInfo> implements ElisaInfoService {
	@Autowired
	private ElisaValuesMapper evMapper;
	@Autowired
	private ElisaValuesService evService;
	@Autowired
	private ElisaTemplateService etlService;
	@Autowired
	private TestConfigInfoMapper tciMapper;
	@Autowired
	private ElisaIiqcService iiqcService;
	@Autowired
	private ConfigService cService;
	@Autowired
	public LogService logService;
	
	// nc 阴性值   pc 阳性值
	private Integer nc =0 , pc=0;
	/**
	 * 保存酶标仪检测记录
	 * 
	 * @param elisaInfo
	 * @param doInsertQC
	 *            是否保存质控记录 
	 * @param isElisa
	 *            是否酶联检测法
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean insertAll(ElisaInfo elisaInfo, Boolean doInsertQC, Boolean isElisa) throws Exception {

		// 先保存信息表
		baseMapper.insert(elisaInfo);
		Long eiId = elisaInfo.getId();
		try {
			logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "保存了酶标板化验结果 ID 为:"+eiId),"null");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 批量更新孔位中的infoId
		List<ElisaValues> evLists = elisaInfo.getElisaValues();
		for (ElisaValues ev : evLists) {
			ev.setEiId(eiId);
			if (ev.getType() == 5 && doInsertQC) { // 如果是质检孔,则要进行即控操作
				double[] n2s = { 1.15, 1.46, 1.67, 1.82, 1.94, 2.03, 2.11, 2.18, 2.23, 2.29, 2.33, 2.37, 2.41, 2.44,
						2.47, 2.50, 2.53, 2.56 };
				double[] n3s = { 1.16, 1.49, 1.75, 1.94, 2.10, 2.22, 2.32, 2.41, 2.48, 2.55, 2.61, 2.66, 2.71, 2.75,
						2.79, 2.82, 2.85, 2.88 };
				List<ElisaIiqc> lists = iiqcService.findByTciidAndQcId(elisaInfo.getTciId(), elisaInfo.getQcId());
				if (lists.size() < 2) { // 如果数据不够运算条件则先只保存数据
					ElisaIiqc eii = new ElisaIiqc();
					eii.setTciid(elisaInfo.getTciId());
					eii.setEiId(elisaInfo.getId());
					eii.setOpAdmin(elisaInfo.getOpAdmin());
					eii.setCutOffvalue(elisaInfo.getCutOffValue());
					eii.setOdValue(ev.getOdValue());
					eii.setsDivideCO(new BigDecimal( MathUtils.divide(ev.getOdValue().doubleValue(), elisaInfo.getCutOffValue().doubleValue(), 4)));
					eii.setAltValue(ev.getValue());
					eii.setQcId(elisaInfo.getQcId());
					
					iiqcService.insert(eii);
					try {
						logService.insertLog(IConstants.MODULE_QC, IConstants.DESC.replace("{0}", "保存了酶标板化验结果 ID 为:"+eiId+"的质控记录,当前次数还不够计算质控"),"null");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else { // 超过2次则要做即控操作了
					ElisaIiqc eii = new ElisaIiqc();
					// 得到最后一次的记录,用于本次更新框架数值
					ElisaIiqc oldeii = lists.get(lists.size() - 1);
					eii.setTciid(elisaInfo.getTciId());
					eii.setEiId(elisaInfo.getId());
					eii.setOpAdmin(elisaInfo.getOpAdmin());
					eii.setCutOffvalue(elisaInfo.getCutOffValue());
					eii.setOdValue(ev.getOdValue());
					eii.setsDivideCO((new BigDecimal( MathUtils.divide(ev.getOdValue().doubleValue(), elisaInfo.getCutOffValue().doubleValue(), 4))));
					eii.setAltValue(ev.getValue());
					eii.setQcId(elisaInfo.getQcId());
					// 第一步 计算标准差
					StandardDeviation sd = new StandardDeviation();
					double sum = 0;
					Float siMAX = 0f, siMIN = 10000f;
					
					// 循环将s/co值加入标准差计算公式, 并且得到最小值和最大值
					for (int i = 0, j = lists.size(); i < j; i++) {
						Float dco = lists.get(i).getsDivideCO().floatValue();
						sum = sum + dco;
						sd.addSample(dco);
						// 取最大值
						if (dco > siMAX) {
							siMAX = dco;
						}
						// 取最小值
						if (dco < siMIN) {
							siMIN = dco;
						}
					}
					float sDivideCO = eii.getsDivideCO().floatValue();
					sum= sum+sDivideCO;
					// 取最大值
					if (sDivideCO > siMAX) {
						siMAX = sDivideCO;
					}
					// 取最小值
					if (sDivideCO < siMIN) {
						siMIN = sDivideCO;
					}
					sd.addSample(sDivideCO);
					// 保存标准差
					eii.setStandardDeviation(new BigDecimal( sd.getRunningVariance()));
					// 保存平均值 - 靶值
					eii.setTargetValue(new BigDecimal(sum / (lists.size()+1)));
					// 保存CV值
					eii.setCv(new BigDecimal(eii.getStandardDeviation().divide(eii.getTargetValue(),4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100));
					
					// 这里保存 SDI值
					if (eii.getStandardDeviation().floatValue() == 0) {
						// 保存siMAX
						eii.setSiMAX(new BigDecimal(0f));
						// 保存siMIN
						eii.setSiMIN(new BigDecimal(0f));
					} else {
						// 保存siMAX
						eii.setSiMAX(new BigDecimal(siMAX - eii.getTargetValue().floatValue()).divide(eii.getStandardDeviation(),4,BigDecimal.ROUND_HALF_UP));
						// 保存siMIN
						eii.setSiMIN(new BigDecimal(eii.getTargetValue().floatValue() - siMIN).divide(eii.getStandardDeviation(),4,BigDecimal.ROUND_HALF_UP));
					}
					if (isElisa) {
						// 取模为1则更新框架值
						if((lists.size()-20)%20==0) {
							// 保存框架标准差
							eii.setFrameStandardDeviation(oldeii.getStandardDeviation());
							// 保存框架平均值
							eii.setFrameTargetValue(oldeii.getTargetValue());
							// 保存框架CV值
							eii.setFrameCV(oldeii.getCv());
						}else { // 其他的则保存上次的框架值
							// 保存框架标准差
							eii.setFrameStandardDeviation(oldeii.getFrameStandardDeviation());
							// 保存框架平均值
							eii.setFrameTargetValue(oldeii.getFrameTargetValue());
							// 保存框架CV值
							eii.setFrameCV(oldeii.getFrameCV());
						}
						
						
						if (lists.size() < 20) { // 20次内使用即刻法计算结果
							eii.setN2s(new BigDecimal(n2s[lists.size() - 2]));
							eii.setN3s(new BigDecimal(n3s[lists.size() - 2]));
							// 保存判定结果
							if (eii.getSiMAX().compareTo(eii.getN2s())==-1  && eii.getSiMIN().compareTo(eii.getN2s())==-1) { // 上下限都在N2s内
								eii.setCriticalResult(0); // 在控
							} else if (eii.getSiMAX().compareTo(eii.getN3s())==1  || eii.getSiMIN().compareTo(eii.getN3s())==1) { // 上下限有一个超过N3s
								eii.setCriticalResult(2); // 失控
							} else {
								eii.setCriticalResult(1); // 警告
							}
						} else {// 超过20次使用质控图计算结果
							// 保存 n2s
							eii.setN2s(eii.getStandardDeviation().multiply(BigDecimal.valueOf(2)).add( eii.getTargetValue()));
							// 保存 n3s
							eii.setN3s(eii.getStandardDeviation().multiply(BigDecimal.valueOf(3)).add( eii.getTargetValue()));
							// 保存判定结果 判定标准为OD值在N2s以内就是在控
							float tValue = eii.getOdValue().floatValue();
							float n2ss = eii.getN2s().floatValue();
							float n3ss = eii.getN3s().floatValue();
							float fn2ss = eii.getTargetValue().floatValue() - eii.getStandardDeviation().floatValue() * 2;
							float fn3ss = eii.getTargetValue().floatValue() - eii.getStandardDeviation().floatValue() * 3;
							if (tValue < n2ss && n2ss > fn2ss) { // 上下限都在N2s内
								eii.setCriticalResult(0); // 在控
							} else if (tValue > n3ss || tValue < fn3ss) { // 上下限有一个超过N3s内
								eii.setCriticalResult(2); // 失控
							} else {
								eii.setCriticalResult(1); // 警告
							}
							float otValue = oldeii.getOdValue().floatValue();
							// 连续两次同侧超出N2s 也是失控
							if(tValue > n2ss && tValue <n3ss && otValue > n2ss && otValue <n3ss) {
								eii.setCriticalResult(2); // 失控
							}
							if(tValue > fn3ss && tValue < fn2ss && otValue > fn3ss && otValue < fn2ss) {
								eii.setCriticalResult(2); // 失控
							}
//							int istop =0, isdown = 0;
							// 连续同侧7次N2s内也是失控
//							List<ElisaIiqc> seven = lists.subList(lists.size()-6, lists.size());
//							for(ElisaIiqc ei : seven) {
//								if(ei.getOdValue().floatValue()<n2ss && ei.getOdValue().compareTo(ei.getFrameTargetValue())==1) {
//									istop ++;
//								}else
//								if(ei.getOdValue().floatValue()>fn2ss && ei.getOdValue().compareTo(ei.getFrameTargetValue())==-1) {
//									isdown ++;
//								}
//							}
//							if(istop>=6 || isdown >=6) {
//								eii.setCriticalResult(2); // 失控
//							}
						}
					} else {
						// 取模为1则更新框架值
						if((lists.size()-20)%20==0) {
							// 保存框架标准差
							eii.setFrameStandardDeviation(oldeii.getStandardDeviation());
							// 保存框架平均值
							eii.setFrameTargetValue(oldeii.getTargetValue());
							// 保存框架CV值
							eii.setFrameCV(oldeii.getCv());
						}else {
							// 保存框架标准差
							eii.setFrameStandardDeviation(oldeii.getFrameStandardDeviation());
							// 保存框架平均值
							eii.setFrameTargetValue(oldeii.getFrameTargetValue());
							// 保存框架CV值
							eii.setFrameCV(oldeii.getFrameCV());
						}
						
						
						// 第二步 计算赖氏法的标准差
						StandardDeviation altsd = new StandardDeviation();
						double altsum = 0;
						Float altSiMAX = 0f, altSiMIN = 10000f;
						for (int i = 0, j = lists.size(); i < j; i++) {
							Float dco = lists.get(i).getAltValue().floatValue();
							// 取最大值
							if (dco > altSiMAX) {
								altSiMAX = dco;
							}
							// 取最小值
							if (dco!=0 && dco < altSiMIN) {
								altSiMIN = dco;
							}
							altsum = altsum + dco;
							altsd.addSample(dco);
							
						}
						// 得到本次的卡门值
						float altValue = eii.getAltValue().floatValue();
						altsum = altsum + altValue;
						// 再次取最大值
						if (altValue > altSiMAX) {
							altSiMAX = altValue;
						}
						// 再次取最小值
						if (altValue!=0 && altValue < altSiMIN) {
							altSiMIN = altValue;
						}
						altsd.addSample(altValue);
						// 保存ALT标准差
						eii.setAltStandardDeviation(new BigDecimal( altsd.getRunningVariance()));
							// 保存ALT平均值
							eii.setAltTargetValue(new BigDecimal(altsum / (lists.size()+1)));
							// 保存ALTCV值
							eii.setAltCV(new BigDecimal((eii.getAltStandardDeviation().floatValue() / eii.getAltTargetValue().floatValue()) * 100));
							if((lists.size()-20)%20==0) {
								// 保存ALT框架标准差
								eii.setAltFrameStandardDeviation(oldeii.getAltStandardDeviation());
								// 保存ALT框架平均值
								eii.setAltFrameTargetValue(oldeii.getAltTargetValue());
								// 保存ALT框架CV值
								eii.setAltFrameCV(oldeii.getAltCV());
							}else {
								// 保存ALT框架标准差
								eii.setAltFrameStandardDeviation(oldeii.getAltFrameStandardDeviation());
								// 保存ALT框架平均值
								eii.setAltFrameTargetValue(oldeii.getAltFrameTargetValue());
								// 保存ALT框架CV值
								eii.setAltFrameCV(oldeii.getAltFrameCV());
							}
							if (eii.getAltStandardDeviation().floatValue() == 0) {
								// 保存ALTsiMAX
								eii.setAltSIMAX(new BigDecimal(0f));
								// 保存ALTsiMIN
								eii.setAltSIMIN(new BigDecimal(0f));
							} else {
								// 保存ALTsiMAX
								eii.setAltSIMAX(BigDecimal.valueOf(altSiMAX).subtract(eii.getAltTargetValue()).divide(eii.getAltStandardDeviation(),4,BigDecimal.ROUND_HALF_UP));
								// 保存ALTsiMIN
								eii.setAltSIMIN(eii.getAltTargetValue().subtract(BigDecimal.valueOf(altSiMIN)).divide(eii.getAltStandardDeviation(),4,BigDecimal.ROUND_HALF_UP));
							}
							
							if (lists.size() < 20) { // 20次内使用即刻法计算结果
								eii.setN2s(new BigDecimal(n2s[lists.size() - 2]));
								eii.setN3s(new BigDecimal(n3s[lists.size() - 2]));
								eii.setAltN2S(new BigDecimal(n2s[lists.size() - 2]));
								eii.setAltN3S(new BigDecimal(n3s[lists.size() - 2]));
								// 保存判定结果
								if (eii.getAltSIMAX().compareTo(eii.getAltN2S())==-1 && eii.getAltSIMIN().compareTo(eii.getAltN2S())==-1) { // 上下限都在N2s内
									eii.setCriticalResult(0); // 在控
								} else if (eii.getAltSIMAX().compareTo(eii.getAltN3S())==1 || eii.getAltSIMIN().compareTo(eii.getAltN3S())==1) { // 上下限有一个超过N3s
									eii.setCriticalResult(2); // 失控
								} else {
									eii.setCriticalResult(1); // 警告
								}
						} else { // 超过20次使用质控图计算结果
							// 保存 ALTn2s
							eii.setN2s(eii.getAltStandardDeviation().multiply(BigDecimal.valueOf(2)).add(eii.getAltTargetValue()));
							eii.setN3s(eii.getAltStandardDeviation().multiply(BigDecimal.valueOf(3)).add(eii.getAltTargetValue()));
							eii.setAltN2S(eii.getAltStandardDeviation().multiply(BigDecimal.valueOf(2)).add(eii.getAltTargetValue()));
							// 保存ALT n3s
							eii.setAltN3S(eii.getAltStandardDeviation().multiply(BigDecimal.valueOf(3)).add(eii.getAltTargetValue()));
							// 保存判定结果 判定标准为卡门值在N2s以内就是在控
							float tValue = eii.getAltValue().floatValue();
							float n2ss = eii.getAltN2S().floatValue();
							float n3ss = eii.getAltN3S().floatValue();
							float fn2ss = eii.getAltTargetValue().floatValue() - eii.getAltStandardDeviation().floatValue() * 2;
							float fn3ss = eii.getAltTargetValue().floatValue() - eii.getAltStandardDeviation().floatValue() * 3;
							if (tValue < n2ss && n2ss > fn2ss) { // 上下限都在N2s内
								eii.setCriticalResult(0); // 在控
							} else if (tValue > n3ss || tValue < fn3ss) { // 上下限有一个超过N3s内
								eii.setCriticalResult(2); // 失控
							} else {
								eii.setCriticalResult(1); // 警告
							}
							float otValue = oldeii.getAltValue().floatValue();
							// 连续两次同侧超出N2s 也是失控
							if(tValue > n2ss && tValue <n3ss && otValue > n2ss && otValue <n3ss) {
								eii.setCriticalResult(2); // 失控
							}
							if(tValue > fn3ss && tValue < fn2ss && otValue > fn3ss && otValue < fn2ss) {
								eii.setCriticalResult(2); // 失控
							}
//							int istop =0, isdown = 0;
							// 连续同侧7次N2s内也是失控
//							List<ElisaIiqc> seven = lists.subList(lists.size()-6, lists.size());
//							for(ElisaIiqc ei : seven) {
//								if(ei.getAltValue().floatValue()<n2ss && ei.getAltValue().compareTo(ei.getAltFrameTargetValue())==1) {
//									istop ++;
//								}else
//								if(ei.getAltValue().floatValue()>fn2ss && ei.getAltValue().compareTo(ei.getAltFrameTargetValue())==-1) {
//									isdown ++;
//								}
//							}
//							if(istop>=6 || isdown >=6) {
//								eii.setCriticalResult(2); // 失控
//							}
						}
					}
					if (!iiqcService.insert(eii)) {
						
						throw new Exception("保存质控记录失败");
					}else {
						try {
							logService.insertLog(IConstants.MODULE_QC, IConstants.DESC.replace("{0}", "保存了酶标板化验结果 ID 为:"+eiId+"的质控记录,本次质控结果为: "+(eii.getCriticalResult()==0?"在控":eii.getCriticalResult()==1?"告警":"失控")),"null");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		}
		if (!evService.insertBatch(evLists)) {
			throw new Exception("保存读板记录失败");
		}else {
			try {
				logService.insertLog(IConstants.MODULE_QC, IConstants.DESC.replace("{0}", "保存了酶标微板号为:"+elisaInfo.getAllSequenceNumber()+"的浆员化验记录."),"null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public ElisaInfo selectById(Serializable id) {
		ElisaInfo elisaInfo = baseMapper.selectById(id);
		ElisaTemplate elisaTemplate = etlService.selectById(elisaInfo.getEtId());
		elisaInfo.setElisaTemplate(elisaTemplate);
		
		List<ElisaValues> lst = evMapper.selectListByEiId(elisaInfo.getId());
		elisaInfo.setElisaValues(lst);

		Wrapper<TestConfigInfo> ews = new EntityWrapper<TestConfigInfo>();
		ews.eq("reagentBatch", elisaInfo.getReagentId());
		ews.eq("del_flag", 0);
		ews.le("startDate", DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN) + " 00:00:00");
		ews.ge("endTime", DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN) + " 23:59:59");
		List<TestConfigInfo> tcis = tciMapper.selectList(ews);
		if (tcis.size() > 0) {
			elisaInfo.setTestConfigInfo(tcis.get(0));
		}
		// 关联查找质控结果
		Wrapper<ElisaIiqc> ew2 = new EntityWrapper<ElisaIiqc>();
		ew2.eq("eiId", elisaInfo.getId());
		List<ElisaIiqc> eis = iiqcService.selectList(ew2);
		if(eis.size()!=0) {
			elisaInfo.setQcResult(""+eis.get(0).getCriticalResult());
		}
		return elisaInfo;
	}

	/**
	 * 根据创建时间找到最大序号
	 * 
	 * @param createDate
	 * @return
	 */
	@Override
	public String getMaxSeqNumberByCreateDate(String createDate) {

		return baseMapper.getMaxSeqNumberByCreateDate(createDate);
	}

	/**
	 * 根据条件查询酶标板读板信息
	 * 
	 * @param page
	 * @param elisaInfo
	 * @return
	 */
	@Override
	public Page<DataRow> selectInfoByCondition(Page<DataRow> page, ElisaInfo elisaInfo) {
		return page.setRecords(baseMapper.selectInfoByCondition(page, elisaInfo));
	}

	/**
	 * 返回当天的所有的全序号
	 * 
	 * @return
	 */
	@Override
	public List<String> getAllSequenceNumber() {
		return baseMapper.getAllSequenceNumber();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean delById(Long id) throws Exception {
		// 先删除酶标仪孔位信息
		Wrapper<ElisaValues> ew = new EntityWrapper<ElisaValues>();
		ew.eq("eiId", id);
		if (evMapper.delete(ew) > 96) {
			throw new Exception("删除了超过96个孔位");
		}else {
			logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "删除了酶标化验板记录,ID为"+id+"的浆员化验记录."),"null");
		}
		if (baseMapper.deleteById(id) > 1) {
			throw new Exception("删除了多个记录");
		}else {
			logService.insertLog(IConstants.MODULE_TEST, IConstants.DESC.replace("{0}", "删除了酶标化验板记录,ID为"+id),"null");
		}
		Wrapper<ElisaIiqc> ew1 = new EntityWrapper<ElisaIiqc>();
		ew1.eq("eiId", id);
		// 删除即控数据
		iiqcService.delete(ew1);
		logService.insertLog(IConstants.MODULE_QC, IConstants.DESC.replace("{0}", "删除了酶标化验板记录,ID为"+id+"的 质控记录"),"null");
		return true;

	}

	@Override
	public void doInsertEi(Map<String, String> map, TestConfigInfo tci, ElisaTemplate template, String[] values,
			DataRow messageMap) throws Exception {
		// 页面传过来的96孔数据
				String [] tds =  map.get("tds").split(",");
				// 得到cutoff值
				Double cutoffValue = 0.00;
				if(!"1".equals(tci.getProjectName())) { // 如果不是ALT则要计算灰区值
					cutoffValue = getCutoffValue(template.getCutoffFormula(), template, values ,messageMap);
					if(IConstants.RESULT_FAIL_CODE.equals(messageMap.getString(IConstants.RESULT_CODE))) {
						throw new Exception(messageMap.getString(IConstants.RESULT_MESSAGE));
					}
				}else { //现在默认使用25,以后要修改再修改
					Config config = new Config();
					// 根据检测方法得到具体的CUTOFF值
					if(tci.getTestingMethodid() == 2) { // 赖氏法使用
						config = cService.getConfig("alt_value", "赖氏法");
					}else { // 速率法使用(再添加新的检验方法则修改为 else if)
						config = cService.getConfig("alt_value", "速率法");
					}
					cutoffValue = Double.parseDouble(config.getValue());
				}
				
				
				// 得到灰区计算方式
				// 是否按上限为cutoff计算
				boolean isTopOff=true;
				// 最低灰区值
				double lowValue=0;
				// 最高灰区值
				double topValue=0;
				
				ElisaGrayAreaSettings gary = template.getElisaGrayAreaSettings();
				if(gary.getIsMaxWithCutOff()==0){
					isTopOff = false;
				}
				if(gary.getOptionsValue()==1){ // 按比例
					lowValue = cutoffValue*(1-gary.getProportionValue().doubleValue());
					if(isTopOff){
						topValue = cutoffValue*(1+gary.getProportionValue().doubleValue());
					}else{
						topValue = cutoffValue;
					}
				}else if(gary.getOptionsValue()==0){ // 按常规
					lowValue = cutoffValue-gary.getConventionalValue().doubleValue();
					if(isTopOff){
						topValue = cutoffValue-gary.getConventionalValue().doubleValue();
					}else{
						topValue = cutoffValue;
					}
				}else{
					lowValue = gary.getMinValue().doubleValue();
					topValue = gary.getMaxValue().doubleValue();
				}
				// 保存孔位信息
				ElisaInfo elisaInfo = new ElisaInfo();
				elisaInfo.setEtId(template.getId());
				elisaInfo.setTciId(tci.getId());
				elisaInfo.setQcId(Long.parseLong(map.get("qcId")));
				elisaInfo.setReagentId(Long.parseLong(map.get("reagentId")));
				elisaInfo.setCutOffValue(new BigDecimal(cutoffValue));
				String testDate ="";
				if(	StringUtils.isEmpty(map.get("testDate"))) {
					testDate = DateUtil.getSystemTime(null);
				}else {
					testDate = map.get("testDate");
				}
				elisaInfo.setTestDate(testDate);
				Config con = cService.getConfigByValue("elisa_check_project", tci.getProjectName());
				elisaInfo.setProjectName(con.getLable());
				String createDate = DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN);
				String sn = getMaxSeqNumberByCreateDate(createDate);
				// 计算最大的序号，并且给出来
				if(StringUtils.isEmpty(sn)) {
					sn = "0001";
				}else {
					sn = String.format("%04d", Integer.parseInt(sn)+1);
				}
				elisaInfo.setSequenceNumber(sn);
				elisaInfo.setAllSequenceNumber(DateUtil.formatDate(new Date(), DateUtil.yyyyMMdd)+sn);
				// 设置检测人
				elisaInfo.setOpAdmin(Long.parseLong(map.get("creater").toString()));
				List<ElisaValues> evs =  new ArrayList<ElisaValues>();
				String [] sampleNos = new String[96]; 
				String [] allIds = new String[96]; 
				String [] pdIds = new String[96]; 
				String [] personNames = new String[96]; 
				String [] bloodTypes = new String[96]; 
				// 找出本次要检测的所有的小样号
				for(int i=0 ,j=tds.length;i<j;i++) {
					// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名   有可能只有小样号
					String [] tdVal = tds[i].split("_");
					if(tdVal.length==5) {
						sampleNos[i]=tdVal[0];
		    			allIds[i]=tdVal[1];
		    			pdIds[i]=tdVal[2];
		    			bloodTypes[i] = tdVal[3];
		    			personNames[i] = tdVal[4];
					}
				}
				// 找出与本次检验的小样号相关的历史检验信息
				List<ElisaValues> evlist = evService.selectBysampleNos(sampleNos);
				List<ElisaTemplateValues> etValues = template.getElisaTemplateValues();
				if("1".equals(tci.getProjectName())) {  //ALT走这里
					// 记录ALT使用的STD值
		            double[] x = { 0.00, 0.00, 0.00, 0.00, 0.00 };
		            int j=0;
					// 获取 STD值 计算
					for(int i=0;i<etValues.size();i++) {
						ElisaTemplateValues et = etValues.get(i);
						if(et.getType()==0) {
							x[j] = (Double.parseDouble(values[et.getEtvIndex()-1]))/10000;
							j++;
						}
					}
					// 对x排序
					ElisaMeasurementMethod emm = template.getElisaMeasurementMethod();
					double[] y = {emm.getStd1(),emm.getStd2(),emm.getStd3(),emm.getStd4(),emm.getStd5()};
					PolynomialRegression regression = new PolynomialRegression(x, y, 3);// 多元函数拟合  得到预测值
					for(int i=0;i<etValues.size();i++) {
						evs.add(getEVs(new EV(etValues.get(i), sampleNos, allIds,pdIds,personNames,bloodTypes,values,regression, evlist,i),cutoffValue ,lowValue, topValue));
		    		}
		    		elisaInfo.setElisaValues(evs);
		    		// 保存记录,  后面是如果有检测错误则不保存即控
		    		if(insertAll(elisaInfo,IConstants.RESULT_SUCCESS_CODE.equals(messageMap.get(IConstants.RESULT_CODE)),false)) {
		    			elisaInfo = selectById(elisaInfo.getId());
		    		}
		    		// 判断是否有错误信息, 将错误消息保存到另外一个记录中,供页面使用
		    		if(IConstants.RESULT_FAIL_CODE.equals(messageMap.get(IConstants.RESULT_CODE))) {
		    			messageMap.put("emsg", messageMap.get(IConstants.RESULT_MESSAGE));
		    		}
		    		messageMap.initSuccess(elisaInfo);
				}else {  
					
		    		for(int i=0;i<etValues.size();i++) {
		    			evs.add(getEVs(new EV(etValues.get(i), sampleNos, allIds,pdIds,personNames,bloodTypes,values,null, evlist,i),cutoffValue ,lowValue, topValue));
		    		}
		    		// 保存记录,  后面是如果有检测错误则不保存即控
		    		elisaInfo.setElisaValues(evs);
		     		if(insertAll(elisaInfo,IConstants.RESULT_SUCCESS_CODE.equals(messageMap.get(IConstants.RESULT_CODE)), true)) {
		    			elisaInfo = selectById(elisaInfo.getId());
		    		}
		     		// 判断是否有错误信息, 将错误消息保存到另外一个记录中,供页面使用
		    		if(IConstants.RESULT_FAIL_CODE.equals(messageMap.get(IConstants.RESULT_CODE))) {
		    			messageMap.put("emsg", messageMap.get(IConstants.RESULT_MESSAGE));
		    		}
		    		messageMap.initSuccess(elisaInfo);
				}
		
	}
	/**
	 *  计算cutoff值,并返回nc和pc的值
	 * @param cutoffFormula
	 * @param etvs
	 * @param values
	 * @param nc
	 * @param pc
	 * @return
	 */
	private Double getCutoffValue(String cutoffFormula,ElisaTemplate template, String[] values , DataRow messageMap) {
		// 得到常用设置记录
		ElisaCommonSetting setting = template.getElisaCommonSetting();
		boolean isCheckDE =setting.getDetectionEffectiveness()==1; // 是否开启检测有效性
		List<ElisaTemplateValues> etvs = template.getElisaTemplateValues();
		cutoffFormula = cutoffFormula.trim().toLowerCase();
		String [] param = new  String[] {"s","SMP","p","p2","n","q","l","BLK",""};
		double p2=0,p=0,n=0,q=0,l=0,s=0,b=0;
		double pavg=0,navg=0,bavg=0;
		for(int i=0,j=param.length;i<j;i++) {
			int index = 0;
			int tempValue= 0;
			int sumValue=0;
			for(int k=0,m=etvs.size();k<m;k++) {
				ElisaTemplateValues etv = etvs.get(k);
				if(etv.getType() ==i) {
					tempValue = Math.abs(Integer.parseInt(StringUtils.isEmpty(values[etv.getEtvIndex()-1])?"0":values[etv.getEtvIndex()-1]));
									
					if(param[i].equals("p") || param[i].equals("p2")) {
						if(isCheckDE) // 如果开启检验有效性
							if(param[i].equals("p")) { // 检验阳性孔值的有效性
								if(setting.getPositiveHoleOperator() != 1000) {
									if(!contrast(tempValue, setting.getPositiveHoleOperator(), setting.getPositiveHoleValue().doubleValue()*10000)) {
										messageMap.initFial("第"+etv.getEtvIndex()+"孔的阳性孔值已经超出设置的阈值,本次实验无效.");
										messageMap.put("canContinue", true);
									}
								}
							}else {
								if(setting.getPositive2HoleOperator() != 1000)
									if(!contrast(tempValue, setting.getPositive2HoleOperator(), setting.getPositive2HoleValue().doubleValue()*10000)) {
										messageMap.initFial("第"+etv.getEtvIndex()+"孔的阳性2孔值已经超出设置的阈值,本次实验无效.");
										messageMap.put("canContinue", true);
									}
							}
						
						// 判断是否启用阳性阈值
						if(setting.getUsePositiveThreshold() ==1) {
							BigDecimal b11 = setting.getPositiveThresholdValue();
							BigDecimal b21 = new BigDecimal(10000);
							int ptv  = b11.multiply(b21).intValue();
							// 计算上限值 大于
							if(setting.getPositiveMaxOperator()==0) { // 使用大于符号
								if(ptv<tempValue) { // 阈值大于计算值
									if(setting.getPositiveMaxValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcspositiveMaxValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}else { // 使用大于等于
								if(ptv<=tempValue) {
									if(setting.getPositiveMaxValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcspositiveMaxValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}
							// 计算下限值 小于
							if(setting.getPositiveMinOperator()==0) { // 使用小于符号
								if(ptv>tempValue) { // 阈值小于计算值
									if(setting.getPositiveMinValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcspositiveMinValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}else { // 使用小于等于
								if(ptv>=tempValue) {
									if(setting.getPositiveMinValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcspositiveMinValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}
						}
						if(pc>tempValue||pc==0) {
							pc=tempValue;
						}
						//values[etv.getEtvIndex()-1] = ""+tempValue;
					}else if(param[i].equals("n")) {
						if(isCheckDE) // 如果开启检验有效性
							if(setting.getNegativeHoleOperator() != 1000)
								if(!contrast(tempValue, setting.getNegativeHoleOperator(), setting.getNegativeHoleValue().doubleValue()*10000)) {
									messageMap.initFial("第"+etv.getEtvIndex()+"孔的阴性孔值已经超出设置的阈值,本次实验无效.");
									messageMap.put("canContinue", true);
								}
						// 判断是否启用阴性阈值
						if(setting.getUseNegativeThreshold() ==1) {
							// 计算上限值 大于
							  
							BigDecimal b11 = setting.getNegativeThresholdValue();
							BigDecimal b21 = new BigDecimal(10000);
							int ntv  = b11.multiply(b21).intValue();
							if(setting.getNegativeMaxOperator()==0) { // 使用大于符号
								if(ntv<tempValue) { // 阈值大于计算值
									if(setting.getNegativemaxValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcsnegativemaxValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}else { // 使用大于等于
								if(ntv<=tempValue) {
									if(setting.getNegativemaxValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcsnegativemaxValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}
							// 计算下限值 小于
							if(setting.getNegativeMinOperator()==0) { // 使用小于符号
								if(ntv>tempValue) { // 阈值小于计算值
									if(setting.getNegativeMinValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcsnegativeMinValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}else { // 使用小于等于
								if(ntv>=tempValue) {
									if(setting.getNegativeMinValue().floatValue() != 1000.0) { // 使用固定值
										BigDecimal b1 = setting.getEcsnegativeMinValue();
										BigDecimal b2 = new BigDecimal(10000);
										tempValue  = b1.multiply(b2).intValue();
									}
								}
							}
						}
						if(nc<tempValue) {
							nc=tempValue;
						}
						//values[etv.getEtvIndex()-1] = ""+tempValue;
					}else if(param[i].equals("BLK")){
						if(b<tempValue) {
							b=tempValue;
						}
						if(isCheckDE)
							if(setting.getBlankHoleOperator()!=1000)
								if(!contrast(tempValue, setting.getBlankHoleOperator(), setting.getBlankHoleValue().doubleValue()*10000)) {
									messageMap.initFial("第"+etv.getEtvIndex()+"孔的空白孔值已经超出设置的阈值,本次实验无效.");
									messageMap.put("canContinue", true);
								}
					}
				
					sumValue  = sumValue+tempValue;
					 index++;
				}
				
			}
			BigDecimal bd =new BigDecimal(sumValue).divide(new BigDecimal((index==0?1:index)*10000),2, BigDecimal.ROUND_HALF_UP);
			double tempValued = bd.doubleValue();
			// 根据类型授权
			switch(i){
		  	case 0:
	  			s=tempValued;
			    break;
		  	case 1:
			  	break;
		  	case 2:
		  		if(pavg != tempValued && pavg!=0) {
		  			pavg = (new BigDecimal(pavg + tempValued)).divide(new BigDecimal(2),2, BigDecimal.ROUND_HALF_UP).doubleValue();
		  		}else {
		  			pavg=tempValued;
		  		}
		  		p = p<tempValued&&p!=0?p:tempValued;   // 取阳性最小值
	  			break;
		  	case 3:
		  		if(pavg != tempValued && pavg!=0) {
		  			pavg = (new BigDecimal(pavg + tempValued)).divide(new BigDecimal(2),2, BigDecimal.ROUND_HALF_UP).doubleValue();
		  		}else {
		  			pavg=tempValued;
		  		}
		  		p2 =  p2<tempValued&&p2!=0?p2:tempValued;
				break;
		  	case 4:
		  		navg=tempValued;
				break;
		  	case 5:
		  		q=tempValued;
				break;
		  	case 6:
		  		l=tempValued;
				  break;
		  	case 7:
		  		bavg = tempValued;
				  break;
		  	default:
		  		break;
		  }
			
		}
		
		
		
		if(isCheckDE) { // 开启检测有效性判断 判断均值是否在控
			if(setting.getNegativeMeanOperator()!=1000) { // 阴性孔值的计算
				if(!contrast(navg, setting.getNegativeMeanOperator().intValue(), setting.getNegativeMeanValue().doubleValue())) {
					messageMap.initFial("阴性均值已经超出设置的阈值,本次实验无效.");
					messageMap.put("canContinue", true);
				}
			}
			if(setting.getPositiveMeanOperator()!=1000) { // 阳性孔值的计算
				if(!contrast(pavg, setting.getPositiveMeanOperator().intValue(), setting.getPositiveMeanValue().doubleValue())) {
					messageMap.initFial("阳性均值已经超出设置的阈值,本次实验无效.");
					messageMap.put("canContinue", true);
				}
			}
			if(setting.getPositive2MeanOperator()!=1000) { // 阳性2孔值的计算
				if(!contrast(pavg, setting.getPositive2MeanOperator().intValue(), setting.getPositive2MeanValue().doubleValue())) {
					messageMap.initFial("阳性均值已经超出设置的阈值,本次实验无效.");
					messageMap.put("canContinue", true);
				}
			}
			if(setting.getBlankMeanOperator()!=1000) { // 空白孔值的计算
				if(!contrast(bavg, setting.getBlankMeanOperator().intValue(), setting.getBlankMeanValue().doubleValue())) {
					messageMap.initFial("空白孔均值已经超出设置的阈值,本次实验无效.");
					messageMap.put("canContinue", true);
				}
			}
		}
		
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine se = manager.getEngineByName("js");
	    String arrrg= "var p2="+pavg+",p="+pavg+",n="+navg+",q="+q+",l="+l+",s="+s+";";
		try {
			return (Double) se.eval(arrrg+cutoffFormula);
		} catch (ScriptException e) {
			messageMap.initFial("cutoff公式有误");
			return 0.00d;
		}
	}
	/**
	 * 根据operator 对比大小 对比规则first:operator:last  
	 * 假定 first = 1,operator = 0 , last = 2
	 * 根据运算则返回结果
	 * @param first
	 * @param operator  1:大于, 2:大于等于, 3:小于,4:小于等于 ,1000 不计算(也请不要放进来)return null
	 * @param last
	 * @return
	 */
	private Boolean contrast(double first, int operator, double last) {
		switch (operator) {
		case 1: // 大于
			return first > last;
		case 2: // 大于等于
			return first >= last;
		case 3: // 小于
			return first < last;
		case 4: // 小于等于
			return first <= last;
		default:
			return null;
		}
	}
	/**
	 * String [] sampleNos = new String[96]; 
            		String [] allIds = new String[96]; 
            		String [] pdIds = new String[96]; 
            		String [] personNames = new String[96]; 
            		String [] bloodTypes = new String[96]; 
            		BigDecimal pcvaluebd
	 * @param et
	 * @return
	 */
	private ElisaValues getEVs(EV ev, double pcvalue ,double lowValue, double topValue) {
		ElisaValues e =  new ElisaValues();
		e.setEvIndex(ev.et.getEtvIndex());
		e.setType(ev.et.getType());
		// 双波检测
		String odStr = ev.values[ev.et.getEtvIndex()-1];
		double odValue = (Double.parseDouble(StringUtils.isEmpty(odStr)?"0.00":odStr))/10000;
		if(ev.regression == null) {
			// 现在写死的,大于等于25则为阳性
			// 大于等于阳性为1 小于阳性大于等于cutoff为弱阳性2 合格为0  lowValue低   topValue高
			e.setJudgementResult(odValue>=pcvalue?1:odValue>=lowValue&&odValue<=topValue?2:0);
			e.setValue(BigDecimal.valueOf(0.00));
		}else {
			double predict = ev.regression.predict(odValue);
			if(predict<0) predict = 0;
			double f1 = (double) Math.round(predict * 10000) / 10000;  
			e.setValue(BigDecimal.valueOf(f1));  //alt值写入测值
			e.setJudgementResult(f1>pcvalue?1:0);
		}
		e.setOdValue(new BigDecimal(odValue));
		e.setOriginalODValue(new BigDecimal(odValue));
		e.setSampleNo("null".equals(ev.sampleNos[ev.i])?null:ev.sampleNos[ev.i]);
		String allIdStr = ev.allIds[ev.i];
		e.setAllId(Long.parseLong(StringUtils.isEmpty(allIdStr)?"-1":allIdStr));
		e.setPbId(ev.pdIds[ev.i]);
		e.setBloodType(ev.bloodTypes[ev.i]);
		e.setPersonName(ev.personNames[ev.i]);
		// 记录检验次数
		e.setSampleType(0); // 默认为初检
		for(ElisaValues ev1 : ev.evlist) {
			if(ev1.getAllId()==e.getAllId()) { // 如果存在则为复检
				e.setSampleType(1);
				break;
			}
		}
		return e;
	}
}

class EV {
	ElisaTemplateValues et;
	String [] sampleNos;
	String [] allIds;
	String [] pdIds;
	String [] personNames;
	String [] bloodTypes;
	String[] values;
	PolynomialRegression regression;
	List<ElisaValues> evlist;
	
	int i;
	public EV(ElisaTemplateValues et,String [] sampleNos,String [] allIds,String [] pdIds,String [] personNames,String [] bloodTypes,String[] values,PolynomialRegression regression, List<ElisaValues> evlist,int i) {
		this.et=et;
		this.sampleNos=sampleNos;
		this.allIds=allIds;
		this.pdIds=pdIds;
		this.personNames=personNames;
		this.bloodTypes=bloodTypes;
		this.values=values;
		this.regression=regression;
		this.evlist=evlist;
		this.i=i;
	}
}
