package com.fadl.cost.controller;

import java.util.HashMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.cost.service.LevelConfigService;

/**
 * 邀请绑定  前端控制器
 * 
 * */
@Controller
@RequestMapping("/bindMessage")
public class InviteBindController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	private LevelConfigService configService;
	
	/**邀请绑定--->绑定信息列表页面*/
	@RequestMapping("/toInviteBind")
	@RequiresPermissions("bindMessage:view")
	public String toInviteBind() {
		return "/cost/invite_bind_list";
	}
	/**
	 * 邀请绑定--->绑定信息列表
	 * */
	@RequestMapping("/queryBindMsgList")
	@InvokeLog(name = "queryBindMsgList", description = "邀请绑定列表")
	@ResponseBody
	@RequiresPermissions("bindMessage:view")
	public DataRow queryBindMsgList(Integer page,Integer limit,@RequestParam HashMap<String, String> map) {
		try {
			Page<DataRow> page2 = new Page<>(page,limit);
			configService.queryBindMsgList(map, page2);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("InviteBindController>>>>>queryBindMsgList>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理---邀请绑定--新增绑定页面
	 * */
	@RequestMapping("/toBindAdd")
	@RequiresPermissions("bindMessage:add")
	public String toBindAdd() {
		return "/cost/invite_bind_add";
	}
	
	/**
	 * 费用管理---邀请绑定--新增绑定
	 * */
	@RequestMapping("/bindAdd")
	@InvokeLog(name = "bindAdd", description = "新增邀请绑定")
	@ResponseBody
	@RequiresPermissions("bindMessage:add")
	public DataRow addBindFit(@RequestParam(name="inviterType") Long inviterType,
			 				  @RequestParam(name="inviter")String inviter,
			 				  @RequestParam(name="id")Long id) {
		try {
			if(configService.addBind(inviterType, inviter, id,messageMap)) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("InviteBindController>>>>>addBindFit>>>>", e);
		}
		return messageMap;
	}
	
	/**费用管理---邀请绑定--解绑*/
	@RequestMapping("/unbind")
	@InvokeLog(name = "unbind", description = "邀请绑定解绑")
	@ResponseBody
	@RequiresPermissions("bindMessage:update")
	public DataRow unbind(Long id) {
		try {
			int count = configService.unbind(id);
			if(count > 0) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("InviteBindController>>>>>unbind>>>>", e);
		}
		return messageMap;
	}
}
