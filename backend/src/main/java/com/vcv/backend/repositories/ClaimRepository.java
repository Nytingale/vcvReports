package com.vcv.backend.repositories;

import com.vcv.backend.entities.Claim;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends PagingAndSortingRepository<Claim, Claim.CompositeKey> {
}
