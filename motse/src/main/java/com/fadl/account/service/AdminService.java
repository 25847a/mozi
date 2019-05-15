package com.fadl.account.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.health.entity.Usercode;

import java.sql.SQLException;
import java.util.Map;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 后台页面用户表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AdminService extends IService<Admin> {
	/**
	 * 根据用户名查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public Admin queryAdminInfo(String account)throws SQLException;
	/**
	 * 查询代理商的名字
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminAgentInfo(Long id)throws SQLException;
	/**
	 * 查询用户管理列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminInfoList(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
     * 修改供应商的头像
     * avatar
     * @return
     */
	public DataRow updateAgentImage(Admin admin,DataRow messageMap)throws Exception;
	/**
     * 修改供应商的密码
     * avatar
     * @return
     */
	public DataRow updateAgentPassword(Admin admin,Map<String,String> map,DataRow messageMap)throws Exception;
	/**
     * 修改密码验证手机号码
     * phone
     * @return
     */
	public DataRow checkingPhone(String phone,DataRow messageMap)throws Exception;
	/**
     * 通过手机号码获取验证码
     * phone
     * @return
     */
	public DataRow checkingCode(String phone,DataRow messageMap)throws Exception;
	/**
     * 判断验证码是否正确
     * usercode
     * @return
     */
	public DataRow queryCheckingCode(Usercode usercode,DataRow messageMap)throws Exception;
	/**
     * 忘记密码更改密码
     * admin
     * @return
     */
	public DataRow updatePassWord(Admin admin,DataRow messageMap)throws Exception;
	
	
	
	
	
	
}
