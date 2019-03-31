package com.plasma.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateUtils {


    /** 日期格式(yyyy-MM-dd) */
    public static final String yyyy_MM_dd_EN = "yyyy-MM-dd";

    /** 日期格式(yyyyMMdd) */
    public static final String yyyyMMdd_EN = "yyyyMMdd";

    /** 日期格式(yyyy-MM) */
    public static final String yyyy_MM_EN = "yyyy-MM";

    /** 日期格式(yyyyMM) */
    public static final String yyyyMM_EN = "yyyyMM";

    /** 日期格式(yyyy-MM-dd HH:mm:ss) */
    public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyyMMddHHmmss";

    /** 日期格式(yyyyMMddHHmmss) */
    public static final String yyyyMMddHHmmss_EN = "yyyyMMddHHmmss";

    /** 日期格式(yyyy年MM月dd日) */
    public static final String yyyy_MM_dd_CN = "yyyy年MM月dd日";

    /** 日期格式(yyyy年MM月dd日HH时mm分ss秒) */
    public static final String yyyy_MM_dd_HH_mm_ss_CN = "yyyy年MM月dd日HH时mm分ss秒";

    /** 日期格式(yyyy年MM月dd日HH时mm分) */
    public static final String yyyy_MM_dd_HH_mm_CN = "yyyy年MM月dd日HH时mm分";

    /** DateFormat缓存 */
    private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();
    
    public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static final DateFormat YYYY = new SimpleDateFormat("yyyy");
    
    /**
     * String 转Date 后格式化再转String 
     * @param formatStr
     * @return
     * @throws Exception
     */
    public static String getStringFormatString(String formatStr) throws Exception{
		Date date = YYYYMMDDHHMMSS.parse(formatStr);
		String strTime =DateUtils.YYYYMMDDHHMMSS.format(date);
		return strTime;
    }
    /**
     * 获取DateFormat
     *
     * @param formatStr
     * @return
     */
    public static DateFormat getDateFormat(String formatStr) {
        DateFormat df = dateFormatMap.get(formatStr);
        if (df == null) {
            df = new SimpleDateFormat(formatStr);
            dateFormatMap.put(formatStr, df);
        }
        return df;
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
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr == null || dateTimeStr.equals("")) {
                return null;
            }
            DateFormat sdf = DateUtils.getDateFormat(formatStr);
            Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转化dateTimeStr为Date类型
     *
     * @param dateTimeStr
     * @return
     */
    public static Date convertDate(String dateTimeStr) {
        try {
            if (dateTimeStr == null || dateTimeStr.equals("")) {
                return null;
            }
            DateFormat sdf = DateUtils.getDateFormat(yyyy_MM_dd_EN);
            Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照默认显示日期时间的格式"yyyy-MM-dd"，转化dateTimeStr为Date类型
     * dateTimeStr必须是"yyyy-MM-dd"的形式
     *
     * @param dateTimeStr
     * @return
     */
    public static Date getDate(String dateTimeStr) {
        return getDate(dateTimeStr, yyyy_MM_dd_EN);
    }

    /**
     * 将YYYYMMDD转换成Date日期
     *
     * @param date
     * @return
     */
    public static Date transferDate(String date) throws Exception {
        if (date == null || date.length() < 1)
            return null;

        if (date.length() != 8)
            throw new Exception("日期格式错误");
        String con = "-";

        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);

        int month = Integer.parseInt(mm);
        int day = Integer.parseInt(dd);
        if (month < 1 || month > 12 || day < 1 || day > 31)
            throw new Exception("日期格式错误");

        String str = yyyy + con + mm + con + dd;
        return DateUtils.getDate(str, DateUtils.yyyy_MM_dd_EN);
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, yyyy_MM_dd_HH_mm_ss_EN);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 将String转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr1
     * @param formatStr2
     * @return
     */
    public static String stringToDateString(String date, String formatStr1, String formatStr2) {
        Date d = getDate(date, formatStr1);
        DateFormat df = getDateFormat(formatStr2);

        return df.format(d);
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     *
     * @return
     */
    public static String getCurDate() {
        return dateToDateString(new Date(), yyyy_MM_dd_EN);
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     *
     * @return
     */
    public static String getCurCNDate() {
        return dateToDateString(new Date(), yyyy_MM_dd_CN);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     *
     * @return
     */
    public static String getCurDateTime(String formatStr) {
        return dateToDateString(new Date(), formatStr);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return
     */
    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), yyyy_MM_dd_HH_mm_ss_CN);
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(String time1, String time2) {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     *
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取当前日期years年后的一个(formatStr)的字符串
     *
     * @param years
     * @param formatStr
     * @return
     */
    public static String getDateStringOfYear(int years, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.YEAR, years);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期mon月后的一个(formatStr)的字符串
     *
     * @param months
     * @param formatStr
     * @return
     */
    public static String getDateStringOfMon(int months, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MONTH, months);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期days天后的一个(formatStr)的字符串
     *
     * @param days
     * @param formatStr
     * @return
     */
    public static String getDateStringOfDay(int days, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.DATE, days);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期hours小时后的一个(formatStr)的字符串
     *
     * @param hours
     * @param formatStr
     * @return
     */
    public static String getDateStringOfHour(int hours, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.HOUR_OF_DAY, hours);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mon月后的一个(formatStr)的字符串
     *
     * @param date
     * @param mon
     * @param formatStr
     * @return
     */
    public static String getDateOfMon(String date, int mon, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtils.getDate(date, formatStr));
        now.add(Calendar.MONTH, mon);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期day天后的一个(formatStr)的字符串
     *
     * @param date
     * @param day
     * @param formatStr
     * @return
     */
    public static String getDateOfDay(String date, int day, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtils.getDate(date, formatStr));
        now.add(Calendar.DATE, day);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mins分钟后的一个(formatStr)的字符串
     *
     * @param date
     * @param mins
     * @param formatStr
     * @return
     */
    public static String getDateOfMin(String date, int mins, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtils.getDate(date, formatStr));
        now.add(Calendar.SECOND, mins * 60);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mins分钟后的一个日期
     *
     * @param date
     * @param mins
     * @return
     */
    public static Date getDateOfMin(Date date, int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        now.add(Calendar.SECOND, mins * 60);
        return now.getTime();
    }

    /**
     * 获取当前日期mins分钟后的一个(formatStr)的字符串
     *
     * @param mins
     * @param formatStr
     * @return
     */
    public static String getDateStringOfMin(int mins, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MINUTE, mins);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期mins分钟后的一个日期
     *
     * @param mins
     * @return
     */
    public static Date getDateOfMin(int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MINUTE, mins);
        return now.getTime();
    }

    /**
     * 获取当前日期sec秒后的一个(formatStr)的字符串
     *
     * @param sec
     * @param formatStr
     * @return
     */
    public static String getDateStringOfSec(int sec, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.SECOND, sec);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获得指定日期月份的天数
     *
     * @return
     */
    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获得系统当前月份的天数
     *
     * @return
     */
    public static int getCurentMonthDay() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    /**
     * 获得指定日期月份的天数 yyyy-mm-dd
     *
     * @return
     */
    public static int getMonthDay(String date) {
        Date strDate = getDate(date, yyyy_MM_dd_EN);
        return getMonthDay(strDate);
    }

    /**
     * 获取19xx,20xx形式的年
     *
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取月份，1-12月
     *
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取xxxx-xx-xx的日
     *
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取Date中的小时(24小时)
     *
     * @param d
     * @return
     */
    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取Date中的分钟
     *
     * @param d
     * @return
     */
    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取Date中的秒
     *
     * @param d
     * @return
     */
    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return dateToDateString(c.getTime(), yyyy_MM_dd_EN);
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return dateToDateString(c.getTime());
    }

    /**
     * 得到本周周(*)
     *
     * @return yyyy-MM-dd
     */
    public static String getDayOfThisWeek(int num) {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + num);
        return dateToDateString(c.getTime(), yyyy_MM_dd_EN);
    }

    /**
     * 得到本月指定天
     *
     * @return yyyy-MM-dd
     */
    public static String getDayOfThisMoon(String num) {
        String date = dateToDateString(new Date(), yyyy_MM_EN);
        date = date + "-" + num;
        return date;
    }

    /**
     * 得到当前星期数
     *
     */
    public static String getCurWeek() {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

        String s = DateUtils.getCurDate();
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        try {
            date = sdfInput.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
    }
    public static final String getDateTimeFileName()
    throws Exception
    {
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
     
         java.sql.Date tempDate = new java.sql.Date(System.currentTimeMillis());
         String fileName = format.format(tempDate);
     
         int value = (int)Math.round(Math.random() * 100.0D);
         if (value < 10) {
           value += 10;
         }
         value += 100;
         fileName = fileName + value;
    
         return fileName;
    }
    
    /**
     * 当月第一天
     * @return
     */
	public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
       return  df.format(gcLast.getTime());
    }
    
    /**
     * 当月最后一天
     * @return
     */
    public static String getLastDay() {
    	// 获取Calendar  
    	Calendar calendar = Calendar.getInstance();  
    	// 设置时间,当前时间不用设置  
    	// calendar.setTime(new Date());  
    	// 设置日期为本月最大日期  
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
    	// 打印  
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
    	return format.format(calendar.getTime());
    }
    
    /**
     * 得到上个月第一天
     * @return
     */
    public static String getPrevFirstDay(){
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         Calendar calendar = Calendar.getInstance();
         calendar.add(Calendar.MONTH, -1);
         Date theDate = calendar.getTime();
         GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
         gcLast.setTime(theDate);
         gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return  df.format(gcLast.getTime());
    }
    
    /**
     * 得到上个月最后一天
     * @return
     */
    public static String getPrevLastDay(){
    	// 获取Calendar  
    	Calendar calendar = Calendar.getInstance();  
    	// 设置时间,当前时间不用设置  
    	calendar.add(Calendar.MONTH, -1);  
    	// 设置日期为本月最大日期  
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
    	// 打印  
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
    	return format.format(calendar.getTime());
    }
    
    /**
     * 增加时间
     * @param date 时间
     * @param type 增加的类型（小时，分钟，天，月 等）
     * @param num 需要增加 的 数字
     * @return
     */
    public static Date addDate(Date date,int type,int num){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(type, num);
    	return calendar.getTime();
    }
    /**
     * 判断当前时间 在时时彩开奖的那个阶段
     * @param args
     */
    public static String getSSCLuckTime() throws Exception{
    		Integer res =Integer.valueOf(String.valueOf(getMin(new Date())).length()<=1?String.valueOf(getMin(new Date())):String.valueOf(getMin(new Date())).substring(1)); //分钟后一位数
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
        	 Date date = new Date();
        	 Long time =format.parse(format.format(date)).getTime(); //当前时间  时分秒 毫秒
        	 Long startDate=format.parse(getCurDate()+" 10:00:00.000").getTime(); //控制时间
        	 Long endDate = format.parse(getCurDate()+" 22:00:00.000").getTime();
        	 Long startDate1=format.parse(getCurDate()+" 01:55:00.000").getTime(); //控制时间
        	 //增加当前时间 分钟
        	 int minute=0;
        	if(time>=startDate&&time<endDate){
        		//10:00-22:00 满标 设置开奖时间
        		minute=14-res;
        	}else if(time>=startDate1&&time<startDate){
        		//1:55-10:00  时时彩不开奖  则添加时间为  10:03开奖
        		minute=-1;
        	}else{
        		//22:00-1:55  满标 设置开奖时间
        		if(res>=5){
        			minute=13-res;
        		}else{
        			minute=8-res;
        		}
        		
        	}
        	
        	if(minute!=-1){
        		return format.format(addDate(new Date(),Calendar.MINUTE,minute));
        	}else{
        		return getCurDate()+" 10:03:00.000";
        	}
    }
    
    /**
     * 计算两个时间之间相差的天数
     * @param start
     * @param end
     * @return
     */
    public static long daysBetween(String start, String end) {
    	Date one = getDate(start);
    	Date two = getDate(end);
    	long difference =  (one.getTime()-two.getTime())/86400000;  
        return Math.abs(difference); 
    } 
}
