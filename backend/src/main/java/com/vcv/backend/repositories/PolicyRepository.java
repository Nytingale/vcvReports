package com.vcv.backend.repositories;

import com.vcv.backend.entities.Policy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends PagingAndSortingRepository<Policy, Policy.CompositeKey> {
}
