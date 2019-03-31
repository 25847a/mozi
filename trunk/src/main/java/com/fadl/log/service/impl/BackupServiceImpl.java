package com.fadl.log.service.impl;

import com.fadl.log.entity.Backup;
import com.fadl.common.DataRow;
import com.fadl.log.dao.BackupMapper;
import com.fadl.log.service.BackupService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  数据备份
 * </p>
 *
 * @author wj
 * @since 2018-09-25
 */
@Service
public class BackupServiceImpl extends ServiceImpl<BackupMapper, Backup> implements BackupService {
	@Autowired BackupMapper backupMapper;
	/**
	 * 查询数据备份记录
	 */
	@Override
	public Page<DataRow> queryBackUp(Map<String, Object> map, Page<DataRow> pageing) throws Exception {
		return pageing.setRecords(backupMapper.queryBackUp(map, pageing));
	}
}
