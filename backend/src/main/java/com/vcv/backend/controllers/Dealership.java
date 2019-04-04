package com.vcv.backend.controllers;

import com.vcv.backend.entities.User;
import com.vcv.backend.entities.Vehicle;
import com.vcv.backend.exceptions.ControllerException;
import com.vcv.backend.services.*;
import com.vcv.backend.utilities.Utils;
import com.vcv.backend.views.MessageView;
import com.vcv.backend.views.VehicleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dealership")
public class Dealership {
    @Autowired private UserService userService;
    @Autowired private VehicleService vehicleService;

    @GetMapping("/getRegisteredVehicles")
    public List<VehicleView> getRegisteredVehicles(@RequestParam(value = "dealership", required = false) String dealership) {
        try {
            String validDealership = Utils.isValidString(dealership);
            if(validDealership != null) {
                return vehicleService.getRegisteredVehicles(validDealership);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/registerVehicle")
    public MessageView.Registration registerVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle validVehicle = (Vehicle) Utils.isValidEntity(vehicle);
            if(validVehicle != null) {
                return vehicleService.register(validVehicle);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/changePassword")
    public MessageView.UserReport changePassword(@RequestBody Map<String, Object> map) {
        try {
            User validUser = (User) Utils.isValidEntity(map.get("User"));
            String validNewPassword = Utils.isValidPassword((String) map.get("New Password"));
            if(validUser != null && validNewPassword != null) {
                return userService.changePassword(validUser, validNewPassword);
            } else throw new ControllerException("Error 001: No Valid Parameters Used");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
