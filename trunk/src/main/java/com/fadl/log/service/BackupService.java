package com.fadl.log.service;

import com.fadl.common.DataRow;
import com.fadl.log.entity.Backup;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  数据备份
 * </p>
 *
 * @author wj
 * @since 2018-09-25
 */
public interface BackupService extends IService<Backup> {
	/**
	 * 查询数据备份记录
	 * @param map
	 * @param pageing
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryBackUp(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
}
