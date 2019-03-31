package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.InfoMapper;
import com.fadl.supplies.entity.Info;
import com.fadl.supplies.service.InfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 耗材信息表 服务实现类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

	@Autowired
	InfoMapper infoMapper;
	
	/**
	 * 查询耗材信息表列表
	 * @param suppliesInfo
	 * @param page
	 * @param limit
	 * @return
	 */	
	@Override
	public void querySuppliesInfoList( Page<DataRow> page,Map<String,String> map) throws Exception {
 		page.setRecords(infoMapper.querySuppliesInfoList(map, page));
	}
	/**
	 * 新增耗材信息表
	 * @param suppliesInfo
	 * @return
	 */
	@Override
	public void insertSuppliesInfo(Info suppliesInfo,DataRow messageMap) throws Exception {
		int row = infoMapper.insert(suppliesInfo);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改耗材信息表
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateSuppliesInfo(Info suppliesInfo,DataRow messageMap) throws Exception {
		int row = infoMapper.updateById(suppliesInfo);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除耗材信息表
	 * @param id
	 * @return
	 */
	@Override
	public void deleteSuppliesInfo(Long ids,DataRow messageMap) throws Exception {
		EntityWrapper<Info> ew = new EntityWrapper<Info>();
		Info info= new Info();
		info.setIsDelete(1);
		ew.eq("id", ids);
		int row = infoMapper.update(info, ew);
		if(row>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	
}
