package com.fadl.image.controller;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.image.service.PlasmaImageService;

/**
 * <p>
 * 图片存储表 前端控制器
 * </p>
 *
 * @author hu
 * @since 2018-05-09
 */
@RestController
@RequestMapping("/plasmaImage")
public class PlasmaImageController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PlasmaImageController.class);
	
	@Autowired
	public PlasmaImageService plasmaImageService;
	
	/**
	 * 根据 imgId 和 type 获取现场图片
	 * @return
	 */
	@RequestMapping(value="/queryPlasmaImageInfo",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaImageInfo", description = "查询现场照片和登记号")
	@ResponseBody
	public DataRow queryPlasmaImageInfo(@RequestParam HashMap<String, String> map){
		try {
            DataRow pl = plasmaImageService.queryPlasmaImageInfo(map);
            messageMap.initSuccess(pl);
		} catch (Exception e) {
			logger.error("PlasmaImageController>>>>>>>>>queryPlasmaImageInfo",e);
		}
		return messageMap;
	}
	
	
	/**
	 * 根据 imgId 和 type 获取现场图片
	 * @return
	 */
	@RequestMapping(value="/queryPlasmaImageByImgIdAndType",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmaImageByImgIdAndType", description = "查询现场照片和登记号")
	@ResponseBody
	public DataRow queryPlasmaImageByImgIdAndType(@RequestParam HashMap<String, String> map){
		try {
			DataRow pl = plasmaImageService.queryPlasmaImageByImgIdAndType(map);
            messageMap.initSuccess(pl);
		} catch (Exception e) {
			logger.error("PlasmaImageController>>>>>>>>>queryPlasmaImageByImgIdAndType",e);
		}
		return messageMap;
	}
}

