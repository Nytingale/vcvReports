package com.vcv.backend.controllers;

import com.vcv.backend.entities.*;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.views.*;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Website {
    @Autowired private CompanyService companyService;
    @Autowired private VehicleService vehicleService;

    /* Casual Users */
    @GetMapping("/getCompanies")
    public List<CompanyView> getCompanies() {
        try {
            return companyService.getCompanies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/searchForVehicle")
    public VehicleView.BasicReport searchForVehicle(@RequestParam(value = "vin", required = false) String vin,
                                                    @RequestParam(value = "year", required = false) String year,
                                                    @RequestParam(value = "make", required = false) String make,
                                                    @RequestParam(value = "model", required = false) String model) {
        try {
            Integer validYear = Utils.isValidYear(year);
            String validVin = Utils.isValidVin(vin);
            String validMake = Utils.isValidString(make);
            String validModel = Utils.isValidString(model);
            if(validVin != null && validYear == null && validMake == null && validModel == null) {
                return vehicleService.getBasicVehicleReport(validVin);
            } else if(validVin == null && validYear != null && validMake != null && validModel != null) {
                return vehicleService.getBasicVehicleReport(validYear, make, model);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/generateReport")
    public VehicleView.FullReport generateReport(@RequestParam(value = "vin", required = false) String vin) {
        try {
            String validVin = Utils.isValidVin(vin);
            if(validVin != null) {
                return vehicleService.getFullVehicleReport(validVin);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
