package com.fadl.health.controller;


import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.fadl.common.JsonUtil;
import com.fadl.common.SessionUtil;
import com.fadl.common.WebSocketServer;
import com.fadl.health.service.EquipmentService;
import com.fadl.health.service.HealthService;
import com.fadl.health.service.UserService;

/**
 * <p>
 * 惊凡给的数据表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/health")
public class HealthController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	EquipmentService equipmentService;
	@Autowired
	HealthService healthService;
	/**
	 * 跳转历史健康数据页面
	 * @return
	 */
	@RequestMapping("/historyPage")
	@RequiresPermissions("history:view")
	public String historyPage(){
		return "/health/history";
	}
	/**
	 * 跳转重点关爱管理页面
	 * @return
	 */
	@RequestMapping("/lovePage")
	public String lovePage(){
		return "/health/love";
	}
	/**
	 * 跳转健康数据管理页面
	 * @return
	 */
	@RequestMapping("/healthPage")
	public String healthPage(){
		return "/backstage/health";
	}
  
    /**
     * 养老院页面
     * @return
     */
    @RequestMapping("/queryBeadhouseList")
  //  @RequiresPermissions("admin:query")
    @ResponseBody//page
    public DataRow queryBeadhouseList(@RequestParam Map<String,Object> map){
    	try {
    		if(map.get("page").equals("")){
    			map.put("page", "1");
    		}
    		Admin admin = SessionUtil.getSessionAdmin();
        	System.out.println(admin.getAccount()+">>>>>>>>>>>>>");
        	map.put("adminId", admin.getId());
        	messageMap=healthService.queryBeadhouseList(messageMap,map);
        	WebSocketServer.sendInfo(JsonUtil.getMapper().writeValueAsString(messageMap), String.valueOf(admin.getId()));
		} catch (Exception e) {
			logger.error("HealthController<<<<<<<<<<<<<<<<<<queryBeadhouseList",e);
		}
		return messageMap;
    }
	
	/**
	 * 查询历史健康数据
	 */
	@RequestMapping("/queryHistoryList")
	@ResponseBody
	public DataRow queryHistoryList(@RequestParam Map<String,Object> map){
		try {
		Admin admin = SessionUtil.getSessionAdmin();
		map.put("agentid", admin.getId());
		messageMap=healthService.queryHistoryList(map,messageMap);
		} catch (Exception e) {
			logger.error("HealthController>>>>>>>>>>>>>queryBeadhouseList",e);
		}
		return messageMap;
	}
	/**
	 * 查询健康数据管理列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryHealthInfoList")
	@ResponseBody
	public DataRow queryHealthInfoList(@RequestParam Map<String,Object> map){
		try {
		messageMap=healthService.queryHealthInfoList(map,messageMap);
		} catch (Exception e) {
			logger.error("HealthController>>>>>>>>>>>>>queryHealthInfoList",e);
		}
		return messageMap;
	}
}

