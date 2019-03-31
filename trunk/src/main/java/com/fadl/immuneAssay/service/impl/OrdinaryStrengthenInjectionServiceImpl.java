package com.fadl.immuneAssay.service.impl;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.OrdinaryStrengthenInjectionMapper;
import com.fadl.immuneAssay.entity.OrdinaryStrengthenInjection;
import com.fadl.immuneAssay.service.OrdinaryStrengthenInjectionService;

/**
 * <p>
 * 注射管理-普免加强针注射 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Service
public class OrdinaryStrengthenInjectionServiceImpl extends ServiceImpl<OrdinaryStrengthenInjectionMapper, OrdinaryStrengthenInjection> implements OrdinaryStrengthenInjectionService {

	@Autowired
	private OrdinaryStrengthenInjectionMapper ordinaryStrengthenInjectionMapper;
	/**
	 * 普免加强针注射  未注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> notShotOrdStrengthen(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryStrengthenInjectionMapper.notShotOrdStrengthen(updateDate,page));
	}
	/**
	 * 普免加强针注射  已注射人员
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> haveShotOrdStrengthen(String updateDate, Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryStrengthenInjectionMapper.haveShotOrdStrengthen(updateDate,page));
	}
	/**
	 * 普免加强针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow ordinaryStrengthenHead(Map<String, Object> map) throws Exception {
		return ordinaryStrengthenInjectionMapper.ordinaryStrengthenHead(map);
	}
	/**
	 * 啊健(查询普免加强登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	@Override
	public OrdinaryStrengthenInjection queryStrengInjectionInfo(String providerNo) throws SQLException {
		return ordinaryStrengthenInjectionMapper.queryStrengInjectionInfo(providerNo);
	}
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryVaccineInfoStreng(String immune) throws Exception {
		return ordinaryStrengthenInjectionMapper.queryVaccineInfoStreng(immune);
	}
	/**
	 * 获取加强免疫所有类别
	 * @param id
	 * @return
	 */
	@Override
	public DataRow getBaseImmuneStreng(String id) throws SQLException {
		return ordinaryStrengthenInjectionMapper.getBaseImmuneStreng(id);
	}

}
