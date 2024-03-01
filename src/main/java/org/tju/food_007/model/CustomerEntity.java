package org.tju.food_007.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer", schema = "food_007", catalog = "")
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cus_ID")
    private int cusId;
    @Basic
    @Column(name = "cus_nickname")
    private String cusNickname;
    @Basic
    @Column(name = "cus_payPassword")
    private String cusPayPassword;
    @Basic
    @Column(name = "cus_state")
    private int cusState;

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getCusNickname() {
        return cusNickname;
    }

    public void setCusNickname(String cusNickname) {
        this.cusNickname = cusNickname;
    }

    public String getCusPayPassword() {
        return cusPayPassword;
    }

    public void setCusPayPassword(String cusPayPassword) {
        this.cusPayPassword = cusPayPassword;
    }

    public int getCusState() {
        return cusState;
    }

    public void setCusState(int cusState) {
        this.cusState = cusState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return cusId == that.cusId && cusState == that.cusState && Objects.equals(cusNickname, that.cusNickname) && Objects.equals(cusPayPassword, that.cusPayPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cusId, cusNickname, cusPayPassword, cusState);
    }
}
