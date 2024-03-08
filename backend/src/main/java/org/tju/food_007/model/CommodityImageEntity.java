package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "commodityImage", schema = "food_007", catalog = "")
@IdClass(CommodityImageEntityPK.class)
public class CommodityImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_image")
    private String comImage;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComImage() {
        return comImage;
    }

    public void setComImage(String comImage) {
        this.comImage = comImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityImageEntity that = (CommodityImageEntity) o;
        return comId == that.comId && Objects.equals(comImage, that.comImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comImage);
    }
}
