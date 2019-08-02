package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 健康数据(原始数据)
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@TableName("equipment_data")
public class EquipmentData extends Model<EquipmentData> {

    private static final long serialVersionUID = 1L;

    /**
     * Id 自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 当天步数
     */
    @TableField("step_when")
    private Integer stepWhen;
    /**
     * 卡里路
     */
    private Integer carrieroad;
    /**
     * 数据上传时间
     */
    private String createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStepWhen() {
        return stepWhen;
    }

    public void setStepWhen(Integer stepWhen) {
        this.stepWhen = stepWhen;
    }

    public Integer getCarrieroad() {
        return carrieroad;
    }

    public void setCarrieroad(Integer carrieroad) {
        this.carrieroad = carrieroad;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EquipmentData{" +
        "id=" + id +
        ", userId=" + userId +
        ", stepWhen=" + stepWhen +
        ", carrieroad=" + carrieroad +
        ", createtime=" + createtime +
        "}";
    }
}
