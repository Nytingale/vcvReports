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
}
