package com.fadl.hr.controller;


import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.hr.entity.Hr;
import com.fadl.hr.service.HrService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-10
 */
@Controller
@RequestMapping("/hr")
public class HrController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HrController.class);
	
	@Autowired
	HrService hrService;
	 /**
     * 查询机构管理页面下工作人员的信息列表
     * map
     * @return
     */
    @RequestMapping("/queryHrList")
    @ResponseBody
    public DataRow queryHrList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap =  hrService.queryHrList(map,messageMap);
		} catch (Exception e) {
			logger.error("HrController<<<<<<<<<<<<<<<<<<queryHrList",e);
		}
		return messageMap;
    }
    /**
     * 删除供应商的个人信息
     * map
     * @return
     */
    @RequestMapping("/deleteHrInfo")
    @ResponseBody
    public DataRow deleteHrInfo(Hr hr){
    	try {
    		messageMap =  hrService.deleteHrInfo(hr,messageMap);
		} catch (Exception e) {
			logger.error("HrController<<<<<<<<<<<<<<<<<<queryHrListCount",e);
		}
		return messageMap;
    }
}

