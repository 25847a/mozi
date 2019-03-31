package com.fadl;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
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
	public static void main(String[] args) throws Exception{
		ConfigurableApplicationContext app = SpringApplication.run(ActivityApplication.class, args);
		SecurityManager securityManager = (SecurityManager) app.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);
		logger.info("=======================启动成功======================");
	} 
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}
	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		//Connector监听的http的端口号
		connector.setPort(8080);
		connector.setSecure(false);
		//监听到http的端口号后转向到的https的端口号
		connector.setRedirectPort(8443);
		return connector;
	}
}