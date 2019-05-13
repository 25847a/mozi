package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUserGender(Map<String,Object> map)throws SQLException;
	/**
	 * 查询添加用户的列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAdduserList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询添加用户的列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryAdduserListCount(Map<String,Object> map)throws SQLException;
	/**
     * 查询添加用户页面的信息
     * @param map
     * @return
     */
	public DataRow queryaddUserInfo(Map<String,String> map)throws SQLException;
	/**
     * 查询添加用户预警设置信息
     * @param map
     * @return
     */
	public DataRow queryPushInfo(Map<String,String> map)throws SQLException;
	/**
     * 点击查询个人详情的信息
     * @param map
     * @return
     */
	public DataRow queryUserEquipmentInfo(Map<String,String> map)throws SQLException;
	/**
     * 通过设备ID查询观察者
     * @param map
     * @return
     */
	public List<DataRow> queryObserver(Map<String,String> map)throws SQLException;
	/**
     * 通过设备查询用户和设备信息
     * @param map
     * @return
     */
	public DataRow queryUserEquipment(Map<String,String> map)throws SQLException;
	/**
     * 通过设备查询用户和设备信息
     * @param map
     * @return
     */
	public int deleteUserCount(Map<String,Object> map)throws SQLException;
	/**
	 * 根据代理商ID查询使用者总数
	 */
	public int queryUserCount(long id)throws SQLException;
	
}
