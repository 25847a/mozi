package com.fadl.plasma.dao;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.DataRow;
import com.fadl.plasma.entity.ProviderBaseinfoTemp;

/**
 * <p>
 * 浆员基本信息临时表 Mapper 接口
 * </p>
 *
 * @author zll
 * @since 2018-09-02
 */
public interface ProviderBaseinfoTempMapper extends BaseMapper<ProviderBaseinfoTemp> {

	/**
	 * 查询临时表浆员详情
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBaseTempInfo(Map<String, String> map)throws SQLException;
	
	/**
	 * 根据浆员卡号更新状态
	 * @return
	 * @throws SQLException
	 */
	public int updateProviderBaseInfoTempByProvider(DataRow row)throws SQLException;
}
