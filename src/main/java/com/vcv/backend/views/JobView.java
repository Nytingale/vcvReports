package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobView implements Serializable {
    private Integer cost;
    private String type;
    private String date;
    private String garage;
    private String details;
    private String insuranceName;
    private String claim;
    private String vin;

    public Integer getCost() {
        return cost;
    }
    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public String getGarage() {
        return garage;
    }
    public String getDetails() {
        return details;
    }
    public String getInsuranceName() {
        return insuranceName;
    }
    public String getClaim() {
        return claim;
    }
    public String getVin() {
        return vin;
    }

    public JobView setCost(Integer cost) {
        this.cost = cost;
        return this;
    }
    public JobView setType(String type) {
        this.type = type;
        return this;
    }
    public JobView setDate(String date) {
        this.date = date;
        return this;
    }
    public JobView setGarage(String garage) {
        this.garage = garage;
        return this;
    }
    public JobView setDetails(String details) {
        this.details = details;
        return this;
    }
    public JobView setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
        return this;
    }
    public JobView setClaim(String claim) {
        this.claim = claim;
        return this;
    }
    public JobView setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public JobView() {}
    public JobView build(Job job, Company garageCompany, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.cost = job.getJobCost();
        this.type = job.getJobType().toString();
        this.date = LocalDate.ofInstant(job.getJobDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.details = job.getJobDetails();
        this.garage = garageCompany.getCompanyName();
        this.insuranceName = insuranceCompany != null ? insuranceCompany.getCompanyName() : null;
        this.claim = job.getClaimNumber();
        this.vin = job.getVin();

        return this;
    }
    public List<JobView> build(List<Job> jobs, Company garageCompany, Company insuranceCompany) {
        List<JobView> views = new ArrayList<>();

        for(Job job: jobs) {
            JobView view = new JobView().build(job, garageCompany, insuranceCompany);
            views.add(view);
        }

        return views;
    }
    public List<JobView> build(List<Job> jobs, List<Company> garageCompanies, Company insuranceCompany) {
        List<JobView> views = new ArrayList<>();

        for(int x=0; x<jobs.size(); x++) {
            Job job = jobs.get(x);
            Company garageCompany = garageCompanies.get(x);
            JobView view = new JobView().build(job, garageCompany, insuranceCompany);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobView)) return false;
        JobView that = (JobView) o;
        return cost.equals(that.cost) &&
                type.equals(that.type) &&
                date.equals(that.date) &&
                garage.equals(that.garage) &&
                details.equals(that.details) &&
                Objects.equals(insuranceName, that.insuranceName) &&
                claim.equals(that.claim) &&
                vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, type, date, garage, details, insuranceName, claim, vin);
    }
}
