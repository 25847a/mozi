package cn.mozistar.mapper;

import cn.mozistar.pojo.Healthdao;
import cn.mozistar.pojo.Push;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface HealthdaoMapper {
    @Delete({
        "delete from healthdao",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into healthdao (id, hrv, ",
        "highBloodPressure, lowBloodPressure, ",
        "heartRate, bloodOxygen, ",
        "microcirculation, respirationrate, ",
        "userId, createtime, ",
        "phone, amedicalreport)",
        "values (#{id,jdbcType=INTEGER}, #{hrv,jdbcType=INTEGER}, ",
        "#{highBloodPressure,jdbcType=INTEGER}, #{lowBloodPressure,jdbcType=INTEGER}, ",
        "#{heartRate,jdbcType=INTEGER}, #{bloodOxygen,jdbcType=INTEGER}, ",
        "#{microcirculation,jdbcType=INTEGER}, #{respirationrate,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER}, #{createtime,jdbcType=TIMESTAMP}, ",
        "#{phone,jdbcType=VARCHAR}, #{amedicalreport,jdbcType=LONGVARCHAR})"
    })
    int insert(Healthdao record);

    @InsertProvider(type=HealthdaoSqlProvider.class, method="insertSelective")
    int insertSelective(Healthdao record);

    @Select({
        "select",
        "id, hrv, highBloodPressure, lowBloodPressure, heartRate, bloodOxygen, microcirculation, ",
        "respirationrate, userId, createtime, phone, amedicalreport",
        "from healthdao",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="hrv", property="hrv", jdbcType=JdbcType.INTEGER),
        @Result(column="highBloodPressure", property="highBloodPressure", jdbcType=JdbcType.INTEGER),
        @Result(column="lowBloodPressure", property="lowBloodPressure", jdbcType=JdbcType.INTEGER),
        @Result(column="heartRate", property="heartRate", jdbcType=JdbcType.INTEGER),
        @Result(column="bloodOxygen", property="bloodOxygen", jdbcType=JdbcType.INTEGER),
        @Result(column="microcirculation", property="microcirculation", jdbcType=JdbcType.INTEGER),
        @Result(column="respirationrate", property="respirationrate", jdbcType=JdbcType.INTEGER),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="createtime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="amedicalreport", property="amedicalreport", jdbcType=JdbcType.LONGVARCHAR)
    })
    Healthdao selectByPrimaryKey(Integer id);

    @UpdateProvider(type=HealthdaoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Healthdao record);

    @Update({
        "update healthdao",
        "set hrv = #{hrv,jdbcType=INTEGER},",
          "highBloodPressure = #{highBloodPressure,jdbcType=INTEGER},",
          "lowBloodPressure = #{lowBloodPressure,jdbcType=INTEGER},",
          "heartRate = #{heartRate,jdbcType=INTEGER},",
          "bloodOxygen = #{bloodOxygen,jdbcType=INTEGER},",
          "microcirculation = #{microcirculation,jdbcType=INTEGER},",
          "respirationrate = #{respirationrate,jdbcType=INTEGER},",
          "userId = #{userId,jdbcType=INTEGER},",
          "createtime = #{createtime,jdbcType=TIMESTAMP},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "amedicalreport = #{amedicalreport,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Healthdao record);

    @Update({
        "update healthdao",
        "set hrv = #{hrv,jdbcType=INTEGER},",
          "highBloodPressure = #{highBloodPressure,jdbcType=INTEGER},",
          "lowBloodPressure = #{lowBloodPressure,jdbcType=INTEGER},",
          "heartRate = #{heartRate,jdbcType=INTEGER},",
          "bloodOxygen = #{bloodOxygen,jdbcType=INTEGER},",
          "microcirculation = #{microcirculation,jdbcType=INTEGER},",
          "respirationrate = #{respirationrate,jdbcType=INTEGER},",
          "userId = #{userId,jdbcType=INTEGER},",
          "createtime = #{createtime,jdbcType=TIMESTAMP},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Healthdao record);
    
    
    @Select("select * from healthdao where userId = #{userId}")
	Healthdao getHealthdaoByUserId(Integer userId);

    @UpdateProvider(type=HealthdaoSqlProvider.class, method="updateHealthdaoByUserId")
	int updateHealthdaoByUserId(Healthdao healthdao);
    
    
    
}