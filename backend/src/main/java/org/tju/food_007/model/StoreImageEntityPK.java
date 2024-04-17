package org.tju.food_007.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class StoreImageEntityPK implements Serializable {
    @Column(name = "sto_ID")
    @Id
    private int stoId;
    @Column(name = "sto_image")
    @Basic
    @Id
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
        StoreImageEntityPK that = (StoreImageEntityPK) o;
        return stoId == that.stoId && Objects.equals(stoImage, that.stoImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoImage);
    }
}
