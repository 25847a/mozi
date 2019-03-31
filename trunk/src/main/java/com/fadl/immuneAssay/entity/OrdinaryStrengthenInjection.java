package com.fadl.immuneAssay.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 注射管理-普免加强针注射
 * </p>
 *
 * @author zll
 * @since 2018-07-17
 */
@TableName("f_ordinary_strengthen_injection")
public class OrdinaryStrengthenInjection extends Model<OrdinaryStrengthenInjection> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 免疫类型 id
     */
    private Long immuneId;
    /**
     * 第几针
     */
    private Integer num;
    /**
     * 注射量
     */
    private BigDecimal injection;
    /**
     * 疫苗设置 id
     */
    private Long vaccineId;
    /**
     * 有无异常( 0.无   1.有 )
     */
    private Integer status;
    /**
     * 异常原因
     */
    private String remarks;
    /**
     * 是否注射(0.未注射 1.已注射)
     */
    private Integer isShot;
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

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Long getImmuneId() {
        return immuneId;
    }

    public void setImmuneId(Long immuneId) {
        this.immuneId = immuneId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getInjection() {
        return injection;
    }

    public void setInjection(BigDecimal injection) {
        this.injection = injection;
    }

    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
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

    public Integer getIsShot() {
        return isShot;
    }

    public void setIsShot(Integer isShot) {
        this.isShot = isShot;
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
        return "OrdinaryStrengthenInjection{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", immuneId=" + immuneId +
        ", num=" + num +
        ", injection=" + injection +
        ", vaccineId=" + vaccineId +
        ", status=" + status +
        ", remarks=" + remarks +
        ", isShot=" + isShot +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
