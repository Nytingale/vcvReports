package com.vcv.backend.repositories;

import com.vcv.backend.entities.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Company findByName(String name);
}
