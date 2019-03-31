package com.fadl.immuneAssay.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 免疫化验效价值设置
 * </p>
 *
 * @author CJ
 * @since 2018-08-25
 */
@TableName("f_immune_assay_setting")
public class ImmuneAssaySetting extends Model<ImmuneAssaySetting> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 免疫名称id
     */
    private Long immuneId;
    /**
     * 效价值最小范围值
     */
    private BigDecimal effectiveMin;
    /**
     * 效价值最大范围值
     */
    private BigDecimal effectiveMax;
    /**
     * 包装后的效价值
     */
    private BigDecimal packingEffective;
    /**
     * 包装后的免疫类型
     */
    private Long packingImmuneId;
    /**
     * 是否删除(0、否 1、是)
     */
    private Integer isDelete;
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

    public BigDecimal getEffectiveMin() {
        return effectiveMin;
    }

    public void setEffectiveMin(BigDecimal effectiveMin) {
        this.effectiveMin = effectiveMin;
    }

    public BigDecimal getEffectiveMax() {
        return effectiveMax;
    }

    public void setEffectiveMax(BigDecimal effectiveMax) {
        this.effectiveMax = effectiveMax;
    }

    public BigDecimal getPackingEffective() {
        return packingEffective;
    }

    public void setPackingEffective(BigDecimal packingEffective) {
        this.packingEffective = packingEffective;
    }

    public Long getPackingImmuneId() {
        return packingImmuneId;
    }

    public void setPackingImmuneId(Long packingImmuneId) {
        this.packingImmuneId = packingImmuneId;
    }

    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

    public String getPlasmaId() {
        return plasmaId;
    }

    public void setPlasmaId(String plasmaId) {
        this.plasmaId = plasmaId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ImmuneAssaySetting{" +
        "id=" + id +
        ", immuneId=" + immuneId +
        ", effectiveMin=" + effectiveMin +
        ", effectiveMax=" + effectiveMax +
        ", packingEffective=" + packingEffective +
        ", packingImmuneId=" + packingImmuneId +
         ", isDelete=" + isDelete +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", plasmaId=" + plasmaId +
        "}";
    }
}
