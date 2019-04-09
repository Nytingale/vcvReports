package com.vcv.backend.views;

import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    public JobView() {}
    public JobView build(Job job, Company garageCompany, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        JobView view = new JobView();

        view.cost = job.getJobCost();
        view.type = job.getJobType().toString();
        view.date = LocalDate.ofInstant(job.getJobDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.details = job.getJobDetails();
        view.garage = garageCompany.getCompanyName();
        view.insuranceName = insuranceCompany != null ? insuranceCompany.getCompanyName() : null;
        view.claim = job.getClaimNumber();
        view.vin = job.getVin();

        return view;
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
        JobView jobView = (JobView) o;
        return cost.equals(jobView.cost) &&
                type.equals(jobView.type) &&
                date.equals(jobView.date) &&
                garage.equals(jobView.garage) &&
                details.equals(jobView.details) &&
                insuranceName.equals(jobView.insuranceName) &&
                claim.equals(jobView.claim) &&
                vin.equals(jobView.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, type, date, garage, details, insuranceName, claim, vin);
    }
}
