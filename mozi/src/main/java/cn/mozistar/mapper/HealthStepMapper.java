package cn.mozistar.mapper;

import cn.mozistar.pojo.Healthstep;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface HealthStepMapper {

    @Insert({"insert into healthstep (id, stepWhen, carrieroad, userId,  createtime)",
        "values (#{id,jdbcType=INTEGER}, #{stepWhen,jdbcType=INTEGER}, ",
        "#{carrieroad,jdbcType=INTEGER},#{userId,jdbcType=INTEGER}, #{createtime,jdbcType=TIMESTAMP})"})
    int insertHealthstep(Healthstep healthstep);

    @Select("Select * from healthstep where userId=#{userId} ORDER BY createtime DESC LIMIT 1")
    Healthstep queryHealthstep(int userId);
    
}