package cn.mozistar.mapper;

import cn.mozistar.pojo.Health;
import cn.mozistar.vo.Chart;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface HealthMapper {
	

	@Delete({ "delete from health", "where id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);


	@Insert({ "insert into health (id, phone, ", "hrv, highBloodPressure, ", "lowBloodPressure, heartRate, ",
			"bloodOxygen, microcirculation, ", "amedicalreport, respirationrate, ", "userId, createtime, ", "waveform)",
			"values (#{id,jdbcType=INTEGER}, #{phone,jdbcType=VARCHAR}, ",
			"#{hrv,jdbcType=INTEGER}, #{highBloodPressure,jdbcType=INTEGER}, ",
			"#{lowBloodPressure,jdbcType=INTEGER}, #{heartRate,jdbcType=INTEGER}, ",
			"#{bloodOxygen,jdbcType=INTEGER}, #{microcirculation,jdbcType=INTEGER}, ",
			"#{amedicalreport,jdbcType=VARCHAR}, #{respirationrate,jdbcType=INTEGER}, ",
			"#{userId,jdbcType=INTEGER}, #{createtime,jdbcType=TIMESTAMP}, ", "#{waveform,jdbcType=VARCHAR})" })
	int insert(Health record);


	@InsertProvider(type = HealthSqlProvider.class, method = "insertSelective")
	int insertSelective(Health record);


	@Select({ "select", "id, phone, hrv, highBloodPressure, lowBloodPressure, heartRate, bloodOxygen, ",
			"microcirculation, amedicalreport, respirationrate, userId, createtime, waveform", "from health",
			"where id = #{id,jdbcType=INTEGER}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "hrv", property = "hrv", jdbcType = JdbcType.INTEGER),
			@Result(column = "highBloodPressure", property = "highBloodPressure", jdbcType = JdbcType.INTEGER),
			@Result(column = "lowBloodPressure", property = "lowBloodPressure", jdbcType = JdbcType.INTEGER),
			@Result(column = "heartRate", property = "heartRate", jdbcType = JdbcType.INTEGER),
			@Result(column = "bloodOxygen", property = "bloodOxygen", jdbcType = JdbcType.INTEGER),
			@Result(column = "microcirculation", property = "microcirculation", jdbcType = JdbcType.INTEGER),
			@Result(column = "amedicalreport", property = "amedicalreport", jdbcType = JdbcType.VARCHAR),
			@Result(column = "respirationrate", property = "respirationrate", jdbcType = JdbcType.INTEGER),
			@Result(column = "userId", property = "userId", jdbcType = JdbcType.INTEGER),
			@Result(column = "createtime", property = "createtime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "waveform", property = "waveform", jdbcType = JdbcType.VARCHAR) })
	Health selectByPrimaryKey(Integer id);


	@UpdateProvider(type = HealthSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(Health record);


	@Update({ "update health", "set phone = #{phone,jdbcType=VARCHAR},", "hrv = #{hrv,jdbcType=INTEGER},",
			"highBloodPressure = #{highBloodPressure,jdbcType=INTEGER},",
			"lowBloodPressure = #{lowBloodPressure,jdbcType=INTEGER},", "heartRate = #{heartRate,jdbcType=INTEGER},",
			"bloodOxygen = #{bloodOxygen,jdbcType=INTEGER},",
			"microcirculation = #{microcirculation,jdbcType=INTEGER},",
			"amedicalreport = #{amedicalreport,jdbcType=VARCHAR},",
			"respirationrate = #{respirationrate,jdbcType=INTEGER},", "userId = #{userId,jdbcType=INTEGER},",
			"createtime = #{createtime,jdbcType=TIMESTAMP},", "waveform = #{waveform,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(Health record);


	@Select("select * from health where userId = #{userId} order by createtime DESC LIMIT 0,1")
	Health selectByUserId(Integer userId);

	
	@Select("<script>" +
			"<if test='service != null and service != \"\" and  service == \"year\" '>" +
				"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
				"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
				"date_format(createtime, '%Y-%m-%d')" +
				"as date" +
				"FROM health WHERE DATE_FORMAT(createtime, '%Y') =" +
				"#{timedata} AND" +
				"userId=#{userId} GROUP BY date_format(createtime," +
				"'%Y%m%d');" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"month\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"	date_format(createtime, '%Y-%m-%d')" +
			"	as date FROM health WHERE DATE_FORMAT(createtime, '%Y-%m') =" +
			"	#{timedata} AND userId=#{userId}" +
			"	GROUP BY date_format(createtime," +
			"	'%Y%m%d');" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"day\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"	date_format(createtime,'%Y-%m-%d %H:%i:%S') as date FROM health WHERE" +
			"	DATE_FORMAT(createtime,'%Y-%m-%d')=#{timedata} AND userId=#{userId}" +
			"	GROUP BY date_format(createtime, '%Y%m%d %H:%i:%S');" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"week\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"	date_format(createtime, '%Y-%m-%d')" +
			"	as date FROM health WHERE" +
			"	YEARWEEK(DATE_FORMAT(createtime,'%Y-%m-%d'))=YEARWEEK(NOW()) AND" +
			"	userId=#{userId}" +
			"	GROUP BY date_format(createtime, '%Y%m%d');" +
			"</if>" +
		"</script>")
	List<Chart> selecthealth(Map<String, Object> m);

	@Select("<script>" 
			+ " <if test='keyWord != null and keyWord != \"\" and  keyWord == \"year\" '>"
			+ " select * from  health  where year(createtime)= #{timedata}    and  userId =#{userId} ORDER BY highBloodPressure DESC LIMIT 1 ;"
			+ "   </if>" 
			+ "   <if test='keyWord != null and keyWord != \"\" and  keyWord == \"month\" '>"
			+ "	select * from health  where month(createtime)= #{timedata} and  userId =#{userId} and year(createtime)= #{year} ORDER BY highBloodPressure DESC LIMIT 1  ;"
			+ "	</if>" 
			+ "	<if test='keyWord != null and keyWord != \"\" and  keyWord == \"day\" '>"
			+ "	  select * from health  where  day(createtime)= #{timedata} and  userId =#{userId} and year(createtime)= #{year}  and  "
			+ "   month(createtime)= #{month} ORDER BY highBloodPressure DESC LIMIT 1;" 
			+ "	</if>"
			+ "	<if test='keyWord != null and keyWord != \"\" and  keyWord == \"week\" '>"
			+ "	  select * from health  where YEARWEEK(DATE_FORMAT(createtime,'%Y-%m-%d'))=YEARWEEK(NOW()) and  userId =#{userId} ORDER BY highBloodPressure DESC LIMIT 1;"
			+ "	</if>" 
			+ "	</script>")
	Health selecthealthMax(Map<String, Object> map);

	@Select("<script>" + "<if test= 'keyWord != null and keyWord != \"\" and  keyWord == \"year\" '>"
			+ "select * from  health  where year(createtime)= #{timedata}    and  userId =#{userId} ORDER BY lowBloodPressure LIMIT 1,1 ;"
			+ "</if>" + "<if test='keyWord != null and keyWord != \"\" and  keyWord == \"month\" '>"
			+ "select * from health  where month(createtime)= #{timedata} and  userId =#{userId} and year(createtime)= #{year} ORDER BY lowBloodPressure LIMIT 1,1 ;"
			+ "</if>" + "<if test='keyWord != null and keyWord != \"\" and  keyWord == \"day\" '>"
			+ "select * from health  where  day(createtime)= #{timedata} and  userId =#{userId} and year(createtime)= #{year}  and "
			+ " month(createtime)= #{month} ORDER BY lowBloodPressure LIMIT 1,1;" + "</if>"
			+ "<if test='keyWord != null and keyWord != \"\" and  keyWord == \"week\" '>"
			+ " select * from health  where YEARWEEK(DATE_FORMAT(createtime,'%Y-%m-%d'))=YEARWEEK(NOW()) and  userId =#{userId} ORDER BY lowBloodPressure LIMIT 1,1 ;"
			+ " </if>" + " </script>")
	Health selecthealthMin(Map<String, Object> map);
}