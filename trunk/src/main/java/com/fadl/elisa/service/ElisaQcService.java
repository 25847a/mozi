package com.fadl.elisa.service;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaQc;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 质控品和检验方法绑定表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-31
 */
public interface ElisaQcService extends IService<ElisaQc> {
	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param qc
	 * @return
	 */
	List<DataRow> querySuppliesListByProjectId(ElisaQc qc);
}
