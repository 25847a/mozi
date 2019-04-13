package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 惊凡给的数据表
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@TableName("jfhealth")
public class Health extends Model<Health> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * HRV
     */
    @TableField("HRV")
    private Integer hrv;
    /**
     * 高血压
     */
    @TableField("sbp_ave")
    private Integer sbpAve;
    /**
     * 低压
     */
    @TableField("dbp_ave")
    private Integer dbpAve;
    /**
     * 心率
     */
    private Integer Heartrate;
    /**
     * 血氧
     */
    private Integer Bloodoxygen;
    /**
     * 微循环
     */
    private Integer microcirculation;
    /**
     * 体检报告
     */
    private String Amedicalreport;
    /**
     * 呼吸频率
     */
    private Integer respirationrate;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 设备号
     */
    private String imei;
    /**
     * 数据上传时间
     */
    private String createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHrv() {
        return hrv;
    }

    public void setHrv(Integer hrv) {
        this.hrv = hrv;
    }

    public Integer getSbpAve() {
        return sbpAve;
    }

    public void setSbpAve(Integer sbpAve) {
        this.sbpAve = sbpAve;
    }

    public Integer getDbpAve() {
        return dbpAve;
    }

    public void setDbpAve(Integer dbpAve) {
        this.dbpAve = dbpAve;
    }

    public Integer getHeartrate() {
        return Heartrate;
    }

    public void setHeartrate(Integer Heartrate) {
        this.Heartrate = Heartrate;
    }

    public Integer getBloodoxygen() {
        return Bloodoxygen;
    }

    public void setBloodoxygen(Integer Bloodoxygen) {
        this.Bloodoxygen = Bloodoxygen;
    }

    public Integer getMicrocirculation() {
        return microcirculation;
    }

    public void setMicrocirculation(Integer microcirculation) {
        this.microcirculation = microcirculation;
    }

    public String getAmedicalreport() {
        return Amedicalreport;
    }

    public void setAmedicalreport(String Amedicalreport) {
        this.Amedicalreport = Amedicalreport;
    }

    public Integer getRespirationrate() {
        return respirationrate;
    }

    public void setRespirationrate(Integer respirationrate) {
        this.respirationrate = respirationrate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Jfhealth{" +
        "id=" + id +
        ", hrv=" + hrv +
        ", sbpAve=" + sbpAve +
        ", dbpAve=" + dbpAve +
        ", Heartrate=" + Heartrate +
        ", Bloodoxygen=" + Bloodoxygen +
        ", microcirculation=" + microcirculation +
        ", Amedicalreport=" + Amedicalreport +
        ", respirationrate=" + respirationrate +
        ", phone=" + phone +
        ", imei=" + imei +
        ", createtime=" + createtime +
        "}";
    }
}
