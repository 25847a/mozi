package cn.mozistar.mapper;

import cn.mozistar.pojo.InvitationList;
import org.apache.ibatis.jdbc.SQL;

public class InvitationListSqlProvider {

    public String insertSelective(InvitationList record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("invitation_list");
		if (record.getId() != null) {
			sql.VALUES("id", "#{id,jdbcType=INTEGER}");
		}
		if (record.getUserId() != null) {
			sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
		}
		if (record.getPhone() != null) {
			sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	public String updateByPrimaryKeySelective(InvitationList record) {
		SQL sql = new SQL();
		sql.UPDATE("invitation_list");
		if (record.getUserId() != null) {
			sql.SET("userId = #{userId,jdbcType=INTEGER}");
		}
		if (record.getPhone() != null) {
			sql.SET("phone = #{phone,jdbcType=VARCHAR}");
		}
		sql.WHERE("id = #{id,jdbcType=INTEGER}");
		return sql.toString();
	}

}