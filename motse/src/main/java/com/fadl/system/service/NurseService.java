package com.fadl.system.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.entity.Nurse;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 护士表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
public interface NurseService extends IService<Nurse> {

	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	public DataRow queryNurseInfo(Admin admin,DataRow messageMap)throws SQLException;
}
