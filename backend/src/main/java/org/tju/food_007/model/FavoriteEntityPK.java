package org.tju.food_007.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteEntityPK implements Serializable {
    @Column(name = "com_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comId;
    @Column(name = "cus_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cusId;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteEntityPK that = (FavoriteEntityPK) o;
        return comId == that.comId && cusId == that.cusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, cusId);
    }
}
