package com.fadl.propagandist.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 宣传员
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
@TableName("f_propagandist")
public class Propagandist extends Model<Propagandist> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型 （0.宣传员 1.体检医生）
     */
    private Integer type;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工号（拓展员、业务员卡号）
     */
    private String providerNo;
	/**
     * 身份证
     */
    private String idNo;
    /**
     * 性别(0、男 1、女)
     */
    private Integer sex;
    /**
     * 电话
     */
    private String tel;
    /**
     * 可用金额
     */
    private BigDecimal money;
    /**
     * 冻结金额
     */
    private BigDecimal freezeMoney;
    /**
     * 是否禁用(0、正常 1、禁用)
     */
    private Integer isDisable;
    /**
     * 备注
     */
    private String remark;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 组号ID
     */
    private Long groupId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setRemark(String remark) {
        this.remark = remark;
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
		return "Propagandist [id=" + id + ", type=" + type + ", name=" + name
				+ ", providerNo=" + providerNo + ", idNo=" + idNo + ", sex=" + sex
				+ ", tel=" + tel + ", money=" + money + ", freezeMoney="
				+ freezeMoney + ", isDisable=" + isDisable + ", remark="
				+ remark + ", level=" + level + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", creater=" + creater
				+ ", updater=" + updater + "]";
	}
}
