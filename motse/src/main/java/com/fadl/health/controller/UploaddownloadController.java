package com.fadl.health.controller;


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
import com.fadl.health.service.UploaddownloadService;

/**
 * <p>
 * 上传下载表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-19
 */
@Controller
@RequestMapping("/uploaddownload")
public class UploaddownloadController extends AbstractController{

	
private static Logger logger = LoggerFactory.getLogger(UploaddownloadController.class);
	
	@Autowired
	UploaddownloadService uploaddownloadService;
	/**
     * 版本管理页面
     * @return
     */
    @RequestMapping("/versionPage")
    public String versionPage(){
    	return "/backstage/version";
    }
    /**
   	 * 查询设备数据列表
   	 */
   	@RequestMapping("/queryUploaddownloadList")
   	@ResponseBody
   	public DataRow queryUploaddownloadList(@RequestParam Map<String,Object> map){
   		try {
   		messageMap=uploaddownloadService.queryUploaddownloadList(map,messageMap);
   		} catch (Exception e) {
   			logger.error("EquipmentController>>>>>>>>>>>>>queryEquipmentList",e);
   		}
   		return messageMap;
   	}
}

