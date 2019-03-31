package com.fadl.workflow.service;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.workflow.entity.Workflow;

/**
 * <p>
 * 工作流程表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-23
 */
public interface WorkflowService extends IService<Workflow> {
	/**
	 * 根据allId修改流程指定的节点值
	 * @param allId
	 * @param flowStep
	 * @param workStep
	 * @param flowValue
	 * @param workValue
	 * @return
	 */
	int updateByAllIdWithWorkStepAndFlowStep(Long allId, int flowStep, int workStep, char flowValue, char workValue)throws Exception;
	
	/**
	 * 根据allId 生成一条工作流程记录
	 * 初始化工作流程记录
	 * @param allId
	 * @return
	 */
	int insertWorkflow(Long allId) ;
}
