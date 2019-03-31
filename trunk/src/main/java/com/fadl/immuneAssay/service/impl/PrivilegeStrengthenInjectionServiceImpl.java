package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.PrivilegeStrengthenInjection;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.PrivilegeStrengthenInjectionMapper;
import com.fadl.immuneAssay.service.PrivilegeStrengthenInjectionService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 注射管理-特免加强针注射 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Service
public class PrivilegeStrengthenInjectionServiceImpl extends ServiceImpl<PrivilegeStrengthenInjectionMapper, PrivilegeStrengthenInjection> implements PrivilegeStrengthenInjectionService {

	@Autowired
	private PrivilegeStrengthenInjectionMapper privilegeStrengthenInjectionMapper;
	/**
	 * 特免加强针注射  未注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> notShotSpeStrengthen(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(privilegeStrengthenInjectionMapper.notShotSpeStrengthen(updateDate,page));
	}
	/**
	 * 特免加强针注射  已注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> haveShotSpeStrengthen(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(privilegeStrengthenInjectionMapper.haveShotSpeStrengthen(updateDate,page));
	}
	/**
	 * 特免加强针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow specialStrengthenHead(Map<String, Object> map) throws Exception {
		return privilegeStrengthenInjectionMapper.specialStrengthenHead(map);
	}
	/**
	 * 啊健(查询特免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	@Override
	public PrivilegeStrengthenInjection queryStrengPrivilegeInfo(String providerNo) throws SQLException {
		return privilegeStrengthenInjectionMapper.queryStrengPrivilegeInfo(providerNo);
	}

}
