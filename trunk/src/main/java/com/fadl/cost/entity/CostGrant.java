package com.fadl.cost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>费用发放记录</p>
 * 
 * @author zhanll
 * @since 2018-5-5
 * */
@TableName("f_cost_grant")
public class CostGrant extends Model<CostGrant>{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	@TableId(value="id",type=IdType.AUTO)
	private Long id;
	
	/**
	 * 全登记号
	 */
	private Long allId;
	
	/**
	 * 浆员卡号
	 */
	private String providerNo;
	
	/**
	 * 发放金额
	 */
	private BigDecimal money;
	
	/**
	 * 补助类型
	 */
	private Integer roadFeeType;
	
	/** 
	 * 补助金额
	 */
	private BigDecimal roadFee;
	
	/**
	 * 是否发放
	 */
	private Integer isGrant;
	
	/**
	 * 是否发放路费（0.不可发，1.可发）
	 */
	private Integer isRoadFee;
	
	/**
	 * 审核状态
	 */
	private Integer status;
	
	/**
	 * 采浆时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:dd")
	private String collDate;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 验证类型(0、人脸识别 1、静脉验证)
	 */
	private Integer type;
	
	/**
	 * 是否验证身份(0、未验证 1、已验证)
	 */
	private Integer isIdentity;
	
	/**
	 * 创建时间
	 */
	@TableField(value="createDate",fill=FieldFill.INSERT)
	private String createDate;
	
	/**
	 * 修改时间
	 */
	@TableField(value="updateDate",fill=FieldFill.INSERT_UPDATE)
	private String updateDate;
	
	/**
	 * 创建人
	 */
	@TableField(value="creater",fill=FieldFill.INSERT)
	private Long creater;
	
	/**
	 * 修改人
	 */
	@TableField(value="updater",fill=FieldFill.INSERT_UPDATE)
	private Long updater;
	
	/**
	 * 所属浆站标识
	 */
	@TableField(fill = FieldFill.INSERT)
	private String plasmaId;
	
	
	public String getPlasmaId() {
		return plasmaId;
	}

	public void setPlasmaId(String plasmaId) {
		this.plasmaId = plasmaId;
	}

	public Long getId() {
		return id;
	}
	
	public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getIsGrant() {
		return isGrant;
	}

	public void setIsGrant(Integer isGrant) {
		this.isGrant = isGrant;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCollDate() {
		return collDate;
	}

	public void setCollDate(String collDate) {
		this.collDate = collDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsIdentity() {
		return isIdentity;
	}

	public void setIsIdentity(Integer isIdentity) {
		this.isIdentity = isIdentity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Integer getRoadFeeType() {
		return roadFeeType;
	}

	public void setRoadFeeType(Integer roadFeeType) {
		this.roadFeeType = roadFeeType;
	}

	public BigDecimal getRoadFee() {
		return roadFee;
	}

	public void setRoadFee(BigDecimal roadFee) {
		this.roadFee = roadFee;
	}

	public Integer getIsRoadFee() {
		return isRoadFee;
	}

	public void setIsRoadFee(Integer isRoadFee) {
		this.isRoadFee = isRoadFee;
	}



	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	@Override
	public String toString() {
		return "CostGrant{"+"id="+id+
				",allId="+allId+
				",providerNo="+providerNo+
				",money="+money+
				",roadFeeType="+roadFeeType+
				",roadFee="+roadFeeType+
				",isGrant="+isGrant+
				",status="+status+
				",collDate="+collDate+
				",isIdentity="+isIdentity+
				",type="+type+
				",remarks="+remarks+
				",createDate="+createDate+
				",updateDate="+updateDate+
				",creater="+creater+
				",updater="+creater+"}";
	}
}
