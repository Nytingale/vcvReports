package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ClaimServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.JobRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {
    @Autowired private JobRepository jobRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Insurance) */
    public List<ClaimView> getInsuranceClaims(String company) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByCompanyNameOrderByClaimDateDesc(company);
        if(!claims.isEmpty()) return new ClaimView().build(claims);
        else throw new ClaimServiceException("Error 100: getInsuranceClaims(company) returned null");
    }

    public MessageView.InsuranceReport addClaim(Claim claim) throws ClaimServiceException {
        // First, Confirm that the VIN in the new Claim Exists
        Vehicle vehicle = vehicleRepository.findByVin(claim.getVin());
        if(vehicle == null) throw new ClaimServiceException("Error 105: addClaim(claim) failed to find a matching VIN that exists");

        try {
            claimRepository.save(claim);
            return new MessageView.InsuranceReport().build(claim, "Successfully Added Claim");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 115: addClaim(claim) failed to add the Claim");
        }
    }

    /* Per Vehicle */
    public List<ClaimView> getClaims(String vin) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByVinOrderByClaimDateDesc(vin);
        if(!claims.isEmpty()) return new ClaimView().build(claims);
        else throw new ClaimServiceException("Error 100: getClaims(vin) returned null");
    }

    /* Per Claim */
    public MessageView.InsuranceReport updateClaim(Claim claim) throws ClaimServiceException {
        // First, find the Claim to be Updated in the Database
        Claim claimDB = claimRepository.findByCompanyNameAndClaimNumber(claim.getCompanyName(), claim.getClaimNumber());
        if(claimDB == null) throw new ClaimServiceException("Error 105: updateClaim(claim) failed to find a matching Claim that exists");

        // Second, Update all Fields of the Database's Claim from the Inputted Claim, except for the PK and FK
        claimDB.setClaimDate(claim.getClaimDate());
        claimDB.setClaimType(claim.getClaimType());
        claimDB.setClaimDetails(claim.getClaimDetails());

        try {
            // Third, Save the Updates
            claimRepository.save(claimDB);
            return new MessageView.InsuranceReport().build(claim, "Successfully Updated Claim");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 115: updateClaim(claim) failed to update the Claim");
        }
    }

    public MessageView.InsuranceReport linkJobToClaim(Long id,
                                                      String number,
                                                      String company) throws ClaimServiceException {
        // First, Confirm that the Job Exists
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()) throw new ClaimServiceException("Error 105: linkJobToClaim(id, number, company) failed to find a matching Job that exists");

        // Second, Confirm that the Claim Exists
        Claim claim = claimRepository.findByCompanyNameAndClaimNumber(company, number);
        if(claim == null) throw new ClaimServiceException("Error 110: linkJobToClaim(id, number, company) failed to find a matching Claim that exists");

        // Third, Link the Job to the Claim and the Claim to the Job in their respective FKs
        job.get().setClaimNumber(claim.getClaimNumber());
        claim.setJobId(job.get().getJobId());

        try {
            // Fourth, Save the Changes to Both the Claim and the Job
            jobRepository.save(job.get());
            claimRepository.save(claim);
            return new MessageView.InsuranceReport().build(claim, "Successfully Linked Job to Claim");
        } catch(Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 115: linkJobToClaim(id, number, company) failed to save the Updates to the Job and Claim");
        }
    }
}
