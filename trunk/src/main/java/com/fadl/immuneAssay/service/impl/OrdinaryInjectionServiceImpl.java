package com.fadl.immuneAssay.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.OrdinaryInjectionMapper;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.service.OrdinaryInjectionService;

/**
 * <p>
 * 注射管理--普免基础针注射 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@Service
public class OrdinaryInjectionServiceImpl extends ServiceImpl<OrdinaryInjectionMapper, OrdinaryInjection> implements OrdinaryInjectionService {

	@Autowired
	private OrdinaryInjectionMapper ordinaryInjectionMapper;
	/**
	 * 普免基础针 未注射人员（分页）
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> notShotOrdBaseList(String updateDate,Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryInjectionMapper.notShotOrdBaseList(updateDate,page));
	}
	/**
	 * 普免基础针 已注射人员（分页）
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> shotOrdBaseList(String updateDate,Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryInjectionMapper.shotOrdBaseList(updateDate,page));
	}
	/**
	 * 普免基础针  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow ordinaryBaseHead(Map<String, Object> map) throws Exception {
		return ordinaryInjectionMapper.ordinaryBaseHead(map);
	}
	/**
	 * 注射信息查询 
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> queryShotMsg(Map<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryInjectionMapper.queryShotMsg(map,page));
	}
	/**
	 * 免疫注射信息      免疫基本信息
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<DataRow> imuneMsg(Map<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(ordinaryInjectionMapper.imuneMsg(map,page));
	}
	/**
	 * 免疫注射信息     基础针注射信息
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public void baseInjectMsg(Map<String,String> map, Page<DataRow> page) throws Exception {
		page.setRecords(ordinaryInjectionMapper.baseInjectMsg(map,page));
	}
	/**
	 * 免疫注射信息       加强针注射信息
	 * @param providerNo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public void strengthenInjectMsg(Map<String,String> map, Page<DataRow> page) throws Exception {
		page.setRecords(ordinaryInjectionMapper.strengthenInjectMsg(map,page));
	}
	/**
	 * 获取基础免疫所有类别
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow getBaseImmuneTypes(String id) throws Exception {
		return ordinaryInjectionMapper.getBaseImmuneTypes(id);
	}
	/**
	 * 获取疫苗信息(啊健)
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryVaccineInfo(String immune) throws Exception {
		return ordinaryInjectionMapper.queryVaccineInfo(immune);
	}
	/**
	 * 获取加强免疫所有类别
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataRow> getStreImmuneTypes(String id) throws Exception {
		return ordinaryInjectionMapper.getStreImmuneTypes(id);
	}
	
	/**
	 * 啊健(查询普免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	@Override
	public OrdinaryInjection queryInjectionInfo(String providerNo) throws SQLException {
		return ordinaryInjectionMapper.queryInjectionInfo(providerNo);
	}
	/**
	 * 打印注射文件
	 * @param map
	 * @return
	 */
	@Override
	public void downloadImmune(Map<String, String> map, DataRow messageMap) throws Exception {
		List<DataRow> list =ordinaryInjectionMapper.downloadImmune(map);
		if(!list.isEmpty()) {
			messageMap.initSuccess(list);
		}else {
			messageMap.initError();
		}
		
		
	}
}
