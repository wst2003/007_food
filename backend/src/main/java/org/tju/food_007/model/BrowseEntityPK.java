package org.tju.food_007.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class BrowseEntityPK implements Serializable {
    @Column(name = "bro_time_start")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp broTimeStart;
    @Column(name = "com_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comId;
    @Column(name = "browser_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int browserId;

    public Timestamp getBroTimeStart() {
        return broTimeStart;
    }

    public void setBroTimeStart(Timestamp broTimeStart) {
        this.broTimeStart = broTimeStart;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public int getBrowserId() {
        return browserId;
    }

    public void setBrowserId(int browserId) {
        this.browserId = browserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrowseEntityPK that = (BrowseEntityPK) o;
        return comId == that.comId && browserId == that.browserId && Objects.equals(broTimeStart, that.broTimeStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broTimeStart, comId, browserId);
    }
}
