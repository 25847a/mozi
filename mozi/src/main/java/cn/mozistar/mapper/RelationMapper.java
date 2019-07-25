package cn.mozistar.mapper;

import cn.mozistar.pojo.Relation;
import cn.mozistar.util.DataRow;

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

public interface RelationMapper {
    @Delete({
        "delete from relation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into relation (id, userId, ",
        "observeId)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{observeId,jdbcType=INTEGER})"
    })
    int insert(Relation record);

    @InsertProvider(type=RelationSqlProvider.class, method="insertSelective")
    int insertSelective(Relation record);

    @Select({
        "select",
        "id, userId, observeId",
        "from relation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="observeId", property="observeId", jdbcType=JdbcType.INTEGER)
    })
    Relation selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RelationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Relation record);

    @Update({
        "update relation",
        "set userId = #{userId,jdbcType=INTEGER},",
          "observeId = #{observeId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Relation record);
    
    @Select("SELECT tt.id as userId,tt.name,tt.avatar FROM relation tp"+
    		" INNER JOIN user tt"+
    		" ON tp.observeId=tt.id"+
    		" WHERE userId=#{userId}")
	List<DataRow> queryObserveInfo(Integer userId);
    
    @Select("select * from relation where userId = #{userId}")
	List<Relation> selectByUserId(Integer userId);
    /**
     * 根据 userId 和 observeId 查询
     * @param relation  #{userId}  #{observeId}
     * @return Relation
     */
    @Select("select * from relation where userId = #{userId} and observeId =#{observeId} ")
	Relation selectRelation(Relation relation);

    @Select("select * from relation where observeId = #{observeId}")
	List<Relation> selectByObserveId(int observeId);

    @Delete("delete from relation where userId = #{userId} and observeId = #{observeId}")
	int deleteRelation(Relation relation);

}