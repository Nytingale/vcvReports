package com.vcv.backend.repositories;

import com.vcv.backend.entities.Claim;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends PagingAndSortingRepository<Claim, Claim.CompositeKey> {
    List<Claim> findByVin(String vin);
}
