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
 * 测试结果
 * </p>
 *
 * @author xim.xie
 * @since 2018-10-20
 */
@TableName("f_test_result")
public class TestResult extends Model<TestResult> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 批次板子编号
     */
    private String batchPlate;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 行
     */
    private Integer row;
    /**
     * 列
     */
    private Integer col;
    /**
     * Od值
     */
    private BigDecimal od;
    /**
     * 孔类型(0std 1smp 2pc 3pc2 4nc 5qc 6blk 7null)
     */
    private Integer holeType;
    /**
     * （+、-、？）？为灰区
     */
    private String strResultValue;
    /**
     * 项目名称(1alt 2HBsAG 3HCV 4HIV 5syphilis 6总蛋白量 )
     */
    private Integer proName;
    /**
     * 测试日期
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
    /**
     * 板序号（在机器中的位置）
     */
    private Integer plateNo;
    /**
     * 质控品批号
     */
    private String qcBatchNo;
    /**
     * 试剂批号
     */
    private String reagentBatchNo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchPlate() {
        return batchPlate;
    }

    public void setBatchPlate(String batchPlate) {
        this.batchPlate = batchPlate;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public BigDecimal getOd() {
        return od;
    }

    public void setOd(BigDecimal od) {
        this.od = od;
    }

    public Integer getHoleType() {
        return holeType;
    }

    public void setHoleType(Integer holeType) {
        this.holeType = holeType;
    }

    public String getStrResultValue() {
        return strResultValue;
    }

    public void setStrResultValue(String strResultValue) {
        this.strResultValue = strResultValue;
    }

    public Integer getProName() {
        return proName;
    }

    public void setProName(Integer proName) {
        this.proName = proName;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
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

    public String getPlasmaId() {
        return plasmaId;
    }

    public void setPlasmaId(String plasmaId) {
        this.plasmaId = plasmaId;
    }

    public Integer getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(Integer plateNo) {
        this.plateNo = plateNo;
    }

    public String getQcBatchNo() {
        return qcBatchNo;
    }

    public void setQcBatchNo(String qcBatchNo) {
        this.qcBatchNo = qcBatchNo;
    }

    public String getReagentBatchNo() {
        return reagentBatchNo;
    }

    public void setReagentBatchNo(String reagentBatchNo) {
        this.reagentBatchNo = reagentBatchNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TestResult{" +
        "id=" + id +
        ", batchPlate=" + batchPlate +
        ", sampleNo=" + sampleNo +
        ", row=" + row +
        ", col=" + col +
        ", od=" + od +
        ", holeType=" + holeType +
        ", strResultValue=" + strResultValue +
        ", proName=" + proName +
        ", testDate=" + testDate +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", plasmaId=" + plasmaId +
        ", plateNo=" + plateNo +
        ", qcBatchNo=" + qcBatchNo +
        ", reagentBatchNo=" + reagentBatchNo +
        "}";
    }
}
