package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class VehicleView {
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
    private String mileage;
    private String engine;
    private String drive;
    private String body;
    private Integer doors;
    private Integer seats;
    private Boolean writtenOff;
    private Boolean stolen;
    private Integer numAccidents;
    private Integer numRobberies;
    private Integer numSalvages;
    private Integer numServices;
    private Integer numOwners;
    private String insuranceName;
    private String policyNumber;

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
        view.evaluationDate = dateFormatter.format(vehicle.getEvaluationDate().toInstant());
        view.registrationDate = dateFormatter.format(vehicle.getRegistrationDate().toInstant());
        view.manufacturer = vehicle.getManufacturer();
        view.transmission = vehicle.getTransmission();
        view.fuelType = vehicle.getFuelType();
        view.steering = vehicle.getSteering();
        view.mileage = vehicle.getMileage();
        view.engine = vehicle.getEngine();
        view.colour = vehicle.getColour();
        view.drive = vehicle.getDrive();
        view.body = vehicle.getBody();
        view.doors = vehicle.getDoors();
        view.seats = vehicle.getSeats();
        view.writtenOff = vehicle.isWrittenOff();
        view.stolen = vehicle.isStolen();
        view.numAccidents = vehicle.getNumAccidents();
        view.numRobberies = vehicle.getNumRobberies();
        view.numSalvages = vehicle.getNumSalvages();
        view.numServices = vehicle.getNumServices();
        view.numOwners = vehicle.getNumOwners();
        view.insuranceName = vehicle.getInsuranceName();
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

    public static class BasicReport {
        private String vin;
        private Integer year;
        private String make;
        private String model;
        private String colour;
        private String manufacturer;

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
    public static class FullReport {
        private VehicleView vehicle;
        private List<ClaimView> claims;
        private List<JobView> jobs;

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
