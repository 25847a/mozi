package com.fadl.account.dao;

import com.fadl.account.entity.Agent;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-05-08
 */
public interface AgentMapper extends BaseMapper<Agent> {

	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAgentList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询代理商列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryAgentListCount(Map<String,Object> map)throws SQLException;
	/**
	 * 根据供应商ID查询机构信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Agent queryAgentInfo(long id)throws SQLException;
}
