package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 会员制度
 * </p>
 *
 * @author jian
 * @since 2019-07-29
 */
@TableName("member")
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 使用者ID
     */
    private Integer userId;
    /**
     * 话费
     */
    private BigDecimal charges;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 会员等级(0.普通会员  1.高级会员 2.黄金会员 3.VIP会员)
     */
    private Integer member;
    /**
     * 用户到期时间
     */
    private String endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Member{" +
        "id=" + id +
        ", userId=" + userId +
        ", charges=" + charges +
        ", integral=" + integral +
        ", member=" + member +
        ", endTime=" + endTime +
        "}";
    }
}
