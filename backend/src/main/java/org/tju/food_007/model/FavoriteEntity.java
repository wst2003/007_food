package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "favorite", schema = "food_007", catalog = "")
@IdClass(FavoriteEntityPK.class)
public class FavoriteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cus_ID")
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
        FavoriteEntity that = (FavoriteEntity) o;
        return comId == that.comId && cusId == that.cusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, cusId);
    }
}
