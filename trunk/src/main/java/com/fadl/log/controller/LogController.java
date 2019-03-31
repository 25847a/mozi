package com.fadl.log.controller;


import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fadl.account.entity.Admin;
import com.fadl.common.CommonUtil;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.log.entity.Log;
import com.fadl.log.service.LogService;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.sf.json.JSONObject;

/**
 * <p>
 *  拦截器：记录用户操作日志，检查用户是否登录…… 
 * </p>
 *
 * @author wj
 * @since 2018-07-20
 */
@Aspect  
@Component 
public class LogController {
	/*   
	 @Autowired
	 
	LogService logService;
	 public static final Logger logger = LoggerFactory.getLogger(LogController.class);
	    // 切点`
	    @Pointcut("@annotation(com.fadl.common.annotation.InvokeLog)")
	  //@Pointcut("execution(* com.fadl.*.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	    public void executePointCut() {

	    }


	    // around 切面强化
	    @Around("executePointCut()")
	    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
	        Object[] args = joinPoint.getArgs();
	        String name = joinPoint.getSignature().getDeclaringTypeName();
	        //CommonController 公共方法不保存到日志表
	    	if (name.indexOf("CommonController")!=-1) {
	    		return joinPoint.proceed(args); 
			}
	        Object retrunobj = null;
	        if (logger.isDebugEnabled() || logger.isWarnEnabled()) {
	            StopWatch clock = new StopWatch();
	            clock.start();
	            try {
	                // 注意和finally中的执行顺序 finally是在return中的计算结束返回前执行
	            	return retrunobj = joinPoint.proceed(args);
	            } catch (Throwable e) {
	            	logger.error("LogController>>>>>>>>>execute>>>>>",e);
	            } finally {
	                clock.stop();
	                long totalTime = clock.getTotalTimeMillis();
	                // 打印日志
	                Session session= SecurityUtils.getSubject().getSession();
	                Admin admin=null; 
	                if(session!=null){
	                	  admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
	                }
	                try {
	                	 handleLog(joinPoint, args, retrunobj, admin==null?null:admin.getId(),totalTime);
					} catch (Exception e2) {
						logger.error("LogController>>>>>>>>>execute>>>>>",e2);
					}
	               
	            }
	        } else {
	            return joinPoint.proceed(args);
	        }
	        return retrunobj;
	    }



	    *//**
	     * 日志处理
	     *
	     * @param joinPoint 位置
	     * @param args      参数
	     * @param retrunobj 响应
	     * @param creater 操作人
	     * @param totalTime  耗时ms
	     *//*
	    private void handleLog(ProceedingJoinPoint joinPoint, Object[] args, Object retrunobj,Long creater, long totalTime)throws Exception {
	        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
	        InvokeLog invokeLog = method.getAnnotation(InvokeLog.class);
	        printLogMsg(invokeLog.name(), invokeLog.description(), invokeLog.printReturn(), joinPoint, args, retrunobj,creater, totalTime);

	    }

	    *//**
	     * @param name            操作名称
	     * @param description     描述
	     * @param printReturn     是否打印响应
	     * @param joinPoint       位置
	     * @param args            参数
	     * @param returnObj       响应
	     * @param creater         操作人
	     * @param totalTimeMillis 耗时ms
	     *//*
	    protected void printLogMsg(String name, String description, boolean printReturn, ProceedingJoinPoint joinPoint, Object[] args, Object returnObj,Long creater, long totalTimeMillis)throws Exception {
	        String params = analysisParam(joinPoint);
	        logger.info(params);
	        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	        Log log = new Log();
	        log.setMethod(name);
	        log.setDescribe(description);
	        log.setParam(params.toString());
	        log.setCreater(creater);
	        log.setUpdater(creater);
	        log.setTime(totalTimeMillis);
	        log.setReturnValue(getPrintMsg(printReturn, returnObj));
	        String ip=CommonUtil.getIpAddress(request);
	        Date d = new Date();
	        log.setCreateDate(DateUtil.sf.format(d));
	        log.setUpdateDate(DateUtil.sf.format(d));
	        log.setIp(ip);
	        logService.insert(log);
	    }



	    private String getPrintMsg(boolean printReturn, Object returnObj) {
	        return printReturn ? ((returnObj != null) ? JSONObject.fromObject(returnObj).toString(): "null") : "[printReturn = false]";  // (returnObj) : "null") : "[printReturn = false]";
	    }

	    private String argsDemote(Object[] args) {
	        if (args == null || args.length == 0) {
	            return "";
	        }
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < args.length; i++) {
	            Object arg = args[i];
	            if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof ModelMap
	                    || arg instanceof Model || arg instanceof InputStreamSource ||
	                    arg instanceof File) {
	                sb.append(args.length-1==i?args.toString():args.toString()+",");
	            } else {
	                sb.append(args.length-1==i?args[i]:args[i]+",");
	            }
	        }
	        return sb.toString();
	    }

		public String analysisParam(ProceedingJoinPoint joinPoint) throws Exception{
			String classType = joinPoint.getTarget().getClass().getName();  
		    Class<?> clazz = Class.forName(classType);  
		    String clazzName = clazz.getName();  
		    String methodName = joinPoint.getSignature().getName(); //获取方法名称 
		    Object[] args = joinPoint.getArgs();//参数
		    //获取参数名称和值
		    Map<String,Object > nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName,args); 
		    return nameAndArgs.toString();
		}
	  
		@SuppressWarnings("rawtypes")
		private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws Exception { 
			Map<String,Object > map=new HashMap<String,Object>();
			
	        ClassPool pool = ClassPool.getDefault();  
	        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
	        ClassClassPath classPath = new ClassClassPath(cls);  
	        pool.insertClassPath(classPath);  
	          
	        CtClass cc = pool.get(clazzName);  
	        CtMethod cm = cc.getDeclaredMethod(methodName);  
	        MethodInfo methodInfo = cm.getMethodInfo();
	        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
	        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
	        if (attr == null) {  
	            // exception  
	        }  
	       // String[] paramNames = new String[cm.getParameterTypes().length];  
	        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
	        for (int i = 0; i < cm.getParameterTypes().length; i++){  
	            map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名  
	        }  
	        return map;  
	    }  
*/
}

