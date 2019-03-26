package com.vcv.backend.views;

import com.vcv.backend.entities.Job;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class JobView {
    @Autowired
    private CompanyRepository companyRepository;

    private Integer cost;
    private String type;
    private String date;
    private String details;
    private String garage;
    private String insurance;
    private String claim;
    private String vin;

    public JobView() {}
    public JobView build(Job job) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        JobView view = new JobView();

        view.cost = job.getJobCost();
        view.type = job.getJobType().toString();
        view.date = dateFormatter.format(job.getJobDate().toInstant());
        view.details = job.getJobDetails();
        view.garage = companyRepository.findById(job.getCompanyId()).get().getCompanyName();
        view.insurance = companyRepository.findById(job.getInsuranceId()).get().getCompanyName();
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
