package cn.mozistar.mapper;

import cn.mozistar.pojo.User;
import cn.mozistar.util.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
			"atlasttime, weight,walkCount, height, born, updatetime,coordinate,calibration, code", "from user", "where id = #{id,jdbcType=INTEGER}" })
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
			@Result(column = "walkCount", property = "walkCount", jdbcType = JdbcType.INTEGER),
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
	/**
	 * 首页数据
	 * @param account
	 */
	@Select("SELECT tp.id,tp.name,tp.avatar,tp.coordinate,tt.heartRate,tt.bloodOxygen,tt.microcirculation,tt.respirationrate,"+
	"tt.stepWhen,tt.highBloodPressure,tt.lowBloodPressure,tt.hrv,tt.mood,tt.carrieroad,tt.arrhythmia,tt.waveform,DATE_FORMAT(tt.createtime,'%Y-%m-%d %H:%m:%s') as createtime FROM user tp"+
	"INNER JOIN (SELECT * FROM health WHERE userId=#{userId} ORDER BY createtime DESC LIMIT 1) tt"+
	"ON tp.id=tt.userId WHERE tp.id=#{userId}")
	public DataRow selectHomePage111(int userId);
	/**
	 * 首页数据
	 * @param account
	 */
	@Select("SELECT id as userId,name,avatar,coordinate FROM user WHERE id=#{userId}")
	public DataRow selectHomePage(int userId);
	
	
	
	
	@Select("<script>select * from user where id in "
			+ "<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>" 
			+ "#{item}"
			+ "</foreach>" + "</script>")
	List<User> selectUserList(@Param(value = "idList") List<Integer> idList);
	/**
	 * 修改目标步数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Update("update user set walkCount=#{walkCount} where id = #{userId}")
	int updateWalkCount(Map<String,Object> map)throws SQLException;
}