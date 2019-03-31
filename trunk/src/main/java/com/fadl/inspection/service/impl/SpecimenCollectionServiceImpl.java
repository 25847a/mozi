package com.fadl.inspection.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.inspection.dao.BloodRedProteinContentMapper;
import com.fadl.inspection.dao.DetectionProteinsMapper;
import com.fadl.inspection.dao.NewCardMapper;
import com.fadl.inspection.dao.ProteinContentMapper;
import com.fadl.inspection.dao.SpecimenCollectionMapper;
import com.fadl.inspection.entity.BloodRedProteinContent;
import com.fadl.inspection.entity.DetectionProteins;
import com.fadl.inspection.entity.FixedPulpRegister;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.entity.ProteinContent;
import com.fadl.inspection.entity.SpecimenCollection;
import com.fadl.inspection.service.FixedPulpRegisterService;
import com.fadl.inspection.service.SpecimenCollectionService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.common.WorkFlow.Inspections;
import com.fadl.workflow.common.WorkFlow.WorkFlowEnum;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;
import com.fadl.yell.dao.PlasmYellMapper;
import com.fadl.yell.entity.PlasmYell;

/**
 * <p>
 * 检验-标本采集 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Service
public class SpecimenCollectionServiceImpl extends ServiceImpl<SpecimenCollectionMapper, SpecimenCollection>
		implements SpecimenCollectionService {
	@Autowired
	private WorkflowMapper workflowMapper;
	@Autowired
	private ProviderRegistriesMapper registriesMapper;
	@Autowired
	private PlasmYellMapper yellMapper;
	@Autowired
	private BloodRedProteinContentMapper redProteinContentMapper;
	@Autowired
	private DetectionProteinsMapper proteinsMapper;
	@Autowired
	private ProteinContentMapper contentMapper;
	@Autowired
	private NewCardMapper newCardMapper;
	@Autowired
	private SystemSeqService seqService; 
	@Autowired
	private FixedPulpRegisterService fprService;
	@Autowired
	private ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public LogService logService;
	
	/**
	 * 获取血浆采集记录,带分页和日期查询
	 * @throws Exception 
	 */
	public Page<DataRow> queryListByCreateDateAndIsCollection(Page<DataRow> page, SpecimenCollection collection) throws Exception {
		if(collection.getIsCollection() == 0) { // 如果是查未采集记录 则要查询14天内的
			collection.setEndTime(collection.getStartTime()+" 23:59:59");
			Date startTime = DateUtil.formatDate(collection.getStartTime()+" 00:00:00", DateUtil.yyyyMMddHHmmss);
			collection.setStartTime(DateUtil.getStrFourteenWithDate(startTime));
		}else {
			collection.setEndTime(collection.getStartTime()+" 23:59:59");
			collection.setStartTime(collection.getStartTime()+" 00:00:00");
		}
		return page.setRecords(baseMapper.queryListByCreateDateAndIsCollection(page, collection));
	}

	/**
	 * 根据ID查找实体的方法,方法会返回浆员的部分个人信息
	 */
	public DataRow querySpecimenCollectionByEntity(Long id) {
		return baseMapper.querySpecimenCollectionByEntity(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateWithCollection(SpecimenCollection collection) {
		baseMapper.updateWithCollection(collection);
		try {
			logService.insertLog(IConstants.MODULE_TCI, IConstants.DESC.replace("{0}", "更新浆员号: "+collection.getProviderNo()+"的小样状态为: "+(collection.getIsCollection()==0?"未采集":"已采集")),collection.getProviderNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateEntityById(SpecimenCollection collection, DataRow dataRow) throws Exception{
		
		// 如果更新为未采集则要做撤回操作
		if (0 == collection.getIsCollection()) {
			if( !retractWorkFlow(collection.getAllId(),WorkFlow.Inspections.REGISTER,dataRow)) {
				throw new Exception();
			}
			collection.setSampleNo("");
			try {
				logService.insertLog(IConstants.MODULE_TCI, IConstants.DESC.replace("{0}", "撤回了浆员号: "+collection.getProviderNo()+"的小样状态为: "+(collection.getIsCollection()==0?"未采集":"已采集")),collection.getProviderNo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				logService.insertLog(IConstants.MODULE_TCI, IConstants.DESC.replace("{0}", "修改了浆员号: "+collection.getProviderNo()+"的信息"),collection.getProviderNo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		baseMapper.updateById(collection);
		return true;
	}



	/**
	 * 根据allId更新下一步操作 没有事务,请在service中调用
	 * @param allId
	 * @param dataRow
	 * @param int  子流程
	 * @return boolean
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean nextWorkFlow(Long allId, DataRow dataRow, int wflow) throws Exception {
		Workflow wf = workflowMapper.selectByAllId(allId);
		if (wf == null) {
			dataRow.initFial("不存在对应的工作流程");
			throw new Exception();
		}
		if(wflow == 6) {
			WorkFlow wfEnum = WorkFlowEnum.INSPECTIONS;
			// 判断是否在检测流程中
			if (wf.getFlowStep().lastIndexOf("1") != WorkFlowEnum.valueOf(wfEnum.toString()).ordinal()) {
				dataRow.initFial("已经不在检测的流程中");
				throw new Exception("已经不在检测的流程中");
			}
		}
		// 获得当前的小流程记录
		String[] workStep = wf.getWorkStep().split(",");
		String workSteps = workStep[WorkFlowEnum.INSPECTIONS.ordinal()];
		char[] flowStep = wf.getFlowStep().toCharArray();
		char[] workStepc = workSteps.toCharArray();
		// 判断当前操作的节点是否能操作
		if(workStepc[wflow]!='1') {
			dataRow.initFial("已经不在当前流程中");
			throw new Exception("已经不在当前流程中");
		}
		// 这里需要根据浆员信息操作具体表
		ProviderRegistries entity = new ProviderRegistries();
		entity.setAllId(allId);
		entity = registriesMapper.selectOne(entity);
		// 标本采集流程
		if(Inspections.REGISTER.ordinal() == wflow || Inspections.SMALL_BLOOD.ordinal() == wflow) {
			String providerNo = entity.getProviderNo();
			// 先插入三个小流程记录
			BloodRedProteinContent bloodRedProtein = new BloodRedProteinContent();
			bloodRedProtein.setAllId(allId);
			bloodRedProtein.setProviderNo(providerNo);
			bloodRedProtein.setIsCollection(0);
			DetectionProteins detectionProteins = new DetectionProteins();
			detectionProteins.setAllId(allId);
			detectionProteins.setProviderNo(providerNo);
			ProteinContent proteinContent = new ProteinContent();
			proteinContent.setAllId(allId);
			proteinContent.setProviderNo(providerNo);
			redProteinContentMapper.insert(bloodRedProtein);
			proteinsMapper.insert(detectionProteins);
			contentMapper.insert(proteinContent);
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			// 标记酶标板为流程进行中
			workStepc[wflow] = '2';
			workStepc[WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.HEMOGLOBIN_DETECTION.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.PROTEIN_CONTENT.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if(workflowMapper.updateById(wf)!=1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			return true;
		}else if(Inspections.HEMOGLOBIN_CONTENT.ordinal() == wflow||Inspections.HEMOGLOBIN_DETECTION.ordinal() == wflow||Inspections.PROTEIN_CONTENT.ordinal() == wflow) {
			// 操作血红蛋白含量流程
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			workStepc[wflow] = '2';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if(workflowMapper.updateById(wf)!=1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			// 检测是否三个流程完成了,如果完成则继续下一个步骤
			is3Off(allId, String.valueOf(workStepc), dataRow, entity, flowStep, workStepc, workStep, wf);
			return true;
		}else if(Inspections.FIXED_PULP_REGISTER.ordinal() == wflow) {
			// 操作固定浆员检验登记流程
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal()] = '2';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if(workflowMapper.updateById(wf)!=1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			Wrapper<BloodRedProteinContent > ew = new EntityWrapper<BloodRedProteinContent>();
			ew.eq("allId", allId);
			List<BloodRedProteinContent> brpcs = redProteinContentMapper.selectList(ew);
			if (brpcs.size() == 0) {
				dataRow.initFial("没有血红蛋白含量信息");
				throw new Exception("没有血红蛋白含量信息");
			}
			BloodRedProteinContent bloodRedProtein = brpcs.get(0);
			// 获取血红蛋白含量信息
			DetectionProteins detectionProteins = new DetectionProteins();
			detectionProteins.setAllId(allId);
			detectionProteins = proteinsMapper.selectOne(detectionProteins); // 获取血红蛋白检测信息
			if (detectionProteins == null) {
				dataRow.initFial("没有血红蛋白检测信息");
				throw new Exception("没有血红蛋白检测信息");
			}
			ProteinContent proteinContent = new ProteinContent();
			proteinContent.setAllId(allId);
			proteinContent = contentMapper.selectOne(proteinContent); // 获取蛋白含量信息
			if (proteinContent == null) {
				dataRow.initFial("没有蛋白含量信息");
				throw new Exception("没有蛋白含量信息");
			}
			return saveNewCard(dataRow, flowStep, workStepc, workStep, wf, entity, bloodRedProtein,proteinContent,detectionProteins);
		}else if(Inspections.NEW_CARD_ASSAY.ordinal() == wflow) {
			// 新卡化验
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if(workflowMapper.updateById(wf)!=1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			is2Off(allId,  String.valueOf(workStepc), dataRow, entity, flowStep, workStepc, workStep, wf);
			return true;
			
		}else if(Inspections.PLASMA_ASSAY.ordinal() == wflow) {
			// 浆员化验
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.PLASMA_ASSAY.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if(workflowMapper.updateById(wf)!=1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			is2Off(allId,  String.valueOf(workStepc), dataRow, entity, flowStep, workStepc, workStep, wf);
			return true;
		}
		return true;
	}

	/**
	 * 根据allId撤回下一步操作
	 * 			没有事务,请在service中调用
	 * 			如果在3个流程中,则需要判断是否为true的情况下dataRow返回的是否为Fail 
	 * 失败标识3个流程还有流程需要撤回,如果为成功则标识3个流程都已经撤回,并且步骤回到标本采集
	 * @param allId
	 * @param dataRow
	 * @param WorkFlow
	 * @return boolean
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean retractWorkFlow(Long allId, WorkFlow wfs, DataRow dataRow) throws Exception {
		Workflow wf = workflowMapper.selectByAllId(allId);
		if (wf == null) {
			dataRow.initFial("不存在对应的工作流程");
			throw new Exception("不存在对应的工作流程");
		}
		// 获取大流程的具体步骤位置
		char[] flowStep = wf.getFlowStep().toCharArray();
		// 将小步骤分成数组供具体操作
		String[] workStep = wf.getWorkStep().split(",");
		// 指定小步骤中的检验步骤
		char[] workStepc = workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()].toCharArray();
		WorkFlow wfEnum = WorkFlowEnum.INSPECTIONS;
		// 判断是否在检测流程中
		if (wf.getFlowStep().lastIndexOf("1") != WorkFlowEnum.valueOf(wfEnum.toString()).ordinal()) {
			dataRow.initFial("已经不在检测的流程中");
			throw new Exception("已经不在检测的流程中");
		}
		// 这里需要根据浆员信息操作具体表
		ProviderRegistries registries = new ProviderRegistries();
		registries.setAllId(allId);
		registries = registriesMapper.selectOne(registries);
		// 获得当前的小流程记录
		String checked = workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()];
		if (wfs == WorkFlow.Inspections.NEW_CARD_ASSAY || wfs == WorkFlow.Inspections.PLASMA_ASSAY) {
			// 获得2个检测小流程的记录
			String checked2 = checked.substring(WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal(), WorkFlow.Inspections.PLASMA_ASSAY.ordinal());
			if (checked2.indexOf("2") > -1) {
				dataRow.initFial("还有检测没有撤回不能继续撤回主流程");
				throw new Exception("还有检测没有撤回不能继续撤回主流程");
			} else {
				// 先删除采浆叫号信息
				Wrapper<PlasmYell> ew = new EntityWrapper<PlasmYell>();
				ew.eq("allId", allId);
				if (1 < yellMapper.delete(ew)) {
					dataRow.initFial("删除的记录不是1条");
					throw new Exception("删除的记录不是1条");
				}
				// 更新工作流程表
				flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
				// 标记酶标板为流程进行中
				workStepc[WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal()] = '0';
				workStepc[WorkFlow.Inspections.PLASMA_ASSAY.ordinal()] = '0';
				workStepc[WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal()] = '1';
				workStepc[WorkFlow.Inspections.HEMOGLOBIN_DETECTION.ordinal()] = '1';
				workStepc[WorkFlow.Inspections.PROTEIN_CONTENT.ordinal()] = '1';
				wf.setFlowStep(String.valueOf(flowStep));
				workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
				wf.setWorkStep(StringUtils.join(workStep,","));
				if (workflowMapper.updateById(wf) != 1) {
					dataRow.initFial("更新了多条工作流程记录");
					throw new Exception("更新了多条工作流程记录");
				}
				dataRow.initSuccess();
				return true;
			}
			// 如果是三个流程中的一个进入这里
		} else if (wfs == WorkFlow.Inspections.HEMOGLOBIN_CONTENT || wfs == WorkFlow.Inspections.HEMOGLOBIN_DETECTION
				|| wfs == WorkFlow.Inspections.PROTEIN_CONTENT) {
			// 获得3个检测小流程的记录
			String checked3 = checked.substring(WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal(), WorkFlow.Inspections.PROTEIN_CONTENT.ordinal()+1);
			// 判断是否完成了3个检测小流程
			if (!"000".equals(checked3)) {
				// 更新工作流程表
				flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
				workStepc[WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal()] = '0';
				workStepc[WorkFlow.Inspections.PLASMA_ASSAY.ordinal()] = '0';
				workStepc[WorkFlow.Inspections.valueOf(wfs.toString()).ordinal()] = '1';
				wf.setFlowStep(String.valueOf(flowStep));
				workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
				wf.setWorkStep(StringUtils.join(workStep,","));
				if (workflowMapper.updateById(wf) != 1) {
					dataRow.initFial("更新了多条工作流程记录");
					throw new Exception("更新了多条工作流程记录");
				}
				if (is3Retract(allId, String.valueOf(workStepc), dataRow, registries,wfs, flowStep, workStepc, workStep, wf)) {
					dataRow.initSuccess();
					return true;
				} else {
					dataRow.initFial("还有流程需要撤回");
					return true;
				}
			} else {
				return is3Retract(allId, String.valueOf(workStepc), dataRow, registries,wfs, flowStep, workStepc, workStep, wf);
			}
		} else if (wfs == WorkFlow.Inspections.REGISTER || wfs == WorkFlow.Inspections.SMALL_BLOOD) {
			// 获得采集流程的记录
			String checked1 = checked.substring(WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal(), WorkFlow.Inspections.PROTEIN_CONTENT.ordinal()+1);
			if (checked1.indexOf("2")!=-1) {
				dataRow.initFial("已经不在采集的流程中");
				throw new Exception("已经不在采集的流程中");
			}
			String checked2 = checked.substring(WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal(), WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal()+1);
			if (checked2.indexOf("2")!=-1) {
				dataRow.initFial("已经检验登记了");
				throw new Exception("已经检验登记了");
			}
			// 撤回标本采集记录,回撤检验流程
			Wrapper<BloodRedProteinContent> ew1 = new EntityWrapper<BloodRedProteinContent>();
			ew1.eq("allId", allId);
			redProteinContentMapper.delete(ew1);
			Wrapper<DetectionProteins> ew2 = new EntityWrapper<DetectionProteins>();
			ew2.eq("allId", allId);
			proteinsMapper.delete(ew2);
			Wrapper<ProteinContent> ew3 = new EntityWrapper<ProteinContent>();
			ew3.eq("allId", allId);
			contentMapper.delete(ew3);
			// 撤回检验登记记录
			Wrapper<FixedPulpRegister> ew4 = new EntityWrapper<FixedPulpRegister>();
			ew4.eq("allId", allId);
			fprService.delete(ew4);
			// 撤回自身流程数据
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			workStepc[Inspections.valueOf(wfs.toString()).ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if (workflowMapper.updateById(wf) != 1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception("更新了多条工作流程记录");
			}
			dataRow.initSuccess();
			return true;

		} else { // 检验登记
			if(wfs == WorkFlow.Inspections.FIXED_PULP_REGISTER) {
				// 判断后面的流程是否已经有做
				String checked1 = checked.substring(WorkFlow.Inspections.HEMOGLOBIN_CONTENT.ordinal(), WorkFlow.Inspections.PROTEIN_CONTENT.ordinal()+1);
				if (checked1.indexOf("2")!=-1) {
					dataRow.initFial("已经不在登记的流程中");
					throw new Exception("已经不在登记的流程中");
				}
				
				// 删除掉新老卡记录
				NewCard nc =  new NewCard();
				
				nc.setAllId(allId); 
				nc = newCardMapper.selectOne(nc);
				newCardMapper.deleteById(nc.getId());
				// 撤回自身流程数据
				flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
				workStepc[Inspections.valueOf(wfs.toString()).ordinal()] = '1';
				workStepc[Inspections.NEW_CARD_ASSAY.ordinal()] = '0';
				workStepc[Inspections.PLASMA_ASSAY.ordinal()] = '0';
				wf.setFlowStep(String.valueOf(flowStep));
				workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
				wf.setWorkStep(StringUtils.join(workStep,","));
				if (workflowMapper.updateById(wf) != 1) {
					dataRow.initFial("更新了多条工作流程记录");
					throw new Exception("更新了多条工作流程记录");
				}
			}
			return true;
		}
	}
	/**
	 * 3个小流程的整体撤回,如果有一个流程不是0的情况下不会执行,返回false
	 * @param allId
	 * @param checked
	 * @param dataRow
	 * @param registries
	 * @param flowStep
	 * @param workStepc
	 * @param workStep
	 * @param wf
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean is3Retract(Long allId, String checked, DataRow dataRow, ProviderRegistries registries,WorkFlow wfs, char[] flowStep, char []workStepc, String []workStep ,Workflow wf)throws Exception {
		String checked3 = checked;
		// 判断是否完成了3个检测小流程
		if (!"000".equals(checked3)) {
			
			return false;
		}else {
			Wrapper<NewCard> ew = new EntityWrapper<NewCard>();
			ew.eq("allId", allId);
			if (1 < newCardMapper.delete(ew)) {
				dataRow.initFial("删除的记录多过1条");
				return false;
			}
			// 更新工作流程表
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			// 标记酶标板为流程进行中
			workStepc[Inspections.valueOf(wfs.toString()).ordinal()] = '1';
			workStepc[WorkFlow.Inspections.REGISTER.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
			if (workflowMapper.updateById(wf) != 1) {
				dataRow.initFial("更新了多条工作流程记录");
				throw new Exception();
			}
			dataRow.initSuccess();
			return true;
		}
	}
	/**
	 * 判断2个卡化验流程是否完成,并进入下一阶段
	 * @param allId   		全登记号
	 * @param checked		小流程信息 
	 * @param dataRow
	 * @param registries	浆员信息
	 * @param flowStep		大流程字符数组
	 * @param workStepc		小流程数组
	 * @param workStep		小流程全流程信息
	 * @param wf			工作流程表
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean is2Off(Long allId, String checked, DataRow dataRow, ProviderRegistries registries, char[] flowStep, char []workStepc, String []workStep ,Workflow wf) throws Exception{
		// 获取供浆员化验和新卡化验的流程记录
		String checked2 = checked.substring(Inspections.NEW_CARD_ASSAY.ordinal(), Inspections.PLASMA_ASSAY.ordinal());
		// 判定供浆员化验和新卡化验是否有做一个.有做一个就判定完成了
		if (checked2.indexOf("2") != -1) {
			dataRow.initFial("已经完成了检验相关流程");
			return false;
		}
		
		

		// 标记检测流程进行中
		flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '2';
		// 如果采浆没有开始则要添加 
		if(flowStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] == '0') {
			flowStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = '1';
			char[] workStepc1= workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()].toCharArray();
			workStepc1[WorkFlow.Collection.COLLECTION.ordinal()] = '1';
			workStep[WorkFlow.Collection.COLLECTION.ordinal()] = String.valueOf(workStepc1);
			PlasmYell yell = new PlasmYell();
			yell.setAllId(allId);
			yell.setAssayDate(DateUtil.getSystemTime(new Date()));
			yell.setProviderNo(registries.getProviderNo());
			int count = yellMapper.insert(yell);
			if (count != 1) {
				dataRow.initFial("插入采浆记录错误");
				throw new Exception("插入采浆记录错误");
			}
		}
		
		// 标记酶标板为流程进行中
		workStepc[WorkFlow.Inspections.ELISA_PLATE_DETECTION.ordinal()] = '2';
		workStepc[WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal()] = '2';
		workStepc[WorkFlow.Inspections.PLASMA_ASSAY.ordinal()] = '2';
		wf.setFlowStep(String.valueOf(flowStep));
		workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
		
		wf.setWorkStep(StringUtils.join(workStep,","));
		if (workflowMapper.updateById(wf) != 1) {
			dataRow.initFial("更新了多条工作流程记录");
			throw new Exception("更新了多条工作流程记录");
		}
		dataRow.initSuccess();
		return true;
	}

	/**
	 * /**
	 * 判断3个小检测流程是否完成
	 * @param allId   		全登记号
	 * @param checked		小流程信息 
	 * @param dataRow
	 * @param registries	浆员信息
	 * @param flowStep		大流程字符数组
	 * @param workStepc		小流程数组
	 * @param workStep		小流程全流程信息
	 * @param wf			工作流程表
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean is3Off(Long allId, String checked, DataRow dataRow, ProviderRegistries registries, char[] flowStep, char []workStepc, String []workStep ,Workflow wf) throws Exception{
		String checked3 = checked.substring(Inspections.HEMOGLOBIN_CONTENT.ordinal(), Inspections.PROTEIN_CONTENT.ordinal()+1);
		// 判断是否完成了3个检测小流程
		if (!"222".equals(checked3)) {
			dataRow.initFial("还没有完成三项检测");
			return false;
		}
		Wrapper<BloodRedProteinContent > ew = new EntityWrapper<BloodRedProteinContent>();
		ew.eq("allId", allId);
		List<BloodRedProteinContent> brpcs = redProteinContentMapper.selectList(ew);
		if (brpcs.size() == 0) {
			dataRow.initFial("没有血红蛋白含量信息");
			throw new Exception();
		}
		BloodRedProteinContent bloodRedProtein = brpcs.get(0);
		// 获取血红蛋白含量信息
		DetectionProteins detectionProteins = new DetectionProteins();
		detectionProteins.setAllId(allId);
		detectionProteins = proteinsMapper.selectOne(detectionProteins); // 获取血红蛋白检测信息
		if (detectionProteins == null) {
			dataRow.initFial("没有血红蛋白检测信息");
			throw new Exception();
		}
		ProteinContent proteinContent = new ProteinContent();
		proteinContent.setAllId(allId);
		proteinContent = contentMapper.selectOne(proteinContent); // 获取蛋白含量信息
		if (proteinContent == null) {
			dataRow.initFial("没有蛋白含量信息");
			throw new Exception();
		}
		// 固定浆员 去到采浆记录
		if (registries.getPlasmaType() != 0) {
			PlasmYell yell = new PlasmYell();
			yell.setAllId(allId);
			yell.setProviderNo(registries.getProviderNo());
			int count = yellMapper.insert(yell);
			if (count != 1) {
				dataRow.initFial("插入采浆记录错误");
				throw new Exception();
			}
			// 标记检测流程进行中
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			flowStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = '1';
			// 标记酶标板为流程进行中
			workStepc[WorkFlow.Inspections.ELISA_PLATE_DETECTION.ordinal()] = '1';
			char[] workStepc1= workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()].toCharArray();
			workStepc1[WorkFlow.Collection.COLLECTION.ordinal()] = '1';
			workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = String.valueOf(workStepc1);
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setFlowStep(String.valueOf(flowStep));
			wf.setWorkStep(StringUtils.join(workStep,","));
			dataRow.initSuccess();
		}
		saveNewCard(dataRow, flowStep, workStepc, workStep, wf, registries, bloodRedProtein, proteinContent,detectionProteins);
		dataRow.initSuccess();
		
		return true;

	}
	/**
	 * 新增一条新老卡化验记录
	 * @param dataRow
	 * @param flowStep
	 * @param workStepc
	 * @param workStep
	 * @param wf
	 * @param registries
	 * @param bloodRedProtein
	 * @param proteinContent
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private boolean saveNewCard(DataRow dataRow,char[] flowStep, char []workStepc,String []workStep ,Workflow wf, ProviderRegistries registries, BloodRedProteinContent bloodRedProtein,ProteinContent proteinContent,DetectionProteins detectionProteins )throws Exception {
		Wrapper<ProviderBaseinfo> wrapper = new EntityWrapper<ProviderBaseinfo>();
		wrapper.eq("providerNo", registries.getProviderNo());
		boolean isInsert= true;
		ProviderBaseinfo baseinfo = providerBaseinfoService.selectOne(wrapper);
		NewCard card = new NewCard();
		Wrapper<NewCard> ew =new EntityWrapper<NewCard>();
		ew.eq("allId", registries.getAllId());
		List<NewCard> cards = newCardMapper.selectList(ew);
		if(cards.size()>0) {
			card = cards.get(0);
			isInsert = false;
		}
		card.setAllId(registries.getAllId());
		card.setSampleNo(registries.getSampleNo());
		card.setProviderNo(registries.getProviderNo());
		card.setBlood(""+baseinfo.getBloodType());
		// 血红蛋白含量
		card.setBloodRedProtein(bloodRedProtein.getResultId());
		card.setBloodRedProteinValue(detectionProteins.getValue());
		// 蛋白含量
		card.setProtein(""+proteinContent.getResult());
		card.setProteinValue(proteinContent.getProtein());
		card.setSerumProtein(proteinContent.getProtein());
		// 血红蛋白含量 设置是否合格,不存实际的值
		card.setWholeBlood(""+bloodRedProtein.getBluestone());
		
		// 如果是新卡则生成新卡化验记录
		if (registries.getIsNew() == 0) {
			card.setType(0); // 设置新卡化验
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			// 标记酶标板为流程进行中
			workStepc[WorkFlow.Inspections.ELISA_PLATE_DETECTION.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.NEW_CARD_ASSAY.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			String workStepStr = "";
			for(String str : workStep) {
				workStepStr = workStepStr + str+ ",";
			}
			workStepStr = workStepStr.substring(0,workStepStr.length()-1);
			wf.setWorkStep(workStepStr);
		} else {
			card.setType(1); // 非固定浆员生成
			// 标记检测流程进行中
			flowStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = '1';
			// 标记酶标板为流程进行中
			workStepc[WorkFlow.Inspections.ELISA_PLATE_DETECTION.ordinal()] = '1';
			workStepc[WorkFlow.Inspections.PLASMA_ASSAY.ordinal()] = '1';
			wf.setFlowStep(String.valueOf(flowStep));
			workStep[WorkFlow.WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(workStepc);
			wf.setWorkStep(StringUtils.join(workStep,","));
		}
		if (workflowMapper.updateById(wf) != 1) {
			dataRow.initFial("更新了多条工作流程记录");
			throw new Exception();
		}
		int count = 0;
		if(isInsert) {
			count = newCardMapper.insert(card);
		}else {
			count = newCardMapper.updateAllColumnById(card);
		}
		if (count == 1) {
			dataRow.initSuccess();
		} else {
			dataRow.initFial("插入新老卡化验 记录错误");
			throw new Exception();
		}
		return true;
	}
	
	/**
	 * 浆员采小血打印小票
	 */
	@Override
	public HashMap<String, String> printSpecimenCollection(Long id, Long isSpecimen) throws Exception {
		return baseMapper.printSpecimenCollection(id, isSpecimen);
	}

	@Override
	public List<String> selectAllSampleNoByDateStr(String dateStr) throws Exception {
		return baseMapper.selectAllSampleNoByDateStr(dateStr);
	}
	/**
	 * 根据实体集合更新标本采集属性
	 *
	 * @param collections
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateBatchEntityByIds(List<SpecimenCollection> collections, DataRow dataRow) throws Exception {
			// 批量更新
			for(SpecimenCollection collection : collections) {
				// 检测是否可以走下一流程
				if(!nextWorkFlow(collection.getAllId(), dataRow, WorkFlow.Inspections.REGISTER.ordinal())) {
					throw new Exception();
				}
				// 添加到检验登记
				FixedPulpRegister register = new FixedPulpRegister();
				register.setAllId(collection.getAllId());
				register.setIsAssay(0);
				register.setPlasmaId(collection.getPlasmaId());
				register.setProviderNo(collection.getProviderNo());
				if(StringUtils.isNotBlank(collection.getSendPerson()))
				register.setSendPerson(Long.parseLong(collection.getSendPerson()));
				fprService.insert(register);
				// 更新workflow的检验登记状态
				Workflow wf = workflowMapper.selectByAllId(collection.getAllId());
				String[] ws = wf.getWorkStep().split(",");
				String workSteps = ws[WorkFlowEnum.INSPECTIONS.ordinal()];
				char[] wsc = workSteps.toCharArray();
				wsc[WorkFlow.Inspections.FIXED_PULP_REGISTER.ordinal()] ='1';
				ws[WorkFlowEnum.INSPECTIONS.ordinal()] = String.valueOf(wsc);
				wf.setWorkStep(StringUtils.join(ws,","));
				workflowMapper.updateById(wf);
				// 更新当前记录
				if(updateEntityById(collection, dataRow)) {
					dataRow.initSuccess();
				}
			}
		return true;
	}
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	@Override
	public List<DataRow> querySendInfosByUpdateDate(String updateDate) throws Exception {
		return baseMapper.querySendInfosByUpdateDate(updateDate);
	}
}
