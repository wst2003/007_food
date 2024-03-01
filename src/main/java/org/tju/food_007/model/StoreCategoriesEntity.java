package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "store_categories", schema = "food_007", catalog = "")
@IdClass(StoreCategoriesEntityPK.class)
public class StoreCategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "store_ID")
    private int storeId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_category")
    private String comCategory;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getComCategory() {
        return comCategory;
    }

    public void setComCategory(String comCategory) {
        this.comCategory = comCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreCategoriesEntity that = (StoreCategoriesEntity) o;
        return storeId == that.storeId && Objects.equals(comCategory, that.comCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, comCategory);
    }
}
