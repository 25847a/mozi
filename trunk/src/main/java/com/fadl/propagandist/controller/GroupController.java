package com.fadl.propagandist.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.propagandist.entity.Group;
import com.fadl.propagandist.service.GroupService;

/**
 * <p>
 * 组号表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-07-27
 */
@Controller
@RequestMapping("/group")
public class GroupController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	@Autowired
	GroupService groupService;
	
	/**
     * 跳转小组设置页面
     */
    @RequestMapping("/groupPage")
    public String groupPage() {
        return "/collectionConfig/group";
    }
	 /**
     * 跳转小组设置添加页面
     */
    @RequestMapping("/groupAddPage")
    public String groupAddPage() {
        return "/collectionConfig/group_add";
    }
    /**
	 * 跳转小组设置修改页面
	 * @param id
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/groupUpdate")
    public String groupUpdate(String id, Model model) {
    	try {
    		Group group = new Group();
    		group = groupService.selectById(id);
    		model.addAttribute("group",group);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>groupUpdate>>>>>",e);
		}
        return "/collectionConfig/group_update";
    }
	/**
	 * 小组设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/groupList", method= RequestMethod.POST)
	@InvokeLog(name = "groupList", description = "小组设置列表")
	@ResponseBody
	public DataRow groupList(Group group, Integer page, Integer limit) {
		Page<Group> paging = null;
		try {
			paging = new Page<Group>(page, limit);
			groupService.groupList(group,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>>>>>groupList",e);
		}
		return messageMap;
	}
	/**
	 * 新增小组设置
	 * @param group
	 * @return
	 */
	@RequestMapping(value = "/insertGroup", method= RequestMethod.POST)
	@InvokeLog(name = "insertGroup", description = "新增小组设置")
	@ResponseBody
	public DataRow insertGroup(Group group) {
		try {
			groupService.insertGroup(group,messageMap);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>>>>>insertGroup",e);
		}
		return messageMap;
	}
	/**
	 * 修改小组设置
	 * @param group
	 * @return
	 */
	@RequestMapping(value = "/updateGroup", method= RequestMethod.POST)
	@InvokeLog(name = "updateGroup", description = "修改小组设置")
	@ResponseBody
	public DataRow updateGroup(Group group) {
		try {
			groupService.updateGroup(group,messageMap);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>>>>>updateGroup",e);
		}
		return messageMap;
	}
	/**
	 * 删除小组设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteGroup", method= RequestMethod.POST)
	@InvokeLog(name = "deleteGroup", description = "删除小组设置")
	@ResponseBody
	public DataRow deleteGroup(Long ids) {
		try {
			groupService.deleteGroup(ids,messageMap);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>>>>>deleteGroup",e);
		}
		return messageMap;
	}
	/**
	 * 查询小组信息
	 * @return
	 */
	@RequestMapping(value="queryGroupInfo", method= RequestMethod.POST)
	@ResponseBody
	public DataRow queryGroupInfo() {
		try {
			List<Group> list =groupService.selectList(null);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("GroupController>>>>>>>>>>>>>queryGroupInfo",e);
		}
		return messageMap;
	}
}

