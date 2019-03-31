package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.ProteinContent;

/**
 * <p>
 * 检验-蛋白含量 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface ProteinContentMapper extends BaseMapper<ProteinContent> {
	/**
	 * 根据更新时间查找所有的蛋白含量检测的记录带分页
	 * 
	 * @param page
	 * @param proteinContent
	 * @return
	 */
	List<DataRow> queryListByUpdateDate(Pagination page, ProteinContent proteinContent);
	/**
	 * 查询头部信息(啊健)
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public DataRow queryProteinHeadInfo(ProteinContent content)throws Exception;

	/**
	 * 根据ID查找对应的蛋白含量信息,带浆员个人信息
	 * 
	 * @param id
	 * @return
	 */
	DataRow queryProteinContentById(Long id);

	/**
	 * 根据ID更新蛋白含量信息.会根据浆员信息在新卡化验或者采浆表中添加一条记录 (如果存在则更新记录,存在多条则报错.)
	 * 
	 * @param proteinContent
	 */
	void updateInfoById(ProteinContent proteinContent) throws Exception;
	
	/**
	 * 根据选择的日期获取当天做了检测的所有的浆员信息,供打印使用
	 * @param chooseDate
	 * @return
	 */
	List<DataRow> queryInfosByChooseDate(String chooseDate);
}
