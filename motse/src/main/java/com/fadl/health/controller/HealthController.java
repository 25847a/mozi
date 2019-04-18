package com.fadl.health.controller;


import java.util.List;
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
	 * 养老院所有的数据都是从这个接口获取
	 */
	@RequestMapping("queryBeadhouseList")
	@ResponseBody
	public DataRow queryBeadhouseList(){
		try {
		//	List<DataRow> tableData = healthService.queryHealthList();//列表数据
			//messageMap.initSuccess(tableData);
		} catch (Exception e) {
			logger.error("HealthController>>>>>>>>>>>>>queryBeadhouseList",e);
		}
		return messageMap;
	}
}

