package cn.mozistar.vo;


/**
 * 这个是返回图表数据的实体类(啊健)
 * @author Administrator
 *
 */
public class Chart {
	//平均值
	 private Integer hrv;

	 private Integer highBloodPressure;

	 private Integer lowBloodPressure;

	 private Integer heartRate;

	 private Integer bloodOxygen;

	 private Integer microcirculation;

	 private Integer respirationrate;
	 
//	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	 private String date;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
	

}
