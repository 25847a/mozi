package com.fadl.system.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.entity.Nurse;

import java.sql.SQLException;
import java.util.Map;

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
	 * 查询分属护士列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryNurseList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
     * 新增分属护士信息
     * @return
     */
	public DataRow addNurseInfo(Nurse nurse,DataRow messageMap)throws SQLException;
	/**
     * 修改分属护士信息
     * @return
     */
	public DataRow updateNurseInfo(Nurse nurse,DataRow messageMap)throws SQLException;
	 /**
     * 删除分属护士信息
     * @return
     */
	public DataRow deleteNurseInfo(Long id,DataRow messageMap)throws SQLException;
	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	public DataRow queryNurseInfo(Admin admin,DataRow messageMap)throws SQLException;
}
