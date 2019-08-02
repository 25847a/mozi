package com.fadl.common;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{adminId}")
@Component
@Scope("prototype")
public class WebSocketServer {
	
		private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class); 
		//静态变量，用来记录当前在线连接数,由于是成员变量,存在于堆中,数据共享,线程不安全,设计成线程安全,需要用到synchronized.
		private static int onlineCount=0;
		//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象.
		private static CopyOnWriteArraySet<WebSocketServer> webSocketServer = new CopyOnWriteArraySet<WebSocketServer>();
		
		//与某个客户端的连接会话，需要通过它来给客户端发送数据
		private Session session;
		//接收adminId
	    private String adminId="";
		/**
		 * 连接建立成功调用的方法
		 * @param session
		 * @param sid
		 */
		@OnOpen
		public void onOpen(Session session,@PathParam("adminId") String adminId){
			try {
			this.session=session;
			webSocketServer.add(this);
			addOnlineCount();
			logger.error("有新的窗口开始监听:"+adminId+":当前在线的用户为："+getOnlineCount());
			this.adminId=adminId;
			logger.error("<<<<<<<<<<<<<<<<websocket：连接成功>>>>>>>>>>>>>>>>>>>");
			} catch (Exception e) {
				logger.error("websocket连接成功产生异常:>>>>>>>>>>>>>>>>>>>",e);
			}
					
		}
		/**
		 * 连接关闭调用的方法
		 */
		@OnClose
		public void onClose(){
			try {
				webSocketServer.remove(this);
				subOnlineCount();
				logger.error("<<<<<<<<<<当前有一个连接关闭！当前在线人数为>>>>>>>>>>"+getOnlineCount());
			} catch (Exception e) {
				logger.error("websocket连接失败产生异常:>>>>>>>>>>>>>>>>>>>",e);
			}
		}
		/**
		 * 收到客户端消息后调用的方法
		 * @param message
		 * @param session
		 */
		@OnMessage
		public void onMessage(String message,Session session){
			logger.info("收到来自窗口"+adminId+"的信息:"+message);
			try {
				for(WebSocketServer item:webSocketServer){
					
					item.sendMessage(message);
				}
			} catch (Exception e) {
				logger.error("websocket连接返回时产生异常:>>>>>>>>>>>>>>>>>>>",e);
			}
		}
		
		/**
		 * 产生错误进入的方法
		 * @param session
		 * @param error
		 */
		@OnError
		public void onError(Session session,Throwable error){
			webSocketServer.remove(this);
			subOnlineCount();
			logger.error("websocket异常:>>>>>>>>>>>>>>>>>>>",error);
		}
		
		/**
		 * 实现服务器主动推送
		 * @param message
		 * @throws IOException
		 */
		public void sendMessage(String message)throws IOException{
			
		//	DataRow messageMap = new DataRow();
			try {
			//	messageMap=	SpringContextHolder.getApplicationContext().getBean(HealthService.class).queryBeadhouseList(messageMap,page);
				//	this.session.getBasicRemote().sendText("1111");
			} catch (Exception e) {
				logger.error("sendMessage推送异常:>>>>>>>>>>>>>>>>>>>",e);
			}
		//	String msg = JsonUtil.getMapper().writeValueAsString(messageMap);
		//	synchronized (this.session) {
			this.session.getBasicRemote().sendText(message);
		//	}
		}
		/**
		 * 群发自定义消息
		 * @param message
		 * @throws IOException
		 */
		public static void sendInfo(String message,String adminId)throws IOException{
			for(WebSocketServer item:webSocketServer){
				try {
					//item.sendMessage(JsonUtil.getMapper().writeValueAsString(message));
					//这里可以设定只推送给这个sid的，为null则全部推送
	            	if(adminId==null) {
	            		item.sendMessage(message);
	            	}else if(item.adminId.equals(adminId)){
	            		item.sendMessage(message);
	            	}
				} catch (Exception e) {
					logger.error("websocket群发自定义消息:>>>>>>>>>>>>>>>>>>>",e);
				}
			}
		}
		/**
		 * 获取在线数
		 * @return
		 */
		public static synchronized int getOnlineCount(){
			return WebSocketServer.onlineCount;
		}
		/**
		 * 增加在线数
		 */
		public static synchronized void addOnlineCount(){
			WebSocketServer.onlineCount++;
		}
		/**
		 * 减少在线数
		 */
		public static synchronized void subOnlineCount(){
			WebSocketServer.onlineCount--;
		}
}
