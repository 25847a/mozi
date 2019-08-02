package cn.mozistar.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.mozistar.pojo.User;
import cn.mozistar.service.HealthService;
import cn.mozistar.service.UserService;
import cn.mozistar.util.DataRow;
import cn.mozistar.util.DataUtil;
import cn.mozistar.util.DateUtil;
import cn.mozistar.util.ResultData;
import cn.mozistar.vo.Chart;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/health")
public class HealthController {
	
	
	@Autowired
	private HealthService healthservice;
	@Autowired
	UserService userService;
	
	
	/**
	 * 获取步数数据 （根据年月日 周）查找
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/selectStepWhen")
	@ResponseBody
	public ResultData<Map<String,Object>> selectStepWhen(@RequestBody Map<String,Object> m) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("categoryId", "3");
		data.put("name", "stepWhen");
		data.put("desc", "步数");
		ResultData<Map<String,Object>> re = new ResultData<Map<String,Object>>();
		
		List<DataRow> chart = healthservice.queryHealthstep(m);
		if (chart != null && chart.size() > 0) {
		List<Map<String,Object>> bloodpressureList = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < chart.size(); i++) {
				DataRow j = chart.get(i);
				Map<String,Object> chartData = new HashMap<String,Object>();
				int[] stepWhen = new int[1];
				stepWhen[0] = j.getInt("stepWhen");
				chartData.put("value", stepWhen);
				chartData.put("createtime", j.getString("date"));
				chartData.put("updateTime",  j.getString("date"));
				bloodpressureList.add(chartData);
			}
			Map<String,String> map = healthservice.selectStepWhenInfo(m);
			List<Map<String,Object>> list = DataUtil.polymerization("步数最高",map.get("maxtime"),"步数最低",map.get("mintime"),"最新步数","平均步数",map);	
			data.put("detail", list);
			data.put("kilometre", map.get("kilometre"));
			data.put("createtime", map.get("createtime"));
			data.put("userId", m.get("userId"));
			data.put("show", DataUtil.tipsStepWhen(m));
			data.put("chartData", bloodpressureList);
			User user2 = userService.getUser(Integer.valueOf(String.valueOf(m.get("userId"))));
			data.put("stepNumber", user2.getWalkCount());
			re.setCode(200);
			re.setData(data);
			re.setMessage("获取步数健康数据成功！！！");
		} else {
			re.setCode(400);
			re.setMessage("没有健康数据！！！");
		}
		return re;
	}
	
	/**
	 * 获取血压数据 （根据年月日 周）查找
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
				chartData.put("createtime", j.getDate());
				chartData.put("updateTime", j.getDate());
				bloodpressureList.add(chartData);
			}
			Map<String,String> map = healthservice.selectBloodpressureInfo(m);
			if(map==null){
				map = new HashMap<String,String>();
				map.put("max", "0/0");
				map.put("min", "0/0");
				map.put("count", "0");
				map.put("avg", "0/0");
				map.put("new", "0/0");
				map.put("createtime", DateUtil.sfshi.format(new Date()));
			}
			List<Map<String,Object>> list = DataUtil.polymerization("最高血压","","最低血压","","最新血压","平均血压",map);	
			data.put("detail", list);
			data.put("chartData", bloodpressureList);
			data.put("createtime", map.get("createtime"));
			data.put("count", map.get("count"));
			data.put("show", DataUtil.tipsBloodpressure(m));
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
				chartData.put("createtime", j.getDate());
				chartData.put("updateTime",  j.getDate());
				bloodpressureList.add(chartData);
			}
			Map<String,String> map = healthservice.selectHeartRateInfo(m);
			if(map==null){
				map = new HashMap<String,String>();
				map.put("max", "0");
				map.put("min", "0");
				map.put("count","0");
				map.put("avg", "0");
				map.put("new", "0");
				map.put("createtime", DateUtil.sfshi.format(new Date()));
			}
			List<Map<String,Object>> list = DataUtil.polymerization("最快心率","","最慢心率","","最新心率","平均心率",map);	
			data.put("detail", list);
			data.put("count", map.get("count"));
			data.put("createtime", map.get("createtime"));
			data.put("userId", map.get("userId"));
			data.put("show", DataUtil.tipsHeartRate(m));
			data.put("chartData", bloodpressureList);
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
					chartData.put("createtime", j.getDate());
					chartData.put("updateTime",  j.getDate());
					bloodpressureList.add(chartData);
				}
				Map<String,String> map =healthservice.selectHrvInfo(m);
				if(map==null){
					map = new HashMap<String,String>();
					map.put("max", "0");
					map.put("min", "0");
					map.put("count","0");
					map.put("avg", "0");
					map.put("new", "0");
					map.put("createtime", DateUtil.sfshi.format(new Date()));
				}
				List<Map<String,Object>> list = DataUtil.polymerization("最高HRV","","最低HRV","","最新HRV","平均HRV",map);	
				data.put("detail", list);
				data.put("chartData", bloodpressureList);
				data.put("count", map.get("count"));
				data.put("createtime", map.get("createtime"));
				data.put("userId", map.get("userId"));
				data.put("show", DataUtil.tipsHrv(m));
				re.setCode(200);
				re.setData(data);
				re.setMessage("获取心率变异性数据成功！！！");
			} else {
				re.setCode(400);
				re.setMessage("没有心率变异性数据！！！");
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
					chartData.put("createtime",j.getDate());
					chartData.put("updateTime",  j.getDate());
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
