package com.plasma.buss.role.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.role.service.MenuService;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.VP;
import com.plasma.common.util.Validator;

/**
 * 菜单管理
 * @author hu
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class MenuAction extends BaseController {

	private static final Logger log = LogManager.getLogger(UserRoleAction.class);
	
	@Autowired
	public MenuService menuService;
	
	/**
	 * 菜单管理页面
	 * @return
	 */
	@RequestMapping("/menu")
	public String menu() {
		return "config/menu_list";
	}
	
	/**
	 * 菜单列表
	 * @return
	 */
	@RequestMapping("/menuList")
	@ResponseBody
	public Object menuList(){
		try {
			pageBean = menuService.queryMenuList(info,pageBean);
			DataRow dr = new DataRow();
			dr.put("pageNum", pageBean.pageNum);
			dr.put("pageSize", pageBean.getPageSize());
			dr.put("listData", pageBean.getPage());
			dr.put("totalNum", pageBean.getTotalNum());
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>roleList>>>",e);
		}
		return messageMap;
	} 
	/**
	 * 菜单全部列表
	 * @return
	 */
	@RequestMapping("/menuAllList")
	@ResponseBody
	public Object menuAllList(){
		try {
			List<DataRow> drList =menuService.menuAllList();
			DataRow dr = new DataRow();
			dr.put("listData", drList);
			super.fillResultContext(messageMap, dr);
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>menuAllList>>>",e);
		}
		return messageMap;
	} 
	
	/**
	 * 添加或修改菜单
	 * @return
	 */
	@RequestMapping("/saveOrUpdateMenu")
	@ResponseBody
	public Object saveOrUpdateMenu(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("menuName");
			val.addParamCheck("pid");
			if (val.validate()) {
				if (StringHelper.isEmpty(info.getString("id"))) {
					menuService.saveMenu(info);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
				}else{
					int res = menuService.updateMenuById(info);
					if (res > 0) {
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
						messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
					}else{
						messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
						messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
					}
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>saveOrUpdateMenu>>>",e);
		}
		return messageMap;
	}
	
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("/delMenu")
	@ResponseBody
	public Object delMenu(){
		try {
			Validator val = new Validator(info,messageMap);
			val.addParamCheck("id",VP.NUMBER);
			if (val.validate()) {
				int res = menuService.deleteMenuRole(info.getLong("id"));
				if (res> 0) {
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
				}else{
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
				}
			}
		} catch (Exception e) {
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
			log.error("UserRoleAction>>>>>updateRole>>>",e);
		}
		return messageMap;
	}
}
