package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "customer_love", schema = "food_007", catalog = "")
@IdClass(CustomerLoveEntityPK.class)
public class CustomerLoveEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cus_ID")
    private int cusId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_category")
    private String comCategory;
    @Basic
    @Column(name = "cus_love_weight")
    private BigDecimal cusLoveWeight;

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getComCategory() {
        return comCategory;
    }

    public void setComCategory(String comCategory) {
        this.comCategory = comCategory;
    }

    public BigDecimal getCusLoveWeight() {
        return cusLoveWeight;
    }

    public void setCusLoveWeight(BigDecimal cusLoveWeight) {
        this.cusLoveWeight = cusLoveWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerLoveEntity that = (CustomerLoveEntity) o;
        return cusId == that.cusId && Objects.equals(comCategory, that.comCategory) && Objects.equals(cusLoveWeight, that.cusLoveWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cusId, comCategory, cusLoveWeight);
    }
}
