package com.plasma.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Jdbc {

	public static final String url = "jdbc:mysql://127.0.0.1/commission?serverTimezone=UTC";
	public static final String name = "com.mysql.cj.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "admin";

	public static void main(String[] args) throws IOException {
		
		String path="D:\\360\\f_plasm_collection.xml";
		file(path);
		save("f_plasm_collection",path);
		System.err.println("文件已生成，路径："+path);
	}
	
	public static String change(String name){
		String[] s = name.split("_");
		StringBuilder sb = new StringBuilder();
		if (s.length==2) {
			sb.append(s[1].substring(0,1).toUpperCase()+s[1].substring(1, s[1].length()));
		}else{
			for (int i = 1; i < s.length; i++) {
				sb.append(s[i].substring(0,1).toUpperCase()+s[i].substring(1, s[i].length()));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成文件
	 * @param table 表名称
	 * @param path 文件路径
	 */
	public static void save(String table,String path){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			Class.forName(name);// 指定连接类型
			conn = (Connection) DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement("select COLUMN_NAME,column_comment from information_schema.COLUMNS where table_name = '"+table+"'");// 准备执行语句
			res = pst.executeQuery();
			String[] str = new String[100];
			int i=0;
			
			
			StringBuilder sbs = new StringBuilder();
			StringBuilder param = new StringBuilder();
			StringBuilder clomn = new StringBuilder();
			while (res.next()) {  
                String uid = res.getString(1);  
                str[i]=uid;
                i++;
                
                clomn.append("\""+uid+"\",");
                param.append("val.addParamCheck(\""+uid+"\");\n");
                sbs.append("map.put(\""+uid+"\",\""+res.getString(2)+"\");\n");
            }
			System.out.println(clomn+"\n\n");
			System.out.println(param+"\n\n\n");
			System.out.println(sbs);
			//把 table 的首字母变成大写 
			/*String tables = table.substring(table.indexOf("_")+1,table.length());
			tables = tables.substring(0,1).toUpperCase()+tables.substring(1, tables.length());*/
			
			String tables = change(table);
			
			
			
			StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n");
			sb.append("<!DOCTYPE mapper \n PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
			sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> \n");
			sb.append("<mapper namespace=\""+tables+"\"> \n\n");
			
			//sb.append("\t<typeAlias alias=\"DataRow\" type=\"com.shopping.common.util.DataRow\" />");
			
			//查询所有
			sb.append("\t<!--查询所有"+table+"-->\n");
			sb.append("\t<select id=\"query"+tables+"List\" resultType=\"DataRow\" parameterType=\"java.util.Map\">\n");
			sb.append("\t\t SELECT * FROM "+table+"\n");
			sb.append("\t</select>\n\n");
			
			//查询总数
			sb.append("\t<!--查询总数"+table+"-->\n");
			sb.append("\t<select id=\"query"+tables+"ListCount\" resultType=\"java.lang.Integer\" parameterType=\"java.util.Map\">\n");
			sb.append("\t\t SELECT COUNT(*) FROM "+table+"\n");
			sb.append("\t</select>\n\n");
			
			//根据ID查询
			sb.append("\t<!--根据ID查询"+table+"-->\n");
			sb.append("\t<select id=\"query"+tables+"ById\" resultType=\"DataRow\" parameterType=\"java.lang.Long\">\n");
			sb.append("\t\t SELECT * FROM "+table+" WHERE id=#{id}\n");
			sb.append("\t</select>\n\n");
			
			//添加
			sb.append("\t<!--添加"+table+"-->\n");
			sb.append("\t<insert id=\"save"+tables+"\"  parameterType=\"java.util.Map\">\n");
			
			sb.append("\t\tINSERT INTO "+table+"(");
			
			StringBuilder stringBuilder = new StringBuilder();
			for (int j = 1; j < str.length; j++) {
				if (null==str[j]) {
					break;
				}
				stringBuilder.append(str[j]+",");
			}
			String inserts = stringBuilder.substring(0, stringBuilder.length()-1);
			sb.append(inserts+") VALUES \n (");
			
			stringBuilder = new StringBuilder();
			for (int j = 1; j < str.length; j++) {
				if (null==str[j]) {
					break;
				}
				stringBuilder.append("#{"+str[j]+"},");
			}
			inserts = stringBuilder.substring(0, stringBuilder.length()-1);
			sb.append(inserts+")\n");
			sb.append("\t</insert>\n\n");
			
			
			//修改
			sb.append("\t<!--根据ID修改"+table+"-->\n");
			sb.append("\t<update id=\"update"+tables+"ById\" parameterType=\"java.util.Map\">\n");
			sb.append("\t\tUPDATE "+table+" set \n");
			
			for (int j = 1; j < str.length; j++) {
				if (null==str[j]) {
					break;
				}
				sb.append("\t\t <if test=\""+str[j]+"!=null and "+str[j]+"!='' \">\n");
				sb.append("\t\t"+str[j]+"=#{"+str[j]+"},\n");
				sb.append("\t\t</if>\n");
			}
			sb.append("\t\tid = #{id} WHERE id=#{id}\n");
			sb.append("\t</update>\n\n");
			
			//删除
			sb.append("\t<!--根据ID删除"+table+"-->\n");
			sb.append("\t<delete id=\"delete"+tables+"ById\" parameterType=\"java.lang.Long\">\n");
			sb.append("\t\tDELETE FROM "+table+" where id=#{id}\n");
			sb.append("\t</delete>\n\n");
			sb.append("</mapper>");
			OutputStream out = new FileOutputStream(new File(path));
			out.write(sb.toString().getBytes());
			out.close();
			
			res.close();
			pst.close();
			conn.close();
			
			str = new String[10];
			str[0]="query"+tables+"List";
			str[1]="query"+tables+"ListCount";
			str[2]="query"+tables+"ById";
			str[3]="save"+tables;
			str[4]="update"+tables+"ById";
			str[5]="delete"+tables+"ById";
			sb = new StringBuilder();
			for (int j = 0; j < str.length; j++) {
				switch (j) {
				case 0:
					sb.append("public List<DataRow> "+str[j]+"(DataRow data)throws SQLException;");
					break;
				case 1:
					sb.append("public int "+str[j]+"(DataRow data)throws SQLException;");
					break;
				case 2:
					sb.append("public DataRow "+str[j]+"(Long id)throws SQLException;");
					break;
				case 3:
					sb.append("public void "+str[j]+"(DataRow data)throws SQLException;");
					break;
				case 4:
					sb.append("public int "+str[j]+"(DataRow data)throws SQLException;");
					break;
				case 5:
					sb.append("public int "+str[j]+"(Long id)throws SQLException;");
					break;
				default:
					break;
				}
			}
			System.err.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (null!=res) {
					res.close();
				}
				if (null!=pst) {
					pst.close();
				}
				if (null!=conn) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成的文件名称
	 */
	public static void file(String fileName){
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("文件已存在");
		}
	}
}
