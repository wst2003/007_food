package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "storeLicense", schema = "food_007", catalog = "")
@IdClass(StoreLicenseEntityPK.class)
public class StoreLicenseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sto_ID")
    private int stoId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "sto_license")
    private String stoLicense;

    public int getStoId() {
        return stoId;
    }

    public void setStoId(int stoId) {
        this.stoId = stoId;
    }

    public String getStoLicense() {
        return stoLicense;
    }

    public void setStoLicense(String stoLicense) {
        this.stoLicense = stoLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreLicenseEntity that = (StoreLicenseEntity) o;
        return stoId == that.stoId && Objects.equals(stoLicense, that.stoLicense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoLicense);
    }
}
