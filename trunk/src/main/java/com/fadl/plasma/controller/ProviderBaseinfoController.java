package com.fadl.plasma.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 浆员基本信息表 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-04-10
 */
@Controller
@RequestMapping("/providerBaseinfo")
public class ProviderBaseinfoController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(ProviderBaseinfoController.class);
    @Autowired
    ProviderBaseinfoService providerBaseinfoService;
    /**
     * 加载新浆员注册页面
     * @return
     */
    @RequestMapping("/insertNewPlasma")
    @RequiresPermissions("baseinfo:view")
    public String insertNewPlasma() {
        return "/business/busblood/new_registration_list";
    }

    /**
     * 加载浆员详情页面
     */
    @RequestMapping("/queryNewRegistrationDetails")
    @RequiresPermissions("baseinfo:detail")
    public String queryNewRegistrationDetails(String id,Model model) {
        model.addAttribute("id",id);
        return "/business/busblood/new_registration_details";
    }
    
    /**
     * 跳转新浆员登记页面
     * @return
     */
    @RequestMapping("/newRegistrantionPage")
    public String newRegistrantionPage(){
    	return "/business/register/new_registration";
    }

    /**
     * 加载浆员详情页面
     */
    @RequestMapping("/queryPlasmaInfoDetail")
    @ResponseBody
    @RequiresPermissions("baseinfo:detail")
    public DataRow queryPlasmaInfoDetail(String id) {
        try {
             providerBaseinfoService.queryPlasmaInfoDetail(id,messageMap);
        }catch (Exception e){
            logger.info("ProviderBaseinfoController>>>>>>>>>queryPlasmaInfoDetail>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 加载浆员信息变更页
     */
    @RequestMapping("/queryInformationhy")
    @RequiresPermissions("baseinfo:updateview")
    public String queryInformationhy() {
        return "/business/busblood/information_hy";
    }

    /**
     * 查询浆员登记采浆详细信息
     */
    @RequestMapping("/queryPlasmaInfoDetailList")
    @InvokeLog(name = "queryPlasmaInfoDetailList", description = "查询浆员登记采浆详细信息")
    @ResponseBody
    @RequiresPermissions("baseinfo:detail")
    public DataRow queryPlasmaInfoDetailList(String providerNo,Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = providerBaseinfoService.queryPlasmaInfoDetailList(dr, providerNo);
            messageMap.initPage(dr);
        }catch (Exception e){
            logger.info("ProviderBaseinfoController>>>>>>>>>queryPlasmaInfoDetailList>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 查询新浆员登记信息
     * @param startTime 工作日期
     * @param page 页码
     * @param limit 每页条数
     * @return
     */
    @RequestMapping(value="/queryRegistriesList",method= RequestMethod.POST)
    @InvokeLog(name = "queryRegistriesList", description = "查询新浆员登记信息")
    @ResponseBody
    @RequiresPermissions("baseinfo:view")
    public DataRow queryRegistriesList(String startTime, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
           dr = providerBaseinfoService.queryRegistriesList(dr, startTime);
            messageMap.initPage(dr);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>queryRegistriesList>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 查询浆员基本信息
     * @param map 工作日期
     * @param page 页码
     * @param limit 每页条数
     * @return
     */
    @RequestMapping(value="/queryProviderBaseInfoList",method= RequestMethod.POST)
    @InvokeLog(name = "queryProviderBaseInfoList", description = "查询浆员基本信息")
    @ResponseBody
    @RequiresPermissions("baseinfo:view")
    public DataRow queryProviderBaseInfoList(@RequestParam HashMap<String,Object> map, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = providerBaseinfoService.queryProviderBaseInfoList(dr, map);
            messageMap.initPage(dr);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>queryProviderBaseInfoList>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 查询浆员信息详情
     * @param providerNo 浆员卡号
     * @param startTime
     * @return
     */
    @RequestMapping(value="/queryProviderBaseInfoDetails",method= RequestMethod.POST)
    @InvokeLog(name = "queryProviderBaseInfoDetails", description = "查询浆员信息详情")
    @ResponseBody
    @RequiresPermissions("baseinfo:view")
    public DataRow queryProviderBaseInfoDetails(String providerNo,String startTime) {
        try {
            DataRow dr =providerBaseinfoService.queryProviderBaseInfoDetails(providerNo,startTime);
            messageMap.initSuccess(dr);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>queryProviderBaseInfoDetails>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 浆员信息变更
     * @param file 文件流
     * @param providerBaseinfo  浆员基本信息
     * @param detailedInfo 浆员详细信息
     * @return
     */
    @RequestMapping(value="/updateProviderBaseInfo",method= RequestMethod.POST)
    @InvokeLog(name = "updateProviderBaseInfo", description = "浆员信息变更")
    @ResponseBody
    @RequiresPermissions("baseinfo:update")
    public DataRow updateProviderBaseInfo(MultipartFile[] file,@RequestParam HashMap<String, String> map) {
        try {
            providerBaseinfoService.updateProviderBaseInfo(file,map,messageMap);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>updateProviderBaseInfo>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 浆员基本信息审核
     * @param ids 审核的浆员id集合
     * @return
     */
    @RequestMapping(value="/updateBaseInfoExamine",method= RequestMethod.POST)
    @InvokeLog(name = "updateBaseInfoExamine", description = "浆员基本信息审核")
    @ResponseBody
    public DataRow updateBaseInfoExamine(@RequestParam List<Long> ids,Integer examineStatus) {
        try {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	map.put("ids", ids);
        	map.put("examineStatus", examineStatus);
            providerBaseinfoService.updateBaseInfoExamine(map,messageMap);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>updateBaseInfoExamine>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 新增浆员信息
     * @param file 文件流
     * @param providerBaseinfo  浆员基本信息
     * @param detailedInfo 浆员详细信息
     * @param validateType 是否验证 0人脸识别
     * @return
     */
    @RequestMapping(value="/insertBaseInfo",method= RequestMethod.POST)
    @InvokeLog(name = "insertBaseInfo", description = "新增浆员信息")
    @ResponseBody
    @RequiresPermissions("baseinfo:add")
    public DataRow insertBaseInfo(MultipartFile[] file, ProviderBaseinfo providerBaseinfo, DetailedInfo detailedInfo, Integer validateType, Integer roadFeeType, BigDecimal roadFee,String group){
        try {
            providerBaseinfoService.insertBaseInfo(file,providerBaseinfo,detailedInfo,validateType,roadFeeType,roadFee,group,messageMap);
        }catch (Exception e){
            logger.error("ProviderBaseinfoController>>>>>>>>>insertBaseInfo>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 验证浆员信息是否合规合法
     * @return
     */
    @RequestMapping(value="/queryPlasmaLegal",method= RequestMethod.POST)
    @InvokeLog(name = "queryPlasmaLegal", description = "验证浆员信息是否合规合法")
    @ResponseBody
    public DataRow queryPlasmaLegal(String birthday,String address,String idNo,String validDate){
        try {
            providerBaseinfoService.queryPlasmaLegal(birthday,address,idNo,validDate,messageMap);
        }catch (Exception e){
            logger.error("ProviderBaseinfoController>>>>>>>>>insertBaseInfo>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 删除 浆员信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBaseInfo")
    @InvokeLog(name = "deleteBaseInfo", description = "删除 浆员信息")
    @ResponseBody
    @RequiresPermissions("baseinfo:del")
    public DataRow deleteBaseInfo(String ids){
        try {
            providerBaseinfoService.deleteBaseInfo(ids,messageMap);
        }catch (Exception e){
            logger.error("ProviderBaseinfoController>>>>>>>>>>>>>>deleteRegistration>>>>>>>>>>>",e);
        }
        return messageMap;
    }
    
    /**
     * 加载浆员高级查询
     */
    @RequestMapping("/plasmaList")
    public String plasmaList() {
        return "/business/register/query_plasma_list";
    }
    
    /**
     * 献浆员高级查询
     * @return
     */
    @RequestMapping(value = "/queryPlasmaDetailList")
    @InvokeLog(name = "queryPlasmaDetailList", description = "献浆员高级查询")
    @ResponseBody
    @RequiresPermissions("baseinfo:query")
    public DataRow queryPlasmaDetailList(@RequestParam HashMap<String,String> map, Integer page, Integer limit){
    	try {
    		Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = providerBaseinfoService.queryPlasmaDetailList(dr, map);
            messageMap.initPage(dr);
		} catch (Exception e) {
			logger.error("ProviderBaseinfoController>>>>>>>>>>>>>>queryPlasmaDetailList>>>>>>>>>>>",e);
		}
    	return messageMap;
    }
    
    /**
     * 基本信息审核
     * @return
     */
    @RequestMapping("/plasmaExamine")
    @RequiresPermissions("baseinfo:examine")
    public String plasmaExamine(){
    	return "/business/register/plasma_examine";
    }
    
    /**
     * 获取基本信息审核列表
     * @return
     */
    @RequestMapping(value = "/queryPlasmaExamineList")
    @InvokeLog(name = "queryPlasmaExamineList", description = "获取基本信息审核列表")
    @ResponseBody
    @RequiresPermissions("baseinfo:examine")
    public DataRow queryPlasmaExamineList(@RequestParam HashMap<String,String> map, Integer page, Integer limit){
    	try {
    		Page<DataRow> dr = new Page<DataRow>(page, limit);
    		dr = providerBaseinfoService.queryPlasmaExamineList(dr, map);
    		messageMap.initPage(dr);
		} catch (Exception e) {
			logger.error("ProviderBaseinfoController>>>>>>>>>>>>>>queryPlasmaExamineList>>>>>>>>>>>",e);
		}
    	return messageMap;
    }
    
    /**
     * 卫计委审核通过后，更新浆员信息
     * @return
     */
    @RequestMapping(value = "/updatePlasmaInfo")
    @InvokeLog(name = "updatePlasmaInfo", description = "卫计委审核通过后，更新浆员信息")
    @ResponseBody
    public DataRow updatePlasmaInfo(@RequestParam String providerNo){
    	try {
    		providerBaseinfoService.updatePlasmaInfo(providerNo, messageMap);
		} catch (Exception e) {
			logger.error("ProviderBaseinfoController>>>>>>>>>>>>>>updatePlasmaInfo>>>>>>>>>>>",e);
		}
    	return messageMap;
    }
    
    /**
     * 查询新浆员登记信息
     * @param startTime 工作日期
     * @param page 页码
     * @param limit 每页条数
     * @return
     */
    @RequestMapping(value="/queryNewRegistriesList",method= RequestMethod.POST)
    @InvokeLog(name = "queryNewRegistriesList", description = "查询新浆员登记信息")
    @ResponseBody
    public DataRow queryNewRegistriesList(String startTime, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
           dr = providerBaseinfoService.queryNewRegistriesList(dr, startTime);
            messageMap.initPage(dr);
        } catch (Exception e) {
            logger.error("ProviderBaseinfoController>>>>>>>>>queryNewRegistriesList>>>>>",e);
        }
        return messageMap;
    }
}
