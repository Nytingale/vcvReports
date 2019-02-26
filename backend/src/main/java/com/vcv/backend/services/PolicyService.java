package com.vcv.backend.services;

import com.vcv.backend.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;
}
