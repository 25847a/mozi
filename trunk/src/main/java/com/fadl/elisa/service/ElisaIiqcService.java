package com.fadl.elisa.service;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaIiqc;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 即刻性室内质控记录表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-20
 */
public interface ElisaIiqcService extends IService<ElisaIiqc> {
	/**
	 * 根据检验配置详情Id 查询记录,根据CreateDate排序
	 * @param tciid
	 * @param qcId
	 * @return
	 */
	List<ElisaIiqc> findByTciidAndQcId(Long tciid,Long qcId) throws Exception;
	
	
	/**
	 * 根据检验配置详情Id和页码 查询记录(固定20记录为一页),根据CreateDate排序
	 * @param page
	 * @param tciid
	 * @param qcId
	 * @return
	 */
	Page<DataRow> findByTciidAndPageNo(Page<DataRow> page, Long tciid,Long qcId, Integer stratRow, Integer endRow) throws Exception;
}
