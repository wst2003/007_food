package org.tju.food_007.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "browse", schema = "food_007", catalog = "")
@IdClass(BrowseEntityPK.class)
public class BrowseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bro_time_start")
    private Timestamp broTimeStart;
    @Basic
    @Column(name = "bro_time_end")
    private Timestamp broTimeEnd;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "com_ID")
    private int comId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "browser_ID")
    private int browserId;

    public Timestamp getBroTimeStart() {
        return broTimeStart;
    }

    public void setBroTimeStart(Timestamp broTimeStart) {
        this.broTimeStart = broTimeStart;
    }

    public Timestamp getBroTimeEnd() {
        return broTimeEnd;
    }

    public void setBroTimeEnd(Timestamp broTimeEnd) {
        this.broTimeEnd = broTimeEnd;
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
        BrowseEntity that = (BrowseEntity) o;
        return comId == that.comId && browserId == that.browserId && Objects.equals(broTimeStart, that.broTimeStart) && Objects.equals(broTimeEnd, that.broTimeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broTimeStart, broTimeEnd, comId, browserId);
    }
}
