package com.vcv.backend.services;

import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.PolicyServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.PolicyRepository;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.PolicyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    @Autowired private PolicyRepository policyRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Insurance) */
    public List<PolicyView> getInsurancePolicies(String insurance) throws PolicyServiceException {
        Company company = companyRepository.findByCompanyName(insurance);
        List<Policy> policies = policyRepository.findByCompanyIdOrderByPolicyDateDesc(company.getId());
        if(!policies.isEmpty()) return new PolicyView().build(policies, company);
        else throw new PolicyServiceException("Error 300: getInsurancePolicies(insurance) returned null");
    }

    public MessageView.InsuranceReport add(Policy policy) throws PolicyServiceException {
        // First, Ensure that the Policy Number does not Already Exist with this Insurance ClientCompany
        Policy policyDB = policyRepository.findByCompanyIdAndPolicyNumber(policy.getCompanyId(), policy.getPolicyNumber());
        if(policyDB != null) throw new PolicyServiceException("Error 305: add(policy) found an already existing copy of this Policy");

        // Second, Confirm that the VIN in the new Policy Exists
        Optional<Vehicle> vehicle = vehicleRepository.findById(policy.getVin());
        if(vehicle.isEmpty()) throw new PolicyServiceException("Error 310: add(policy) failed to find a matching VIN that exists");

        // Third, Check whether this VIN Exists on Another Policy and if so, Update to the Latter Policy as Valid
        policyDB = policyRepository.findByVinAndValid(vehicle.get().getVin(), true);
        if(policyDB != null && policyDB.getVin().equals(vehicle.get().getVin())) {
            policy.setValid(true);
            policyDB.setValid(false);
        }

        try {
            Company company = companyRepository.findById(policy.getCompanyId()).get();

            // Fourth, Save the new Policy and Update the Vehicle Record
            vehicle.get().setInsuranceId(policy.getCompanyId());
            vehicle.get().setPolicyNumber(policy.getPolicyNumber());
            vehicle.get().setNumOwners(vehicle.get().getNumOwners() + 1);
            vehicleRepository.save(vehicle.get());
            policyRepository.save(policy);
            return new MessageView.InsuranceReport().build(policy, company, "Successfully Added Policy");
        } catch (Exception e) {
            e.printStackTrace();
            throw new PolicyServiceException("Error 315: add(policy) failed to add the Policy");
        }
    }

    /* Per Vehicle */
    public PolicyView getPolicy(String vin) throws PolicyServiceException {
        Policy policy = policyRepository.findByVinAndValid(vin, true);
        Optional<Company> company = companyRepository.findById(policy.getCompanyId());
        if(policy != null && company.isPresent()) return new PolicyView().build(policy, company.get());
        else throw new PolicyServiceException("Error 300: getPolicy(vin) returned null");
    }

    public PolicyView getPolicyView(String policyNumber,
                                    String insurance) throws PolicyServiceException {
        Company company = companyRepository.findByCompanyName(insurance);
        Optional<Policy> policy = policyRepository.findById(new Policy.CompositeKey(company.getId(), policyNumber));
        if(policy.isPresent()) return new PolicyView().build(policy.get(), company);
        else throw new PolicyServiceException("Error 300: getPolicyView(policyNumber, insurance) returned null");
    }
}
