package com.fadl.inspection.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.inspection.dao.BloodRedProteinContentMapper;
import com.fadl.inspection.entity.BloodRedProteinContent;
import com.fadl.inspection.entity.DetectionProteins;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.BloodRedProteinContentService;
import com.fadl.inspection.service.DetectionProteinsService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.log.service.LogService;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.workflow.common.WorkFlow;

/**
 * <p>
 * 检验-血红蛋白含量表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class BloodRedProteinContentServiceImpl extends ServiceImpl<BloodRedProteinContentMapper, BloodRedProteinContent>
		implements BloodRedProteinContentService {
	@Autowired
	private DetectionProteinsService dps;

	@Autowired
	private SpecimenCollectionService specimen;
	
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	private RefuseInfoMapper rfMapper;
    @Autowired
    public LogService logService;
	@Override
	public Page<DataRow> queryListByUpdateDate(Page<DataRow> page, BloodRedProteinContent bloodRedProteinContent) throws Exception {
		bloodRedProteinContent.setEndTime(bloodRedProteinContent.getStartTime()+" 23:59:59");
		bloodRedProteinContent.setStartTime(bloodRedProteinContent.getStartTime()+" 00:00:00");
		return page.setRecords(baseMapper.queryListByUpdateDate(page, bloodRedProteinContent));
	}

	@Override
	public DataRow queryBloodRedProteinContentById(Long id) {
		return baseMapper.queryBloodRedProteinContentById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateInfoById(BloodRedProteinContent content, DataRow dataRow) throws Exception {
		if(1==content.getIsCollection()) { // 如果是非撤回操作则要更新试剂
			// 设置项目为血红蛋白含量
			TestConfigInfo tci =  tciService.getByLableAndType("全血比重", "比重法");
			content.setTciId(tci.getId());
		}
		// 保存血红蛋白含量信息
		baseMapper.updateAllColumnById(content);
		boolean isRetracted = false; // 是否撤回
		// 撤回操作
		if (content.getIsCollection() == 0) {
			specimen.retractWorkFlow(content.getAllId(), WorkFlow.Inspections.HEMOGLOBIN_CONTENT, dataRow);
			dataRow.initSuccess();
			logService.insertLog(IConstants.MODULE_RETRACT_BRPC, IConstants.DESCRIBE.replace("{0}", content.getProviderNo())
	        		.replace("{1}", content.getProviderNo()).replace("{2}", "撤回了血红蛋白含量记录"),content.getProviderNo());
			isRetracted = true;
		} else {
			// 判定是否要走下一步操作
			specimen.nextWorkFlow(content.getAllId(), dataRow, WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal());
			//  如果结果是不合格 还要发布拒绝信息
			if(content.getBluestone()==1) {
				RefuseInfo info = new RefuseInfo();
				 // 无法找到对应的拒绝信息,只能新增
				info.setAllId(content.getAllId());
				// 此处的1为化验发布
				info.setType(1);
				info.setEliminateReason("全血比重不合格");
				info.setProviderNo(content.getProviderNo());
				// 先不发拒绝信息 rfMapper.insert(info); // 保存拒绝信息
				logService.insertLog(IConstants.MODULE_SAVE_BRPC, IConstants.DESCRIBE.replace("{0}", content.getProviderNo())
		        		.replace("{1}", content.getProviderNo()).replace("{2}", "保存了不合格的血红蛋白含量记录"),content.getProviderNo());
			}
			dataRow.initSuccess();

		}
		if(null != content.getBluestone() || isRetracted) {
			// 添加血红蛋白检测记录
			DetectionProteins proteins = new DetectionProteins();
			Wrapper<DetectionProteins> ew =  new EntityWrapper<DetectionProteins>();
			ew.eq("allId", content.getAllId());
			proteins = dps.selectOne(ew);
			// 液面下是合格,液面下对应的值是1
			proteins.setResult(""+content.getBluestone());
			if(content.getIsCollection() == 0) {
				proteins.setResult("4"); // 随意数字 只要不是0和1 其它值表示撤回
			}
			// 根据血红蛋白含量的判定结果生存的数字, 121表示合格, 108表示不合格,数字没有含义仅仅是记录
			if(proteins.getResult().equals("0")) {
				proteins.setValue("121");
			}else {
				proteins.setValue("108");
			}
			// 更新血红蛋白检测信息
			dps.updateInfoById(proteins, dataRow);
			logService.insertLog(IConstants.MODULE_SAVE_BRPC, IConstants.DESCRIBE.replace("{0}", proteins.getProviderNo())
		        		.replace("{1}", proteins.getProviderNo()).replace("{2}", "修改了血红蛋白含量记录"),proteins.getProviderNo());
		}
		
	}

	@Override
	public List<DataRow> queryInfosByChooseDate(String chooseDate) {
		return baseMapper.queryInfosByChooseDate(chooseDate);
	}

}
