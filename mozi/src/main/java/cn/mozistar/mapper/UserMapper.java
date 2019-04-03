package cn.mozistar.mapper;

import cn.mozistar.pojo.User;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
	@Delete({ "delete from user", "where id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	@Insert({ "insert into user (id, account, ", "password, name, ", "age, gender, phone, ", "address, avatar, ",
			"createtime, atlasttime, ", "weight, height, born, ", "updatetime, code)",
			"values (#{id,jdbcType=INTEGER}, #{account,jdbcType=VARCHAR}, ",
			"#{password,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
			"#{age,jdbcType=INTEGER}, #{gender,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
			"#{address,jdbcType=VARCHAR}, #{avatar,jdbcType=VARCHAR}, ",
			"#{createtime,jdbcType=TIMESTAMP}, #{atlasttime,jdbcType=TIMESTAMP}, ",
			"#{weight,jdbcType=REAL}, #{height,jdbcType=REAL}, #{born,jdbcType=TIMESTAMP}, ",
			"#{updatetime,jdbcType=TIMESTAMP}, #{code,jdbcType=VARCHAR})" })
	int insert(User record);

	@InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
	int insertSelective(User record);

	@Select({ "select", "id, account, password, name, age, gender, phone, address, avatar, createtime, ",
			"atlasttime, weight, height, born, updatetime,coordinate,calibration, code", "from user", "where id = #{id,jdbcType=INTEGER}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "account", property = "account", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "age", property = "age", jdbcType = JdbcType.INTEGER),
			@Result(column = "gender", property = "gender", jdbcType = JdbcType.VARCHAR),
			@Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
			@Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "createtime", property = "createtime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "atlasttime", property = "atlasttime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "weight", property = "weight", jdbcType = JdbcType.REAL),
			@Result(column = "height", property = "height", jdbcType = JdbcType.REAL),
			@Result(column = "born", property = "born", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "updatetime", property = "updatetime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "coordinate", property = "coordinate", jdbcType = JdbcType.VARCHAR),
			@Result(column = "calibration", property = "calibration", jdbcType = JdbcType.INTEGER),
			@Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR) })
	User selectByPrimaryKey(Integer id);

	@UpdateProvider(type = UserSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(User record);

	@Update({ "update user", "set account = #{account,jdbcType=VARCHAR},", "password = #{password,jdbcType=VARCHAR},",
			"name = #{name,jdbcType=VARCHAR},", "age = #{age,jdbcType=INTEGER},",
			"gender = #{gender,jdbcType=VARCHAR},", "phone = #{phone,jdbcType=VARCHAR},",
			"address = #{address,jdbcType=VARCHAR},", "avatar = #{avatar,jdbcType=VARCHAR},",
			"createtime = #{createtime,jdbcType=TIMESTAMP},", "atlasttime = #{atlasttime,jdbcType=TIMESTAMP},",
			"weight = #{weight,jdbcType=REAL},", "height = #{height,jdbcType=REAL},",
			"born = #{born,jdbcType=TIMESTAMP},", "updatetime = #{updatetime,jdbcType=TIMESTAMP},",
			"code = #{code,jdbcType=VARCHAR}" ,"coordinate = #{coordinate,jdbcType=VARCHAR}","calibration=#{calibration,jdbcType=INTEGER}", "where id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(User record);

	/**
	 * 用户账号查询用户
	 * 
	 * @param account
	 */
	@Select("select * from user where account = #{account}")
	User selectUserByAccount(String account);

	@Select("<script>select * from user where id in "
			+ "<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>" 
			+ "#{item}"
			+ "</foreach>" + "</script>")
	//@Results(value = { @Result(column = "user_name", property = "username") })
	List<User> selectUserList(@Param(value = "idList") List<Integer> idList);

}