package com.fadl.config.shiro;

import com.fadl.account.entity.SysPermission;
import com.fadl.account.service.SysPermissionService;
import com.fadl.common.StringHelper;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:wangjing
 * @Description:
 * @Date:2018-04-24
 */
@Service
public class ShiroService {
    private static Logger logger = LoggerFactory.getLogger(ShiroService.class);
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    private SysPermissionService sysPermissionService;
    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        try {
            // 权限控制map.从数据库获取
            filterChainDefinitionMap.put("/logout", "logout");
            filterChainDefinitionMap.put("/css*/**/**","anon");
            filterChainDefinitionMap.put("/js*/**/**","anon");
            filterChainDefinitionMap.put("/img*/**/**","anon");
            filterChainDefinitionMap.put("/fonts*/**/**","anon");
            filterChainDefinitionMap.put("/iconfont*/**//**","anon");
            filterChainDefinitionMap.put("/json*/**/**","anon");
            filterChainDefinitionMap.put("/layui-master*/**/**","anon");

            //不需要认证即可访问的
            filterChainDefinitionMap.put("/log/**","anon");
            filterChainDefinitionMap.put("/upload/**","anon");
            List<SysPermission> permissionList = sysPermissionService.querySysPermissionList();
            for(SysPermission sp:permissionList){
                if (!StringHelper.isEmpty(sp.getUrl())&&!StringHelper.isEmpty(sp.getPermission())) {
                    String permission = "perms[" +sp.getPermission()+ "]";
                    filterChainDefinitionMap.put(sp.getUrl(),permission);
                }
            }
            filterChainDefinitionMap.put("/**", "authc");
        }catch (Exception e){
            logger.error("ShiroService>>>>>>>>>>>>>>loadFilterChainDefinitions>>>>>>>>>>>",e);
        }

        return filterChainDefinitionMap;
    }

    /**
     * 重新加载权限
     */
    public void updatePermission() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean
                    .setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            logger.info("更新权限成功！！");
        }
    }
}
