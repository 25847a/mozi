package com.fadl.system.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.health.controller.HealthController;
import com.fadl.system.service.NurseService;

/**
 * <p>
 * 护士表 前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Controller
@RequestMapping("/nurse")
public class NurseController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(HealthController.class);
	
	@Autowired
	NurseService nurseService;
	
	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryNurseInfo")
	@ResponseBody
	public DataRow queryNurseInfo(){
		try {
		Admin admin = SessionUtil.getSessionAdmin();
		messageMap=nurseService.queryNurseInfo(admin,messageMap);
		} catch (Exception e) {
			logger.error("NurseController>>>>>>>>>>>>>queryNurseInfo",e);
		}
		return messageMap;
	}
}

