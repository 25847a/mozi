package cn.mozistar.mapper;

import cn.mozistar.pojo.Positionig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface PositionigMapper {
    @Delete({
        "delete from positionig",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into positionig (id, coordinate, ",
        "cratetime, userId)",
        "values (#{id,jdbcType=INTEGER}, #{coordinate,jdbcType=VARCHAR}, ",
        "#{cratetime,jdbcType=TIMESTAMP}, #{userId,jdbcType=INTEGER})"
    })
    int insert(Positionig record);

    @InsertProvider(type=PositionigSqlProvider.class, method="insertSelective")
    int insertSelective(Positionig record);

    @Select({
        "select",
        "id, coordinate, cratetime, userId",
        "from positionig",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="coordinate", property="coordinate", jdbcType=JdbcType.VARCHAR),
        @Result(column="cratetime", property="cratetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER)
    })
    Positionig selectByPrimaryKey(Integer id);

    @UpdateProvider(type=PositionigSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Positionig record);

    @Update({
        "update positionig",
        "set coordinate = #{coordinate,jdbcType=VARCHAR},",
          "cratetime = #{cratetime,jdbcType=TIMESTAMP},",
          "userId = #{userId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Positionig record);
}