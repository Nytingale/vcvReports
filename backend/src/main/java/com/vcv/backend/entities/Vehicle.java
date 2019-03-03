package com.vcv.backend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Vehicles")
public class Vehicle {
    @Id
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String manufacturer;
    private String engine;
    private String colour;
    private String mileage;
    private String transmission;
    private String dealership;
    private Integer value;
    private Boolean writtenOff;
    private Boolean stolen;
    private Timestamp evaluationDate;
    private Timestamp registrationDate;
    private Integer numAccidents;
    private Integer numRobberies;
    private Integer numSalvages;
    private Integer numServices;
    private Integer numOwners;
    private String insuranceName;
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
    public String getManufacturer() {
        return manufacturer;
    }
    public String getEngine() {
        return engine;
    }
    public String getColour() {
        return colour;
    }
    public String getMileage() {
        return mileage;
    }
    public String getTransmission() {
        return transmission;
    }
    public String getDealership() {
        return dealership;
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
    public String getInsuranceName() {
        return insuranceName;
    }
    public String getPolicyNumber() {
        return policyNumber;
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
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
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
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, year, make, model, manufacturer, engine, colour, mileage, transmission, value, dealership, writtenOff, stolen, evaluationDate, registrationDate, numAccidents, numRobberies, numSalvages, numServices, numOwners, insuranceName, policyNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return year == vehicle.year &&
                value == vehicle.value &&
                writtenOff == vehicle.writtenOff &&
                stolen == vehicle.stolen &&
                numAccidents == vehicle.numAccidents &&
                numRobberies == vehicle.numRobberies &&
                numSalvages == vehicle.numSalvages &&
                numServices == vehicle.numServices &&
                numOwners == vehicle.numOwners &&
                vin.equals(vehicle.vin) &&
                make.equals(vehicle.make) &&
                model.equals(vehicle.model) &&
                manufacturer.equals(vehicle.manufacturer) &&
                engine.equals(vehicle.engine) &&
                colour.equals(vehicle.colour) &&
                mileage.equals(vehicle.mileage) &&
                transmission.equals(vehicle.transmission) &&
                dealership.equals(vehicle.dealership) &&
                evaluationDate.equals(vehicle.evaluationDate) &&
                registrationDate.equals(vehicle.registrationDate) &&
                insuranceName.equals(vehicle.insuranceName) &&
                policyNumber.equals(vehicle.policyNumber);
    }
}
