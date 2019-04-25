package com.fadl.account.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.entity.Role;
import com.fadl.account.service.RoleService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
/**
 * <p>
 * 后台页面角色表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	RoleService roleService;
	/**
	 * 跳转角色管理页面
	 * @return
	 */
	@RequestMapping("/rolePage")
	public String historyPage(){
		return "/admin/role";
	}
	
	/**
	 * 查询角色管理列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryRoleList")
	@ResponseBody
	public DataRow queryHealthInfoList(@RequestParam Map<String,String> map){
		try {
			
		messageMap=roleService.queryRoleList(messageMap);
		} catch (Exception e) {
			logger.error("RoleController>>>>>>>>>>>>>queryRoleList",e);
		}
		return messageMap;
	}
	/**
	 * 查询角色管理信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryRoleInfo")
	@ResponseBody
	public DataRow queryRoleInfo(){
		try {
			List<Role> list = roleService.selectList(null);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("RoleController>>>>>>>>>>>>>queryRoleInfo",e);
		}
		return messageMap;
	}
}

