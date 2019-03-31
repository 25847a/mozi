package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneAssaySetting;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 免疫化验效价值设置 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-08-25
 */
public interface ImmuneAssaySettingMapper extends BaseMapper<ImmuneAssaySetting> {

	/**
	 * 效价值列表
	 * @param map
	 * @param pagination
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAssaySettingList(Map<String,String> map,Pagination pagination)throws SQLException;
}
