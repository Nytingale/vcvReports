package com.vcv.backend.repositories;

import com.vcv.backend.entities.Job;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
    List<Job> findByVin(String vin);
}
