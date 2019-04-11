package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired private JobRepository jobRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Mechanics/Garages) */
    public List<JobView> getMechanicJobs(String garage) throws JobServiceException {
        Company garageCompany = companyRepository.findByCompanyName(garage);
        List<Job> jobs = jobRepository.findByCompanyIdOrderByIdDesc(garageCompany.getId());
        if(!jobs.isEmpty()) return new JobView().build(jobs, garageCompany, null);
        else throw new JobServiceException("Error 200: getMechanicJobs(garage) returned null");
    }

    public MessageView.JobReport addJob(Job job) throws JobServiceException {
        // First, Confirm that the VIN exists
        Optional<Vehicle> vehicle = vehicleRepository.findById(job.getVin());
        if(vehicle.isEmpty()) {
            throw new JobServiceException("Error 220: addJob(job) has failed to a matching VIN that exists");
        }

        try {
            // Second, Add the New Job to the Database
            jobRepository.save(job);
            return new MessageView.JobReport().build(job, "Successfully Saved the Mechanic Job");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }

    public MessageView.JobReport updateJob(Job job) throws JobServiceException {
        // First, Confirm that the Job Exists
        if(jobRepository.findById(job.getId()).isEmpty()) {
            throw new JobServiceException("Error 205: updateJob(job) failed to find a matching Job that exists");
        }

        // Second, Confirm that any Claims Attached to the Job, Exists
        if(job.getClaimNumber() != null && !job.getClaimNumber().equals("")) {
            Optional<Claim> claim = claimRepository.findById(new Claim.CompositeKey(job.getClaimNumber(), job.getInsuranceId()));
            if (claim.isEmpty()) {
                throw new JobServiceException("Error 210: updateJob(job) failed to find a matching Claim with that Number/Insurance");
            }
        }

        // Third, Confirm that the Job's VIN exists
        Optional<Vehicle> vehicle = vehicleRepository.findById(job.getVin());
        if(vehicle.isEmpty()) {
            throw new JobServiceException("Error 220: updateJob(job) has failed to a matching VIN that exists");
        }

        try {
            // Fourth, Save the Update
            jobRepository.save(job);
            return new MessageView.JobReport().build(job, "Successfully Updated the Mechanic Job");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobServiceException("Error 215: addJob(job) failed to add the Job");
        }
    }

    /* Per Vehicle */
    public List<Job> getJobs(String vin) throws JobServiceException {
        List<Job> jobs = jobRepository.findByVinOrderByIdDesc(vin);
        if(!jobs.isEmpty()) return jobs;
        else throw new JobServiceException("Error 200: getJobs(vin) returned null");
    }

}
