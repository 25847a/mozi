package com.fadl.health.service.impl;

import com.fadl.health.entity.Uploaddownload;
import com.fadl.common.DataRow;
import com.fadl.health.dao.UploaddownloadMapper;
import com.fadl.health.service.UploaddownloadService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 上传下载表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-19
 */
@Service
public class UploaddownloadServiceImpl extends ServiceImpl<UploaddownloadMapper, Uploaddownload> implements UploaddownloadService {
	
	@Autowired
	UploaddownloadMapper uploaddownloadMapper;
	/**
	 * 查询版本更新列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryUploaddownloadList(DataRow messageMap) throws SQLException {
		List<DataRow> list = uploaddownloadMapper.queryUploaddownloadList();
		messageMap.initSuccess(list);
		return messageMap;
	}

}
