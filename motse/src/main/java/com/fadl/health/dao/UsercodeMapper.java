package com.fadl.health.dao;

import com.fadl.health.entity.Usercode;

import java.sql.SQLException;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 这应该是获取验证码的表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-05-14
 */
public interface UsercodeMapper extends BaseMapper<Usercode> {
	/**
	 * 查询验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public Usercode queryUsercodeInfo(String phone)throws SQLException;
	
	/**
	 * 插入验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public int insertUsercode(Usercode usercode)throws SQLException;
	/**
	 * 更新验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	public int updateUsercode(Usercode usercode)throws SQLException;
}
