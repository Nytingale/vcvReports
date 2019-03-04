package com.vcv.backend.services;

import com.vcv.backend.entities.Job;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.views.JobView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<JobView> getJobs(String vin) throws JobServiceException {
        List<Job> jobs = jobRepository.findByVin(vin);
        if(jobs.size() > 0) return new JobView().build(jobs);
        else throw new JobServiceException("Error 200: getJobs(vin) returned null");
    }

    public Boolean addJob(Job job,
                          String company) throws JobServiceException {
        try {
            job.setCompanyName(company);
            jobRepository.save(job);
            return true;
        } catch (Exception e) {
            throw new JobServiceException("Error 200: addJob(job, company) failed to add the Job");
        }
    }
}
