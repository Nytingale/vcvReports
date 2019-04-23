package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ClaimServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.JobRepository;
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
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Insurance) */
    public List<ClaimView> getInsuranceClaims(String insurance) throws ClaimServiceException {
        Company company = companyRepository.findByCompanyName(insurance);
        List<Claim> claims = claimRepository.findByCompanyIdOrderByClaimDateDesc(company.getId());
        if(!claims.isEmpty()) return new ClaimView().build(claims, company);
        else throw new ClaimServiceException("Error 100: getInsuranceClaims(insurance) returned null");
    }

    public MessageView.InsuranceReport add(Claim claim) throws ClaimServiceException {
        // First, Confirm that the VIN in the new Claim Exists
        Optional<Vehicle> vehicle = vehicleRepository.findById(claim.getVin());
        if(vehicle.isEmpty()) throw new ClaimServiceException("Error 105: add(claim) failed to find a matching VIN that exists");

        try {
            Company company = companyRepository.findById(claim.getCompanyId()).get();

            claimRepository.save(claim);
            return new MessageView.InsuranceReport().build(claim, company, "Successfully Added Claim");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 115: add(claim) failed to add the Claim");
        }
    }

    /* Per Vehicle */
    public List<Claim> getClaims(String vin) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByVinOrderByClaimDateDesc(vin);
        if(!claims.isEmpty()) return claims;
        else throw new ClaimServiceException("Error 100: getClaims(vin) returned null");
    }

    /* Per Claim */
    public MessageView.InsuranceReport update(Claim claim) throws ClaimServiceException {
        // First, find the Claim to be Updated in the Database
        if(claimRepository.findById(new Claim.CompositeKey(claim.getClaimNumber(), claim.getCompanyId())).isEmpty()) {
            throw new ClaimServiceException("Error 105: update(claim) failed to find a matching Claim that exists");
        }

        // Second, Update all Fields of the Database's Claim from the Inputted Claim, except for the PK and FK
        claim.setClaimDate(claim.getClaimDate());
        claim.setClaimType(claim.getClaimType());
        claim.setClaimDetails(claim.getClaimDetails());

        try {
            Company company = companyRepository.findById(claim.getCompanyId()).get();

            // Third, Save the Updates
            claimRepository.save(claim);
            return new MessageView.InsuranceReport().build(claim, company, "Successfully Updated Claim");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 115: update(claim) failed to update the Claim");
        }
    }

    public MessageView.InsuranceReport link(Long id,
                                            String number,
                                            String insurance) throws ClaimServiceException {
        // First, Confirm that the Job Exists
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()) throw new ClaimServiceException("Error 105: link(id, number, insurance) failed to find a matching Job that exists");

        // Second, Find the Insurance ClientCompany
        Company company = companyRepository.findByCompanyName(insurance);
        if(company == null) throw new ClaimServiceException("Error 110: link(id, number, insurance) failed to find a matching ClientCompany by that name");

        // Third, Confirm that the Claim Exists
        Optional<Claim> claim = claimRepository.findById(new Claim.CompositeKey(number, company.getId()));
        if(claim.isEmpty()) throw new ClaimServiceException("Error 115: link(id, number, insurance) failed to find a matching Claim that exists");

        // Fourth, Link the Job to the Claim and the Claim to the Job in their respective FKs
        job.get().setClaimNumber(claim.get().getClaimNumber());
        claim.get().setJobId(job.get().getId());

        try {
            // Fifth, Save the Changes to Both the Claim and the Job
            jobRepository.save(job.get());
            claimRepository.save(claim.get());
            return new MessageView.InsuranceReport().build(claim.get(), company, "Successfully Linked Job to Claim");
        } catch(Exception e) {
            e.printStackTrace();
            throw new ClaimServiceException("Error 120: link(id, number, insurance) failed to save the Updates to the Job and Claim");
        }
    }

    public ClaimView getClaimView(String claimNumber,
                                  String companyName) throws ClaimServiceException {
        Company company = companyRepository.findByCompanyName(companyName);
        Optional<Claim> claim = claimRepository.findById(new Claim.CompositeKey(claimNumber, company.getId()));
        if(claim.isPresent()) { return new ClaimView().build(claim.get(), company); }
        throw new ClaimServiceException("Error 100: getClaimView(claimNumber, companyName) returned null");
    }
}
