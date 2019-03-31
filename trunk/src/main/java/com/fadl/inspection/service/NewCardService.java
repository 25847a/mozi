package com.fadl.inspection.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.NewCard;

/**
 * <p>
 * 检验-新-老卡化验 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface NewCardService extends IService<NewCard> {
	/**
	 * 根据创建时间查询检验记录
	 * @param page
	 * @param newCard
	 * @return
	 */
	Page<DataRow> queryListsByCreateDate(Page<DataRow> page,NewCard newCard);
	
	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param newCard
	 * @return
	 */
	Page<DataRow> queryListsByCondition(Page<DataRow> page,NewCard newCard);
	
	/**
	 * 根据条件查询检验记录
	 * @param page
	 * @param map
	 * @return
	 */
	Page<DataRow> queryListsByCondition(Page<DataRow> page,HashMap<String, Object> map) throws Exception;
	
	/**
	 * 查询未化验浆员信息, 带分页 需要指定查询日期和是否新老浆员
	 * @param page
	 * @param newCard
	 * @return
	 */
	Page<DataRow> queryListByCreateDateAndIsAssay (Page<DataRow> page,NewCard newCard) throws Exception;
	
	/**
	 * 查询已化验浆员信息, 带分页 需要指定查询日期和是否新老浆员
	 * @param page
	 * @param newCard
	 * @return
	 */
	Page<DataRow> queryListByCreateDateAndIsAssayWith1(Page<DataRow> page,NewCard newCard);
	
	
	/**
	 * 根据ID查询新老卡化验信息,包含浆员信息
	 * @param newCard
	 * @return
	 */
	DataRow selectInfoById(NewCard newCard);
	
	/**
	 * 根据ID更新新老卡化验信息
	 * @param card
	 * @return
	 * @throws Exception
	 */
	boolean updateInfoById(NewCard card, DataRow dataRow)throws Exception;
	
	/**
	 * 查询当天的不合格记录信息 传入参数为null 则默认使用系统时间
	 * @param sendDate
	 * @return
	 */
	List<DataRow> queryListsByUnqualified(String sendDate);
	
	/**
	 * 查询已经发布的化验结果记录,根据时间段查询
	 * @param card
	 * @return
	 */
	List<HashMap<String, String>> queryListByUpdateDateAndIsAssayWith1(NewCard card);
	/**
	 * 查询当天的合格记录信息 传入参数为null 则默认使用系统时间
	 * @param sendDate
	 * @return
	 */
	List<DataRow> queryListsByQualified(String sendDate);
}
