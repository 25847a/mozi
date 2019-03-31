package cn.mozistar.util;

import java.text.ParseException;

public class Teset {
	public static void main(String[] args) throws ParseException {
		
	     //SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
	    // Date date=sDateFormat.parse("2019-01-09");
	    // System.out.println(date);
		
		
		JpushClientUtil.sendToAlias("28", "推送测试","推送测试", "11", "11", "11");
	}
}
