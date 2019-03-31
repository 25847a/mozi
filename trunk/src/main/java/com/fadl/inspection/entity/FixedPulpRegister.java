package com.fadl.inspection.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 固定浆员检验登记
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-16
 */
@TableName("f_fixed_pulp_register")
public class FixedPulpRegister extends Model<FixedPulpRegister> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 全登记号
     */
    private Long allId;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 小样号
     */
    private Long sampleNo;
    /**
     * 是否登记(0、未登记 1、已登记)
     */
    private Integer isAssay;
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
 * 接收人
 */
    private String receivePerson;
    /**
     * 送样人
     */
    private Long sendPerson;
    
    /**
     * 样本状态: 0正常样本,1拒收样本
     */
    private Integer status;
    /**
     * 样本状态: 0脂血,1溶血,2标签不完整,3渗漏,4量不足
     */
    private Integer refuseRemark;
    
    public String getPlasmaId() {
		return plasmaId;
	}

	public void setPlasmaId(String plasmaId) {
		this.plasmaId = plasmaId;
	}

	@TableField(exist = false)
    private String withinSixMonths;
    /**
     * 查询用开始时间
     */
    @TableField(exist = false)
    private String startTime;
    /**
     * 查询用结束时间
     */
    @TableField(exist = false)
    private String endTime;
    
    public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getWithinSixMonths() {
		return withinSixMonths;
	}

	public void setWithinSixMonths(String withinSixMonths) {
		this.withinSixMonths = withinSixMonths;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Long getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(Long sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Integer getIsAssay() {
        return isAssay;
    }

    public void setIsAssay(Integer isAssay) {
        this.isAssay = isAssay;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getSendPerson() {
		return sendPerson;
	}

	public void setSendPerson(Long sendPerson) {
		this.sendPerson = sendPerson;
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
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRefuseRemark() {
		return refuseRemark;
	}

	public void setRefuseRemark(Integer refuseRemark) {
		this.refuseRemark = refuseRemark;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FixedPulpRegister{" +
        "id=" + id +
        ", allId=" + allId +
        ", providerNo=" + providerNo +
        ", sampleNo=" + sampleNo +
        ", isAssay=" + isAssay +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
