package com.fadl.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:wangjing
 * @Description:异常处理
 * @Date:2018-04-19
 */
@Controller
public class MainsiteErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 404){
            return "/errorpage/404";
        }else{
            return "/errorpage/500";
        }

    }
    @Override
    public String getErrorPath() {
        return "/error";
    }




}
