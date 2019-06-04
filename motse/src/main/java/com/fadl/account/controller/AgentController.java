package com.fadl.account.controller;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.Agent;
import com.fadl.account.service.AgentService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2019-05-08
 */
@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	@Autowired
	AgentService agentService;
	
	
	
	
	 /**
     * 查询代理商列表
     * @return
     */
    @RequestMapping("/queryAgentList")
    @ResponseBody
    public DataRow queryAgentList(@RequestParam Map<String,Object> map){
    	try {
    		messageMap = agentService.queryAgentList(map,messageMap);
		} catch (Exception e) {
			logger.error("AgentController<<<<<<<<<<<<<<<<<<queryAgentList",e);
		}
		return messageMap;
    }
	 /**
     * 获取代理商基础信息
     * avatar
     * @return
     */
    @RequestMapping("/queryAgentInfo")
    @ResponseBody
    public DataRow queryAgentInfo(){
    	try {
    		Admin admin = SessionUtil.getSessionAdmin();
    		messageMap =  agentService.queryAgentInfo(admin.getId(),messageMap);
		} catch (Exception e) {
			logger.error("AgentController<<<<<<<<<<<<<<<<<<updateAgentImage",e);
		}
		return messageMap;
    }
    /**
     * 查询供应商列表选项
     * @return
     */
    @RequestMapping("/queryAgentOption")
    @ResponseBody
    public DataRow queryAgentOption(){
    	try {
    		messageMap =  agentService.queryAgentOption(messageMap);
		} catch (Exception e) {
			logger.error("AgentController<<<<<<<<<<<<<<<<<<queryAgentOption",e);
		}
		return messageMap;
    }
    /**
	 * 新增代理商信息
	 * @return
	 * @throws SQLException
	 */
    @RequestMapping("/addAgentInfo")
    @ResponseBody
    public DataRow addAgentInfo(Agent agent){
    	try {
    		messageMap =  agentService.addAgentInfo(agent,messageMap);
		} catch (Exception e) {
			logger.error("AgentController<<<<<<<<<<<<<<<<<<addAgentInfo",e);
		}
		return messageMap;
    }
    /**
   	 * 修改代理商信息
   	 * @return
   	 * @throws SQLException
   	 */
       @RequestMapping("/updateAgentInfo")
       @ResponseBody
       public DataRow updateAgentInfo(Agent agent){
       	try {
       		messageMap =  agentService.updateAgentInfo(agent,messageMap);
   		} catch (Exception e) {
   			logger.error("AgentController<<<<<<<<<<<<<<<<<<updateAgentInfo",e);
   		}
   		return messageMap;
       }
       /**
   	 * 查询代理商信息
   	 * @param map
   	 * @return
   	 */
   	@RequestMapping("/queryAgent")
   	@ResponseBody
   	public DataRow queryRoleInfo(){
   		try {
   			List<Agent> list = agentService.selectList(null);
   			messageMap.initSuccess(list);
   		} catch (Exception e) {
   			logger.error("AgentController>>>>>>>>>>>>>queryAgent",e);
   		}
   		return messageMap;
   	}
}

