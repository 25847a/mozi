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
 * 检验-新-老卡化验
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@TableName("f_new_card")
public class NewCard extends Model<NewCard> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 全小样号
     */
    private Long sampleNo;
    /**
     * 查询用的 小样号开始
     */
    @TableField(exist = false)
    private Long startSampleNo;
    /**
     * 查询用的 小样号结束
     */
    @TableField(exist = false)
    private Long endSampleNo;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 蛋白含量(0是1否)-蛋白含量
     */
    private String protein;
    /**
     * 蛋白含量-蛋白含量
     */
    private String proteinValue;
    
    /**
     * 血红蛋白含量(>=120 >120 >=110 >110)-血红蛋白含量
     */
    private Long bloodRedProtein;
    
    /**
     * 血红蛋白含量-血红蛋白检测
     */
    private String bloodRedProteinValue;
    /**
     * 全血比重(0是1否)-血红蛋白含量
     */
    private String wholeBlood;
    /**
     * 梅毒
     */
    private String syphilis;
    /**
     * 谷丙转氨酶
     */
    private String alt;
    /**
     * 血清(血浆)蛋白含量  
     */
    private String serumProtein;
    /**
     * 乙肝表面抗原
     */
    private String hbsag;
    /**
     * 丙肝表面抗原
     */
    private String hcv;
    /**
     * 艾滋病表面抗原
     */
    private String hiv;
    /**
     * 血型(0、A 1、B 2、AB 3、O)
     */
    private String blood;
    /**
     * 配置模板ID
     */
    private Long suppliesId;
    
    private Long allId;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 新浆员是否化验(0、未化验 1、已化验)
     */
    private Integer isAssay;
    /**
     * 化验结果(0、合格  1、不合格)
     */
    private Integer result;
    /**
     * 浆员类型(0、新卡化验 1、供浆员化验)
     */
    private Integer type;
    /**
     * 报告着ID
     */
    private Long reportAdminid;
    /**
     * 复检标记 0：非复检，1：已复检
     */
    private Integer rechecked;
    
    /**
     * 血型检测配置的检验配置InfoId
     */
    private Long bloodTciId;
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
     * 查询用复检标识 0 初检 1复检
     */
    @TableField(exist = false)
    private Integer sampleType;
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

    
    
    public Long getStartSampleNo() {
		return startSampleNo;
	}



	public Long getReportAdminid() {
		return reportAdminid;
	}



	public void setReportAdminid(Long reportAdminid) {
		this.reportAdminid = reportAdminid;
	}



	public void setStartSampleNo(Long startSampleNo) {
		this.startSampleNo = startSampleNo;
	}



	public Long getEndSampleNo() {
		return endSampleNo;
	}



	public Integer getSampleType() {
		return sampleType;
	}



	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}



	public void setEndSampleNo(Long endSampleNo) {
		this.endSampleNo = endSampleNo;
	}



	public Long getBloodTciId() {
		return bloodTciId;
	}



	public void setBloodTciId(Long bloodTciId) {
		this.bloodTciId = bloodTciId;
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

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public Long getBloodRedProtein() {
        return bloodRedProtein;
    }

    public void setBloodRedProtein(Long bloodRedProtein) {
        this.bloodRedProtein = bloodRedProtein;
    }

    public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
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

	public String getWholeBlood() {
        return wholeBlood;
    }

    public void setWholeBlood(String wholeBlood) {
        this.wholeBlood = wholeBlood;
    }

    public String getSyphilis() {
        return syphilis;
    }

    public void setSyphilis(String syphilis) {
        this.syphilis = syphilis;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSerumProtein() {
        return serumProtein;
    }

    public void setSerumProtein(String serumProtein) {
        this.serumProtein = serumProtein;
    }

    
    public Long getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(Long sampleNo) {
		this.sampleNo = sampleNo;
	}

	

    public String getHbsag() {
		return hbsag;
	}



	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}



	public String getHcv() {
        return hcv;
    }

    public void setHcv(String hcv) {
        this.hcv = hcv;
    }

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getIsAssay() {
        return isAssay;
    }

    public void setIsAssay(Integer isAssay) {
        this.isAssay = isAssay;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
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


	public String getBloodRedProteinValue() {
		return bloodRedProteinValue;
	}



	public void setBloodRedProteinValue(String bloodRedProteinValue) {
		this.bloodRedProteinValue = bloodRedProteinValue;
	}



	public Integer getRechecked() {
		return rechecked;
	}



	public void setRechecked(Integer rechecked) {
		this.rechecked = rechecked;
	}



	public String getProteinValue() {
		return proteinValue;
	}

	public void setProteinValue(String proteinValue) {
		this.proteinValue = proteinValue;
	}

	public Long getSuppliesId() {
		return suppliesId;
	}

	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "NewCard{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", protein=" + protein +
        ", bloodRedProtein=" + bloodRedProtein +
        ", wholeBlood=" + wholeBlood +
        ", syphilis=" + syphilis +
        ", alt=" + alt +
        ", serumProtein=" + serumProtein +
        ", HBsAg=" + hbsag +
        ", hcv=" + hcv +
        ", hiv=" + hiv +
        ", blood=" + blood +
        ", remarks=" + remarks +
        ", isAssay=" + isAssay +
        ", result=" + result +
        ", type=" + type +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
