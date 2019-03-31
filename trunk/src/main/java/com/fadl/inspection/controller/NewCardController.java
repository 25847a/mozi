package com.fadl.inspection.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.service.NewCardService;
import com.fadl.inspection.service.TestConfigInfoService;

/**
 * <p>
 * 检验-新-老卡化验 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/newCard")
public class NewCardController extends AbstractController{
	private static Logger logger = LoggerFactory.getLogger(NewCardController.class);
	@Autowired
	private NewCardService newCardService;

	/**
	 * 跳转新卡化验页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("newcard:view")
	public String initPage() {
		return "/business/inspection/in_new_pulping";
	}
	

	
	/**
	 * 跳转老卡化验页面
	 * 
	 * @return
	 */
	@RequestMapping("/initOldPage")
	@RequiresPermissions("newcard:view1")
	public String initOldPage() {
		return "/business/inspection/in_pulps";
	}
	/**
	 * 根据创建时间查询化验记录
	 * @param newCard
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryListsByCreateDate", method=RequestMethod.POST)
	@InvokeLog(name = "queryListsByCreateDate", description = "根据创建时间查询化验记录")
	@RequiresPermissions("newcard:view")
	public DataRow queryListsByCreateDate(NewCard newCard, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page,limit);
			if(StringUtils.isEmpty(newCard.getEndTime())) {
				newCard.setEndTime(newCard.getStartTime());
			}
			// 为了性能 区分查询
			if(newCard.getIsAssay()==null ) {
				dr = newCardService.queryListByCreateDateAndIsAssayWith1(dr, newCard);
			} else if( newCard.getIsAssay()==0) {
				dr = newCardService.queryListByCreateDateAndIsAssay(dr, newCard);
			}else {
				dr = newCardService.queryListByCreateDateAndIsAssayWith1(dr, newCard);
			}
			messageMap.initPage(dr);
		} catch (Exception e) {
			logger.error("NewCardController>>>>>>>>>queryListsByCreateDate>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 根据条件查询化验记录 
	 * 目前支持小样号段,创建时间段查询
	 * @param newCard
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryListsByCondition", method=RequestMethod.POST)
	@InvokeLog(name = "queryListsByCondition", description = "根据条件查询化验记录 ")
	@RequiresPermissions("newcard:view")
	public DataRow queryListsByCondition(NewCard newCard, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page,limit);
			messageMap.initPage(newCardService.queryListsByCondition(dr, newCard));
			
			
			
			
		} catch (Exception e) {
			logger.error("NewCardController>>>>>>>>>queryListsByCondition>>>>>", e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 根据ID查找新老卡化验结果
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectById", method=RequestMethod.POST)
	@InvokeLog(name = "selectById", description = "根据ID查找新老卡化验结果")
	@RequiresPermissions("newcard:view")
	public DataRow selectById(Long id) {
		try {
			NewCard card = new NewCard();
			card.setId(id);
			messageMap.initSuccess(newCardService.selectInfoById(card));
		}catch (Exception e) {
			logger.error("NewCardController>>>>>>>>>selectById>>>>>", e);
			messageMap.initFial();
		}
		
		return messageMap;
	}
	
	/**
	 * 根据ID更新新老卡化验结果
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateById", method=RequestMethod.POST)
	@InvokeLog(name = "updateById", description = "根据ID更新新老卡化验结果")
	@RequiresPermissions("newcard:edit")
	public DataRow updateById(NewCard card) {
		try {
			newCardService.updateInfoById(card,messageMap);
			messageMap.initSuccess();
		}catch (Exception e) {
			logger.error("NewCardController>>>>>>>>>updateById>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
}

