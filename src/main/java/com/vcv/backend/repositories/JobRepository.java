package com.vcv.backend.repositories;

import com.vcv.backend.entities.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findByVinOrderByIdDesc(String vin);
    List<Job> findByCompanyIdOrderByIdDesc(Long companyId);
}
