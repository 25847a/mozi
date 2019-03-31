package com.fadl.immuneAssay.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ConversionRecord;

/**
 * <p>
 * 特免转类记录表 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-27
 */
public interface ConversionRecordService extends IService<ConversionRecord> {

	
	/**
	 * 特免转类列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> specialTransferList(Map<String,String> map,Page<DataRow> page) throws Exception;
	
	/**
	 * 特免转类  头部内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void querySpecialTransferHead(Map<String,String> map,DataRow messageMap) throws Exception;
	
	/**
	 * 特免转类  转类
	 * @param providerNo
	 * @param thisStatus
	 * @param transferType
	 * @return
	 * @throws Exception
	 */
	public void transferType(String providerNo,String thisStatus,String transferType,DataRow messageMap) throws Exception;
	
	/**
	 * 已转类浆员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> conversionRecordList (Map<String,String> map,Page<DataRow> page) throws Exception;
}
