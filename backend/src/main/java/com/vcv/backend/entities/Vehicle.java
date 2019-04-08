package com.vcv.backend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String manufacturer;
    private String engine;
    private String colour;
    private String dealership;
    private Integer mileage;
    private Integer value;
    private Timestamp evaluationDate;
    private Timestamp registrationDate;
    private String transmission;
    private String fuelType;
    private String steering;
    private String drive;
    private String body;
    private Integer numSeats;
    private Integer numDoors;
    private Boolean writtenOff;
    private Boolean stolen;
    private Integer numAccidents;
    private Integer numRobberies;
    private Integer numSalvages;
    private Integer numServices;
    private Integer numOwners;
    private String policyNumber;
    private Long insuranceId;

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
    public String getManufacturer() {
        return manufacturer;
    }
    public String getEngine() {
        return engine;
    }
    public String getColour() {
        return colour;
    }
    public Integer getMileage() {
        return mileage;
    }
    public String getDealership() {
        return dealership;
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
    public Integer getValue() {
        return value;
    }
    public Boolean isWrittenOff() {
        return writtenOff;
    }
    public Boolean isStolen() {
        return stolen;
    }
    public Timestamp getEvaluationDate() {
        return evaluationDate;
    }
    public Timestamp getRegistrationDate() {
        return registrationDate;
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
    public String getPolicyNumber() {
        return policyNumber;
    }
    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public void setSteering(String steering) {
        this.steering = steering;
    }
    public void setDrive(String drive) {
        this.drive = drive;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }
    public void setNumDoors(Integer numDoors) {
        this.numDoors = numDoors;
    }
    public void setDealership(String dealership) {
        this.dealership = dealership;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public void setWrittenOff(Boolean writtenOff) {
        this.writtenOff = writtenOff;
    }
    public void setStolen(Boolean stolen) {
        this.stolen = stolen;
    }
    public void setEvaluationDate(Timestamp evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    public void setNumAccidents(Integer numAccidents) {
        this.numAccidents = numAccidents;
    }
    public void setNumRobberies(Integer numRobberies) {
        this.numRobberies = numRobberies;
    }
    public void setNumSalvages(Integer numSalvages) {
        this.numSalvages = numSalvages;
    }
    public void setNumServices(Integer numServices) {
        this.numServices = numServices;
    }
    public void setNumOwners(Integer numOwners) {
        this.numOwners = numOwners;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                vin,
                year,
                make,
                model,
                manufacturer,
                engine,
                colour,
                mileage,
                dealership,
                value,
                evaluationDate,
                registrationDate,
                transmission,
                fuelType,
                steering,
                drive,
                body,
                numSeats,
                numDoors,
                writtenOff,
                stolen,
                numAccidents,
                numRobberies,
                numSalvages,
                numServices,
                numOwners,
                insuranceId,
                policyNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return year.equals(vehicle.year) &&
                value.equals(vehicle.value) &&
                writtenOff == vehicle.writtenOff &&
                stolen == vehicle.stolen &&
                numAccidents.equals(vehicle.numAccidents) &&
                numRobberies.equals(vehicle.numRobberies) &&
                numSalvages.equals(vehicle.numSalvages) &&
                numServices.equals(vehicle.numServices) &&
                numOwners.equals(vehicle.numOwners) &&
                vin.equals(vehicle.vin) &&
                make.equals(vehicle.make) &&
                model.equals(vehicle.model) &&
                manufacturer.equals(vehicle.manufacturer) &&
                engine.equals(vehicle.engine) &&
                colour.equals(vehicle.colour) &&
                mileage.equals(vehicle.mileage) &&
                fuelType.equals(vehicle.fuelType) &&
                steering.equals(vehicle.steering) &&
                drive.equals(vehicle.drive) &&
                body.equals(vehicle.body) &&
                numSeats.equals(vehicle.numSeats) &&
                numDoors.equals(vehicle.numDoors) &&
                transmission.equals(vehicle.transmission) &&
                dealership.equals(vehicle.dealership) &&
                evaluationDate.equals(vehicle.evaluationDate) &&
                registrationDate.equals(vehicle.registrationDate) &&
                insuranceId.equals(vehicle.insuranceId) &&
                policyNumber.equals(vehicle.policyNumber);
    }

    public static class Builder {
        private String vin;
        private Integer year;
        private String make;
        private String model;
        private String manufacturer;
        private String engine;
        private String colour;
        private Integer mileage;
        private String dealership;
        private Integer value;
        private Timestamp evaluationDate;
        private Timestamp registrationDate;
        private String transmission;
        private String fuelType;
        private String steering;
        private String drive;
        private String body;
        private Integer seats;
        private Integer doors;
        private Boolean writtenOff;
        private Boolean stolen;
        private Integer numAccidents;
        private Integer numRobberies;
        private Integer numSalvages;
        private Integer numServices;
        private Integer numOwners;
        private String policyNumber;
        private Long insuranceId;

        public Builder setVin(String vin) {
            this.vin = vin;
            return this;
        }
        public Builder setYear(Integer year) {
            this.year = year;
            return this;
        }
        public Builder setMake(String make) {
            this.make = make;
            return this;
        }
        public Builder setModel(String model) {
            this.model = model;
            return this;
        }
        public Builder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }
        public Builder setEngine(String engine) {
            this.engine = engine;
            return this;
        }
        public Builder setColour(String colour) {
            this.colour = colour;
            return this;
        }
        public Builder setMileage(Integer mileage) {
            this.mileage = mileage;
            return this;
        }
        public Builder setDealership(String dealership) {
            this.dealership = dealership;
            return this;
        }
        public Builder setValue(Integer value) {
            this.value = value;
            return this;
        }
        public Builder setEvaluationDate(Timestamp evaluationDate) {
            this.evaluationDate = evaluationDate;
            return this;
        }
        public Builder setRegistrationDate(Timestamp registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }
        public Builder setTransmission(String transmission) {
            this.transmission = transmission;
            return this;
        }
        public Builder setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }
        public Builder setSteering(String steering) {
            this.steering = steering;
            return this;
        }
        public Builder setDrive(String drive) {
            this.drive = drive;
            return this;
        }
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }
        public Builder setSeats(Integer seats) {
            this.seats = seats;
            return this;
        }
        public Builder setDoors(Integer doors) {
            this.doors = doors;
            return this;
        }
        public Builder setWrittenOff(Boolean writtenOff) {
            this.writtenOff = writtenOff;
            return this;
        }
        public Builder setStolen(Boolean stolen) {
            this.stolen = stolen;
            return this;
        }
        public Builder setNumAccidents(Integer numAccidents) {
            this.numAccidents = numAccidents;
            return this;
        }
        public Builder setNumRobberies(Integer numRobberies) {
            this.numRobberies = numRobberies;
            return this;
        }
        public Builder setNumSalvages(Integer numSalvages) {
            this.numSalvages = numSalvages;
            return this;
        }
        public Builder setNumServices(Integer numServices) {
            this.numServices = numServices;
            return this;
        }
        public Builder setNumOwners(Integer numOwners) {
            this.numOwners = numOwners;
            return this;
        }
        public Builder setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
            return this;
        }
        public Builder setInsuranceId(Long insuranceId) {
            this.insuranceId = insuranceId;
            return this;
        }

        public Vehicle build() {
            Vehicle vehicle = new Vehicle();

            vehicle.setVin(this.vin);
            vehicle.setYear(this.year);
            vehicle.setMake(this.make);
            vehicle.setModel(this.model);
            vehicle.setManufacturer(this.manufacturer);
            vehicle.setEngine(this.engine);
            vehicle.setColour(this.colour);
            vehicle.setMileage(this.mileage);
            vehicle.setDealership(this.dealership);
            vehicle.setValue(this.value);
            vehicle.setEvaluationDate(this.evaluationDate);
            vehicle.setRegistrationDate(this.registrationDate);
            vehicle.setTransmission(this.transmission);
            vehicle.setFuelType(this.fuelType);
            vehicle.setSteering(this.steering);
            vehicle.setDrive(this.drive);
            vehicle.setBody(this.body);
            vehicle.setNumSeats(this.seats);
            vehicle.setNumDoors(this.doors);
            vehicle.setWrittenOff(this.writtenOff);
            vehicle.setStolen(this.stolen);
            vehicle.setNumAccidents(this.numAccidents);
            vehicle.setNumRobberies(this.numRobberies);
            vehicle.setNumSalvages(this.numSalvages);
            vehicle.setNumServices(this.numServices);
            vehicle.setNumOwners(this.numOwners);
            vehicle.setPolicyNumber(this.policyNumber);
            vehicle.setInsuranceId(this.insuranceId);

            return vehicle;
        }
    }
}
