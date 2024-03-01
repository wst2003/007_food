package org.tju.food_007.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "food_007", catalog = "")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cmt_ID")
    private int cmtId;
    @Basic
    @Column(name = "cmt_father")
    private Integer cmtFather;
    @Basic
    @Column(name = "cmt_content")
    private String cmtContent;
    @Basic
    @Column(name = "cmt_time")
    private Timestamp cmtTime;
    @Basic
    @Column(name = "ind_ID")
    private int indId;
    @Basic
    @Column(name = "user_ID")
    private int userId;

    public int getCmtId() {
        return cmtId;
    }

    public void setCmtId(int cmtId) {
        this.cmtId = cmtId;
    }

    public Integer getCmtFather() {
        return cmtFather;
    }

    public void setCmtFather(Integer cmtFather) {
        this.cmtFather = cmtFather;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public Timestamp getCmtTime() {
        return cmtTime;
    }

    public void setCmtTime(Timestamp cmtTime) {
        this.cmtTime = cmtTime;
    }

    public int getIndId() {
        return indId;
    }

    public void setIndId(int indId) {
        this.indId = indId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return cmtId == that.cmtId && indId == that.indId && userId == that.userId && Objects.equals(cmtFather, that.cmtFather) && Objects.equals(cmtContent, that.cmtContent) && Objects.equals(cmtTime, that.cmtTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cmtId, cmtFather, cmtContent, cmtTime, indId, userId);
    }
}
