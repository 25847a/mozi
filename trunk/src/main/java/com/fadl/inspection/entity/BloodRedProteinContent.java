package com.fadl.inspection.entity;

import java.io.Serializable;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 检验-血红蛋白含量表
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
@TableName("f_blood_red_protein_content")
public class BloodRedProteinContent extends Model<BloodRedProteinContent> {

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
    
    private Long checkedAdminId;
    
    private Integer isCollection;
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
     * 硫酸铜(0、液上 1、液下)
     */
    private Integer bluestone;
    /**
     * 硫酸铜检测结果(系统配置)
     */
    private Long resultId;
    /**
     * 血红蛋白含量配置的检验配置InfoId
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

    public Integer getBluestone() {
        return bluestone;
    }

    public void setBluestone(Integer bluestone) {
        this.bluestone = bluestone;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }


    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return StringUtils.isEmpty(updateDate)?"":updateDate;
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

    public Long getTciId() {
		return tciId;
	}

	public void setTciId(Long tciId) {
		this.tciId = tciId;
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
	
	public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}

	public Long getCheckedAdminId() {
		return checkedAdminId;
	}

	public void setCheckedAdminId(Long checkedAdminId) {
		this.checkedAdminId = checkedAdminId;
	}
	

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BloodRedProteinContent{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", bluestone=" + bluestone +
        ", resultId=" + resultId +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
