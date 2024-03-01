package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "indent", schema = "food_007", catalog = "")
public class IndentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ind_ID")
    private int indId;
    @Basic
    @Column(name = "cus_ID")
    private int cusId;
    @Basic
    @Column(name = "ind_money")
    private BigDecimal indMoney;
    @Basic
    @Column(name = "ind_creationTime")
    private Timestamp indCreationTime;
    @Basic
    @Column(name = "ind_verificationCode")
    private String indVerificationCode;
    @Basic
    @Column(name = "ind_notes")
    private String indNotes;
    @Basic
    @Column(name = "ind_state")
    private int indState;
    @Basic
    @Column(name = "food_quality_score")
    private BigDecimal foodQualityScore;
    @Basic
    @Column(name = "service_quality_score")
    private BigDecimal serviceQualityScore;
    @Basic
    @Column(name = "price_performance_ratio")
    private BigDecimal pricePerformanceRatio;
    @Basic
    @Column(name = "delivery_method")
    private int deliveryMethod;
    @Basic
    @Column(name = "delivery_address")
    private String deliveryAddress;

    public int getIndId() {
        return indId;
    }

    public void setIndId(int indId) {
        this.indId = indId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public BigDecimal getIndMoney() {
        return indMoney;
    }

    public void setIndMoney(BigDecimal indMoney) {
        this.indMoney = indMoney;
    }

    public Timestamp getIndCreationTime() {
        return indCreationTime;
    }

    public void setIndCreationTime(Timestamp indCreationTime) {
        this.indCreationTime = indCreationTime;
    }

    public String getIndVerificationCode() {
        return indVerificationCode;
    }

    public void setIndVerificationCode(String indVerificationCode) {
        this.indVerificationCode = indVerificationCode;
    }

    public String getIndNotes() {
        return indNotes;
    }

    public void setIndNotes(String indNotes) {
        this.indNotes = indNotes;
    }

    public int getIndState() {
        return indState;
    }

    public void setIndState(int indState) {
        this.indState = indState;
    }

    public BigDecimal getFoodQualityScore() {
        return foodQualityScore;
    }

    public void setFoodQualityScore(BigDecimal foodQualityScore) {
        this.foodQualityScore = foodQualityScore;
    }

    public BigDecimal getServiceQualityScore() {
        return serviceQualityScore;
    }

    public void setServiceQualityScore(BigDecimal serviceQualityScore) {
        this.serviceQualityScore = serviceQualityScore;
    }

    public BigDecimal getPricePerformanceRatio() {
        return pricePerformanceRatio;
    }

    public void setPricePerformanceRatio(BigDecimal pricePerformanceRatio) {
        this.pricePerformanceRatio = pricePerformanceRatio;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentEntity that = (IndentEntity) o;
        return indId == that.indId && cusId == that.cusId && indState == that.indState && deliveryMethod == that.deliveryMethod && Objects.equals(indMoney, that.indMoney) && Objects.equals(indCreationTime, that.indCreationTime) && Objects.equals(indVerificationCode, that.indVerificationCode) && Objects.equals(indNotes, that.indNotes) && Objects.equals(foodQualityScore, that.foodQualityScore) && Objects.equals(serviceQualityScore, that.serviceQualityScore) && Objects.equals(pricePerformanceRatio, that.pricePerformanceRatio) && Objects.equals(deliveryAddress, that.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indId, cusId, indMoney, indCreationTime, indVerificationCode, indNotes, indState, foodQualityScore, serviceQualityScore, pricePerformanceRatio, deliveryMethod, deliveryAddress);
    }
}
