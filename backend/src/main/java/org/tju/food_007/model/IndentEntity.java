package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author WGY
 * @create 2024-04-01-18:26
 */
@Entity
@Table(name = "indent", schema = "food_007", catalog = "")
public class IndentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ind_ID")
    private Integer indId;
    @Basic
    @Column(name = "cus_ID")
    private Integer cusId;
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
    private Integer indState;
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
    private Integer deliveryMethod;
    @Basic
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Basic
    @Column(name = "delivery_altitude")
    private BigDecimal deliveryAltitude;
    @Basic
    @Column(name = "delivery_longitude")
    private BigDecimal deliveryLongitude;

    public Integer getIndId() {
        return indId;
    }

    public void setIndId(Integer indId) {
        this.indId = indId;
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
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

    public Integer getIndState() {
        return indState;
    }

    public void setIndState(Integer indState) {
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

    public Integer getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Integer deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getDeliveryAltitude() {
        return deliveryAltitude;
    }

    public void setDeliveryAltitude(BigDecimal deliveryAltitude) {
        this.deliveryAltitude = deliveryAltitude;
    }

    public BigDecimal getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(BigDecimal deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentEntity that = (IndentEntity) o;
        return Objects.equals(indId, that.indId) && Objects.equals(cusId, that.cusId) && Objects.equals(indMoney, that.indMoney) && Objects.equals(indCreationTime, that.indCreationTime) && Objects.equals(indVerificationCode, that.indVerificationCode) && Objects.equals(indNotes, that.indNotes) && Objects.equals(indState, that.indState) && Objects.equals(foodQualityScore, that.foodQualityScore) && Objects.equals(serviceQualityScore, that.serviceQualityScore) && Objects.equals(pricePerformanceRatio, that.pricePerformanceRatio) && Objects.equals(deliveryMethod, that.deliveryMethod) && Objects.equals(deliveryAddress, that.deliveryAddress) && Objects.equals(deliveryAltitude, that.deliveryAltitude) && Objects.equals(deliveryLongitude, that.deliveryLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indId, cusId, indMoney, indCreationTime, indVerificationCode, indNotes, indState, foodQualityScore, serviceQualityScore, pricePerformanceRatio, deliveryMethod, deliveryAddress, deliveryAltitude, deliveryLongitude);
    }
}
