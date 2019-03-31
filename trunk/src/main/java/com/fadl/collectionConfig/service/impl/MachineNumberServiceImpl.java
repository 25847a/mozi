package com.fadl.collectionConfig.service.impl;

import java.util.Map;

import com.fadl.collectionConfig.entity.MachineNumber;
import com.fadl.collectionConfig.dao.MachineNumberMapper;
import com.fadl.collectionConfig.service.MachineNumberService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采浆机号 服务实现类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
@Service
public class MachineNumberServiceImpl extends ServiceImpl<MachineNumberMapper, MachineNumber> implements MachineNumberService {
	@Autowired
	MachineNumberMapper machineNumberMapper;
	
	/**
	 * 查询采浆机号表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public Page<DataRow> queryMachineNumberList(Map<String,Object> map, Page<DataRow> pageing) throws Exception {
		return pageing.setRecords(machineNumberMapper.queryMachineNumberList(map, pageing));
	}
	/**
	 * 新增采浆机号
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertMachineNumber(MachineNumber machineNumber,DataRow messageMap) throws Exception {
		int row = machineNumberMapper.insert(machineNumber);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改采浆机号
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateMachineNumber(MachineNumber machineNumber,DataRow messageMap) throws Exception {
		int row = machineNumberMapper.updateById(machineNumber);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
