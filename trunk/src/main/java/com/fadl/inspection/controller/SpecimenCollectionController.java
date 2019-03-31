package com.fadl.inspection.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.inspection.entity.SpecimenCollection;
import com.fadl.inspection.service.SpecimenCollectionService;

/**
 * <p>
 * 检验-标本采集 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@Controller
@RequestMapping("/specimenCollection")
public class SpecimenCollectionController  extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(SpecimenCollectionController.class);
	
	@Autowired
	private SpecimenCollectionService specimenCollectionService ;
	@Autowired
	private PlasmaImageService piService; 
	
	/**
     * 跳转标本采集页面
     * @return
     */
    @RequestMapping("/initPage")
    @RequiresPermissions("specimenc:view")
    public String initPage() {
        return "/business/inspection/in_specimen";
    }
    
	/**
     * 跳转送样记录打印页面
     * @return
     */
    @RequestMapping("/printSendInfo")
    //TODO  @RequiresPermissions("specimenc:print")
    public String printSendInfo(String type,String sendDate, Model model) {
    	model.addAttribute("sendDate",sendDate);
    	model.addAttribute("type",type);
        return "/business/inspection/aayin";
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
			List<DataRow> ds = specimenCollectionService.querySendInfosByUpdateDate(sendDate);
			messageMap.initSuccess(ds);
		} catch (Exception e) {
			logger.error("SpecimenCollectionController>>>>>>>>printSendInfo>>>>>", e);
		}
    	return messageMap;
    }
    
    
    /**
     * 根据ID获取抽样信息
     * @param id
     * @return
     */
    @RequestMapping(value="/querySpecimenById" , method=RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("specimenc:view")
    public DataRow  querySpecimenById(@NotEmpty Long id) {
    	try {
    		messageMap.initSuccess(specimenCollectionService.querySpecimenCollectionByEntity(id));
    		
    	}catch (Exception e) {
    		logger.error("SpecimenCollectionController>>>>>>>>querySpecimenById>>>>>", e);
			messageMap.initFial();
    	}
    	return messageMap;
    }
    
    /**
     * 根据创建时间和是否抽样查询列表集合
     * @param specimenCollection
     * @return
     */
    @RequestMapping(value="/queryListByCreateDate" , method=RequestMethod.POST)
    @InvokeLog(name = "queryListByCreateDate", description = "根据创建时间和是否抽样查询列表集合")
    @ResponseBody
    @RequiresPermissions("specimenc:view")
    public DataRow  queryListByCreateDate(SpecimenCollection specimenCollection, Integer page, Integer limit) {
    	
    
    	try {
            String startTime = specimenCollection.getStartTime();
            startTime= (StringUtils.isEmpty(startTime)?DateUtil.sfDay.format(new Date()):startTime);
        	Page<DataRow> dr = new Page<DataRow>(page,limit);
        	specimenCollection.setStartTime(startTime);
        	Page<DataRow> page2 = specimenCollectionService.queryListByCreateDateAndIsCollection(dr, specimenCollection);
            messageMap.initPage(page2);
    	}catch (Exception e) {
    		logger.error("SpecimenCollectionController>>>>>>>>queryListByCreateDate>>>>>", e);
		}
    	return messageMap;
    }
    
    /**
     * 根据ID更新是否抽样
     * @param specimenCollection
     * @return
     */
    @RequestMapping(value="/updateWithCollection" , method=RequestMethod.POST)
    @InvokeLog(name = "updateWithCollection", description = "根据ID更新是否抽样")
    @ResponseBody
    @RequiresPermissions("specimenc:edit")
    public DataRow updateWithCollection(SpecimenCollection specimenCollection, String image) {
    	try {
    		SpecimenCollection collection = specimenCollectionService.selectById(specimenCollection.getId());
    		collection.setIsCollection(specimenCollection.getIsCollection());
    		collection.setIsSendOff(0);
    		collection.setSendPerson(null);
    		if(specimenCollectionService.updateEntityById(collection,messageMap)) {
    			if(!StringUtils.isEmpty(image)) {
    				piService.saveImage(image, 2, collection.getId(), messageMap);
    			}
    			messageMap.initSuccess();
    		}
    	}catch (Exception e) {
    		logger.error("SpecimenCollectionController>>>>>>>>updateWithCollection>>>>>", e);
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
	@RequiresPermissions("specimenc:edit")
	public DataRow updateWithSendOff(@RequestParam List<Long> ids, String sendPerson) {
		try {
    		List<SpecimenCollection> sbs  = specimenCollectionService.selectBatchIds(ids);
    		for(SpecimenCollection sb : sbs) {
    			if(1==sb.getIsSendOff()) {
    				messageMap.initFial("浆员卡号:"+sb.getAllId()+"已经送样,无须再次送样!");
    				return messageMap;
    			}
    			sb.setIsSendOff(1);
    			sb.setSendPerson(sendPerson);
    		}
    		specimenCollectionService.updateBatchEntityByIds(sbs, messageMap);
		} catch (Exception e) {
			logger.error("SmallBloodController>>>>>>>>updateWithSendOff>>>>>", e);
			messageMap.initFial(e.getMessage());
		}
		return messageMap;
	}
     
    /**
	 * 根据小样号前面日期得到所有被使用的小样号集合
	 * @param dateStr
	 * @return
	 */
    @RequestMapping(value="/selectAllSampleNoByDateStr" , method=RequestMethod.POST)
    @InvokeLog(name = "selectAllSampleNoByDateStr", description = "根据小样号前面日期得到所有被使用的小样号集合")
    @ResponseBody
    @RequiresPermissions("specimenc:edit")
	public DataRow selectAllSampleNoByDateStr(String dateStr){
    	messageMap.initSuccess(new ArrayList<String>());
    	try {
			String daStr = DateUtil.formatDate(DateUtil.formatDate(dateStr,DateUtil.yyyy_MM_dd_EN), DateUtil.yyyyMMdd);
			// 得到已使用的小样号
			List<String> sn = specimenCollectionService.selectAllSampleNoByDateStr(daStr);
			if(sn.size()<=0) {
				messageMap.initSuccess(new ArrayList<String>());
				return  messageMap;
			}
				
			// 得到最大小样号
			String maxSN =  sn.get(sn.size()-1);
			// 如果长度等于 则表明有正确小样号 , 可以进行小样号截取,取到当前游标记录的小样号
			if(maxSN.length() == 12) {
				// 截取最后4位
				String nostr = maxSN.substring(8, 12);
				// 转换为int
				int noint = Integer.parseInt(nostr);
				if(noint!=sn.size()) { // 如果数字和集合长度不一致则有断档
					// 初始化一个集合来保存所有的小样号段
					List<String> allSmList = new ArrayList<String>();
					for(int i=1; i<=noint;i++) {
						String tempStr = daStr+String.format("%04d", i);
						if(!sn.contains(tempStr)) {
							allSmList.add(tempStr);
						}
					}
					messageMap.initSuccess(allSmList);
				}
			}
			
		} catch (Exception e) {
			logger.error("SpecimenCollectionController>>>>>>>>selectAllSampleNoByDateStr>>>>>", e);
    		messageMap.initFial(e.getMessage());
		}
    	return messageMap;
	}
}

