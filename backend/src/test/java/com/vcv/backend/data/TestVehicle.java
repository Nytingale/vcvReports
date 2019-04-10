package com.vcv.backend.data;

import com.vcv.backend.entities.Vehicle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestVehicle {
    private Vehicle oldVehicle;
    private Vehicle newVehicle;
    private List<Vehicle> vehicles;
    private String year;
    private String make;
    private String model;

    public TestVehicle() {
        this.year = "";
        this.make = "";
        this.model = "";
        this.vehicles = new ArrayList<>();
        this.oldVehicle = null;
        this.newVehicle = new Vehicle.Builder()
                .setVin("4T1BE46K87U521931")
                .setYear(2007)
                .setMake("Toyota")
                .setModel("Camry")
                .setValue(65000)
                .setMileage(8500)
                .setDealership("MQI")
                .setEvaluationDate(Timestamp.valueOf(LocalDateTime.now()))
                .setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()))
                .setNumAccidents(1)
                .setNumRobberies(0)
                .setNumSalvages(0)
                .setNumServices(0)
                .setNumOwners(1)
                .setDecoded(false)
                .setWrittenOff(false)
                .setStolen(false)
                .setPolicyNumber("D78FDG785563")
                .setInsuranceId(2L)
                .build();
    }

    public Vehicle getOldVehicle() {
        return oldVehicle;
    }
    public Vehicle getNewVehicle() {
        return newVehicle;
    }
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public String getYear() {
        return year;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }

    public void setOldVehicle(Vehicle oldVehicle) {
        this.oldVehicle = oldVehicle;
    }
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
