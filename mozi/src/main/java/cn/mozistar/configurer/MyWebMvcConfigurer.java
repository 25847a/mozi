package cn.mozistar.configurer;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cn.mozistar.interceptor.MyInterceptor;

/**
 *配置类
 */
@Configuration
public class MyWebMvcConfigurer implements  WebMvcConfigurer {

    /**
     * 配置静态资源
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
            //.excludePathPatterns("/hlladmin/login") //登录页
    }

}
