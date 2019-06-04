package com.fadl.system.service.impl;

import com.fadl.system.entity.BedNumber;
import com.fadl.account.dao.AgentMapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.Agent;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.system.dao.BedNumberMapper;
import com.fadl.system.service.BedNumberService;
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
 * 床位表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Service
public class BedNumberServiceImpl extends ServiceImpl<BedNumberMapper, BedNumber> implements BedNumberService {

	@Autowired
	AgentMapper agentMapper;
	@Autowired
	BedNumberMapper bedNumberMapper;
	
	/**
	 * 查询床位列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryBedList(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("id", SessionUtil.getSessionAdmin().getId());
		List<DataRow> result = bedNumberMapper.queryBedList(map);
		int total = bedNumberMapper.queryBedListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
     * 新增床位信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addBedInfo(BedNumber bedNumber, DataRow messageMap) throws SQLException {
		Agent agent =agentMapper.queryAgentInfo(SessionUtil.getSessionAdmin().getId());
		bedNumber.setAgenId(agent.getId());
		int row =bedNumberMapper.insert(bedNumber);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 修改床位信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateBedInfo(BedNumber bedNumber, DataRow messageMap) throws SQLException {
		int row= bedNumberMapper.updateById(bedNumber);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 删除床位信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow deleteBedInfo(Long id, DataRow messageMap) throws SQLException {
		int row =bedNumberMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
	 * 查询床位数据
	 * @return
	 */
	@Override
	public DataRow queryBedNumberInfo(Admin admin, DataRow messageMap) throws SQLException {
		EntityWrapper<BedNumber> ew = new EntityWrapper<BedNumber>();
		Agent agent =agentMapper.queryAgentInfo(admin.getId());
		ew.eq("agenId", agent.getId());
		List<BedNumber> list =this.selectList(ew);
		messageMap.initSuccess(list);
		return messageMap;
	}
}
