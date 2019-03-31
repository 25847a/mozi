package com.fadl.card.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.plasma.service.ProviderBaseinfoTempService;

/**
 * 写卡前端控制器
 * @author Administrator
 *
 */
@RequestMapping("/card")
@Controller
public class CardController  extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(CardController.class);
	
	@Autowired
	private ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public ProviderBaseinfoTempService providerBaseinfoTempService;
	
	/**
	 * 发卡页面
	 * @return
	 */
	@RequestMapping("/toCardList")
	public String toCardList() {
		return "/card/grant_card";
	}
	
	/**
	 * 发卡页面显示（分页）（可以传参）
	 * @throws Exception
	 *  */
	@RequestMapping("cardList")
	@InvokeLog(name = "cardList", description = "发卡页面显示")
	@ResponseBody
	public DataRow cardList(String createDate,Integer page,Integer limit) {
		Page<DataRow> paging = new Page<>(page,limit);
		try {
			providerBaseinfoService.queryCard(createDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			messageMap.initFial();
			logger.error("CardController>>>>>>>cardList>>>>>",e);
		}
		
		return messageMap;
	}
	
	/**
	 * 发卡页面根据id查询相关信息
	 * @param id
	 * @param model
	 * @return
	 */
	public DataRow queryCardByid(Long id,Model model) {
		ProviderBaseinfo info = providerBaseinfoService.selectById(id);
		if(info != null) {
			model.addAttribute("card",info);
			messageMap.initSuccess();
		}else {
			logger.error("CardController>>>>>>>queryCardByid>>>>>");
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 发送数据到卫计委，并更新浆员卡号
	 * @param providerNo
	 * @param bloodType
	 * @return
	 */
	@RequestMapping("/changeProviderNo")
	@InvokeLog(name = "changeProviderNo", description = "发送数据到卫计委，并更新浆员卡号")
	@ResponseBody
	public DataRow changeProviderNo(@RequestParam Map<String, String> map) {
		//给检验合格的浆员将临时卡号改为浆员卡号
		try {
			providerBaseinfoService.changeProviderNo(map,messageMap);
		} catch (java.lang.Exception e) {
			logger.error("CardController>>>>>>>changeProviderNo>>>>>",e);
			if (StringHelper.isEmpty(messageMap.getString(IConstants.RESULT_MESSAGE))) {
				messageMap.initFial();
			}
		}
		return messageMap;
	}
	
	
	/** 
	 * 发卡，即给浆员绑定卡号
	 * @throws Exception 
	 * */
	@RequestMapping("/grantCard")
	@InvokeLog(name = "grantCard", description = "发卡，即给浆员绑定卡号")
	@ResponseBody
	public DataRow grantCard(@RequestParam HashMap<String, String> map) {
		try {
			providerBaseinfoService.grantCard(map,messageMap);
		} catch (java.lang.Exception e) {
			logger.error("CardController>>>>>>>grantCard>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 今日已发卡浆员信息
	 * @param createDate
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/haveGrantCard")
	@InvokeLog(name = "haveGrantCard", description = "今日已发卡浆员信息")
	@ResponseBody
	public DataRow haveGrantCard(@RequestParam Map<String, String> map,Integer page,Integer limit) {
		Page<DataRow> paging = new Page<>(page,limit);
		try {
			providerBaseinfoService.haveGrantCard(map, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("CardController>>>>>>>cardList>>>>>",e);
		}
		
		return messageMap;
	}
	
	/**
	 * 取消发卡
	 * @param id
	 * @return
	 */
	@RequestMapping("/cancelCard")
	public String cancelCard(String id) {
		return "/card/cancel_card";
	}
	
	/**
	 * 写卡页面
	 * @return
	 */
	@RequestMapping("/toWriteCard")
	public String toWriteCard() {
		return "/card/write_card";
	}
	
	/**
	 * 写卡(可以根据idNo或者providerNo进行查询)
	 * @param page
	 * @param limit
	 * @param idNo
	 * @param providerNo
	 * @return
	 */
	@RequestMapping("/queryPunchCard")
	@InvokeLog(name = "queryPunchCard", description = "写卡")
	@ResponseBody
	public DataRow queryPunchCard(Integer page,Integer limit,@RequestParam(name="idNo") String idNo,@RequestParam(name="providerNo") String providerNo) {
		Map<String, String> map = new HashMap<>();
		map.put("providerNo", providerNo);
		map.put("idNo", idNo);
		Page<DataRow> paging = new Page<>();
		try {
			providerBaseinfoService.queryPunchCard(map, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			messageMap.initFial();
			logger.error("CardController>>>>>>>queryPunchCard>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 发送取消发卡请求到卫计委
	 * @return
	 */
	@RequestMapping("/sendCancelCard")
	@InvokeLog(name = "sendCancelCard", description = "发送取消发卡请求到卫计委")
	@ResponseBody
	public DataRow sendCancelCard(@RequestParam HashMap<String, String> map){
		try {
			providerBaseinfoService.sendCancelCard(map, messageMap);
		} catch (Exception e) {
			messageMap.initFial();
			logger.error("CardController>>>>>>>sendCancelCard>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 取消发卡
	 * @return
	 */
	@RequestMapping("/cancelPlassmaCard")
	@InvokeLog(name = "cancelPlassmaCard", description = "取消发卡")
	@ResponseBody
	public DataRow cancelPlassmaCard(@RequestParam String providerNo){
		try {
			providerBaseinfoService.cancelPlassmaCard(providerNo,messageMap);
		} catch (Exception e) {
			messageMap.initFial();
			logger.error("CardController>>>>>>>cancelPlassmaCard>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 浆员信息修改后，提交卫计委系统审核
	 * @return
	 */
	@RequestMapping("/submitPlasmaUpdate")
	@InvokeLog(name = "submitPlasmaUpdate", description = "浆员信息修改后，提交卫计委系统审核")
	@ResponseBody
	public DataRow submitPlasmaUpdate(@RequestParam Map<String, String> map){
		try {
			providerBaseinfoService.submitPlasmaUpdate(map, messageMap);
		} catch (Exception e) {
			if (StringHelper.isEmpty(messageMap.getString("message"))) {
				messageMap.initFial();
			}
			logger.error("CardController>>>>>>>submitPlasmaUpdate>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 申请取消发卡页面
	 * @return
	 */
	@RequestMapping("/applyCancelCard")
	public String applyCancelCard(){
		return "/card/apply_cancel_card";
	}
}
