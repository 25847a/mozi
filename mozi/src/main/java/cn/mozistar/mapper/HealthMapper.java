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
				"SUM(stepWhen) as stepWhen,SUM(carrieroad) as carrieroad,"+
				"date_format(createtime, '%Y-%m-%d')" +
				"as date" +
				"FROM health WHERE DATE_FORMAT(createtime, '%Y') =" +
				"#{timedata} AND" +
				"userId=#{userId} GROUP BY date_format(createtime," +
				"'%Y%m%d')" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"month\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"SUM(stepWhen) as stepWhen,SUM(carrieroad) as carrieroad,"+
			"	date_format(createtime, '%Y-%m-%d')" +
			"	as date FROM health WHERE DATE_FORMAT(createtime, '%Y-%m') =" +
			"	#{timedata} AND userId=#{userId}" +
			"	GROUP BY date_format(createtime," +
			"	'%Y%m%d')" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"day\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"SUM(stepWhen) as stepWhen,SUM(carrieroad) as carrieroad,"+
			"	date_format(createtime,'%Y-%m-%d %H:%i:%S') as date FROM health WHERE" +
			"	DATE_FORMAT(createtime,'%Y-%m-%d')=#{timedata} AND userId=#{userId}" +
			"	GROUP BY  HOUR(createtime);" +
			"</if>" +
			"<if test='service != null and service != \"\" and  service == \"week\" '>" +
			"SELECT TRUNCATE(avg(hrv),0) as hrv,TRUNCATE(avg(highBloodPressure),0) as highBloodPressure,TRUNCATE(avg(lowBloodPressure),0) as lowBloodPressure,TRUNCATE(avg(heartRate),0) as heartRate," +
			"TRUNCATE(avg(bloodOxygen),0) as bloodOxygen,TRUNCATE(avg(microcirculation),0) as microcirculation,TRUNCATE(avg(respirationrate),0) as respirationrate," +
			"SUM(stepWhen) as stepWhen,SUM(carrieroad) as carrieroad,"+
			"	date_format(createtime, '%Y-%m-%d')" +
			"	as date FROM health WHERE" +
			"	YEARWEEK(DATE_FORMAT(createtime,'%Y-%m-%d'))=YEARWEEK(NOW()) AND" +
			"	userId=#{userId}" +
			"	GROUP BY date_format(createtime, '%Y%m%d')" +
			"</if>" +
		"</script>")
	List<Chart> selecthealth(Map<String, Object> m);
	/**
	 * 心率MAX,MIN,AVG,COUNT
	 * @param map
	 * @return
	 */
	@Select("<script>" +
			"<if test='service != null and service != \"\" and  service == \"year\" '>" +
			"SELECT tt.*,new,createtime FROM ("+
			"SELECT MAX(heartRate) as max,MIN(heartRate) AS min,COUNT(*) AS count,ROUND(AVG(heartRate)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,heartRate as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" 	#{userId} AND date_format(tp.createtime,'%Y')=#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"month\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(heartRate) as max,MIN(heartRate) AS min,COUNT(*) AS count,ROUND(AVG(heartRate)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,heartRate as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"day\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(heartRate) as max,MIN(heartRate) AS min,COUNT(*) AS count,ROUND(AVG(heartRate)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,heartRate as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m-%d')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"week\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(heartRate) as max,MIN(heartRate) AS min,COUNT(*) AS count,ROUND(AVG(heartRate)) AS avg,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,heartRate as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND YEARWEEK(date_format(tp.createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
	"</script>")
	Map<String,String> selectHeartRateInfo(Map<String, Object> map);
	/**
	 * 血压MAX,MIN,AVG,COUNT
	 * @param map
	 * @return
	 */
	@Select("<script>" +
			"<if test='service != null and service != \"\" and  service == \"year\" '>" +
			"SELECT tt.*,new,createtime FROM ("+
			"SELECT CONCAT(MAX(highBloodPressure),'/',MAX(lowBloodPressure)) AS max,CONCAT(MIN(highBloodPressure),'/',MIN(lowBloodPressure)) min,COUNT(*) AS count,CONCAT(ROUND(AVG(highBloodPressure)),'/',ROUND(AVG(lowBloodPressure))) as avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,CONCAT(highBloodPressure,'/',lowBloodPressure) as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" 	#{userId} AND date_format(tp.createtime,'%Y')=#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"month\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT CONCAT(MAX(highBloodPressure),'/',MAX(lowBloodPressure)) AS max,CONCAT(MIN(highBloodPressure),'/',MIN(lowBloodPressure)) min,COUNT(*) AS count,CONCAT(ROUND(AVG(highBloodPressure)),'/',ROUND(AVG(lowBloodPressure))) as avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,CONCAT(highBloodPressure,'/',lowBloodPressure) as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"day\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT CONCAT(MAX(highBloodPressure),'/',MAX(lowBloodPressure)) AS max,CONCAT(MIN(highBloodPressure),'/',MIN(lowBloodPressure)) min,COUNT(*) AS count,CONCAT(ROUND(AVG(highBloodPressure)),'/',ROUND(AVG(lowBloodPressure))) as avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,CONCAT(highBloodPressure,'/',lowBloodPressure) as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m-%d')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"week\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT CONCAT(MAX(highBloodPressure),'/',MAX(lowBloodPressure)) AS max,CONCAT(MIN(highBloodPressure),'/',MIN(lowBloodPressure)) min,COUNT(*) AS count,CONCAT(ROUND(AVG(highBloodPressure)),'/',ROUND(AVG(lowBloodPressure))) as avg,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,CONCAT(highBloodPressure,'/',lowBloodPressure) as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND YEARWEEK(date_format(tp.createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
	"</script>")
	Map<String,String> selectBloodpressureInfo(Map<String, Object> map);
	/**
	 * 步数MAX,MIN,AVG,COUNT
	 * @param map
	 * @return
	 */
	@Select("<script>" +
			"<if test='service != null and service != \"\" and  service == \"year\" '>" +
			"SELECT tt.new,tg.createtime,tv.max,tv.maxtime,tj.min,tj.mintime,tt.avg,kilometre FROM ("+
			"SELECT COUNT(*) AS new,ROUND(AVG(stepWhen)) as avg,ROUND(COUNT(*)*50/100000) as kilometre,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y') =#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y') =#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y') =#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS max,date_format(createtime,'%Y') as maxtime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen DESC  LIMIT 0,1"+
			") tv"+
			" ON tg.userId=tv.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS min,date_format(createtime,'%Y') as mintime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen  LIMIT 0,1"+
			") tj"+
			" ON tg.userId= tj.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"month\" '>" +
			"SELECT tt.new,tg.createtime,tv.max,tv.maxtime,tj.min,tj.mintime,tt.avg,kilometre FROM ("+
			"SELECT COUNT(*) AS new,ROUND(AVG(stepWhen)) as avg,ROUND(COUNT(*)*50/100000) as kilometre,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m') =#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m') =#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m') =#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS max,date_format(createtime,'%Y-%m') as maxtime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen DESC  LIMIT 0,1"+
			") tv"+
			" ON tg.userId=tv.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS min,date_format(createtime,'%Y-%m') as mintime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen  LIMIT 0,1"+
			") tj"+
			" ON tg.userId= tj.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"day\" '>" +
			"SELECT tt.new,tg.createtime,tv.max,tv.maxtime,tj.min,tj.mintime,tt.avg,kilometre FROM ("+
			"SELECT COUNT(*) AS new,ROUND(AVG(stepWhen)) as avg,ROUND(COUNT(*)*50/100000) as kilometre,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d') =#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d') =#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m-%d') =#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS max,date_format(createtime,'%Y-%m-%d') as maxtime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen DESC  LIMIT 0,1"+
			") tv"+
			" ON tg.userId=tv.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS min,date_format(createtime,'%Y-%m-%d') as mintime,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d') =#{timedata} GROUP BY date_format(createtime,'%Y-%m') ORDER BY stepWhen  LIMIT 0,1"+
			") tj"+
			" ON tg.userId= tj.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"week\" '>" +
			"SELECT tt.new,tg.createtime,tv.max,tv.maxtime,tj.min,tj.mintime,tt.avg,kilometre FROM ("+
			"SELECT COUNT(*) AS new,ROUND(AVG(stepWhen)) as avg,ROUND(COUNT(*)*50/100000) as kilometre,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())) tt"+
			" ON tp.createtime=tt.createtime"+
			" WHERE tp.userId=#{userId} AND YEARWEEK(date_format(tp.createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
			") tg"+
			" ON tt.userId=tg.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS max,date_format(createtime,'%Y-%m-%d') as maxtime,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW()) GROUP BY date_format(createtime,'%Y-%m-%d') ORDER BY stepWhen DESC  LIMIT 0,1"+
			") tv"+
			" ON tg.userId=tv.userId"+
			" INNER JOIN (SELECT SUM(stepWhen) AS min,date_format(createtime,'%Y-%m-%d') as mintime,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW()) GROUP BY date_format(createtime,'%Y-%m-%d') ORDER BY stepWhen  LIMIT 0,1"+
			") tj"+
			" ON tg.userId= tj.userId"+
		"</if>" +
	"</script>")
	Map<String,String> selectStepWhenInfo(Map<String, Object> map);
	/**
	 * HRVMAX,MIN,AVG,COUNT
	 * @param map
	 * @return
	 */
	@Select("<script>" +
			"<if test='service != null and service != \"\" and  service == \"year\" '>" +
			"SELECT tt.*,new,createtime FROM ("+
			"SELECT MAX(hrv) as max,MIN(hrv) AS min,COUNT(*) AS count,ROUND(AVG(hrv)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}"+
			")tt"+
			" INNER JOIN ("+
			"SELECT tp.userId,hrv as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
			" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y')=#{timedata}) tt"+
			" ON tp.createtime=tt.createtime"+
			" 	#{userId} AND date_format(tp.createtime,'%Y')=#{timedata}"+
			") tg"+
			" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"month\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(hrv) as max,MIN(hrv) AS min,COUNT(*) AS count,ROUND(AVG(hrv)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,hrv as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"day\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(hrv) as max,MIN(hrv) AS min,COUNT(*) AS count,ROUND(AVG(hrv)) AS avg,userId FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,hrv as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND date_format(createtime,'%Y-%m-%d')=#{timedata}) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND date_format(tp.createtime,'%Y-%m-%d')=#{timedata}"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
		"<if test='service != null and service != \"\" and  service == \"week\" '>" +
		"SELECT tt.*,new,createtime FROM ("+
		"SELECT MAX(hrv) as max,MIN(hrv) AS min,COUNT(*) AS count,ROUND(AVG(hrv)) AS avg,userId FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		")tt"+
		" INNER JOIN ("+
		"SELECT tp.userId,hrv as new,date_format(tp.createtime,'%H:%i') as createtime FROM health tp"+
		" INNER JOIN (SELECT MAX(createtime) as createtime FROM health WHERE userId=#{userId} AND YEARWEEK(date_format(createtime,'%Y-%m-%d')) =YEARWEEK(NOW())) tt"+
		" ON tp.createtime=tt.createtime"+
		" WHERE tp.userId=#{userId} AND YEARWEEK(date_format(tp.createtime,'%Y-%m-%d')) =YEARWEEK(NOW())"+
		") tg"+
		" ON tt.userId=tg.userId"+
		"</if>" +
	"</script>")
	Map<String,String> selectHrvInfo(Map<String, Object> map);
}