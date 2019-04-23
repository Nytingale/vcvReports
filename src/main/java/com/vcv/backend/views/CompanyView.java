package com.vcv.backend.views;

import com.vcv.backend.entities.Company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyView implements Serializable {
    private String name;
    private String type;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private Boolean blacklisted;
    private Boolean warning;
    private String website;
    private Boolean valid;

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }
    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }
    public Boolean getBlacklisted() {
        return blacklisted;
    }
    public Boolean getWarning() {
        return warning;
    }
    public String getWebsite() {
        return website;
    }
    public Boolean getValid() {
        return valid;
    }

    public CompanyView setName(String name) {
        this.name = name;
        return this;
    }
    public CompanyView setType(String type) {
        this.type = type;
        return this;
    }
    public CompanyView setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
        return this;
    }
    public CompanyView setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
        return this;
    }
    public CompanyView setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
        return this;
    }
    public CompanyView setWarning(Boolean warning) {
        this.warning = warning;
        return this;
    }
    public CompanyView setWebsite(String website) {
        this.website = website;
        return this;
    }
    public CompanyView setValid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public CompanyView() {}
    public CompanyView build(Company company) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.name = company.getCompanyName();
        this.type = company.getCompanyType().toString();
        this.subscriptionStartDate = LocalDate.ofInstant(company.getSubscriptionStartDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.subscriptionEndDate = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.blacklisted = company.getBlacklisted();
        this.warning = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).getDayOfYear() - LocalDate.now().getDayOfYear() <= 7;
        this.website = company.getWebsite();
        this.valid = company.getValid();

        return this;
    }
    public List<CompanyView> build(List<Company> companies) {
        List<CompanyView> views = new ArrayList<>();

        for(Company company: companies) {
            CompanyView view = new CompanyView().build(company);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyView)) return false;
        CompanyView that = (CompanyView) o;
        return name.equals(that.name) &&
                type.equals(that.type) &&
                subscriptionStartDate.equals(that.subscriptionStartDate) &&
                subscriptionEndDate.equals(that.subscriptionEndDate) &&
                blacklisted.equals(that.blacklisted) &&
                warning.equals(that.warning) &&
                website.equals(that.website) &&
                valid.equals(that.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, subscriptionStartDate, subscriptionEndDate, blacklisted, warning, website, valid);
    }
}
