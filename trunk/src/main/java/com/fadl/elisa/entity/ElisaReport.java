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
 * 酶标板报表头设置
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
@TableName("f_elisa_report")
public class ElisaReport extends Model<ElisaReport> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酶标板模板ID
     */
    private Long etId;
    /**
     * 报表名称
     */
    private String reportName;
    /**
     * 报表签名
     */
    private String reportSignature;
    /**
     * 报表编号
     */
    private String reportNO;
    /**
     * 送检单位
     */
    private String inspectionUnit;
    /**
     * 送检标本
     */
    private String inspectionSpecimen;
    /**
     * 检测单位
     */
    private String detectionUnit;
    /**
     * 酶标仪型号及厂家
     */
    private String elisaEquipmentAndManufacturers;
    /**
     * 洗板机型号及厂家
     */
    private String cleaningEquipmentAndManufacturers;
    /**
     * 质控品批号
     */
    private String qualityControlProducts;
    /**
     * 使用波长
     */
    private String useWavelength;
    /**
     * 判断结果
     */
    private String criticalResult;
    /**
     * 质控记录编号
     */
    private String qualityControlNO;
    /**
     * 生效日期
     */
    private String effectiveDate;
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

    public Long getEtId() {
        return etId;
    }

    public void setEtId(Long etId) {
        this.etId = etId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportSignature() {
        return reportSignature;
    }

    public void setReportSignature(String reportSignature) {
        this.reportSignature = reportSignature;
    }

    public String getReportNO() {
        return reportNO;
    }

    public void setReportNO(String reportNO) {
        this.reportNO = reportNO;
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit;
    }

    public String getInspectionSpecimen() {
        return inspectionSpecimen;
    }

    public void setInspectionSpecimen(String inspectionSpecimen) {
        this.inspectionSpecimen = inspectionSpecimen;
    }

    public String getDetectionUnit() {
        return detectionUnit;
    }

    public void setDetectionUnit(String detectionUnit) {
        this.detectionUnit = detectionUnit;
    }

    public String getElisaEquipmentAndManufacturers() {
        return elisaEquipmentAndManufacturers;
    }

    public void setElisaEquipmentAndManufacturers(String elisaEquipmentAndManufacturers) {
        this.elisaEquipmentAndManufacturers = elisaEquipmentAndManufacturers;
    }

    public String getCleaningEquipmentAndManufacturers() {
        return cleaningEquipmentAndManufacturers;
    }

    public void setCleaningEquipmentAndManufacturers(String cleaningEquipmentAndManufacturers) {
        this.cleaningEquipmentAndManufacturers = cleaningEquipmentAndManufacturers;
    }

    public String getQualityControlProducts() {
        return qualityControlProducts;
    }

    public void setQualityControlProducts(String qualityControlProducts) {
        this.qualityControlProducts = qualityControlProducts;
    }

    public String getUseWavelength() {
        return useWavelength;
    }

    public void setUseWavelength(String useWavelength) {
        this.useWavelength = useWavelength;
    }

    public String getCriticalResult() {
        return criticalResult;
    }

    public void setCriticalResult(String criticalResult) {
        this.criticalResult = criticalResult;
    }

    public String getQualityControlNO() {
        return qualityControlNO;
    }

    public void setQualityControlNO(String qualityControlNO) {
        this.qualityControlNO = qualityControlNO;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
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
        return "ElisaReport{" +
        "id=" + id +
        ", etId=" + etId +
        ", reportName=" + reportName +
        ", reportSignature=" + reportSignature +
        ", reportNO=" + reportNO +
        ", inspectionUnit=" + inspectionUnit +
        ", inspectionSpecimen=" + inspectionSpecimen +
        ", detectionUnit=" + detectionUnit +
        ", elisaEquipmentAndManufacturers=" + elisaEquipmentAndManufacturers +
        ", cleaningEquipmentAndManufacturers=" + cleaningEquipmentAndManufacturers +
        ", qualityControlProducts=" + qualityControlProducts +
        ", useWavelength=" + useWavelength +
        ", criticalResult=" + criticalResult +
        ", qualityControlNO=" + qualityControlNO +
        ", effectiveDate=" + effectiveDate +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
