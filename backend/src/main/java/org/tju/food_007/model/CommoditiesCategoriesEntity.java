package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "commodities_categories", schema = "food_007", catalog = "")
public class CommoditiesCategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_category")
    private String comCategory;

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
        CommoditiesCategoriesEntity that = (CommoditiesCategoriesEntity) o;
        return Objects.equals(comCategory, that.comCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comCategory);
    }
}
