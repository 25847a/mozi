package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.PrivilegeInjectionMapper;
import com.fadl.immuneAssay.service.PrivilegeInjectionService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 注射管理-特免基础针注射 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Service
public class PrivilegeInjectionServiceImpl extends ServiceImpl<PrivilegeInjectionMapper, PrivilegeInjection> implements PrivilegeInjectionService {

	@Autowired
	private PrivilegeInjectionMapper privilegeInjectionMapper;
	/**
	 * 特免基础针注射  未注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> notShotSpeBase(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(privilegeInjectionMapper.notShotSpeBase(updateDate,page));
	}
	/**
	 * 特免基础针注射  已注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> haveShotSpeBase(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(privilegeInjectionMapper.haveShotSpeBase(updateDate,page));
	}
	/**
	 * 特免基础针注射  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow specialBaseHead(Map<String, Object> map) throws Exception {
		return privilegeInjectionMapper.specialBaseHead(map);
	}

	/**
	 * 啊健(查询特免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	@Override
	public PrivilegeInjection queryPrivilegeInfo(String providerNo) throws SQLException {
		return privilegeInjectionMapper.queryPrivilegeInfo(providerNo);
	}

}
