package com.fadl.supplies.entity;
 
import java.io.Serializable;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 耗材信息表
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
@TableName("f_supplies_info")
public class Info extends Model<Info> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 耗材编号
     */
	private String number;
    /**
     * 所属类别
     */
	private Long typeId;
    /**
     * 单位编号
     */
	private Long unitId;
    /**
     * 供应链编号
     */
	private Long supplyId;
    /**
     * 耗材名称
     */
	private String name;
	/**
	 * 规格
	 */
	private String standard;
	/**
	 * 浓度
	 */
	private String thick;
    /**
     * 进货价格
     */
	private BigDecimal money;
    /**
     * 最小库存
     */
	private Integer minStock;
    /**
     * 最大库存
     */
	private Integer maxStock;
    /**
     * 用于模块(0、采浆 1、化验 2、免疫)
     */
	private Integer apply;
    /**
     * 备注
     */
	private String remark;
	/**
	 * 类型（0.试剂 1.质控品）
	 */
	private Integer type;
	/**
	 * 是否禁用（0、否 1、是）
	 */
	private Integer disable;
	 /**
     * 是否删除(0、否 1、是)
     */
	private Integer isDelete;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getThick() {
		return thick;
	}

	public void setThick(String thick) {
		this.thick = thick;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}

	public Integer getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Integer maxStock) {
		this.maxStock = maxStock;
	}

	public Integer getApply() {
		return apply;
	}

	public void setApply(Integer apply) {
		this.apply = apply;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getDisable() {
		return disable;
	}

	public void setDisable(Integer disable) {
		this.disable = disable;
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
		return "SuppliesInfo{" +
			"id=" + id +
			", number=" + number +
			", typeId=" + typeId +
			", unitId=" + unitId +
			", supplyId=" + supplyId +
			", name=" + name +
			", standard=" + standard +
			", thick=" + thick +
			", money=" + money +
			", minStock=" + minStock +
			", maxStock=" + maxStock +
			", apply=" + apply +
			", remark=" + remark +
			", type=" + type +
			", isDelete=" + isDelete +
			", disable=" + disable +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
