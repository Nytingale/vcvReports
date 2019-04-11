package com.vcv.backend.data;

import com.vcv.backend.entities.Company;
import com.vcv.backend.enums.CompanyType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class TestCompany {
    private Company vcv;
    private Company garage;
    private Company insurance;
    private Company company;
    private Company newCompany;
    private Company renewedCompany;
    private Company cancelledComapny;
    private Company blacklistedCompany;
    private List<Company> garages;
    private List<Company> companies;
    private String garageString;
    private String companyString;
    private String insuranceString;

    public TestCompany() {
        this.vcv = null;
        this.garage = null;
        this.company = null;
        this.newCompany = new Company.Builder()
                .setId(7L)
                .setCompanyName("TestNameCompany")
                .setCompanyType(CompanyType.Dealership)
                .setSubscriptionStartDate(Timestamp.valueOf(LocalDateTime.now()))
                .setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)))
                .setWebsite("http://stuff.com")
                .setAdmin("TestEmail1@email.com")
                .setBlacklisted(false)
                .setValid(true)
                .build();
        this.renewedCompany = null;
        this.cancelledComapny = null;
        this.blacklistedCompany = null;
        this.garages = new ArrayList<>();
        this.companies = new ArrayList<>();
        this.insurance = null;
        this.garageString = "";
        this.companyString = "";
        this.insuranceString = "";
    }

    public Company getVcv() {
        return vcv;
    }
    public Company getGarage() {
        return garage;
    }
    public Company getCompany() {
        return company;
    }
    public Company getNewCompany() {
        return newCompany;
    }
    public Company getRenewedCompany() {
        return renewedCompany;
    }
    public Company getCancelledCompany() {
        return cancelledComapny;
    }
    public Company getBlacklistedCompany() {
        return this.blacklistedCompany;
    }
    public Company getInsurance() {
        return insurance;
    }
    public List<Company> getGarages() {
        return garages;
    }
    public List<Company> getCompanies() {
        return companies;
    }
    public String getGarageString() {
        return garageString;
    }
    public String getCompanyString() {
        return companyString;
    }
    public String getInsuranceString() {
        return insuranceString;
    }

    public void setVcv(Company vcv) {
        this.vcv = vcv;
    }
    public void setGarage(Company garage) {
        this.garage = garage;
    }
    public void setCompany(Company company) {
        this.company = company;

        this.blacklistedCompany = new Company.Builder().build(company);
        this.blacklistedCompany.setBlacklisted(true);

        this.cancelledComapny = new Company.Builder().build(company);;
        this.cancelledComapny.setSubscriptionEndDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        this.cancelledComapny.setValid(false);

        this.renewedCompany = new Company.Builder().build(company);;
        this.renewedCompany.setSubscriptionStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        this.renewedCompany.setSubscriptionEndDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().plusYears(1)));
        this.renewedCompany.setValid(true);
    }
    public void setInsurance(Company insurance) {
        this.insurance = insurance;
    }
    public void setGarages(List<Company> garages) {
        this.garages = garages;
    }
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
    public void setGarageString(String garageString) {
        this.garageString = garageString;
    }
    public void setCompanyString(String companyString) {
        this.companyString = companyString;
    }
    public void setInsuranceString(String insuranceString) {
        this.insuranceString = insuranceString;
    }
}
