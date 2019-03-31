package com.fadl.elisa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.elisa.entity.ElisaValues;

/**
 * <p>
 * 酶标仪检测记录表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaValuesMapper extends BaseMapper<ElisaValues> {

	/**
	 * 批量更新
	 * @param evs
	 */
	public void updateBatchWithReportStatus(@Param("evs")List<ElisaValues> evs, @Param("reportStatus")int reportStatus);  
	
	/**
	 * 根据ElisaInfo的ID和evIndex查询具体的
	 * @param elisaValues
	 * @return
	 */
	public ElisaValues queryValueByEiIdAndEvIndex(ElisaValues elisaValues);
	
	/**
	 * 批量新增
	 * @param evs
	 */
	public void addElisaValues(@Param("evs")List<ElisaValues> evs);
	
	/**
	 * 根据小样号查询检测记录
	 * @param sampleNos
	 * @return
	 */
	public List<ElisaValues> selectBysampleNos(@Param("sampleNos")String sampleNos);
	
	/**
	 * 根据ElisaInfo id 查询所有相关的检验记录
	 * @param eiId
	 * @return
	 */
	public List<ElisaValues> selectListByEiId(@Param("eiId")Long eiId);
	
}
