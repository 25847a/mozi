package com.fadl.account.service.impl;

import com.fadl.account.entity.Auth;
import com.fadl.account.entity.RoleAuth;
import com.fadl.account.dao.AuthMapper;
import com.fadl.account.service.AuthService;
import com.fadl.account.service.RoleAuthService;
import com.fadl.common.DataRow;
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
 * 权限菜单表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {
	
	@Autowired
	AuthMapper authMapper;
	@Autowired
	RoleAuthService roleAuthService;
	
	/**
     * 查询菜单管理列表
     * @return
     */
	@Override
	public DataRow queryAuthInfoList(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = authMapper.queryAuthList(map);
		int total = authMapper.queryAuthListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
     * 新增菜单信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addAuthInfo(Auth auth, DataRow messageMap) throws SQLException {
		int row =authMapper.insert(auth);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	 /**
     * 修改菜单信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateAuthInfo(Auth auth, DataRow messageMap) throws SQLException {
		int row =authMapper.updateById(auth);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 删除菜单信息
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow deleteAuthInfo(Auth auth, DataRow messageMap) throws SQLException {
		EntityWrapper<RoleAuth> ew = new EntityWrapper<RoleAuth>();
		ew.eq("auth_id", auth.getId());
		List<RoleAuth> list = roleAuthService.selectList(ew);
		if(list!=null && !list.isEmpty()){
			messageMap.initFial("该菜单绑定了权限,请解除权限再删除");
		}else{
			int row =authMapper.deleteById(auth.getId());
			if(row>0){
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}
		
		return messageMap;
	}
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Auth> queryAuthList() throws Exception {
		return authMapper.queryAuthList();
	}
	/**
	 * 查询角色全部信息
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Auth> queryAuthInfo() throws SQLException {
		return authMapper.queryAuthInfo();
	}
}
