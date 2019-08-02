package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 极光推送表
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
@TableName("push")
public class Push extends Model<Push> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 卡片id用来单独设置,与alias一起唯一
     */
    private Integer userId;
    /**
     * 需要通知的id
     */
    private Integer alias;
    /**
     * 总通知开关 
     */
    private Integer allNotifyOn;
    /**
     * 心率通知开关
     */
    private Integer heartNotifyOn;
    /**
     * 血压通知开关
     */
    private Integer boolPreNotifyOn;
    /**
     * 心率低阈值 
     */
    private Integer heartLowThd;
    /**
     * 心率通知高阈值 
     */
    private Integer heartHigThd;
    /**
     * 创建时间 
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 低血压低位值
     */
    private Integer lbpstart;
    /**
     * 低血压高位值
     */
    private Integer lbpend;
    /**
     * 高血压低位值
     */
    private Integer hbpstart;
    /**
     * 高血压高位值
     */
    private Integer hbpend;


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

    public Integer getAlias() {
        return alias;
    }

    public void setAlias(Integer alias) {
        this.alias = alias;
    }

    public Integer getAllNotifyOn() {
        return allNotifyOn;
    }

    public void setAllNotifyOn(Integer allNotifyOn) {
        this.allNotifyOn = allNotifyOn;
    }

    public Integer getHeartNotifyOn() {
        return heartNotifyOn;
    }

    public void setHeartNotifyOn(Integer heartNotifyOn) {
        this.heartNotifyOn = heartNotifyOn;
    }

    public Integer getBoolPreNotifyOn() {
        return boolPreNotifyOn;
    }

    public void setBoolPreNotifyOn(Integer boolPreNotifyOn) {
        this.boolPreNotifyOn = boolPreNotifyOn;
    }

    public Integer getHeartLowThd() {
        return heartLowThd;
    }

    public void setHeartLowThd(Integer heartLowThd) {
        this.heartLowThd = heartLowThd;
    }

    public Integer getHeartHigThd() {
        return heartHigThd;
    }

    public void setHeartHigThd(Integer heartHigThd) {
        this.heartHigThd = heartHigThd;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLbpstart() {
        return lbpstart;
    }

    public void setLbpstart(Integer lbpstart) {
        this.lbpstart = lbpstart;
    }

    public Integer getLbpend() {
        return lbpend;
    }

    public void setLbpend(Integer lbpend) {
        this.lbpend = lbpend;
    }

    public Integer getHbpstart() {
        return hbpstart;
    }

    public void setHbpstart(Integer hbpstart) {
        this.hbpstart = hbpstart;
    }

    public Integer getHbpend() {
        return hbpend;
    }

    public void setHbpend(Integer hbpend) {
        this.hbpend = hbpend;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Push{" +
        "id=" + id +
        ", userId=" + userId +
        ", alias=" + alias +
        ", allNotifyOn=" + allNotifyOn +
        ", heartNotifyOn=" + heartNotifyOn +
        ", boolPreNotifyOn=" + boolPreNotifyOn +
        ", heartLowThd=" + heartLowThd +
        ", heartHigThd=" + heartHigThd +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", lbpstart=" + lbpstart +
        ", lbpend=" + lbpend +
        ", hbpstart=" + hbpstart +
        ", hbpend=" + hbpend +
        "}";
    }
}
