package com.vcv.backend.services;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.DecoderServiceException;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.VehicleRepository;

import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.VehicleView;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class VehicleService {
    @Autowired private DecoderService decoderService;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Dealerships) */
    public List<VehicleView> getRegisteredVehicles(String dealership) throws VehicleServiceException, DecoderServiceException {
        List<Vehicle> vehicles = vehicleRepository.findByDealershipOrderByRegistrationDateDesc(dealership);
        if(!vehicles.isEmpty()) return new VehicleView().build(decoderService.updateVehicles(vehicles));
        else throw new VehicleServiceException("Error 500: getRegisteredVehicles(dealership) returned null");
    }

    public MessageView.Registration register(Vehicle vehicle) throws VehicleServiceException {
        try {
            // First, Ensure that a Vehicle with this VIN Doesn't Exist
            Vehicle vehicleDB = vehicleRepository.findByVin(vehicle.getVin());
            if(vehicleDB != null) throw new VehicleServiceException("Error 505: register(vehicle) found an already existing copy of this Vehicle");

            // Second, Save the New Vehicle
            vehicleRepository.save(vehicle);
            return new MessageView.Registration().build(vehicle, "Successfully Registered Vehicle");
        } catch (Exception e) {
            e.printStackTrace();
            throw new VehicleServiceException("Error 500: register(vehicle) failed to register the Vehicle");
        }
    }

    /* Portal (Insurance) */
    public List<VehicleView> getInsuredVehicles(String insurance) throws VehicleServiceException, DecoderServiceException {
        List<Vehicle> vehicles = vehicleRepository.findByInsuranceNameOrderByRegistrationDateDesc(insurance);
        if(!vehicles.isEmpty()) return new VehicleView().build(decoderService.updateVehicles(vehicles));
        else throw new VehicleServiceException("Error 500: getInsuredVehicles(insurance) returned null");
    }

    public MessageView.WriteOff reportWrittenOff(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(true);
            vehicleRepository.save(vehicle);
            return new MessageView.WriteOff().build(vehicle, "Successfully Written Off Vehicle");
        }

        throw new VehicleServiceException("Error 500: reportWrittenOff(vehicle, dealership) returned null");
    }

    public MessageView.StolenReport reportStolen(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setStolen(true);
            vehicle.setNumRobberies(vehicle.getNumRobberies() + 1);
            vehicleRepository.save(vehicle);
            return new MessageView.StolenReport().build(vehicle, "Successfully Reported Vehicle Stolen");
        }

        throw new VehicleServiceException("Error 500: reportStolen(vehicle, dealership) returned null");
    }

    public MessageView.StolenReport reportRecovered(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setStolen(false);
            vehicleRepository.save(vehicle);
            return new MessageView.StolenReport().build(vehicle, "Successfully Recovered Vehicle");
        }

        throw new VehicleServiceException("Error 500: reportRecovered(vehicle, dealership) returned null");
    }

    public MessageView.SalvageReport reportSalvaged(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(false);
            vehicle.setNumSalvages(vehicle.getNumSalvages() + 1);
            vehicleRepository.save(vehicle);
            return new MessageView.SalvageReport().build(vehicle, "Successfully Salvaged Vehicle");
        }

        throw new VehicleServiceException("Error 500: reportSalvaged(vehicle, dealership) returned null");
    }

    public MessageView.AccidentReport reportAccident(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setNumAccidents(vehicle.getNumAccidents() + 1);
            vehicleRepository.save(vehicle);
            return new MessageView.AccidentReport().build(vehicle, "Successfully Reported Accident on Vehicle");
        }

        throw new VehicleServiceException("Error 500: reportAccident(vin) returned null");
    }

    /* General */
    public Vehicle getVehicle(String vin) throws VehicleServiceException, DecoderServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) return decoderService.updateVehicle(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(vin) returned null");
    }

    public Vehicle getVehicle(Integer year,
                              String make,
                              String model) throws VehicleServiceException, DecoderServiceException {
        Vehicle vehicle = vehicleRepository.findByYearMakeModel(year, make, model);
        if(vehicle != null) return decoderService.updateVehicle(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(year, make, model) returned null");
    }
}
