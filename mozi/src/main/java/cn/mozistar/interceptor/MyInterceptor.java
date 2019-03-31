package cn.mozistar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Component;

/**
 * 拦截器  
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
	
	//在请求处理之前进行调用（Controller方法调用之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
    	String requestUrl = request.getRequestURI();
		logger.info("请求接口》》》"+requestUrl);
		return true;
    }

    //执行contoller后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    //进入视图渲染执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
