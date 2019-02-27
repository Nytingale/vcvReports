package com.vcv.backend.controllers;

import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.VcvInvalidParameterException;
import com.vcv.backend.services.*;

import com.vcv.backend.utilities.Utils;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class Controller {
    @Autowired private JobService     jobService;
    @Autowired private UserService    userService;
    @Autowired private ClaimService   claimService;
    @Autowired private PolicyService  policyService;
    @Autowired private VehicleService vehicleService;

    @GetMapping(path = "/search")
    public String search(@RequestParam(value = "vin", required = false) String vin,
                          @RequestParam(value = "year", required = false) String year,
                          @RequestParam(value = "make", required = false) String make,
                          @RequestParam(value = "model", required = false) String model) throws VcvInvalidParameterException {
        try {
            Integer veh_year = Utils.isValidYear(year);
            String veh_vin = Utils.isValidVin(vin);
            String veh_make = make;
            String veh_model = model;

            if(veh_vin != null && veh_year == null && veh_make == null && veh_model == null) {
                return vehicleService.findByVin(veh_vin);
            } else if(veh_vin == null && veh_year != null && veh_make != null && veh_model != null) {
                return vehicleService.findByYearMakeModel(veh_year, veh_make, veh_model);
            } else throw new VcvInvalidParameterException("Error 100: No Valid Parameters");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
