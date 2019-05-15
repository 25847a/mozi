package com.fadl.health.service.impl;

import com.fadl.health.entity.Usercode;
import com.fadl.health.dao.UsercodeMapper;
import com.fadl.health.service.UsercodeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 这应该是获取验证码的表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-14
 */
@Service
public class UsercodeServiceImpl extends ServiceImpl<UsercodeMapper, Usercode> implements UsercodeService {

	
	@Autowired
	UsercodeMapper usercodeMapper;
	/**
	 * 插入到验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateUsercode(Usercode usercode) throws SQLException {
		return usercodeMapper.updateUsercode(usercode);
	}
	/**
	 * 修改验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int insertUsercode(Usercode usercode) throws SQLException {
		return usercodeMapper.insertUsercode(usercode);
	}
	/**
	 * 查询验证码表
	 * @param usercode
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Usercode queryUsercodeInfo(String phone) throws Exception {
		return usercodeMapper.queryUsercodeInfo(phone);
	}

}
