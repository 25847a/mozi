package com.fadl.refuseInfo.service;

import com.fadl.common.DataRow;
import com.fadl.refuseInfo.entity.RefuseInfo;

import java.sql.SQLException;
import java.util.HashMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 拒绝信息发布 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
public interface RefuseInfoService extends IService<RefuseInfo> {

	/**
	 * 查询体检拒绝信息列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryCheckRefuseInfoList(HashMap<String, String> map,Page<DataRow> page)throws SQLException;
	
	/**
	 * 查询化验拒绝信息列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryAssayRefuseInfoList(HashMap<String, String> map,Page<DataRow> page)throws SQLException;
	
	/**
	 * 更新体检信息
	 * @param refuseInfo
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateRefuseInfo(RefuseInfo refuseInfo,DataRow messageMap)throws Exception;
	
	/**
	 * 生物所反馈
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryOtherRefuseInfoList(HashMap<String, String> map,Page<DataRow> page)throws Exception;
	
	/**
	 * 发布生物所拒绝信息
	 * @param refuseInfo
	 * @param messageMap
	 * @throws Exception
	 */
	public void upateOtherRefuseInfo(RefuseInfo refuseInfo,DataRow messageMap)throws Exception;
	/**
   	 * 定时任务自动淘汰55-60岁小于两次采浆记录,60岁直接淘汰的记录
   	 * @throws Exception
   	 */
   	public void queryEliminateYearOld()throws Exception;
   	/**
	 * 查询浆员的最新拒绝信息发布-CJ
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public RefuseInfo plasmaRefuseInfo(String providerNo)throws Exception;
	/**
	 * 查询转氨酶暂拒情况 -CJ
	 * @param eliminateReason
	 * @return
	 * @throws Exception
	 */
	public RefuseInfo queryTransaminaseInfo(String eliminateReason,String providerNo)throws Exception;
}
