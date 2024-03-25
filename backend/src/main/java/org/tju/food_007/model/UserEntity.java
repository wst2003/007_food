package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "food_007", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_ID")
    private int userId;
    @Basic
    @Column(name = "user_phone")
    private String userPhone;
    @Basic
    @Column(name = "user_password")
    private String userPassword;
    @Basic
    @Column(name = "user_address")
    private String userAddress;
    @Basic
    @Column(name = "user_state")
    private int userState;
    @Basic
    @Column(name = "user_balance")
    private BigDecimal userBalance;
    @Basic
    @Column(name = "user_regTime")
    private Timestamp userRegTime;
    @Basic
    @Column(name = "user_type")
    private int userType;
    @Basic
    @Column(name = "user_gender")
    private int userGender;

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public Timestamp getUserRegTime() {
        return userRegTime;
    }

    public void setUserRegTime(Timestamp userRegTime) {
        this.userRegTime = userRegTime;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId && userState == that.userState && userType == that.userType && userGender == that.userGender && Objects.equals(userPhone, that.userPhone) && Objects.equals(userPassword, that.userPassword) && Objects.equals(userAddress, that.userAddress) && Objects.equals(userBalance, that.userBalance) && Objects.equals(userRegTime, that.userRegTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userPhone, userPassword, userAddress, userState, userBalance, userRegTime, userType, userGender);
    }
}
