package com.fadl.inspection.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.SpecimenCollection;

/**
 * <p>
 * 检验-标本采集 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface SpecimenCollectionMapper extends BaseMapper<SpecimenCollection> {
	
	List<DataRow> queryListByCreateDateAndIsCollection(Pagination page, SpecimenCollection collection);
	
	DataRow querySpecimenCollectionByEntity(Long id);
	
	void updateWithCollection(SpecimenCollection collection);
	
	/**
	 * 浆员采小血打印小票
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	HashMap<String, String> printSpecimenCollection(@Param("id") Long id, @Param("isSpecimen") Long isSpecimen)throws SQLException;
	/**
	 * 根据小样号前面日期得到所有被使用的小样号集合
	 * @param dateStr
	 * @return
	 */
	List<String> selectAllSampleNoByDateStr(String dateStr);
	
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate);
	
}
