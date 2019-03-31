package com.fadl.collectionConfig.service;
import com.fadl.collectionConfig.entity.Room;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 采室表 服务类
 * </p>
 *
 * @author caijian
 * @since 2018-04-21
 */
public interface RoomService extends IService<Room> {
	/**
	 * 新增采室表
	 * @param situationConfig
	 * @return
	 */
	public void insertRoom(Room room,DataRow messageMap)throws Exception;
	/**
	 * 修改采室表
	 * @param situationConfig
	 * @return
	 */
	public void updateRoom(Room room,DataRow messageMap)throws Exception;
}
