package com.fadl.check.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 体检记录表
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
@TableName("f_check")
public class Check extends Model<Check> {

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
     * 是否体检（0.未体检  1.已体检）
     */
    private Integer isCheck;
    /**
     * 验证类型(0、静脉验证 1、人脸识别)
     */
    private Integer type;
    /**
     * 是否验证身份(0、未验证 1、已验证)
     */
    private Integer isIdentity;
    /**
     * 体重
     */
    private String tz;
    /**
     * 体温
     */
    private String tw;
    /**
     * 脉搏
     */
    private String mb;
    /**
     * 血压MIN
     */
    private String xya;
    /**
     * 血压MAX
     */
    private String xyb;
    /**
     * 胸部( 0.正常 1.不正常 )
     */
    private String xb;
    /**
     * 腹部(0.正常 1.有肿块 2.压痛 3.肝脾肿大)
     */
    private String fb;
    /**
     * 皮肤(0.正常 1.黄染 2.有创面感染 3.有大面积皮肤病 4.浅表淋巴结有明显肿大 )
     */
    private String pf;
    /**
     * 五官( 0.正常 1.严重疾病 2.巩膜黄染 3.甲状腺肿大 )
     */
    private String wg;
    /**
     * 四肢( 0.正常 1.严重残疾 2.功能性障碍 3.关节红肿 4.静脉穿刺部分皮肤损伤 5.有静脉注射药物痕迹)
     */
    private String sz;
    /**
     * 是否征询(0、是 1、否)
     */
    private Integer consult;
    /**
     * 征询结果(0、合格 1、不合格)
     */
    private Integer consultResult;
    /**
     * 检查结果(0、合格 1、不合格)
     */
    private Integer result;
    /**
     * 最终结果(0、合格 1、不合格)
     */
    private Integer finalResult;
    /**
     * 淘汰原因
     */
    private String reason;
    /**
     * 签名医生
     */
    private Long userId;
    /**
     * 是否删除(0、未删除 1、已删除)
     */
    private Integer isDel;
    /**
     * 心脏( 0.正常 1.不正常 )
     */
    private Integer heart;
    /**
     * 肝( 0.正常 1.不正常 )
     */
    private Integer liver;
    /**
     * 脾( 0.正常 1.不正常 )
     */
    private Integer spleen;
    /**
     * 肺( 0.正常 1.不正常 )
     */
    private Integer lung;
    /**
     * 体检类型( 0.正常体检 1.重检 )
     */
    private Integer checkType;
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

    public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}
	
    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
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

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getTw() {
        return tw;
    }

    public void setTw(String tw) {
        this.tw = tw;
    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getXya() {
        return xya;
    }

    public void setXya(String xya) {
        this.xya = xya;
    }

    public String getXyb() {
        return xyb;
    }

    public void setXyb(String xyb) {
        this.xyb = xyb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getWg() {
        return wg;
    }

    public void setWg(String wg) {
        this.wg = wg;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public Integer getConsult() {
        return consult;
    }

    public void setConsult(Integer consult) {
        this.consult = consult;
    }

    public Integer getConsultResult() {
        return consultResult;
    }

    public void setConsultResult(Integer consultResult) {
        this.consultResult = consultResult;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
    
    public Integer getHeart() {
		return heart;
	}

	public void setHeart(Integer heart) {
		this.heart = heart;
	}

	public Integer getLiver() {
		return liver;
	}

	public void setLiver(Integer liver) {
		this.liver = liver;
	}

	public Integer getSpleen() {
		return spleen;
	}

	public void setSpleen(Integer spleen) {
		this.spleen = spleen;
	}

	public Integer getLung() {
		return lung;
	}

	public void setLung(Integer lung) {
		this.lung = lung;
	}
	
    public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(Integer finalResult) {
		this.finalResult = finalResult;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Check{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", allId=" + allId +
        ", isCheck=" + isCheck +
        ", type=" + type +
        ", isIdentity=" + isIdentity +
        ", tz=" + tz +
        ", finalResult=" + finalResult +
        ", tw=" + tw +
        ", mb=" + mb +
        ", xya=" + xya +
        ", xyb=" + xyb +
        ", xb=" + xb +
        ", fb=" + fb +
        ", pf=" + pf +
        ", wg=" + wg +
        ", sz=" + sz +
        ", heart=" + heart +
        ", liver=" + liver +
        ", spleen=" + spleen +
        ", lung=" + lung +
        ", consult=" + consult +
        ", consultResult=" + consultResult +
        ", result=" + result +
        ", reason=" + reason +
        ", userId=" + userId +
        ", isDel=" + isDel +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
