package com.fadl.cost.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.cost.entity.LevelConfig;

/**
 * <p>
 * 推荐等级奖励设置 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-17
 */
public interface LevelConfigService extends IService<LevelConfig> {

	/**
	 * 邀请设置to绑定信息列表（条件查询）
	 * 
	 * */
	public Page<DataRow> queryLevelConfigList(Map<String, String> map,Page<DataRow> page) throws Exception;
	
	
	//*******************************************邀请绑定*********************************************
	
	/**绑定信息列表查询列
	 * @throws Exception */
	public Page<DataRow> queryBindMsgList(Map<String, String> map,Page<DataRow> page) throws Exception;
	
	/**费用管理---邀请绑定--解绑
	 * @throws Exception */
	public int unbind(Long id) throws Exception;
	
	/**
	 * 费用管理---邀请绑定--新增绑定
	 * @throws Exception 
	 * */
	public boolean addBind(Long inviterType,String IDNo,Long id,DataRow message) throws Exception;
}
