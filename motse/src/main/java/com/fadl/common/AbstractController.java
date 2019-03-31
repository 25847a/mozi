package com.fadl.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * @Description:控制器基类
 * @Date:2019-03-27 jian
 */
public class AbstractController {
	@Autowired
    public HttpServletRequest request;
    public  DataRow messageMap;

    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);


    /**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            if (isAjaxRequest(request)) {
                messageMap.initNoPermission();
                out.write(JSONUtils.toJSONString(messageMap));
            }else{
                response.setHeader("Content-Type", "text/html");
                response.getWriter().write("<script type=\"text/javascript\">window.location.href='/common/jurhundred'</script>");
            }
        }catch (Exception e){
            logger.error("AbstractController>>>>>>>>authorizationException>>>>>>>>",e);
            messageMap.initNoPage();
        }finally {

            if(out!=null){
                out.close();
            }
        }
        return null;
    }

    //防止同一个controller为单例值被串改，每次请求重新初始化对象，改为多例
    @ModelAttribute
    public void handleParameter(){
        messageMap = new DataRow();
        messageMap.initError();
    }
    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     * @author SHANHY
     * @create 2017年4月4日
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
