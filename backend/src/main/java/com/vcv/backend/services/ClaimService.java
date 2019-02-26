package com.vcv.backend.services;

import com.vcv.backend.repositories.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
}
