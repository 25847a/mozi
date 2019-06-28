package cn.mozistar.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 健康数据校准表
 * @author Admin
 *
 */
public class Healthdao {
    private Integer id;

    private Integer hrv;

    private Integer highBloodPressure;

    private Integer lowBloodPressure;

    private Integer heartRate;

    private Integer bloodOxygen;

    private Integer microcirculation;

    private Integer respirationrate;

    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    private Date createtime;

    private String phone;

    private String amedicalreport;
    
    private String waveform;

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

    public Integer getHighBloodPressure() {
        return highBloodPressure;
    }

    public void setHighBloodPressure(Integer highBloodPressure) {
        this.highBloodPressure = highBloodPressure;
    }

    public Integer getLowBloodPressure() {
        return lowBloodPressure;
    }

    public void setLowBloodPressure(Integer lowBloodPressure) {
        this.lowBloodPressure = lowBloodPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(Integer bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public Integer getMicrocirculation() {
        return microcirculation;
    }

    public void setMicrocirculation(Integer microcirculation) {
        this.microcirculation = microcirculation;
    }

    public Integer getRespirationrate() {
        return respirationrate;
    }

    public void setRespirationrate(Integer respirationrate) {
        this.respirationrate = respirationrate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAmedicalreport() {
        return amedicalreport;
    }

    public void setAmedicalreport(String amedicalreport) {
        this.amedicalreport = amedicalreport == null ? null : amedicalreport.trim();
    }

	public String getWaveform() {
		return waveform;
	}

	public void setWaveform(String waveform) {
		this.waveform = waveform == null ? null : waveform.trim();
	}

}