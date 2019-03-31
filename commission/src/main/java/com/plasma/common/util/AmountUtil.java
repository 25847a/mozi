package com.plasma.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 金额计算工具类
 * @author wangjing
 *
 */
public class AmountUtil {
	  // 默认除法运算精度
	  private static final int DEF_DIV_SCALE = 3;
	  /**
		 * 　* 提供精确的加法运算。 　* @param v1 被加数 　* @param v2 加数 　* @return 两个参数的和
		 */
		public static double add(Object v1,Object v2){
			BigDecimal b1 = new BigDecimal(v1.toString());
			BigDecimal b2 = new BigDecimal(v2.toString());
			return b1.add(b2).doubleValue();
		}
		public static double add(double v1,double v2){
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.add(b2).doubleValue();
		}
		/**
		 * 　* 提供精确的减法运算。 　* @param v1 被减数 　* @param v2 减数 　* @return 两个参数的差 　
		 */
		public static double sub(Object v1, Object v2) {
			BigDecimal b1 = new BigDecimal(v1.toString());
			BigDecimal b2 = new BigDecimal(v2.toString());
			return b1.subtract(b2).doubleValue();
		}
		public static double sub(double v1,double v2){
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.subtract(b2).doubleValue();
		}
		/**
		 * 提供精确的乘法运算。
		 * 
		 * @param v1
		 *            被乘数
		 * @param v2
		 *            乘数
		 * @return 两个参数的积
		 */
		public static double mul(Object v1,Object v2) {
			BigDecimal b1 = new BigDecimal(v1.toString());
			BigDecimal b2 = new BigDecimal(v2.toString());
			return b1.multiply(b2).doubleValue();
		}
		public static double mul(double v1,double v2){
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.subtract(b2).doubleValue();
		}
		/**
		 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * @param v2
		 *            除数
		 * @return 两个参数的商
		 */

		public static double div(Object v1, Object v2) {
			return div(v1, v2, DEF_DIV_SCALE);
		}
		public static double div(double v1, double v2) {
			return div(v1, v2, DEF_DIV_SCALE);
		}

		/**
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * @param v2
		 *            除数
		 * @param scale
		 *            表示表示需要精确到小数点以后几位。
		 * @return 两个参数的商
		 */
		public static double div(Object v1, Object v2, int scale) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b1 = new BigDecimal(v1.toString());
			BigDecimal b2 = new BigDecimal(v2.toString());
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		public static double div(double v1, double v2, int scale) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		/**
		 * 提供精确的小数位四舍五入处理。
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * @param scale
		 *            小数点后保留几位
		 * @return 四舍五入后的结果
		 */
		public static double round(double v, int scale) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		/**
		 * 金额判断
		 */
		public static boolean moneySize(Object v1,Object v2,char operator){
			if(v2==null){
				v2=0;
			}
			if(v1==null){
				v1=0;
			}
			boolean flag =true;
			switch (operator) {
			case '>':
				if(Double.valueOf(v1.toString())<Double.valueOf(v2.toString())){
					flag= false;
				}
				break;
			case '<':
				if(Double.valueOf(v1.toString())>Double.valueOf(v2.toString())){
					flag= false;
				}
				break;
			default:
				break;
			}
			return flag;
		}

		
		
		
		
		
		
		  //格式化保留两位数
		  private DecimalFormat df_two = new DecimalFormat("#0.00");
		  //格式化保留4位数
		  private DecimalFormat df_four = new DecimalFormat("#0.0000");
		  //日期格式化
		  private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		  //月利率
		  private double monthRate = 0;
		  //月还利息
		  private double monPayRate = 0;
		  //月还本金
		  private double monPayAmount = 0;
		  //到期还款本息总额
		  private double totalAmount = 0;
		  //月还本息
		  private double totalSum = 0;
		  //本金余额
		  private double principalBalance = 0;
		  //利息余额
		  private double interestBalance = 0;
		  //总利息
		  private double totalInterest = 0;
		  //当前时间
		  private Date currTime;
		  //剩余本金
		  private double stillAmount = 0;
		  //月还款
		  private double monPay = 0;
		  //所借本金
		  private double amount = 0;
		  //本息余额
		  private double payRemain = 0;
		  //本金
		  private double payAmount = 0;

		  //获取日期
		  private Date date = new Date();
		  //返回的结果集
		  private List<Map<String,Object>> mapList = null;
		  //map记录
		  private Map<String,Object> map = null;
		
		
		
		
		/**   
		 * @MethodName: rateCalculateMonth
		 * msg 收益消息
		 * realAmount 实际投资金额
		 * totalInterest 收益利息
		 * rewardSum 收益奖励 
		 * iManageFee 投资管理费  
		 * repayPeriod 还款期数
		 * repayDate   还款日期
		 * stillPrincipal  应还本金
		 * stillInterest   应还利息
		 * principalBalance 本金余额
		 * interestBalance  利息余额
		 * mRate   月利率
		 * totalSum  本息余额
		 * totalAmount  还款总额
		 * @Param: AmountUtil  
		 * @Author: 
		 * @Date: 2013-4-20 下午09:01:34
		 * @Return:    
		 * @Descb: 按月等额还款
		 * @Throws:
		*/
		public List<Map<String,Object>> rateCalculateMonth(double borrowSum,double yearRate,int deadline,int isDayThe){
			  mapList = new ArrayList<Map<String,Object>>();
			  monthRate = Double.valueOf(yearRate*0.01)/12.0;
			  if(isDayThe == 1){
				  //月标
				   monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
				   monPay = Double.valueOf(df_two.format(monPay));
				   amount = borrowSum;
				   monPayRate = 0;
				   monPayAmount = 0;
				   totalAmount = monPay*deadline;
				   payRemain = Double.valueOf(df_two.format(totalAmount));
				   for(int n=1;n<=deadline;n++){
					   map = new HashMap<String,Object>();
					   currTime = add(date,Calendar.MONTH,n);
					   monPayRate = Double.valueOf(df_two.format(amount*monthRate));
					   monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
					   amount = Double.valueOf(df_two.format(amount-monPayAmount));
					   
					   if(n == deadline){
						   monPay =payRemain;
						   monPayAmount = borrowSum - payAmount;
						   monPayRate = monPay - monPayAmount;
					   }
					   payAmount += monPayAmount;
					   payRemain = Double.valueOf(df_two.format(payRemain-monPay));
					   principalBalance = amount;
					   interestBalance = Double.valueOf(df_two.format(payRemain - principalBalance));
					   if(n == deadline){
						   payRemain = 0;
						   principalBalance = 0;
						   interestBalance = 0;
					   }
					   totalSum =monPayAmount +monPayRate;
					   map.put("repayPeriod", n+"/"+deadline);
					   map.put("repayDate", sf.format(currTime));
					   map.put("stillPrincipal", df_two.format(monPayAmount));
				       map.put("principalBalance", df_two.format(principalBalance));
					   map.put("interestBalance", df_two.format(interestBalance));
					   map.put("stillInterest", df_two.format(monPayRate));
					   map.put("mRate", df_four.format(monthRate*100));
					   map.put("totalSum", df_two.format(totalSum));
					   map.put("totalAmount", df_two.format(totalAmount));
					   mapList.add(map); 
				   }
			  }else{
				  //天标
				  map = new HashMap<String,Object>();
				  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
				  totalInterest = (monPayRate*deadline)/30.0;
				  currTime = add(date,Calendar.DATE,deadline);
				  totalAmount = borrowSum + totalInterest;
				  map.put("repayPeriod", "1/1");
				  map.put("repayDate", sf.format(currTime));
				  map.put("stillPrincipal", df_two.format(borrowSum));
				  map.put("stillInterest", df_two.format(totalInterest));
				  map.put("principalBalance", 0);
				  map.put("interestBalance", 0);
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalAmount));
				  map.put("totalAmount", df_two.format(totalAmount));
				  mapList.add(map);
			  }
			return mapList;
		}
		
		/**   
		 * @MethodName: rateCalculateSum
		 * repayPeriod 还款期数
		 * repayDate   还款日期
		 * stillPrincipal  应还本金
		 * stillInterest   应还利息
		 * principalBalance 本金余额
		 * interestBalance  利息余额
		 * mRate   月利率
		 * totalSum  本息余额
		 * totalAmount  还款总额  
		 * @Param: AmountUtil  
		 * @Author: 
		 * @Date: 2013-4-20 下午06:21:20
		 * @Return:
		 * @Descb: 按月先息后本
		 * @Throws:
		*/
		public List<Map<String,Object>> rateCalculateSum(double borrowSum,double yearRate,int deadline,int isDayThe){
			  mapList = new ArrayList<Map<String,Object>>();
			  monthRate = (yearRate*0.01)/12;
			  if(isDayThe == 1){
				  //月标
				  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
				  totalInterest = monPayRate*deadline;
				  interestBalance = totalInterest;
				  for(int n = 1;n<=deadline;n++){
					  map = new HashMap<String,Object>();
					  currTime = add(date,Calendar.MONTH,n);
					  if(n == deadline){
						  stillAmount = borrowSum;
						  map.put("stillPrincipal", df_two.format(borrowSum));
						  map.put("principalBalance", 0);
						  map.put("interestBalance", 0);
					  }else{
						  interestBalance = interestBalance- monPayRate;
				          map.put("stillPrincipal", 0);
				          map.put("principalBalance", df_two.format(borrowSum));
						  map.put("interestBalance", df_two.format(interestBalance));
					  }
					  totalSum = stillAmount + monPayRate;
					  totalAmount = borrowSum + totalInterest;
					  map.put("repayPeriod", n+"/"+deadline);
					  map.put("repayDate", sf.format(currTime));
					  map.put("stillInterest", df_two.format(monPayRate));
					  map.put("mRate", df_four.format(monthRate*100));
					  map.put("totalSum", df_two.format(totalSum));
					  map.put("totalAmount", df_two.format(totalAmount));
					  mapList.add(map);
				  }
			  }else{
				  map = new HashMap<String,Object>();
				  //天标
				  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
				  totalInterest = (monPayRate*deadline)/30.0;
				  currTime = add(date,Calendar.DATE,deadline);
				  totalAmount = borrowSum + totalInterest;
				  map.put("repayPeriod", "1/1");
				  map.put("repayDate", sf.format(currTime));
				  map.put("stillPrincipal", df_two.format(borrowSum));
				  map.put("stillInterest", df_two.format(totalInterest));
				  map.put("principalBalance", 0);
				  map.put("interestBalance", 0);
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalAmount));
				  map.put("totalAmount", df_two.format(totalAmount));
				  mapList.add(map);
			  }
			  return mapList;
		  }
		
		/**   
		 * @MethodName: rateSecondsSum  
		 * repayPeriod 还款期数
		 * repayDate   还款日期
		 * stillPrincipal  应还本金
		 * stillInterest   应还利息
		 * principalBalance 本金余额
		 * interestBalance  利息余额
		 * mRate   月利率
		 * totalSum  本息余额
		 * totalAmount  还款总额
		 * @Param: AmountUtil  
		 * @Author: 
		 * @Date: 2013-4-20 下午09:42:34
		 * @Return:    
		 * @Descb: 秒还借款
		 * @Throws:
		*/
		public List<Map<String,Object>> rateSecondsSum(double borrowSum,double yearRate,int deadline,int isDayThe){
			  mapList = new ArrayList<Map<String,Object>>();
			  map = new HashMap<String,Object>();
			  monthRate = Double.valueOf(yearRate*0.01)/12.0;
			  totalInterest = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  if(isDayThe == 1){
				  currTime = add(date,Calendar.MONTH,deadline);
				  map.put("repayDate", sf.format(currTime));
			  }else{
				  currTime = add(date,Calendar.DATE,deadline);
				  map.put("repayDate", sf.format(currTime));
			  }
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
			  return mapList;
		}	
		
		
		
		
		/**   
		 * @MethodName: add  
		 * @Param: AmountUtil  
		 * @Author: 
		 * @Date: 2013-4-20 下午05:15:18
		 * @Return:    
		 * @Descb: 日期累加
		 * @Throws:
		*/
		private static Date add(Date date,int type,int value){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(type, value);
				return calendar.getTime();
		 }
}