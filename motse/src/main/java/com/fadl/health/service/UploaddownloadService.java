package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Uploaddownload;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 上传下载表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-19
 */
public interface UploaddownloadService extends IService<Uploaddownload> {

	
	/**
	 * 查询版本更新列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUploaddownloadList(DataRow messageMap)throws SQLException;
}
