package com.fadl.supplies.entity;
 
import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * <p>
 * 订购单记录
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
@TableName("f_order_form")
public class OrderForm extends Model<OrderForm> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订购单号
     */
	private String oddNumber;
    /**
     * 付款方式(0、转账)
     */
	private Integer paymentType;
    /**
     * 状态(0、待审批 1、待检验 2、已完成 3、订购失败)
     */
	private Integer status;
    /**
     * 总价
     */
	private BigDecimal sumMoney;
    /**
     * 备注
     */
	private String remarks;
	/**
	 * 检验时间
	 */
	private String quarantineDate;
	/**
	 * 检验人
	 */
	private Long quarantiner;
	/**
	 * 入库时间
	 */
	private String intoStockDate;
	/**
	 * 入库人
	 */
	private Long intoStocker;
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

	public String getOddNumber() {
		return oddNumber;
	}

	public void setOddNumber(String oddNumber) {
		this.oddNumber = oddNumber;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getQuarantineDate() {
		return quarantineDate;
	}

	public void setQuarantineDate(String quarantineDate) {
		this.quarantineDate = quarantineDate;
	}

	public Long getQuarantiner() {
		return quarantiner;
	}

	public void setQuarantiner(Long quarantiner) {
		this.quarantiner = quarantiner;
	}

	public String getIntoStockDate() {
		return intoStockDate;
	}

	public void setIntoStockDate(String intoStockDate) {
		this.intoStockDate = intoStockDate;
	}

	public Long getIntoStocker() {
		return intoStocker;
	}

	public void setIntoStocker(Long intoStocker) {
		this.intoStocker = intoStocker;
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
		return "OrderForm [id=" + id + ", oddNumber=" + oddNumber
				+ ", paymentType=" + paymentType + ", status=" + status
				+ ", sumMoney=" + sumMoney + ", remarks=" + remarks
				+ ", quarantineDate=" + quarantineDate + ", quarantiner="
				+ quarantiner + ", intoStockDate=" + intoStockDate
				+ ", intoStocker=" + intoStocker + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", creater=" + creater
				+ ", updater=" + updater + "]";
	}
}
