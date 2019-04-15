package com.vcv.backend.repositories;

import com.vcv.backend.entities.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findByVinOrderByIdDesc(String vin);
    List<Job> findByCompanyIdOrderByIdDesc(Long companyId);
}
