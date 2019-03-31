package com.fadl.cost.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.cost.dao.LevelConfigMapper;
import com.fadl.cost.entity.LevelConfig;
import com.fadl.cost.service.LevelConfigService;
import com.fadl.plasma.dao.DetailedInfoMapper;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.propagandist.dao.PropagandistMapper;
import com.fadl.propagandist.entity.Propagandist;

/**
 * <p>
 * 推荐等级奖励设置 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-17
 */
@Service
public class LevelConfigServiceImpl extends ServiceImpl<LevelConfigMapper, LevelConfig> implements LevelConfigService {
	
	@Autowired
	private LevelConfigMapper configMapper;

	/**
	 * 邀请设置to绑定信息列表（条件查询）
	 * 
	 * */
	public Page<DataRow> queryLevelConfigList(Map<String, String> map,Page<DataRow> page) throws SQLException {
		/*List<LevelConfig> list = configMapper.queryLevelConfigList(map, page);
		page.setRecords(list);
		int total =  configMapper.queryLevelConfigListCount(map);
		page.setTotal(total);*/
		return page.setRecords(configMapper.queryLevelConfigList(map, page));
	}
	
	//****************************************************邀请绑定*********************************************
	@Autowired
	private ProviderBaseinfoMapper baseinfoMapper;
	@Autowired
	private PropagandistMapper propagandistMapper;
	@Autowired
	private DetailedInfoMapper detailedInfoMapper;
	
	/**绑定信息列表查询列
	 * @throws Exception */
	public Page<DataRow> queryBindMsgList(Map<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(configMapper.queryBindMsgList(map, page));
	}
	/**费用管理---邀请绑定--解绑
	 * @throws Exception */
	@Transactional
	public int unbind(Long id) throws Exception {
		
		//解除绑定前先更新等级
		int level = 0;
		DetailedInfo info = detailedInfoMapper.selectById(id);
		Long inviteId =info.getInviteId();
		Integer inviterType = info.getInviteType()==null ?0:info.getInviteType();
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map1.put("inviterType", inviterType);
		map1.put("inviteId", inviteId);
		int total = configMapper.queryCount(map1);
		if(inviterType == 0) {
			ProviderBaseinfo base = baseinfoMapper.selectById(inviteId);
			level += (base.getLevel() == null ?0:base.getLevel());
			
		}else if(inviterType == 1) {
			Propagandist prop = propagandistMapper.selectById(inviteId);
			level += (prop.getLevel() == null ?0:prop.getLevel());
		}
		LevelConfig entity = new LevelConfig();
		entity.setLevel(level);
		LevelConfig levelCon = configMapper.selectOne(entity);
		int levalPeople = 0;
		if(levelCon != null) {
			levalPeople += levelCon.getPeople();
		}
		//int levalPeople = leval==null ? 0 :leval;
		if(inviterType == 0) {
			if(total < (levalPeople+1)) {
				map2.put("level", level-1);
				map2.put("inviteId", inviteId);
				configMapper.updateBaseLevel(map2);
			}
		}else if(inviterType == 1){
			if(total < (levalPeople+1)) {
				map2.put("level", level-1);
				map2.put("inviteId", inviteId);
				configMapper.updatePropLevel(map2);
			}
		}
		
		return configMapper.unbind(id);
	}

	/**
	 * 费用管理---邀请绑定--新增绑定
	 * @throws Exception 
	 * */
	@Transactional
	public boolean addBind(Long inviterType,String IDNo,Long id,DataRow message) {
		Long inviteId = null;
		//判断是浆员基本信息表还是宣传员表
		if(inviterType == 0) {
			inviteId = configMapper.queBaseIdByIDNo(IDNo);
		}else if(inviterType == 1) {
			inviteId = configMapper.quePropIdByIDNo(IDNo);
		}
		if(inviteId == null) {
			message.initFial("身份证号与邀请人信息不匹配");
			return false;
		}else {
			//添加绑定前先更新等级
			int level = 0;
			Map<String, Object> map1 = new HashMap<>();
			Map<String, Object> map2 = new HashMap<>();
			map1.put("inviterType", inviterType);
			map1.put("inviteId", inviteId);
			int total = configMapper.queryCount(map1);
			if(inviterType == 0) {
				ProviderBaseinfo base = baseinfoMapper.selectById(inviteId);
				level += (base.getLevel() == null ?0:base.getLevel());
				
			}else if(inviterType == 1) {
				Propagandist prop = propagandistMapper.selectById(inviteId);
				level += (prop.getLevel() == null ?0:prop.getLevel());
			}
			LevelConfig entity = new LevelConfig();
			entity.setLevel(level+1);
			int levalPeople = configMapper.selectOne(entity).getPeople();
			if(inviterType == 0) {
				if((total+1) >= levalPeople) {
					map2.put("level", level+1);
					map2.put("inviteId", inviteId);
					configMapper.updateBaseLevel(map2);
				}
			}else if(inviterType == 1){
				if((total+1) >= levalPeople) {
					map2.put("level", level+1);
					map2.put("inviteId", inviteId);
					configMapper.updatePropLevel(map2);
				}
			}
			//添加绑定
			Map<String, Object> map = new HashMap<>();
			map.put("inviteId", inviteId);
			map.put("inviterType", inviterType);
			map.put("id", id);
			return configMapper.addBind(map);
		}
	}
}
