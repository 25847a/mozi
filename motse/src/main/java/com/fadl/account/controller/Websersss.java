package com.fadl.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.ReadProperties;
@RequestMapping("/ceshi")
@Controller
public class Websersss  extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(Websersss.class); 
	
	public static void main(String[] args) {
		String a = ReadProperties.getValue("imageAddress");
		System.out.println(a);
	}
	
	
	@RequestMapping("/loggn")
	public String loggn(){
		return "/business/register/websocket";
	}
	
	@RequestMapping("/webserver")
	@ResponseBody
	public DataRow webserver(Admin admin){
		try {
			while(true){
				//Thread.sleep(2000);
			//	WebSocketServer.sendInfo(""+i);
			}
		} catch (Exception e) {
			logger.error("Websersss<<<<<<<<<<<<<<<<<<webserver",e);
		}
		return messageMap;
	}
 	
	
}
