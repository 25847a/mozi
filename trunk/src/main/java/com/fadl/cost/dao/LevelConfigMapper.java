package com.fadl.cost.dao;

import com.fadl.common.DataRow;
import com.fadl.cost.entity.LevelConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 推荐等级奖励设置 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-17
 */
public interface LevelConfigMapper extends BaseMapper<LevelConfig> {
	
	/**
	 * 邀请设置to绑定信息列表（条件查询）
	 * 
	 * */
	public List<DataRow> queryLevelConfigList(Map<String, String> map,Page<DataRow> page) throws SQLException;
	

	//*****************************************邀请绑定***********************************************
	/**绑定信息列表查询列*/
	public List<DataRow> queryBindMsgList(Map<String, String> map,Page<DataRow> page);
	
	/**费用管理---邀请绑定--解绑*/
	public int unbind(Long id);
	
	/**当邀请人类型为0时，查询浆员基本信息表，并根据邀请人身份证号码查询主键id*/
	public Long queBaseIdByIDNo (String IDNo);
	
	/**当邀请人类型为1时，查询宣传员表，并根据邀请人身份证号码查询主键id*/
	public Long quePropIdByIDNo (String IDNo);
	
	/**根据id确定浆员详细信息表具体修改那一条记录*/
	public boolean addBind (Map<String, Object> map);
	
	/**統計邀請人總人數*/
	public int queryCount(Map<String, Object> map);
	
	/**修改邀請人總人數（浆员基本信息表）*/
	public int updateBaseLevel(Map<String, Object> map);
	
	/**修改人總人數（宣传员表）*/
	public int updatePropLevel(Map<String, Object> map);
	
}
