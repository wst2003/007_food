package org.tju.food_007.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "notice", schema = "food_007", catalog = "")
public class NoticeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ntc_ID")
    private int ntcId;
    @Basic
    @Column(name = "ntc_time")
    private Timestamp ntcTime;
    @Basic
    @Column(name = "ntc_content")
    private String ntcContent;
    @Basic
    @Column(name = "ntc_state")
    private int ntcState;
    @Basic
    @Column(name = "sto_ID")
    private int stoId;

    public int getNtcId() {
        return ntcId;
    }

    public void setNtcId(int ntcId) {
        this.ntcId = ntcId;
    }

    public Timestamp getNtcTime() {
        return ntcTime;
    }

    public void setNtcTime(Timestamp ntcTime) {
        this.ntcTime = ntcTime;
    }

    public String getNtcContent() {
        return ntcContent;
    }

    public void setNtcContent(String ntcContent) {
        this.ntcContent = ntcContent;
    }

    public int getNtcState() {
        return ntcState;
    }

    public void setNtcState(int ntcState) {
        this.ntcState = ntcState;
    }

    public int getStoId() {
        return stoId;
    }

    public void setStoId(int stoId) {
        this.stoId = stoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeEntity that = (NoticeEntity) o;
        return ntcId == that.ntcId && ntcState == that.ntcState && stoId == that.stoId && Objects.equals(ntcTime, that.ntcTime) && Objects.equals(ntcContent, that.ntcContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ntcId, ntcTime, ntcContent, ntcState, stoId);
    }
}
