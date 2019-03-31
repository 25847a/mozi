package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.Vaccine;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 疫苗设置 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-14
 */
public interface VaccineMapper extends BaseMapper<Vaccine> {
	/**
	 * 疫苗设置列表
	 * @param map
	 * @param pagination
	 * @return
	 */
	public List<DataRow> vaccineList(Map<String,String> map,Pagination pagination)throws Exception;
}
