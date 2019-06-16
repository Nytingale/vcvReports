package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Company;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.VehicleServiceException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleView implements Serializable {
    protected String vin;
    protected Integer year;
    protected String make;
    protected String model;
    protected String colour;
    protected Integer value;
    protected String dealership;
    protected String evaluationDate;
    protected String registrationDate;
    protected Timestamp evaluationTimestamp;
    protected Timestamp registrationTimestamp;
    protected String manufacturer;
    protected String transmission;
    protected String fuelType;
    protected String steering;
    protected Integer mileage;
    protected String engine;
    protected String drive;
    protected String body;
    protected Integer numDoors;
    protected Integer numSeats;
    protected Boolean writtenOff;
    protected Boolean stolen;
    protected Integer numAccidents;
    protected Integer numRobberies;
    protected Integer numSalvages;
    protected Integer numServices;
    protected Integer numOwners;
    protected Boolean decoded;
    protected String insurance;
    protected Long insuranceId;
    protected String policyNumber;

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
    public Timestamp getEvaluationTimestamp() {
        return evaluationTimestamp;
    }
    public Timestamp getRegistrationTimestamp() {
        return registrationTimestamp;
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
    public Boolean getDecoded() {
        return decoded;
    }
    public String getInsurance() {
        return insurance;
    }
    public Long getInsuranceId() {
        return insuranceId;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }

    public VehicleView setVin(String vin) {
        this.vin = vin;
        return this;
    }
    public VehicleView setYear(Integer year) {
        this.year = year;
        return this;
    }
    public VehicleView setMake(String make) {
        this.make = make;
        return this;
    }
    public VehicleView setModel(String model) {
        this.model = model;
        return this;
    }
    public VehicleView setColour(String colour) {
        this.colour = colour;
        return this;
    }
    public VehicleView setValue(Integer value) {
        this.value = value;
        return this;
    }
    public VehicleView setDealership(String dealership) {
        this.dealership = dealership;
        return this;
    }
    public VehicleView setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
        return this;
    }
    public VehicleView setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }
    public VehicleView setEvaluationTimestamp(Timestamp evaluationTimestamp) {
        this.evaluationTimestamp = evaluationTimestamp;
        return this;
    }
    public VehicleView setRegistrationTimestamp(Timestamp registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
        return this;
    }
    public VehicleView setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }
    public VehicleView setTransmission(String transmission) {
        this.transmission = transmission;
        return this;
    }
    public VehicleView setFuelType(String fuelType) {
        this.fuelType = fuelType;
        return this;
    }
    public VehicleView setSteering(String steering) {
        this.steering = steering;
        return this;
    }
    public VehicleView setMileage(Integer mileage) {
        this.mileage = mileage;
        return this;
    }
    public VehicleView setEngine(String engine) {
        this.engine = engine;
        return this;
    }
    public VehicleView setDrive(String drive) {
        this.drive = drive;
        return this;
    }
    public VehicleView setBody(String body) {
        this.body = body;
        return this;
    }
    public VehicleView setNumDoors(Integer numDoors) {
        this.numDoors = numDoors;
        return this;
    }
    public VehicleView setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
        return this;
    }
    public VehicleView setWrittenOff(Boolean writtenOff) {
        this.writtenOff = writtenOff;
        return this;
    }
    public VehicleView setStolen(Boolean stolen) {
        this.stolen = stolen;
        return this;
    }
    public VehicleView setNumAccidents(Integer numAccidents) {
        this.numAccidents = numAccidents;
        return this;
    }
    public VehicleView setNumRobberies(Integer numRobberies) {
        this.numRobberies = numRobberies;
        return this;
    }
    public VehicleView setNumSalvages(Integer numSalvages) {
        this.numSalvages = numSalvages;
        return this;
    }
    public VehicleView setNumServices(Integer numServices) {
        this.numServices = numServices;
        return this;
    }
    public VehicleView setNumOwners(Integer numOwners) {
        this.numOwners = numOwners;
        return this;
    }
    public VehicleView setDecoded(Boolean decoded) {
        this.decoded = decoded;
        return this;
    }
    public VehicleView setInsurance(String insurance) {
        this.insurance = insurance;
        return this;
    }
    public VehicleView setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
        return this;
    }
    public VehicleView setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public VehicleView() {}
    public VehicleView build(Vehicle vehicle, Company insuranceCompany) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.vin = vehicle.getVin();
        this.year = vehicle.getYear();
        this.make = vehicle.getMake();
        this.model = vehicle.getModel();
        this.dealership = vehicle.getDealership();
        this.value = vehicle.getValue();
        this.evaluationDate = LocalDate.ofInstant(vehicle.getEvaluationDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.registrationDate = LocalDate.ofInstant(vehicle.getRegistrationDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.evaluationTimestamp = vehicle.getEvaluationDate();
        this.registrationTimestamp = vehicle.getRegistrationDate();
        this.manufacturer = vehicle.getManufacturer();
        this.transmission = vehicle.getTransmission();
        this.fuelType = vehicle.getFuelType();
        this.steering = vehicle.getSteering();
        this.mileage = vehicle.getMileage();
        this.engine = vehicle.getEngine();
        this.colour = vehicle.getColour();
        this.drive = vehicle.getDrive();
        this.body = vehicle.getBody();
        this.numDoors = vehicle.getNumDoors();
        this.numSeats = vehicle.getNumSeats();
        this.writtenOff = vehicle.isWrittenOff();
        this.stolen = vehicle.isStolen();
        this.numAccidents = vehicle.getNumAccidents();
        this.numRobberies = vehicle.getNumRobberies();
        this.numSalvages = vehicle.getNumSalvages();
        this.numServices = vehicle.getNumServices();
        this.numOwners = vehicle.getNumOwners();
        this.decoded = vehicle.isDecoded();
        this.insurance = insuranceCompany != null ? insuranceCompany.getCompanyName(): null;
        this.insuranceId = vehicle.getInsuranceId();
        this.policyNumber = vehicle.getPolicyNumber();

        return this;
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
                evaluationTimestamp.equals(that.evaluationTimestamp) &&
                registrationTimestamp.equals(that.registrationTimestamp) &&
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
        return Objects.hash(vin, year, make, model, colour, value, dealership, evaluationDate, registrationDate, evaluationTimestamp, registrationTimestamp, manufacturer, transmission, fuelType, steering, mileage, engine, drive, body, numDoors, numSeats, writtenOff, stolen, numAccidents, numRobberies, numSalvages, numServices, numOwners, insurance, insuranceId, policyNumber);
    }

    public static class BasicReport extends VehicleView {
        public BasicReport() {}
        public BasicReport build(Vehicle vehicle) {
            VehicleView vehicleView = new VehicleView().build(vehicle, null);

            this.vin = vehicleView.vin;
            this.year = vehicleView.year;
            this.make = vehicleView.make;
            this.model = vehicleView.model;
            this.colour = vehicleView.colour;
            this.manufacturer = vehicleView.manufacturer;

            return this;
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
            this.vehicle = new VehicleView().build(vehicle, insuranceCompany);
            this.claims = new ClaimView().build(claims, insuranceCompany);
            this.jobs = new JobView().build(jobs, garageCompanies, insuranceCompany);

            return this;
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
