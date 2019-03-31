package cn.mozistar.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mozistar.pojo.Health;
import cn.mozistar.service.HealthService;
import cn.mozistar.util.ResultData;
import cn.mozistar.vo.Chart;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/health")
public class HealthController {
	
	private  DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
	//private static String mozistar = "mozistar";
	
	@Autowired
	private HealthService healthservice;
	

	/**
	 * 1获取血压数据 （根据年月日 周）查找
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/bloodpressure")
	@ResponseBody
	public ResultData<Map<String,Object>> bloodpressure(@RequestBody JSONObject m) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("categoryId", "1");
		data.put("name", "pressure");
		data.put("desc", "血压");
		ResultData<Map<String,Object>> re = new ResultData<Map<String,Object>>();
		String service = (String) m.get("service");
		String timedata = (String) m.get("timedata");
		Integer userId =  m.getInt("userId");
		Map<String,Object> map = new HashMap<String,Object>();
		String[] timedatas = null;
		if (!service.equals("week")) {//把2018-04-11分成3个数组
			timedatas = timedata.split("-");
		}
		//日
		if (service.equals("day")) {
			map.put("month", timedatas[1]);//04
			map.put("timedata", timedatas[2]);//11
			map.put("year", timedatas[0]);//2018
			map.put("keyWord", "day");
			// 月
		} else if (service.equals("month")) {
			map.put("timedata", timedatas[1]);
			map.put("year", timedatas[0]);
			map.put("keyWord", "month");
			// 年
		} else if (service.equals("year")) {
			map.put("timedata", timedatas[0]);
			map.put("keyWord", "year");
		} else if (service.equals("week")) {
			map.put("keyWord", "week");
		}
		map.put("userId", userId);//把用户手机放入Map
		m.put("userId",  userId);//把用户手机放入Map
		Health bloodpressureMax = healthservice.selecthealthMax(map);//获取时间下最大血压
		Health bloodpressureMin = healthservice.selecthealthMin(map);//获取时间下最小血压
		Health jfhealth = healthservice.getHealthByUserId(userId);
		if(bloodpressureMax != null && bloodpressureMin !=null && null!=jfhealth) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();	
			Map<String,Object> detail = new HashMap<String,Object>();
			detail.put("detailId", "5");
			detail.put("name", "血压最高值");
			detail.put("value", bloodpressureMax.getHighBloodPressure()+"/"+bloodpressureMax.getLowBloodPressure());
			detail.put("updateTime", format.format(bloodpressureMax.getCreatetime()));
			list.add(detail);
			detail = new HashMap<String,Object>();
			detail.put("detailId", "10");
			detail.put("name", "血压最低值");
			detail.put("value", bloodpressureMin.getHighBloodPressure()+"/"+bloodpressureMin.getLowBloodPressure());
			detail.put("updateTime", format.format(bloodpressureMax.getCreatetime()));
			list.add(detail);
			detail = new HashMap<String,Object>();
			detail.put("detailId", "15");
			detail.put("name", "血压");
			detail.put("value", jfhealth.getHighBloodPressure()+"/"+jfhealth.getLowBloodPressure());
			detail.put("updateTime", format.format(jfhealth.getCreatetime()));
			list.add(detail);
			data.put("detail", list);
		}else {
			re.setCode(350);
			re.setMessage("暂无健康数据！！！");
			return re;
		}
		List<Chart> chart = healthservice.selecthealth(m);//查询用户2018-04-11健康数据
		if (chart != null && chart.size() > 0) {//判断非空
			 List<Map<String,Object>> bloodpressureList = new ArrayList<Map<String,Object>>();	
			for (int i = 0; i < chart.size(); i++) {
				Map<String,Object> chartData = new HashMap<String,Object>();
				Chart j = chart.get(i);
				int[] bloodpressure = new int[2];
				bloodpressure[0]=j.getHighBloodPressure();
				bloodpressure[1]=j.getLowBloodPressure();
				chartData.put("value", bloodpressure);
				chartData.put("createtime", format.format(j.getDate()));
				chartData.put("updateTime", format.format(j.getDate()));
				bloodpressureList.add(chartData);
			}
			data.put("chartData", bloodpressureList);
			data.put("h5url","http://120.76.201.150:8080/avatars/120.png");
			data.put("imageurl", "http://120.76.201.150:8080/avatars/bloodpressure.png");
			re.setCode(200);
			re.setData(data);
			re.setMessage("获取血压健康数据成功！！！");
		} else {
			re.setCode(350);
			re.setMessage("暂无健康数据！！！");
		}
		return re;
	}	
	
	/**
	 * 获取心率数据 （根据年月日 周）查找
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/selecthealth")
	@ResponseBody
	public ResultData<Map<String,Object>> selecthealth(@RequestBody Map<String,Object> m) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("categoryId", "2");
		data.put("name", "heartrate");
		data.put("desc", "心率");
		ResultData<Map<String,Object>> re = new ResultData<Map<String,Object>>();
		
		List<Chart> chart = healthservice.selecthealth(m);
		if (chart != null && chart.size() > 0) {
		List<Map<String,Object>> bloodpressureList = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < chart.size(); i++) {
				Chart j = chart.get(i);
				Map<String,Object> chartData = new HashMap<String,Object>();
				int[] bloodpressure = new int[1];
				bloodpressure[0] = j.getHeartRate();
				chartData.put("value", bloodpressure);
				chartData.put("createtime", format.format(j.getDate()));
				chartData.put("updateTime",  format.format(j.getDate()));
				bloodpressureList.add(chartData);
			}
			data.put("chartData", bloodpressureList);
			data.put("h5url","http://120.76.201.150:8080/avatars/120.png");
			data.put("imageurl","http://120.76.201.150:8080/avatars/health.png");
			re.setCode(200);
			re.setData(data);
			re.setMessage("获取心率健康数据成功！！！");
		} else {
			re.setCode(400);
			re.setMessage("没有健康数据！！！");
		}
		return re;
	}
	
	/**
	 * 获取心跳变异数据 （根据年月日 周）查找
	 * @param m
	 * @return
	 
	*/
	@RequestMapping(value="/selectHrv")
	@ResponseBody
	public ResultData<Map<String,Object>> selectHrv(@RequestBody Map<String,Object> m){
		Map<String,Object> data = new HashMap<String,Object>();
		//List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//Map<String,Object> detail = new HashMap<String,Object>();
		data.put("categoryId", "5");
		data.put("name", "hrv");
		data.put("desc", "心率变异性HRV");
		ResultData<Map<String,Object>> re = new ResultData<Map<String,Object>>();
		List<Chart> chart = healthservice.selecthealth(m);
		if (chart != null && chart.size() > 0) {
			List<Map<String,Object>> bloodpressureList = new ArrayList<Map<String,Object>>();
				for (int i = 0; i < chart.size(); i++) {
					Chart j = chart.get(i);
					Map<String,Object> chartData = new HashMap<String,Object>();
					int[] bloodpressure = new int[1];
					bloodpressure[0] = j.getHrv();
					chartData.put("value", bloodpressure);
					chartData.put("createtime", format.format(j.getDate()));
					chartData.put("updateTime",  format.format(j.getDate()));
					bloodpressureList.add(chartData);
				}
				data.put("chartData", bloodpressureList);
				re.setCode(200);
				re.setData(data);
				re.setMessage("获取心跳变异数据成功！！！");
			} else {
				re.setCode(400);
				re.setMessage("没有心跳变异数据！！！");
			}
			return re;
		
	} 
	/**
	 * 获取微循环数据 （根据年月日 周）查找
	 * @param m
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/selectMocrocirculation")
	@ResponseBody
	public ResultData<Map<String,Object>> selectMocrocirculation(@RequestBody Map<String,Object> m){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("categoryId", "6");
		data.put("name", "mocrocirculation");
		data.put("desc", "微循环");
		ResultData<Map<String,Object>> re = new ResultData<Map<String,Object>>();
		List<Chart> chart = healthservice.selecthealth(m);
		if (chart != null && chart.size() > 0) {
			List<Map<String,Object>> bloodpressureList = new ArrayList<Map<String,Object>>();
				for (int i = 0; i < chart.size(); i++) {
					Chart j = chart.get(i);
					Map<String,Object> chartData = new HashMap<String,Object>();
					int[] bloodpressure = new int[1];
					bloodpressure[0] = j.getMicrocirculation();
					chartData.put("value", bloodpressure);
					chartData.put("createtime",format.format(j.getDate()));
					chartData.put("updateTime",  format.format(j.getDate()));
					bloodpressureList.add(chartData);
	}
				data.put("chartData", bloodpressureList);
				re.setCode(200);
				re.setData(data);
				re.setMessage("获取微循环健康数据成功！！！");
			} else {
				re.setCode(400);
				re.setMessage("没有微循环健康数据！！！");
			}
			return re;
		
	}
}
