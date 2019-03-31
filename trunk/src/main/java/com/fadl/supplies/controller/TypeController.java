package com.fadl.supplies.controller;

 
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.supplies.entity.Type;
import com.fadl.supplies.service.TypeService;
/**
 * <p>
 * 耗材类别信息 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
@Controller
@RequestMapping("/type")
public class TypeController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(TypeController.class);
	
	@Autowired
	TypeService typeService;
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping("/typePage")
	@RequiresPermissions("type:view")
    public String typePage() {
        return "/supplies/config/type";
    }
    /**
     * 跳转类型添加页面
     */
    @RequestMapping("/typeAddPage")
    public String typeAddPage() {
        return "/supplies/config/type_add";
    }
    /**
	 * 跳转类型修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/typeUpdate")
    public String typeUpdate(String id, Model model) {
    	try {
    		Type type = new Type();
    		type = typeService.selectById(id);
    		model.addAttribute("type",type);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>typeUpdate>>>>>",e);
		}
        return "/supplies/config/type_update";
    }
	/**
	 * 查询耗材类别信息列表
	 * @param unit
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryTypeList")
	@InvokeLog(name = "queryTypeList", description = "查询耗材类别信息列表")
	@ResponseBody
	public DataRow queryTypeList(Type type, Integer page, Integer limit) {
		Page<Type> paging = null;
		try {
			EntityWrapper<Type> ew = new EntityWrapper<Type>();
			ew.like("name", type.getName(), SqlLike.DEFAULT);
			ew.eq("isDelete", 0);
			// depot 以后可以用这个做为分页的查询条件
			paging = new Page<Type>(page, limit);
			paging = typeService.selectPage(paging,ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>>>>>queryTypeList",e);
		}
		return messageMap;
	}
	/**
	 * 新增耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/insertType")
	@InvokeLog(name = "insertType", description = "新增耗材类别信息")
	@ResponseBody
	@RequiresPermissions("type:add")
	public DataRow insertType(Type type) {
		try {
			typeService.insertType(type,messageMap);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>>>>>insertType",e);
		}
		return messageMap;
	}

	/**
	 * 修改耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	@RequestMapping(value = "/updateType")
	@InvokeLog(name = "updateType", description = "修改耗材类别信息")
	@ResponseBody
	@RequiresPermissions("type:update")
	public DataRow updateType(Type type) {
		try {
			typeService.updateType(type,messageMap);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>>>>>updateType",e);
		}
		return messageMap;
	}
	/**
	 * 删除耗材类别信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteType")
	@InvokeLog(name = "deleteType", description = "删除耗材类别信息")
	@ResponseBody
	@RequiresPermissions("type:delect")
	public DataRow deleteType(Long ids) {
		try {
			typeService.deleteType(ids,messageMap);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>>>>>deleteType",e);
		}
		return messageMap;
	}
	/**
	 * 查询耗材类别信息
	 * @return
	 */
	@RequestMapping(value="/querySuppliesTypeInfo")
	@InvokeLog(name = "querySuppliesTypeInfo", description = "查询耗材类别信息")
	@ResponseBody
	public DataRow querySuppliesTypeInfo() {
		EntityWrapper<Type> ew = new EntityWrapper<Type>();
		try {
			List<Type> list =typeService.selectList(ew);
			messageMap.initSuccessObject("获取成功", list);
		} catch (Exception e) {
			logger.error("TypeController>>>>>>>>>>>>>querySuppliesTypeInfo",e);
			messageMap.initSuccess("获取失败");
		}
		return messageMap;
	}
	
}
