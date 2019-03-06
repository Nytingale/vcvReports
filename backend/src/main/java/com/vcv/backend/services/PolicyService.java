package com.vcv.backend.services;

import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.PolicyServiceException;
import com.vcv.backend.repositories.PolicyRepository;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.PolicyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

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

    public List<PolicyView> getCompanyPolicies(String company) throws PolicyServiceException {
        List<Policy> policies = policyRepository.findByCompanyNameOrderByPolicyDateDesc(company);
        if(!policies.isEmpty()) return new PolicyView().build(policies);
        else throw new PolicyServiceException("Error 300: getCompanyPolicies(company) returned null");
    }

    public MessageView.InsuranceReport addPolicy(Policy policy) throws PolicyServiceException {
        // First, Ensure that the Policy Number does not Already Exist with this Insurance Company
        Policy policyDB = policyRepository.findByCompanyNameAndPolicyNumber(policy.getCompanyName(), policy.getPolicyNumber());
        if(policyDB != null) throw new PolicyServiceException("Error 305: addPolicy(policy) found an already existing copy of this Policy");

        // Second, Confirm that the VIN in the new Policy Exists
        Vehicle vehicle = vehicleRepository.findByVin(policy.getVin());
        if(vehicle == null) throw new PolicyServiceException("Error 310: addPolicy(policy) failed to find a matching VIN that exists");

        // Third, Check whether this VIN Exists on Another Policy and if so, Update to the Latter Policy as Valid
        policyDB = policyRepository.findByVin(vehicle.getVin());
        if(policyDB.getVin().equals(vehicle.getVin())) {
            policy.setValid(true);
            policyDB.setValid(false);
            policyRepository.save(policyDB);
        }

        try {
            // Fourth, Save the new Policy and Update the Vehicle Record
            vehicle.setInsuranceName(policy.getCompanyName());
            vehicle.setPolicyNumber(policy.getPolicyNumber());
            vehicle.setNumOwners(vehicle.getNumOwners() + 1);
            vehicleRepository.save(vehicle);
            policyRepository.save(policy);
            return new MessageView.InsuranceReport().build(policy, "Successfully Added Policy");
        } catch (Exception e) {
            throw new PolicyServiceException("Error 315: addPolicy(policy) failed to add the Policy");
        }
    }
}
