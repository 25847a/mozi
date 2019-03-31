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
 * 即刻性室内质控记录表
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-20
 */
@TableName("f_elisa_iiqc")
public class ElisaIiqc extends Model<ElisaIiqc> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 检验配置详情Id
     */
    private Long tciid;
    /**
     * 质控品ID
     */
    private Long qcId;
    
    /**
     * 操作人id
     */
    private Long opAdmin;
    /**
     * 酶标板化验id 
     */
    private Long eiId;
    /**
     * 本次测试靶值(平均值)
     */
    private BigDecimal targetValue;
    /**
     * 本次标准差
     */
    private BigDecimal standardDeviation;
    /**
     * 本次CV
     */
    private BigDecimal cv;
    /**
     * 框架CV值
     */
    private BigDecimal frameCV;
    /**
     * 框架标准差
     */
    private BigDecimal frameStandardDeviation;
    /**
     * 框架靶值(平均值)
     */
    private BigDecimal frameTargetValue;
    /**
     * 实际OD值
     */
    private BigDecimal odValue;
    /**
     * 卡门式值,用于ALT报表二
     */
    private BigDecimal altValue;
    /**
     * s/co值,标准差除以CutOff值
     */
    private BigDecimal sDivideCO;
    /**
     * cutOff值
     */
    private BigDecimal cutOffvalue;
    
    /**
     * 框架alt的CV值
     */
    private BigDecimal altFrameCV;
    /**
     * 框架alt的标准差
     */
    private BigDecimal altFrameStandardDeviation;
    /**
     * 框架alt的靶值(平均值)
     */
    private BigDecimal altFrameTargetValue;
    /**
     * 本次alt的测试靶值(平均值)
     */
    private BigDecimal altTargetValue;
    /**
     * 本次alt的标准差
     */
    private BigDecimal altStandardDeviation;
    /**
     * 本次alt的CV
     */
    private BigDecimal altCV;
    
    /**
     * 判断结果0,在控;1警告;2失控
     */
    private Integer criticalResult;
    /**
     * si上限值
     */
    private BigDecimal siMAX;
    /**
     * si下限值
     */
    private BigDecimal siMIN;
    /**
     * n2s
     */
    private BigDecimal n2s;
    /**
     * n3s
     */
    private BigDecimal n3s;
    /**
     * alt的SI上限值
     */
    private BigDecimal altSIMAX;
    /**
     * alt的SI下限值
     */
    private BigDecimal altSIMIN;
    /**
     * alt的n2s
     */
    private BigDecimal altN2S;
    /**
     * alt的n3s
     */
    private BigDecimal altN3S;
    
    
    
    
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

    public Long getTciid() {
        return tciid;
    }

    public void setTciid(Long tciid) {
        this.tciid = tciid;
    }

    public Long getQcId() {
		return qcId;
	}

	public void setQcId(Long qcId) {
		this.qcId = qcId;
	}

	public Long getOpAdmin() {
		return opAdmin;
	}

	public void setOpAdmin(Long opAdmin) {
		this.opAdmin = opAdmin;
	}

	public Long getEiId() {
		return eiId;
	}

	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	
	public Integer getCriticalResult() {
		return criticalResult;
	}

	public void setCriticalResult(Integer criticalResult) {
		this.criticalResult = criticalResult;
	}


	public BigDecimal getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(BigDecimal targetValue) {
		this.targetValue = targetValue;
	}

	public BigDecimal getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(BigDecimal standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public BigDecimal getCv() {
		return cv;
	}

	public void setCv(BigDecimal cv) {
		this.cv = cv;
	}

	public BigDecimal getFrameCV() {
		return frameCV;
	}

	public void setFrameCV(BigDecimal frameCV) {
		this.frameCV = frameCV;
	}

	public BigDecimal getFrameStandardDeviation() {
		return frameStandardDeviation;
	}

	public void setFrameStandardDeviation(BigDecimal frameStandardDeviation) {
		this.frameStandardDeviation = frameStandardDeviation;
	}

	public BigDecimal getFrameTargetValue() {
		return frameTargetValue;
	}

	public void setFrameTargetValue(BigDecimal frameTargetValue) {
		this.frameTargetValue = frameTargetValue;
	}

	public BigDecimal getOdValue() {
		return odValue;
	}

	public void setOdValue(BigDecimal odValue) {
		this.odValue = odValue;
	}

	public BigDecimal getAltValue() {
		return altValue;
	}

	public void setAltValue(BigDecimal altValue) {
		this.altValue = altValue;
	}

	public BigDecimal getsDivideCO() {
		return sDivideCO;
	}

	public void setsDivideCO(BigDecimal sDivideCO) {
		this.sDivideCO = sDivideCO;
	}

	public BigDecimal getCutOffvalue() {
		return cutOffvalue;
	}

	public void setCutOffvalue(BigDecimal cutOffvalue) {
		this.cutOffvalue = cutOffvalue;
	}

	public BigDecimal getAltFrameCV() {
		return altFrameCV;
	}

	public void setAltFrameCV(BigDecimal altFrameCV) {
		this.altFrameCV = altFrameCV;
	}

	public BigDecimal getAltFrameStandardDeviation() {
		return altFrameStandardDeviation;
	}

	public void setAltFrameStandardDeviation(BigDecimal altFrameStandardDeviation) {
		this.altFrameStandardDeviation = altFrameStandardDeviation;
	}

	public BigDecimal getAltFrameTargetValue() {
		return altFrameTargetValue;
	}

	public void setAltFrameTargetValue(BigDecimal altFrameTargetValue) {
		this.altFrameTargetValue = altFrameTargetValue;
	}

	public BigDecimal getAltTargetValue() {
		return altTargetValue;
	}

	public void setAltTargetValue(BigDecimal altTargetValue) {
		this.altTargetValue = altTargetValue;
	}

	public BigDecimal getAltStandardDeviation() {
		return altStandardDeviation;
	}

	public void setAltStandardDeviation(BigDecimal altStandardDeviation) {
		this.altStandardDeviation = altStandardDeviation;
	}

	public BigDecimal getAltCV() {
		return altCV;
	}

	public void setAltCV(BigDecimal altCV) {
		this.altCV = altCV;
	}

	public BigDecimal getSiMAX() {
		return siMAX;
	}

	public void setSiMAX(BigDecimal siMAX) {
		this.siMAX = siMAX;
	}

	public BigDecimal getSiMIN() {
		return siMIN;
	}

	public void setSiMIN(BigDecimal siMIN) {
		this.siMIN = siMIN;
	}

	public BigDecimal getN2s() {
		return n2s;
	}

	public void setN2s(BigDecimal n2s) {
		this.n2s = n2s;
	}

	public BigDecimal getN3s() {
		return n3s;
	}

	public void setN3s(BigDecimal n3s) {
		this.n3s = n3s;
	}

	public BigDecimal getAltSIMAX() {
		return altSIMAX;
	}

	public void setAltSIMAX(BigDecimal altSIMAX) {
		this.altSIMAX = altSIMAX;
	}

	public BigDecimal getAltSIMIN() {
		return altSIMIN;
	}

	public void setAltSIMIN(BigDecimal altSIMIN) {
		this.altSIMIN = altSIMIN;
	}

	public BigDecimal getAltN2S() {
		return altN2S;
	}

	public void setAltN2S(BigDecimal altN2S) {
		this.altN2S = altN2S;
	}

	public BigDecimal getAltN3S() {
		return altN3S;
	}

	public void setAltN3S(BigDecimal altN3S) {
		this.altN3S = altN3S;
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
        return "ElisaIiqc{" +
        "id=" + id +
        ", tciid=" + tciid +
        ", targetValue=" + targetValue +
        ", standardDeviation=" + standardDeviation +
        ", cv=" + cv +
        ", frameCV=" + frameCV +
        ", frameStandardDeviation=" + frameStandardDeviation +
        ", frameTargetValue=" + frameTargetValue +
        ", odValue=" + odValue +
        ", altValue=" + altValue +
        ", sDivideCO=" + sDivideCO +
        ", cutOffvalue=" + cutOffvalue +
        ", altFrameCV=" + altFrameCV +
        ", altFrameStandardDeviation=" + altFrameStandardDeviation +
        ", altFrameTargetValue=" + altFrameTargetValue +
        ", altTargetValue=" + altTargetValue +
        ", altStandardDeviation=" + altStandardDeviation +
        ", altCV=" + altCV +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
