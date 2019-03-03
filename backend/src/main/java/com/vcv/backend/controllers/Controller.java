package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.ClaimView;
import com.vcv.backend.views.JobView;
import com.vcv.backend.views.UserView;
import com.vcv.backend.views.VehicleView;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class Controller {
    @Autowired private JobService     jobService;
    @Autowired private UserService    userService;
    @Autowired private ClaimService   claimService;
    @Autowired private PolicyService  policyService;
    @Autowired private VehicleService vehicleService;

    /* Casual Users */
    @GetMapping("/searchForVehicle")
    public VehicleView.BasicReport searchForVehicle(@RequestParam(value = "vin", required = false) String vin,
                                                    @RequestParam(value = "year", required = false) String year,
                                                    @RequestParam(value = "make", required = false) String make,
                                                    @RequestParam(value = "model", required = false) String model) throws ControllerException {
        try {
            Integer validYear = Utils.isValidYear(year);
            String validVin = Utils.isValidVin(vin);
            String validMake = Utils.isValidString(make);
            String validModel = Utils.isValidString(model);

            if(validVin != null && validYear == null && validMake == null && validModel == null) {
                return new VehicleView.BasicReport().build(vehicleService.getVehicle(validVin));
            } else if(validVin == null && validYear != null && validMake != null && validModel != null) {
                return new VehicleView.BasicReport().build(vehicleService.getVehicle(validYear, make, model));
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/generateReport")
    public VehicleView.FullReport generateReport(@RequestParam(value = "vin", required = false) String vin) throws ControllerException {
        try {
            String validVin = Utils.isValidVin(vin);

            if(validVin != null) {
                VehicleView vehicle = vehicleService.getVehicle(validVin);
                List<ClaimView> claims = claimService.getClaims(validVin);
                List<JobView> jobs = jobService.getJobs(validVin);
                return new VehicleView
                        .FullReport()
                        .build(vehicle, claims, jobs);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Company Users */
       /* General */
    @GetMapping("/renewSubscription")
    public UserView.SubscriptionConsole rewnewSubscription(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "type", required = false) String type,
                                                           @RequestParam(value = "email", required = false) String email) throws ControllerException {
        try {
            String validName = Utils.isValidSubscribingCompany(name, type);
            String validEmail = Utils.isValidEmail(email);
            if(validName != null || validEmail != null) {
                UserView user = userService.renewSubscription(validEmail, validName);
                return new UserView
                        .SubscriptionConsole()
                        .build(user);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Dealership */
    @PostMapping("/uploadVehicle")
    public Boolean uploadVehicle(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "file", required = false) MultipartFile file) throws ControllerException {
        try {
            String validName = Utils.isValidString(name);
            MultipartFile validFile = Utils.isValidExcelFile(file);
            if(validName != null && validFile != null) {
                return vehicleService.uploadVehicle(validName, validFile);
            } else if(validFile == null) throw new ControllerException("Error 005: The Uploaded File is not a valid format (.xls, .xlsx)");
            else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
