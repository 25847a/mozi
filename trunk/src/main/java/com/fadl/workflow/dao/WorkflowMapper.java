package com.fadl.workflow.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.workflow.entity.Workflow;

/**
 * <p>
 * 工作流程表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-23
 */
public interface WorkflowMapper extends BaseMapper<Workflow> {
	
	/**
	 * 根据allId查找工作流程信息
	 * @param allId
	 * @return
	 */
	Workflow selectByAllId(@Param("allId")Long allId);
}
