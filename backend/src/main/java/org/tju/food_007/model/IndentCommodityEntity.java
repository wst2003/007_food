package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "indent_commodity", schema = "food_007", catalog = "")
@IdClass(IndentCommodityEntityPK.class)
public class IndentCommodityEntity {
    @Id
    @Column(name = "ind_ID")
    private int indId;
    @Id
    @Column(name = "com_ID")
    private int comId;
    @Basic
    @Column(name = "ind_quantity")
    private int indQuantity;
    @Basic
    @Column(name = "commodity_money")
    private BigDecimal commodityMoney;
    @Basic
    @Column(name = "rating_type")
    private int ratingType;

    public Integer getIndId() {
        return indId;
    }

    public void setIndId(int indId) {
        this.indId = indId;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public int getIndQuantity() {
        return indQuantity;
    }

    public void setIndQuantity(int indQuantity) {
        this.indQuantity = indQuantity;
    }

    public BigDecimal getCommodityMoney() {
        return commodityMoney;
    }

    public void setCommodityMoney(BigDecimal commodityMoney) {
        this.commodityMoney = commodityMoney;
    }

    public int getRatingType() {
        return ratingType;
    }

    public void setRatingType(int ratingType) {
        this.ratingType = ratingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentCommodityEntity that = (IndentCommodityEntity) o;
        return indId == that.indId && comId == that.comId && indQuantity == that.indQuantity && ratingType == that.ratingType && Objects.equals(commodityMoney, that.commodityMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indId, comId, indQuantity, commodityMoney, ratingType);
    }
}
