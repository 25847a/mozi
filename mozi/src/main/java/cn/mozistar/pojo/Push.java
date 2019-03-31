package cn.mozistar.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Push {
    private Integer id;

    private Integer alias;

    private Integer userId;

    private Integer phone;

    private Integer allNotifyOn;

    private Integer heartNotifyOn;

    private Integer boolPreNotifyOn;

    private Integer fallNotifyOn;

    private Integer walkNotifyOn;

    private Integer fenceNotifyOn;

    private Integer heartLowThd;

    private Integer heartHigThd;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    private Date updateTime;

    private Integer lbpstart;

    private Integer lbpend;

    private Integer hbpstart;

    private Integer hbpend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlias() {
        return alias;
    }

    public void setAlias(Integer alias) {
        this.alias = alias;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
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

    public Integer getFallNotifyOn() {
        return fallNotifyOn;
    }

    public void setFallNotifyOn(Integer fallNotifyOn) {
        this.fallNotifyOn = fallNotifyOn;
    }

    public Integer getWalkNotifyOn() {
        return walkNotifyOn;
    }

    public void setWalkNotifyOn(Integer walkNotifyOn) {
        this.walkNotifyOn = walkNotifyOn;
    }

    public Integer getFenceNotifyOn() {
        return fenceNotifyOn;
    }

    public void setFenceNotifyOn(Integer fenceNotifyOn) {
        this.fenceNotifyOn = fenceNotifyOn;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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
}