package cn.mozistar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import cn.mozistar.pojo.Push;

public interface PushMapper {
    @Delete({
        "delete from push",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into push (id, alias, ",
        "userId, phone, allNotifyOn, ",
        "heartNotifyOn, boolPreNotifyOn, ",
        "fallNotifyOn, walkNotifyOn, ",
        "fenceNotifyOn, heartLowThd, ",
        "heartHigThd, createTime, ",
        "updateTime, lbpstart, ",
        "lbpend, hbpstart, ",
        "hbpend)",
        "values (#{id,jdbcType=INTEGER}, #{alias,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER}, #{phone,jdbcType=INTEGER}, #{allNotifyOn,jdbcType=INTEGER}, ",
        "#{heartNotifyOn,jdbcType=INTEGER}, #{boolPreNotifyOn,jdbcType=INTEGER}, ",
        "#{fallNotifyOn,jdbcType=INTEGER}, #{walkNotifyOn,jdbcType=INTEGER}, ",
        "#{fenceNotifyOn,jdbcType=INTEGER}, #{heartLowThd,jdbcType=INTEGER}, ",
        "#{heartHigThd,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{lbpstart,jdbcType=INTEGER}, ",
        "#{lbpend,jdbcType=INTEGER}, #{hbpstart,jdbcType=INTEGER}, ",
        "#{hbpend,jdbcType=INTEGER})"
    })
    int insert(Push record);

    @InsertProvider(type=PushSqlProvider.class, method="insertSelective")
    int insertSelective(Push record);

    @Select({
        "select",
        "id, alias, userId, phone, allNotifyOn, heartNotifyOn, boolPreNotifyOn, fallNotifyOn, ",
        "walkNotifyOn, fenceNotifyOn, heartLowThd, heartHigThd, createTime, updateTime, ",
        "lbpstart, lbpend, hbpstart, hbpend",
        "from push",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="alias", property="alias", jdbcType=JdbcType.INTEGER),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="phone", property="phone", jdbcType=JdbcType.INTEGER),
        @Result(column="allNotifyOn", property="allNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="heartNotifyOn", property="heartNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="boolPreNotifyOn", property="boolPreNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="fallNotifyOn", property="fallNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="walkNotifyOn", property="walkNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="fenceNotifyOn", property="fenceNotifyOn", jdbcType=JdbcType.INTEGER),
        @Result(column="heartLowThd", property="heartLowThd", jdbcType=JdbcType.INTEGER),
        @Result(column="heartHigThd", property="heartHigThd", jdbcType=JdbcType.INTEGER),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="lbpstart", property="lbpstart", jdbcType=JdbcType.INTEGER),
        @Result(column="lbpend", property="lbpend", jdbcType=JdbcType.INTEGER),
        @Result(column="hbpstart", property="hbpstart", jdbcType=JdbcType.INTEGER),
        @Result(column="hbpend", property="hbpend", jdbcType=JdbcType.INTEGER)
    })
    Push selectByPrimaryKey(Integer id);

    @UpdateProvider(type=PushSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Push record);

    @Update({
        "update push",
        "set alias = #{alias,jdbcType=INTEGER},",
          "userId = #{userId,jdbcType=INTEGER},",
          "phone = #{phone,jdbcType=INTEGER},",
          "allNotifyOn = #{allNotifyOn,jdbcType=INTEGER},",
          "heartNotifyOn = #{heartNotifyOn,jdbcType=INTEGER},",
          "boolPreNotifyOn = #{boolPreNotifyOn,jdbcType=INTEGER},",
          "fallNotifyOn = #{fallNotifyOn,jdbcType=INTEGER},",
          "walkNotifyOn = #{walkNotifyOn,jdbcType=INTEGER},",
          "fenceNotifyOn = #{fenceNotifyOn,jdbcType=INTEGER},",
          "heartLowThd = #{heartLowThd,jdbcType=INTEGER},",
          "heartHigThd = #{heartHigThd,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "updateTime = #{updateTime,jdbcType=TIMESTAMP},",
          "lbpstart = #{lbpstart,jdbcType=INTEGER},",
          "lbpend = #{lbpend,jdbcType=INTEGER},",
          "hbpstart = #{hbpstart,jdbcType=INTEGER},",
          "hbpend = #{hbpend,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Push record);

    @UpdateProvider(type=PushSqlProvider.class, method="updatePush")
	int updatePush(Push push);

    @Delete("delete from push where userId=#{userId} and alias=#{alias}")
	int deletePush(Push push);
    
    @Select("Select * from push where userId=#{userId} and alias=#{alias}")
	Push selectPushByAliasAndUserId(Push push);

    @Select("Select * from push where userId=#{userId}")
	List<Push> selectPushByUserId(Integer userId);
    
    
    
}