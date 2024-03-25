package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author WGY
 * @create 2024-03-20-16:42
 */
@Entity
@Table(name = "mystery_box", schema = "food_007", catalog = "")
public class MysteryBoxEntity {
    @Id
    @Column(name = "mystery_box_ID")
    private Integer mysteryBoxId;
    @Basic
    @Column(name = "highest_price")
    private BigDecimal highestPrice;
    @Basic
    @Column(name = "lowest_price")
    private BigDecimal lowestPrice;
    @Basic
    @Column(name = "category_count")
    private Integer categoryCount;
    @Basic
    @Column(name = "item_image")
    private String itemImage;

    public Integer getMysteryBoxId() {
        return mysteryBoxId;
    }

    public void setMysteryBoxId(Integer mysteryBoxId) {
        this.mysteryBoxId = mysteryBoxId;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MysteryBoxEntity that = (MysteryBoxEntity) o;
        return Objects.equals(mysteryBoxId, that.mysteryBoxId) && Objects.equals(highestPrice, that.highestPrice) && Objects.equals(lowestPrice, that.lowestPrice) && Objects.equals(categoryCount, that.categoryCount) && Objects.equals(itemImage, that.itemImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mysteryBoxId, highestPrice, lowestPrice, categoryCount, itemImage);
    }
}
