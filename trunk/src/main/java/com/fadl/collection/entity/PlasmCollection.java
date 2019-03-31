package com.fadl.collection.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 采浆记录
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@TableName("f_plasm_collection")
public class PlasmCollection extends Model<PlasmCollection> {

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
     * 采浆机型
     */
    private Long typeId;
    /**
     * 采浆机号
     */
    private Long machineId;
    /**
     * 采室编号
     */
    private Long roomId;
    /**
     * 采浆量
     */
    private Integer plasmAmount;
    /**
     * 全血量
     */
    private Integer wholeBlood;
    /**
     * 抗凝剂用量
     */
    private Integer knAmount;
    /**
     * 循环次数
     */
    private Integer loopCount;
    /**
     * 运行时间(单位:分钟)
     */
    private Integer runTime;
    /**
     * 静脉穿刺
     */
    private Integer veinCount;
    /**
     * 是否采足(0、采足 1、未采足)
     */
    private Integer isAmple;
    /**
     * 验证类型(0、静脉验证 1、人脸识别)
     */
    private Integer type;
    /**
     * 是否验证身份(0、未验证 1、已验证)
     */
    private Integer isIdentity;
    /**
     * 采浆情况编号
     */
    private Long situation;
    /**
     * 耗材模板编号
     */
    private Long suppliesId;
    /**
     * 采浆结果(0、合格 1、不合格)
     */
    private Integer result;
    /**
     * 是否采集（0.未采集1.已采集）
     */
    private Integer isCollection;
    /**
     * 血浆状态(0、正常 1、报废)
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否已经送样(0,未送,1已送)
     */
    private Integer isSendOff;
    /**
     * 送样人
     */
    private String sendPerson;
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
     * 采浆人
     */
    private Long userId;
    /**
	 * 所属浆站标识
	 */
    @TableField(fill = FieldFill.INSERT)
	private String plasmaId;
    /**
     * 报废人
     */
    private Long scrapUserId;
    /**
     * 报废时间
     */
    private String scrapDate;
    /**
     * 报废时间
     */
    private Integer isSuppliesCheck;
    /**
     * 报废时间
     */
    private String collectionStart;
    /**
     * 报废时间
     */
    private String collectionEnd;
    
	public Integer getIsSuppliesCheck() {
		return isSuppliesCheck;
	}

	public void setIsSuppliesCheck(Integer isSuppliesCheck) {
		this.isSuppliesCheck = isSuppliesCheck;
	}

	public String getCollectionStart() {
		return collectionStart;
	}

	public void setCollectionStart(String collectionStart) {
		this.collectionStart = collectionStart;
	}

	public String getCollectionEnd() {
		return collectionEnd;
	}

	public void setCollectionEnd(String collectionEnd) {
		this.collectionEnd = collectionEnd;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getPlasmAmount() {
        return plasmAmount;
    }

    public void setPlasmAmount(Integer plasmAmount) {
        this.plasmAmount = plasmAmount;
    }

    public Integer getWholeBlood() {
        return wholeBlood;
    }

    public void setWholeBlood(Integer wholeBlood) {
        this.wholeBlood = wholeBlood;
    }

    public Integer getKnAmount() {
        return knAmount;
    }

    public void setKnAmount(Integer knAmount) {
        this.knAmount = knAmount;
    }

    public Integer getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(Integer loopCount) {
        this.loopCount = loopCount;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Integer getVeinCount() {
        return veinCount;
    }

    public void setVeinCount(Integer veinCount) {
        this.veinCount = veinCount;
    }

    public Integer getIsAmple() {
        return isAmple;
    }

    public void setIsAmple(Integer isAmple) {
        this.isAmple = isAmple;
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

    public Long getSituation() {
        return situation;
    }

    public void setSituation(Long situation) {
        this.situation = situation;
    }

    public Long getSuppliesId() {
        return suppliesId;
    }

    public void setSuppliesId(Long suppliesId) {
        this.suppliesId = suppliesId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Long getScrapUserId() {
		return scrapUserId;
	}

	public void setScrapUserId(Long scrapUserId) {
		this.scrapUserId = scrapUserId;
	}

	public String getScrapDate() {
		return scrapDate;
	}

	public void setScrapDate(String scrapDate) {
		this.scrapDate = scrapDate;
	}

	
	public Integer getIsSendOff() {
		return isSendOff;
	}

	public void setIsSendOff(Integer isSendOff) {
		this.isSendOff = isSendOff;
	}

	public String getSendPerson() {
		return sendPerson;
	}

	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}

	@Override
    public String toString() {
        return "PlasmCollection{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", typeId=" + typeId +
        ", machineId=" + machineId +
        ", roomId=" + roomId +
        ", plasmAmount=" + plasmAmount +
        ", wholeBlood=" + wholeBlood +
        ", knAmount=" + knAmount +
        ", loopCount=" + loopCount +
        ", runTime=" + runTime +
        ", veinCount=" + veinCount +
        ", isAmple=" + isAmple +
        ", type=" + type +
        ", isIdentity=" + isIdentity +
        ", situation=" + situation +
        ", suppliesId=" + suppliesId +
        ", result=" + result +
        ", isCollection=" + isCollection +
        ", status=" + status +
        ", remarks=" + remarks +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
