package com.fadl.collectionConfig.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.collectionConfig.entity.PlasmType;
import com.fadl.collectionConfig.entity.Room;
import com.fadl.collectionConfig.service.RoomService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
/**
 * <p>
 * 采室表 前端控制器
 * </p>
 * @author caijian&guokang
 * @since 2018-04-21
 */
@Controller
@RequestMapping("/room")
public class RoomController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(RoomController.class);
	@Autowired
	RoomService roomService;
	
	/**
	 * 跳转采浆机型页面
	 * @return
	 */
	@RequestMapping("/room")
	@RequiresPermissions("room:view")
	public String room() {
		return "/collectionConfig/room";
	}
	
	/**
	 * 跳转采浆机型新增页面
	 * @return
	 */
	@RequestMapping("/roomAdd")
	@RequiresPermissions("room:add")
	public String roomAdd() {
		return "/collectionConfig/room_add";
	}
	
	/**
	 * 跳转采浆机型修改页面
	 * @return
	 */
	@RequestMapping("/roomDetails")
	@RequiresPermissions("room:update")
	public String roomDetails(String id,Model model ) {
		try {
			Room room = new Room();
			room = roomService.selectById(id);
    		model.addAttribute("room",room);
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>roomDetails>>>>>",e);
		}
		return "/collectionConfig/room_details";
	}
	
	/**
	 * 采室表列表
	 * @param situationConfig
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryRoomList",method= RequestMethod.POST)
	@InvokeLog(name = "queryRoomList", description = "采室表列表")
	@ResponseBody
	@RequiresPermissions("room:view")
	public DataRow queryRoomList(Room room, Integer page, Integer limit) {
		Page<Room> paging = null;
		try {
			EntityWrapper<Room> ew=new EntityWrapper<Room>();
	        ew.where("isDelete=0");
	        ew.like("name", room.getName(),SqlLike.DEFAULT);
			paging = new Page<Room>(page, limit);
			paging = roomService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>>>>>queryRoomList",e);
		}
		return messageMap;
	}
	/**
	 * 新增采室表
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/insertRoom",method= RequestMethod.POST)
	@InvokeLog(name = "insertRoom", description = "新增采室表")
	@ResponseBody
	@RequiresPermissions("room:add")
	public DataRow insertRoom(Room room) {
		try {
			room.setIsDelete(0);
			roomService.insertRoom(room,messageMap);
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>>>>>insertRoom",e);
		}
		return messageMap;
	}

	/**
	 * 修改采室表
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/updateRoom",method= RequestMethod.POST)
	@InvokeLog(name = "updateRoom", description = "修改采室表")
	@ResponseBody
	@RequiresPermissions("room:update")
	public DataRow updateRoom(Room room) {
		try {
			roomService.updateRoom(room,messageMap);
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>>>>>updateRoom",e);
		}
		return messageMap;
	}
	
	/**
	 * 删除采室表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteRoom",method= RequestMethod.POST)
	@InvokeLog(name = "deleteRoom", description = "删除采室表")
	@ResponseBody
	@RequiresPermissions("room:del")
	public DataRow deleteRoom(@RequestParam Long ids) {
		try {
			Room room = new Room();
			EntityWrapper<PlasmType> ew = new EntityWrapper<PlasmType>();
			ew.eq("roomId", ids);
			ew.eq("isDelete", 0);
			//先判断属本采室的采浆机型是否已全部删除
			if(null==new PlasmType().selectOne(ew)){
				room.setIsDelete(1);
				room.setId(ids);
				boolean res = roomService.updateById(room);
				if(res){
					messageMap.initSuccess();
				}else {
					messageMap.initFial();
				}
			}else{
				messageMap.initFial("请先删除属于该采室的机型！");
			}
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>>>>>deleteRoom",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询采室列表
	 * @return
	 */
	@RequestMapping(value="/queryRoomByPlasmType",method= RequestMethod.POST)
	@InvokeLog(name = "queryRoomByPlasmType", description = "查询采室列表")
	@ResponseBody
	public DataRow queryRoomByPlasmType() {
		try {
			EntityWrapper<Room> ew = new EntityWrapper<Room>();
			ew.where("isDelete=0");
			List<Room> list =roomService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("RoomController>>>>>>>>>>>>>queryRoomByPlasmType",e);
			messageMap.initFial();
		}
		return messageMap;
	}
}
