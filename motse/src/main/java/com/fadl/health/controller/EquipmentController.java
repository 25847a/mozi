package com.fadl.health.controller;


import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;
import com.fadl.health.service.EquipmentService;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/equipment")
public class EquipmentController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);
	
	@Autowired
	EquipmentService equipmentService;
	/**
     * 设备管理页面
     * @return
     */
    @RequestMapping("/equipmentPage")
    public String equipmentPage(){
    	return "/backstage/equipment";
    }
    /**
	 * 查询设备数据列表
	 */
	@RequestMapping("/queryEquipmentList")
	@ResponseBody
	public DataRow queryEquipmentList(@RequestParam Map<String,Object> map){
		try {
		messageMap=equipmentService.queryEquipmentList(map,messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>queryEquipmentList",e);
		}
		return messageMap;
	}
    /**
     * 在线离线
     * @param equipment
     * @return
     */
    @RequestMapping("/updateBluetooth")
    @ResponseBody
    public DataRow updateBluetooth(Equipment equipment){
    	try {
    		messageMap=equipmentService.updateBluetooth(equipment,messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>updateBluetooth",e);
		}
		return messageMap;
    }
    /**
     * 开始学习
     * @param equipment
     * @return
     */
    @RequestMapping("/startLearning")
    @ResponseBody
    public DataRow startLearning(Equipment equipment){
    	try {
    		messageMap=equipmentService.startLearning(equipment,messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>startLearning",e);
		}
		return messageMap;
    }
    /**
     * 代理商管理页面根据代理商ID查询设备信息
     * @param map
     * @return
     */
    @RequestMapping("/queryEquipmentImeiInfo")
    @ResponseBody
    public DataRow queryEquipmentImeiInfo(@RequestParam Map<String,Object> map){
    	try {
    		messageMap=equipmentService.queryEquipmentImeiInfo(map,messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>queryEquipmentImeiInfo",e);
		}
		return messageMap;
    }
    /**
     * 录入设备到代理商名下
     * @param map
     * @return
     */
    @RequestMapping("/inuptEquipmentImeiInfo")
    @ResponseBody
    public DataRow inuptEquipmentImeiInfo(Integer id,String[] imeis){
    	try {
    		messageMap=equipmentService.inuptEquipmentImeiInfo(id,imeis,messageMap);
		} catch (Exception e) {
			logger.error("EquipmentController>>>>>>>>>>>>>inuptEquipmentImeiInfo",e);
		}
		return messageMap;
    }
    
}

