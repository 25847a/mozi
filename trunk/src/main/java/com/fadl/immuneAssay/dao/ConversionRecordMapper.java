package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ConversionRecord;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 特免转类记录表 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-07-29
 */
public interface ConversionRecordMapper extends BaseMapper<ConversionRecord> {

	/**
	 * 特免转类列表
	 * @param page
	 * @return
	 */
	List<DataRow> specialTransferList(Map<String,String> map,Page<DataRow> page) throws SQLException;

	/**
	 * 特免转类  头部内容
	 * @param id
	 * @return
	 */
	DataRow querySpecialTransferHead(Map<String,String> map) throws SQLException;
	
	/**
	 * 已转类浆员列表
	 * @return
	 * @throws SQLException
	 */
	List<DataRow> conversionRecordList(Map<String,String> map,Page<DataRow> page) throws SQLException;

}
