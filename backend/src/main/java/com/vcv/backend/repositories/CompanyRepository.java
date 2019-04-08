package com.vcv.backend.repositories;

import com.vcv.backend.entities.Company;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Company findByCompanyName(String companyName);
}
