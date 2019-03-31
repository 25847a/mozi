package com.fadl.immuneAssay.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.dao.ConversionRecordMapper;
import com.fadl.immuneAssay.dao.ImmuneRegisterMapper;
import com.fadl.immuneAssay.dao.ImmuneSettingMapper;
import com.fadl.immuneAssay.dao.OrdinaryInjectionMapper;
import com.fadl.immuneAssay.dao.PrivilegeInjectionMapper;
import com.fadl.immuneAssay.entity.ConversionRecord;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import com.fadl.immuneAssay.entity.ImmuneSetting;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.immuneAssay.service.ConversionRecordService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.ProviderBaseinfo;

/**
 * <p>
 * 特免转类记录表 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-27
 */
@Service
public class ConversionRecordServiceImpl extends ServiceImpl<ConversionRecordMapper, ConversionRecord> implements ConversionRecordService {

	@Autowired
	private ConversionRecordMapper conversionRecordMapper;
	@Autowired
	private ProviderBaseinfoMapper providerBaseinfoMapper;
	@Autowired
	private ImmuneRegisterMapper immuneRegisterMapper;
	@Autowired
	private OrdinaryInjectionMapper ordinaryInjectionMapper;
	@Autowired
	private PrivilegeInjectionMapper privilegeInjectionMapper;
	@Autowired
	private ImmuneSettingMapper immuneSettingMapper;
	@Autowired
	private ConfigService configService;
	@Autowired
	LogService logService;
	/**
	 * 特免转类列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> specialTransferList(Map<String,String> map,Page<DataRow> page) throws Exception {
		Config config = configService.queryConfig("immune_type","1");
		if(config.getIsDisable()==1) {//被禁用了
			map.put("type", "2");
		}
		return page.setRecords(conversionRecordMapper.specialTransferList(map,page));
	}
	/**
	 * 特免转类  头部内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public void querySpecialTransferHead(Map<String,String> map,DataRow messageMap) throws Exception {
		DataRow result=  conversionRecordMapper.querySpecialTransferHead(map);
		if(result != null) {
			messageMap.initSuccess(result);
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 特免转类  转类
	 * @param providerNo
	 * @param thisStatus
	 * @param transferType
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void transferType(String providerNo,String thisStatus,String transferType,DataRow messageMap) throws Exception {
		EntityWrapper<ImmuneRegister> imm = new EntityWrapper<ImmuneRegister>();
		imm.eq("providerNo", providerNo);
		imm.eq("immuneId", transferType);
		List<ImmuneRegister> register = immuneRegisterMapper.selectList(imm);
		if(register.isEmpty()) {
			//基本信息设置 thisStatus当前状态    transferType转为类别
			ImmuneSetting setting =immuneSettingMapper.selectById(transferType);
			DecimalFormat df = new DecimalFormat("000000");
			String ordinaryNo = setting.getTypeCode()+df.format(setting.getNum()+1);//免疫编号
			ImmuneRegister ir = new ImmuneRegister();//免疫登记表
			ProviderBaseinfo baseInfo = new ProviderBaseinfo();//浆员信息
			EntityWrapper<ProviderBaseinfo> ew = new EntityWrapper<>();
			ew.eq("providerNo", providerNo);
			//如果没有普免，都是普通的话
			if(setting.getType()==0) {//如果为普通类型,更新baseinfo表，免疫登记表，如果等于0，说明浆员还没选择免疫类型，就只能插入到登记表
					ir.setProviderNo(providerNo);
					ir.setType(0);
					baseInfo.setType(0);
					//为什么要做如此判断呢？因为有两个版本的免疫管理,如果乙肝为普通类型,type=0,如果乙肝为普免类型,type=1
					Config config = configService.queryConfig("immune_type","1");
					if(config.getIsDisable()==1) {//==1被禁用,乙肝为0,普通类型
						//如果没有普免状态,乙肝在免疫登记插入已通过，往普免注射插入一针，baseinfo 更新
						ir.setOrdinaryNo(ordinaryNo);
						ir.setImmuneId(Long.valueOf(transferType));
						ir.setStatus(0);
						OrdinaryInjection or = new OrdinaryInjection();
						or.setProviderNo(providerNo);
						or.setImmuneId(Long.valueOf(transferType));
						or.setNum(1);
						or.setIsShot(0);
						ordinaryInjectionMapper.insert(or);
						baseInfo.setImmuneId(transferType);
						baseInfo.setImmuneRegisterId(ordinaryNo);
					}else {
						ir.setOrdinaryNo(null);//彻底清除免疫类型
						ir.setImmuneId(null);
						ir.setStatus(1);
						//更改浆员基本信息表浆员状态
						baseInfo.setImmuneId(null);
						baseInfo.setImmuneRegisterId(null);
					}
					immuneRegisterMapper.insert(ir);
					providerBaseinfoMapper.update(baseInfo, ew);
			}else if(setting.getType()==1) {
				//普免   在免疫登记插入已通过，往普免注射插入一针，baseinfo 更新
				ir.setProviderNo(providerNo);
				ir.setOrdinaryNo(ordinaryNo);
				ir.setType(1);
				ir.setImmuneId(Long.valueOf(transferType));
				ir.setStatus(0);
				immuneRegisterMapper.insert(ir);
				OrdinaryInjection or = new OrdinaryInjection();
				or.setProviderNo(providerNo);
				or.setImmuneId(Long.valueOf(transferType));
				or.setNum(1);
				or.setIsShot(0);
				ordinaryInjectionMapper.insert(or);
				baseInfo.setType(1);
				baseInfo.setImmuneId(transferType);
				baseInfo.setImmuneRegisterId(ordinaryNo);
				providerBaseinfoMapper.update(baseInfo, ew);
			}else if(setting.getType()==2) {
				//特免   在免疫登记插入已通过，往特免注射插入一针，baseinfo 更新
				ir.setProviderNo(providerNo);
				ir.setOrdinaryNo(ordinaryNo);
				ir.setType(2);
				ir.setImmuneId(Long.valueOf(transferType));
				ir.setStatus(0);
				immuneRegisterMapper.insert(ir);
				//插入记录到特免基础注射表
				PrivilegeInjection pi = new PrivilegeInjection();
				pi.setProviderNo(providerNo);
				pi.setImmuneId(Long.valueOf(transferType));
				pi.setNum(1);
				pi.setIsShot(0);
				privilegeInjectionMapper.insert(pi);
				//更改浆员基本信息表浆员状态
				baseInfo.setType(2);
				baseInfo.setImmuneId(transferType);
				baseInfo.setImmuneRegisterId(ordinaryNo);
				providerBaseinfoMapper.update(baseInfo, ew);
			}
			//更新免疫类别设置表
			setting.setNum(setting.getNum()+1);
			immuneSettingMapper.updateById(setting);
			//插入记录到特免转类记录表
			ConversionRecord record = new ConversionRecord();
			record.setProviderNo(Long.valueOf(providerNo));
			record.setImmuneId(Long.valueOf(transferType));
			record.setPrimaryImmuneId(Long.valueOf(thisStatus));
			conversionRecordMapper.insert(record);
			//最后插入日志
			logService.insertLog(IConstants.transferType, IConstants.DESC.replace("{0}", "已特免转类,浆员卡号"+providerNo),providerNo);
			messageMap.initSuccess();
		}else {
			messageMap.initFial("已登记过的免疫类型,不可转换,请重新选择");
		}
		
	}
	/**
	 * 已转类浆员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> conversionRecordList(Map<String,String> map,Page<DataRow> page) throws Exception {
		return page.setRecords(conversionRecordMapper.conversionRecordList(map,page));
	}
}
