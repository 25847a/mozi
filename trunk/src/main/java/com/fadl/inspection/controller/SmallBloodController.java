package com.fadl.inspection.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.inspection.entity.SmallBlood;
import com.fadl.inspection.service.SmallBloodService;

/**
 * <p>
 * 检验-采小血 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/smallBlood")
public class SmallBloodController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(SpecimenCollectionController.class);

	@Autowired
	private SmallBloodService smallBloodService;
	@Autowired
	private PlasmaImageService piService; 

	/**
	 * 跳转标本采集页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("smallbc:view")
	public String initPage() {
		return "/business/inspection/in_deconfxue";
	}

	/**
	 * 根据ID查找固定浆员采样记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryByID", method = RequestMethod.POST)
	@InvokeLog(name = "queryByID", description = "根据ID查找固定浆员采样记录")
	@ResponseBody
	@RequiresPermissions("smallbc:view")
	public DataRow queryByID(@NotEmpty Long id) {
		try {
			messageMap.initSuccess(smallBloodService.querySmallBloodByEntity(id));
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>queryByID>>>>>", e);
		}
		return messageMap;
	}

	/**
	 * 根据日期查询采小血的记录 带分页
	 * 
	 * @param blood
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryListByCreateDate", method = RequestMethod.POST)
	@InvokeLog(name = "queryListByCreateDate", description = "根据日期查询采小血的记录 带分页")
	@ResponseBody
	@RequiresPermissions("smallbc:view")
	public DataRow queryListByCreateDate(SmallBlood blood, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page, limit);
			Page<DataRow> page2 = smallBloodService.queryListByCreateDateAndIsCollection(dr, blood);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>queryListByCreateDate>>>>>", e);
		}
		return messageMap;

	}
	
    
    /**
     * 获取送样记录,根据更新时间
     * @param sendDate
     * @return
     */
    @RequestMapping(value="/getSendInfoBySendDate" , method=RequestMethod.POST)
    @ResponseBody
    public DataRow getSendInfoBySendDate(String sendDate) {
    	try {
			List<DataRow> ds = smallBloodService.querySendInfosByUpdateDate(sendDate);
			messageMap.initSuccess(ds);
		} catch (Exception e) {
			logger.error("SpecimenCollectionController>>>>>>>>printSendInfo>>>>>", e);
		}
    	return messageMap;
    }
	/**
	 * 根据日期,浆员卡号查询查询采小血的id,采浆次数(啊健)
	 * @param blood
	 * @return
	 */
	@RequestMapping(value = "/querySmallBloodHeadInfo", method = RequestMethod.POST)
	@InvokeLog(name = "querySmallBloodHeadInfo", description = "根据日期，浆员卡号查询查询采小血的id,采浆次数")
	@ResponseBody
	public DataRow querySmallBloodHeadInfo(SmallBlood blood) {
		try {
			smallBloodService.querySmallBloodHeadInfo(blood,messageMap);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>querySmallBloodHeadInfo>>>>>", e);
		}
		return messageMap;

	}

	/**
	 * 根据ID更新是否采小样
	 * 
	 * @param blood
	 * @return
	 */
	@RequestMapping(value = "/updateWithCollection", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithCollection", description = "根据ID更新是否采小样")
	@ResponseBody
	@RequiresPermissions("smallbc:edit")
	public DataRow updateWithCollection(SmallBlood blood,String image) {
		try {
    		smallBloodService.updateWithCollection(blood,messageMap);
    		if(!StringUtils.isEmpty(image)) {
				piService.saveImage(image, 5, blood.getId(), messageMap);
			}
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>updateWithCollection>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	/**
	 * 批量送样
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/updateWithSendOff", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithSendOff", description = "批量送样")
	@ResponseBody
	@RequiresPermissions("smallbc:edit")
	public DataRow updateWithSendOff(@RequestParam List<Long> ids, String sendPerson) {
		try {
    		List<SmallBlood> sbs  = smallBloodService.selectBatchIds(ids);
    		for(SmallBlood sb : sbs) {
    			sb.setIsSendOff(1);
    			sb.setSendPerson(sendPerson);
    		}
    		smallBloodService.updateBatchEntityByIds(sbs, messageMap);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>updateWithSendOff>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	/**
	 * 取消采样
	 * @return
	 */
	@RequestMapping(value = "/cacalSmallBlood", method = RequestMethod.POST)
	@InvokeLog(name = "cacalSmallBlood", description = "取消采样")
	@ResponseBody
	@RequiresPermissions("smallbc:cacal")
	public DataRow cacalSmallBlood(SmallBlood blood){
		try {
			smallBloodService.cacalSmallBlood(blood, messageMap);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>cacalSmallBlood>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
	/**
	 * 读卡判断是否存在此浆员
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/querySmallBloodDu", method = RequestMethod.POST)
	@ResponseBody
	public DataRow querySmallBloodDu(SmallBlood smallBlood) {
		try {
			EntityWrapper<SmallBlood> ew = new EntityWrapper<SmallBlood>();
			ew.eq("providerNo", smallBlood.getProviderNo());
			ew.eq("DATE_FORMAT(updateDate,'%Y-%m-%d')", smallBlood.getUpdateDate());
			SmallBlood s =smallBloodService.selectOne(ew);
			messageMap.initSuccess(s);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>querySmallBloodDu>>>>>", e);
		}
		return messageMap;
	}
}
