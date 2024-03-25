package org.tju.food_007.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "regular_commodity", schema = "food_007", catalog = "")
public class RegularCommodityEntity {
    @Id
    @Column(name = "regular_com_ID")
    private int regularComId;
    @Basic
    @Column(name = "com_expirationDate")
    private Timestamp comExpirationDate;
    @Basic
    @Column(name = "com_producedDate")
    private Timestamp comProducedDate;

    public int getRegularComId() {
        return regularComId;
    }

    public void setRegularComId(Integer regularComId) {
        this.regularComId = regularComId;
    }

    public void setRegularComId(int regularComId) {
        this.regularComId = regularComId;
    }

    public Timestamp getComExpirationDate() {
        return comExpirationDate;
    }

    public void setComExpirationDate(Timestamp comExpirationDate) {
        this.comExpirationDate = comExpirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularCommodityEntity that = (RegularCommodityEntity) o;
        return regularComId == that.regularComId && Objects.equals(comExpirationDate, that.comExpirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regularComId, comExpirationDate);
    }

    public Timestamp getComProducedDate() {
        return comProducedDate;
    }

    public void setComProducedDate(Timestamp comProducedDate) {
        this.comProducedDate = comProducedDate;
    }
}
