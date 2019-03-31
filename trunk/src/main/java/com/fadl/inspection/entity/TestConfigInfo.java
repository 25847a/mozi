package com.fadl.inspection.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.fadl.common.DateUtil;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 检验配置记录表
 * </p>
 *
 * @author CJ
 * @since 2018-06-05
 */
@TableName("f_test_config_info")
public class TestConfigInfo extends Model<TestConfigInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 检测配置表ID
     */
    private Integer tcid;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 检测备注信息(用于质控报表显示)
     */
    private String tcMark;
    /**
     * 厂家ID
     */
    private Integer venderid;
    /**
     * 试剂ID
     */
    private Integer reagentid;
    /**
     * 缺省值
     */
    private String defaultValue;
    /**
     * 试剂批号 id
     */
    private Long reagentBatch;
    /**
     * 检测者ID
     */
    private Long testAdminid;
    /**
     * 核对者ID
     */
    private Long checkAdminid;
    /**
     * 报告者ID
     */
    private Long reportAdminid;
    /**
     * 检验方法ID
     */
    private Long testingMethodid;
    /**
     * 删除标记0:正常,1:删除
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 有效期
     */
    private String termOfValidity;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDate;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 截止日期
     */
    private String endTime;
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

    public String getTcMark() {
		return tcMark;
	}

	public void setTcMark(String tcMark) {
		this.tcMark = tcMark;
	}

	public Integer getTcid() {
        return tcid;
    }

    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public Integer getReagentid() {
        return reagentid;
    }

    public void setReagentid(Integer reagentid) {
        this.reagentid = reagentid;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getReagentBatch() {
        return reagentBatch;
    }

    public void setReagentBatch(Long reagentBatch) {
        this.reagentBatch = reagentBatch;
    }

    public Long getTestAdminid() {
        return testAdminid;
    }

    public void setTestAdminid(Long testAdminid) {
        this.testAdminid = testAdminid;
    }

    public Long getCheckAdminid() {
        return checkAdminid;
    }

    public void setCheckAdminid(Long checkAdminid) {
        this.checkAdminid = checkAdminid;
    }

    public Long getReportAdminid() {
        return reportAdminid;
    }

    public void setReportAdminid(Long reportAdminid) {
        this.reportAdminid = reportAdminid;
    }

    public Long getTestingMethodid() {
        return testingMethodid;
    }

    public void setTestingMethodid(Long testingMethodid) {
        this.testingMethodid = testingMethodid;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getTermOfValidity() {
        return termOfValidity;
    }
    public String getTermOfValidity1() {
    	 try {
 			return StringUtils.isEmpty(termOfValidity)?null:DateUtil.formatDate(DateUtil.formatDate(termOfValidity, DateUtil.yyyy_MM_dd_EN), DateUtil.yyyy_MM_dd_EN);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
         return null;
    }
    public void setTermOfValidity(String termOfValidity) {
        this.termOfValidity = termOfValidity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
        return "TestConfigInfo{" +
        "id=" + id +
        ", tcid=" + tcid +
        ", projectName=" + projectName +
        ", venderid=" + venderid +
        ", reagentid=" + reagentid +
        ", defaultValue=" + defaultValue +
        ", reagentBatch=" + reagentBatch +
        ", testAdminid=" + testAdminid +
        ", checkAdminid=" + checkAdminid +
        ", reportAdminid=" + reportAdminid +
        ", testingMethodid=" + testingMethodid +
        ", delFlag=" + delFlag +
        ", termOfValidity=" + termOfValidity +
        ", createDate=" + createDate +
        ", startDate=" + startDate +
        ", endTime=" + endTime +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
