package com.vcv.backend.views;

import com.vcv.backend.entities.Job;
import com.vcv.backend.enums.JobType;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class JobView {
    private Long jobId;
    private Integer jobCost;
    private JobType jobType;
    private String jobDate;
    private String jobDetails;
    private String companyName;
    private String insuranceName;
    private String claimNumber;
    private String vin;

    public JobView() {}
    public JobView build(Job job) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        JobView view = new JobView();

        view.jobId = job.getJobId();
        view.jobCost = job.getJobCost();
        view.jobType = job.getJobType();
        view.jobDate = dateFormatter.format(job.getJobDate().toInstant());
        view.jobDetails = job.getJobDetails();
        view.companyName = job.getCompanyId();
        view.insuranceName = job.getInsuranceId();
        view.claimNumber = job.getClaimNumber();
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
