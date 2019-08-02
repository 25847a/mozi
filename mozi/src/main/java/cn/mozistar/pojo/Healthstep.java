package cn.mozistar.pojo;

import java.util.Date;

public class Healthstep{
	
    private Integer id;

	private Integer carrieroad;

	private Integer stepWhen;

	private Integer userId;

	private Date createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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