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
 * 特免登记
 * </p>
 *
 * @author zll
 * @since 2018-07-24
 */
@TableName("f_privilege_register")
public class PrivilegeRegister extends Model<PrivilegeRegister> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 特免编号
     */
    private String privilegeNo;
    /**
     * 浆员卡号
     */
    private Long providerNo;
    /**
     * 免疫类型 id
     */
    private Long immuneId;
    /**
     * 状态（0.通过 1.不通过）
     */
    private Integer status;
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

    public String getPrivilegeNo() {
        return privilegeNo;
    }

    public void setPrivilegeNo(String privilegeNo) {
        this.privilegeNo = privilegeNo;
    }

    public Long getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(Long providerNo) {
        this.providerNo = providerNo;
    }

    public Long getImmuneId() {
        return immuneId;
    }

    public void setImmuneId(Long immuneId) {
        this.immuneId = immuneId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "PrivilegeRegister{" +
        "id=" + id +
        ", privilegeNo=" + privilegeNo +
        ", providerNo=" + providerNo +
        ", immuneId=" + immuneId +
        ", status=" + status +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
