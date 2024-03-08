package org.tju.food_007.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "store", schema = "food_007", catalog = "")
public class StoreEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sto_ID")
    private int stoId;
    @Basic
    @Column(name = "sto_name")
    private String stoName;
    @Basic
    @Column(name = "sto_introduction")
    private String stoIntroduction;
    @Basic
    @Column(name = "sto_state")
    private int stoState;
    @Basic
    @Column(name = "sto_openingTime")
    private Time stoOpeningTime;
    @Basic
    @Column(name = "sto_closingTime")
    private Time stoClosingTime;

    public int getStoId() {
        return stoId;
    }

    public void setStoId(int stoId) {
        this.stoId = stoId;
    }

    public String getStoName() {
        return stoName;
    }

    public void setStoName(String stoName) {
        this.stoName = stoName;
    }

    public String getStoIntroduction() {
        return stoIntroduction;
    }

    public void setStoIntroduction(String stoIntroduction) {
        this.stoIntroduction = stoIntroduction;
    }

    public int getStoState() {
        return stoState;
    }

    public void setStoState(int stoState) {
        this.stoState = stoState;
    }

    public Time getStoOpeningTime() {
        return stoOpeningTime;
    }

    public void setStoOpeningTime(Time stoOpeningTime) {
        this.stoOpeningTime = stoOpeningTime;
    }

    public Time getStoClosingTime() {
        return stoClosingTime;
    }

    public void setStoClosingTime(Time stoClosingTime) {
        this.stoClosingTime = stoClosingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreEntity that = (StoreEntity) o;
        return stoId == that.stoId && stoState == that.stoState && Objects.equals(stoName, that.stoName) && Objects.equals(stoIntroduction, that.stoIntroduction) && Objects.equals(stoOpeningTime, that.stoOpeningTime) && Objects.equals(stoClosingTime, that.stoClosingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoName, stoIntroduction, stoState, stoOpeningTime, stoClosingTime);
    }

    @Basic
    @Column(name = "sto_latitude")
    private String stoLatitude;

    public String getStoLatitude() {
        return stoLatitude;
    }

    public void setStoLatitude(String stoLatitude) {
        this.stoLatitude = stoLatitude;
    }

    @Basic
    @Column(name = "sto_longtitude")
    private String stoLongtitude;

    public String getStoLongtitude() {
        return stoLongtitude;
    }

    public void setStoLongtitude(String stoLongtitude) {
        this.stoLongtitude = stoLongtitude;
    }
}
