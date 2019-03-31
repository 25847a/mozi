package com.plasma.buss.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * 用户管理
 * 
 * @author hu
 *
 */
public interface UserDao {

	/**
	 * 用户登录
	 * 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUserLogin(DataRow data) throws SQLException;
	/**
	 * 查询用户列表
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryUserList(DataRow dr)throws SQLException;
	/**
	 * 查询用户列表总数
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int queryUserListCount(DataRow dr)throws SQLException;
	/**
	 * 添加用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int saveUser(DataRow dr)throws SQLException;
	/**
	 * 修改用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int updateUser(DataRow dr)throws SQLException;
	/**
	 * 修改头像
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int updatePicture(DataRow dr)throws SQLException;
	/**
	 * 修改密码
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int updatePassword(DataRow dr)throws SQLException;
	/**
	 * 删除用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int deleteUser(DataRow dr)throws SQLException;
	/**
	 * 查询指定用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryByIdUser(DataRow dr)throws SQLException;
	/**
	 * 查询手机是否存在
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPhoneExist(DataRow dr)throws SQLException;
	
}
