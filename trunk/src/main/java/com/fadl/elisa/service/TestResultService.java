package com.fadl.elisa.service;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.TestResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 测试结果 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-10-20
 */
public interface TestResultService extends IService<TestResult> {

	/**
	 * 得到全自动设备检验记录,并按照实验方法和项目进行分析
	 * @param dr
	 * @return
	 * @throws Exception
	 */
	Boolean doAutoElisa(DataRow dr) throws Exception;
}
