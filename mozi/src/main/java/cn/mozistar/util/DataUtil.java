package cn.mozistar.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP 二级页面封装
 * @author Administrator
 *
 */
public class DataUtil {
	
	
	/**
	 * 二级页面的聚合函数值
	 * @return
	 */
	public static List<Map<String,Object>> polymerization(String max,String maxtime,String min,String mintime,String nw,String avg, Map<String,String> map){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();	
		Map<String,Object> detail = new HashMap<String,Object>();
		detail.put("detailId", "5");
		detail.put("name", max);
		detail.put("value", map.get("max"));
		detail.put("time", maxtime);
		list.add(detail);
		detail = new HashMap<String,Object>();
		detail.put("detailId", "10");
		detail.put("name", min);
		detail.put("value", map.get("min"));
		detail.put("time", mintime);
		list.add(detail);
		detail = new HashMap<String,Object>();
		detail.put("detailId", "15");
		detail.put("name", nw);
		detail.put("value", map.get("new"));
		detail.put("time", "");
		list.add(detail);
		detail = new HashMap<String,Object>();
		detail.put("detailId", "20");
		detail.put("name", avg);
		detail.put("value", map.get("avg"));
		detail.put("time", "");
		list.add(detail);
		return list;
	}
	/**
	 * 二级页面HRV的提示语
	 * @return
	 */
	public static String tipsHrv(Map<String,Object> m){
		String show="";
		if(((String) m.get("service")) .equals("day")){
			System.out.println("进来来>>>>>>>>>>>>>>11111111111111111111111");
			show="由于HRV受到诸如性别、年龄及环境等因素的影响，本标准值仅供参考：\n18岁-29岁\t25-120ms\t正常\n30岁-49岁\t27-69ms\t正常\n50岁-60岁\t22-59ms\t正常";
		}else if(((String) m.get("service")) .equals("week")){
			show=Managementconstant.HRV_WEEK;
		}else if(((String) m.get("service")) .equals("month")){
			show=Managementconstant.HRV_MONTH;
		}
		return show;
	}
	/**
	 * 二级页面心率的提示语
	 * @return
	 */
	public static String tipsHeartRate(Map<String,Object> m){
		String show="";
		if(((String) m.get("service")) .equals("day")){
			show=Managementconstant.HEARTRATE_DAY;
		}else if(((String) m.get("service")) .equals("week")){
			show=Managementconstant.HEARTRATE_WEEK;
		}else if(((String) m.get("service")) .equals("month")){
			show=Managementconstant.HEARTRATE_MONTH;
		}
		return show;
	}
	/**
	 * 二级页面血压的提示语
	 * @return
	 */
	public static String tipsBloodpressure(Map<String,Object> m){
		String show="";
		if(((String) m.get("service")) .equals("day")){
			show=Managementconstant.BLOODPRESSURE_DAY;
		}else if(((String) m.get("service")) .equals("week")){
			show=Managementconstant.BLOODPRESSURE_WEEK;
		}else if(((String) m.get("service")) .equals("month")){
			show=Managementconstant.BLOODPRESSURE_MONTH;
		}
		return show;
	}
	/**
	 * 二级页面步数的提示语
	 * @return
	 */
	public static String tipsStepWhen(Map<String,Object> m){
		String show="";
		if(((String) m.get("service")) .equals("day")){
			show=Managementconstant.STEPWHEN_DAY;
		}else if(((String) m.get("service")) .equals("week")){
			show=Managementconstant.STEPWHEN_WEEK;
		}else if(((String) m.get("service")) .equals("month")){
			show=Managementconstant.STEPWHEN_MONTH;
		}
		return show;
	}
}
