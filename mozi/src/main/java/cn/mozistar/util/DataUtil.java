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
	/*******************************************************首页数据******************************************************/
	/**
	 * 步数type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow stepWhenData(String name,String desc,int category,String unit,int lastestValue){
		DataRow map=healthyData(name,desc,category,unit,lastestValue,0);
		return map;
	}
	/**
	 * 心率type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow heartrateData(String name,String desc,int category,String unit,int lastestValue){
		int type=1;
		if(lastestValue<IConstants.heartHig && lastestValue>IConstants.heartLow){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * 血压type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow bloodData(String name,String desc,int category,String unit,int sbpAve,int dbpAve){
		int type=1;
		if(sbpAve<IConstants.bloodHig && sbpAve>IConstants.bloodLow){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,sbpAve+ "/"+ dbpAve,type);
		return map;
	}
	/**
	 *  湿度type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow humidityData(String name,String desc,int category,String unit,float lastestValue){
		int type=1;
		if(lastestValue>=IConstants.warmLow && lastestValue<IConstants.warmJust){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,"--",type);
	//	DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * 体温type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow temperatureData(String name,String desc,int category,String unit,float lastestValue){
		int type=1;
		if(lastestValue>=IConstants.warmLow && lastestValue<IConstants.warmJust){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,"--",type);
	//	DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * HRVtype=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow hrvData(String name,String desc,int category,String unit,int lastestValue,int age){
		int type=1;
		if(age>IConstants.childLow &&  age<IConstants.childHig){
			if(lastestValue>=IConstants.childHRVLow && lastestValue<=IConstants.childHRVHig){
				type=0;
			}
		}else if(age>IConstants.youthLow && age<IConstants.youthHig){
			if(lastestValue>=IConstants.youthHRVLow && lastestValue<=IConstants.youthHRVHig){
				type=0;
			}
		}else if(age>IConstants.elderlyLow && age<IConstants.elderlyHig){
			if(lastestValue>=IConstants.elderlyHRVLow && lastestValue<=IConstants.elderlyHRVHig){
				type=0;
			}
		}
		DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * 情绪type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow moodData(String name,String desc,int category,String unit,int lastestValue){
		int type=0;
		String value = "";
		if(lastestValue>100){
			value="良好";
		}else if(lastestValue>=50 && lastestValue<=100){
			value="波动";
		}else if(lastestValue<50){
			value="改变";
			type=1;
		}
		DataRow map=healthyData(name,desc,category,unit,value,type);
		return map;
	}
	/**
	 * 微循环type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow mocrocirculationData(String name,String desc,int category,String unit,int lastestValue){
		int type=1;
		if(lastestValue>=IConstants.microcirculationLow){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * 血氧type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow qxygenData(String name,String desc,int category,String unit,int lastestValue){
		int type=1;
		if(lastestValue>IConstants.bloodOxygenLow && lastestValue<IConstants.bloodOxygenHig){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	/**
	 * 卡路里type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow carrieroadData(String name,String desc,int category,String unit,int lastestValue){
		DataRow map=healthyData(name,desc,category,unit,lastestValue,0);
		return map;
	}
	/**
	 * 呼吸type=0(正常) type=1(异常)
	 * @return
	 */
	public static DataRow breatheData(String name,String desc,int category,String unit,int lastestValue){
		int type=1;
		if(lastestValue>=IConstants.respirationrateLow && lastestValue<=IConstants.respirationrateHig){
			type=0;
		}
		DataRow map=healthyData(name,desc,category,unit,lastestValue,type);
		return map;
	}
	public static DataRow healthyData(String name,String desc,int category,String unit,Object lastestValue,int type){
		DataRow map = new DataRow();
		map.put("name", name);
		map.put("desc", desc);
		map.put("category", category);
		map.put("unit", unit);
		map.put("lastestValue", String.valueOf(lastestValue));
		map.put("type", type);
		return map;
	}
}
