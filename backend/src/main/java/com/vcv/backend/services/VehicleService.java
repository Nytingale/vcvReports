package com.vcv.backend.services;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.configs.FileStorageConfig;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.VehicleRepository;

import com.vcv.backend.views.VehicleView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

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

    public Boolean register(Vehicle vehicle,
                            String dealership) throws VehicleServiceException {
        try {
            vehicle.setDealership(dealership);
            vehicleRepository.save(vehicle);
            return true;
        } catch (Exception e) {
            throw new VehicleServiceException("Error 500: register(vehicle, dealership) failed to register the Vehicle");
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
