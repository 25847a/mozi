package com.fadl.elisa.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.elisa.service.ElisaIiqcService;

/**
 * <p>
 * 即刻性室内质控记录表 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-20
 */
@Controller
@RequestMapping("/elisaIiqc")
public class ElisaIiqcController extends AbstractController{
	@Autowired
	private ElisaIiqcService iiqcService;
	
	private static Logger logger = LoggerFactory.getLogger(ElisaIiqcController.class);
	
	public DataRow findByTciidAndPageNo(Long tciid, Long qcId, Integer page, Integer limit) {
		
		return  messageMap;
	}
	
	
}

