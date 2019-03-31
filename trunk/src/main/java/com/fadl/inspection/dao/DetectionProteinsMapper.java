package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.DetectionProteins;

/**
 * <p>
 * 检验-血红蛋白检测 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface DetectionProteinsMapper extends BaseMapper<DetectionProteins> {
	/**
	 * 根据更新时间查找所有的血红蛋白检测的记录带分页
	 * @param page
	 * @param DetectionProteins
	 * @return
	 */
	List<DataRow> queryListByUpdateDate(Pagination page, DetectionProteins proteins);
	
	/**
	 * 根据ID查找对应的血红蛋白检测信息,带浆员个人信息
	 * @param id
	 * @return
	 */
	DataRow queryDetectionProteinsById(Long id);
	/**
	 * 根据选择的日期获取当天做了检测的所有的浆员信息,供打印使用
	 * @param chooseDate
	 * @return
	 */
	List<DataRow> queryInfosByChooseDate(String chooseDate);
}
