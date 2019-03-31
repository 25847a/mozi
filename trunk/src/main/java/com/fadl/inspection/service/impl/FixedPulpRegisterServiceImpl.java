package com.fadl.inspection.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.inspection.dao.FixedPulpRegisterMapper;
import com.fadl.inspection.entity.FixedPulpRegister;
import com.fadl.inspection.service.FixedPulpRegisterService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.log.service.LogService;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.workflow.common.WorkFlow;

/**
 * <p>
 * 固定浆员检验登记 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-16
 */
@Service
public class FixedPulpRegisterServiceImpl extends ServiceImpl<FixedPulpRegisterMapper, FixedPulpRegister> implements FixedPulpRegisterService {
	@Autowired
	public SpecimenCollectionService specimenCollectionService;
	@Autowired
	private ProviderRegistriesMapper registriesMapper;
	@Autowired
	public LogService logService;
	
	@Override
	public Page<DataRow> queryListByCreateDateAndIsAssay(Page<DataRow> page, FixedPulpRegister pulpRegister)throws Exception {
		if(pulpRegister.getIsAssay() == 0) { 
			pulpRegister.setEndTime(pulpRegister.getStartTime()+" 23:59:59");
			Date startTime = DateUtil.formatDate(pulpRegister.getStartTime()+" 00:00:00", DateUtil.yyyyMMddHHmmss);
			pulpRegister.setStartTime(DateUtil.getStrFourteenWithDate(startTime));
		}else {
			pulpRegister.setEndTime(pulpRegister.getStartTime()+" 23:59:59");
			pulpRegister.setStartTime(pulpRegister.getStartTime()+" 00:00:00");
		}
		return page.setRecords(baseMapper.queryListByCreateDateAndIsAssay(page, pulpRegister));
	}
	/**
	 * 根据Id查找实体的方法,方法会返回浆员的部分个人信息
	 * @param entity
	 * @return
	 */
	@Override
	public DataRow queryFixedPulpRegisterById(Long id)throws Exception {
		FixedPulpRegister entity = new FixedPulpRegister();
		entity.setId(id);
		return baseMapper.queryFixedPulpRegisterByEntity(entity);
	}
	
	/**
	 * 根据AllId查找实体的方法,方法会返回浆员的部分个人信息
	 * @param entity
	 * @return
	 */
	@Override
	public DataRow queryFixedPulpRegisterByEntity(FixedPulpRegister entity) throws Exception{
		return baseMapper.queryFixedPulpRegisterByEntity(entity);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateWithCollection(FixedPulpRegister pulpRegister) throws Exception {
		baseMapper.updateWithCollection(pulpRegister);
		logService.insertLog(IConstants.MODULE_SAVE_FPR,
				IConstants.DESCRIBE.replace("{0}", pulpRegister.getProviderNo())
						.replace("{1}", pulpRegister.getProviderNo()).replace("{2}", "保存了检验登记记录"),pulpRegister.getProviderNo());
	}

	/**
	 * 固定浆员检验登记
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateAssayById(FixedPulpRegister fixedPulpRegister,DataRow dataRow) throws Exception {
		// 还要更新浆员登记表中的小样号
		ProviderRegistries registries = new ProviderRegistries();
		registries.setAllId(fixedPulpRegister.getAllId());
		registries = registriesMapper.selectOne(registries);
		registries.setSampleNo(fixedPulpRegister.getSampleNo());
		if(fixedPulpRegister.getIsAssay()==1) { // 0未登记  1已登记
			if(!specimenCollectionService.nextWorkFlow(fixedPulpRegister.getAllId(), dataRow, WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal())) {
				throw new Exception();
			}
			logService.insertLog(IConstants.MODULE_SAVE_FPR,
					IConstants.DESCRIBE.replace("{0}", fixedPulpRegister.getProviderNo())
							.replace("{1}", fixedPulpRegister.getProviderNo()).replace("{2}", "保存了检验登记记录"),fixedPulpRegister.getProviderNo());
		}else {
			if(!specimenCollectionService.retractWorkFlow(fixedPulpRegister.getAllId(), WorkFlow.Inspections.FIXED_PULP_REGISTER, dataRow)) {
				throw new Exception();
			}
			logService.insertLog(IConstants.MODULE_RETRACT_FPR,
					IConstants.DESCRIBE.replace("{0}", fixedPulpRegister.getProviderNo())
							.replace("{1}", fixedPulpRegister.getProviderNo()).replace("{2}", "撤回了检验登记记录"),fixedPulpRegister.getProviderNo());
			fixedPulpRegister.setSampleNo(null);
			registries.setSampleNo(null);
			fixedPulpRegister.setIsAssay(0);
		}
		int res = baseMapper.updateAllColumnById(fixedPulpRegister);
		logService.insertLog(IConstants.MODULE_SAVE_FPR,
				IConstants.DESCRIBE.replace("{0}", fixedPulpRegister.getProviderNo())
						.replace("{1}", fixedPulpRegister.getProviderNo()).replace("{2}", "修改了检验登记记录"),fixedPulpRegister.getProviderNo());
		int res1 = registriesMapper.updateAllColumnById(registries);
		logService.insertLog(IConstants.MODULE_SAVE_FPR,
				IConstants.DESCRIBE.replace("{0}", fixedPulpRegister.getProviderNo())
						.replace("{1}", fixedPulpRegister.getProviderNo()).replace("{2}", "修改了检验登记记录"),fixedPulpRegister.getProviderNo());
		if(res!=1 && res1!=1) {
			dataRow.initFial("更新了多个记录");
			throw new SQLException();
		}
		dataRow.initSuccess();
		return true;
	}

	@Override
	public List<DataRow> querySendInfosByUpdateDate(String updateDate) throws Exception {
		return baseMapper.querySendInfosByUpdateDate(updateDate);
	}

	@Override
	public List<DataRow> queryRefuseInfosByUpdateDate(String updateDate) throws Exception {
		return baseMapper.queryRefuseInfosByUpdateDate(updateDate);
	}

}
