package cn.mozistar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.RelationMapper;
import cn.mozistar.mapper.UserMapper;
import cn.mozistar.pojo.Relation;
import cn.mozistar.pojo.User;
import cn.mozistar.service.RelationService;
import net.sf.json.JSONObject;

@Transactional
@Service
public class RelationServiceImpl implements RelationService{
	
	
	@Autowired 
	private UserMapper userMapper;
	@Autowired 
	private RelationMapper relationMapper;

	/**
	 * 根据 userId 查询
	 * return List<Relation>
	 */
	public List<Relation> selectByUserId(Integer userId) {
		return relationMapper.selectByUserId(userId);
	}
	
	/**
	 * ObserveId 列表
	 */
	public List<Integer> getObserveIdList(Integer userId){
		List<Relation> list = selectByUserId(userId);
		List<Integer> observeIdList = null;
		if(list!=null&&list.size()>0){
			observeIdList = new ArrayList<>();
			for (Relation r : list) {
				observeIdList.add(r.getObserveId());
			}
		}
		return observeIdList;
	}
	/**
	 * userId 列表
	 */
	public List<Integer> getUserIdList(Integer observeId){
		List<Relation> list = selectByObserveId(observeId);
		List<Integer> userIdList = null;
		
		if(list!=null&&list.size()>0){
			userIdList = new ArrayList<>();
			
			for (Relation r : list) {
				userIdList.add(r.getUserId());
			}
		}
		return userIdList;
	}
	
	public int insert(Relation re){
		return relationMapper.insertSelective(re);
	}

	 /**
     * 根据 userId 和 observeId 查询
     * @param relation  #{userId}  #{observeId}
     * @return Relation
     */
	public Relation selectRelation(Relation relation) {
		return relationMapper.selectRelation(relation);
	}

	/**
	 * 根据 observeId 查询
	 * return List<Relation>
	 */
	public List<Relation> selectByObserveId(int observeId) {
		return relationMapper.selectByObserveId(observeId);
	}

	/**
	 * 查询我关注的
	 */
	public List<JSONObject> selectMyAttention(Integer userId) {
		
		List<JSONObject> list = null;
		List<Integer> observeIdList = getObserveIdList(userId);
		if(observeIdList!=null&&observeIdList.size()>0){
			
			List<User> userList = userMapper.selectUserList(observeIdList);
			if(userList!=null&&userList.size()>0){
				
				list = new ArrayList<>();
				for (User user : userList) {
					JSONObject j = new JSONObject();
					j.put("userId", user.getId());
					j.put("avatar", user.getAvatar());
					j.put("name", user.getName());
					j.put("phone",user.getPhone());
					list.add(j);
				}
			}
		}
		return list;
		
	}
	/**
	 * 查询关注我的
	 */
	public List<JSONObject> selectAttentionMy(Integer observeId) {
		List<JSONObject> list = null;
		
		List<Integer> userIdList = getUserIdList(observeId);
		if(userIdList!=null&&userIdList.size()>0){
			
			List<User> userList = userMapper.selectUserList(userIdList);
			if(userList!=null&&userList.size()>0){
				
				list = new ArrayList<>();
				for (User user : userList) {
					JSONObject j = new JSONObject();
					j.put("userId", user.getId());
					j.put("avatar", user.getAvatar());
					j.put("name", user.getName());
					j.put("phone",user.getPhone());
					list.add(j);
				}
			}
		}
		return list;
	}

	@Override
	public int deleteRelation(Relation relation) {
		
		return relationMapper.deleteRelation(relation);
	}
}
