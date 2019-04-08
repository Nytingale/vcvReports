package com.vcv.backend.views;

import com.vcv.backend.entities.Company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

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

    public CompanyView() {}
    public CompanyView build(Company company) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        CompanyView view = new CompanyView();

        view.name = company.getCompanyName();
        view.type = company.getCompanyType().toString();
        view.subscriptionStartDate = LocalDate.ofInstant(company.getSubscriptionStartDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.subscriptionEndDate = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.blacklisted = company.getBlacklisted();
        view.warning = LocalDate.ofInstant(company.getSubscriptionEndDate().toInstant(), ZoneId.systemDefault()).getDayOfYear() - LocalDate.now().getDayOfYear() <= 7;
        view.website = company.getWebsite();
        view.valid = company.getValid();

        return view;
    }
    public List<CompanyView> build(List<Company> companies) {
        List<CompanyView> views = new ArrayList<>();

        for(Company company: companies) {
            CompanyView view = new CompanyView().build(company);
            views.add(view);
        }

        return views;
    }
}
