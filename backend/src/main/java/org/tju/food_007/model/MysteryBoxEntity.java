package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "mystery_box", schema = "food_007", catalog = "")
public class MysteryBoxEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mystery_box_ID")
    private int mysteryBoxId;
    @Basic
    @Column(name = "highest_price")
    private BigDecimal highestPrice;
    @Basic
    @Column(name = "lowest_price")
    private BigDecimal lowestPrice;
    @Basic
    @Column(name = "category_count")
    private int categoryCount;
    @Basic
    @Column(name = "item_price")
    private BigDecimal itemPrice;
    @Basic
    @Column(name = "item_introduction")
    private String itemIntroduction;
    @Basic
    @Column(name = "item_image")
    private String itemImage;
    @Basic
    @Column(name = "item_name")
    private String itemName;

    public int getMysteryBoxId() {
        return mysteryBoxId;
    }

    public void setMysteryBoxId(int mysteryBoxId) {
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

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemIntroduction() {
        return itemIntroduction;
    }

    public void setItemIntroduction(String itemIntroduction) {
        this.itemIntroduction = itemIntroduction;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MysteryBoxEntity that = (MysteryBoxEntity) o;
        return mysteryBoxId == that.mysteryBoxId && categoryCount == that.categoryCount && Objects.equals(highestPrice, that.highestPrice) && Objects.equals(lowestPrice, that.lowestPrice) && Objects.equals(itemPrice, that.itemPrice) && Objects.equals(itemIntroduction, that.itemIntroduction) && Objects.equals(itemImage, that.itemImage) && Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mysteryBoxId, highestPrice, lowestPrice, categoryCount, itemPrice, itemIntroduction, itemImage, itemName);
    }
}
