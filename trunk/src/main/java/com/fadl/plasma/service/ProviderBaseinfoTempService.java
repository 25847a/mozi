package com.fadl.plasma.service;

import com.fadl.common.DataRow;
import com.fadl.plasma.entity.ProviderBaseinfoTemp;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 浆员基本信息临时表 服务类
 * </p>
 *
 * @author zll
 * @since 2018-09-02
 */
public interface ProviderBaseinfoTempService extends IService<ProviderBaseinfoTemp> {

	/**
	 * 查询临时表浆员详情
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBaseTempInfo(Map<String, String> map)throws Exception;
	
	/**
	 * 根据浆员卡号更新状态
	 * @return
	 * @throws SQLException
	 */
	public int updateProviderBaseInfoTempByProvider(DataRow row)throws SQLException;
}
