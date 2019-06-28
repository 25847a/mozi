package cn.mozistar.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PushRecord {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 使用者ID
	 */
	private int userId;
	/**
	 * 心率异常值
	 */
	private int heartUnusual;
	/**
	 * 舒张压异常值
	 */
	private int highBloodUnusual;
	/**
	 * 收缩压异常值
	 */
	private int lowBloodUnusual;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getHeartUnusual() {
		return heartUnusual;
	}
	public void setHeartUnusual(int heartUnusual) {
		this.heartUnusual = heartUnusual;
	}
	public int getHighBloodUnusual() {
		return highBloodUnusual;
	}
	public void setHighBloodUnusual(int highBloodUnusual) {
		this.highBloodUnusual = highBloodUnusual;
	}
	public int getLowBloodUnusual() {
		return lowBloodUnusual;
	}
	public void setLowBloodUnusual(int lowBloodUnusual) {
		this.lowBloodUnusual = lowBloodUnusual;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
