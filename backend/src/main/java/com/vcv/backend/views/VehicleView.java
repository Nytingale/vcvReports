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
    private String manufacturer;
    private String engine;
    private String colour;
    private String mileage;
    private String transmission;
    private String dealership;
    private Integer value;
    private String evaluationDate;
    private String registrationDate;
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
        view.manufacturer = vehicle.getManufacturer();
        view.engine = vehicle.getEngine();
        view.colour = vehicle.getColour();
        view.mileage = vehicle.getMileage();
        view.transmission = vehicle.getTransmission();
        view.dealership = vehicle.getDealership();
        view.value = vehicle.getValue();
        view.evaluationDate = dateFormatter.format(vehicle.getEvaluationDate().toInstant());
        view.registrationDate = dateFormatter.format(vehicle.getRegistrationDate().toInstant());
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
        private String manufacturer;
        private String colour;

        public BasicReport() {}
        public BasicReport build(VehicleView vehicle) {
            BasicReport view = new BasicReport();

            view.vin = vehicle.vin;
            view.year = vehicle.year;
            view.make = vehicle.make;
            view.model = vehicle.model;
            view.colour = vehicle.colour;
            view.manufacturer = vehicle.manufacturer;

            return view;
        }
    }

    public static class FullReport {
        private VehicleView vehicle;
        private List<ClaimView> claims;
        private List<JobView> jobs;

        public FullReport() {}
        public FullReport build(VehicleView vehicle, List<ClaimView> claims, List<JobView> jobs) {
            FullReport view = new FullReport();

            view.vehicle = vehicle;
            view.claims = claims;
            view.jobs = jobs;

            return view;
        }
    }
}
