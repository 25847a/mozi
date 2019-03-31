package com.fadl.inspection.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.NewCard;

/**
 * <p>
 * 检验-新-老卡化验 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface NewCardMapper extends BaseMapper<NewCard> {
	/**
	 * 根据创建时间查询检验记录,带分页
	 * @param page
	 * @param newCard
	 * @return
	 */
	List<DataRow> queryListsByCreateDate(Pagination page, NewCard newCard);
	
	/**
	 * 查询未化验浆员信息, 带分页 需要指定查询日期和是否新老浆员
	 * @param page
	 * @param newCard
	 * @return
	 */
	List<DataRow> queryListByCreateDateAndIsAssay(Pagination page, NewCard newCard);
	
	/**
	 * 查询已化验浆员信息, 带分页 需要指定查询日期和是否新老浆员
	 * @param page
	 * @param newCard
	 * @return
	 */
	List<DataRow> queryListByCreateDateAndIsAssayWith1(Pagination page, NewCard newCard);
	/**
	 * 查询已化验浆员信息, 不带分页 需要指定查询日期
	 * @param page
	 * @param newCard
	 * @return
	 */
	List<HashMap<String, String>> queryListByUpdateDateAndIsAssayWith1(NewCard newCard);
	
	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param newCard
	 * @return
	 */
	List<DataRow> queryListsByNewCard(Pagination page, NewCard newCard);
	/**
	 * 根据ID查询新老卡化验信息,包含浆员信息
	 * @param newCard
	 * @return
	 */
	DataRow selectInfoById(NewCard newCard);
	
	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param map
	 * @return
	 */
	List<DataRow> queryListsByCondition(Pagination page,  HashMap<String,Object> map)throws SQLException;
	
	/**
	 * 查询当天的化验记录信息
	 * @param newCard
	 * @return
	 */
	List<DataRow> queryLists(NewCard newCard);
}
