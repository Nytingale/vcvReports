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

    public VehicleView.BasicReport basicReport(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);

        if(vehicle != null) {
            return new VehicleView.BasicReport()
                    .vin(vehicle.getVin())
                    .year(vehicle.getYear())
                    .make(vehicle.getMake())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .manufacturer(vehicle.getManufacturer())
                    .build();
        }
        else throw new VehicleServiceException("Error 1000: basicReport(vin) returned null");
    }

    public VehicleView.BasicReport basicReport(Integer year,
                                               String make,
                                               String model) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByYearMakeModel(year, make, model);

        if(vehicle != null) {
            return new VehicleView.BasicReport()
                    .vin(vehicle.getVin())
                    .year(vehicle.getYear())
                    .make(vehicle.getMake())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .manufacturer(vehicle.getManufacturer())
                    .build();
        }
        else throw new VehicleServiceException("Error 1100: basicReport(year, make, model) returned null");
    }

    public Vehicle fullReport(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);

        if(vehicle != null) return vehicle;
        else throw new VehicleServiceException("Error 1000: fullReport(vin) returned null");
    }
}
