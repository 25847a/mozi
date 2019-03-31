package com.fadl.collectionConfig.service.impl;

import java.util.List;
import java.util.Map;

import com.fadl.collectionConfig.entity.Area;
import com.fadl.collectionConfig.dao.AreaMapper;
import com.fadl.collectionConfig.service.AreaService;
import com.fadl.common.AreaAnalysis;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区域设置表 服务实现类
 * </p>
 *
 * @author guokang
 * @since 2018-05-24
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	@Autowired
	AreaMapper areaMapper;
	/**
	 * 新增区域设置
	 */
	@Override
	public void insertArea(Area area,DataRow messageMap) throws Exception {
		int row = areaMapper.insert(area);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改区域设置
	 */
	@Override
	public void updateArea(Area area,DataRow messageMap) throws Exception {
		int row = areaMapper.updateById(area);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	
	/**
	 * 通过截取身份证地址判断合法区域
	 */
	@Override
	public Area judgeArea(String address) {
		try{
			Map<String,String> map = AreaAnalysis.addressResolution(address);
			if(map!=null){
				EntityWrapper<Area> ew = new EntityWrapper<Area>();
				ew.eq("province", map.get("province"));
				ew.eq("county", map.get("county"));
				if(null!=map.get("town")){
					ew.like("town", map.get("town"),SqlLike.DEFAULT);
				}
				List<Area> a =this.selectList(ew);
				if(null!=a){
					for(Area da:a){
						String county =da.getCounty();
						if(county.equals(map.get("county"))){
							String town = da.getTown();
							if(town.equals(map.get("town"))){
								return da;
							}
						}
					}
				}
			}
		}catch(Exception ex){
			logger.error("AreaServiceImpl>>>>>>>>>judgeArea>>>>>",ex);
		}
		return null;
	}
}