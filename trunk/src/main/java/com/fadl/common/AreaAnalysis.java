/*package com.fadl.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaAnalysis {
	public static void main(String[] args) {
		String place =
				//"海南省保亭黎族苗族自治县国营南茂农场一区加茂队";
				"湖南省道县道江镇道县自来水公司宿舍";
	            System.out.println(addressResolution(place));
	            
	}
	*//**
     * 合法区域解析地址
     * @param address
     * @return
     *//*
    public static Map<String,String> addressResolution(String address){
        String regex="((?<province>[^省]+省|.+自治区|上海|北京|天津|重庆|广西|新疆|西藏|宁夏|内蒙古))"
        		+ "(?<county>[^县]+市市|.+县市|.+市|.+市区|.+县区|.+区|.+县|.+旗)"
        		+ "?(?<town>[^区]+区|.+街镇|.+镇镇|.+乡镇|.+村镇|.+镇|.+镇乡|.+乡乡|.+街乡|.+村乡|.+乡"
        		+ "|.+街村|.+镇村|.+乡村|.+村村|.+村|.+街道|.+镇街|.+乡街|.+村街.+街街|.+街"
        		+ "|.+公司|.+农场|.+居委会|.+村委会|.+苏木)"
        		+ "?(?<village>.*)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,county=null,town=null,village=null;
        Map<String,String> row=null;
        int mIdx = 0;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            county=m.group("county");
            Matcher q =Pattern.compile(regex).matcher("道县");
            while(q.find()){
            	System.out.println(q.group());
                mIdx++;
                //当"/"符号第三次出现的位置
                if(mIdx == 1){
                    break;
                }
            }
            
            System.out.println(q.start());
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
        }
        return row;
    }
}*/

package com.fadl.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaAnalysis {
	public static void main(String[] args) {
		String place = "湖南省道县道江镇道县自来水宿舍";
	            System.out.println(addressResolution(place));
	}
	/**
     * 合法区域解析地址
     * @param address
     * @return
     */
    public static Map<String,String> addressResolution(String address){
        String regex="((?<province>[^省]+省|.+自治区|上海|北京|天津|重庆|广西|新疆|西藏|宁夏|内蒙古))"
        		+ "(?<county>[^县]+县市|.+县区|.+市市|.+市区|.+县|.+市|.+区|.+旗)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,county=null,town=null,village=null;
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
          province=m.group("province");
        row.put("province", province==null?"":province.trim());
            county=m.group("county");
            int count  = 0;
            for(int i=0;i<county.length();i++) {
            	if("县".equals(String.valueOf(county.charAt(i)))) {
            		count++;
            	}
            }
            Matcher mat =null;
           if(count>=2) {
        	   for(int i=0;i<county.length();i++) {
               	char str = county.charAt(i);
               	if("县".equals(String.valueOf(str))) {
               		String off = county.substring(0, i+1);
               		 row.put("county", off.trim());
               		 break;
               	}
               }
        	   String off="";
        	   for(int j=0;j<address.length();j++) {
                  	char str = address.charAt(j);
                  	if("县".equals(String.valueOf(str))) {
                  		off = address.substring(j+1, address.length());
                  		break;
                  	}
                  }
        	   String regexx ="(?<town>[^镇]+镇|.+街道|.+街街|.+街镇|.+街乡|.+街村|.+镇街|.+镇镇|.+镇乡|.+镇村"
        	        		+ "|.+乡街|.+乡镇|.+乡乡|.+乡村|.+村街|.+村镇|.+村乡|.+村村|.+区|.+街|.+乡|.+村|.+苏木)"
        	        		+ "(?<village>.*)";
        	   mat = Pattern.compile(regexx).matcher(off);
           }else {
        	   row.put("county", county==null?"":county.trim());
        	   String regexx = regex+"?(?<town>[^镇]+镇|.+街道|.+街街|.+街镇|.+街乡|.+街村|.+镇街|.+镇镇|.+镇乡|.+镇村"
   	        		+ "|.+乡街|.+乡镇|.+乡乡|.+乡村|.+村街|.+村镇|.+村乡|.+村村|.+区|.+街|.+乡|.+村|.+苏木)"
   	        		+ "?(?<village>.*)";
        	   mat = Pattern.compile(regexx).matcher(address);
           }
           while(mat.find()) {
    		   town=mat.group("town");
               row.put("town", town==null?"":town.trim());
               village=mat.group("village");
               row.put("village", village==null?"":village.trim());
    	   }
        }
        return row;
    }
}

