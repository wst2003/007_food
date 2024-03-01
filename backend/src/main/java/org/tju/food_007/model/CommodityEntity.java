package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "commodity", schema = "food_007", catalog = "")
public class CommodityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @Basic
    @Column(name = "com_name")
    private String comName;
    @Basic
    @Column(name = "com_introduction")
    private String comIntroduction;
    @Basic
    @Column(name = "com_uploadDate")
    private Timestamp comUploadDate;
    @Basic
    @Column(name = "com_left")
    private int comLeft;
    @Basic
    @Column(name = "sto_ID")
    private int stoId;
    @Basic
    @Column(name = "com_type")
    private int comType;
    @Basic
    @Column(name = "com_oriPrice")
    private BigDecimal comOriPrice;
    @Basic
    @Column(name = "praise_rate")
    private BigDecimal praiseRate;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComIntroduction() {
        return comIntroduction;
    }

    public void setComIntroduction(String comIntroduction) {
        this.comIntroduction = comIntroduction;
    }

    public Timestamp getComUploadDate() {
        return comUploadDate;
    }

    public void setComUploadDate(Timestamp comUploadDate) {
        this.comUploadDate = comUploadDate;
    }

    public int getComLeft() {
        return comLeft;
    }

    public void setComLeft(int comLeft) {
        this.comLeft = comLeft;
    }

    public int getStoId() {
        return stoId;
    }

    public void setStoId(int stoId) {
        this.stoId = stoId;
    }

    public int getComType() {
        return comType;
    }

    public void setComType(int comType) {
        this.comType = comType;
    }

    public BigDecimal getComOriPrice() {
        return comOriPrice;
    }

    public void setComOriPrice(BigDecimal comOriPrice) {
        this.comOriPrice = comOriPrice;
    }

    public BigDecimal getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(BigDecimal praiseRate) {
        this.praiseRate = praiseRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityEntity that = (CommodityEntity) o;
        return comId == that.comId && comLeft == that.comLeft && stoId == that.stoId && comType == that.comType && Objects.equals(comName, that.comName) && Objects.equals(comIntroduction, that.comIntroduction) && Objects.equals(comUploadDate, that.comUploadDate) && Objects.equals(comOriPrice, that.comOriPrice) && Objects.equals(praiseRate, that.praiseRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comName, comIntroduction, comUploadDate, comLeft, stoId, comType, comOriPrice, praiseRate);
    }
}
