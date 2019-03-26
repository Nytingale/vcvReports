package com.vcv.backend.entities;

import com.vcv.backend.enums.CompanyType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    private Long id;
    private String companyName;
    private CompanyType companyType;
    private Timestamp subscriptionStartDate;
    private Timestamp subscriptionEndDate;
    private Integer rating;
    private String website;
    private String admin;
    private Boolean blacklisted;
    private Boolean valid;

    public Long getId() {
        return id;
    }
    public String getCompanyName() {
        return companyName;
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
    public String getWebsite() {
        return website;
    }
    public String getAdmin() {
        return admin;
    }
    public Boolean getBlacklisted() {
        return blacklisted;
    }
    public Boolean getValid() {
        return valid;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyType, subscriptionStartDate, subscriptionEndDate, rating, website, admin, blacklisted, valid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id) &&
                companyType == company.companyType &&
                subscriptionStartDate.equals(company.subscriptionStartDate) &&
                subscriptionEndDate.equals(company.subscriptionEndDate) &&
                rating.equals(company.rating) &&
                Objects.equals(website, company.website) &&
                admin.equals(company.admin) &&
                blacklisted.equals(company.blacklisted) &&
                valid.equals(company.valid);
    }
}
