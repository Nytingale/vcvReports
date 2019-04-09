package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.VehicleServiceException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleView implements Serializable {
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
    public VehicleView build(Vehicle vehicle, Company insuranceCompany) {
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
        view.insurance = insuranceCompany != null ? insuranceCompany.getCompanyName(): null;
        view.policyNumber = vehicle.getPolicyNumber();

        return view;
    }
    public List<VehicleView> build(List<Vehicle> vehicles, Company insuranceCompany) {
        List<VehicleView> views = new ArrayList<>();

        for(Vehicle vehicle: vehicles) {
            VehicleView view = new VehicleView().build(vehicle, insuranceCompany);
            views.add(view);
        }

        return views;
    }
    public List<VehicleView> build(List<Vehicle> vehicles, List<Company> insuranceCompanies) {
        List<VehicleView> views = new ArrayList<>();

        for(int x=0; x<vehicles.size(); x++) {
            Vehicle vehicle = vehicles.get(x);
            Company insuranceCompany = insuranceCompanies.get(x);
            VehicleView view = new VehicleView().build(vehicle, insuranceCompany);
            views.add(view);
        }

        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleView)) return false;
        VehicleView that = (VehicleView) o;
        return vin.equals(that.vin) &&
                year.equals(that.year) &&
                make.equals(that.make) &&
                model.equals(that.model) &&
                Objects.equals(colour, that.colour) &&
                value.equals(that.value) &&
                dealership.equals(that.dealership) &&
                evaluationDate.equals(that.evaluationDate) &&
                registrationDate.equals(that.registrationDate) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(transmission, that.transmission) &&
                Objects.equals(fuelType, that.fuelType) &&
                Objects.equals(steering, that.steering) &&
                Objects.equals(mileage, that.mileage) &&
                Objects.equals(engine, that.engine) &&
                Objects.equals(drive, that.drive) &&
                Objects.equals(body, that.body) &&
                Objects.equals(numDoors, that.numDoors) &&
                Objects.equals(numSeats, that.numSeats) &&
                writtenOff.equals(that.writtenOff) &&
                stolen.equals(that.stolen) &&
                numAccidents.equals(that.numAccidents) &&
                numRobberies.equals(that.numRobberies) &&
                numSalvages.equals(that.numSalvages) &&
                numServices.equals(that.numServices) &&
                numOwners.equals(that.numOwners) &&
                Objects.equals(insurance, that.insurance) &&
                Objects.equals(policyNumber, that.policyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, year, make, model, colour, value, dealership, evaluationDate, registrationDate, manufacturer, transmission, fuelType, steering, mileage, engine, drive, body, numDoors, numSeats, writtenOff, stolen, numAccidents, numRobberies, numSalvages, numServices, numOwners, insurance, policyNumber);
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
            VehicleView vehicleView = new VehicleView().build(vehicle, null);

            view.vin = vehicleView.vin;
            view.year = vehicleView.year;
            view.make = vehicleView.make;
            view.model = vehicleView.model;
            view.colour = vehicleView.colour;
            view.manufacturer = vehicleView.manufacturer;

            return view;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BasicReport)) return false;
            BasicReport that = (BasicReport) o;
            return vin.equals(that.vin) &&
                    year.equals(that.year) &&
                    make.equals(that.make) &&
                    model.equals(that.model) &&
                    Objects.equals(colour, that.colour) &&
                    Objects.equals(manufacturer, that.manufacturer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vin, year, make, model, colour, manufacturer);
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
        public FullReport build(Vehicle vehicle, Company insuranceCompany, List<Job> jobs, List<Company> garageCompanies, List<Claim> claims) throws VehicleServiceException {
            FullReport view = new FullReport();

            view.vehicle = new VehicleView().build(vehicle, insuranceCompany);
            view.claims = new ClaimView().build(claims, insuranceCompany);
            view.jobs = new JobView().build(jobs, garageCompanies, insuranceCompany);

            return view;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FullReport)) return false;
            FullReport that = (FullReport) o;
            return vehicle.equals(that.vehicle) &&
                    claims.equals(that.claims) &&
                    jobs.equals(that.jobs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vehicle, claims, jobs);
        }
    }
}
