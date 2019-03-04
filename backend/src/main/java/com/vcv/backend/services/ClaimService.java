package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.exceptions.ClaimServiceException;
import com.vcv.backend.repositories.ClaimRepository;
import com.vcv.backend.views.ClaimView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public List<ClaimView> getClaims(String vin) throws ClaimServiceException {
        List<Claim> claims = claimRepository.findByVin(vin);
        if(claims.size() > 0) return new ClaimView().build(claims);
        else throw new ClaimServiceException("Error 100: getClaims(vin) returned null");
    }

    public Boolean addClaim(Claim claim,
                            String insurance) throws ClaimServiceException {
        try {
            claim.setCompanyName(insurance);
            claimRepository.save(claim);
            return true;
        } catch (Exception e) {
            throw new ClaimServiceException("Error 105: addClaim(claim, insurance) failed to add the Claim");
        }
    }

    public Boolean updateClaim(Claim claim) throws ClaimServiceException {
        // First, find the Claim to be Updated in the Database
        Claim claimDB = claimRepository.findByCompanyNameAndClaimNumber(claim.getCompanyName(), claim.getClaimNumber());
        if(claimDB == null) throw new ClaimServiceException("Error 100: updateClaim(claim) has returned null");

        // Second, Update all Fields of the Database's Claim from the Inputted Claim, except for the PK and FK
        claimDB.setClaimDate(claim.getClaimDate());
        claimDB.setClaimType(claim.getClaimType());
        claimDB.setClaimDetails(claim.getClaimDetails());

        // Third, Save the Database's Claim with the Updated Fields
        try {
            claimRepository.save(claimDB);
            return true;
        } catch (Exception e) {
            throw new ClaimServiceException("Error 105: updateClaim(claim) failed to update the Claim");
        }
    }
}
