package com.fadl.registries.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 登记记录表
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
@TableName("f_provider_registries")
public class ProviderRegistries extends Model<ProviderRegistries> {

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
     * 全登记号
     */
    private Long allId;
    /**
     * 登记号
     */
    private Integer registriesNo;
    /**
     * 全小样号
     */
    private Long sampleNo;
    /**
     * 验证类型(0、静脉验证 1、人脸识别)
     */
    private Integer type;
    /**
     * 浆员类型(0固定、1非固定)
     */
    private Integer plasmaType;
    /**
     * 是否是新老浆员（0新浆员 1老浆员）
     */
    private Integer isNew;
    /**
     * 是否验证身份(0、未验证 1、已验证)
     */
    private Integer isIdentity;
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

    public Long getAllId() {
        return allId;
    }

    public void setAllId(Long allId) {
        this.allId = allId;
    }

    public Integer getRegistriesNo() {
        return registriesNo;
    }

    public void setRegistriesNo(Integer registriesNo) {
        this.registriesNo = registriesNo;
    }

    public Long getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(Long sampleNo) {
		this.sampleNo = sampleNo;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlasmaType() {
		return plasmaType;
	}

	public void setPlasmaType(Integer plasmaType) {
		this.plasmaType = plasmaType;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(Integer isIdentity) {
        this.isIdentity = isIdentity;
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
        return "ProviderRegistries{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", registriesNo=" + registriesNo +
        ", sampleNo=" + sampleNo +
        ", type=" + type +
        ", plasmaType=" + plasmaType +
        ", isNew=" + isNew +
        ", isIdentity=" + isIdentity +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
