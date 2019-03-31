package com.fadl.version.service;

import com.fadl.common.DataRow;
import com.fadl.version.entity.Version;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统版本表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-09-25
 */
public interface VersionService extends IService<Version> {
	/**
	 * 新增版本
	 * @param v
	 * @param messageMap
	 * @throws Exception
	 */
	public void insertVersion(Version v,DataRow messageMap)throws Exception;
}
