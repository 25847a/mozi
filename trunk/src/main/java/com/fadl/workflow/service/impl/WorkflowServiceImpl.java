package com.fadl.workflow.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;
import com.fadl.workflow.service.WorkflowService;

/**
 * <p>
 * 工作流程表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-23
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {
	/**
	 * 根据allId修改流程指定的节点值
	 * @param allId
	 * @param flowStep  大流程的节点,请使用 WorkFlowEnum指定
	 * @param workStep  小流程的节点,请使用WorkFlow中具体的流程指定
	 * @param flowValue
	 * @param workValue
	 * @return
	 */
	@Override
	@Transactional(readOnly= false,propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int updateByAllIdWithWorkStepAndFlowStep(Long allId, int flowStep, int workStep, char flowValue, char workValue)throws Exception {
		Workflow workflow = baseMapper.selectByAllId(allId);
		if (workflow == null) {
			throw new Exception("不存在对应的工作流程");
		}
		// 获取大流程的具体步骤位置
		char[] flowStepC = workflow.getFlowStep().toCharArray();
		// 将小步骤分成数组供具体操作
		String[] workStepS = workflow.getWorkStep().split(",");
		// 具体的小步骤变成具体的节点方便更新
		char[] workStepC = workStepS[workStep].toCharArray();
		// 设置大流程值
		flowStepC[flowStep] = flowValue;
		// 设置小流程值
		workStepC[workStep] = workValue;
		// 设置回字段中
		workflow.setFlowStep(Arrays.toString(flowStepC));
		workStepS[workStep]=Arrays.toString(workStepC);
		workflow.setWorkStep(Arrays.toString(workStepS));
		// 更新并返回
		return baseMapper.updateById(workflow);
	}
	
	/**
	 * 根据allId 生成一条工作流程记录
	 * 初始化工作流程记录
	 * @param allId
	 * @return
	 */
	public int insertWorkflow(Long allId) {
							// 登记,体检,检验,.....
		String workStr = "2,1,000000000,00";
		Workflow workflow = new Workflow();
		workflow.setAllId(allId);
		workflow.setWorkStep(workStr);
		return baseMapper.insert(workflow);
	}

}
