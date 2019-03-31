package com.fadl.log.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.CommonUtil;
import com.fadl.log.dao.LogMapper;
import com.fadl.log.entity.Log;
import com.fadl.log.service.LogService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wj
 * @since 2018-07-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

	@Autowired
	LogMapper logMapper;
	
	/**
	 * 查询用户操作日志
	 */
	@Override
	public Page<DataRow> queryLog(Map<String, Object> map, Page<DataRow> pageing) throws Exception {
		return pageing.setRecords(logMapper.queryLog(map, pageing));
	}
	/**
	 * 添加操作日志
	 * @param method 方法或模块
	 * @param describe 描述
	 * @param ip
	 * @param param	参数
	 * @param time	耗时
	 * @param returnValue 返回值
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public Integer insertLog(String method,String describe,String providerNo)throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Log log = new Log();
		Session session = SecurityUtils.getSubject().getSession();
		Admin admin = null;
		if (session != null) {
			admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
		}
		log.setMethod(method);
		log.setProviderNo(providerNo);
		log.setDescribe(describe.replace("{-1}", admin.getName()));
		log.setCreater(admin.getId());
		log.setUpdater(admin.getId());
		String ip = CommonUtil.getIpAddress(request);
		Date d = new Date();
		log.setCreateDate(DateUtil.sf.format(d));
		log.setUpdateDate(DateUtil.sf.format(d));
		log.setIp(ip);
		return logMapper.insert(log);
	}
}
