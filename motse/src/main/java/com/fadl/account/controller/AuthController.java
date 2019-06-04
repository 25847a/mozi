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
import com.fadl.account.entity.Auth;
import com.fadl.account.service.AuthService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;

/**
 * <p>
 * 权限菜单表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	AuthService authService;
	/**
	 * 跳转菜单页面
	 * @return
	 */
	@RequestMapping("/authPage")
	public String login(){
		return "/admin/menu";
	}
	
	/**
     * 查询菜单管理列表
     * @return
     */
    @RequestMapping("/queryAuthInfoList")
    @ResponseBody
    public DataRow queryAuthInfoList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap = authService.queryAuthInfoList(map,messageMap);
		} catch (Exception e) {
			logger.error("AuthController<<<<<<<<<<<<<<<<<<queryAuthInfoList",e);
		}
		return messageMap;
    }
    /**
     * 新增菜单信息
     * @return
     */
    @RequestMapping("/addAuthInfo")
    @ResponseBody
    public DataRow addAuthInfo(Auth auth){
    	try {
    		messageMap = authService.addAuthInfo(auth,messageMap);
		} catch (Exception e) {
			logger.error("AuthController<<<<<<<<<<<<<<<<<<addAuthInfo",e);
		}
		return messageMap;
    }
    /**
     * 修改菜单信息
     * @return
     */
    @RequestMapping("/updateAuthInfo")
    @ResponseBody
    public DataRow updateAuthInfo(Auth auth){
    	try {
    		messageMap = authService.updateAuthInfo(auth,messageMap);
		} catch (Exception e) {
			logger.error("AuthController<<<<<<<<<<<<<<<<<<updateAuthInfo",e);
		}
		return messageMap;
    }
    /**
     * 删除菜单信息
     * @return
     */
    @RequestMapping("/deleteAuthInfo")
    @ResponseBody
    public DataRow deleteAuthInfo(Auth auth){
    	try {
    		messageMap = authService.deleteAuthInfo(auth,messageMap);
		} catch (Exception e) {
			logger.error("AuthController<<<<<<<<<<<<<<<<<<deleteAuthInfo",e);
		}
		return messageMap;
    };
    /**
	 * 查询角色全部信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryAuthInfo")
	@ResponseBody
	public DataRow queryAuthInfo(){
		try {
			List<Auth> list = authService.queryAuthInfo();
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("RoleController>>>>>>>>>>>>>queryAuthInfo",e);
		}
		return messageMap;
	}
	
}

