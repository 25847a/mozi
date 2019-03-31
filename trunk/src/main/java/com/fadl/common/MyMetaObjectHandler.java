package com.fadl.common;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.fadl.SpringContextHolder;
import com.fadl.account.entity.Admin;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;

/**
 * @Description:mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 * @Date:2018-03-07
 */
    @Component
    public class MyMetaObjectHandler extends MetaObjectHandler {
    	
        //新增填充
        @Override
        public void insertFill(MetaObject metaObject){
           Session session= SecurityUtils.getSubject().getSession();
            Admin admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
            Date d = new Date();
                setFieldValByName("creater", admin.getId(),metaObject);
                setFieldValByName("updater", admin.getId(),metaObject);
                setFieldValByName("createDate",DateUtil.sf.format(d),metaObject);
                setFieldValByName("updateDate",DateUtil.sf.format(d),metaObject);
                try {
                	ConfigService configService = SpringContextHolder.getBean("configServiceImpl");
					Config config = configService.getConfig("stock_config", "code");
					if(null!=config){
						setFieldValByName("plasmaId",config.getValue(),metaObject);
					}else{
						setFieldValByName("plasmaId","",metaObject);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
        }
        //更新填充
        @Override
        public void updateFill(MetaObject metaObject) {
            Session session= SecurityUtils.getSubject().getSession();
            Admin admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
            if (null!=admin) {
            	setFieldValByName("updateDate", DateUtil.sf.format(new Date()), metaObject);
                setFieldValByName("updater", admin.getId(),metaObject);
			}
        }
}
