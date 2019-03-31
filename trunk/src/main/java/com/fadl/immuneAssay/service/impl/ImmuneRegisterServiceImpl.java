package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.ConversionRecord;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import com.fadl.immuneAssay.entity.ImmuneSetting;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.dao.ImmuneRegisterMapper;
import com.fadl.immuneAssay.service.ConversionRecordService;
import com.fadl.immuneAssay.service.ImmuneRegisterService;
import com.fadl.immuneAssay.service.ImmuneSettingService;
import com.fadl.immuneAssay.service.OrdinaryInjectionService;
import com.fadl.immuneAssay.service.PrivilegeInjectionService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 免疫登记 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-18
 */
@Service
public class ImmuneRegisterServiceImpl extends ServiceImpl<ImmuneRegisterMapper, ImmuneRegister> implements ImmuneRegisterService {
	@Autowired
	ImmuneRegisterMapper immuneRegisterMapper;
	@Autowired
	OrdinaryInjectionService ordinaryInjectionService;
	@Autowired
	PrivilegeInjectionService privilegeInjectionService;
	@Autowired
	ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	ImmuneSettingService immuneSettingService;
	@Autowired
	ConfigService configservice;
	@Autowired
	ConversionRecordService conversionRecordService;
	@Autowired
	LogService logService;
	/**
	 * 普免登记列表(不通过)
	 * @param page
	 * @return
	 */
	@Override
	public void queryImmuneRegister(Page<DataRow> page,String startTime) throws Exception {
	/*	Config config =configservice.queryConfig("immune_type", "1");
		String type="";
		if(config.getIsDisable()==1) {
			type="1";
		}*/
		if(StringHelper.isEmpty(startTime)) {
			page.setRecords(immuneRegisterMapper.queryImmuneRegisterWhole(page));
		}else {
			page.setRecords(immuneRegisterMapper.queryImmuneRegister(page,startTime));		
		}
	}
	/**
	 *  特免登记列表(不通过)
	 * @param page
	 * @return
	 */
	public void queryImmuneRegisterSpecial(Page<DataRow> page,String startTime)throws Exception{
	/*	Config config =configservice.queryConfig("immune_type", "1");//
		String type="";
		if(config.getIsDisable()==1) {
			type="2";
		}*/
		if(StringHelper.isEmpty(startTime)) {
			page.setRecords(immuneRegisterMapper.queryImmuneRegisterWhole(page));
		}else {
			page.setRecords(immuneRegisterMapper.queryImmuneRegister(page,startTime));		
		}
	}
	/**
	 * 普免登记列表(已通过)
	 * @param page
	 * @return
	 */
	@Override
	public void queryImmuneRegisterAdopt(Page<DataRow> page,String startTime) throws Exception {
		if(StringHelper.isEmpty(startTime)) {
			page.setRecords(immuneRegisterMapper.queryImmuneRegisterAdoptWhole(page));
		}else {
			page.setRecords(immuneRegisterMapper.queryImmuneRegisterAdopt(startTime,page));	
		}
			
	}
	/**
	 * 特免登记列表(已通过)
	 * @param page
	 * @return
	 */
	@Override
	public void queryTeregisterAdopt(Page<DataRow> page,String startTime) throws Exception {
		if(StringHelper.isEmpty(startTime)) {
			page.setRecords(immuneRegisterMapper.queryTeregisterAdoptWhole(page));
		}else {
			page.setRecords(immuneRegisterMapper.queryTeregisterAdopt(startTime,page));		
		}
	}
	/**
	 * 插入登记信息
	 * @param immuneRegister
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateImmuneRegister(ImmuneRegister immuneRegister, DataRow messageMap) throws Exception {
		//这里要判断是否有普免类型
		Config config =configservice.queryConfig("immune_type", "1");
		if(config.getIsDisable()==1) {//==1，被禁用
			immuneRegister.setType(0);
		}else{
			immuneRegister.setType(1);
		}
		immuneRegister.setStatus(0);
		int row =immuneRegisterMapper.updateById(immuneRegister);
		if(row>0) {
			OrdinaryInjection o = new OrdinaryInjection();
			o.setProviderNo(immuneRegister.getProviderNo());
			o.setImmuneId(immuneRegister.getImmuneId());
			o.setNum(1);
			o.setIsShot(0);
			ordinaryInjectionService.insert(o);//往普免基础插入记录
			EntityWrapper<ProviderBaseinfo> ew = new EntityWrapper<ProviderBaseinfo>();
			ew.eq("providerNo", immuneRegister.getProviderNo());
			ProviderBaseinfo baseinfo = new ProviderBaseinfo();
			baseinfo.setProviderNo(immuneRegister.getProviderNo());
			if(config.getIsDisable()==1) {//==1,被禁用
				baseinfo.setType(0);
			}else{
				baseinfo.setType(1);
			}
			baseinfo.setImmuneId(String.valueOf(immuneRegister.getImmuneId()));
			baseinfo.setImmuneRegisterId(immuneRegister.getOrdinaryNo());
			providerBaseinfoService.update(baseinfo, ew);
			//更新免疫类型的序号
			ImmuneSetting im = immuneSettingService.selectById(immuneRegister.getImmuneId());
			im.setNum(im.getNum()+1);
			immuneSettingService.updateById(im);
			messageMap.initSuccess();
			//最后插入日志
			logService.insertLog(IConstants.IMMUNEREGISTRIES, IConstants.DESC.replace("{0}", "已免疫登记该浆员进入普免免注射"),immuneRegister.getProviderNo());
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 取消普免登记
	 * @param OrdinaryInjection
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelPuregisterRegister(OrdinaryInjection ord, DataRow messageMap) throws Exception {
		EntityWrapper<OrdinaryInjection> ew = new EntityWrapper<OrdinaryInjection>();
		ew.eq("providerNo", ord.getProviderNo());
		ew.eq("immuneId", ord.getImmuneId());
		ew.eq("num", 1);
		OrdinaryInjection ordinary = ordinaryInjectionService.selectOne(ew);
		if(null!=ordinary) {
			if(ordinary.getIsShot()==0) {
				//查询之后无需判断已注射和针次
				//更新浆员信息表和登记表
				EntityWrapper<ConversionRecord> ex = new EntityWrapper<ConversionRecord>();
				ex.eq("providerNo", ord.getProviderNo());
				ex.eq("immuneId", ord.getImmuneId());
				ConversionRecord conver =conversionRecordService.selectOne(ex);
				if(null==conver) {
					ImmuneRegister immune = new ImmuneRegister();
					immune.setProviderNo(ordinary.getProviderNo());
					immune.setImmuneId(ordinary.getImmuneId());
					immune = immuneRegisterMapper.selectOne(immune);
					immune.setOrdinaryNo("");
					immune.setType(0);
					immune.setImmuneId(null);
					immune.setStatus(1);
					int ros =immuneRegisterMapper.updateAllColumnById(immune);
					if(ros>0) {
						EntityWrapper<ProviderBaseinfo> eq = new EntityWrapper<ProviderBaseinfo>();
						eq.eq("providerNo", ordinary.getProviderNo());
						ProviderBaseinfo baseinfo = new ProviderBaseinfo();
						baseinfo.setType(0);
						baseinfo.setImmuneId("");
						baseinfo.setImmuneRegisterId("");
					boolean roe =providerBaseinfoService.update(baseinfo, eq);
					if(roe) {
						boolean row =ordinaryInjectionService.deleteById(ordinary.getId());
						if(row) {
							//最后插入日志
							logService.insertLog(IConstants.PUREREGISTRIES, IConstants.DESC.replace("{0}", "取消普免登记该浆员"),ordinary.getProviderNo());
							messageMap.initSuccess();
						}else {
							messageMap.initFial("删除普免针注射失败");
						}
					}else {
						messageMap.initFial("更新浆员信息失败");
					}
					}else {
						messageMap.initFial("更新免疫登记失败");
					}
				}else {
					messageMap.initFial("这条记录为转类浆员,不能取消登记");
				}
			}else {
				messageMap.initFial("浆员已经注射第一针,无法取消登记");
			}
		}else {
			messageMap.initFial("请选择已登记浆员进行取消登记");
		}
	}
	/**
	 * 取消特免登记
	 * @param PrivilegeInjection
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void canceltergisterRegister(PrivilegeInjection pri, DataRow messageMap) throws Exception {
		EntityWrapper<PrivilegeInjection> ew = new EntityWrapper<PrivilegeInjection>();
		ew.eq("providerNo", pri.getProviderNo());
		ew.eq("immuneId", pri.getImmuneId());
		ew.eq("num", 1);
		PrivilegeInjection privile = privilegeInjectionService.selectOne(ew);//这个没问题
		if(null!=privile) {
			if(privile.getIsShot()==0) {//判断是否注射了
				EntityWrapper<ConversionRecord> ex = new EntityWrapper<ConversionRecord>();
				ex.eq("providerNo", pri.getProviderNo());
				ex.eq("immuneId", pri.getImmuneId());
				ConversionRecord conver =conversionRecordService.selectOne(ex);
				if(null==conver) {
					//更新浆员信息表和登记表
					ImmuneRegister immune = new ImmuneRegister();
					immune.setProviderNo(privile.getProviderNo());
					immune.setImmuneId(privile.getImmuneId());
					immune = immuneRegisterMapper.selectOne(immune);
					immune.setOrdinaryNo("");
					immune.setType(0);
					immune.setImmuneId(null);
					immune.setStatus(1);
					int ros =immuneRegisterMapper.updateAllColumnById(immune);//更新为未登记
					if(ros>0) {
						EntityWrapper<ProviderBaseinfo> eq = new EntityWrapper<ProviderBaseinfo>();
						eq.eq("providerNo", privile.getProviderNo());
						ProviderBaseinfo baseinfo = new ProviderBaseinfo();
						baseinfo.setType(0);
						baseinfo.setImmuneId("");
						baseinfo.setImmuneRegisterId("");
					boolean roe =providerBaseinfoService.update(baseinfo, eq);//更新浆员的免疫类型
					if(roe) {
						boolean row =ordinaryInjectionService.deleteById(privile.getId());//删普免注射记录
						if(row) {
							messageMap.initSuccess();
							//最后插入日志
							logService.insertLog(IConstants.TERGISTRIES, IConstants.DESC.replace("{0}", "已登记该浆员"),privile.getProviderNo());
						}else {
							messageMap.initFial("特免针注射失败");
						}
					}else {
						messageMap.initFial("更新浆员信息失败");
					}
					}else {
						messageMap.initFial("更新免疫登记失败");
					}
				}else {
					messageMap.initFial("这条记录为转类浆员,不能取消登记");
				}
			}else {
				messageMap.initFial("浆员已经注射第一针,无法取消登记");
			}
		}else {
			messageMap.initFial("请选择已登记浆员进行取消登记");
		}
	}
	/**
	 * 插入特免登记信息
	 * @param immuneRegister
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	public void updateteregister(ImmuneRegister immuneRegister, DataRow messageMap) throws Exception {
		immuneRegister.setStatus(0);
		immuneRegister.setType(2);
		int row =immuneRegisterMapper.updateById(immuneRegister);
		if(row>0) {
			PrivilegeInjection p =new PrivilegeInjection();
			p.setProviderNo(immuneRegister.getProviderNo());
			p.setImmuneId(immuneRegister.getImmuneId());
			p.setNum(1);
			p.setIsShot(0);
			privilegeInjectionService.insert(p);
			EntityWrapper<ProviderBaseinfo> ew = new EntityWrapper<ProviderBaseinfo>();
			ew.eq("providerNo", immuneRegister.getProviderNo());
			ProviderBaseinfo baseinfo = new ProviderBaseinfo();
			baseinfo.setProviderNo(immuneRegister.getProviderNo());
			baseinfo.setType(2);
			baseinfo.setImmuneId(String.valueOf(immuneRegister.getImmuneId()));
			baseinfo.setImmuneRegisterId(immuneRegister.getOrdinaryNo());
			providerBaseinfoService.update(baseinfo, ew);
			//更新免疫类型的序号
			ImmuneSetting im = immuneSettingService.selectById(immuneRegister.getImmuneId());
			im.setNum(im.getNum()+1);
			immuneSettingService.updateById(im);
			//最后插入日志
			logService.insertLog(IConstants.IMMUNEREGISTRIES, IConstants.DESC.replace("{0}", "已免疫登记该浆员进入特免注射"),immuneRegister.getProviderNo());
		}
		messageMap.initSuccess();
	}
	/**
	 * 查询普通免疫基本信息查询列表
	 * @param page
	 * @param messageMap
	 * @return
	 */
	@Override
	public void queryPuregisterInfo(Map<String,String> map,Page<DataRow> page) throws Exception {
		page.setRecords(immuneRegisterMapper.queryPuregisterInfo(map, page));
	}
	/**
	 * 查询特殊免疫基本信息查询列表
	 * @param page
	 * @param messageMap
	 * @return
	 */
	@Override
	public void queryTuregisterInfo(Map<String, String> map, Page<DataRow> page) throws Exception {
		page.setRecords(immuneRegisterMapper.queryTuregisterInfo(map, page));
	}
	/**
	 * 查询免疫登记表特免未登记的浆员
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ImmuneRegister queryImmuneRegisterInfo(String providerNo) throws Exception {
		return immuneRegisterMapper.queryImmuneRegisterInfo(providerNo);
	}
}
