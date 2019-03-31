package com.fadl.plasma.dao;

import com.fadl.plasma.entity.CardRecord;

import java.sql.SQLException;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 发卡记录表 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-30
 */
public interface CardRecordMapper extends BaseMapper<CardRecord> {

	/**
	 * 获取浆员发卡记录数
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Integer countCardRecord(String id) throws SQLException;
}
