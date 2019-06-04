package com.fadl.account.service.impl;

import com.fadl.account.entity.Role;
import com.fadl.account.dao.RoleMapper;
import com.fadl.account.service.RoleService;
import com.fadl.common.DataRow;
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
 * 后台页面角色表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	/**
     * 查询角色列表
     * @return
     */
	@Override
	public DataRow queryRoleList(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = roleMapper.queryRoleList(map);
		int total = roleMapper.queryRoleListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
     * 新增角色信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addRoleInfo(Role role, DataRow messageMap) throws SQLException {
		int row = roleMapper.insert(role);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 修改角色信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateRoleInfo(Role role, DataRow messageMap) throws SQLException {
		int row= roleMapper.updateById(role);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 删除角色信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow deleteRoleInfo(Role role, DataRow messageMap) throws SQLException {
		int row= roleMapper.updateById(role);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	
}