package com.vcv.backend.views;

import com.vcv.backend.entities.Company;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class CompanyView {
    private String name;
    private String type;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private Boolean blacklisted;
    private Boolean warning;
    private String website;
    private Boolean valid;

    public CompanyView() {}
    public CompanyView build(Company company) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        CompanyView view = new CompanyView();

        view.name = company.getCompanyName();
        view.type = company.getCompanyType().toString();
        view.subscriptionStartDate = dateFormatter.format(company.getSubscriptionStartDate().toInstant());
        view.subscriptionEndDate = dateFormatter.format(company.getSubscriptionEndDate().toInstant());
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
