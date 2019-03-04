package com.vcv.backend.services;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.exceptions.PolicyServiceException;
import com.vcv.backend.repositories.PolicyRepository;
import com.vcv.backend.views.PolicyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    public PolicyView getPolicy(String vin) throws PolicyServiceException {
        Policy policy = policyRepository.findByVin(vin);
        if(policy != null) return new PolicyView().build(policy);
        else throw new PolicyServiceException("Error 300: getPolicy(vin) returned null");
    }

    public PolicyView getPolicy(String company,
                                String number) throws PolicyServiceException {
        Policy policy = policyRepository.findByCompanyNameAndPolicyNumber(company, number);
        if(policy != null) return new PolicyView().build(policy);
        else throw new PolicyServiceException("Error 300: getPolicy(company, number) returned null");
    }

    public Boolean addPolicy(Policy policy,
                             String insurance) throws PolicyServiceException {
        try {
            policy.setCompanyName(insurance);
            policyRepository.save(policy);
            return true;
        } catch (Exception e) {
            throw new PolicyServiceException("Error 305: addPolicy(policy, insurance) failed to add the Policy");
        }
    }
}
