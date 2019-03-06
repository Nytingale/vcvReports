package com.vcv.backend.repositories;

import com.vcv.backend.entities.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, String> {
    Vehicle findByVin(String vin);
    Vehicle findByYearMakeModel(Integer year, String make, String model);

    List<Vehicle> findByDealershipOrderByRegistrationDateDesc(String dealership);
    List<Vehicle> findByInsuranceNameOrderByRegistrationDateDesc(String insurance);
}
