package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;

import java.util.List;

public class VehicleView {
    public static class BasicReport {
        private String vin;
        private Integer year;
        private String make;
        private String model;
        private String manufacturer;
        private String colour;

        public BasicReport() {}
        public BasicReport build(Vehicle vehicle) {
            BasicReport view = new BasicReport();

            view.vin = vehicle.getVin();
            view.year = vehicle.getYear();
            view.make = vehicle.getMake();
            view.model = vehicle.getModel();
            view.colour = vehicle.getColour();
            view.manufacturer = vehicle.getManufacturer();

            return view;
        }
    }

    public static class FullReport {
        private Vehicle vehicle;
        private List<Claim> claims;
        private List<Job> jobs;

        public FullReport() {}
        public FullReport build(Vehicle vehicle, List<Claim> claims, List<Job> jobs) {
            FullReport view = new FullReport();

            view.vehicle = vehicle;
            view.claims = claims;
            view.jobs = jobs;

            return view;
        }
    }
}
