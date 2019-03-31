package com.fadl.rabatinfo.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 胸片检查记录表
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
@TableName("f_rabatinfo")
public class Rabatinfo extends Model<Rabatinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 胸片单位
     */
    private Long rabatunitId;
    /**
     * 全登记号
     */
    private Long allId;
	/**
     * 检查医生
     */
    private Long userId;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 检查结果(0、合格 1、不合格)
     */
    private Integer result;
    /**
     * 胸片图片
     */
    private String rabatImg;
    /**
     * 备注
     */
    private String remarks;
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
    /**
     * 是否体检  ( 0.未体检 1.已体检 )
     */
    private Integer isCheck;

    public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

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
    
    public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}
    
    public Long getRabatunitId() {
        return rabatunitId;
    }

    public void setRabatunitId(Long rabatunitId) {
        this.rabatunitId = rabatunitId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRabatImg() {
        return rabatImg;
    }

    public void setRabatImg(String rabatImg) {
        this.rabatImg = rabatImg;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "Rabatinfo{" +
        "id=" + id +
        ", rabatunitId=" + rabatunitId +
        ", userId=" + userId +
        ", providerNo=" + providerNo +
        ", result=" + result +
        ", rabatImg=" + rabatImg +
        ", remarks=" + remarks +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
