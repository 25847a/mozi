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
import cn.mozistar.mapper.PushRecordMapper;
import cn.mozistar.mapper.UserMapper;
import cn.mozistar.pojo.Health;
import cn.mozistar.pojo.Push;
import cn.mozistar.pojo.PushRecord;
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
	@Autowired
	private PushRecordMapper pushRecordMapper;

	public int insertSelective(Health health) {
		return healthMapper.insertSelective(health);
	}

	public List<Chart> selecthealth(Map<String, Object> m) {
		return healthMapper.selecthealth(m);
	}
	/**
	 * 查询心率MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	@Override
	public Map<String, String> selectHeartRateInfo(Map<String, Object> m) {
		return healthMapper.selectHeartRateInfo(m);
	}
	/**
	 * 查询步数MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	@Override
	public Map<String, String> selectStepWhenInfo(Map<String, Object> m) {
		return healthMapper.selectStepWhenInfo(m);
	}
	/**
	 * 查询血压MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	@Override
	public Map<String, String> selectBloodpressureInfo(Map<String, Object> m) {
		return healthMapper.selectBloodpressureInfo(m);
	}
	/**
	 * 查询HRV MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	@Override
	public Map<String, String> selectHrvInfo(Map<String, Object> m) {
		return healthMapper.selectHrvInfo(m);
	}
	/**
	 * 发送预警
	 */
	public void sendJpush(Health health) {
		
		Push push =pushMapper.selectPushUserId(health.getUserId(),health.getUserId());
		if(push.getAllNotifyOn() == 1){
			User user = userMapper.selectByPrimaryKey(health.getUserId());
			Integer heartRate = health.getHeartRate();
			Integer lowBloodPressure = health.getLowBloodPressure();
			Integer highBloodPressure = health.getHighBloodPressure();
			List<Push> pushs = pushMapper.selectPushByUserId(health.getUserId());//通过userId查询push列表,获取通知值alias
			PushRecord pushRecord = new PushRecord();//
			for(Push p:pushs){
				pushRecord.setUserId(health.getUserId());/////
				// 心率
				if (heartRate > push.getHeartHigThd() || heartRate < push.getHeartLowThd()) {
					pushRecord.setHeartUnusual(heartRate);//////
					JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的心率异常","当前心率" + heartRate,"已经超出预警设定值" + push.getHeartLowThd() + "-" + push.getHeartHigThd(), null, null);
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>心率已经超出预警设定值一>>>"+"通知ID"+p.getAlias());
				}
				// 高压
				if (highBloodPressure < push.getHbpstart() || highBloodPressure > push.getHbpend()) {
					pushRecord.setHighBloodUnusual(highBloodPressure);/////
					JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的收缩压异常","当前收缩压为" + highBloodPressure,"已经超出预警设定值" + push.getHbpstart() + "-" + push.getHbpend(), null, null);
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收缩压已经超出预警设定值一"+"通知ID"+p.getAlias());
				}
				// 低压
				if (lowBloodPressure < push.getLbpstart() || lowBloodPressure > push.getLbpend()) {
					pushRecord.setLowBloodUnusual(lowBloodPressure);/////
				JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的舒张压异常","当前舒张压为" + lowBloodPressure,"已经超出预警设定值" + push.getLbpstart() + "-" + push.getLbpend(), null, null);
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>舒张压已经超出预警设定值一"+"通知ID"+p.getAlias());
				}
			}
			if(pushRecord.getHeartUnusual()!=0 || pushRecord.getHighBloodUnusual()!=0 || pushRecord.getLowBloodUnusual()!=0){
				pushRecordMapper.insertPushRecord(pushRecord);
			}
			
		}
		
	/*	List<Push> pushs = pushMapper.selectPushByUserId(health.getUserId());//通过userId查询push列表,获取通知值alias
		
		User user = userMapper.selectByPrimaryKey(health.getUserId());
		
		//这里的push设计有问题,才健2019-05-28修改下
		if (pushs != null && pushs.size() > 0) {
			pushs.forEach(push -> {
				PushRecord pushRecord = new PushRecord();//
				push.setAlias(push.getUserId());
			//	push.setUserId(push.getAlias());
				Push p = pushMapper.selectPushByAliasAndUserId(push);
				pushRecord.setUserId(p.getUserId());/////
				logger.info(">>>>>>>>>>>>>>循环遍历得到用户名和通知ID:>>>>>>>>>>>>>>>>"+user.getName()+">>>>>"+p.getAlias());
				if (p.getAllNotifyOn() == 1) {
					// 低压
					if (lowBloodPressure < p.getLbpstart() || lowBloodPressure > p.getLbpend()) {
									pushRecord.setLowBloodUnusual(lowBloodPressure);/////
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的舒张压异常",
											"当前舒张压为" + lowBloodPressure,
											"已经超出预警设定值" + p.getLbpstart() + "-" + p.getLbpend(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>舒张压已经超出预警设定值一:");
					}
					// 高压
					if (highBloodPressure < p.getHbpstart() || highBloodPressure > p.getHbpend()) {
									pushRecord.setHighBloodUnusual(highBloodPressure);/////
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的收缩压异常",
											"当前收缩压为" + highBloodPressure,
											"已经超出预警设定值" + p.getHbpstart() + "-" + p.getHbpend(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收缩压已经超出预警设定值一:");
					}
					// 心率
					if (heartRate > p.getHeartHigThd() || heartRate < p.getHeartLowThd()) {
									pushRecord.setHeartUnusual(heartRate);//////
									JpushClientUtil.sendToAlias(p.getAlias().toString(), user.getName() + "的心率异常",
											"当前心率" + heartRate,
											"已经超出预警设定值" + p.getHeartLowThd() + "-" + p.getHeartHigThd(), null, null);
									logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>心率已经超出预警设定值一:");
					}
					System.out.println("alias>>>>"+p.getAlias()+",userId>>>>>>"+p.getUserId());
					if(p.getAlias()==p.getUserId()){
						if(pushRecord.getHeartUnusual()!=0 || pushRecord.getHighBloodUnusual()!=0 || pushRecord.getLowBloodUnusual()!=0){
							pushRecordMapper.insertPushRecord(pushRecord);
						}
					}
				}
			});
		}*/
	}

	@Override
	public Health getHealthByUserId(Integer userId) {
		return healthMapper.selectByUserId(userId);
	}

	
	

}
