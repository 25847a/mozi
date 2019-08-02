package cn.mozistar.configurer;

import java.sql.Connection;
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
			//插入数据
		//	insert(MZ);//惊凡数据表
			insertHealthstep(MZ);
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
	 * 插入数据
	 * @throws SQLException 
	*/
	public static void 	insertHealthstep(Connection ZX){
		boolean fag = true;
		String date = "2019-07-30 00:00:00";
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sf.parse(date));
			Calendar cd = Calendar.getInstance();   
			 cd.setTime(sf.parse(date));
			 while(fag) {
				 cd.add(Calendar.MINUTE, 5);
			String sql = "INSERT INTO healthstep(stepWhen,carrieroad,userId,createtime)"
					+ "VALUES(?,?,129,?)";
			PreparedStatement ten = ZX.prepareStatement(sql);
			ten.setString(1,String.valueOf((int)(50+Math.random()*(90-50+1))));
			ten.setString(2,String.valueOf((int)(100+Math.random()*(120-100+1))));
			String time = sf.format(cd.getTime());
			ten.setString(3,time);
			ten.executeUpdate();
			System.out.println("插入成功");
			if(time.equals("2019-08-30 23:55:00")) {
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
		public static void 	insert(Connection ZX){
			boolean fag = true;
			String date = "2019-07-30 00:00:00";
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sf.parse(date));
				Calendar cd = Calendar.getInstance();   
				 cd.setTime(sf.parse(date));
				 while(fag) {
					 cd.add(Calendar.MINUTE, 5);
				String sql = "INSERT INTO health(phone,hrv,highBloodPressure,lowBloodPressure,heartRate,"
						+ "bloodOxygen,microcirculation,amedicalreport,respirationrate,arrhythmia,mood,userId,createtime)"
						+ "VALUES(13178822742,?,?,?,?,?,?,?,?,?,?,129,?)";
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
				if(time.equals("2019-08-30 23:55:00")) {
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
