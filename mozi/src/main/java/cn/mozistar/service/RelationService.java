package cn.mozistar.service;

import java.util.List;

import cn.mozistar.pojo.Relation;
import net.sf.json.JSONObject;

public interface RelationService {

	
	
	/**
	 * 根据 userId 查询
	 * return List<Relation>
	 */
	List<Relation> selectByUserId(Integer userId);
	
	 /**
     * 根据 userId 和 observeId 查询
     * @param relation  #{userId}  #{observeId}
     * @return Relation
     */
	Relation selectRelation(Relation relation);
	
	/**
	 * 根据userId 获取过多个关注对象 observeId 
	 * @param userId
	 * @return  List<Integer> 
	 */
	public List<Integer> getObserveIdList(Integer userId);
	
	int insert(Relation re);
	
	/**
	 * 根据 observeId 查询 
	 * @param observeId
	 * @return
	 */
	List<Relation> selectByObserveId(int observeId);
	
	/**
	 * 查询我关注的
	 * @param userId
	 * @return
	 */
	List<JSONObject> selectMyAttention(Integer userId);
	
	/**
	 * 查询关注我的
	 * @param userId
	 * @return
	 */
	List<JSONObject> selectAttentionMy(Integer userId);

	int deleteRelation(Relation relation);


}
