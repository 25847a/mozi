package cn.mozistar.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import com.google.gson.Gson;
import cn.mozistar.pojo.Health;
import cn.mozistar.pojo.Healthdao;
import cn.mozistar.pojo.Healths;
import cn.mozistar.pojo.User;
import cn.mozistar.service.HealthService;
import cn.mozistar.service.HealthdaoService;
import cn.mozistar.service.HealthsService;
import cn.mozistar.service.UserService;
import net.sf.json.JSONObject;

@Component
public class HealthtoolUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(HealthtoolUtils.class);
	private static String url = "https://api.jingfantech.com/V1.02/physical_exam/manufacturer";
	private static String charset = "utf-8";
	private static String device_id = "6A6668010001010101010000FFFFFFFF";
	
	
	
	// 血压
	private static final String SBDP = "sbdp";
	// 心率
	private static final String HTRATE = "htrate";
	// 血氧
	private static final String SPO = "spo";
	// 微循环
	private static final String BK = "bk";
	// 体检报告
	private static final String REPORT = "report";
	// 呼吸頻率
	private static final String RESPIRATIONRATE = "respiration_rate";

	private static final String HRV = "HRV";
	
	public static void main(String[] args) throws Exception {
		
	/*	String data = "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";
		String date2="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";
		
		System.out.println(data.length());*/
		
		/*StringBuilder sb = new StringBuilder();
		byte[] fromBASE64 = Base64Utils.decodeFromString(data);
		for (byte b : fromBASE64) {
			sb.append(b+128+",");
		}
		System.out.println(sb.toString());*/
		
	/*	String uploadhealth = Uploadhealth("mozistar18", "123456", date2, device_id, "T14");
		//String uploadhealth = "2018-12-24 15:02:44"; //2018-12-24 15:02:44
		//System.out.println(uploadhealth);
		String hrv = HRV("mozistar18", "123456", uploadhealth, device_id);
		System.out.println(hrv);
		// 血压()
		String bloodr = bloodpressure("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("血压:="+bloodr);
		// 心率
		String heartr = Heartrate("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("心率:="+heartr);
		// 血氧
		String bloodxy = Bloodoxygen("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("血氧:="+bloodxy);
		// 微循环uploadhealth
		String microcir = microcirculation("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("微循环:="+microcir);
		// 体检报告
		String amedical = Amedicalreport("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("体检报告:="+amedical);
		// 呼吸频率
		String respiration = respirationrate("mozistar28338", "123456", uploadhealth, device_id);
		System.out.println("体检报告:="+amedical);
		// 高低压
		String[] bloodxygen = bloodr.split(",");*/

	}
	/**
	 * 硬件上传惊凡数据
	 * 
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public static ResultData<ResultBase> addT14Health(JSONObject json,User user,Healthdao healthdao ,UserService userService,HealthdaoService healthdaoService,HealthService healthService,ResultData<ResultBase> re,HealthsService healthsService){
		try{
				//健康数据
				String data = json.getString("data");
				
				String account = "mozistar" + String.valueOf(user.getId());
				String stattime = Uploadhealth(account, "123456", data, device_id, "T14");
				// 血压()
				String bloodr = bloodpressure(account, "123456", stattime, device_id);
				// 心率
				String heartRate = Heartrate(account, "123456", stattime, device_id);
				// 血氧
				String bloodOxygen = Bloodoxygen(account, "123456", stattime, device_id);
				// 微循环
				String microcir = microcirculation(account, "123456", stattime, device_id);
				// 体检报告
				String amedical = Amedicalreport(account, "123456", stattime, device_id);
				String hrv = HRV(account, "123456", stattime, device_id);
				// 呼吸频率
				String respiration = respirationrate(account, "123456", stattime, device_id);
				// 高低压
				String[] bloodrArr = bloodr.split(",");
						String status = json.getString("status");//1校准  //0健康数据
						System.out.println("status:**************"+status);
						if(status.equals("1")){
							logger.info("心率+"+heartRate+">血氧:"+bloodOxygen+">微循环:"+microcir+">hrv:"+hrv+">呼吸频率:"+respiration+">高压:"+bloodrArr[0]+">低压:"+bloodrArr[1]);
							if(!heartRate.equals("0") && !bloodrArr[0].equals("0") && !bloodrArr[1].equals("0")){
								Healthdao h = new Healthdao();
								h=DataParsing.DataHealthdao(h, heartRate, bloodOxygen, microcir, hrv, respiration,bloodrArr[0],bloodrArr[1]);
								//波形图数据
								String waveform = json.getString("waveform");
								StringBuilder sb = new StringBuilder();
								byte[] fromBASE64 = Base64Utils.decodeFromString(waveform.trim());
								for (byte b : fromBASE64) {
									sb.append(b+128+",");
								}
								h.setWaveform(sb.toString());
								h.setUserId(user.getId());
								h.setCreatetime(new Date());
								if(healthdao!=null){
									//修改
									healthdaoService.updateHealthdaoByUserId(h);
									user.setCalibration(1);
									userService.update(user);
								}
								re.setData(h);
							}else{
								re.setMessage("此次学习并未检测到皮肤,学习失败!");
								re.setCode(350);
								return re;
							}
						}else{
							//波形图数据
							String waveform = json.getString("waveform");
							StringBuilder sb = new StringBuilder();
							byte[] fromBASE64 = Base64Utils.decodeFromString(waveform.trim());
							for (byte b : fromBASE64) {
								sb.append(b+128+",");
							}
							Healths healths = new Healths();
							healths.setPhone(user.getPhone());
							healths.setHrv(Integer.valueOf(hrv));
							healths.setHeartRate(Integer.valueOf(heartRate));;
							// 高压)  
							healths.setHighBloodPressure(Integer.valueOf(bloodrArr[0]));
							// 低压
							healths.setLowBloodPressure(Integer.valueOf(bloodrArr[1]));
							healths.setBloodOxygen(Integer.parseInt(bloodOxygen));
							healths.setMicrocirculation(Integer.parseInt(microcir));
							// 报告
							healths.setAmedicalreport(amedical);
							healths.setRespirationrate(Integer.valueOf(respiration));
							healths.setCreatetime(new Date());
							healths.setUserId(user.getId());
						
							healthsService.insertSelective(healths)	;
							
							Health h = healthService.getHealthByUserId(user.getId());
							
							Health health = new Health();
							//血压,心率
							health = DataParsing.bloodPressure(health, healthdao, heartRate, bloodrArr[0], bloodrArr[1]);
							// 报告
							health.setAmedicalreport(amedical);
							// 血氧
							health.setBloodOxygen(Integer.parseInt(bloodOxygen)<93?(int)(95+Math.random()*(99-95+1)):Integer.parseInt(bloodOxygen));
							// Hrv
							health=DataParsing.DataHrv(health, healthdao, hrv);
							// 微循环
							health=DataParsing.DataMicrocirculation(health, healthdao, microcir);
							//呼吸
							health=DataParsing.respirationrate(health, healthdao, respiration);
							//步数
							health.setStepWhen(h.getStepWhen()+json.getInt("stepWhen"));
							//卡里路
							health.setCarrieroad(h.getCarrieroad()+json.getInt("stepWhen")*2);
							
							health.setCreatetime(new Date());
							health.setUserId(user.getId());
							health.setPhone(user.getPhone());
							health.setWaveform(sb.toString());
							healthService.insertSelective(health);
							user.setCoordinate(json.getString("coordinate"));
							userService.update(user);
							//预警功能
							healthService.sendJpush(health);
						}
					re.setCode(200);
					re.setMessage("操作成功");
				}catch (Exception e) {
					e.printStackTrace();
					logger.info("数据解析出错");
					re.setMessage("数据采集失败,请检查传感器");
					re.setCode(400);
				}
				return re;
	}
	/**
	 * 硬件上传惊凡数据T15
	 * 
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public static void addT15Health(String data,User user,HealthdaoService healthdaoService) {
		
		
		String account = "mozistar" + String.valueOf(user.getId());
		String stattime = Uploadhealth(account, "123456", data, device_id, "T15");
		
		try{
			// 血压()
			String bloodr = bloodpressure(account, "123456", stattime, device_id);
			// 心率
			String heartRate = Heartrate(account, "123456", stattime, device_id);
			// 血氧
			String bloodOxygen = Bloodoxygen(account, "123456", stattime, device_id);
			// 微循环
			String microcir = microcirculation(account, "123456", stattime, device_id);
			// 体检报告
			String amedical = Amedicalreport(account, "123456", stattime, device_id);
			String hrv = HRV(account, "123456", stattime, device_id);
			// 呼吸频率
			String respiration = respirationrate(account, "123456", stattime, device_id);
			// 高低压
			String[] bloodrArr = bloodr.split(",");
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			
			
			
			Healthdao healthdao = new Healthdao();
			healthdao.setHighBloodPressure(Integer.parseInt(bloodrArr[0]));
			healthdao.setLowBloodPressure(Integer.parseInt(bloodrArr[1]));
			healthdao.setHeartRate(Integer.parseInt(heartRate));
			healthdao.setBloodOxygen(Integer.parseInt(bloodOxygen));
			healthdao.setMicrocirculation(Integer.parseInt(microcir));
			healthdao.setAmedicalreport(amedical);
			healthdao.setHrv(Integer.parseInt(hrv));
			healthdao.setRespirationrate(Integer.parseInt(respiration));
			healthdao.setCreatetime(new Date());
			healthdao.setUserId(user.getId());
			healthdao.setPhone(user.getPhone());
			
			Healthdao bean = healthdaoService.getHealthdaoByUserId(user.getId());
			
			if(bean!=null){
				healthdao.setId(bean.getId());
				healthdaoService.updateByPrimaryKeySelective(healthdao);
			}else{
				healthdaoService.insertSelective(healthdao);
			}
		}catch (Exception e) {
			logger.info("数据解析出错");
		}
	}

	public static JSONObject requesthealth(String service, String phone_num, String password, String start_time,
			String device_id) {
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("service", service);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		createMap.put("name", phone_num);
		createMap.put("secret", password);
		createMap.put("client_id", "0");
		createMap.put("device_id", device_id);
		createMap.put("start_time", start_time);
		createMap.put("period", "1h");
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		//logger.info("惊凡返回的数据》》》》》》》》》》》》》》"+httpOrgCreateTestRtn);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		// System.out.println("惊凡返回的数据》》》》》》》》》》》》》》"+jsonObject);
		return jsonObject;
	}

	public static String HAVrespirationrate(String service, String phone_num, String password, String start_time,
			String device_id) {
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("service", service);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		createMap.put("name", phone_num);
		createMap.put("secret", password);
		createMap.put("client_id", "0");
		createMap.put("device_id", device_id);
		createMap.put("start_time", start_time);
		createMap.put("period", "1h");
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		//logger.info("惊凡返回的数据》》》》》》》》》》》》》》"+httpOrgCreateTestRtn);
		return httpOrgCreateTestRtn;
	}

	/**
	 * @param phone_num
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String Uploadhealth(String phone_num, String password, String data, String device_id, 
			String type) {
		
		User u = new User();
		
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime = time.format(new Date());
		String httpOrgCreateTest = "https://api.jingfantech.com/V1.02/writeARecord";
		JSONObject createMap = new JSONObject();
		
		
		createMap.put("device_id", device_id);
		createMap.put("session_id", "0");
		createMap.put("client_id", "0");
		createMap.put("format", "bin");
		createMap.put("model_no", "bk");
		createMap.put("channel_id","mozistar");
		createMap.put("channel_secret","aTjaZcJFBnpk");
		createMap.put("start_time", starttime);
		createMap.put("name", phone_num);
		createMap.put("secret", password);
		
		
		String gender = "男";
		if (gender.equals("男")) {
			createMap.put("sex", "1");
		} else {
			createMap.put("sex", "0");
		}
		
		
		if (type.equals("T14")) {
			createMap.put("calibrate", "0");
		} else {
			createMap.put("calibrate", "1");
		}

		createMap.put("height", u.getHeight());
		createMap.put("weight", u.getWeight());
		createMap.put("age", u.getAge());
		JSONObject specjs = new JSONObject();
		specjs.put("origin", "4032");
		specjs.put("channel_len", "3072");
		createMap.put("spec", specjs);
		createMap.put("finished", "true");
		createMap.put("data", data);
		
		try {
			HttpClientUtil.doPost(httpOrgCreateTest, createMap.toString(), charset);
		} catch (Exception e) {
		}
		return starttime;
	}

	/**
	 * 血压
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String bloodpressure(String phone_num, String password, String start_time, String device_id)
			throws Exception {

		JSONObject sdbjs = null;
		JSONObject jsonObject = HealthtoolUtils.requesthealth(SBDP, phone_num, password, start_time, device_id);
		//logger.info(jsonObject.toString());		
		JSONObject sbdp_result = (JSONObject) jsonObject.get("sbdp_result");
		sdbjs = (JSONObject) sbdp_result.get(String.valueOf(sbdp_result.size()));
		return sdbjs.get("sbp_ave").toString() + "," + sdbjs.get("dbp_ave").toString();
	}

	/**
	 * 心率
	 * 
	 * @return
	 */
	public static String Heartrate(String phone_num, String password, String start_time, String device_id) {
		JSONObject jsonObject = HealthtoolUtils.requesthealth(HTRATE, phone_num, password, start_time, device_id);
		//logger.info(jsonObject.toString());
		JSONObject htrate_result = (JSONObject) jsonObject.get("htrate_result");
		JSONObject htratejs = (JSONObject) htrate_result.get(String.valueOf(htrate_result.size()));
		return htratejs.get("htrate_ave").toString();
	}

	/**
	 * 血氧
	 * 
	 * @return
	 */
	public static String Bloodoxygen(String phone_num, String password, String start_time, String device_id) {
		JSONObject jsonObject = HealthtoolUtils.requesthealth(SPO, phone_num, password, start_time, device_id);
		//logger.info(jsonObject.toString());
		JSONObject spo_result = (JSONObject) jsonObject.get("spo_result");
		JSONObject spojs = (JSONObject) spo_result.get(String.valueOf(spo_result.size()));
		return spojs.get("spo_ave").toString();
	}

	/**
	 * 微循环
	 * 
	 * @return
	 */
	public static String microcirculation(String phone_num, String password, String start_time, String device_id) {
		JSONObject jsonObject = HealthtoolUtils.requesthealth(BK, phone_num, password, start_time, device_id);
		//logger.info(jsonObject.toString());
		JSONObject bk_result = (JSONObject) jsonObject.get("bk_result");
		JSONObject bkjs = (JSONObject) bk_result.get(String.valueOf(bk_result.size()));
		return bkjs.get("bk_ave").toString();
	}

	/**
	 * 体检报告
	 * 
	 * @return
	 */
	public static String Amedicalreport(String phone_num, String password, String start_time, String device_id) {
		JSONObject jsonObject = HealthtoolUtils.requesthealth(REPORT, phone_num, password, start_time, device_id);
		//logger.info(jsonObject.toString());
		String report = (String) jsonObject.get("report");
		return report;
	}

	/**
	 * 呼吸頻率
	 * 
	 * @return
	 */
	public static String respirationrate(String phone_num, String password, String start_time, String device_id) {

		return HealthtoolUtils.HAVrespirationrate(RESPIRATIONRATE, phone_num, password, start_time, device_id);
	}

	/**
	 * HRV
	 * 
	 * @return
	 */
	public static String HRV(String phone_num, String password, String start_time, String device_id) {
		return HealthtoolUtils.HAVrespirationrate(HRV, phone_num, password, start_time, device_id);
	}

	// 注册发送验证码
	/**
	 * 发送验证码
	 * 
	 * @param validate_number
	 *            验证码
	 * @param phone_num
	 *            手机号码
	 * @param channel_id
	 *            惊凡提供
	 * @param channel_secret
	 *            惊凡提供
	 * @return
	 */
	public static boolean registeredcode(String phone_num, String num) {
		String url = "https://api.jingfantech.com/V1.02/register_step_1";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("validate_number", num);
		createMap.put("phone_num", phone_num);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Send successfully.")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 注册
	 * 
	 * @param validate_number
	 *            验证码
	 * @param phone_num
	 *            手机号码
	 * @param password
	 *            要设置的密码
	 * @param channel_id
	 *            惊凡提供
	 * @param channel_secret
	 *            惊凡提供
	 * @return
	 */
	public static boolean registered(String phone_num, String validate_number, String password) {
		String url = "https://api.jingfantech.com/V1.02/register_step_2";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("validate_number", validate_number);
		createMap.put("phone_num", phone_num);
		createMap.put("password", password);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Registered successfully.")
				|| jsonObject.get("message").equals("The user already exists and failed to register.")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 登录
	 * 
	 * @param phone_num
	 * @param password
	 * @param channel_id
	 * @param channel_secret
	 * @return
	 */
	public static boolean login(String phone_num, String password) {
		String url = "https://api.jingfantech.com/V1.02/login";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("phone_num", phone_num);
		createMap.put("password", password);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Login successfully.")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 找回密码发送验证码
	 * 
	 * @param phone_num
	 * @param password
	 * @param channel_id
	 * @param channel_secret
	 * @return
	 */
	public static boolean Getbackpassword(String phone_num, String num) {
		String url = "https://api.jingfantech.com/V1.02/retrieve_pw_1";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("validate_number", num);
		createMap.put("phone_num", phone_num);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Send successfully.")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 更新密码
	 * 
	 * @param new_password
	 * @param phone_num
	 * @param channel_id
	 * @param channel_secret
	 * @return
	 */
	public static boolean updatepassword(String new_password, String phone_num) {
		String url = "https://api.jingfantech.com/V1.02/retrieve_pw_2";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("new_password", new_password);
		createMap.put("phone_num", phone_num);
		createMap.put("channel_id", Managementconstant.channel_id);
		createMap.put("channel_secret", Managementconstant.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest, json, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Password update successfully.")) {
			return true;
		} else {
			return false;
		}

	}

}
