package com.fadl.system.service.impl;

import com.fadl.system.entity.Nurse;
import com.fadl.account.dao.AgentMapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.Agent;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.system.dao.NurseMapper;
import com.fadl.system.service.NurseService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 护士表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Service
public class NurseServiceImpl extends ServiceImpl<NurseMapper, Nurse> implements NurseService {
	
	@Autowired
	AgentMapper agentMapper;
	@Autowired
	NurseMapper nurseMapper;
	/**
	 * 查询分属护士列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryNurseList(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("id", SessionUtil.getSessionAdmin().getId());
		List<DataRow> result = nurseMapper.queryNurseList(map);
		int total = nurseMapper.queryNurseListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	@Override
	public DataRow queryNurseInfo(Admin admin, DataRow messageMap) throws SQLException {
		EntityWrapper<Nurse> ew = new EntityWrapper<Nurse>();
		Agent agent =agentMapper.queryAgentInfo(admin.getId());
		ew.eq("agenId", agent.getId());
		List<Nurse> list =this.selectList(ew);
		messageMap.initSuccess(list);
		return messageMap;
	}
	/**
     * 新增分属护士信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addNurseInfo(Nurse nurse, DataRow messageMap) throws SQLException {
		Agent agent =agentMapper.queryAgentInfo(SessionUtil.getSessionAdmin().getId());
		nurse.setAgenId(agent.getId());
		int row =nurseMapper.insert(nurse);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 修改分属护士信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateNurseInfo(Nurse nurse, DataRow messageMap) throws SQLException {
		int row= nurseMapper.updateById(nurse);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	 /**
     * 删除分属护士信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow deleteNurseInfo(Long id, DataRow messageMap) throws SQLException {
		int row =nurseMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
}
