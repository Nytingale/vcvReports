package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.enums.JobType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobView implements Serializable {
    private Long id;
    private String type;
    private String date;
    private Integer cost;
    private String garage;
    private String details;
    private JobType jType;
    private Timestamp timestamp;
    private String insurance;
    private Long insuranceId;
    private Long garageId;
    private String claimNumber;
    private String vin;

    public Long getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public Integer getCost() {
        return cost;
    }
    public String getGarage() {
        return garage;
    }
    public String getDetails() {
        return details;
    }
    public JobType getJType() {
        return jType;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getInsurance() {
        return insurance;
    }
    public Long getInsuranceId() {
        return insuranceId;
    }
    public Long getGarageId() {
        return garageId;
    }
    public String getClaimNumber() {
        return claimNumber;
    }
    public String getVin() {
        return vin;
    }

    public JobView setId(Long id) {
        this.id = id;
        return this;
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
    public JobView setJType(JobType jType) {
        this.jType = jType;
        return this;
    }
    public JobView setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public JobView setInsurance(String insurance) {
        this.insurance = insurance;
        return this;
    }
    public JobView setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
        return this;
    }
    public JobView setGarageId(Long garageId) {
        this.garageId = garageId;
        return this;
    }
    public JobView setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
        return this;
    }
    public JobView setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public JobView() {}
    public JobView build(Job job, Company garageCompany, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.id = job.getId();
        this.jType = job.getJobType();
        this.type = job.getJobType().toString();
        this.cost = job.getJobCost();
        this.date = LocalDate.ofInstant(job.getJobDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.details = job.getJobDetails();
        this.timestamp = job.getJobDate();
        this.garage = garageCompany.getCompanyName();
        this.insurance = insuranceCompany != null ? insuranceCompany.getCompanyName() : null;
        this.insuranceId = job.getInsuranceId();
        this.garageId = job.getCompanyId();
        this.claimNumber = job.getClaimNumber();
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
        return id.equals(that.id) &&
                type.equals(that.type) &&
                date.equals(that.date) &&
                cost.equals(that.cost) &&
                garage.equals(that.garage) &&
                details.equals(that.details) &&
                timestamp.equals(that.timestamp) &&
                Objects.equals(insurance, that.insurance) &&
                insuranceId.equals(that.insuranceId) &&
                garageId.equals(that.garageId) &&
                claimNumber.equals(that.claimNumber) &&
                vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, cost, date, garage, details, timestamp, insurance, insuranceId, garageId, claimNumber, vin);
    }
}
