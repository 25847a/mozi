package com.fadl.immuneAssay.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 免疫类别设置
 * </p>
 *
 * @author hkk
 * @since 2018-07-12
 */
@TableName("f_immune_setting")
public class ImmuneSetting extends Model<ImmuneSetting> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类别编码
     */
    private String typeCode;
    /**
     * 免疫名称
     */
    private String immuneName;
    /**
     * 基础针数
     */
    private Integer basicNum;
    /**
     * 加强针数
     */
    private Integer strengthenNum;
    /**
     * 失效期
     */
    private Integer invalidDate;
    /**
     * 所属类型( 0.普通 1.普免 2.特殊3.临免 )
     */
    private Integer type;
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
     * 阳性孔值运算符
     */
    private Integer positiveHoleOperator;
    /**
     * 序号，排序的序号
     */
    private Long num;
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

    public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getImmuneName() {
        return immuneName;
    }

    public void setImmuneName(String immuneName) {
        this.immuneName = immuneName;
    }

    public Integer getBasicNum() {
        return basicNum;
    }

    public void setBasicNum(Integer basicNum) {
        this.basicNum = basicNum;
    }

    public Integer getStrengthenNum() {
        return strengthenNum;
    }

    public void setStrengthenNum(Integer strengthenNum) {
        this.strengthenNum = strengthenNum;
    }

    public Integer getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Integer invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getPositiveHoleOperator() {
        return positiveHoleOperator;
    }

    public void setPositiveHoleOperator(Integer positiveHoleOperator) {
        this.positiveHoleOperator = positiveHoleOperator;
    }

    public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ImmuneSetting{" +
        "id=" + id +
        "typeCode=" + typeCode +
        ", immuneName=" + immuneName +
        ", basicNum=" + basicNum +
        ", strengthenNum=" + strengthenNum +
        ", invalidDate=" + invalidDate +
        ", type=" + type +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", positiveHoleOperator=" + positiveHoleOperator +
         ", num=" + num +
        "}";
    }
}
