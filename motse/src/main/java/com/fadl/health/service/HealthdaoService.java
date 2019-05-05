package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Healthdao;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 健康数据校准表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-29
 */
public interface HealthdaoService extends IService<Healthdao> {
	/**
	 *  查询人工智能学习信息
	 * @param map
	 * @return
	 */
	public DataRow queryHealthDaoInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
	 *  更改人工智能学习信息
	 * @param map
	 * @return
	 */
	public DataRow updateHealthDaoInfo(Healthdao healthdao,DataRow messageMap)throws SQLException;
}
