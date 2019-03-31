package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.BloodRedProteinContent;
/**
 * <p>
 * 检验-血红蛋白含量表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface BloodRedProteinContentMapper extends BaseMapper<BloodRedProteinContent> {

	/**
	 * 根据更新时间查找所有的血红蛋白含量检测的记录带分页
	 * @param page
	 * @param bloodRedProteinContent
	 * @return
	 */
	List<DataRow> queryListByUpdateDate(Pagination page, BloodRedProteinContent bloodRedProteinContent);
	
	/**
	 * 根据ID查找对应的血红蛋白含量检测信息,带浆员个人信息
	 * @param id
	 * @return
	 */
	DataRow queryBloodRedProteinContentById(Long id);
	
	/**
	 * 根据选择的日期获取当天做了检测的所有的浆员信息,供打印使用
	 * @param chooseDate
	 * @return
	 */
	List<DataRow> queryInfosByChooseDate(String chooseDate);
}
