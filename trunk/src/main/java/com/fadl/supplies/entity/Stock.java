package com.fadl.supplies.entity;

import java.io.Serializable;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fadl.common.DateUtil;
/**
 * <p>
 * 耗材库存记录
 * </p>
 *
 * @author wangjing
 * @since 2018-04-24
 */
@TableName("f_supplies_stock")
public class Stock extends Model<Stock> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订购单编号
     */
	private Long orderId;
    /**
     * 耗材编号
     */
	private Long suppliesId;
    /**
     * 剩余数量
     */
	private Integer surplusNumber;
    /**
     * 批号
     */
	private String batchNumber;
    /**
     * 到期时间
     */
	private String expiryTime;
	 /**
     * 开始时间
     */
	private String startTime;
	 /**
     * 结束时间
     */
	private String endTime;
    /**
     * 耗材状态  是否禁用(0.否 1.是)
     */
	private Integer status;
	/**
     * 类型（0.试剂 1.质控品）
     */
	private Integer type;
    /**
     * 备注
     */
	private String remarks;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT )
	private String createDate;
    /**
     * 修改时间
     */
	@TableField(fill = FieldFill.INSERT )
	private String updateDate;
    /**
     * 创建人
     */
	@TableField(fill = FieldFill.INSERT_UPDATE )
	private Long creater;
    /**
     * 修改人
     */
	@TableField(fill = FieldFill.INSERT_UPDATE )
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSuppliesId() {
		return suppliesId;
	}

	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}

	public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getExpiryTime() {
		return expiryTime;
	}

	public String getExpiryTime1() {
		 try {
				return StringUtils.isEmpty(expiryTime)?null:DateUtil.formatDate(DateUtil.formatDate(expiryTime, DateUtil.yyyy_MM_dd_EN), DateUtil.yyyy_MM_dd_EN);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return null;
	}
	
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SuppliesStock{" +
			"id=" + id +
			", orderId=" + orderId +
			", suppliesId=" + suppliesId +
			", surplusNumber=" + surplusNumber +
			", batchNumber=" + batchNumber +
			", expiryTime=" + expiryTime +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", status=" + status +
			", type=" + type +
			", remarks=" + remarks +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
