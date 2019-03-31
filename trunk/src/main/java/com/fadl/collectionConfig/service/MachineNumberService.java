package com.fadl.collectionConfig.service;

import java.util.Map;

import com.fadl.collectionConfig.entity.MachineNumber;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 采浆机号 服务类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
public interface MachineNumberService extends IService<MachineNumber> {
	
	/**
	 * 查询采浆机号表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<DataRow> queryMachineNumberList(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 新增采浆机号
	 * @param situationConfig
	 * @return
	 */
	public void insertMachineNumber(MachineNumber machineNumber,DataRow messageMap)throws Exception;
	/**
	 * 修改采浆机号
	 * @param situationConfig
	 * @return
	 */
	public void updateMachineNumber(MachineNumber machineNumber,DataRow messageMap)throws Exception;
}
