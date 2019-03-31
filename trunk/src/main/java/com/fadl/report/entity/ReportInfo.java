package com.fadl.report.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 报表页眉页脚
 * </p>
 *
 * @author xim.xie
 * @since 2018-09-07
 */
@TableName("f_report_info")
public class ReportInfo extends Model<ReportInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 报表序号，使用UUID
     */
    private String reportNO;
    /**
     * 页眉左
     */
    private String headLeft;
    /**
     * 页眉中
     */
    private String headCenter;
    /**
     * 页眉右
     */
    private String headRight;
    /**
     * 页脚左
     */
    private String footLeft;
    /**
     * 页脚中
     */
    private String footCenter;
    /**
     * 页脚右
     */
    private String footRight;
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
     * 标注一般用于标注哪个报表使用
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportNO() {
        return reportNO;
    }

    public void setReportNO(String reportNO) {
        this.reportNO = reportNO;
    }

    public String getHeadLeft() {
        return headLeft;
    }

    public void setHeadLeft(String headLeft) {
        this.headLeft = headLeft;
    }

    public String getHeadCenter() {
        return headCenter;
    }

    public void setHeadCenter(String headCenter) {
        this.headCenter = headCenter;
    }

    public String getHeadRight() {
        return headRight;
    }

    public void setHeadRight(String headRight) {
        this.headRight = headRight;
    }

    public String getFootLeft() {
        return footLeft;
    }

    public void setFootLeft(String footLeft) {
        this.footLeft = footLeft;
    }

    public String getFootCenter() {
        return footCenter;
    }

    public void setFootCenter(String footCenter) {
        this.footCenter = footCenter;
    }

    public String getFootRight() {
        return footRight;
    }

    public void setFootRight(String footRight) {
        this.footRight = footRight;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
        "id=" + id +
        ", reportNO=" + reportNO +
        ", headLeft=" + headLeft +
        ", headCenter=" + headCenter +
        ", headRight=" + headRight +
        ", footLeft=" + footLeft +
        ", footCenter=" + footCenter +
        ", footRight=" + footRight +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", plasmaId=" + plasmaId +
        ", remark=" + remark +
        "}";
    }
}
