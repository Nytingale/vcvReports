package com.vcv.backend.views;

import com.vcv.backend.entities.Job;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class JobView implements Serializable {
    @Autowired private CompanyRepository companyRepository;

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
    public JobView build(Job job) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        JobView view = new JobView();

        view.cost = job.getJobCost();
        view.type = job.getJobType().toString();
        view.date = LocalDate.ofInstant(job.getJobDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.details = job.getJobDetails();
        view.garage = companyRepository.findById(job.getCompanyId()).get().getCompanyName();
        view.insuranceName = companyRepository.findById(job.getInsuranceId()).get().getCompanyName();
        view.claim = job.getClaimNumber();
        view.vin = job.getVin();

        return view;
    }
    public List<JobView> build(List<Job> jobs) {
        List<JobView> views = new ArrayList<>();

        for(Job job: jobs) {
            JobView view = new JobView().build(job);
            views.add(view);
        }

        return views;
    }
}
