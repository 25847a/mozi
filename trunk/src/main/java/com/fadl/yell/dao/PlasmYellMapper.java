package com.fadl.yell.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.yell.entity.PlasmYell;

/**
 * <p>
 * 采浆叫号 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface PlasmYellMapper extends BaseMapper<PlasmYell> {

	/**
	 * 查询采浆验证列表
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryplasmaYellList(Pagination page,HashMap<String, String> map)throws SQLException;
	/**
	 * 采浆验证打印条码
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> queryPlasmYellPrintInfo(String allId)throws SQLException;
}
