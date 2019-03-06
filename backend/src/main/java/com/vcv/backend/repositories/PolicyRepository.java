package com.vcv.backend.repositories;

import com.vcv.backend.entities.Policy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends PagingAndSortingRepository<Policy, Policy.CompositeKey> {
    Policy findByVin(String vin);
    Policy findByCompanyNameAndPolicyNumber(String companyName, String policyNumber);

    List<Policy> findByCompanyNameOrderByPolicyDateDesc(String companyName);
}
