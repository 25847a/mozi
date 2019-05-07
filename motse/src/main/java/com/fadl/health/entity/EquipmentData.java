package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 健康数据(原始数据)
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@TableName("equipment_data")
public class EquipmentData extends Model<EquipmentData> {

    private static final long serialVersionUID = 1L;

    /**
     * Id 自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 心率
     */
    private Integer heartrate;
    /**
     * 高压
     */
    private Integer highpressure;
    /**
     * 底压
     */
    private Integer bottompressure;
    /**
     * 血压
     */
    private Integer bloodpressure;
    /**
     * 微循环
     */
    private Integer mocrocirculation;
    /**
     * 呼吸
     */
    private Integer breathe;
    /**
     * 睡眠
     */
    private Double sleeping;
    /**
     * 当天步数
     */
    @TableField("step_when")
    private Integer stepWhen;
    /**
     * 卡里路
     */
    private Integer carrieroad;
    /**
     * 久坐
     */
    private String sedentary;
    /**
     * 运动状态
     */
    private Integer movementstate;
    /**
     * 体温
     */
    private Integer bodytemp;
    /**
     * 湿度
     */
    private Integer humidity;
    /**
     * 冲撞
     */
    private Integer crash;
    /**
     * 数据上传时间
     */
    private String createtime;
    /**
     * 血氧
     */
    private Integer qxygen;
    /**
     * 睡眠状态
     */
    @TableField("sleeping_s")
    private Integer sleepingS;
    /**
     * 跑步状态(）
     */
    @TableField("run_s")
    private Integer runS;
    /**
     * 每个小时步数
     */
    @TableField("step_time")
    private Integer stepTime;
    /**
     * 每天步数目标
     */
    @TableField("step_each")
    private Integer stepEach;
    /**
     * 心跳变异
     */
    private Integer hrv;
    /**
     * 情绪
     */
    private Integer mood;


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

    public Integer getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(Integer heartrate) {
        this.heartrate = heartrate;
    }

    public Integer getHighpressure() {
        return highpressure;
    }

    public void setHighpressure(Integer highpressure) {
        this.highpressure = highpressure;
    }

    public Integer getBottompressure() {
        return bottompressure;
    }

    public void setBottompressure(Integer bottompressure) {
        this.bottompressure = bottompressure;
    }

    public Integer getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(Integer bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public Integer getMocrocirculation() {
        return mocrocirculation;
    }

    public void setMocrocirculation(Integer mocrocirculation) {
        this.mocrocirculation = mocrocirculation;
    }

    public Integer getBreathe() {
        return breathe;
    }

    public void setBreathe(Integer breathe) {
        this.breathe = breathe;
    }

    public Double getSleeping() {
        return sleeping;
    }

    public void setSleeping(Double sleeping) {
        this.sleeping = sleeping;
    }

    public Integer getStepWhen() {
        return stepWhen;
    }

    public void setStepWhen(Integer stepWhen) {
        this.stepWhen = stepWhen;
    }

    public Integer getCarrieroad() {
        return carrieroad;
    }

    public void setCarrieroad(Integer carrieroad) {
        this.carrieroad = carrieroad;
    }

    public String getSedentary() {
        return sedentary;
    }

    public void setSedentary(String sedentary) {
        this.sedentary = sedentary;
    }

    public Integer getMovementstate() {
        return movementstate;
    }

    public void setMovementstate(Integer movementstate) {
        this.movementstate = movementstate;
    }

    public Integer getBodytemp() {
        return bodytemp;
    }

    public void setBodytemp(Integer bodytemp) {
        this.bodytemp = bodytemp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getCrash() {
        return crash;
    }

    public void setCrash(Integer crash) {
        this.crash = crash;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getQxygen() {
        return qxygen;
    }

    public void setQxygen(Integer qxygen) {
        this.qxygen = qxygen;
    }

    public Integer getSleepingS() {
        return sleepingS;
    }

    public void setSleepingS(Integer sleepingS) {
        this.sleepingS = sleepingS;
    }

    public Integer getRunS() {
        return runS;
    }

    public void setRunS(Integer runS) {
        this.runS = runS;
    }

    public Integer getStepTime() {
        return stepTime;
    }

    public void setStepTime(Integer stepTime) {
        this.stepTime = stepTime;
    }

    public Integer getStepEach() {
        return stepEach;
    }

    public void setStepEach(Integer stepEach) {
        this.stepEach = stepEach;
    }

    public Integer getHrv() {
        return hrv;
    }

    public void setHrv(Integer hrv) {
        this.hrv = hrv;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EquipmentData{" +
        "id=" + id +
        ", userId=" + userId +
        ", heartrate=" + heartrate +
        ", highpressure=" + highpressure +
        ", bottompressure=" + bottompressure +
        ", bloodpressure=" + bloodpressure +
        ", mocrocirculation=" + mocrocirculation +
        ", breathe=" + breathe +
        ", sleeping=" + sleeping +
        ", stepWhen=" + stepWhen +
        ", carrieroad=" + carrieroad +
        ", sedentary=" + sedentary +
        ", movementstate=" + movementstate +
        ", bodytemp=" + bodytemp +
        ", humidity=" + humidity +
        ", crash=" + crash +
        ", createtime=" + createtime +
        ", qxygen=" + qxygen +
        ", sleepingS=" + sleepingS +
        ", runS=" + runS +
        ", stepTime=" + stepTime +
        ", stepEach=" + stepEach +
        ", hrv=" + hrv +
        ", mood=" + mood +
        "}";
    }
}
