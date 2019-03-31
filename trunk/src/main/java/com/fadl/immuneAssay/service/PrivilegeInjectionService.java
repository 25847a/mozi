package com.fadl.immuneAssay.service;

import java.sql.SQLException;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.PrivilegeInjection;

/**
 * <p>
 * 注射管理-特免基础针注射 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
public interface PrivilegeInjectionService extends IService<PrivilegeInjection> {
	
	/**
	 * 特免基础针注射  未注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> notShotSpeBase (String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 特免基础针注射  已注射
	 * @param updateDate
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> haveShotSpeBase (String updateDate,Page<DataRow> page) throws Exception;
	
	/**
	 * 特免基础针注射  头部内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow specialBaseHead(Map<String, Object> map) throws Exception;
	
	/**
	 * 啊健(查询特免登记表信息)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public PrivilegeInjection queryPrivilegeInfo(String providerNo) throws SQLException;
	
}
