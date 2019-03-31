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
 * 免疫化验
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@TableName("f_immune_assay")
public class ImmuneAssay extends Model<ImmuneAssay> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 全登记号
     */
    private Long allId;
    /**
     * 序号
     */
    private Integer number;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 效价值
     */
    private BigDecimal effectiveValue;
    /**
     * 化验结果（0.合格 1.不合格）
     */
    private Integer result;
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
     * 效价后的免疫类型Id
     */
    private Long immuneId;
    /**
     * 原免疫类型Id
     */
    private Long oldImmuneId;
	/**
     * 0：未化验；1：已化验
     * */
    private Integer isAssay;
    
    /**
     * 备注
     * */
    private String remarks;
    
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

	public Integer getIsAssay() {
		return isAssay;
	}

	public void setIsAssay(Integer isAssay) {
		this.isAssay = isAssay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAllId() {
        return allId;
    }

    public void setAllId(Long allId) {
        this.allId = allId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public BigDecimal getEffectiveValue() {
        return effectiveValue;
    }

    public void setEffectiveValue(BigDecimal effectiveValue) {
        this.effectiveValue = effectiveValue;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
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

    public Long getImmuneId() {
		return immuneId;
	}

	public void setImmuneId(Long immuneId) {
		this.immuneId = immuneId;
	}

	
	public Long getOldImmuneId() {
		return oldImmuneId;
	}

	public void setOldImmuneId(Long oldImmuneId) {
		this.oldImmuneId = oldImmuneId;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ImmuneAssay{" +
        "id=" + id +
        ", allId=" + allId +
        ", number=" + number +
        ", providerNo=" + providerNo +
        ", effectiveValue=" + effectiveValue +
        ", result=" + result +
        ", isAssay=" + isAssay +
        ", remarks=" + remarks +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", immuneId=" + immuneId +
        ", oldImmuneId=" + oldImmuneId +
         ", plasmaId=" + plasmaId +
        "}";
    }
}
