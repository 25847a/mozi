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
 * 耗材订购记录表
 * </p>
 *
 * @author wangjing
 * @since 2018-04-24
 */
@TableName("f_supplies_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订购单编号
     */
	private Long orderformId;
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
	 * 状态0未入库  1已入库
	 */
	private Integer status;
    /**
     * 备注
     */
	private String remarks;
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

	public Long getOrderformId() {
		return orderformId;
	}

	public void setOrderformId(Long orderformId) {
		this.orderformId = orderformId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "SuppliesOrder{" +
			"id=" + id +
			", orderformId=" + orderformId +
			", depotId=" + depotId +
			", suppliesId=" + suppliesId +
			", num=" + num +
			", money=" + money +
			", sumMoney=" + sumMoney +
			", status=" + status +
			", remarks=" + remarks +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
