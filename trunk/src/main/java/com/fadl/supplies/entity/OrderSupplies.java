package com.fadl.supplies.entity;
 
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;

/**
 * 订单入库实体类--啊健
 * @author Administrator
 *
 */
public class OrderSupplies{
	/**
     * id
     
	private Long id;*/
	 /**
     * 付款方式(0、转账)
     */
	private Integer paymentType;
	/**
     * 状态(0、待审批 1、待检验 2、已完成 3、订购失败)
     */
	private Integer status;
	/**
     * from总价
     */
	private BigDecimal fromSumMoney;
	/**
     * 备注
     */
	private String remarks;
	 /**
     * 订购仓库编号
     */
	private Long depotId;
	/**
     * 耗材编号
     */
	private Long suppliesId;
	/**
     * 数量
     */
	private Integer num;
    /**
     * 单价
     */
	private BigDecimal money;
    /**
     * 总价
     */
	private BigDecimal sumMoney;
	/**
     * 备注
     */
	private String orderRemarks;
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
	/*public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}*/
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getDepotId() {
		return depotId;
	}
	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}
	public Long getSuppliesId() {
		return suppliesId;
	}
	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}
	public String getOrderRemarks() {
		return orderRemarks;
	}
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getFromSumMoney() {
		return fromSumMoney;
	}
	public void setFromSumMoney(BigDecimal fromSumMoney) {
		this.fromSumMoney = fromSumMoney;
	}
}
