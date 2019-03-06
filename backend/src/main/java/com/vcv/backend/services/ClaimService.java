package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ClaimServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.ClaimView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<ClaimView> getClaims(String vin) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByVinOrderByClaimDateDesc(vin);
        if(claims.size() > 0) return new ClaimView().build(claims);
        else throw new ClaimServiceException("Error 100: getClaims(vin) returned null");
    }

    public List<ClaimView> getCompanyClaims(String company) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByCompanyNameOrderByClaimDateDesc(company);
        if(claims.size() > 0) return new ClaimView().build(claims);
        else throw new ClaimServiceException("Error 100: getCompanyClaims(company) returned null");
    }

    public Boolean addClaim(Claim claim) throws ClaimServiceException {
        // First, Confirm that the VIN in the new Claim Exists
        Vehicle vehicle = vehicleRepository.findByVin(claim.getVin());
        if(vehicle == null) throw new ClaimServiceException("Error 105: addClaim(claim) failed to find a matching VIN that exists");

        try {
            claimRepository.save(claim);
            return true;
        } catch (Exception e) {
            throw new ClaimServiceException("Error 115: addClaim(claim) failed to add the Claim");
        }
    }

    public Boolean updateClaim(Claim claim) throws ClaimServiceException {
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
            return true;
        } catch (Exception e) {
            throw new ClaimServiceException("Error 115: updateClaim(claim) failed to update the Claim");
        }
    }
}
