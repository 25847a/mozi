package com.fadl.account.service.impl;

import com.fadl.account.entity.Agent;
import com.fadl.account.dao.AgentMapper;
import com.fadl.account.service.AgentService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-08
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {

	@Autowired
	AgentMapper agentMapper;
	
	
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAgentList(Map<String,Object> map,DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = agentMapper.queryAgentList(map);
		int total = agentMapper.queryAgentListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
	 * 根据供应商ID查询机构信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAgentInfo(long id,DataRow messageMap) throws Exception {
		
		Agent agent =agentMapper.queryAgentInfo(id);
		if(agent!=null){
			agent.setStartDate(DateUtil.sf.format(DateUtil.sf.parse(agent.getStartDate())));
			agent.setEndDate(DateUtil.sf.format(DateUtil.sf.parse(agent.getEndDate())));
			messageMap.initSuccess(agent);
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
	 * 查询供应商列表选项
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAgentOption(DataRow messageMap) throws Exception {
		EntityWrapper<Agent> ew = new EntityWrapper<Agent>();
		ew.eq("type", 0);
		List<Agent> agent=agentMapper.selectList(ew);
		messageMap.initSuccess(agent);
		return messageMap;
	}
	/**
	 * 新增代理商信息
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow addAgentInfo(Agent agent, DataRow messageMap) throws Exception {
		agent.setEndDate(DateUtil.sf.format(new Date()));
		int row =agentMapper.insert(agent);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
   	 * 修改代理商信息
   	 * @return
   	 * @throws SQLException
   	 */
	@Override
	public DataRow updateAgentInfo(Agent agent, DataRow messageMap) throws Exception {
		int row =agentMapper.updateById(agent);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}

}
