package com.fadl.elisa.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 酶标板模板表
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_template")
public class ElisaTemplate extends Model<ElisaTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * cutoff计算公式
     */
    private String cutoffFormula;
    
    private Long apiId;
    
    /**
     * 质控品的ID(f_supplies_info)
     */
    private Long qcId;
    
    /**
     * 对应的试剂ID(f_supplies_info)
     */
    private Long reagentId;
    /**
     * 检测项目Id
     */
    private Integer projectId;
    /**
     * 是否是自动化设备使用 0:否， 1：是
     */
    private Integer isAuto;
    
    /**
     * 排列方式 0:横向， 1：纵向
     */
    private Integer arrangement;
    
    /**
     * 报表头设置 
     */
    @TableField(exist = false)
    private ElisaReport elisaReport;
    /**
     * 模板使用的API接口设置 
     */
    @TableField(exist = false)
    private ElisaApi elisaApi;
    /**
     * 灰区设置
     */
    @TableField(exist = false)
    private ElisaGrayAreaSettings elisaGrayAreaSettings;
    /**
     * 计量方式设置
     */
    @TableField(exist = false)
    private ElisaMeasurementMethod elisaMeasurementMethod;
    /**
     * 模板常用设置
     */
    @TableField(exist = false)
    private ElisaCommonSetting elisaCommonSetting;
    /**
     * 酶标板孔值记录信息
     */
    @TableField(exist = false)
    private List<ElisaTemplateValues> elisaTemplateValues;
    
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
     * 删除标记0否 1是
     */
    private Integer delFlag;
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

    
    public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCutoffFormula() {
        return cutoffFormula;
    }

    public void setCutoffFormula(String cutoffFormula) {
        this.cutoffFormula = cutoffFormula;
    }

    public Integer getArrangement() {
		return arrangement;
	}

	public void setArrangement(Integer arrangement) {
		this.arrangement = arrangement;
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

    public Integer getDelFlag() {
		return delFlag;
	}

	public Long getApiId() {
		return apiId;
	}

	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}

	
	public Long getQcId() {
		return qcId;
	}

	public void setQcId(Long qcId) {
		this.qcId = qcId;
	}

	public Long getReagentId() {
		return reagentId;
	}

	public void setReagentId(Long reagentId) {
		this.reagentId = reagentId;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	
	public ElisaGrayAreaSettings getElisaGrayAreaSettings() {
		return elisaGrayAreaSettings;
	}

	public void setElisaGrayAreaSettings(ElisaGrayAreaSettings elisaGrayAreaSettings) {
		this.elisaGrayAreaSettings = elisaGrayAreaSettings;
	}

	public ElisaReport getElisaReport() {
		return elisaReport;
	}

	public void setElisaReport(ElisaReport elisaReport) {
		this.elisaReport = elisaReport;
	}

	public ElisaMeasurementMethod getElisaMeasurementMethod() {
		return elisaMeasurementMethod;
	}

	public void setElisaMeasurementMethod(ElisaMeasurementMethod elisaMeasurementMethod) {
		this.elisaMeasurementMethod = elisaMeasurementMethod;
	}

	public ElisaCommonSetting getElisaCommonSetting() {
		return elisaCommonSetting;
	}

	public void setElisaCommonSetting(ElisaCommonSetting elisaCommonSetting) {
		this.elisaCommonSetting = elisaCommonSetting;
	}

	public List<ElisaTemplateValues> getElisaTemplateValues() {
		return elisaTemplateValues;
	}

	public void setElisaTemplateValues(List<ElisaTemplateValues> elisaTemplateValues) {
		this.elisaTemplateValues = elisaTemplateValues;
	}

	
	
	public Integer getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}

	public ElisaApi getElisaApi() {
		return elisaApi;
	}

	public void setElisaApi(ElisaApi elisaApi) {
		this.elisaApi = elisaApi;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ElisaTemplate{" +
        "id=" + id +
        ", templateName=" + templateName +
        ", cutoffFormula=" + cutoffFormula +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
