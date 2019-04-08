package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class VehicleView implements Serializable {
    @Autowired private CompanyRepository companyRepository;

    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String colour;
    private Integer value;
    private String dealership;
    private String evaluationDate;
    private String registrationDate;
    private String manufacturer;
    private String transmission;
    private String fuelType;
    private String steering;
    private Integer mileage;
    private String engine;
    private String drive;
    private String body;
    private Integer numDoors;
    private Integer numSeats;
    private Boolean writtenOff;
    private Boolean stolen;
    private Integer numAccidents;
    private Integer numRobberies;
    private Integer numSalvages;
    private Integer numServices;
    private Integer numOwners;
    private String insurance;
    private String policyNumber;

    public String getVin() {
        return vin;
    }
    public Integer getYear() {
        return year;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getColour() {
        return colour;
    }
    public Integer getValue() {
        return value;
    }
    public String getDealership() {
        return dealership;
    }
    public String getEvaluationDate() {
        return evaluationDate;
    }
    public String getRegistrationDate() {
        return registrationDate;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getTransmission() {
        return transmission;
    }
    public String getFuelType() {
        return fuelType;
    }
    public String getSteering() {
        return steering;
    }
    public Integer getMileage() {
        return mileage;
    }
    public String getEngine() {
        return engine;
    }
    public String getDrive() {
        return drive;
    }
    public String getBody() {
        return body;
    }
    public Integer getNumDoors() {
        return numDoors;
    }
    public Integer getNumSeats() {
        return numSeats;
    }
    public Boolean getWrittenOff() {
        return writtenOff;
    }
    public Boolean getStolen() {
        return stolen;
    }
    public Integer getNumAccidents() {
        return numAccidents;
    }
    public Integer getNumRobberies() {
        return numRobberies;
    }
    public Integer getNumSalvages() {
        return numSalvages;
    }
    public Integer getNumServices() {
        return numServices;
    }
    public Integer getNumOwners() {
        return numOwners;
    }
    public String getInsurance() {
        return insurance;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }

    public VehicleView() {}
    public VehicleView build(Vehicle vehicle) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        VehicleView view = new VehicleView();

        view.vin = vehicle.getVin();
        view.year = vehicle.getYear();
        view.make = vehicle.getMake();
        view.model = vehicle.getModel();
        view.dealership = vehicle.getDealership();
        view.value = vehicle.getValue();
        view.evaluationDate = LocalDate.ofInstant(vehicle.getEvaluationDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.registrationDate = LocalDate.ofInstant(vehicle.getRegistrationDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        view.manufacturer = vehicle.getManufacturer();
        view.transmission = vehicle.getTransmission();
        view.fuelType = vehicle.getFuelType();
        view.steering = vehicle.getSteering();
        view.mileage = vehicle.getMileage();
        view.engine = vehicle.getEngine();
        view.colour = vehicle.getColour();
        view.drive = vehicle.getDrive();
        view.body = vehicle.getBody();
        view.numDoors = vehicle.getNumDoors();
        view.numSeats = vehicle.getNumSeats();
        view.writtenOff = vehicle.isWrittenOff();
        view.stolen = vehicle.isStolen();
        view.numAccidents = vehicle.getNumAccidents();
        view.numRobberies = vehicle.getNumRobberies();
        view.numSalvages = vehicle.getNumSalvages();
        view.numServices = vehicle.getNumServices();
        view.numOwners = vehicle.getNumOwners();
        view.insurance = companyRepository.findById(vehicle.getInsuranceId()).get().getCompanyName();
        view.policyNumber = vehicle.getPolicyNumber();

        return view;
    }
    public List<VehicleView> build(List<Vehicle> vehicles) {
        List<VehicleView> views = new ArrayList<>();

        for(Vehicle vehicle: vehicles) {
            VehicleView view = new VehicleView().build(vehicle);
            views.add(view);
        }

        return views;
    }

    public static class BasicReport implements Serializable {
        private String vin;
        private Integer year;
        private String make;
        private String model;
        private String colour;
        private String manufacturer;

        public String getVin() {
            return vin;
        }
        public Integer getYear() {
            return year;
        }
        public String getMake() {
            return make;
        }
        public String getModel() {
            return model;
        }
        public String getColour() {
            return colour;
        }
        public String getManufacturer() {
            return manufacturer;
        }

        public BasicReport() {}
        public BasicReport build(Vehicle vehicle) {
            BasicReport view = new BasicReport();
            VehicleView vehicleView = new VehicleView().build(vehicle);

            view.vin = vehicleView.vin;
            view.year = vehicleView.year;
            view.make = vehicleView.make;
            view.model = vehicleView.model;
            view.colour = vehicleView.colour;
            view.manufacturer = vehicleView.manufacturer;

            return view;
        }
    }
    public static class FullReport implements Serializable {
        private VehicleView vehicle;
        private List<ClaimView> claims;
        private List<JobView> jobs;

        public VehicleView getVehicle() {
            return vehicle;
        }
        public List<ClaimView> getClaims() {
            return claims;
        }
        public List<JobView> getJobs() {
            return jobs;
        }

        public FullReport() {}
        public FullReport build(Vehicle vehicle, List<Claim> claims, List<Job> jobs) {
            FullReport view = new FullReport();

            view.vehicle = new VehicleView().build(vehicle);
            view.claims = new ClaimView().build(claims);
            view.jobs = new JobView().build(jobs);

            return view;
        }
    }
}
