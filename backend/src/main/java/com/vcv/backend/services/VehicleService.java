package com.vcv.backend.services;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.configs.FileStorageConfig;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.VehicleRepository;

import com.vcv.backend.views.VehicleView;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleView getVehicle(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) return new VehicleView().build(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(vin) returned null");
    }

    public VehicleView getVehicle(Integer year,
                                  String make,
                                  String model) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByYearMakeModel(year, make, model);
        if(vehicle != null) return new VehicleView().build(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(year, make, model) returned null");
    }

    public List<VehicleView> getCompanyVehicles(String dealership) throws VehicleServiceException {
        List<Vehicle> vehicles = vehicleRepository.findByDealershipOrderByRegistrationDateDesc(dealership);
        if(vehicles.size() > 0) return new VehicleView().build(vehicles);
        else throw new VehicleServiceException("Error 500: getCompanyVehicles(dealership) returned null");
    }

    public List<VehicleView> getInsuredVehicles(String insurance) throws VehicleServiceException {
        List<Vehicle> policies = vehicleRepository.findByInsuranceNameOrderByRegistrationDateDesc(insurance);
        if(policies.size() > 0) return new VehicleView().build(policies);
        else throw new VehicleServiceException("Error 500: getInsuredVehicles(insurance) returned null");
    }

    public Boolean register(Vehicle vehicle) throws VehicleServiceException {
        try {
            // First, Ensure that a Vehicle with this VIN Doesn't Exist
            Vehicle vehicleDB = vehicleRepository.findByVin(vehicle.getVin());
            if(vehicleDB != null) throw new VehicleServiceException("Error 505: register(vehicle) found an already existing copy of this Vehicle");

            // Second, Save the New Vehicle
            vehicleRepository.save(vehicle);
            return true;
        } catch (Exception e) {
            throw new VehicleServiceException("Error 500: register(vehicle) failed to register the Vehicle");
        }
    }

    public Boolean reportStolen(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setStolen(true);
            vehicle.setNumRobberies(vehicle.getNumRobberies() + 1);
            vehicleRepository.save(vehicle);
            return true;
        }

        throw new VehicleServiceException("Error 500: register(vehicle, dealership) failed to register the Vehicle");
    }

    public Boolean reportRecovered(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setStolen(false);
            vehicleRepository.save(vehicle);
            return true;
        }

        throw new VehicleServiceException("Error 500: register(vehicle, dealership) failed to register the Vehicle");
    }

    public Boolean reportWrittenOff(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(true);
            vehicle.setNumAccidents(vehicle.getNumAccidents() + 1);
            vehicleRepository.save(vehicle);
            return true;
        }

        throw new VehicleServiceException("Error 500: register(vehicle, dealership) failed to register the Vehicle");
    }

    public Boolean reportSalvaged(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(false);
            vehicle.setNumSalvages(vehicle.getNumSalvages() + 1);
            vehicleRepository.save(vehicle);
            return true;
        }

        throw new VehicleServiceException("Error 500: register(vehicle, dealership) failed to register the Vehicle");
    }
}
