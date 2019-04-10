package com.vcv.backend.repositories;

import com.vcv.backend.entities.Policy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends PagingAndSortingRepository<Policy, Policy.CompositeKey> {
    Policy findByVinAndValid(String vin, Boolean valid);
    Policy findByCompanyIdAndPolicyNumber(Long companyId, String policyNumber);

    List<Policy> findByCompanyIdOrderByPolicyDateDesc(Long companyId);
}
