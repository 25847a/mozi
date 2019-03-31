package com.fadl.plasma.service.impl;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.plasma.dao.ProviderBaseinfoTempMapper;
import com.fadl.plasma.entity.ProviderBaseinfoTemp;
import com.fadl.plasma.service.ProviderBaseinfoTempService;

/**
 * <p>
 * 浆员基本信息临时表 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-09-02
 */
@Service
public class ProviderBaseinfoTempServiceImpl extends ServiceImpl<ProviderBaseinfoTempMapper, ProviderBaseinfoTemp> implements ProviderBaseinfoTempService {

	
	@Autowired
	public ProviderBaseinfoTempMapper providerBaseinfoTempMapper;
	
	/**
	 * 查询临时表浆员详情
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBaseTempInfo(Map<String, String> map)throws Exception{
		return providerBaseinfoTempMapper.queryBaseTempInfo(map);
	}
	
	/**
	 * 根据浆员卡号更新状态
	 * @return
	 * @throws SQLException
	 */
	public int updateProviderBaseInfoTempByProvider(DataRow row)throws SQLException{
		return providerBaseinfoTempMapper.updateProviderBaseInfoTempByProvider(row);
	}
}
