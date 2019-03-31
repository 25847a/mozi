package com.fadl.upload.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.check.service.CheckService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.StringHelper;

/**
 * 传叫号数据到叫号系统
 * @author hu
 *
 */
@RequestMapping("/upload")
@Controller
public class CallController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(CallController.class);
	@Autowired
	public CheckService checkService;
	
	
	/**
	 * 同步体检数据到叫号系统 
	 * @return
	 */
	@RequestMapping(value="/queryCallDataList")
    @ResponseBody
	public DataRow queryCallDataList(@RequestParam HashMap<String, String> map){
		try {
			if (!StringHelper.isEmpty(map.get("type"))) {
				List<DataRow> list = checkService.queryCallDataList(map);
				messageMap.initSuccess(list);
			}else{
				messageMap.initFial("参数错误");
			}
		} catch (Exception e) {
			logger.error("CallController>>>>>>>>>queryCallDataList>>>>>",e);
		}
		return messageMap;
	}
}
