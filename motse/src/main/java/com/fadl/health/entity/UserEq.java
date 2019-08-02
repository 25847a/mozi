package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户设备关联表
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
@TableName("user_eq")
public class UserEq extends Model<UserEq> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 设备ID
     */
    @TableField("eq_id")
    private Integer eqId;
    /**
     * 关联关系
     */
    private Integer typeof;
    /**
     * 已授权--未授权
     */
    private String authorized;
    /**
     * 默认关注首页显示  0.隐藏  1.显示	
     */
    private Integer follow; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEqId() {
        return eqId;
    }

    public void setEqId(Integer eqId) {
        this.eqId = eqId;
    }

    public Integer getTypeof() {
        return typeof;
    }

    public void setTypeof(Integer typeof) {
        this.typeof = typeof;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public Integer getFollow() {
		return follow;
	}

	public void setFollow(Integer follow) {
		this.follow = follow;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserEq{" +
        "id=" + id +
        ", userId=" + userId +
        ", eqId=" + eqId +
        ", typeof=" + typeof +
        ", authorized=" + authorized +
        ", follow=" + follow +
        "}";
    }
}
