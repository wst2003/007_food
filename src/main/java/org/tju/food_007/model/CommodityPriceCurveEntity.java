package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "commodityPriceCurve", schema = "food_007", catalog = "")
@IdClass(CommodityPriceCurveEntityPK.class)
public class CommodityPriceCurveEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_pc_time")
    private Timestamp comPcTime;
    @Basic
    @Column(name = "com_pc_price")
    private BigDecimal comPcPrice;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public Timestamp getComPcTime() {
        return comPcTime;
    }

    public void setComPcTime(Timestamp comPcTime) {
        this.comPcTime = comPcTime;
    }

    public BigDecimal getComPcPrice() {
        return comPcPrice;
    }

    public void setComPcPrice(BigDecimal comPcPrice) {
        this.comPcPrice = comPcPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityPriceCurveEntity that = (CommodityPriceCurveEntity) o;
        return comId == that.comId && Objects.equals(comPcTime, that.comPcTime) && Objects.equals(comPcPrice, that.comPcPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comPcTime, comPcPrice);
    }
}
