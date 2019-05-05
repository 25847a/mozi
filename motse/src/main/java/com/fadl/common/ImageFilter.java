package com.fadl.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 本地图片地址配置
 * @author hu
 *
 */
@Configuration
public class ImageFilter extends WebMvcConfigurerAdapter{

	
/*************  静态资源映射     *****************/
	
	/**
	 * 本地图片地址配置 
	 * 
	 * 访问地址 http://ip:端口/image/upload/idCard/440803199108081136.jpg
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String a = ReadProperties.getValue("imageAddress");
		registry.addResourceHandler("/image/upload/**").addResourceLocations("file:///"+ReadProperties.getValue("imageAddress"));
		super.addResourceHandlers(registry);
	}
}
