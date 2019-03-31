package com.fadl.quarantine.controller;


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
import com.fadl.common.CommonUtil;
import com.fadl.common.DataRow;
import com.fadl.common.SocketUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.service.ConfigService;
import com.fadl.quarantine.service.QuarantineService;

/**
 * <p>
 * 检疫期回访登记 前端控制器
 * </p>
 *
 * @author wj
 * @since 2018-11-11
 */
@Controller
@RequestMapping("/quarantine")
public class QuarantineController extends AbstractController {
    private static Logger logger = LoggerFactory.getLogger(QuarantineController.class);
    @Autowired
    QuarantineService quarantineService;
    @Autowired
    SocketUtil socketUtil;
    @Autowired
    ConfigService configService;
    /**
     * 跳转检疫期名单列表页面
     * @return
     */
    @RequestMapping("/quarantinePage")
    @RequiresPermissions("quarantine:view")
    public String newRegistrantionPage(){
        return "/business/quarantine/quarantine_list";
    }


    /**
     * 查询检疫期名单列表信息
     */
    @RequestMapping("/queryQuarantine")
    @InvokeLog(name = "queryQuarantine", description = "查询检疫期名单列表信息")
    @ResponseBody
    @RequiresPermissions("quarantine:view")
    public DataRow queryQuarantine(@RequestParam HashMap<String,String> data, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = quarantineService.queryQuarantine(dr,data);
            messageMap.initPage(dr);
        }catch (Exception e){
            logger.info("QuarantineController>>>>>>>>>queryQuarantine>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 检疫期回访登记
     */
    @RequestMapping("/quarantineAdd")
    @InvokeLog(name = "quarantineAdd", description = "检疫期回访登记")
    @ResponseBody
    @RequiresPermissions("quarantine:add")
    public DataRow quarantineAdd(@RequestParam HashMap<String,String> data) {
        try {
            String ip = CommonUtil.getIpAddress(request);
            quarantineService.quarantineAdd(data,ip,messageMap);
            return messageMap;
        }catch (Exception e){
            logger.info("QuarantineController>>>>>>>>>quarantineAdd>>>>>",e);
        }
        return messageMap;
    }

    /**
     * 跳转检疫期名单列表页面
     * @return
     */
    @RequestMapping("/quarantineRegPage")
    @RequiresPermissions("quarantineReg:view")
    public String quarantineRegPage(){
        return "/business/quarantine/quarantineadd_list";
    }

    /**
     * 查询检疫期登记列表信息
     */
    @RequestMapping("/queryQuarantineReg")
    @InvokeLog(name = "queryQuarantineReg", description = "查询检疫期登记列表信息")
    @RequiresPermissions("quarantineReg:view")
    @ResponseBody
    public DataRow queryQuarantineReg(@RequestParam HashMap<String,String> data, Integer page, Integer limit) {
        try {
            Page<DataRow> dr = new Page<DataRow>(page, limit);
            dr = quarantineService.queryQuarantineReg(dr,data);
            messageMap.initPage(dr);
        }catch (Exception e){
            logger.info("QuarantineController>>>>>>>>>queryQuarantineReg>>>>>",e);
        }
        return messageMap;
    }
}

