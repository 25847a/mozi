package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneControl;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 免疫针次控制设置 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-17
 */
public interface ImmuneControlMapper extends BaseMapper<ImmuneControl> {
	/**
	 * 免疫针次控制设置 列表
	 * @param map
	 * @param pagination
	 * @throws Exception
	 */
	public List<DataRow> immuneControlList(Map<String,String> map,Pagination pagination)throws Exception;
}
