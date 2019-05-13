package com.fadl.hr.dao;

import com.fadl.common.DataRow;
import com.fadl.hr.entity.Hr;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-05-10
 */
public interface HrMapper extends BaseMapper<Hr> {
	/**
	 * 查询机构管理页面下工作人员的信息列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryHrList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询机构管理页面下工作人员的信息总数
	 * @return
	 * @throws SQLException
	 */
	public int queryHrListCount(Map<String,Object> map)throws SQLException;
}
