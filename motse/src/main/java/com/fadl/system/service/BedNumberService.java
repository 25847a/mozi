package com.fadl.system.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.entity.BedNumber;
import java.sql.SQLException;
import java.util.Map;
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
	 * 查询床位列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBedList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
     * 新增床位信息
     * @return
     */
	public DataRow addBedInfo(BedNumber bedNumber,DataRow messageMap)throws SQLException;
	/**
     * 修改床位信息
     * @return
     */
	public DataRow updateBedInfo(BedNumber bedNumber,DataRow messageMap)throws SQLException;
	 /**
     * 删除床位信息
     * @return
     */
	public DataRow deleteBedInfo(Long id,DataRow messageMap)throws SQLException;
	/**
	 * 查询床位数据
	 * @return
	 */
	public DataRow queryBedNumberInfo(Admin admin,DataRow messageMap)throws SQLException;
}
