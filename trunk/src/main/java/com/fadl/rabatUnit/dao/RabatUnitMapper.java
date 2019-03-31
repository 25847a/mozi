package com.fadl.rabatUnit.dao;

import com.fadl.rabatUnit.entity.RabatUnit;

import java.sql.SQLException;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 胸片检查单位表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
public interface RabatUnitMapper extends BaseMapper<RabatUnit> {

	/**
	 * 更新所有状态为 未 默认
	 * @return
	 * @throws SQLException
	 */
	public int updateRabatUnitStatus()throws SQLException;
}
