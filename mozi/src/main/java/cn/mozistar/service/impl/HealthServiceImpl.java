package cn.mozistar.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.HealthMapper;
import cn.mozistar.mapper.PushMapper;
import cn.mozistar.mapper.UserMapper;
import cn.mozistar.pojo.Health;
import cn.mozistar.pojo.Push;
import cn.mozistar.pojo.User;
import cn.mozistar.service.HealthService;
import cn.mozistar.util.JpushClientUtil;
import cn.mozistar.vo.Chart;

@Transactional
@Service
public class HealthServiceImpl implements HealthService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HealthMapper healthMapper;
	@Autowired
	private PushMapper pushMapper;
	@Autowired
	private UserMapper userMapper;

	public Health getHealthByUserId(Integer userId) {
		return healthMapper.selectByUserId(userId);
	}

	public int insertSelective(Health health) {
		return healthMapper.insertSelective(health);
	}

	public List<Chart> selecthealth(Map<String, Object> m) {
		return healthMapper.selecthealth(m);
	}

	public Health selecthealthMax(Map<String, Object> map) {
		return healthMapper.selecthealthMax(map);
	}

	public Health selecthealthMin(Map<String, Object> map) {
		return healthMapper.selecthealthMin(map);
	}

	/**
	 * 发送预警
	 */
	public void sendJpush(Health health) {
		List<Push> pushs = pushMapper.selectPushByUserId(health.getUserId());//通过userId查询push列表,获取通知值alias
		User user = userMapper.selectByPrimaryKey(health.getUserId());
		Integer heartRate = health.getHeartRate();
		Integer lowBloodPressure = health.getLowBloodPressure();
		Integer highBloodPressure = health.getHighBloodPressure();
		if (pushs != null && pushs.size() > 0) {
			pushs.forEach(push -> {
				push.setUserId(push.getAlias());
				Push p = pushMapper.selectPushByAliasAndUserId(push);
				logger.info(">>>>>>>>>>>>>>循环遍历得到用户名和通知ID:>>>>>>>>>>>>>>>>"+user.getName()+">>>>>"+p.getAlias());
				if (p.getAllNotifyOn() == 1) {
					// 低压
					if (lowBloodPressure < p.getLbpstart() || lowBloodPressure > p.getLbpend()) {
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的舒张压异常",
											"当前舒张压为" + lowBloodPressure,
											"已经超出预警设定值" + p.getLbpstart() + "-" + p.getLbpend(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>舒张压已经超出预警设定值一:");
					}
					// 高压
					if (highBloodPressure < p.getHbpstart() || highBloodPressure > p.getHbpend()) {
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的收缩压异常",
											"当前收缩压为" + highBloodPressure,
											"已经超出预警设定值" + p.getHbpstart() + "-" + p.getHbpend(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收缩压已经超出预警设定值一:");
					}

					// 心率
					if (heartRate > p.getHeartHigThd() || heartRate < p.getHeartLowThd()) {
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的心率异常",
											"当前心率" + heartRate,
											"已经超出预警设定值" + p.getHeartLowThd() + "-" + p.getHeartHigThd(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>心率已经超出预警设定值一:");
					}
				}
			});
		}
	}

}
