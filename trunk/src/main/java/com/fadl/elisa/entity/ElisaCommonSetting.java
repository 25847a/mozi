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
 * 酶标板常用设置表
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_common_setting")
public class ElisaCommonSetting extends Model<ElisaCommonSetting> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酶标板模板ID
     */
    private Long etId;
    /**
     * 是否使用阴性阈值0 否 1是
     */
    private Integer useNegativeThreshold;
    /**
     * 阴性阈值
     */
    private BigDecimal negativeThresholdValue;
    /**
     * 是否使用阳性阈值 0否 1是
     */
    private Integer usePositiveThreshold;
    /**
     * 阳性阈值
     */
    private BigDecimal positiveThresholdValue;
    /**
     * 阳性下限阈值运算符
     */
    private Integer positiveMinOperator;
    /**
     * 阳性上限阈值运算符
     */
    private Integer positiveMaxOperator;
    /**
     * 使用阳性下限值(1000为取平均值)
     */
    private BigDecimal positiveMinValue;
    
    /**
     * 阴性上限值
     */
    private BigDecimal ecsnegativemaxValue;
    /**
     * 阴性下限值
     */
    private BigDecimal ecsnegativeMinValue;
    /**
     * 阳性上限值
     */
    private BigDecimal ecspositiveMaxValue;
    /**
     * 阳性下限值
     */
    private BigDecimal ecspositiveMinValue;
    /**
     * 使用阳性上限值(1000为取平均值)
     */
    private BigDecimal positiveMaxValue;
    /**
     * 阴性下限阈值运算符
     */
    private Integer negativeMinOperator;
    /**
     * 阴性上限阈值运算符
     */
    private Integer negativeMaxOperator;
    /**
     * 使用阴性下限阈值(1000为取平均值)
     */
    private BigDecimal negativeMinValue;
    /**
     * 使用阴性上限阈值(1000为取平均值)
     */
    private BigDecimal negativemaxValue;
    /**
     * 检测有效性0否 1是
     */
    private Integer detectionEffectiveness;
    /**
     * 阴性均值运算符
     */
    private Integer negativeMeanOperator;
    /**
     * 阴性均值
     */
    private BigDecimal negativeMeanValue;
    /**
     * 阴性孔值
     */
    private BigDecimal negativeHoleValue;
    /**
     * 阴性孔值运算符
     */
    private Integer negativeHoleOperator;
    /**
     * 空白均值运算符
     */
    private Integer blankMeanOperator;
    /**
     * 空白均值
     */
    private BigDecimal blankMeanValue;
    /**
     * 空白孔值运算符
     */
    private Integer blankHoleOperator;
    /**
     * 空白孔值
     */
    private BigDecimal blankHoleValue;
    /**
     * 阳性均值运算符
     */
    private Integer positiveMeanOperator;
    /**
     * 阳性均值
     */
    private BigDecimal positiveMeanValue;
    /**
     * 阳性2均值运算符
     */
    private Integer positive2MeanOperator;
    /**
     * 阳性2均值
     */
    private BigDecimal positive2MeanValue;
    /**
     * 阳性孔值运算符
     */
    private Integer positiveHoleOperator;
    /**
     * 阳性孔值
     */
    private BigDecimal positiveHoleValue;
    /**
     * 阳性2孔值运算符
     */
    private Integer positive2HoleOperator;
    /**
     * 阳性2孔值
     */
    private BigDecimal positive2HoleValue;
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

    public Integer getUseNegativeThreshold() {
        return useNegativeThreshold;
    }

    public void setUseNegativeThreshold(Integer useNegativeThreshold) {
        this.useNegativeThreshold = useNegativeThreshold;
    }

    public Integer getUsePositiveThreshold() {
        return usePositiveThreshold;
    }

    public void setUsePositiveThreshold(Integer usePositiveThreshold) {
        this.usePositiveThreshold = usePositiveThreshold;
    }

    public Integer getPositiveMinOperator() {
        return positiveMinOperator;
    }

    public void setPositiveMinOperator(Integer positiveMinOperator) {
        this.positiveMinOperator = positiveMinOperator;
    }

    public Integer getPositiveMaxOperator() {
        return positiveMaxOperator;
    }

    public void setPositiveMaxOperator(Integer positiveMaxOperator) {
        this.positiveMaxOperator = positiveMaxOperator;
    }

    public BigDecimal getPositiveMinValue() {
        return positiveMinValue;
    }

    public void setPositiveMinValue(BigDecimal positiveMinValue) {
        this.positiveMinValue = positiveMinValue;
    }

    public BigDecimal getPositiveMaxValue() {
        return positiveMaxValue;
    }

    public void setPositiveMaxValue(BigDecimal positiveMaxValue) {
        this.positiveMaxValue = positiveMaxValue;
    }

    public Integer getNegativeMinOperator() {
        return negativeMinOperator;
    }

    public void setNegativeMinOperator(Integer negativeMinOperator) {
        this.negativeMinOperator = negativeMinOperator;
    }

    public Integer getNegativeMaxOperator() {
        return negativeMaxOperator;
    }

    public void setNegativeMaxOperator(Integer negativeMaxOperator) {
        this.negativeMaxOperator = negativeMaxOperator;
    }

    public BigDecimal getNegativeMinValue() {
        return negativeMinValue;
    }

    public void setNegativeMinValue(BigDecimal negativeMinValue) {
        this.negativeMinValue = negativeMinValue;
    }

    public BigDecimal getNegativemaxValue() {
        return negativemaxValue;
    }

    public void setNegativemaxValue(BigDecimal negativemaxValue) {
        this.negativemaxValue = negativemaxValue;
    }

    public Integer getDetectionEffectiveness() {
        return detectionEffectiveness;
    }

    public void setDetectionEffectiveness(Integer detectionEffectiveness) {
        this.detectionEffectiveness = detectionEffectiveness;
    }

    public Integer getNegativeMeanOperator() {
        return negativeMeanOperator;
    }

    public void setNegativeMeanOperator(Integer negativeMeanOperator) {
        this.negativeMeanOperator = negativeMeanOperator;
    }

    public BigDecimal getNegativeMeanValue() {
        return negativeMeanValue;
    }

    public void setNegativeMeanValue(BigDecimal negativeMeanValue) {
        this.negativeMeanValue = negativeMeanValue;
    }

    public BigDecimal getNegativeHoleValue() {
        return negativeHoleValue;
    }

    public void setNegativeHoleValue(BigDecimal negativeHoleValue) {
        this.negativeHoleValue = negativeHoleValue;
    }

    public Integer getNegativeHoleOperator() {
        return negativeHoleOperator;
    }

    public void setNegativeHoleOperator(Integer negativeHoleOperator) {
        this.negativeHoleOperator = negativeHoleOperator;
    }

    public Integer getBlankMeanOperator() {
        return blankMeanOperator;
    }

    public void setBlankMeanOperator(Integer blankMeanOperator) {
        this.blankMeanOperator = blankMeanOperator;
    }

    public BigDecimal getBlankMeanValue() {
        return blankMeanValue;
    }

    public void setBlankMeanValue(BigDecimal blankMeanValue) {
        this.blankMeanValue = blankMeanValue;
    }

    public Integer getBlankHoleOperator() {
        return blankHoleOperator;
    }

    public void setBlankHoleOperator(Integer blankHoleOperator) {
        this.blankHoleOperator = blankHoleOperator;
    }

    public BigDecimal getBlankHoleValue() {
        return blankHoleValue;
    }

    public void setBlankHoleValue(BigDecimal blankHoleValue) {
        this.blankHoleValue = blankHoleValue;
    }

    public Integer getPositiveMeanOperator() {
        return positiveMeanOperator;
    }

    public void setPositiveMeanOperator(Integer positiveMeanOperator) {
        this.positiveMeanOperator = positiveMeanOperator;
    }

    public BigDecimal getPositiveMeanValue() {
        return positiveMeanValue;
    }

    public void setPositiveMeanValue(BigDecimal positiveMeanValue) {
        this.positiveMeanValue = positiveMeanValue;
    }

    public Integer getPositive2MeanOperator() {
        return positive2MeanOperator;
    }

    public void setPositive2MeanOperator(Integer positive2MeanOperator) {
        this.positive2MeanOperator = positive2MeanOperator;
    }

    public BigDecimal getPositive2MeanValue() {
        return positive2MeanValue;
    }

    public void setPositive2MeanValue(BigDecimal positive2MeanValue) {
        this.positive2MeanValue = positive2MeanValue;
    }

    public Integer getPositiveHoleOperator() {
        return positiveHoleOperator;
    }

    public void setPositiveHoleOperator(Integer positiveHoleOperator) {
        this.positiveHoleOperator = positiveHoleOperator;
    }

    public BigDecimal getPositiveHoleValue() {
        return positiveHoleValue;
    }

    public void setPositiveHoleValue(BigDecimal positiveHoleValue) {
        this.positiveHoleValue = positiveHoleValue;
    }

    public Integer getPositive2HoleOperator() {
        return positive2HoleOperator;
    }

    public void setPositive2HoleOperator(Integer positive2HoleOperator) {
        this.positive2HoleOperator = positive2HoleOperator;
    }

    public BigDecimal getPositive2HoleValue() {
        return positive2HoleValue;
    }

    public void setPositive2HoleValue(BigDecimal positive2HoleValue) {
        this.positive2HoleValue = positive2HoleValue;
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
    
    
    
    public BigDecimal getEcsnegativemaxValue() {
		return ecsnegativemaxValue;
	}

	public void setEcsnegativemaxValue(BigDecimal ecsnegativemaxValue) {
		this.ecsnegativemaxValue = ecsnegativemaxValue;
	}

	public BigDecimal getEcsnegativeMinValue() {
		return ecsnegativeMinValue;
	}

	public void setEcsnegativeMinValue(BigDecimal ecsnegativeMinValue) {
		this.ecsnegativeMinValue = ecsnegativeMinValue;
	}

	public BigDecimal getEcspositiveMaxValue() {
		return ecspositiveMaxValue;
	}

	public void setEcspositiveMaxValue(BigDecimal ecspositiveMaxValue) {
		this.ecspositiveMaxValue = ecspositiveMaxValue;
	}

	public BigDecimal getEcspositiveMinValue() {
		return ecspositiveMinValue;
	}

	public void setEcspositiveMinValue(BigDecimal ecspositiveMinValue) {
		this.ecspositiveMinValue = ecspositiveMinValue;
	}

	public BigDecimal getNegativeThresholdValue() {
		return negativeThresholdValue;
	}

	public void setNegativeThresholdValue(BigDecimal negativeThresholdValue) {
		this.negativeThresholdValue = negativeThresholdValue;
	}

	public BigDecimal getPositiveThresholdValue() {
		return positiveThresholdValue;
	}

	public void setPositiveThresholdValue(BigDecimal positiveThresholdValue) {
		this.positiveThresholdValue = positiveThresholdValue;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ElisaCommonSetting{" +
        "id=" + id +
        ", etId=" + etId +
        ", useNegativeThreshold=" + useNegativeThreshold +
        ", usePositiveThreshold=" + usePositiveThreshold +
        ", positiveMinOperator=" + positiveMinOperator +
        ", positiveMaxOperator=" + positiveMaxOperator +
        ", positiveMinValue=" + positiveMinValue +
        ", positiveMaxValue=" + positiveMaxValue +
        ", negativeMinOperator=" + negativeMinOperator +
        ", negativeMaxOperator=" + negativeMaxOperator +
        ", negativeMinValue=" + negativeMinValue +
        ", negativemaxValue=" + negativemaxValue +
        ", detectionEffectiveness=" + detectionEffectiveness +
        ", negativeMeanOperator=" + negativeMeanOperator +
        ", negativeMeanValue=" + negativeMeanValue +
        ", negativeHoleValue=" + negativeHoleValue +
        ", negativeHoleOperator=" + negativeHoleOperator +
        ", blankMeanOperator=" + blankMeanOperator +
        ", blankMeanValue=" + blankMeanValue +
        ", blankHoleOperator=" + blankHoleOperator +
        ", blankHoleValue=" + blankHoleValue +
        ", positiveMeanOperator=" + positiveMeanOperator +
        ", positiveMeanValue=" + positiveMeanValue +
        ", positive2MeanOperator=" + positive2MeanOperator +
        ", positive2MeanValue=" + positive2MeanValue +
        ", positiveHoleOperator=" + positiveHoleOperator +
        ", positiveHoleValue=" + positiveHoleValue +
        ", positive2HoleOperator=" + positive2HoleOperator +
        ", positive2HoleValue=" + positive2HoleValue +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
