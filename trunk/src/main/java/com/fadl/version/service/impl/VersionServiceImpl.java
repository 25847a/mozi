package com.fadl.version.service.impl;

import com.fadl.version.entity.Version;
import com.fadl.common.DataRow;
import com.fadl.version.dao.VersionMapper;
import com.fadl.version.service.VersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统版本表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-09-25
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements VersionService {
	
	@Autowired
	VersionMapper versionMapper;
	
	/**
	 * 新增版本
	 * @param v
	 * @param messageMap
	 * @throws Exception
	 */
	@Override
	public void insertVersion(Version v, DataRow messageMap) throws Exception {
		v.setVersionNumber(v.getVersionNumber().trim());
		v.setVersionDescription(v.getVersionDescription().replaceAll("\\s*",""));
		int row =versionMapper.insert(v);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initError();
		}
	}

}
