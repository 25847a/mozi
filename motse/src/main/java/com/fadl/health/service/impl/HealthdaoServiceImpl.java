package com.fadl.health.service.impl;

import com.fadl.health.entity.Healthdao;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.health.dao.HealthdaoMapper;
import com.fadl.health.service.HealthdaoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 健康数据校准表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-29
 */
@Service
public class HealthdaoServiceImpl extends ServiceImpl<HealthdaoMapper, Healthdao> implements HealthdaoService {
	
	
	@Autowired
	HealthdaoMapper healthdaoMapper;
	/**
	 *  查询人工智能学习信息
	 * @param map
	 * @return
	 */
	@Override
	public DataRow queryHealthDaoInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		messageMap=healthdaoMapper.queryHealthDaoInfo(map);
		messageMap.initSuccess();
		return messageMap;
	}
	/**
	*  更改人工智能学习信息
	* @param map
	* @return
	*/	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateHealthDaoInfo(Healthdao healthdao, DataRow messageMap) throws SQLException {
		EntityWrapper<Healthdao> ew = new EntityWrapper<Healthdao>();
		ew.eq("phone", healthdao.getPhone());
		Healthdao h=this.selectOne(ew);
		healthdao.setCreatetime(DateUtil.sf.format(new Date()));
		if(h!=null){
			this.update(healthdao, ew);
		}else{
			this.insert(healthdao);
		}
		messageMap.initSuccess();
		return messageMap;
	}

}
