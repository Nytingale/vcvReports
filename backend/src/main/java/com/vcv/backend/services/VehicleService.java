package com.vcv.backend.services;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.VehicleRepository;
import com.vcv.backend.views.VehicleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle getVehicle(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);

        if(vehicle != null) return vehicle;
        else throw new VehicleServiceException("Error 1000: getVehicle(vin) returned null");
    }

    public Vehicle getVehicle(Integer year,
                                              String make,
                                              String model) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByYearMakeModel(year, make, model);

        if(vehicle != null) return vehicle;
        else throw new VehicleServiceException("Error 1100: getVehicle(year, make, model) returned null");
    }
}
