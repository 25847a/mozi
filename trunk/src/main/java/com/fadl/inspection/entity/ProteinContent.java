package com.fadl.inspection.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fadl.common.entity.BaseEntity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 检验-蛋白含量
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@TableName("f_protein_content")
public class ProteinContent extends Model<ProteinContent> {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 折射仪刻度值
     */
    private String value;
    /**
     * 蛋白值
     */
    private String protein;
    /**
     * 判定结果(0、合格 1、不合格)
     */
    private Integer result;

    private Long allId;
    /**
     * 蛋白含量配置的检验配置InfoId
     */
    private Long tciId;
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

	public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getTciId() {
		return tciId;
	}

	public void setTciId(Long tciId) {
		this.tciId = tciId;
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

	public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProteinContent{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", value=" + value +
        ", protein=" + protein +
        ", result=" + result +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
