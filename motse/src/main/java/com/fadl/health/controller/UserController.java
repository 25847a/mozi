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
import com.fadl.common.SessionUtil;
import com.fadl.health.entity.Equipment;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.User;
import com.fadl.health.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	
	/**
     * 添加使用者用户页面
     * @return
     */
    @RequestMapping("/addUserPage")
    @RequiresPermissions("addUser:view")
    public String addUserPage(){
    	System.out.println(">...................................."+">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	return "/user/adduser";
    }
	/**
	 *  代理商列表
	 * @param map
	 * @return
	 */
    @RequestMapping("/queryAgentList")
    @ResponseBody
    public DataRow queryAgentList(@RequestParam Map<String,String> map){
    	try {
			
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryAgentList",e);
		}
		return messageMap;
    }
    /**
     * 查询添加用户的列表
     * @param map
     * @return
     */
    @RequestMapping("/queryAdduserList")
    @ResponseBody
    public DataRow queryAdduserList(@RequestParam Map<String,Object> map){
    	try {
			Admin admin = SessionUtil.getSessionAdmin();
			map.put("agentid", String.valueOf(admin.getId()));
			messageMap=userService.queryAdduserList(map, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryAdduserList",e);
		}
		return messageMap;
    }
    /**
     * 查询添加用户页面的信息
     * @param map
     * @return
     */
    @RequestMapping("/queryaddUserInfo")
    @ResponseBody
    public DataRow queryaddUserInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=userService.queryaddUserInfo(map, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryaddUserInfo",e);
		}
		return messageMap;
    }
    /**
     * 更改重点关爱状态
     * @param map
     * @return
     */
    @RequestMapping("/updateLoveInfo")
    @ResponseBody
    public DataRow updateLoveInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=userService.updateLoveInfo(map, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryaddUserInfo",e);
		}
		return messageMap;
    }
    /**
     * 查询添加用户预警设置信息
     * @param map
     * @return
     */
    @RequestMapping("/queryPushInfo")
    @ResponseBody
    public DataRow queryPushInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=userService.queryPushInfo(map, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryaddUserInfo",e);
		}
		return messageMap;
    }
    /**
     * 更改用户预警设置信息
     * @param map
     * @return
     */
    @RequestMapping("/updatePushInfo")
    @ResponseBody
    public DataRow updatePushInfo(Push push){
    	try {
    		messageMap=userService.updatePushInfo(push, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>updatePushInfo",e);
		}
		return messageMap;
    }
    /**
     * 点击查询个人详情的信息
     * @param map
     * @return
     */
    @RequestMapping("/queryUserEquipmentInfo")
    @ResponseBody
    public DataRow queryUserEquipmentInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=userService.queryUserEquipmentInfo(map, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryUserEquipmentInfo",e);
		}
		return messageMap;
    }
    /**
     * 上传使用者头像图片
     * @param user
     * @return
     */
    @RequestMapping("/uploadUserPicture")
    @ResponseBody
    public DataRow uploadUserPicture(User user){
    	try {
    	messageMap=userService.uploadUserPicture(user,messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>uploadUserPicture",e);
		}
		return messageMap;
    }
    /**
     * 点击确认修改个人详情的信息
     * @param map
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public DataRow updateUserInfo(User user,Equipment equipment){
    	try {
    		messageMap=userService.updateUserInfo(user,equipment,messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>updateUserInfo",e);
		}
		return messageMap;
    }
    /**
     * 查询添加用户按钮的用户信息
     * @param map
     * @return
     */
    @RequestMapping("/queryImeiUserInfo")
    @ResponseBody
    public DataRow queryImeiUserInfo(String imei){
    	try {
    		messageMap=userService.queryImeiUserInfo(imei, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>queryImeiUserInfo",e);
		}
		return messageMap;
    }
    /**
     * 点击添加用户确定键添加用户
     * @param map
     * @return
     */
    @RequestMapping("/AddUserDetermine")
    @ResponseBody
    public DataRow AddUserDetermine(Equipment equipment){
    	try {
    		messageMap=userService.AddUserDetermine(equipment, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>AddUserDetermine",e);
		}
		return messageMap;
    }
    /**
     * 点击移除用户确定键添加用户
     * @param map
     * @return
     */
    @RequestMapping("/deleteUserDetermine")
    @ResponseBody
    public DataRow deleteUserDetermine(User user){
    	try {
    		messageMap=userService.deleteUserDetermine(user, messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>deleteUserDetermine",e);
		}
		return messageMap;
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/addUserInfo")
    @ResponseBody
    public DataRow addUserInfo(User user,String telephone){
    	try {
    		messageMap=userService.addUserInfo(user,telephone,messageMap);
		} catch (Exception e) {
			logger.error("UserController>>>>>>>>>>>>>addUserInfo",e);
		}
		return messageMap;
    }
}

