package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface UserService extends IService<User> {

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
	public DataRow queryAdduserList(Map<String,Object> map,DataRow messageMap)throws SQLException;
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
	public DataRow queryaddUserInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
     * 更改重点关爱状态
     * @param map
     * @return
     */
	public DataRow updateLoveInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
     * 查询添加用户预警设置信息
     * @param map
     * @return
     */
	public DataRow queryPushInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
     * 更改用户预警设置信息
     * @param map
     * @return
     */
	public DataRow updatePushInfo(Push push,DataRow messageMap)throws SQLException;
	/**
     * 点击查询个人详情的信息
     * @param map
     * @return
     */
	public DataRow queryUserEquipmentInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
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
     * 上传使用者头像图片
     * @param user
     * @return
     */
	public DataRow uploadUserPicture(User user,DataRow messageMap)throws Exception;
	/**
     * 点击确认修改个人详情的信息
     * @param map
     * @return
     */
	public DataRow updateUserInfo(User user,Equipment equipment,DataRow messageMap)throws Exception;
	/**
     * 点击确认修改个人详情的信息
     * @param map
     * @return
     */
	public DataRow queryImeiUserInfo(String imei,DataRow messageMap)throws Exception;
	/**
     * 点击添加用户确定键添加用户
     * @param map
     * @return
     */
	public DataRow AddUserDetermine(Equipment equipment,DataRow messageMap)throws SQLException;
	/**
     * 点击移除用户确定键添加用户
     * @param map
     * @return
     */
	public DataRow deleteUserDetermine(User user,DataRow messageMap)throws SQLException;
	/**
     * 添加用户
     * @param map
     * @return
     */
	public DataRow addUserInfo(User user,String telephone,DataRow messageMap)throws Exception;
	/**
	 * 根据代理商ID查询使用者总数
	 */
	public int queryUserCount(long id)throws Exception;
}
