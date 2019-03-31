package com.fadl.collectionConfig.service.impl;

import com.fadl.collectionConfig.entity.Room;
import com.fadl.collectionConfig.dao.RoomMapper;
import com.fadl.collectionConfig.service.RoomService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采室表 服务实现类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
	
	
	@Autowired
	RoomMapper roomMapper;
	/**
	 * 新增采室表
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertRoom(Room room,DataRow messageMap) throws Exception {
		int row = roomMapper.insert(room);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改采室表
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateRoom(Room room,DataRow messageMap) throws Exception {
		int row = roomMapper.updateById(room);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
