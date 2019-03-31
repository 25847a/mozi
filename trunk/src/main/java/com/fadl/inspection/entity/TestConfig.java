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
 * 检验配置表
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
@TableName("f_test_config")
public class TestConfig extends Model<TestConfig> {

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
     * 模板名称
     */
    private String templateName;
    /**
     * 是否默认配置(0:否,1:是)
     */
    private Integer isDefault;
    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
    /**
     * 新浆员是否抽小样(0:否,1:是)
     */
    private Integer isSample;
    /**
     * 固定浆员蛋白值合格最小值
     */
    private Double fixedProteinMin;
    /**
     * 固定浆员蛋白值合格最大值
     */
    private Double fixedProteinMax;
    /**
     * 非固定浆员蛋白值合格最小值
     */
    private Double unfixedProteinMin;
    /**
     * 非固定浆员蛋白值合格最大值
     */
    private Double unfixedProteinMax;

    /**
     * 删除标记0:正常,1:删除
     */
    private Integer delFlag;

    /**
     * 配置日期
     */
    private String cmDate;
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

	public TestConfig() {
    	
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSample() {
        return isSample;
    }

    public void setIsSample(Integer isSample) {
        this.isSample = isSample;
    }

	public Double getFixedProteinMin() {
		return fixedProteinMin;
	}

	public void setFixedProteinMin(Double fixedProteinMin) {
		this.fixedProteinMin = fixedProteinMin;
	}

	public Double getFixedProteinMax() {
		return fixedProteinMax;
	}

	public void setFixedProteinMax(Double fixedProteinMax) {
		this.fixedProteinMax = fixedProteinMax;
	}

	public Double getUnfixedProteinMin() {
		return unfixedProteinMin;
	}

	public void setUnfixedProteinMin(Double unfixedProteinMin) {
		this.unfixedProteinMin = unfixedProteinMin;
	}

	public Double getUnfixedProteinMax() {
		return unfixedProteinMax;
	}

	public void setUnfixedProteinMax(Double unfixedProteinMax) {
		this.unfixedProteinMax = unfixedProteinMax;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getCmDate() {
		return StringUtils.isEmpty(cmDate)?cmDate:cmDate.substring(0, 10);
	}

	public void setCmDate(String cmDate) {
		this.cmDate = cmDate;
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
        return "TestConfig{" +
        "id=" + id +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", templateName=" + templateName +
        ", isDefault=" + isDefault +
        ", status=" + status +
        ", isSample=" + isSample +
        ", fixedProteinMin=" + fixedProteinMin +
        ", fixedProteinMax=" + fixedProteinMax +
        ", unfixedProteinMin=" + unfixedProteinMin +
        ", unfixedProteinMax=" + unfixedProteinMax +
        ", cmDate=" + cmDate +
        ", delFlag=" + delFlag +
        "}";
    }
}
