package com.fadl.elisa.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 酶标板设备接口信息
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_api")
public class ElisaApi extends Model<ElisaApi> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 是否空白 0否 1是
     */
    private Integer isBlank;
    /**
     * 是否振荡 0否 1是
     */
    private Integer isOscillation;
    /**
     * 是否双波检测 0否 1是
     */
    private Integer isDuWaveDetection;

    /**
     * 程序模式
     */
    private Long programModel;
    /**
     * 进板方式
     */
    private Long inboardMode;
    /**
     * 振荡速度
     */
    private Long oscillationSpeed;
    /**
     * 振荡时间 (秒)
     */
    private Integer oscillationTime;
    /**
     * 主滤光片
     */
    private Integer mainFilter;
    /**
     * 副滤光片
     */
    private Integer subFilter;
    /**
     * 延长时间
     */
    private Integer extensionOfTime;
    /**
     * 读板次数
     */
    private Integer readCount;
    /**
     * 间隔时间
     */
    private Integer intervals;
    /**
     * 是否自检 0否 1是
     */
    private Integer isSelfTest;
    /**
     * 使能使用空白孔 0否 1是
     */
    private Integer canUseBlankHole;
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
     * 删除标记0未删除. 1已删除
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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getIsBlank() {
        return isBlank;
    }

    public void setIsBlank(Integer isBlank) {
        this.isBlank = isBlank;
    }

    public Integer getIsOscillation() {
        return isOscillation;
    }

    public void setIsOscillation(Integer isOscillation) {
        this.isOscillation = isOscillation;
    }

    public Integer getIsDuWaveDetection() {
        return isDuWaveDetection;
    }

    public void setIsDuWaveDetection(Integer isDuWaveDetection) {
        this.isDuWaveDetection = isDuWaveDetection;
    }

	public Long getProgramModel() {
        return programModel;
    }

    public void setProgramModel(Long programModel) {
        this.programModel = programModel;
    }

    public Long getInboardMode() {
        return inboardMode;
    }

    public void setInboardMode(Long inboardMode) {
        this.inboardMode = inboardMode;
    }

    public Long getOscillationSpeed() {
        return oscillationSpeed;
    }

    public void setOscillationSpeed(Long oscillationSpeed) {
        this.oscillationSpeed = oscillationSpeed;
    }

    public Integer getOscillationTime() {
        return oscillationTime;
    }

    public void setOscillationTime(Integer oscillationTime) {
        this.oscillationTime = oscillationTime;
    }

    public Integer getMainFilter() {
        return mainFilter;
    }

    public void setMainFilter(Integer mainFilter) {
        this.mainFilter = mainFilter;
    }

    public Integer getSubFilter() {
        return subFilter;
    }

    public void setSubFilter(Integer subFilter) {
        this.subFilter = subFilter;
    }

    public Integer getExtensionOfTime() {
        return extensionOfTime;
    }

    public void setExtensionOfTime(Integer extensionOfTime) {
        this.extensionOfTime = extensionOfTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public Integer getIsSelfTest() {
        return isSelfTest;
    }

    public void setIsSelfTest(Integer isSelfTest) {
        this.isSelfTest = isSelfTest;
    }

    public Integer getCanUseBlankHole() {
        return canUseBlankHole;
    }

    public void setCanUseBlankHole(Integer canUseBlankHole) {
        this.canUseBlankHole = canUseBlankHole;
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
        return "ElisaApi{" +
        "id=" + id +
        ", projectName=" + projectName +
        ", isBlank=" + isBlank +
        ", isOscillation=" + isOscillation +
        ", isDuWaveDetection=" + isDuWaveDetection +
        ", programModel=" + programModel +
        ", inboardMode=" + inboardMode +
        ", oscillationSpeed=" + oscillationSpeed +
        ", oscillationTime=" + oscillationTime +
        ", mainFilter=" + mainFilter +
        ", subFilter=" + subFilter +
        ", extensionOfTime=" + extensionOfTime +
        ", readCount=" + readCount +
        ", intervals=" + intervals +
        ", isSelfTest=" + isSelfTest +
        ", canUseBlankHole=" + canUseBlankHole +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
