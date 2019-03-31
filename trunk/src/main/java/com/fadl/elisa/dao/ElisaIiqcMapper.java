package com.fadl.elisa.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaIiqc;

/**
 * <p>
 * 即刻性室内质控记录表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-20
 */
public interface ElisaIiqcMapper extends BaseMapper<ElisaIiqc> {
	/**
	 * 根据条件查询记录 qcId和reagentId 为必须
	 * @param stratRow
	 * @param endRow
	 * @param dataRow
	 * @return
	 */
	List<DataRow> findByPageNo( DataRow dataRow);
	/**
	 *  根据条件 查询总数
	 * @param dataRow
	 * @return
	 */
	Integer findByPageNoCount(DataRow dataRow);
}
