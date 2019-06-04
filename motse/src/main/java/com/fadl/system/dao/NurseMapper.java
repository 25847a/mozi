package com.fadl.system.dao;

import com.fadl.common.DataRow;
import com.fadl.system.entity.Nurse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 护士表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
public interface NurseMapper extends BaseMapper<Nurse> {

	/**
	 * 查询护士列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryNurseList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询护士列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryNurseListCount(Map<String,Object> map)throws SQLException;
}
