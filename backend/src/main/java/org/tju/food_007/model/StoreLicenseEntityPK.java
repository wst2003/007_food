package org.tju.food_007.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class StoreLicenseEntityPK implements Serializable {
    @Column(name = "sto_ID")
    @Id
    private int stoId;
    @Column(name = "sto_license")
    @Basic
    @Id
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
        StoreLicenseEntityPK that = (StoreLicenseEntityPK) o;
        return stoId == that.stoId && Objects.equals(stoLicense, that.stoLicense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoLicense);
    }
}
