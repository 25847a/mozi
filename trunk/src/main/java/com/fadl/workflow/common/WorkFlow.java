package com.fadl.workflow.common;
/**
 * <p>
 * 工作流程 枚举类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-23
 */
public interface WorkFlow {
	enum WorkFlowEnum implements WorkFlow{
		/**
		 * 登记流程
		 */
		REGISTRIES,
		/**
		 * 体检流程
		 */
		CHECKS,
		/**
		 * 检验流程
		 */
		INSPECTIONS,
		/**
		 * 采浆流程
		 */
		COLLECTION
	}
	/**
	 * 
	 *  注册流程用小流程
	 *
	 */
	enum Registries implements WorkFlow {
		
	}
	
	/**
	 * 体检流程用小流程
	 *
	 */
	enum Checks implements WorkFlow {
		/**
		 * 体检
		 */
		CHECK
	}
	
	/**
	 * 检验流程用小流程枚举
	 *
	 */
	enum Inspections implements WorkFlow {
		/**
		 * 标本采集
		 */
		REGISTER,
		/**
		 * 采小血
		 */
		SMALL_BLOOD,
		/**
		 * 固定浆员检验登记
		 */
		FIXED_PULP_REGISTER,
		/**
		 * 血红蛋白含量
		 */
		HEMOGLOBIN_CONTENT,
		/**
		 * 血红蛋白检测
		 */
		HEMOGLOBIN_DETECTION,
		/**
		 * 蛋白含量
		 */
		PROTEIN_CONTENT,
		/**
		 * 新卡化验
		 */
		NEW_CARD_ASSAY,
		/**
		 * 供浆员化验
		 */
		PLASMA_ASSAY,
		/**
		 * 酶标板检测
		 */
		ELISA_PLATE_DETECTION
		
	}
	/**
	 * 
	 * 采浆流程用小流程枚举
	 *
	 */
	enum Collection implements WorkFlow {
		/**
		 * 采浆验证
		 */
		VALIDATE,
		/**
		 * 采浆
		 */
		COLLECTION
	}
	
}
