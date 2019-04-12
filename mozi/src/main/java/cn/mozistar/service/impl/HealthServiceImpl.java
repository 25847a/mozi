package cn.mozistar.service.impl;

import java.util.List;
import java.util.Map;

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
	public void sendJpush(Health health,String registrationID) {
		List<Push> pushs = pushMapper.selectPushByUserId(health.getUserId());
		User user = userMapper.selectByPrimaryKey(health.getUserId());
		Integer heartRate = health.getHeartRate();
		Integer lowBloodPressure = health.getLowBloodPressure();
		Integer highBloodPressure = health.getHighBloodPressure();
		if (pushs != null && pushs.size() > 0) {
			pushs.forEach(push -> {

				if (push.getAllNotifyOn() == 1) {
					// 低压
					if (lowBloodPressure < push.getLbpstart() || lowBloodPressure > push.getLbpend()) {
						Thread t = new Thread() {
							public void run() {
								if(registrationID.equals("")){
									JpushClientUtil.sendToAlias(push.getAlias().toString(), user.getName() + "的舒张压异常",
											"当前舒张压为" + lowBloodPressure,
											"已经超过正常范围值" + push.getLbpstart() + "-" + push.getLbpend(), null, null);
								}else{
									JpushClientUtil.sendToRegistrationId(registrationID, user.getName() + "的舒张压异常",
											"当前舒张压为" + lowBloodPressure,
											"已经超过正常范围值" + push.getLbpstart() + "-" + push.getLbpend(), null, null);
								}
								
							}
						};
						t.start();
					}
					// 高压
					if (highBloodPressure < push.getHbpstart() || highBloodPressure > push.getHbpend()) {
						Thread t = new Thread() {
							public void run() {
								if(registrationID.equals("")){
									JpushClientUtil.sendToAlias(push.getAlias().toString(), user.getName() + "的收缩压异常",
											"当前收缩压为" + highBloodPressure,
											"已经超过正常范围值" + push.getHbpstart() + "-" + push.getHbpend(), null, null);
								}else{
									JpushClientUtil.sendToRegistrationId(registrationID, user.getName() + "的收缩压异常",
											"当前收缩压为" + highBloodPressure,
											"已经超过正常范围值" + push.getHbpstart() + "-" + push.getHbpend(), null, null);
								}
								
							}
						};
						t.start();
					}

					// 心率
					if (heartRate > push.getHeartHigThd() || heartRate < push.getHeartLowThd()) {
						Thread t = new Thread() {
							public void run() {
								if(registrationID.equals("")){
									JpushClientUtil.sendToAlias(push.getAlias().toString(), user.getName() + "的心率异常",
											"当前心率" + heartRate,
											"已经超过正常范围值" + push.getHeartLowThd() + "-" + push.getHeartHigThd(), null, null);
								}else{
									JpushClientUtil.sendToRegistrationId(registrationID, user.getName() + "的心率异常",
											"当前心率" + heartRate,
											"已经超过正常范围值" + push.getHeartLowThd() + "-" + push.getHeartHigThd(), null, null);
								}
								
							}
						};
						t.start();
					}
				}
			});
		}
	}

}
