package com.vcv.backend.services;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ClaimServiceException;
import com.vcv.backend.exceptions.DecoderServiceException;
import com.vcv.backend.exceptions.JobServiceException;
import com.vcv.backend.exceptions.VehicleServiceException;
import com.vcv.backend.repositories.CompanyRepository;
import com.vcv.backend.repositories.VehicleRepository;

import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.VehicleView;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired private JobService jobService;
    @Autowired private ClaimService claimService;
    @Autowired private DecoderService decoderService;

    @Autowired private CompanyRepository companyRepository;
    @Autowired private VehicleRepository vehicleRepository;

    /* Portal (Dealerships) */
    public List<VehicleView> getRegisteredVehicles(String dealership) throws VehicleServiceException, DecoderServiceException {
        List<Vehicle> vehicles = vehicleRepository.findByDealershipOrderByRegistrationDateDesc(dealership);
        List<Company> insuranceCompanies = new ArrayList<>();
        if(!vehicles.isEmpty()) {
            for(Vehicle vehicle: vehicles) {
                Optional<Company> company = companyRepository.findById(vehicle.getInsuranceId());
                company.ifPresent(insuranceCompanies::add);
            }

            return new VehicleView().build(decoderService.update(vehicles), insuranceCompanies);
        }
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
        Company insuranceCompany = companyRepository.findByCompanyName(insurance);
        List<Vehicle> vehicles = vehicleRepository.findByInsuranceIdOrderByRegistrationDateDesc(insuranceCompany.getId());
        if(!vehicles.isEmpty()) return new VehicleView().build(decoderService.update(vehicles), insuranceCompany);
        else throw new VehicleServiceException("Error 500: getInsuredVehicles(insurance) returned null");
    }

    public MessageView.WriteOffReport reportWrittenOff(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(true);
            vehicleRepository.save(vehicle);
            return new MessageView.WriteOffReport().build(vehicle, "Successfully Written Off Vehicle");
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
            return new MessageView.StolenReport().build(vehicle, "Successfully Reported Vehicle Recovered");
        }

        throw new VehicleServiceException("Error 500: reportRecovered(vehicle, dealership) returned null");
    }

    public MessageView.SalvageReport reportSalvaged(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setWrittenOff(false);
            vehicle.setNumSalvages(vehicle.getNumSalvages() + 1);
            vehicleRepository.save(vehicle);
            return new MessageView.SalvageReport().build(vehicle, "Successfully Reported Vehicle Salvaged");
        }

        throw new VehicleServiceException("Error 500: reportSalvaged(vehicle, dealership) returned null");
    }

    public MessageView.AccidentReport reportAccident(String vin) throws VehicleServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) {
            vehicle.setNumAccidents(vehicle.getNumAccidents() + 1);
            vehicleRepository.save(vehicle);
            return new MessageView.AccidentReport().build(vehicle, "Successfully Reported Vehicle Accident");
        }

        throw new VehicleServiceException("Error 500: reportAccident(vin) returned null");
    }

    /* General */
    public VehicleView.BasicReport getBasicVehicleReport(String vin) throws VehicleServiceException, DecoderServiceException {
        return new VehicleView.BasicReport().build(getVehicle(vin));
    }

    public VehicleView.BasicReport getBasicVehicleReport(Integer year,
                                                         String make,
                                                         String model) throws VehicleServiceException, DecoderServiceException {
        return new VehicleView.BasicReport().build(getVehicle(year, make, model));
    }

    public VehicleView.FullReport getFullVehicleReport(String vin) throws JobServiceException, ClaimServiceException, VehicleServiceException, DecoderServiceException {
        Vehicle vehicle = getVehicle(vin);

        List<Job> jobs = jobService.getJobs(vin);
        List<Claim> claims = claimService.getClaims(vin);
        List<Company> garageCompanies = new ArrayList<>();

        Company insuranceCompany = companyRepository.findById(vehicle.getInsuranceId()).get();
        if(!jobs.isEmpty()) {
            for(Job job: jobs) {
                companyRepository.findById(job.getCompanyId()).ifPresent(garageCompanies::add);
            }
        }

        return new VehicleView.FullReport().build(vehicle, insuranceCompany, jobs, garageCompanies, claims);
    }

    /* Private */
    private Vehicle getVehicle(String vin) throws VehicleServiceException, DecoderServiceException {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if(vehicle != null) return decoderService.update(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(vin) returned null");
    }

    private Vehicle getVehicle(Integer year,
                               String make,
                               String model) throws VehicleServiceException, DecoderServiceException {
        Vehicle vehicle = vehicleRepository.findByYearAndMakeAndModel(year, make, model);
        if(vehicle != null) return decoderService.update(vehicle);
        else throw new VehicleServiceException("Error 500: getVehicle(year, make, model) returned null");
    }
}
