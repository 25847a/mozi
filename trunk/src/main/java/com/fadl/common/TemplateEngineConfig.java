package com.fadl.common;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
/**
 * THY 自定义方言配置类
 * @author Administrator
 *
 */
public class TemplateEngineConfig {
	@Autowired
	private ApplicationContext applicationContext; 
	 @Bean  
	    public ContentNegotiatingViewResolver getViewResolver(){  
		 	SpringResourceTemplateResolver templateResolver=new SpringResourceTemplateResolver();  
		 	templateResolver.setApplicationContext(applicationContext);
	        templateResolver.setPrefix("templates/");  
	        templateResolver.setSuffix(".html");  
	        templateResolver.setCacheable(false);  
	        templateResolver.setCharacterEncoding("UTF-8");  
	        Set<IDialect> additionalDialects=new LinkedHashSet<IDialect>();  
	        //自定义方言  
	        additionalDialects.add(new DictDialect());  
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();  
	        templateEngine.setAdditionalDialects(additionalDialects);  
	        templateEngine.setTemplateResolver(templateResolver);  
	        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();  
	        thymeleafViewResolver.setTemplateEngine(templateEngine);  
	        thymeleafViewResolver.setCharacterEncoding("UTF-8");  
	        thymeleafViewResolver.setOrder(1);  
	        List<ViewResolver> viewResolvers= new ArrayList<>();  
	        viewResolvers.add(thymeleafViewResolver);  
	        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();  
	        viewResolver.setViewResolvers(viewResolvers);  
	        return viewResolver; 
	 }
}
