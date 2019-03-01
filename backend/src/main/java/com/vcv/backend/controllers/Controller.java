package com.vcv.backend.controllers;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.VcvInvalidParameterException;
import com.vcv.backend.views.VehicleView;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired private JobService     jobService;
    @Autowired private UserService    userService;
    @Autowired private ClaimService   claimService;
    @Autowired private PolicyService  policyService;
    @Autowired private VehicleService vehicleService;

    @GetMapping("/searchForVehicle")
    public VehicleView.BasicReport searchForVehicle(@RequestParam(value = "vin", required = false) String vin,
                                        @RequestParam(value = "year", required = false) String year,
                                        @RequestParam(value = "make", required = false) String make,
                                        @RequestParam(value = "model", required = false) String model) throws VcvInvalidParameterException {
        try {
            Integer validYear = Utils.isValidYear(year);
            String validVin = Utils.isValidVin(vin);

            if(validVin != null && validYear == null && make == null && model == null) {
                return vehicleService.getBasicReport(validVin);
            } else if(validVin == null && validYear != null && make != null && model != null) {
                return VehicleView.basicReport(vehicleService.getBasicReport(validYear, make, model));
            } else throw new VcvInvalidParameterException("Error 100: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/generateReport")
    public VehicleView.FullReport generateReport(@RequestParam(value = "vin", required = false) String vin) throws VcvInvalidParameterException {
        try {
            String validVin = Utils.isValidVin(vin);

            if(validVin != null) {
                Vehicle vehicle = vehicleService.getBasicReport(validVin);
                List<Job> jobs = jobService.getJobsOnVehicle(validVin);
                List<Claim> claims = claimService.getClaimsOnVehicle(validVin);
                return VehicleView.fullReport(vehicle, jobs, claims);
            } else throw new VcvInvalidParameterException("Error 100: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
