package com.fadl.elisa.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 酶标板计量方式
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_measurement_method")
public class ElisaMeasurementMethod extends Model<ElisaMeasurementMethod> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酶标板模板ID
     */
    private Long etId;
    /**
     * 计量方式 0定性 1半定量 2标准曲线 3线性拟合 4多项式拟合
     */
    private Integer optionsValue;
    /**
     * STD1值
     */
    @TableField("STD1")
    private Integer std1;
    /**
     * STD2值
     */
    @TableField("STD2")
    private Integer std2;
    /**
     * STD3值
     */
    @TableField("STD3")
    private Integer std3;
    /**
     * STD4值
     */
    @TableField("STD4")
    private Integer std4;
    /**
     * STD5值
     */
    @TableField("STD5")
    private Integer std5;
    
    /**
     * STD6值
     */
    @TableField("STD6")
    private Integer std6;
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

    public Integer getOptionsValue() {
        return optionsValue;
    }

    public void setOptionsValue(Integer optionsValue) {
        this.optionsValue = optionsValue;
    }

    public Integer getStd1() {
        return std1;
    }

    public void setStd1(Integer std1) {
        this.std1 = std1;
    }

    public Integer getStd2() {
        return std2;
    }

    public void setStd2(Integer std2) {
        this.std2 = std2;
    }

    public Integer getStd3() {
        return std3;
    }

    public void setStd3(Integer std3) {
        this.std3 = std3;
    }

    public Integer getStd4() {
        return std4;
    }

    public void setStd4(Integer std4) {
        this.std4 = std4;
    }

    public Integer getStd5() {
        return std5;
    }

    public void setStd5(Integer std5) {
        this.std5 = std5;
    }

    public Integer getStd6() {
		return std6;
	}

	public void setStd6(Integer std6) {
		this.std6 = std6;
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
        return "ElisaMeasurementMethod{" +
        "id=" + id +
        ", etId=" + etId +
        ", optionsValue=" + optionsValue +
        ", std1=" + std1 +
        ", std2=" + std2 +
        ", std3=" + std3 +
        ", std4=" + std4 +
        ", std5=" + std5 +
         ", std6=" + std6 +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
