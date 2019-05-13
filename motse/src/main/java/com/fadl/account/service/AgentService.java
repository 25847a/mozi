package com.fadl.account.service;

import com.fadl.account.entity.Agent;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian
 * @since 2019-05-08
 */
public interface AgentService extends IService<Agent> {

	
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAgentList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
	 * 根据供应商ID查询机构信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAgentInfo(long id,DataRow messageMap)throws Exception;
	/**
	 * 查询供应商列表选项
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAgentOption(DataRow messageMap)throws Exception;
	/**
	 * 新增代理商信息
	 * @return
	 * @throws SQLException
	 */
	public DataRow addAgentInfo(Agent agent,DataRow messageMap)throws Exception;
}
