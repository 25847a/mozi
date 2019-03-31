package com.fadl.immuneAssay.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 疫苗设置
 * </p>
 *
 * @author CJ
 * @since 2018-07-14
 */
@TableName("f_vaccine_setting")
public class Vaccine extends Model<Vaccine> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 免疫类别 id
     */
    private Long immuneId;
    /**
     * 注射方式(0.基础注射 1.加强注射)
     */
    private Integer injectionType;
    /**
     * 耗材库存 id
     */
    private Long suppliesId;
    /**
     * 批号
     */
    private String batchNumber;
    /**
     * 注射量
     */
    private Integer injection;
    /**
     * 有效期(注射多长时间内效价值有效)
     */
    private String validMonth;
    /**
     * 有效开始时间(订购时间)
     */
    private String startDate;
    /**
     * 有效结束时间(个人时间)
     */
    private String endDate;
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

    public Long getImmuneId() {
        return immuneId;
    }

    public void setImmuneId(Long immuneId) {
        this.immuneId = immuneId;
    }

    public Integer getInjectionType() {
        return injectionType;
    }

    public void setInjectionType(Integer injectionType) {
        this.injectionType = injectionType;
    }

    public Long getSuppliesId() {
        return suppliesId;
    }

    public void setSuppliesId(Long suppliesId) {
        this.suppliesId = suppliesId;
    }

    public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Integer getInjection() {
        return injection;
    }

    public void setInjection(Integer injection) {
        this.injection = injection;
    }

    public String getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(String validMonth) {
        this.validMonth = validMonth;
    }

    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
        return "VaccineSetting{" +
        "id=" + id +
        ", immuneId=" + immuneId +
        ", injectionType=" + injectionType +
        ", suppliesId=" + suppliesId +
        ", injection=" + injection +
        ", validMonth=" + validMonth +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
