package com.fadl.elisa.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.fadl.common.DateUtil;
import com.fadl.inspection.entity.TestConfigInfo;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 酶标板检测信息
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_info")
public class ElisaInfo extends Model<ElisaInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酶标板模板ID
     */
    private Long etId;

    /**
     * 操作人ID
     */
    private Long opAdmin;
    /**
     * 质控品ID
     */
    private Long qcId;
    /**
     * cutoff公式计算后的结果
     */
    private BigDecimal cutOffValue;
    /**
     * 试剂ID
     */
    private Long reagentId;
    /**
     * 序号
     */
    private String sequenceNumber;
    /**
     * 全序号
     */
    private String allSequenceNumber;
    /**
     * 检测项目名称,对应config表中的elisa_check_project
     */
    private String projectName;
    /**
     * 检验配置详情ID
     */
    private Long tciId;
    /**
     * 检验时间
     */
    private String testDate;
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

	/**
     * 酶标板孔信息列表
     */
    @TableField(exist = false)
    private List<ElisaValues> elisaValues;
    /**
     * 特殊酶标板孔信息列表
     */
    @TableField(exist = false)
    private List<ElisaValues> specialElisaValues;
    /**
     * 配置信息详情 根据试剂批号查询
     */
    @TableField(exist = false)
    private TestConfigInfo testConfigInfo; 
    /**
     * 使用的酶标板模板信息
     */
    @TableField(exist = false)
    private ElisaTemplate elisaTemplate;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String endDate;
    /**
     * 当前板的质控结果
     */
    @TableField(exist = false)
    private String qcResult;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEtId() {
        return etId;
    }

    public void setEtId(Long etId) {
        this.etId = etId;
    }



    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
    
    public String getUpdateDate1() {
        try {
			return StringUtils.isEmpty(updateDate)?null:DateUtil.formatDate(DateUtil.formatDate(updateDate, DateUtil.yyyy_MM_dd_EN), DateUtil.yyyy_MM_dd_EN);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public String getTestDate1() {
        try {
			return StringUtils.isEmpty(testDate)?getUpdateDate1():DateUtil.formatDate(DateUtil.formatDate(testDate, DateUtil.yyyy_MM_dd_EN), DateUtil.yyyy_MM_dd_EN);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }


    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public TestConfigInfo getTestConfigInfo() {
		return testConfigInfo;
	}

	
	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public void setTestConfigInfo(TestConfigInfo testConfigInfo) {
		this.testConfigInfo = testConfigInfo;
	}

	public String getAllSequenceNumber() {
		return allSequenceNumber;
	}

	public void setAllSequenceNumber(String allSequenceNumber) {
		this.allSequenceNumber = allSequenceNumber;
	}

	public Long getOpAdmin() {
		return opAdmin;
	}

	public void setOpAdmin(Long opAdmin) {
		this.opAdmin = opAdmin;
	}

	public List<ElisaValues> getElisaValues() {
		return elisaValues;
	}

	public void setElisaValues(List<ElisaValues> elisaValues) {
		this.elisaValues = elisaValues;
	}

	
	public BigDecimal getCutOffValue() {
		return cutOffValue;
	}

	public void setCutOffValue(BigDecimal cutOffValue) {
		this.cutOffValue = cutOffValue;
	}

	public List<ElisaValues> getSpecialElisaValues() {
		return specialElisaValues;
	}

	public void setSpecialElisaValues(List<ElisaValues> specialElisaValues) {
		this.specialElisaValues = specialElisaValues;
	}

	public ElisaTemplate getElisaTemplate() {
		return elisaTemplate;
	}

	public void setElisaTemplate(ElisaTemplate elisaTemplate) {
		this.elisaTemplate = elisaTemplate;
	}
	
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public Long getTciId() {
		return tciId;
	}

	public void setTciId(Long tciId) {
		this.tciId = tciId;
	}
	
	public String getQcResult() {
		return qcResult;
	}

	public void setQcResult(String qcResult) {
		this.qcResult = qcResult;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ElisaInfo{" +
        "id=" + id +
        ", etId=" + etId +
        ", sequenceNumber=" + sequenceNumber +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
