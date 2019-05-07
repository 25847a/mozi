package com.fadl.system.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.entity.BedNumber;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 床位表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
public interface BedNumberService extends IService<BedNumber> {

	/**
	 * 查询床位数据
	 * @return
	 */
	public DataRow queryBedNumberInfo(Admin admin,DataRow messageMap)throws SQLException;
}
