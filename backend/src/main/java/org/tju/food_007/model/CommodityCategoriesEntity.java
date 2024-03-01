package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "commodity_categories", schema = "food_007", catalog = "")
@IdClass(CommodityCategoriesEntityPK.class)
public class CommodityCategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_category")
    private String comCategory;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
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
        CommodityCategoriesEntity that = (CommodityCategoriesEntity) o;
        return comId == that.comId && Objects.equals(comCategory, that.comCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comCategory);
    }
}
