package com.fadl.common.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;

/**
 * <p>
 * 序号 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-18
 */
@Controller
@RequestMapping("/systemSeq")
public class SystemSeqController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(SystemSeqController.class);
    @Autowired
    SystemSeqService systemSeqService;

    /**
     * 获取并更新序列号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     */
    @RequestMapping(value="/queryNewSystemSeqInfo",method= RequestMethod.POST)
    @InvokeLog(name = "queryNewSystemSeqInfo", description = "获取并更新序列号")
    @ResponseBody
    public DataRow queryNewSystemSeqInfo(Integer type) {
        try {
            SystemSeq s = systemSeqService.queryNewSystemSeqInfo(type);
            messageMap.initSuccess(s);
        }catch (Exception e){
            logger.error("SystemSeqController>>>>>>>>>queryNewSystemSeqInfo>>>>>",e);
        }
        return messageMap;
    }
    /**
     * 获取新序列号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     */
    @RequestMapping(value="/querySystemSeqInfo",method= RequestMethod.POST)
    @InvokeLog(name = "querySystemSeqInfo", description = "获取新序列号")
    @ResponseBody
    public DataRow querySystemSeqInfo(Integer type) {
        try {
            SystemSeq s = systemSeqService.querySystemSeqInfo(type);
            messageMap.initSuccess(s);
        }catch (Exception e){
            logger.error("SystemSeqController>>>>>>>>>querySystemSeqInfo>>>>>",e);
        }
        return messageMap;
    }

}

