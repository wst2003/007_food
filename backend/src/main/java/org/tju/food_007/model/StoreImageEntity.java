package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "storeImage", schema = "food_007", catalog = "")
@IdClass(StoreImageEntityPK.class)
public class StoreImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sto_ID")
    private int stoId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "sto_image")
    private String stoImage;

    public int getStoId() {
        return stoId;
    }

    public void setStoId(int stoId) {
        this.stoId = stoId;
    }

    public String getStoImage() {
        return stoImage;
    }

    public void setStoImage(String stoImage) {
        this.stoImage = stoImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreImageEntity that = (StoreImageEntity) o;
        return stoId == that.stoId && Objects.equals(stoImage, that.stoImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoImage);
    }
}
