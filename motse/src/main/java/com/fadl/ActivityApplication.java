package com.fadl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement  //开启事务控制
@SpringBootApplication
@MapperScan(basePackages = "com.fadl.**.dao*")
@EnableScheduling //定时任务需要用到
public class ActivityApplication {
	
	private static Logger logger = LoggerFactory.getLogger(ActivityApplication.class);
	/**
	 * springboot启动项
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(ActivityApplication.class, args);
		SecurityManager securityManager = (SecurityManager) app.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);
		logger.info("=======================启动成功======================");
	}
	/**
	 * 设置数据传输大小
	 * @return
	 * @throws Exception
	 */
	@Bean  
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {  
		 return (ConfigurableEmbeddedServletContainer container) -> {  
	            if (container instanceof TomcatEmbeddedServletContainerFactory) {  
	                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;  
	                tomcat.addConnectorCustomizers((connector) -> {  
	                    connector.setMaxPostSize(10000000); // 10 MB  
	                });  
	            };
		 };       
        }; 
}
