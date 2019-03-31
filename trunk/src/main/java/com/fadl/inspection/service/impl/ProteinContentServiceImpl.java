package com.fadl.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.inspection.dao.ProteinContentMapper;
import com.fadl.inspection.entity.ProteinContent;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.ProteinContentService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.log.service.LogService;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.workflow.common.WorkFlow;

/**
 * <p>
 * 检验-蛋白含量 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class ProteinContentServiceImpl extends ServiceImpl<ProteinContentMapper, ProteinContent> implements ProteinContentService {
	@Autowired
	private SpecimenCollectionService specimen;
	@Autowired
	private RefuseInfoMapper rfMapper;
	@Autowired
	private TestConfigInfoService tciService;
	@Autowired
	public LogService logService;
	@Autowired
	private ProteinContentMapper proteinContentMapper;
	@Override
	public Page<DataRow> queryListByUpdateDate(Page<DataRow> page, ProteinContent proteinContent) {
		
		return page.setRecords(baseMapper.queryListByUpdateDate(page, proteinContent));
	}

	@Override
	public DataRow queryProteinContentById(Long id) {
		return baseMapper.queryProteinContentById(id);
	}

	@Override
	public void updateInfoById(ProteinContent proteinContent, DataRow dataRow) throws Exception {
		if(proteinContent.getResult() != 2) { // 如果不是撤回操作则要更新检验配置信息到记录中
			// 设置项目为蛋白含量
			TestConfigInfo tci =  tciService.getByLableAndType("蛋白量", "折射仪法");
			proteinContent.setTciId(tci.getId());
		}
		baseMapper.updateAllColumnById(proteinContent);
		if(proteinContent.getResult()==1) {
			RefuseInfo info = new RefuseInfo();
			 // 无法找到对应的拒绝信息,只能新增
			info.setAllId(proteinContent.getAllId());
			// 此处的1为化验发布
			info.setType(1);
			info.setEliminateReason("蛋白含量值:"+proteinContent.getValue() +" 检测结果:不合格");
			info.setProviderNo(proteinContent.getProviderNo());
			// 先不发拒绝信息 rfMapper.insert(info); // 保存拒绝信息
			logService.insertLog(IConstants.MODULE_SAVE_PC,
					IConstants.DESCRIBE.replace("{0}", proteinContent.getProviderNo())
							.replace("{1}", proteinContent.getProviderNo()).replace("{2}", "保存了不合格的蛋白含量记录"),proteinContent.getProviderNo());
		
		}else if(proteinContent.getResult()==0) {
			// 走下一个流程,会判断是否3个检测都做完了.
			 specimen.nextWorkFlow(proteinContent.getAllId(), dataRow, WorkFlow.Inspections.PROTEIN_CONTENT.ordinal());
			 logService.insertLog(IConstants.MODULE_SAVE_PC,
			IConstants.DESCRIBE.replace("{0}", proteinContent.getProviderNo())
					.replace("{1}", proteinContent.getProviderNo()).replace("{2}", "修改了血红蛋白检测记录"),proteinContent.getProviderNo());
			dataRow.initSuccess();
			
		}else {
			specimen.retractWorkFlow(proteinContent.getAllId(), WorkFlow.Inspections.PROTEIN_CONTENT, dataRow);
			logService.insertLog(IConstants.MODULE_RETRACT_PC,
			IConstants.DESCRIBE.replace("{0}", proteinContent.getProviderNo())
					.replace("{1}", proteinContent.getProviderNo()).replace("{2}", "撤回了血红蛋白检测记录"),proteinContent.getProviderNo());
				dataRow.initSuccess();
			
		}
	}
	/**
	 * 查询头部信息(啊健)
	 * @param content
	 * @return
	 */
	@Override
	public void queryProteinHeadInfo(ProteinContent content, DataRow messageMap) throws Exception {
		DataRow data =proteinContentMapper.queryProteinHeadInfo(content);
		messageMap.initSuccess(data);
	}
	@Override
	public List<DataRow> queryInfosByChooseDate(String chooseDate) {
		return baseMapper.queryInfosByChooseDate(chooseDate);
	}

	
	
	
}
