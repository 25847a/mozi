package com.fadl.supplies.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 出库表
 * </p>
 *
 * @author CJ
 * @since 2018-05-28
 */
@TableName("f_supplies_outstock")
public class Outstock extends Model<Outstock> {

    private static final long serialVersionUID = 1L;
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
     * 出库数量
     */
    private Integer outNumber;
    /**
     * 批号
     */
    private String batchNumber;
    /**
     * 到期时间
     */
    private String expiryTime;
    /**
     * 耗材状态(0、可用 1、不可用）
     */
    private Integer status;
    /**
     * 是否可用（0、可用 1、不否可）
     */
    private Integer isDisable;
    /**
     * 库房ID
     */
    private Long stockId;
	/**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDate;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDate;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creater;
    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    public Integer getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(Integer outNumber) {
        this.outNumber = outNumber;
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

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
    public String getRemarks() {
        return remarks;
    }

    public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
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
        return "SuppliesOutstock{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", suppliesId=" + suppliesId +
        ", outNumber=" + outNumber +
        ", batchNumber=" + batchNumber +
        ", expiryTime=" + expiryTime +
        ", status=" + status +
        ", remarks=" + remarks +
        ", stockId=" + stockId +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
