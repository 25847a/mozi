package com.fadl.refuseInfo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 拒绝信息发布
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
@TableName("f_refuse_info")
public class RefuseInfo extends Model<RefuseInfo> {

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
     * 酶标仪检验信息ID
     */
    private Long evId;
    /**
     * 是否发布拒绝信息(0. 未发布  1.已发布)
     */
    private Integer isRefuse;
	/**
     * 医生意见( 0.暂时拒绝  1.永久淘汰 )
     */
    private Integer opinion;
    /**
     * 淘汰原因
     */
    private String eliminateReason;
    /**
     * 拒绝天数
     */
    private Integer day;
    /**
     * 发布医生
     */
    private Long releaseId;
    /**
     * 发布类型(0、体检发布 1、化验发布 2、生物所反馈)
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

    public Integer getOpinion() {
        return opinion;
    }

    public void setOpinion(Integer opinion) {
        this.opinion = opinion;
    }

    public String getEliminateReason() {
        return eliminateReason;
    }

    public void setEliminateReason(String eliminateReason) {
        this.eliminateReason = eliminateReason;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
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
    
    public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}
	
	
	public Long getEvId() {
		return evId;
	}

	public void setEvId(Long evId) {
		this.evId = evId;
	}

	public Integer getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(Integer isRefuse) {
		this.isRefuse = isRefuse;
	}
	
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RefuseInfo{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", isRefuse=" + isRefuse +
        ", opinion=" + opinion +
        ", eliminateReason=" + eliminateReason +
        ", day=" + day +
        ", releaseId=" + releaseId +
        ", type=" + type +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
