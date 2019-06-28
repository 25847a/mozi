package cn.mozistar.configurer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SQL {
	static Connection con = null;
	public static void main(String[] args) {
		Connection MZ = null;
		try{
			MZ = Mozi();//加载墨子数据库;
		//	List<Map<String,String>> list =Query(MZ);
			//更新数据
		//	update(MZ, list);
			//插入数据
			insert(MZ);//惊凡数据表
		//	insertEQ(MZ);
			//insetequipmentdata(MZ, list);
			System.out.println("加载成功");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				MZ.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 * 出t02健康数据
	 * @throws SQLException 
	*/
	public static void 	insetequipmentdata(Connection ZX,List<Map<String,String>> list){
		Map<String,String> map = new HashMap<String, String>();
		try {
			for(int i=0;i<list.size();i++){
				map=list.get(i);
			  String sql = "insert into equipment_data ( user_id, heartrate, highpressure, bottompressure, bloodpressure,  mocrocirculation, breathe, sleeping,  step_when, carrieroad, sedentary,  movementstate, bodytemp, humidity, crash, createtime, qxygen, sleeping_s, run_s, step_time, step_each, hrv, mood)"+
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";       
			PreparedStatement ten = ZX.prepareStatement(sql);
			ten.setInt(1,118);
			ten.setInt(2,48);
			ten.setInt(3,52);
			ten.setInt(4,25);
			ten.setInt(5,64);
			ten.setInt(6,32);
			ten.setInt(7,35);
			ten.setDouble(8, Double.parseDouble("8.00"));
			ten.setInt(9,24);
			ten.setInt(10,12);
			ten.setString(11, "48");
			ten.setInt(12,1);
			ten.setInt(13,4);
			ten.setInt(14,34);
			ten.setInt(15,56);
			ten.setDate(16, new Date(System.currentTimeMillis()));
			ten.setInt(17,54);
			ten.setInt(18,6);
			ten.setInt(19,3);
			ten.setInt(20,46);
			ten.setInt(21,23);
			ten.setInt(22,3);
			ten.setInt(23,2);
			ten.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("插入失败"+e);
			
		}
		
	} 
	/** 
	 * 获取数据
	 * @param ZX
	 * @return
	 * @throws SQLException
	*/
		public static List<Map<String,String>> Query(Connection ZX){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			
			String sql = "SELECT * from health";
			Statement con;
			try {
				con = ZX.createStatement();
				ResultSet res = con.executeQuery(sql);
			
				while(res.next()){
					Map<String,String> map = new HashMap<String, String>();
					map.put("id", res.getString("id"));
					list.add(map);
			}
			} catch (SQLException e) {
				System.out.println("获取失败"+e);
			}
			return list;
			
		} 
	/**
	 * 更新数据
	 * @throws SQLException 
	*/
	public static void 	update(Connection ZX,List<Map<String,String>> list){
		Map<String,String> map = new HashMap<String, String>();
		try {
			for(int i=0;i<list.size();i++){
				map=list.get(i);
			  String sql = "UPDATE jfhealth SET HRV=?, sbp_ave=?, dbp_ave=?, Heartrate=?, Bloodoxygen=?, microcirculation=?,respirationrate=? WHERE id=?";       
			PreparedStatement ten = ZX.prepareStatement(sql);
			ten.setString(1,String.valueOf((int)(100+Math.random()*(140-100+1))));
			ten.setString(2,String.valueOf((int)(100+Math.random()*(180-100+1))));
			ten.setString(3,String.valueOf((int)(50+Math.random()*(80-50+1))));
			ten.setString(4,String.valueOf((int)(125+Math.random()*(18-125+1))));
			ten.setString(5,String.valueOf((int)(80+Math.random()*(100-80+1))));
			ten.setString(6,String.valueOf((int)(50+Math.random()*(150-50+1))));
			ten.setString(7,String.valueOf((int)(70+Math.random()*(85-70+1))));
			ten.setString(8,map.get("id"));
			ten.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("插入失败"+e);
			
		}
		
	} 
		/**
		 * 插入数据
		 * @throws SQLException 
		*/
		public static void 	insert(Connection ZX){
			boolean fag = true;
			String date = "2019-06-01 00:00:00";
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sf.parse(date));
				Calendar cd = Calendar.getInstance();   
				 cd.setTime(sf.parse(date));
				 while(fag) {
					 cd.add(Calendar.MINUTE, 5);
				String sql = "INSERT INTO health(phone,hrv,highBloodPressure,lowBloodPressure,heartRate,"
						+ "bloodOxygen,microcirculation,amedicalreport,respirationrate,stepWhen,carrieroad,userId,createtime)"
						+ "VALUES(13178822742,?,?,?,?,?,?,?,?,?,?,209,?)";
				PreparedStatement ten = ZX.prepareStatement(sql);
				ten.setString(1,String.valueOf((int)(50+Math.random()*(90-50+1))));
				ten.setString(2,String.valueOf((int)(100+Math.random()*(120-100+1))));
				ten.setString(3,String.valueOf((int)(50+Math.random()*(80-50+1))));
				ten.setString(4,String.valueOf((int)(70+Math.random()*(100-70+1))));
				ten.setString(5,String.valueOf((int)(90+Math.random()*(100-90+1))));
				ten.setString(6,String.valueOf((int)(70+Math.random()*(80-70+1))));
				ten.setString(7,"HRV:57, 呼吸频率:13。\r\n" + 
						"1、适当增加食盐的摄入，同时多饮水；\r\n" + 
						"2、饮食荤素合理搭配，增加总热量；\r\n" + 
						"3、加强体育锻炼，增强体质。体育锻炼对预防高血压及低血压均有益处。\r\n" + 
						"4、低血压人群应当每天适当的补充完全蛋白质、维生素B族、抗压素。适当的补充维生素E可以帮助低血压患者消除疲劳。\r\n" + 
						"5、如有病理性的低血压，请到正规医院就诊。");
				ten.setString(8,String.valueOf((int)(12+Math.random()*(24-12+1))));
				ten.setString(9,String.valueOf((int)(0+Math.random()*(10-0+1))));
				ten.setString(10,String.valueOf((int)(0+Math.random()*(10-0+1))));
				String time = sf.format(cd.getTime());
				ten.setString(11,time);
				ten.executeUpdate();
				System.out.println("插入成功");
				if(time.equals("2019-06-30 23:55:00")) {
					 fag=false;
					 System.out.println("OK");
				 }
				 }
			} catch (Exception e) {
				System.out.println("插入失败"+e);
				
			}
			
		} 
	/**
	 * 插入数据
	 * @throws SQLException 
	*/
	public static void 	insertEQ(Connection ZX){
		boolean fag = true;
		String date = "2018-07-17 00:00:00";
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sf.parse(date));
			Calendar cd = Calendar.getInstance();   
			 cd.setTime(sf.parse(date));
			 while(fag) {
				 cd.add(Calendar.MINUTE, 5);
			String sql = "INSERT INTO equipment_data(user_id,sleeping,step_when,mocrocirculation,createtime,heartrate,highpressure,bottompressure,"
					+ "bloodpressure,breathe,carrieroad,sedentary,bodytemp,humidity,crash,qxygen,step_time,hrv,step_each,mood,"
					+ "movementstate,sleeping_s,run_s)"
					+ "VALUES(286,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ten = ZX.prepareStatement(sql);
			ten.setString(1,String.valueOf((int)(48+Math.random()*(96-48+1))));
			ten.setString(2,String.valueOf((int)(1+Math.random()*(10-1+1))));
		
			ten.setString(3,String.valueOf((int)(0+Math.random()*(2-0+1))));
			String moth = String.valueOf((int)(1+Math.random()*(12-1+1)));
			String day = String.valueOf((int)(1+Math.random()*(7-1+1)));
			String hour = String.valueOf((int)(1+Math.random()*(23-1+1)));
			String minute = String.valueOf((int)(1+Math.random()*(59-1+1)));
			String second = String.valueOf((int)(1+Math.random()*(59-1+1)));
			
		/*	String Hmoth = (moth.length()==1?"0"+moth:moth);
			String Hday = (day.length()==1?"0"+day:day);
			String Hhour = (hour.length()==1?"0"+hour:hour);
			String Hminute = (minute.length()==1?"0"+minute:minute);
			String Hsecond =(second.length()==1?"0"+second:second);
			String time = "2018-"+"05"+"-"+"14"+" "+Hhour+":"+Hminute+":"+Hsecond;*/
			String time = sf.format(cd.getTime());
			ten.setString(4,time);
			ten.setString(5,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(6,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(7,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(8,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(9,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(10,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(11,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(12,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(13,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(14,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(15,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(16,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(17,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(18,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(19,String.valueOf((int)(48+Math.random()*(180-48+1))));
			ten.setString(20,String.valueOf((int)(0+Math.random()*(2-0+1))));
			ten.setString(21,String.valueOf((int)(0+Math.random()*(4-0+1))));
			ten.setString(22,String.valueOf((int)(0+Math.random()*(2-0+1))));
			ten.executeUpdate();
			System.out.println("插入成功");
			if(time.equals("2018-07-19 23:55:00")) {
				 fag=false;
				 System.out.println("OK");
		 }
			
			 }
		} catch (Exception e) {
			System.out.println("插入失败"+e);
			
		}
		
	} 
	/**
	 * 连接墨子数据库mozi
	 * 
	 * @return
	 */
	public static Connection Mozi() {
		String Driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.147:3306/mozi_new?useUnicode=true&characterEncoding=utf8";
		String UserName = "root";
		String Password = "123456";
		try {
			Class.forName(Driver);
			con = DriverManager.getConnection(url, UserName, Password);
			// con.close();
		} catch (Exception e) {
			System.out.println("加载征询库失败" + e);
		}
		return con;
	}
}
