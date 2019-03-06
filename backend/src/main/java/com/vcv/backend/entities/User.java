package com.vcv.backend.entities;

import com.vcv.backend.enums.CompanyType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Users")
@IdClass(User.CompositeKey.class)
public class User {
    @Id private String email;
    @Id private String companyName;

    private String password;
    private CompanyType companyType;
    private Timestamp subscriptionStartDate;
    private Timestamp subscriptionEndDate;
    private Boolean blacklisted;
    private Integer rating;
    private Boolean admin;
    private Boolean valid;

    public String getEmail() {
        return email;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getPassword() {
        return password;
    }
    public CompanyType getCompanyType() {
        return companyType;
    }
    public Timestamp getSubscriptionStartDate() {
        return subscriptionStartDate;
    }
    public Timestamp getSubscriptionEndDate() {
        return subscriptionEndDate;
    }
    public Integer getRating() {
        return rating;
    }
    public Boolean isBlackisted() {
        return blacklisted;
    }
    public Boolean isAdmin() {
        return admin;
    }
    public Boolean isValid() {
        return valid;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }
    public void setSubscriptionStartDate(Timestamp subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }
    public void setSubscriptionEndDate(Timestamp subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }
    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, companyName, password, companyType, subscriptionStartDate, subscriptionEndDate, blacklisted, rating, admin, valid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return blacklisted == user.blacklisted &&
                admin == user.admin &&
                valid == user.valid &&
                rating == user.rating &&
                email.equals(user.email) &&
                companyName.equals(user.companyName) &&
                password.equals(user.password) &&
                companyType == user.companyType &&
                subscriptionStartDate.equals(user.subscriptionStartDate) &&
                subscriptionEndDate.equals(user.subscriptionEndDate);
    }

    public class CompositeKey implements Serializable {
        private String email;
        private String companyName;

        CompositeKey() {

        }
    }
}
