package com.fadl.inspection.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 检验-标本采集
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@TableName("f_specimen_collection")
public class SpecimenCollection extends Model<SpecimenCollection> {

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
     * 创建时间
     */
	@TableField(value = "createDate",fill = FieldFill.INSERT )
    private String createDate;
    /**
     * 修改时间
     */
	@TableField(value = "updateDate",fill = FieldFill.INSERT_UPDATE )
    private String updateDate;
    /**
     * 创建人
     */
	@TableField(value = "creater",fill = FieldFill.INSERT )
    private Long creater;
    /**
     * 修改人
     */
	@TableField(value = "updater",fill = FieldFill.INSERT_UPDATE )
    private Long updater;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 是否采集（0.未采集 1.已采集）
     */
    private Integer isCollection;
    /**
     * 验证类型(0、静脉验证 1、人脸识别)
     */
    private Integer type;
    /**
     * 是否验证身份(0、未验证 1、已验证)
     */
    private Integer isIdentity;
    /**
     * 是否已经送样(0,未送,1已送)
     */
    private Integer isSendOff;
    /**
     * 小样号
     */
    private String sampleNo;

    /**
     * 送样人
     */
    private String sendPerson;
    
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

	public String getProviderNo() {
        return providerNo;
    }

    public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}
	
    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(Integer isIdentity) {
        this.isIdentity = isIdentity;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
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

	
	public String getWithinSixMonths() {
		return withinSixMonths;
	}

	public void setWithinSixMonths(String withinSixMonths) {
		this.withinSixMonths = withinSixMonths;
	}
	
	public String getSendPerson() {
		return sendPerson;
	}

	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}

	public Integer getIsSendOff() {
		return isSendOff;
	}

	public void setIsSendOff(Integer isSendOff) {
		this.isSendOff = isSendOff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
        return "SpecimenCollection{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", isCollection=" + isCollection +
        ", type=" + type +
        ", isIdentity=" + isIdentity +
        ", sampleNo=" + sampleNo +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
