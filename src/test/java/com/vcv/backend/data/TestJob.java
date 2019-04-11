package com.vcv.backend.data;

import com.vcv.backend.entities.Job;
import com.vcv.backend.enums.JobType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestJob {
    private String id;
    private Job newJob;
    private Job updatedJob;
    private List<Job> jobs;

    public TestJob() {
        this.id = "";
        this.jobs = new ArrayList<>();
        this.newJob = new Job.Builder()
                .setId(5L)
                .setJobType(JobType.Accident)
                .setJobDate(Timestamp.valueOf(LocalDateTime.now()))
                .setJobCost(500)
                .setJobDetails("Broken windshield")
                .setVin("4T1BE46K87U521931")
                .setCompanyId(2L)
                .build();

        this.updatedJob = new Job.Builder()
                .setId(5L)
                .setJobType(JobType.Accident)
                .setJobDate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)))
                .setJobCost(700)
                .setJobDetails("Broken windshield and replaced a rim")
                .setVin("4T1BE46K87U521931")
                .setCompanyId(2L)
                .build();
    }

    public String getId() {
        return id;
    }
    public Job getNewJob() {
        return newJob;
    }
    public Job getUpdatedJob() {
        return updatedJob;
    }
    public List<Job> getJobs() {
        return jobs;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
