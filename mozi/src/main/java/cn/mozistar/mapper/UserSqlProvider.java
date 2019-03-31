package cn.mozistar.mapper;

import cn.mozistar.pojo.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAccount() != null) {
            sql.VALUES("account", "#{account,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=INTEGER}");
        }
        
        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("address", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getAvatar() != null) {
            sql.VALUES("avatar", "#{avatar,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createtime", "#{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAtlasttime() != null) {
            sql.VALUES("atlasttime", "#{atlasttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getWeight() != null) {
            sql.VALUES("weight", "#{weight,jdbcType=REAL}");
        }
        
        if (record.getHeight() != null) {
            sql.VALUES("height", "#{height,jdbcType=REAL}");
        }
        
        if (record.getBorn() != null) {
            sql.VALUES("born", "#{born,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdatetime() != null) {
            sql.VALUES("updatetime", "#{updatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("user");
        
        if (record.getAccount() != null) {
            sql.SET("account = #{account,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            sql.SET("age = #{age,jdbcType=INTEGER}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.SET("address = #{address,jdbcType=VARCHAR}");
        }
        
        if (record.getAvatar() != null) {
            sql.SET("avatar = #{avatar,jdbcType=VARCHAR}");
        }
        
        if (record.getCreatetime() != null) {
            sql.SET("createtime = #{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAtlasttime() != null) {
            sql.SET("atlasttime = #{atlasttime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getWeight() != null) {
            sql.SET("weight = #{weight,jdbcType=REAL}");
        }
        
        if (record.getHeight() != null) {
            sql.SET("height = #{height,jdbcType=REAL}");
        }
        
        if (record.getBorn() != null) {
            sql.SET("born = #{born,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdatetime() != null) {
            sql.SET("updatetime = #{updatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }
        if (record.getCoordinate() != null) {
        	sql.SET("coordinate = #{coordinate,jdbcType=VARCHAR}");
        }
        if (record.getCalibration() != null) {
        	sql.SET("calibration = #{calibration,jdbcType=INTEGER}");
        }
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}