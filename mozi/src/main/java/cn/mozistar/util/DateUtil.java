package cn.mozistar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
/**
 * @author:jian
 * @Description:日期格式化
 * @Date:2019-03-27
 */
public class DateUtil {
	/**
     * 日期格式化
     */
    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sfx = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat sfDay = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat yearTwo = new SimpleDateFormat("yy");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdy = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM");
    public static SimpleDateFormat sfZero = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static SimpleDateFormat sfNine = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    public static SimpleDateFormat sfshi = new SimpleDateFormat("HH:mm");
    /** 日期格式(yyyy-MM-dd) */
    public static final String yyyy_MM_dd_EN = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    
    public static void main(String[] args) {
		System.out.println(sfshi.format(new Date()));
	}
    /**
     * 获取当前时间
     * @param d 需格式化的时间，不传为当前时间
     * @return
     */
    public static String getSystemTime(Date d){
        if(d==null){
            return sf.format(new Date());
        }else {
            return sf.format(d);
        }
    }
    /**
     * 获取当前时间
     * @param d 需格式化的时间，不传为当前时间不带空格
     * @return
     */
    public static String getSystemTimex(Date d){
        if(d==null){
            return sfx.format(new Date());
        }else {
            return sfx.format(d);
        }
    }
    /**
     * 获取当前日期
     * @param d 需格式化的日期，不传为当前日期
     * @return
     */
    public static String getSystemDate(Date d){
        if(d==null){
            return sfDay.format(new Date());
        }else {
            return sfDay.format(d);
        }
    }
    /**
     * 获取当前年份两位数
     * @param d 需格式化的日期，不传为当前日期
     * @return
     */
    public static String getSystemYearTwo(Date d){
        if(d==null){
            return yearTwo.format(new Date());
        }else {
            return yearTwo.format(d);
        }
    }
    /**
     * 数据day获取往后推算的时间
     * @param day
     * @return
     */
    public static Date getNextDay(int day){
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DAY_OF_MONTH, day);
    	date = cal.getTime();
		return date;
    	
    }
    @SuppressWarnings("deprecation")
	public static Date getSystemAddHours(int num) {
        Date date = new Date();
        date.setHours(date.getHours()+3);
        return date;
    }
    @SuppressWarnings("deprecation")
	public static Date getSystemAddMinute(int num) {
        Date date = new Date();
        date.setMinutes(date.getMinutes()+num);
        return date;
    }
    public static String formatDate(Date date, String formateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formateString);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return date.toString();
        }
    }
    
    /**
     * 格式化时间
     * @param time
     * @param formatStr
     * @return
     * @throws Exception
     */
    public static Date formatDate(String time,String formatStr) throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(time);
    }
    
    /**
     * 计算两个时间之间相差的天数
     * @param start
     * @param end
     * @return
     * @throws Exception 
     */
    public static long daysBetween(String start, String end) throws Exception {
    	Date one = formatDate(start,yyyy_MM_dd_EN);
    	Date two = formatDate(end,yyyy_MM_dd_EN);
    	long difference =  (two.getTime()-one.getTime())/86400000;  
        return difference; 
    }
    /**
     * 计算两时间相加的日期
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static String addDate(int start, String end) throws Exception{
    	long day = start*24 * 60 * 60 * 1000;
    	Date two =sfDay.parse(end);
    	Date date = new Date(day+two.getTime());
    	String time =sfDay.format(date);
		return time;
    }
    /**
     * 计算两个时间之间相差的天数
     * @param start
     * @param end
     * @return
     * @throws Exception 
     */
    public static long daysBetween(Date start, Date end) throws Exception {
    	//设置 开始时间
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(start);
    	LocalDate s = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    	//设置结束时间
    	calendar.setTime(end);
    	LocalDate e = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        long day = e.toEpochDay() - s.toEpochDay();
        return day;
    }
    
    /**
     * 计算年龄
     */
    public static int getAgeFromBirthTime(String birthdaya) throws Exception {
    	int age=0;
    	if (birthdaya != null&&!"".equals(birthdaya)) {
    		Date birthDay=sdf.parse(birthdaya);
        	Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) {
                throw new IllegalArgumentException("日期格式错误!");
            }
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);
     
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            //计算年龄
            age = yearNow - yearBirth;
            // 判断是否满周岁
            if (monthNow >= monthBirth ) {
            	if (dayOfMonthNow >= dayOfMonthBirth ) {

            	}else{
                	age--;
                }
            }else{
            	age--;
            }
    	}
    	return age;
    }
    /**
     * 计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
           return 0;
        }
    }
    /**
     * 时间加减
     * @param date
     * @param type
     * @param day
     * @return
     */
    public static Date addDate(Date date,int type,int day){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(type, day);
    	return cal.getTime();
    }
    
    /**
     * 根据传入的日期来返回有效的14天开始日期, 
     * 如果传入的日期超出了14天的有效期则会返回自身.
     * @param date
     * @return Date
     */
    public static Date getFourteenWithDate(Date date) {
    	// 得到一个当前日期往前14的日期
    	Calendar date14 = Calendar.getInstance();
    	date14.add(Calendar.DATE, -13);
    	// 先判断是否在14天内
    	if(date.after(date14.getTime())&& date.before(new Date())) {
    		return date14.getTime();
    	}else {
    		return date;
    	}
    }
    /**
     * 根据传入的日期来返回有效的14天开始日期, 
     * 如果传入的日期超出了14天的有效期则会返回自身.
     * @param date
     * @return String
     */
    public static String getStrFourteenWithDate(Date date) {
    	date = getFourteenWithDate(date);
    	return getSystemTime(date);
    }
    /**
     * 时间换算
     * @param num
     * @return
     */
    public static String getConversionDate(int num){
    	 	int time = num*5;
	        int hours = (int) Math.floor(time / 60);
	        int minute = time % 60;
	        return hours + "小时" + minute + "分钟";
    }
    /**得出两个时间差距的天数时分秒
	 * @param d1减数
	 * @param d2被减数
	 */
	public String operating(Date d1,Date d2){
		String text=null;
        try  
        {  
          long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
          long days = diff / (1000 * 60 * 60 * 24);  
       
          long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
          long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  
          System.out.println(""+days+"天"+hours+"小时"+minutes+"分");  
          text=""+days+"天"+hours+"小时"+minutes+"分";
        }catch (Exception e)  
        {  
        	return text;
        }
		return text;  
	}
	
	public long Minute(   Date d1 ,Date d2) throws ParseException{
		   long between=(d1.getTime()-d2.getTime())/1000;//除以1000是为了转换成秒
		   long min=between/60;
		   System.out.println(min);
		   return min;
	}
	
	/**根据分钟得出天数据小时分钟
	 * @param num
	 */
	public String  Minutecount(Integer num){
		int day = num/(24*60);
		int hour = (num%(24*60))/60;
		int minute = (num%(24*60))%60;
		
		System.out.println(day+"天"+hour+"小时"+minute+"分");
		return day+"天"+hour+"小时"+minute+"分";
	}
}
