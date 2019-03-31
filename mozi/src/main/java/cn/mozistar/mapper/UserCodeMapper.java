package cn.mozistar.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import cn.mozistar.pojo.UserCode;

public interface UserCodeMapper {
    @Delete({
        "delete from usercode",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into usercode (id, phone, ",
        "code)",
        "values (#{id,jdbcType=INTEGER}, #{phone,jdbcType=VARCHAR}, ",
        "#{code,jdbcType=VARCHAR})"
    })
    int insert(UserCode record);

    @InsertProvider(type=UserCodeSqlProvider.class, method="insertSelective")
    int insertSelective(UserCode record);

    @Select({
        "select",
        "id, phone, code",
        "from usercode",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR)
    })
    UserCode selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserCodeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserCode record);

    @Update({
        "update usercode",
        "set phone = #{phone,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserCode record);
    
    @Select("select * from usercode where phone=#{phone}")
    UserCode selectUserCode(String phone);

    @Update({
        "update usercode",
        "set code = #{code,jdbcType=VARCHAR}",
        "where phone = #{phone}"
    })
	int update(UserCode c);
    
}