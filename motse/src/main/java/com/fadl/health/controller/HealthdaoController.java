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
import com.fadl.health.entity.Healthdao;
import com.fadl.health.service.HealthdaoService;

/**
 * <p>
 * 健康数据校准表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-04-29
 */
@Controller
@RequestMapping("/healthdao")
public class HealthdaoController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(HealthdaoController.class);
	
	@Autowired
	HealthdaoService healthdaoService;
	
	
	/**
	 *  查询人工智能学习信息
	 * @param map
	 * @return
	 */
    @RequestMapping("/queryHealthDaoInfo")
    @ResponseBody
    public DataRow queryHealthDaoInfo(@RequestParam Map<String,String> map){
    	try {
    		messageMap=healthdaoService.queryHealthDaoInfo(map,messageMap);
		} catch (Exception e) {
			logger.error("HealthdaoController>>>>>>>>>>>>>queryHealthDaoInfo",e);
		}
		return messageMap;
    }
    /**
	 *  更改人工智能学习信息
	 * @param map
	 * @return
	 */
    @RequestMapping("/updateHealthDaoInfo")
    @ResponseBody
    public DataRow updateHealthDaoInfo(Healthdao healthdao){
    	try {
    		messageMap=healthdaoService.updateHealthDaoInfo(healthdao,messageMap);
		} catch (Exception e) {
			logger.error("HealthdaoController>>>>>>>>>>>>>queryHealthDaoInfo",e);
		}
		return messageMap;
    }
}

