package com.fadl.log.dao;

import com.fadl.common.DataRow;
import com.fadl.log.entity.Backup;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wj
 * @since 2018-09-25
 */
public interface BackupMapper extends BaseMapper<Backup> {

	List<DataRow> queryBackUp(Map<String, Object> map, Page<DataRow> pageing);

}
