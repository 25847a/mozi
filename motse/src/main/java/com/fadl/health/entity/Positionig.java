package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设置定位信息表
 * </p>
 *
 * @author jian
 * @since 2019-07-09
 */
@TableName("positionig")
public class Positionig extends Model<Positionig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 定位状态,0基站定位,1gps定位
     */
    @TableField("positioning_s")
    private String positioningS;
    /**
     * 定位数据
     */
    @TableField("positioning_data")
    private String positioningData;
    /**
     * 定位时间
     */
    private String cratetime;
    /**
     * imei
     */
    private String imei;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositioningS() {
        return positioningS;
    }

    public void setPositioningS(String positioningS) {
        this.positioningS = positioningS;
    }

    public String getPositioningData() {
        return positioningData;
    }

    public void setPositioningData(String positioningData) {
        this.positioningData = positioningData;
    }

    public String getCratetime() {
        return cratetime;
    }

    public void setCratetime(String cratetime) {
        this.cratetime = cratetime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Positionig{" +
        "id=" + id +
        ", positioningS=" + positioningS +
        ", positioningData=" + positioningData +
        ", cratetime=" + cratetime +
        ", imei=" + imei +
        "}";
    }
}
