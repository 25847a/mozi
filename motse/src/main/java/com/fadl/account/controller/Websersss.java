package com.fadl.account.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;

public class Websersss {
	static Connection con = null;
	public static void main(String[] args) {
		Connection MZ = null;
		Connection MZCeshi = null;
		try{
			MZ = Mozi();//加载墨子数据库;mozi
			MZCeshi=MoziCeshi();//moziCeshi
		//List<Map<String,String>> list =Query(MZ);//1.获取用户表
		//	insertUser(MZCeshi,list);//2.插入用户
			 
			List<DataRow> Administration= queryAdministration(MZCeshi);//3.获取管理者用户
			List<DataRow> use = queryUse(MZCeshi);//获取使用者用户
			insertCount(MZCeshi,Administration,use);//插入数据
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
	 * 获取数据用户表数据
	 * @param ZX
	 * @return
	 * @throws SQLException
	*/
		public static List<Map<String,String>> Query(Connection ZX){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			
			String sql = "SELECT * FROM user LIMIT 1000,6000";
			Statement con;
			try {
				con = ZX.createStatement();
				ResultSet res = con.executeQuery(sql);
			
				while(res.next()){
					Map<String,String> map = new HashMap<String, String>();
					map.put("name", res.getString("name"));
					map.put("calibration", res.getString("calibration"));
					map.put("jfdataUpdateTime", res.getString("jfdataUpdateTime"));
					map.put("createtime", res.getString("createtime"));
					map.put("atlasttime", res.getString("atlasttime"));
					map.put("password", "E10ADC3949BA59ABBE56E057F20F883E");
					map.put("gender", res.getString("gender"));
					map.put("weight", res.getString("weight"));
					map.put("born", res.getString("born"));
					map.put("height", res.getString("height"));
					map.put("code", res.getString("code"));
					map.put("love", res.getString("love"));
					list.add(map);
			}
			} catch (SQLException e) {
				System.out.println("获取失败"+e);
			}
			return list;
			
		}
		/**
		 * 插入数据用户表
		 * @throws SQLException 
		*/
		public static void 	insertUser(Connection MZCeshi,List<Map<String,String>> list){
			try {
			String userSql = "INSERT INTO user (imei,role,account,name,phone,calibration,jfdataUpdateTime,createtime,atlasttime,password,isDelete,age,gender,"
					+ "weight,born,height)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			if(list.size()%2==1){//奇数,也就是单数不执行代码,执行了也是错的,直接return
				System.out.println("查询出来的user是单数,不执行代码");return;
			}else{
				DecimalFormat dec = new DecimalFormat("00000");
				for(int i=0;i<list.size();i++){
					PreparedStatement userTen= MZCeshi.prepareStatement(userSql,Statement.RETURN_GENERATED_KEYS);
					if(i%2==1){//奇数为使用者
						userTen.setString(1, "8622370455"+dec.format(i));
						userTen.setString(2, "使用者");
						userTen.setString(3, "");
						userTen.setString(4, list.get(i).get("name"));
						userTen.setString(5, getTel());
					}else{//偶数为管理者
						userTen.setString(1, "");
						userTen.setString(2, "管理者");
						userTen.setString(3, getTel());
						userTen.setString(4, list.get(i).get("name"));
						userTen.setString(5, getTel());
					}
					userTen.setString(6, "1");
					userTen.setString(7, "5");
					userTen.setDate(8, new Date(System.currentTimeMillis()));
					userTen.setDate(9, new Date(System.currentTimeMillis()));
					userTen.setString(10, list.get(i).get("password"));
					userTen.setString(11, "0");
					userTen.setString(12, String.valueOf((int)(30+Math.random()*(60-30+1))));
					userTen.setString(13, ((int)(0+Math.random()*(1-0+1))==1)?"男":"女");
					userTen.setString(14, String.valueOf((int)(50+Math.random()*(70-50+1))));
					userTen.setDate(15,  new Date(System.currentTimeMillis()));
					userTen.setString(16, String.valueOf((int)(160+Math.random()*(175-160+1))));
					userTen.executeUpdate();
					
					ResultSet a = userTen.getGeneratedKeys();
					a.next();
					System.out.println(a.getInt(1));//获取ID
					
				}
				
			}
		} catch (Exception e) {
				System.out.println("插入失败"+e);
				
			}
			
		}
		/**
		 * 获取管理者用户ID
		 * @param ZX
		 */
		public static List<DataRow> queryAdministration(Connection ZX){
			List<DataRow> list = new ArrayList<DataRow>();
			
			String sql = "SELECT * FROM user where role='管理者'";
			Statement con;
			try {
				con = ZX.createStatement();
				ResultSet res = con.executeQuery(sql);
			
				while(res.next()){
					DataRow map = new DataRow();
					map.put("id", res.getString("id"));
					list.add(map);
			}
			} catch (SQLException e) {
				System.out.println("获取失败"+e);
			}
			return list;
		}
		/**
		 * 获取使用者管理者用户ID
		 * @param ZX
		 */
		public static List<DataRow> queryUse(Connection ZX){
			List<DataRow> list = new ArrayList<DataRow>();
			String sql = "SELECT * FROM user where role='使用者'";
			Statement con;
			try {
				con = ZX.createStatement();
				ResultSet res = con.executeQuery(sql);
				while(res.next()){
					DataRow map = new DataRow();
					map.put("id", res.getString("id"));
					map.put("imei", res.getString("imei"));
					list.add(map);
			}
			} catch (SQLException e) {
				System.out.println("获取失败"+e);
			}
			return list;
		}
		/**
		 * 插入数据
		 * @param ZX
		 * @param list
		 * @param list
		 */
		public static void insertCount(Connection ZX,List<DataRow> administration,List<DataRow> use){
			if(administration.size()==use.size()){
				for(int i=0;i<administration.size();i++){
					try {
					String userSql = "INSERT INTO equipment (imei,lordpower,signalxhao,bluetooth_type,eq_status,createtime,updatetime,eqtype,bluetooth_name,bluetooth_status,"
							+ "bluetooth_list,bluetooth_electricity,clock,phone1,phone2,name,version,uploadtime,bluetoothmac,agentid,model"
							+ ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement userTen= ZX.prepareStatement(userSql,Statement.RETURN_GENERATED_KEYS);
							userTen.setString(1, use.get(i).getString("imei"));
							userTen.setInt(2, (int)(0+Math.random()*(100-0+1)));
							userTen.setString(3, String.valueOf((int)(12+Math.random()*(20-12+1))));
							userTen.setString(4, "1");
							userTen.setString(5, "H:1");
							userTen.setDate(6, new Date(System.currentTimeMillis()));
							userTen.setDate(7, new Date(System.currentTimeMillis()));
							userTen.setString(8, "1");
							userTen.setString(9, "2025541EDAC6");
							userTen.setString(10, "1");
							userTen.setString(11, "['06F203D981D2','2025541EDAC6']");
							userTen.setString(12, "0");
							userTen.setString(13, "闹钟");
							userTen.setString(14, "儿子,15536362828");
							userTen.setString(15, "儿子,15536362828");
							userTen.setString(16, "设备信息");
							userTen.setString(17, "LN073OV1_1_19042301_1");
							userTen.setDate(18, new Date(System.currentTimeMillis()));
							userTen.setString(19, "2025541EDAC6");
							userTen.setInt(20,  1);
							userTen.setString(21, "LN073OV1");
							userTen.executeUpdate();
							ResultSet a = userTen.getGeneratedKeys();
							a.next();
							System.out.println(a.getInt(1));
							//插入到userEq表
							String userEqadministration = "INSERT INTO user_eq(user_id,eq_id,typeof)values(?,?,0)";
							PreparedStatement userEqadministrationTen = ZX.prepareStatement(userEqadministration);
							userEqadministrationTen.setInt(1, administration.get(i).getInt("id"));
							userEqadministrationTen.setInt(2, a.getInt(1));
							userEqadministrationTen.executeUpdate();
							String userEquse = "INSERT INTO user_eq(user_id,eq_id,typeof)values(?,?,2)";
							PreparedStatement userEquseTen = ZX.prepareStatement(userEquse);
							userEquseTen.setInt(1, use.get(i).getInt("id"));
							userEquseTen.setInt(2, a.getInt(1));
							userEquseTen.executeUpdate();
							insetequipmentdata(ZX,use.get(i));
							inseteJfhealth(ZX,use.get(i));
							insetPush(ZX,use.get(i),administration.get(i));
							insetJfhealthNew(ZX,use.get(i));
							//EquipmentData,Jfhealth,pushMapper,jfhealthNewMapper
							//jfhealth,waveform,equipment_data,jfhealth_new,user  不删除
							//user_eq,usercode,sensorstatus(清空imei的记录),push,positionig(清空imei的记录),jfhealthdao,chat  可删除
							
						
					} catch (Exception e) {
						System.out.println("插入失败"+e);
						
					}
				
				}
			}else{
				System.out.println("使用者与管理者人数不对等");return;
			}
		};
		/**
		 * 插入EquipmentData健康数据
		 * @throws SQLException 
		*/
		public static void 	insetequipmentdata(Connection ZX,DataRow use){
			try {
				  String sql = "insert into equipment_data ( user_id, sleeping,step_when,carrieroad,sedentary,movementstate, crash, createtime)"+
					"values(?,?,?,?,?,?,?,?)";       
				PreparedStatement equipmentdata = ZX.prepareStatement(sql);
				equipmentdata.setInt(1,use.getInt("id"));
				equipmentdata.setInt(2,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setInt(3,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setInt(4,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setInt(5,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setInt(6,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setInt(7,(int)(12+Math.random()*(20-12+1)));
				equipmentdata.setDate(8, new Date(System.currentTimeMillis()));
				equipmentdata.executeUpdate();
			} catch (SQLException e) {
				System.out.println("插入失败"+e);
			}
		}
	/**
	 * 更新Jfhealth数据
	 * @throws SQLException 
	*/
	public static void 	inseteJfhealth(Connection ZX,DataRow use){
		try {
			  String sql = "insert into jfhealth ( HRV, sbp_ave,dbp_ave,Heartrate,Bloodoxygen,microcirculation, Amedicalreport, respirationrate,phone,imei,createtime)"+
				"values(?,?,?,?,?,?,?,?,?,?,?)";       
			PreparedStatement Jfhealth = ZX.prepareStatement(sql);
			Jfhealth.setInt(1,(int)(100+Math.random()*(140-100+1)));
			Jfhealth.setInt(2,(int)(100+Math.random()*(180-100+1)));
			Jfhealth.setInt(3,(int)(50+Math.random()*(80-50+1)));
			Jfhealth.setInt(4,(int)(125+Math.random()*(18-125+1)));
			Jfhealth.setInt(5,(int)(80+Math.random()*(100-80+1)));
			Jfhealth.setInt(6,(int)(50+Math.random()*(150-50+1)));
			Jfhealth.setString(7,"HRV:57, 呼吸频率:13。\r\n" + 
					"1、适当增加食盐的摄入，同时多饮水；\r\n" + 
					"2、饮食荤素合理搭配，增加总热量；\r\n" + 
					"3、加强体育锻炼，增强体质。体育锻炼对预防高血压及低血压均有益处。\r\n" + 
					"4、低血压人群应当每天适当的补充完全蛋白质、维生素B族、抗压素。适当的补充维生素E可以帮助低血压患者消除疲劳。\r\n" + 
					"5、如有病理性的低血压，请到正规医院就诊。");
			Jfhealth.setInt(8,(int)(70+Math.random()*(85-70+1)));
			Jfhealth.setString(9,"mozistar"+use.getInt("id"));
			Jfhealth.setString(10,use.getString("imei"));
			Jfhealth.setDate(11, new Date(System.currentTimeMillis()));
			Jfhealth.executeUpdate();
		} catch (SQLException e) {
			System.out.println("插入失败"+e);
		}
	} 
	/**
	 * 插入EquipmentData健康数据
	 * @throws SQLException 
	*/
	public static void 	insetPush(Connection ZX,DataRow use,DataRow administration){
		try {
			  String sql = "insert into push ( userId, alias,allNotifyOn,heartHigThd,heartLowThd,hbpend, hbpstart, lbpend,lbpstart,createTime,updateTime)"+
				"values(?,?,?,?,?,?,?,?,?,?,?)";       
			PreparedStatement push = ZX.prepareStatement(sql);
			push.setInt(1,use.getInt("id"));
			push.setInt(2,administration.getInt("id"));
			push.setInt(3,1);
			push.setInt(4,(int)(90+Math.random()*(100-90+1)));
			push.setInt(5,(int)(70+Math.random()*(80-70+1)));
			push.setInt(6,(int)(90+Math.random()*(120-90+1)));
			push.setInt(7,(int)(70+Math.random()*(80-70+1)));
			push.setInt(8, (int)(80+Math.random()*(90-80+1)));
			push.setInt(9, (int)(0+Math.random()*(50-0+1)));
			push.setDate(10, new Date(System.currentTimeMillis()));
			push.setDate(11, new Date(System.currentTimeMillis()));
			push.executeUpdate();
		} catch (SQLException e) {
			System.out.println("插入失败"+e);
		}
	};
	/**
	 * 插入EquipmentData健康数据
	 * @throws SQLException 
	*/
	public static void 	insetJfhealthNew(Connection ZX,DataRow use){
		try {
			  String sql = "insert into jfhealth_new ( HRV, sbp_ave,dbp_ave,Heartrate,Bloodoxygen,microcirculation, Amedicalreport, respirationrate,phone,imei,createtime)"+
				"values(?,?,?,?,?,?,?,?,?,?,?)";       
			PreparedStatement Jfhealth = ZX.prepareStatement(sql);
			Jfhealth.setInt(1,(int)(100+Math.random()*(140-100+1)));
			Jfhealth.setInt(2,(int)(100+Math.random()*(180-100+1)));
			Jfhealth.setInt(3,(int)(50+Math.random()*(80-50+1)));
			Jfhealth.setInt(4,(int)(125+Math.random()*(18-125+1)));
			Jfhealth.setInt(5,(int)(80+Math.random()*(100-80+1)));
			Jfhealth.setInt(6,(int)(50+Math.random()*(150-50+1)));
			Jfhealth.setString(7,"HRV:57, 呼吸频率:13。\r\n" + 
					"1、适当增加食盐的摄入，同时多饮水；\r\n" + 
					"2、饮食荤素合理搭配，增加总热量；\r\n" + 
					"3、加强体育锻炼，增强体质。体育锻炼对预防高血压及低血压均有益处。\r\n" + 
					"4、低血压人群应当每天适当的补充完全蛋白质、维生素B族、抗压素。适当的补充维生素E可以帮助低血压患者消除疲劳。\r\n" + 
					"5、如有病理性的低血压，请到正规医院就诊。");
			Jfhealth.setInt(8,(int)(70+Math.random()*(85-70+1)));
			Jfhealth.setString(9,"mozistar"+use.getInt("id"));
			Jfhealth.setString(10,use.getString("imei"));
			Jfhealth.setDate(11, new Date(System.currentTimeMillis()));
			Jfhealth.executeUpdate();
		} catch (SQLException e) {
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
		String url = "jdbc:mysql://192.168.1.147:3306/mozi?useUnicode=true&characterEncoding=utf8";
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
	/**
	 * 连接墨子数据库mozi_ceshi
	 * 
	 * @return
	 */
	public static Connection MoziCeshi() {
		String Driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.1.147:3306/mozi_ceshi?useUnicode=true&characterEncoding=utf8";
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
	  /**
     * 返回手机号码 
     */
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    private static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
}
