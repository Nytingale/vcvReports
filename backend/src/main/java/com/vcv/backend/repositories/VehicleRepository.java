package com.vcv.backend.repositories;

import com.vcv.backend.entities.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, String> {
    Vehicle findByVin(String vin);
    Vehicle findByYearMakeModel(Integer year, String make, String model);
}
