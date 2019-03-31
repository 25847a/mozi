package com.fadl.elisa.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 酶标仪检测记录表
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_values")
public class ElisaValues extends Model<ElisaValues> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 0std 1smp 2pc 3pc2 4nc 5qc 6blk 7null
     */
    private Integer type;
    /**
     * 表头
     */
    private String title;
    /**
     * 当前的游标
     */
    private Integer evIndex;
    /**
     * 浆员信息ID
     */
    private String pbId;
    /**
     * 全登记号
     */
    private Long allId;
    /**
     * 新老卡化验Id
     */
    private Long newCardId;
    /**
     * 酶标板化验Id
     */
    private Long eiId;
    /**
     * 小样号
     */
    private String sampleNo;
    
    /**
     * 浆员名字
     */
    private String personName;
    /**
     * 血型
     */
    private String bloodType;
    
    
    /**
     * 化验结果
     */
    private BigDecimal value;
    /**
     * 判定结果
     */
    private Integer judgementResult;
    /**
     * od值
     */
    private BigDecimal odValue;
    /**
     * 原始OD值
     */
    private BigDecimal originalODValue;
    /**
     * 报告发布状态0未发布 1已发布
     */
    private Integer reportStatus;
    /**
     *  样本类型 0初检 1复检
     */
    private Integer sampleType;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPbId() {
        return pbId;
    }

    public void setPbId(String pbId) {
        this.pbId = pbId;
    }

    public Long getAllId() {
        return allId;
    }

    public void setAllId(Long allId) {
        this.allId = allId;
    }

    public Long getEiId() {
        return eiId;
    }

    public void setEiId(Long eiId) {
        this.eiId = eiId;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getJudgementResult() {
        return judgementResult;
    }

    public void setJudgementResult(Integer judgementResult) {
        this.judgementResult = judgementResult;
    }

    public BigDecimal getOdValue() {
        return odValue;
    }

    public void setOdValue(BigDecimal odValue) {
        this.odValue = odValue;
    }

    public BigDecimal getOriginalODValue() {
        return originalODValue;
    }

    public void setOriginalODValue(BigDecimal originalODValue) {
        this.originalODValue = originalODValue;
    }

    public String getCreateDate() {
        return createDate;
    }
    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }
    
    public Integer getSampleType() {
		return sampleType;
	}

	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
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

    public Integer getEvIndex() {
		return evIndex;
	}

	public void setEvIndex(Integer evIndex) {
		this.evIndex = evIndex;
	}

	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	public Long getNewCardId() {
		return newCardId;
	}

	public void setNewCardId(Long newCardId) {
		this.newCardId = newCardId;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ElisaValues{" +
        "id=" + id +
        ", type=" + type +
        ", title=" + title +
        ", evIndex=" + evIndex +
        ", pbId=" + pbId +
        ", allId=" + allId +
        ", eiId=" + eiId +
        ", sampleNo=" + sampleNo +
        ", value=" + value +
        ", judgementResult=" + judgementResult +
        ", odValue=" + odValue +
        ", originalODValue=" + originalODValue +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
