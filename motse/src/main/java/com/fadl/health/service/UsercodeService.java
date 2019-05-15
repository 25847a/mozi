package com.fadl.health.service;

import com.fadl.health.entity.Usercode;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 这应该是获取验证码的表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-05-14
 */
public interface UsercodeService extends IService<Usercode> {

	/**
	 * 查询验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public Usercode queryUsercodeInfo(String phone)throws Exception;
	/**
	 * 插入到验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public int insertUsercode(Usercode usercode)throws SQLException;
	/**
	 * 修改验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public int updateUsercode(Usercode usercode)throws SQLException;
}
