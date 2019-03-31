package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired private JobRepository jobRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private CompanyRepository companyRepository;

    /* Portal (Mechanics/Garages) */
    public List<JobView> getMechanicJobs(String garage) throws JobServiceException {
        Company company = companyRepository.findByCompanyName(garage);
        List<Job> jobs = jobRepository.findByCompanyIdOrderByJobIdDesc(company.getId());
        if(jobs.isEmpty()) return new JobView().build(jobs);
        else throw new JobServiceException("Error 200: getMechanicJobs(garage) returned null");
    }

    public MessageView.JobReport addJob(Job job) throws JobServiceException {
        try {
            jobRepository.save(job);
            return new MessageView.JobReport().build(job, "Successfully Saved the Mechanic Job");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }

    public MessageView.JobReport updateJob(Job job) throws JobServiceException {
        // First, Confirm that the Job Exists
        if(jobRepository.findById(job.getJobId()).isEmpty()) {
            throw new JobServiceException("Error 205: updateJob(job) failed to find a matching Job that exists");
        }

        // Second, Update all Fields of the Database's Job from the Inputted Job, except for the PK and FK
        job.setJobCost(job.getJobCost());
        job.setJobDate(job.getJobDate());
        job.setJobType(job.getJobType());
        job.setJobDetails(job.getJobDetails());

        // Third, Ensure that changes to the FKs occur on Existing PKs in other Tables
        Claim claim = claimRepository.findByCompanyIdAndClaimNumber(job.getInsuranceId(), job.getClaimNumber());
        if(claim == null) throw new JobServiceException("Error 210: updateJob(job) failed to find a matching Claim with that Number/Insurance");
        job.setInsuranceId(claim.getCompanyId());
        job.setClaimNumber(claim.getClaimNumber());

        try {
            // Fourth, Save the Update
            jobRepository.save(job);
            return new MessageView.JobReport().build(job, "Successfully Saved the Mechanic Job");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }

    /* Per Vehicle */
    public List<Job> getJobs(String vin) throws JobServiceException {
        List<Job> jobs = jobRepository.findByVinOrderByJobDateDesc(vin);
        if(jobs.isEmpty()) return jobs;
        else throw new JobServiceException("Error 200: getJobs(vin) returned null");
    }

}
