package com.fadl.comfig;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fadl.account.entity.Auth;
import com.fadl.account.service.AuthService;
import com.fadl.comfig.shiro.MyShiroRealm;
import com.fadl.common.StringHelper;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	
	
	private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
	@Autowired
	AuthService authService;
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	@Value("${spring.redis.password}")
	private String password;
	/**
	 * 管理shiro bean生命周期
	 * @return
	 */
	@Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
	/**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager  securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
		try {
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		 shiroFilterFactoryBean.setLoginUrl("/admin/login");//如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		 shiroFilterFactoryBean.setSuccessUrl("/index");//登录成功后要跳转的链接
		 shiroFilterFactoryBean.setUnauthorizedUrl("/403");//未授权跳转的页面
		 //拦截器
		 Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		 //配置不会被拦截的链接 顺序判断
		 //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		 filterChainDefinitionMap.put("/static/**", "anon");
		 filterChainDefinitionMap.put("/logout", "logout");
		 filterChainDefinitionMap.put("/css*/**/**","anon");
		 filterChainDefinitionMap.put("/js*/**/**","anon");
	     filterChainDefinitionMap.put("/img*/**/**","anon");
	     filterChainDefinitionMap.put("/fonts*/**/**","anon");
	     filterChainDefinitionMap.put("/log/**","anon");
		 //过滤链定义，从上向下顺序执行，一般将/**放在最为下边:这是一个坑呢，一不小心代码就不好使了;
	     filterChainDefinitionMap.put("/ceshi/**","anon");
	     filterChainDefinitionMap.put("/websocket/**","anon");
	 /*   List<Auth> auth =authService.queryAuthList();
	     for(Auth au:auth){
	    	 if(!StringHelper.isEmpty(au.getUrl()) && !StringHelper.isEmpty(au.getPermission())){
	    		 filterChainDefinitionMap.put(au.getUrl(), "perms["+au.getPermission()+"]");
	    	 }
	     }*/
		 filterChainDefinitionMap.put("/**", "authc");
		 shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		} catch (Exception e) {
			 logger.error("ShiroConfig>>>>>>>>>>>>>>>>shirFilter>>>>>>>>>>>>>>",e);
		}
		return shiroFilterFactoryBean;
	}
	/**
     * 身份认证realm; (自定义，账号密码校验；权限等)
     *
     * @return
     */
	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
	/**
	 * springboot启动项 先执行SecurityManager
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(myShiroRealm());
		// 自定义缓存实现 使用redis
	//	securityManager.setCacheManager(redisCacheManager());
		return securityManager;
	}
	/**
	 * 验证密码算法
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(3);//散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}
	 /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     *  Controller才能使用@RequiresPermissions
     * @param securityManager
     * @return
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorization = new AuthorizationAttributeSourceAdvisor();
		authorization.setSecurityManager(securityManager);
		return authorization;
	}
	/**
	 * 配置shiro redisManager
	 * 使用的是shiro-redis开源插件
	 * @return
	
	public RedisManager redisManager(){
		RedisManager redis = new RedisManager();
		redis.setHost(host);
		redis.setPassword(password);
		redis.setPort(port);
		redis.setExpire(1800);
		redis.setTimeout(timeout);
		return redis;
	} */
	/**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
    
	@Bean
	public RedisCacheManager redisCacheManager(){
		RedisCacheManager redis = new RedisCacheManager();
		redis.setRedisManager(redisManager());
		return redis;
	}
 */
}
