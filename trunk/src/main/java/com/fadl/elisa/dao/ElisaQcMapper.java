package com.fadl.elisa.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaQc;

/**
 * <p>
 * 质控品和检验方法绑定表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-31
 */
public interface ElisaQcMapper extends BaseMapper<ElisaQc> {

	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param qc
	 * @return
	 */
	List<DataRow> querySuppliesListByProjectId(ElisaQc qc);
}
