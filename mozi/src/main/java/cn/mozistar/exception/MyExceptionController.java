package cn.mozistar.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mozistar.util.ResultBase;
/**
 * 统一异常处理
 * @author Admin
 *
 */
@ControllerAdvice
public class MyExceptionController{

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultBase errorHandler(Exception ex) {
    	ex.printStackTrace();
    	ResultBase r = new ResultBase();
    	r.setCode(400);
    	r.setMessage("服务出错");
        return r;
    }

}
