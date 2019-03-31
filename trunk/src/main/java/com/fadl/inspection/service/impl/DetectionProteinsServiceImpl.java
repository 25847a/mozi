package com.fadl.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.inspection.dao.DetectionProteinsMapper;
import com.fadl.inspection.entity.DetectionProteins;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.DetectionProteinsService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.log.service.LogService;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.workflow.common.WorkFlow;

/**
 * <p>
 * 检验-血红蛋白检测 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class DetectionProteinsServiceImpl extends ServiceImpl<DetectionProteinsMapper, DetectionProteins>
		implements DetectionProteinsService {

	@Autowired
	private SpecimenCollectionService specimen;

	@Autowired
	private RefuseInfoMapper rfMapper;

	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	public LogService logService;

	@Override
	public Page<DataRow> queryListByUpdateDate(Page<DataRow> page, DetectionProteins proteins) {
		return page.setRecords(baseMapper.queryListByUpdateDate(page, proteins));
	}

	@Override
	public DataRow queryDetectionProteinsById(Long id) {

		return baseMapper.queryDetectionProteinsById(id);
	}

	@Override
	public void updateInfoById(DetectionProteins proteins, DataRow dataRow) throws Exception {
		// 暂时先不更新.以后要用的时候再加
		// TestConfigInfo tci = tciService.getByLableAndType("全血比重", "比重法");

		// 更新检测记录
		baseMapper.updateAllColumnById(proteins);
		// 如果不合格 则更新拒绝信息 不再维护其他表信息
		if ("1".equals(proteins.getResult())) {
			RefuseInfo info = new RefuseInfo();
			// 不存在的情况下要新增
			info = new RefuseInfo();
			info.setAllId(proteins.getAllId());
			info.setType(1);
			info.setEliminateReason("蛋白含量值:" + proteins.getValue() + " 检测结果:不合格");
			info.setProviderNo(proteins.getProviderNo());
			// 先不发拒绝信息 rfMapper.insert(info); // 保存拒绝信息
			logService.insertLog(IConstants.MODULE_SAVE_DP,
					IConstants.DESCRIBE.replace("{0}", proteins.getProviderNo())
							.replace("{1}", proteins.getProviderNo()).replace("{2}", "保存了不合格的血红蛋白检测记录"),proteins.getProviderNo());
		} else if ("0".equals(proteins.getResult())) {
			// 走下一个流程,会判断是否3个检测都做完了.
			specimen.nextWorkFlow(proteins.getAllId(), dataRow, WorkFlow.Inspections.HEMOGLOBIN_DETECTION.ordinal());
			dataRow.initSuccess();
			logService.insertLog(IConstants.MODULE_SAVE_DP,
					IConstants.DESCRIBE.replace("{0}", proteins.getProviderNo())
							.replace("{1}", proteins.getProviderNo()).replace("{2}", "修改了血红蛋白检测记录"),proteins.getProviderNo());

		} else { // 撤回流程
			specimen.retractWorkFlow(proteins.getAllId(), WorkFlow.Inspections.HEMOGLOBIN_DETECTION, dataRow);
			dataRow.initSuccess();
			logService.insertLog(IConstants.MODULE_RETRACT_DP,
					IConstants.DESCRIBE.replace("{0}", proteins.getProviderNo())
							.replace("{1}", proteins.getProviderNo()).replace("{2}", "撤回了血红蛋白检测记录"),proteins.getProviderNo());
		}

	}

	@Override
	public List<DataRow> queryInfosByChooseDate(String chooseDate) {
		return baseMapper.queryInfosByChooseDate(chooseDate);
	}

}
