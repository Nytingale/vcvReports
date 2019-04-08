package com.vcv.backend.entities;

import com.vcv.backend.enums.CompanyType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    private Long id;
    private String companyName;

    @Enumerated(EnumType.STRING)
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

    public static class Builder {
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


        public Builder setId(Long id) {
            this.id = id;
            return this;
        }
        public Builder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        public Builder setCompanyType(CompanyType companyType) {
            this.companyType = companyType;
            return this;
        }
        public Builder setSubscriptionStartDate(Timestamp subscriptionStartDate) {
            this.subscriptionStartDate = subscriptionStartDate;
            return this;
        }
        public Builder setSubscriptionEndDate(Timestamp subscriptionEndDate) {
            this.subscriptionEndDate = subscriptionEndDate;
            return this;
        }
        public Builder setRating(Integer rating) {
            this.rating = rating;
            return this;
        }
        public Builder setWebsite(String website) {
            this.website = website;
            return this;
        }
        public Builder setAdmin(String admin) {
            this.admin = admin;
            return this;
        }
        public Builder setBlacklisted(Boolean blacklisted) {
            this.blacklisted = blacklisted;
            return this;
        }
        public Builder setValid(Boolean valid) {
            this.valid = valid;
            return this;
        }

        public Company build() {
            Company company = new Company();

            company.setId(this.id);
            company.setCompanyName(this.companyName);
            company.setCompanyType(this.companyType);
            company.setSubscriptionStartDate(this.subscriptionStartDate);
            company.setSubscriptionEndDate(this.subscriptionEndDate);
            company.setRating(this.rating);
            company.setWebsite(this.website);
            company.setAdmin(this.admin);
            company.setBlacklisted(this.blacklisted);
            company.setValid(this.valid);

            return company;
        }
    }
}
