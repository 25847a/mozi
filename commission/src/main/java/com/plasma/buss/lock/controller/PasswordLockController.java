package com.plasma.buss.lock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plasma.buss.lock.service.PasswordLockService;
import com.plasma.common.CommonUtil;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.ReadProperties;
import com.plasma.common.base.BaseController;
import com.plasma.common.util.Validator;

/**
 * @author:wangjing
 * @Description:电子加密狗
 * @Date:2018-12-13
 */
@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class PasswordLockController extends BaseController {
    @Autowired
    public PasswordLockService passwordLockService;
    /**
     * 跳转加密狗列表页面
     * @return
     */
    @RequestMapping("/lockList")
    public String lockList(){
        return "lock/lock_list";
    }

    /**
     * 查询浆站列表
     * @return
     */
    @RequestMapping("/queryPasswordLockList")
    @ResponseBody
    public DataRow queryPasswordLockList(){
        try {
            pageBean = passwordLockService.queryPasswrodLockList(info, pageBean);
            DataRow dr = new DataRow();
            dr.put("pageNum", pageBean.pageNum);
            dr.put("pageSize", pageBean.getPageSize());
            dr.put("listData", pageBean.getPage());
            dr.put("totalNum", pageBean.getTotalNum());
            super.fillResultContext(messageMap, dr);
        } catch (Exception e) {
            messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
            log.error("PasswordLockController>>>>>>>queryPasswordLockList",e);
        }
        return messageMap;
    }
    /**
     * 登记加密狗
     * @return
     */
    @RequestMapping("/insertPasswrodLock")
    @ResponseBody
    public DataRow insertPasswrodLock(){
        try {
            Validator val = new Validator(info,messageMap);
            if (val.validate()) {
                String ip= CommonUtil.getIpAddress(request);
                info.put("ip", ip);
                info.put("port", ReadProperties.getValue("port"));
                info.put("creater", getSessionUserId());
                    int res = passwordLockService.insertPasswrodLock(info,messageMap);
                    if(res>0){
                        messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
                        messageMap.put(IConstants.APP_RESULT_MSG,"添加成功");
                    }
                }
        } catch (Exception e) {
            messageMap.put(IConstants.APP_RESULT_MSG, IConstants.SYSTEM_ERROR);
            log.error("PasswordLockController>>>>>>>insertPasswrodLock",e);
        }
        return messageMap;
    }
}
