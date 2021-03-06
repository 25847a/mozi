package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Uploaddownload;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 上传下载表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-19
 */
public interface UploaddownloadMapper extends BaseMapper<Uploaddownload> {

	/**
	 * 查询版本更新列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryUploaddownloadList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询版本更新列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryUploaddownloadListCount(Map<String,Object> map)throws SQLException;
}
