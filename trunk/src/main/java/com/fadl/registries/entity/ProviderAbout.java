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
 * 预约时间表
 * </p>
 *
 * @author CJ
 * @since 2018-09-11
 */
@TableName("f_provider_about")
public class ProviderAbout extends Model<ProviderAbout> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
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
     * 预约时间
     */
    private String aboutDate;
    /**
     * 最后采浆时间
     */
    private String collectionDate;
    
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

    public String getAboutDate() {
        return aboutDate;
    }

    public void setAboutDate(String aboutDate) {
        this.aboutDate = aboutDate;
    }

    public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
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
        return "ProviderAbout{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", aboutDate=" + aboutDate +
        ", collectionDate=" + collectionDate +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", plasmaId=" + plasmaId +
        "}";
    }
}
