package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Info;

/**
 * <p>
  * 耗材信息表 Mapper 接口
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
public interface InfoMapper extends BaseMapper<Info> {
	/**
	 * 查询耗材信息表列表
	 * @param rowBunds
	 * @return
	 */
	public List<DataRow> querySuppliesInfoList(Map<String,String> map,Pagination page);
}