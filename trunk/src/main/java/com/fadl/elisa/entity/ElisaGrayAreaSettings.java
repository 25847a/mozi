package com.fadl.elisa.entity;

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
 * 酶标板灰区设置
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_gray_area_settings")
public class ElisaGrayAreaSettings extends Model<ElisaGrayAreaSettings> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酶标板模板Id
     */
    private Long etId;
    /**
     * cutoff计算公式
     */
    private String cutoffFormula;
    /**
     * 比例值
     */
    private BigDecimal proportionValue;
    /**
     * 常规值
     */
    private BigDecimal conventionalValue;
    /**
     * 小定值
     */
    private BigDecimal minValue;
    /**
     * 大定值
     */
    private BigDecimal maxValue;
    /**
     * 上限为cutoff值0否 1是
     */
    private Integer isMaxWithCutOff;
    /**
     * 选择方案0常规 1比例 2定值
     */
    private Integer optionsValue;
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

    public Long getEtId() {
        return etId;
    }

    public void setEtId(Long etId) {
        this.etId = etId;
    }

    public String getCutoffFormula() {
        return cutoffFormula;
    }

    public void setCutoffFormula(String cutoffFormula) {
        this.cutoffFormula = cutoffFormula;
    }

    public BigDecimal getProportionValue() {
        return proportionValue;
    }

    public void setProportionValue(BigDecimal proportionValue) {
        this.proportionValue = proportionValue;
    }

    public BigDecimal getConventionalValue() {
        return conventionalValue;
    }

    public void setConventionalValue(BigDecimal conventionalValue) {
        this.conventionalValue = conventionalValue;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getIsMaxWithCutOff() {
        return isMaxWithCutOff;
    }

    public void setIsMaxWithCutOff(Integer isMaxWithCutOff) {
        this.isMaxWithCutOff = isMaxWithCutOff;
    }

    public Integer getOptionsValue() {
        return optionsValue;
    }

    public void setOptionsValue(Integer optionsValue) {
        this.optionsValue = optionsValue;
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
        return "ElisaGrayAreaSettings{" +
        "id=" + id +
        ", etId=" + etId +
        ", cutoffFormula=" + cutoffFormula +
        ", proportionValue=" + proportionValue +
        ", conventionalValue=" + conventionalValue +
        ", minValue=" + minValue +
        ", maxValue=" + maxValue +
        ", isMaxWithCutOff=" + isMaxWithCutOff +
        ", optionsValue=" + optionsValue +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
