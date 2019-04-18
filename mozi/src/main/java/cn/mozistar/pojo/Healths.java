package cn.mozistar.pojo;

import java.util.Date;

public class Healths {
    private Integer id;

	private String phone;

	private Integer hrv;

	private Integer highBloodPressure;

	private Integer lowBloodPressure;

	private Integer heartRate;

	private Integer bloodOxygen;

	private Integer microcirculation;

	private String amedicalreport;

	private Integer respirationrate;

	private Integer userId;

	private Date createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
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

	public String getAmedicalreport() {
		return amedicalreport;
	}

	public void setAmedicalreport(String amedicalreport) {
		this.amedicalreport = amedicalreport == null ? null : amedicalreport.trim();
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
}