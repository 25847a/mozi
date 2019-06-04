package com.fadl.system.controller;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.health.controller.HealthController;
import com.fadl.system.entity.Nurse;
import com.fadl.system.service.NurseService;

/**
 * <p>
 * 护士表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Controller
@RequestMapping("/nurse")
public class NurseController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	/**
	 * 跳转护士页面
	 * @return
	 */
	@RequestMapping("/nursePage")
	public String nursePage(){
		return "/system/nurse";
	}
	
	@Autowired
	NurseService nurseService;
	
	
	 /**
     * 查询分属护士列表
     * @return
     */
    @RequestMapping("/queryNurseList")
    @ResponseBody
    public DataRow queryNurseList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap = nurseService.queryNurseList(map,messageMap);
		} catch (Exception e) {
			logger.error("NurseController<<<<<<<<<<<<<<<<<<queryNurseList",e);
		}
		return messageMap;
    }
    /**
     * 新增分属护士信息
     * @return
     */
    @RequestMapping("/addNurseInfo")
    @ResponseBody
    public DataRow addNurseInfo(Nurse nurse){
    	try {
    		messageMap = nurseService.addNurseInfo(nurse,messageMap);
		} catch (Exception e) {
			logger.error("NurseController<<<<<<<<<<<<<<<<<<addNurseInfo",e);
		}
		return messageMap;
    }
    /**
     * 修改分属护士信息
     * @return
     */
    @RequestMapping("/updateNurseInfo")
    @ResponseBody
    public DataRow updateNurseInfo(Nurse nurse){
    	try {
    		messageMap = nurseService.updateNurseInfo(nurse,messageMap);
		} catch (Exception e) {
			logger.error("NurseController<<<<<<<<<<<<<<<<<<updateNurseInfo",e);
		}
		return messageMap;
    }
    /**
     * 删除分属护士信息
     * @return
     */
    @RequestMapping("/deleteNurseInfo")
    @ResponseBody
    public DataRow deleteNurseInfo(Long id){
    	try {
    		messageMap = nurseService.deleteNurseInfo(id,messageMap);
		} catch (Exception e) {
			logger.error("NurseController<<<<<<<<<<<<<<<<<<deleteNurseInfo",e);
		}
		return messageMap;
    }
    
	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryNurseInfo")
	@ResponseBody
	public DataRow queryNurseInfo(){
		try {
		Admin admin = SessionUtil.getSessionAdmin();
		messageMap=nurseService.queryNurseInfo(admin,messageMap);
		} catch (Exception e) {
			logger.error("NurseController>>>>>>>>>>>>>queryNurseInfo",e);
		}
		return messageMap;
	}
}

