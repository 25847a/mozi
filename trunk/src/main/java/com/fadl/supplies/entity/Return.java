package com.fadl.supplies.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
/**
 * <p>
 * 耗材退还记录表
 * </p>
 *
 * @author wangjing
 * @since 2018-04-24
 */
@TableName("f_supplies_return")
public class Return extends Model<Return> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 批号
     */
	private String batchNumber;
	/**
	 * 耗材ID
	 */
	private Long suppliesId;
    /**
     * 退还单号
     */
	private String returnOrder;
    /**
     * 退还原因
     */
	private String reason;
    /**
     * 退还数量
     */
	private Integer num;
    /**
     * 状态(0、未审核 1、已审核 )
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField(value = "createDate",fill = FieldFill.INSERT )
	private String createDate;
    /**
     * 修改时间
     */
	@TableField(value = "updateDate",fill = FieldFill.INSERT_UPDATE )
	private String updateDate;
    /**
     * 创建人
     */
	@TableField(value = "creater",fill = FieldFill.INSERT )
	private Long creater;
    /**
     * 修改人
     */
	@TableField(value = "updater",fill = FieldFill.INSERT_UPDATE )
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

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Long getSuppliesId() {
		return suppliesId;
	}

	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}

	public String getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(String returnOrder) {
		this.returnOrder = returnOrder;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "SuppliesReturn{" +
			"id=" + id +
			", batchNumber=" + batchNumber +
			", suppliesId=" + suppliesId +
			", returnOrder=" + returnOrder +
			", reason=" + reason +
			", num=" + num +
			", status=" + status +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
