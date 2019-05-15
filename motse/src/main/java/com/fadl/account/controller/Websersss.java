package com.fadl.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fadl.account.entity.Admin;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
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
 	public static void main(String[] args) {
 	        int time = 50*5;
 	        int hours = (int) Math.floor(time / 60);
 	        int minute = time % 60;
 	        System.out.println(hours + "小时" + minute + "分钟");
	}
	
}
