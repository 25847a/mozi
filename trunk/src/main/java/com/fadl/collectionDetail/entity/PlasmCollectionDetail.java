package com.fadl.collectionDetail.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hkk
 * @since 2018-10-12
 */
@TableName("f_plasm_collection_detail")
public class PlasmCollectionDetail extends Model<PlasmCollectionDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 采浆表 id
     */
    private Long collectionId;
    /**
     * 第几采程
     */
    private Integer num;
    /**
     * 采集速度
     */
    private Integer speed;
    /**
     * 采浆量
     */
    private Integer amount;
    /**
     * 回输速度 
     */
    private Double returnSpeed;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getReturnSpeed() {
        return returnSpeed;
    }

    public void setReturnSpeed(Double returnSpeed) {
        this.returnSpeed = returnSpeed;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PlasmCollectionDetail{" +
        "id=" + id +
        ", collectionId=" + collectionId +
        ", num=" + num +
        ", speed=" + speed +
        ", amount=" + amount +
        ", returnSpeed=" + returnSpeed +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", plasmaId=" + plasmaId +
        "}";
    }
}
