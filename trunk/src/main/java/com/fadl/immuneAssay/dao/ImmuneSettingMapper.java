package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneSetting;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 免疫类别设置 Mapper 接口
 * </p>
 *
 * @author hkk
 * @since 2018-07-12
 */
public interface ImmuneSettingMapper extends BaseMapper<ImmuneSetting> {
	
	/**
	 *  免疫类别设置列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> immuneSettingList(ImmuneSetting im,Pagination pagination) throws SQLException;
	/**
	 * 查询免疫设置(获取来世版本的)
	 * @return
	 */
	public List<DataRow> queryAmmuneSetting(Integer type)throws Exception;
	
	/**
	 * 获取所有免疫类别设置类型
	 * @return
	 * @throws SQLException
	 */
	public List<ImmuneSetting> getAmmuneSetting() throws SQLException;

}
