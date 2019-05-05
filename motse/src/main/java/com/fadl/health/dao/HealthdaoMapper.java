package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Healthdao;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 健康数据校准表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-29
 */
public interface HealthdaoMapper extends BaseMapper<Healthdao> {
	/**
	 *  查询人工智能学习信息
	 * @param map
	 * @return
	 */
	public DataRow queryHealthDaoInfo(Map<String,String> map)throws SQLException;
}
