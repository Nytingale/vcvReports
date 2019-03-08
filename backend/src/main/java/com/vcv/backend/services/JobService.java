package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ClaimRepository claimRepository;

    public List<JobView> getJobs(String vin) throws JobServiceException {
        List<Job> jobs = jobRepository.findByVinOrderByJobIdDesc(vin);
        if(jobs.size() > 0) return new JobView().build(jobs);
        else throw new JobServiceException("Error 200: getJobs(vin) returned null");
    }

    public List<JobView> getCompanyJobs(String company) throws JobServiceException {
        List<Job> jobs = jobRepository.findByCompanyNameOrderByJobIdDesc(company);
        if(jobs.size() > 0) return new JobView().build(jobs);
        else throw new JobServiceException("Error 200: getCompanyJobs(company) returned null");
    }

    public MessageView.JobReport addJob(Job job) throws JobServiceException {
        try {
            jobRepository.save(job);
            return new MessageView.JobReport().build(job, "Successfully Saved the Mechanic Job");
        } catch (Exception e) {
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }

    public MessageView.JobReport updateJob(Job job) throws JobServiceException {
        // First, Confirm that the Job Exists
        Optional<Job> jobDB = jobRepository.findById(job.getJobId());
        if(jobDB.isEmpty()) throw new JobServiceException("Error 205: updateJob(job) failed to find a matching Job that exists");

        // Second, Update all Fields of the Database's Job from the Inputted Job, except for the PK and FK
        jobDB.get().setJobCost(job.getJobCost());
        jobDB.get().setJobDate(job.getJobDate());
        jobDB.get().setJobType(job.getJobType());
        jobDB.get().setJobDetails(job.getJobDetails());

        // Third, Ensure that changes to the FKs occur on Existing PKs in other Tables
        Claim claim = claimRepository.findByCompanyNameAndClaimNumber(job.getInsuranceName(), job.getClaimNumber());
        if(claim == null) throw new JobServiceException("Error 210: updateJob(job) failed to find a matching Claim with that Number/Insurance");
        jobDB.get().setInsuranceName(claim.getCompanyName());
        jobDB.get().setClaimNumber(claim.getClaimNumber());

        try {
            // Fourth, Save the Update
            jobRepository.save(jobDB.get());
            return new MessageView.JobReport().build(job, "Successfully Saved the Mechanic Job");
        } catch (Exception e) {
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }
}
