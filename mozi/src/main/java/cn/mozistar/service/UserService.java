package cn.mozistar.service;

import java.util.Map;

import cn.mozistar.pojo.User;
import cn.mozistar.util.ResultBase;

public interface UserService {

	/**
	 * 添加用户
	 * @param u
	 * @return
	 */
	public boolean addUser(User u);
	
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	public  Integer sendSMS(String phone);

	/**
	 * 查询账号是否已经存在
	 * @param account
	 */
	public boolean checkAccount(String account);
	
	User selectUserByAccount(String account);


	public void update(User user);

	public boolean uploadavatar(String string, Integer id);

	public User getUser(Integer id);

	/**
	 * 更新用户信息
	 * 
	 * @param u
	 * @return
	 */
	public boolean updateUser(User u);
	/**
	 * 修改目标步数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ResultBase updateWalkCount(Map<String,Object> map,ResultBase re)throws Exception;
}
