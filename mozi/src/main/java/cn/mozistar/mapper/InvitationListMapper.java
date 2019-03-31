package cn.mozistar.mapper;

import cn.mozistar.pojo.InvitationList;

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

public interface InvitationListMapper {
    @Delete({
        "delete from invitation_list",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into invitation_list (id, userId, ",
        "phone)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{phone,jdbcType=VARCHAR})"
    })
    int insert(InvitationList record);

    @InsertProvider(type=InvitationListSqlProvider.class, method="insertSelective")
    int insertSelective(InvitationList record);

    @Select({
        "select",
        "id, userId, phone",
        "from invitation_list",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR)
    })
    InvitationList selectByPrimaryKey(Integer id);

    @UpdateProvider(type=InvitationListSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(InvitationList record);

    @Update({
        "update invitation_list",
        "set userId = #{userId,jdbcType=INTEGER},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(InvitationList record);

    @Select("select * from invitation_list where phone = #{phone} group by userId")
	List<InvitationList> selectByPhone(String phone);

    @Delete("delete from invitation_list where phone = #{phone}")
	int deleteByPhone(String phone);
}