package com.fadl.account.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.WebSocketServer;

@RequestMapping("/ceshi")
@Controller
public class Websersss  extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(Websersss.class); 
	
	@RequestMapping("/loggn")
	public String loggn(){
		return "/business/register/websocket";
	}
	
	@RequestMapping("/webserver")
	@ResponseBody
	public DataRow webserver(Admin admin){
		int i = 0;
		try {
			while(true){
				//Thread.sleep(2000);
				i++;
			//	WebSocketServer.sendInfo(""+i);
			}
		} catch (Exception e) {
			logger.error("Websersss<<<<<<<<<<<<<<<<<<webserver",e);
		}
		return messageMap;
	}
 	
	
}
