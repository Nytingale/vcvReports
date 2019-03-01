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

        public BasicReport vin(String vin) {
            this.vin = vin;
            return this;
        }
        public BasicReport year(Integer year) {
            this.year = year;
            return this;
        }
        public BasicReport make(String make) {
            this.make = make;
            return this;
        }
        public BasicReport model(String model) {
            this.model = model;
            return this;
        }
        public BasicReport manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }
        public BasicReport colour(String colour) {
            this.colour = colour;
            return this;
        }

        public BasicReport() {}
        public BasicReport build() {
            BasicReport view = new BasicReport();

            view.vin = vin;
            view.year = year;
            view.make = make;
            view.model = model;
            view.manufacturer = manufacturer;
            view.colour = colour;

            return view;
        }
    }

    public static class FullReport {
        private Vehicle vehicle;
        private List<Claim> claims;
        private List<Job> jobs;

        public FullReport vehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }
        public FullReport claims(List<Claim> claims) {
            this.claims = claims;
            return this;
        }
        public FullReport jobs(List<Job> jobs) {
            this.jobs = jobs;
            return this;
        }

        public FullReport() {}
        public FullReport build() {
            FullReport view = new FullReport();

            view.vehicle = vehicle;
            view.claims = claims;
            view.jobs = jobs;

            return view;
        }
    }
}
